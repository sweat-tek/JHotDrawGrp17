package org.jhotdraw.samples.svg.action;

import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.figure.AbstractCompositeFigure;
import org.jhotdraw.draw.figure.Figure;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

import static org.jhotdraw.draw.AttributeKeys.WINDING_RULE;

public class WindingRuleAction extends AbstractAction {

    List<Figure> children = null;
    AbstractCompositeFigure figure;
    Drawing drawing;
    AttributeKeys.WindingRule rule;

    public WindingRuleAction(List<Figure> children, AbstractCompositeFigure figure, Drawing drawing, AttributeKeys.WindingRule rule) {
        this.children = children;
        this.figure = figure;
        this.drawing = drawing;
        this.rule = rule;
        System.out.println(rule);
        putValue(Action.NAME, rule.toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        figure.willChange();
        if(rule == AttributeKeys.WindingRule.NON_ZERO) {
            figure.set(WINDING_RULE, rule);
        }
        drawing.fireUndoableEditHappened(
                WINDING_RULE.setUndoable(figure, rule)
        );
        figure.changed();

    }
}
