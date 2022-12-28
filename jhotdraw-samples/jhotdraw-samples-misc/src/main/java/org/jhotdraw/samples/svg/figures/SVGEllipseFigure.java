/*
 * @(#)SVGEllipse.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.samples.svg.figures;

import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.geom.Geom;
import org.jhotdraw.geom.GrowStroke;
import org.jhotdraw.samples.svg.Gradient;
import org.jhotdraw.samples.svg.SVGAttributeKeys;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import static org.jhotdraw.draw.AttributeKeys.FILL_COLOR;
import static org.jhotdraw.draw.AttributeKeys.TRANSFORM;
import static org.jhotdraw.samples.svg.SVGAttributeKeys.FILL_GRADIENT;
import static org.jhotdraw.samples.svg.SVGAttributeKeys.STROKE_GRADIENT;

/**
 * SVGEllipse represents a SVG ellipse and a SVG circle element.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class SVGEllipseFigure extends SVGAttributedFigure implements SVGFigure {

    private static final long serialVersionUID = 1L;
    private static final double MIN_WIDTH = 0.1;
    private static final double MIN_HEIGHT = 0.1;
    private Ellipse2D.Double ellipse;
    /**
     * This is used to perform faster drawing and hit testing.
     */
    private transient Shape cachedTransformedShape;
    /**
     * This is used to perform faster hit testing.
     */
    private transient Shape cachedHitShape;

    /**
     * Creates a new instance.
     */
    public SVGEllipseFigure() {
        this(0, 0, 0, 0);
    }

    public SVGEllipseFigure(double x, double y, double width, double height) {
        ellipse = new Ellipse2D.Double(x, y, width, height);
        SVGAttributeKeys.setDefaults(this);
        setConnectable(false);
    }

    // DRAWING
    @Override
    protected void drawFill(Graphics2D g) {
        if (ellipse.width > 0 && ellipse.height > 0) {
            g.fill(ellipse);
        }
    }

    @Override
    protected void drawStroke(Graphics2D g) {
        if (ellipse.width > 0 && ellipse.height > 0) {
            g.draw(ellipse);
        }
    }

    // SHAPE AND BOUNDS
    public double getX() {
        return ellipse.x;
    }

    public double getY() {
        return ellipse.y;
    }

    public double getWidth() {
        return ellipse.getWidth();
    }

    public double getHeight() {
        return ellipse.getHeight();
    }

    @Override
    public Rectangle2D.Double getBounds() {
        return (Rectangle2D.Double) ellipse.getBounds2D();
    }

    @Override
    public Rectangle2D.Double getDrawingArea() {
        Rectangle2D.Double r = (Rectangle2D.Double) getTransformedShape().getBounds2D();

        if (hasTransformAttribute()) {
            double strokeTotalWidth = AttributeKeys.getStrokeTotalWidth(this, 1.0);
            double width = strokeTotalWidth / 2d;
            width *= Math.max(get(TRANSFORM).getScaleX(), get(TRANSFORM).getScaleY()) + 1;
            Geom.grow(r, width, width);
        } else {
            double g = SVGAttributeKeys.getPerpendicularHitGrowth(this, 1.0) * 2d + 1;
            Geom.grow(r, g, g);
        }
        return r;
    }

    private boolean hasTransformAttribute() {
        return get(TRANSFORM) != null;
    }

    private boolean hasFillGradiantAttribute() {
        return get(FILL_GRADIENT) != null;
    }

    private boolean hasFillColorAttribute() {
        return get(FILL_COLOR) != null;
    }

    /**
     * Checks if a Point2D.Double is inside the figure.
     */
    @Override
    public boolean contains(Point2D.Double p) {
        return getHitShape().contains(p);
    }

    private Shape getTransformedShape() {
        if (cachedTransformedShape == null) {
            if (hasTransformAttribute()) {
                cachedTransformedShape = get(TRANSFORM).createTransformedShape(ellipse);
            } else {
                cachedTransformedShape = ellipse;
            }
        }
        return cachedTransformedShape;
    }

    private Shape getHitShape() {
        if (cachedHitShape != null) {
            return cachedHitShape;
        }

        Shape strokeShape = getTransformedShape();
        float strokeWidth = (float) SVGAttributeKeys.getStrokeTotalWidth(this, 1.0) / 2f;
        float miterLimit = (float) SVGAttributeKeys.getStrokeTotalMiterLimit(this, 1.0);
        Stroke stroke;

        if (hasFillColorAttribute() || hasFillGradiantAttribute()) {
            stroke = new GrowStroke(strokeWidth, miterLimit);
        } else {
            stroke = SVGAttributeKeys.getHitStroke(this, 1.0);
        }
        cachedHitShape = stroke.createStrokedShape(strokeShape);
        return cachedHitShape;
    }

    @Override
    public void setBounds(Point2D.Double anchor, Point2D.Double lead) {
        ellipse.x = Math.min(anchor.x, lead.x);
        ellipse.y = Math.min(anchor.y, lead.y);
        double width = Math.abs(lead.x - anchor.x);
        double height = Math.abs(lead.y - anchor.y);
        ellipse.width = Math.max(MIN_WIDTH, width);
        ellipse.height = Math.max(MIN_HEIGHT, height);
        invalidate();
    }

    /**
     * Transforms the figure.
     *
     * @param tx the transformation.
     */
    @Override
    public void transform(AffineTransform tx) {
        if (hasTransformAttribute() || (tx.getType() != AffineTransform.TYPE_TRANSLATION)) {
            if (hasTransformAttribute()) {
                performTransformation(tx);
            } else {
                TRANSFORM.setClone(this, tx);
            }
        } else {
            performTranslation(tx);
        }
        invalidate();
    }

    public void performTransformation(AffineTransform tx) {
        AffineTransform t = TRANSFORM.getClone(this);
        t.preConcatenate(tx);
        set(TRANSFORM, t);
    }

    public void performTranslation(AffineTransform tx) {
        Point2D.Double anchor = getStartPoint();
        Point2D.Double lead = getEndPoint();
        Point2D.Double transformedAnchor = (Point2D.Double) tx.transform(anchor, anchor);
        Point2D.Double transformedLead = (Point2D.Double) tx.transform(lead, lead);
        setBounds(transformedAnchor, transformedLead);
    }

    @Override
    public void restoreTransformTo(Object geometry) {
        Object[] restoreData = (Object[]) geometry;
        ellipse = (Ellipse2D.Double) ((Ellipse2D.Double) restoreData[0]).clone();
        TRANSFORM.setClone(this, (AffineTransform) restoreData[1]);
        FILL_GRADIENT.setClone(this, (Gradient) restoreData[2]);
        STROKE_GRADIENT.setClone(this, (Gradient) restoreData[3]);
        invalidate();
    }

    @Override
    public Object getTransformRestoreData() {
        return new Object[]{ellipse.clone(), TRANSFORM.getClone(this), FILL_GRADIENT.getClone(this), STROKE_GRADIENT.getClone(this)};
    }

    // CONNECTING
    // COMPOSITE FIGURES
    // CLONING
    @Override
    public SVGEllipseFigure clone() {
        SVGEllipseFigure that = (SVGEllipseFigure) super.clone();
        that.ellipse = (Ellipse2D.Double) this.ellipse.clone();
        that.cachedTransformedShape = null;
        return that;
    }

    // EVENT HANDLING
    @Override
    public boolean isEmpty() {
        Rectangle2D.Double b = getBounds();
        return b.width <= 0 || b.height <= 0;
    }

    @Override
    public void invalidate() {
        super.invalidate();
        cachedTransformedShape = null;
        cachedHitShape = null;
    }
}
