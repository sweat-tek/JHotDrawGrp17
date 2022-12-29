package JGiven;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.samples.svg.figures.SVGEllipseFigure;

import java.awt.geom.AffineTransform;
import java.util.List;

import static org.junit.Assert.*;

public class When extends Stage<When> {

    @ScenarioState
    Drawing drawing;

    @ScenarioState
    SVGEllipseFigure originalEllipse;

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

}
