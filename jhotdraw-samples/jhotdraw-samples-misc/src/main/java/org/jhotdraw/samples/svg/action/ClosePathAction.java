package org.jhotdraw.samples.svg.action;

import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.figure.AbstractCompositeFigure;
import org.jhotdraw.draw.figure.Figure;

import static org.jhotdraw.draw.AttributeKeys.PATH_CLOSED;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ClosePathAction extends AbstractAction {
    List<Figure> children = null;
    AbstractCompositeFigure figure;
    Drawing drawing;

    ClosePathAction(List<Figure> children, AbstractCompositeFigure figure, Drawing drawing) {
        this.children = children;
        this.figure = figure;
        this.drawing = drawing;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        figure.willChange();
        System.out.println("Figure close path action fired");
        for (Figure child : children) {
            drawing.fireUndoableEditHappened(
                    PATH_CLOSED.setUndoable(child, true)
            );
        }
        figure.changed();
    }
}
