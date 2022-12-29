package JGiven.Stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.samples.svg.figures.SVGEllipseFigure;

import static org.junit.Assert.*;

public class Then extends Stage<Then> {

    @ScenarioState
    Drawing drawing;
    SVGEllipseFigure originalEllipse;

    public Then ellipseHasBeenTranslated() {
        assertNotNull(drawing);
        assertNotNull(originalEllipse);
        assertTrue(drawing.getFiguresFrontToBack().get(0) instanceof SVGEllipseFigure);
        SVGEllipseFigure translatedEllipse = (SVGEllipseFigure) drawing.getFiguresFrontToBack().get(0);
        assertNotEquals(originalEllipse.getX(), translatedEllipse.getX());
        assertNotEquals(originalEllipse.getY(), translatedEllipse.getY());
        return this;
    }

}
