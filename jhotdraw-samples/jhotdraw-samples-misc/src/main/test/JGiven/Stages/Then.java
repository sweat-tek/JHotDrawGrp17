package JGiven.Stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.samples.svg.figures.SVGBezierFigure;
import org.jhotdraw.samples.svg.figures.SVGEllipseFigure;
import org.jhotdraw.samples.svg.figures.SVGPathFigure;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;

import static org.jhotdraw.draw.AttributeKeys.WINDING_RULE;
import static org.junit.Assert.*;

public class Then extends Stage<Then> {

    @ScenarioState
    Drawing drawing;
    @ScenarioState
    SVGEllipseFigure originalEllipse;
    @ScenarioState
    SVGRectFigure roundedRect;

    public Then ellipseHasBeenTranslated() {
        assertNotNull(drawing);
        assertNotNull(originalEllipse);
        assertTrue(drawing.getFiguresFrontToBack().get(0) instanceof SVGEllipseFigure);
        SVGEllipseFigure translatedEllipse = (SVGEllipseFigure) drawing.getFiguresFrontToBack().get(0);
        assertNotEquals(originalEllipse.getX(), translatedEllipse.getX());
        assertNotEquals(originalEllipse.getY(), translatedEllipse.getY());
        return this;
    }

    public Then drawingContainsEllipse(){
        assertNotEquals(0, drawing.getFiguresFrontToBack().size());
        assertTrue(drawing.getFiguresFrontToBack().get(0) instanceof SVGEllipseFigure);
        return this;
    }

    public Then drawingContainsPolygon(){
        Figure polygon = drawing.getChildren().get(0);
        assertNotEquals(0, drawing.getChildren().size());
        assertEquals(polygon.getClass(), SVGBezierFigure.class);
        return this;
    }

    public Then drawingContainsLine() {
        Figure line = drawing.getChildren().get(0);
        assertNotEquals(0, drawing.getChildren());
        assertEquals(line.getClass(), SVGPathFigure.class);
        return this;
    }

    public Then lineActionRuleHasChanged() {
        Figure line = drawing.getChildren().get(0);
        assertEquals(AttributeKeys.WindingRule.EVEN_ODD, line.get(WINDING_RULE));
        return this;
    }

    public Then drawingContainsRectangle() {
        assertNotNull(drawing);
        Figure rectangle = drawing.getChildren().get(0);
        assertEquals(rectangle.getClass(), SVGRectFigure.class);
        return this;
    }

    public Then roundedRectAction() {
        assertNotNull(roundedRect);
        assertEquals(roundedRect.getClass(), SVGRectFigure.class);
        assertNotNull(drawing);
        Figure rectangle = drawing.getChildren().get(0);
        assertEquals(rectangle.getClass(), SVGRectFigure.class);
        assertNotEquals(roundedRect, rectangle);
        return this;
    }

    public Then thereAreMoreNodes(){
        assertTrue(drawing.getChildren().get(0) instanceof SVGBezierFigure);
        SVGBezierFigure polygon = (SVGBezierFigure) drawing.getChildren().get(0);
        assertNotEquals(2,polygon.getNodeCount());
        return this;
    }

}
