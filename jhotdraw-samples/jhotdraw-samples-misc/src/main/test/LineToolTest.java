
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.samples.svg.figures.SVGPathFigure;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import static org.jhotdraw.draw.AttributeKeys.WINDING_RULE;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LineToolTest {

    Graphics2D g;
    SVGPathFigure pathFigure;

    @Before
    public void testSetup() {
        g = mock(Graphics2D.class);
        System.out.println("Testing a line can be created");
        pathFigure = new SVGPathFigure(false);
        Point2D.Double anchor = new Point2D.Double(5,5);
        Point2D.Double lead = new Point2D.Double(10,10);
        pathFigure.setBounds(anchor, lead);
        System.out.println("\t testing if height of line is as expected");
        assertEquals(5.0, pathFigure.getBounds().getHeight(), 0);
    }

    @Test
    public void testLineToolDraw() {
        System.out.println("Testing if line has been drawn on drawingarea");
        assertNotNull(pathFigure.getDrawingArea());
        assertNotNull(pathFigure.getBounds());
    }

    @Test
    public void testLineToolTransformation() {
        System.out.println("Testing transformation of Line");
        System.out.println("\t testing if coordinates of line are as expected");
        assertEquals( 5, pathFigure.getBounds().getX(), 0);
        assertEquals( 5, pathFigure.getBounds().getY(), 0);
        System.out.println("\t transforming line");
        AffineTransform transform = new AffineTransform();
        transform.setToTranslation(15,20);
        pathFigure.transform(transform);
        System.out.println("\t testing if coordinates of line have changes");
        assertNotEquals( 5, pathFigure.getBounds().getX(), 0);
        assertNotEquals( 5, pathFigure.getBounds().getY(), 0);
    }

    @Test
    public void testLineClone() {
        SVGPathFigure clone = pathFigure.clone();
        System.out.println(clone.getBounds());
        System.out.println("\t testing if height of clone is as expected");
        assertEquals(5.0, pathFigure.getBounds().getHeight(), 0);
    }

    @Test
    public void testActions() {
        System.out.println("Testing actions on Line");
        System.out.println("\t testing if line has actions at point (5,5)");
        Collection<Action> anchorActions = pathFigure.getActions(new Point2D.Double(5,5));
        assertEquals(2, anchorActions.size(), 0);

        System.out.println("\t testing if line has actions at point (10,10)");
        Collection<Action> leadActions = pathFigure.getActions(new Point2D.Double(10,10));
        assertEquals(2, leadActions.size(), 0);

        System.out.println("\t testing if you can change the WindinRule action");
        assertEquals(AttributeKeys.WindingRule.NON_ZERO, pathFigure.get(WINDING_RULE));
        pathFigure.set(WINDING_RULE, AttributeKeys.WindingRule.EVEN_ODD);
        assertEquals(AttributeKeys.WindingRule.EVEN_ODD, pathFigure.get(WINDING_RULE));
    }
}
