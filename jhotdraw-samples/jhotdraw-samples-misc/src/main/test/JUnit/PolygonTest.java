package JUnit;

import org.jhotdraw.geom.BezierPath;
import org.jhotdraw.samples.svg.figures.SVGBezierFigure;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class PolygonTest {
    Graphics2D g;
    SVGBezierFigure bezierFigure;

    @Before
    public void initialMock() {
        g = mock(Graphics2D.class);
        System.out.println("Testing if a polygon can be created");
        bezierFigure = new SVGBezierFigure(false);
        Point2D.Double anchor = new Point2D.Double(5,5);
        Point2D.Double lead = new Point2D.Double(10,10);
        bezierFigure.setBounds(anchor, lead);
        System.out.println("\t testing if height of polygon is as expected");
        assertEquals(5.0, bezierFigure.getBounds().getHeight(), 0);
    }

    @Test
    public void testPolygonToolDraw() {
        System.out.println("Testing if line has been drawn on drawingarea");
        assertNotNull(bezierFigure.getDrawingArea());
        assertNotNull(bezierFigure.getBounds());
    }

    @Test
    public void testPolygonAddNode(){
        System.out.println("Testing if more nodes can be added to polygon");
        System.out.println("Testing simple add");
        bezierFigure.addNode(new BezierPath.Node(7,7));
        assertEquals(3, bezierFigure.getNodeCount(), 0);

        System.out.println("Testing specific add");
        bezierFigure.addNode(new BezierPath.Node(1, new Point2D.Double(1,1),new Point2D.Double(2,2),new Point2D.Double(3,3)));
        System.out.println(bezierFigure.getBounds());
        assertEquals(4,bezierFigure.getNodeCount(),0);
    }

    @Test
    public void testPolyClone() {
        SVGBezierFigure clone = (SVGBezierFigure) bezierFigure.clone();
        System.out.println(clone.getBounds());
        System.out.println("Testing if height of clone is as expected");
        assertEquals(5.0, bezierFigure.getBounds().getHeight(), 0);
    }

    @Test
    public void testRemoveNode(){
        System.out.println("Testing if nodes can be removed");
        bezierFigure.removeNode(bezierFigure.getNodeCount()-1);
        assertEquals(1, bezierFigure.getNodeCount(),0);
    }

    @Test
    public void testArcTo(){
        System.out.println("Testing the arcTo method");
        BezierPath path = bezierFigure.getBezierPath();
        Rectangle2D.Double area1 = bezierFigure.getDrawingArea();

        path.arcTo(2,3,90,true,true, new Point2D.Double(8,9));
        bezierFigure.setBezierPath(path);

        assertNotEquals(area1,bezierFigure.getDrawingArea());
    }
}
