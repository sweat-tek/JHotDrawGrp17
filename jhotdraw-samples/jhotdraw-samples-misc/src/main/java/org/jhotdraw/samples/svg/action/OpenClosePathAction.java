package org.jhotdraw.samples.svg.action;

import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.figure.AbstractCompositeFigure;
import org.jhotdraw.draw.figure.Figure;

import static org.jhotdraw.draw.AttributeKeys.PATH_CLOSED;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class OpenClosePathAction extends AbstractAction {
    List<Figure> children = null;
    AbstractCompositeFigure figure;
    Drawing drawing;
    boolean open;

    public OpenClosePathAction(List<Figure> children, AbstractCompositeFigure figure, Drawing drawing, boolean open) {
        this.children = children;
        this.figure = figure;
        this.drawing = drawing;
        this.open = open;
        putValue(Action.NAME, open ? "Close Path" : "Open Path");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        figure.willChange();
        System.out.println("Figure path action fired");
        for (Figure child : children) {
            drawing.fireUndoableEditHappened(
                    PATH_CLOSED.setUndoable(child, open)
            );
        }
        figure.changed();
    }
}
