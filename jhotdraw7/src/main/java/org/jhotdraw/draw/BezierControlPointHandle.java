/*
 * @(#)BezierControlPointHandle.java  2.0  2008-05-12
 *
 * Copyright (c) 1996-2008 by the original authors of JHotDraw
 * and all its contributors.
 * All rights reserved.
 *
 * The copyright of this software is owned by the authors and  
 * contributors of the JHotDraw project ("the copyright holders").  
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * the copyright holders. For details see accompanying license terms. 
 */
package org.jhotdraw.draw;

import org.jhotdraw.util.*;
import org.jhotdraw.undo.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import org.jhotdraw.geom.*;
import static org.jhotdraw.samples.svg.SVGAttributeKeys.*;

/**
 * BezierControlPointHandle.
 *
 * @author Werner Randelshofer
 * @version 2.0 2008-05-11 Handle attributes are now retrieved from
 * DrawingEditor. Added keyPressed method.
 * <br>1.0 23. Januar 2006 Created.
 */
public class BezierControlPointHandle extends AbstractHandle {

    protected int index,  controlPointIndex;
    private CompositeEdit edit;
    private Figure transformOwner;

    /** Creates a new instance. */
    public BezierControlPointHandle(BezierFigure owner, int index, int coord) {
        this(owner, index, coord, owner);
    }

    public BezierControlPointHandle(BezierFigure owner, int index, int coord, Figure transformOwner) {
        super(owner);
        this.index = index;
        this.controlPointIndex = coord;
        this.transformOwner = transformOwner;
        transformOwner.addFigureListener(this);
    }

    public void dispose() {
        super.dispose();
        transformOwner.removeFigureListener(this);
        transformOwner = null;
    }

    protected BezierFigure getBezierFigure() {
        return (BezierFigure) getOwner();
    }

    protected Figure getTransformOwner() {
        return transformOwner;
    }

    protected Point getLocation() {
        if (getBezierFigure().getNodeCount() > index) {
            Point2D.Double p = getBezierFigure().getPoint(index, controlPointIndex);
            if (TRANSFORM.get(getTransformOwner()) != null) {
                TRANSFORM.get(getTransformOwner()).transform(p, p);
            }
            return view.drawingToView(p);
        } else {
            return new Point(10, 10);
        }
    }

    protected BezierPath.Node getBezierNode() {
        return getBezierFigure().getNodeCount() > index ? getBezierFigure().getNode(index) : null;
    }

    /**
     * Draws this handle.
     */
    public void draw(Graphics2D g) {
        BezierFigure f = getBezierFigure();
        if (f.getNodeCount() > index) {
            BezierPath.Node v = f.getNode(index);
            Point2D.Double p0 = new Point2D.Double(v.x[0], v.y[0]);
            Point2D.Double pc = new Point2D.Double(v.x[controlPointIndex], v.y[controlPointIndex]);
            if (TRANSFORM.get(getTransformOwner()) != null) {
                TRANSFORM.get(getTransformOwner()).transform(p0, p0);
                TRANSFORM.get(getTransformOwner()).transform(pc, pc);
            }

            Stroke stroke = (Stroke) getEditor().getHandleAttribute(HandleAttributeKeys.BEZIER_CONTROL_POINT_HANDLE_STROKE_1);
            Color strokeColor = (Color) getEditor().getHandleAttribute(HandleAttributeKeys.BEZIER_CONTROL_POINT_HANDLE_STROKE_COLOR_1);
            if (stroke != null && strokeColor != null) {
                g.setStroke(stroke);
                g.setColor(strokeColor);
                g.draw(new Line2D.Double(
                        view.drawingToView(p0),
                        view.drawingToView(pc)));
            }
            stroke = (Stroke) getEditor().getHandleAttribute(HandleAttributeKeys.BEZIER_CONTROL_POINT_HANDLE_STROKE_2);
            strokeColor = (Color) getEditor().getHandleAttribute(HandleAttributeKeys.BEZIER_CONTROL_POINT_HANDLE_STROKE_COLOR_2);
            if (stroke != null && strokeColor != null) {
                g.setStroke(stroke);
                g.setColor(strokeColor);
                g.draw(new Line2D.Double(
                        view.drawingToView(p0),
                        view.drawingToView(pc)));
            }
            Color fillColor = (Color) getEditor().getHandleAttribute(HandleAttributeKeys.BEZIER_CONTROL_POINT_HANDLE_FILL_COLOR);
            strokeColor = (Color) getEditor().getHandleAttribute(HandleAttributeKeys.BEZIER_CONTROL_POINT_HANDLE_STROKE_COLOR);
            if (v.keepColinear && v.mask == BezierPath.C1C2_MASK &&
                    (index > 0 && index < f.getNodeCount() - 1 || f.isClosed())) {
                drawCircle(g, strokeColor, fillColor);
            } else {
                drawCircle(g, fillColor, strokeColor);
            }

        }
    }

    protected Rectangle basicGetBounds() {
        Rectangle r = new Rectangle(getLocation());
        int h = getHandlesize();
        r.x -= h / 2;
        r.y -= h / 2;
        r.width = r.height = h;
        return r;
    }

    public void trackStart(Point anchor, int modifiersEx) {
        view.getDrawing().fireUndoableEditHappened(edit = new CompositeEdit("Punkt verschieben"));
        Point2D.Double location = view.getConstrainer().constrainPoint(view.viewToDrawing(getLocation()));
        Point2D.Double p = view.getConstrainer().constrainPoint(view.viewToDrawing(anchor));
    }

    public void trackStep(Point anchor, Point lead, int modifiersEx) {
        BezierFigure figure = getBezierFigure();
        Point2D.Double p = view.getConstrainer().constrainPoint(view.viewToDrawing(lead));
        BezierPath.Node v = figure.getNode(index);
        fireAreaInvalidated(v);
        figure.willChange();

        if (TRANSFORM.get(getTransformOwner()) != null) {
            try {
                TRANSFORM.get(getTransformOwner()).inverseTransform(p, p);
            } catch (NoninvertibleTransformException ex) {
                ex.printStackTrace();
            }
        }

        if (!v.keepColinear) {
            // move control point independently
            figure.setPoint(index, controlPointIndex, p);

        } else {
            // move control point and opposite control point on same line
            double a = Math.PI + Math.atan2(p.y - v.y[0], p.x - v.x[0]);
            int c2 = (controlPointIndex == 1) ? 2 : 1;
            double r = Math.sqrt((v.x[c2] - v.x[0]) * (v.x[c2] - v.x[0]) +
                    (v.y[c2] - v.y[0]) * (v.y[c2] - v.y[0]));
            double sina = Math.sin(a);
            double cosa = Math.cos(a);

            Point2D.Double p2 = new Point2D.Double(
                    r * cosa + v.x[0],
                    r * sina + v.y[0]);
            figure.setPoint(index, controlPointIndex, p);
            figure.setPoint(index, c2, p2);
        }
        figure.changed();
        fireAreaInvalidated(figure.getNode(index));

    }

    private void fireAreaInvalidated(BezierPath.Node v) {
        Rectangle2D.Double dr = new Rectangle2D.Double(v.x[0], v.y[0], 0, 0);
        for (int i = 1; i < 3; i++) {
            dr.add(v.x[i], v.y[i]);
        }
        Rectangle vr = view.drawingToView(dr);
        vr.grow(getHandlesize(), getHandlesize());
        fireAreaInvalidated(vr);
    }

    public void trackEnd(Point anchor, Point lead, int modifiersEx) {
        BezierFigure figure = getBezierFigure();
        if ((modifiersEx & (InputEvent.META_DOWN_MASK | InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK)) != 0) {
            figure.willChange();
            BezierPath.Node v = figure.getNode(index);
            v.keepColinear = !v.keepColinear;
            if (v.keepColinear) {
                // move control point and opposite control point on same line
                Point2D.Double p = figure.getPoint(index, controlPointIndex);
                double a = Math.PI + Math.atan2(p.y - v.y[0], p.x - v.x[0]);
                int c2 = (controlPointIndex == 1) ? 2 : 1;
                double r = Math.sqrt((v.x[c2] - v.x[0]) * (v.x[c2] - v.x[0]) +
                        (v.y[c2] - v.y[0]) * (v.y[c2] - v.y[0]));
                double sina = Math.sin(a);
                double cosa = Math.cos(a);

                Point2D.Double p2 = new Point2D.Double(
                        r * cosa + v.x[0],
                        r * sina + v.y[0]);
                v.x[c2] = p2.x;
                v.y[c2] = p2.y;
            }
            figure.setNode(index, v);
            figure.changed();
        }
        view.getDrawing().fireUndoableEditHappened(edit);
    }

    public boolean isCombinableWith(Handle h) {
        if (super.isCombinableWith(h)) {
            BezierControlPointHandle that = (BezierControlPointHandle) h;
            return that.index == this.index &&
                    that.controlPointIndex == this.controlPointIndex &&
                    that.getBezierFigure().getNodeCount() ==
                    this.getBezierFigure().getNodeCount();
        }
        return false;
    }

    public String getToolTipText(Point p) {
        ResourceBundleUtil labels = ResourceBundleUtil.getLAFBundle("org.jhotdraw.draw.Labels");
        BezierPath.Node node = getBezierNode();
        if (node == null) {
            return null;
        }
        if (node.mask == BezierPath.C1C2_MASK) {
            return labels.getFormatted("bezierCubicControlHandle.tip",
                    labels.getFormatted(
                    node.keepColinear ? "bezierCubicControl.colinearControl" : "bezierCubicControl.unconstrainedControl"));
        } else {
            return labels.getString("bezierQuadraticControlHandle.tip");
        }
    }
    public BezierFigure getOwner() {
        return (BezierFigure) super.getOwner();
    }

    public void keyPressed(KeyEvent evt) {
        final BezierFigure f = getOwner();
        BezierPath.Node oldNode = f.getNode(index);

        switch (evt.getKeyCode()) {
            case KeyEvent.VK_UP:
                f.willChange();
                f.setPoint(index, controlPointIndex, new Point2D.Double(oldNode.x[controlPointIndex], oldNode.y[controlPointIndex] - 1d));
                f.changed();
                view.getDrawing().fireUndoableEditHappened(new BezierNodeEdit(f, index, oldNode, f.getNode(index)));
                evt.consume();
                break;
            case KeyEvent.VK_DOWN:
                f.willChange();
                f.setPoint(index, controlPointIndex, new Point2D.Double(oldNode.x[controlPointIndex], oldNode.y[controlPointIndex] + 1d));
                f.changed();
                view.getDrawing().fireUndoableEditHappened(new BezierNodeEdit(f, index, oldNode, f.getNode(index)));
                evt.consume();
                break;
            case KeyEvent.VK_LEFT:
                f.willChange();
                f.setPoint(index, controlPointIndex, new Point2D.Double(oldNode.x[controlPointIndex] - 1d, oldNode.y[controlPointIndex]));
                f.changed();
                view.getDrawing().fireUndoableEditHappened(new BezierNodeEdit(f, index, oldNode, f.getNode(index)));
                evt.consume();
                break;
            case KeyEvent.VK_RIGHT:
                f.willChange();
                f.setPoint(index, controlPointIndex, new Point2D.Double(oldNode.x[controlPointIndex] + 1d, oldNode.y[controlPointIndex]));
                f.changed();
                view.getDrawing().fireUndoableEditHappened(new BezierNodeEdit(f, index, oldNode, f.getNode(index)));
                evt.consume();
                break;
        }
    }
}
