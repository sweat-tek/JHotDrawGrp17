package JGiven.Stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.QuadTreeDrawing;
import org.jhotdraw.samples.svg.figures.SVGEllipseFigure;
import org.jhotdraw.samples.svg.figures.SVGPathFigure;

import java.awt.geom.Point2D;

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

    public Given aLine() {
        SVGPathFigure line = new SVGPathFigure();
        Point2D.Double anchor = new Point2D.Double(5,5);
        Point2D.Double lead = new Point2D.Double(10,10);
        line.setBounds(anchor, lead);
        drawing.add(line);
        return this;
    }

}
