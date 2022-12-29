import org.jhotdraw.samples.svg.figures.SVGEllipseFigure;

import static org.jhotdraw.draw.AttributeKeys.TRANSFORM;
import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class SVGEllipseTest {

    @BeforeClass
    public static void setup(){
        System.out.println("Now Testing SVGEllipseTool");
    }

    @Test
    public void testCreateEllipse() {
        System.out.println("Testing an ellipse can be created");
        SVGEllipseFigure figure = new SVGEllipseFigure(1, 1, 50, 50);
        assertNotNull(figure);
        assertFalse(figure.isEmpty());
        assertNotNull(figure.getEllipse());
        assertNotNull(figure.getDrawingArea());
        assertNotNull(figure.getBounds());
    }

    @Test
    public void testTranslation(){
        System.out.println("Testing whether an ellipse can be moved");
        double ellipseStartX = 1;
        double ellipseStartY = 1;
        double ellipseStartWidth = 10;
        double ellipseStartHeight = 10;
        double translationValue = 5;
        SVGEllipseFigure figure = new SVGEllipseFigure(
                ellipseStartX, ellipseStartY,
                ellipseStartWidth, ellipseStartHeight
        );
        System.out.println("Assert figure x is the same as startX");
        assertEquals(figure.getX(), ellipseStartX, 0.1);
        System.out.println("Assert figure y is the same as startY");
        assertEquals(figure.getX(), ellipseStartY, 0.1);

        AffineTransform transformation = new AffineTransform();
        transformation.setToTranslation(translationValue, translationValue);
        System.out.println("Applying translation");
        figure.transform(transformation);

        System.out.println("Assert figure x is NOT the same as startX");
        assertNotEquals(figure.getX(), ellipseStartX, 0.1);
        System.out.println("Assert figure y is the NOT same as startY");
        assertNotEquals(figure.getX(), ellipseStartY, 0.1);
    }

    @Test
    public void testContains(){
        SVGEllipseFigure figure = new SVGEllipseFigure(1, 1, 5, 5);
        Point2D.Double pointInside = new Point2D.Double(2.5, 2.5);
        Point2D.Double pointOutside = new Point2D.Double(250, 250);
        System.out.println("Testing that point " + pointInside.toString() + " is within ellipse ");
        assertTrue(figure.contains(pointInside));
        System.out.println("Testing that point " + pointOutside.toString() + " is NOT within ellipse ");
        assertFalse(figure.contains(pointOutside));
    }

}
