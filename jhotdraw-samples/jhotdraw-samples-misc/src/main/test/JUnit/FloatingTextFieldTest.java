package JUnit;

import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.figure.TextHolderFigure;
import org.jhotdraw.draw.text.FloatingTextField;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FloatingTextFieldTest {

    DrawingView view;

    TextHolderFigure figure;

    FloatingTextField floatingTextField;

    @Before
    public void testSetup() {
        view = mock(DrawingView.class);
        figure = mock(TextHolderFigure.class);
        System.out.println("Testing the overlay method");
    }

    @Test
    public void createOverlay() {
        floatingTextField.createOverlay(view,figure);
        Assert.assertNotNull(floatingTextField);
    }
}
