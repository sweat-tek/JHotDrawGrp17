package JGiven.Stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.QuadTreeDrawing;
import org.jhotdraw.samples.svg.figures.SVGEllipseFigure;

public class Given extends Stage<Given> {

    @ProvidedScenarioState
    Drawing drawing;

    public Given anEmptyDrawing(){
        drawing = new QuadTreeDrawing();
        return this;
    }


    public Given aDrawing(){
        drawing = new QuadTreeDrawing();
        return this;
    }

    public Given anSVGEllipse(){
        drawing.add(new SVGEllipseFigure(1, 1, 5, 5));
        return this;
    }

}
