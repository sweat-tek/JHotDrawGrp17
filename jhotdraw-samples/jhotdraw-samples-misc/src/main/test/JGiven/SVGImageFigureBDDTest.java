package JGiven;

import JGiven.Stages.Given;
import JGiven.Stages.When;
import JGiven.Stages.Then;
import com.tngtech.jgiven.junit.ScenarioTest;
import org.junit.Test;

public class SVGImageFigureBDDTest extends ScenarioTest<Given, When, Then> {

    @Test
    public void drawImage() {
        given().anEmptyDrawing();
        when().anImageIsDrawn();
        then().drawingContainsImage();
    }

    @Test
    public void transformImage() {
        given().aDrawing().and().anSVGImage();
        when().transformIsPerformed();
        then().imageHasBeenTransformed();
    }
}
