package JUnit;

import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.figure.ImageFigure;
import org.jhotdraw.draw.figure.ImageHolderFigure;
import org.jhotdraw.draw.tool.ImageTool;
import org.jhotdraw.samples.svg.figures.SVGImageFigure;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

import javax.imageio.IIOException;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ImageToolTest {

    Graphics2D g;
    SVGImageFigure imageFigure;

    @Before
    public void testSetup() {
        g = mock(Graphics2D.class);
        System.out.println("Testing an Image can be created");
        imageFigure = new SVGImageFigure(1,1,50,50);
        assertEquals(50, imageFigure.getBounds().getHeight(), 0);
    }

    @Test
    public void testImageInsert(){
        System.out.println("Testing if image is present");
        try{
        imageFigure.loadImage(new File("src/main/resources/TestAssets/flatpepe.jpeg"));
        }catch (IOException e){
        }
        imageFigure.setImage(imageFigure.getImageData(), imageFigure.getBufferedImage());
        assertFalse(imageFigure.isEmpty());
    }

    @Test
    public void testImageClone() {
        SVGImageFigure clone = imageFigure.clone();
        System.out.println(clone.getBounds());
        System.out.println("\t Testing if height of clone is as expected");
        assertEquals(50, imageFigure.getBounds().getHeight(), 0);
    }

    @Test
    public void testImageTransformation() {
        System.out.println("Testing transformation of image");
        System.out.println("\t Testing if coordinates of image are as expected");
        assertEquals(1, imageFigure.getBounds().getX(), 0);
        assertEquals(1, imageFigure.getBounds().getY(), 0);
        System.out.println("\t Transforming line");
        AffineTransform transform = new AffineTransform();
        transform.setToTranslation(10, 30);
        imageFigure.transform(transform);
        System.out.println("\t Testing if coordinates of image has changed");
        assertNotEquals(1, imageFigure.getBounds().getX(), 0);
        assertNotEquals(1, imageFigure.getBounds().getY(), 0);
    }

}
