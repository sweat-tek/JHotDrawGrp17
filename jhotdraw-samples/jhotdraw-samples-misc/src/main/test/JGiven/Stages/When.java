package JGiven.Stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.geom.BezierPath;
import org.jhotdraw.samples.svg.figures.SVGBezierFigure;
import org.jhotdraw.samples.svg.figures.SVGEllipseFigure;
import org.jhotdraw.samples.svg.figures.SVGPathFigure;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import static org.jhotdraw.draw.AttributeKeys.WINDING_RULE;
import static org.junit.Assert.*;

public class When extends Stage<When> {

    @ScenarioState
    Drawing drawing;

    @ScenarioState
    SVGEllipseFigure originalEllipse;
    @ScenarioState
    SVGPathFigure line;

    @ScenarioState
    SVGBezierFigure bezierFigure;

    public When translationIsPerformed(){
        assertNotNull(drawing);
        List<Figure> figures = drawing.getFiguresFrontToBack();
        assertTrue(figures.get(0) instanceof SVGEllipseFigure);

        SVGEllipseFigure ellipseFigure = (SVGEllipseFigure) figures.get(0);
        originalEllipse = ellipseFigure.clone();
        AffineTransform transformation = AffineTransform.getTranslateInstance(5, 5);
        ellipseFigure.transform(transformation);
        return this;
    }

    public When anEllipseIsDrawn() {
        drawing.add(new SVGEllipseFigure(1, 1, 5, 5));
        return this;
    }

    public When aPolygonIsDrawn(){
        bezierFigure = new SVGBezierFigure(false);
        Point2D.Double anchor = new Point2D.Double(5,5);
        Point2D.Double lead = new Point2D.Double(10,10);
        bezierFigure.setBounds(anchor, lead);
        drawing.add(bezierFigure);
        return this;
    }

    public When aLineIsDrawn() {
        SVGPathFigure line = new SVGPathFigure();
        Point2D.Double anchor = new Point2D.Double(5,5);
        Point2D.Double lead = new Point2D.Double(10,10);
        line.setBounds(anchor, lead);
        drawing.add(line);
        return this;
    }

    public When aLineActionIsChanged() {
        Figure line = drawing.getChildren().get(0);
        line.set(WINDING_RULE, AttributeKeys.WindingRule.EVEN_ODD);
        System.out.println(line + " " + line.getActions(new Point2D.Double(5,5)));
        return this;
    }

    public When anArcIsAdded(){
        SVGBezierFigure polygon = (SVGBezierFigure) drawing.getChildren().get(0);
        BezierPath path = polygon.getBezierPath();
        path.arcTo(2,3,90,true,true, new Point2D.Double(8,9));
        polygon.setBezierPath(path);
        return this;
    }
}
