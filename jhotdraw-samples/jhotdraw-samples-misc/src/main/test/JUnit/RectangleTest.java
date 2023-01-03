package JUnit;
import static org.junit.Assert.*;

import org.jhotdraw.draw.action.AlignAction;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import org.junit.Test;

import java.awt.geom.AffineTransform;

public class RectangleTest {

    public static void setup() {
        System.out.println("Testing create rectangle");
    }

    @Test
    public void testCreateRectangle() {
        System.out.println("testing a rectangle can be created");
        SVGRectFigure rectFigure = new SVGRectFigure(10.0, 10.0, 20.0, 30.0);
        assertNotNull(rectFigure);
    }

    @Test
    public void testTransform() {
        System.out.println("Testing transform");
        double startX = 10.0;
        double startY = 10.0;
        SVGRectFigure rectangle = new SVGRectFigure(startX, startY, 20.0, 30.0);
        System.out.println("assert position");
        assertEquals(rectangle.getX(), startX, 0.1);
        assertEquals(rectangle.getX(), startY, 0.1);

        AffineTransform transformation = new AffineTransform();
        transformation.setToTranslation(5, 5);
        rectangle.transform(transformation);
        System.out.println("assert position not the same");
        assertNotEquals(rectangle.getX(), startX, 0.1);
        assertNotEquals(rectangle.getX(), startY, 0.1);
    }
}
