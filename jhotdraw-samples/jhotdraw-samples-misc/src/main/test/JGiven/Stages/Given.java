package JGiven.Stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.QuadTreeDrawing;
import org.jhotdraw.samples.svg.figures.SVGBezierFigure;
import org.jhotdraw.samples.svg.figures.SVGEllipseFigure;
import org.jhotdraw.samples.svg.figures.SVGPathFigure;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;

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

    public Given aPolygon(){
        SVGBezierFigure bezierFigure = new SVGBezierFigure(false);
        Point2D.Double anchor = new Point2D.Double(5,5);
        Point2D.Double lead = new Point2D.Double(10,10);
        bezierFigure.setBounds(anchor, lead);
        drawing.add(bezierFigure);
        return this;
    }

    public Given aRectangle() {
        drawing.add(new SVGRectFigure(10, 10, 15, 5));
        return this;
    }

}
