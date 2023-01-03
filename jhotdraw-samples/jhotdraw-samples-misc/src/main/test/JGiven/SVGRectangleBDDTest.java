package JGiven;

import JGiven.Stages.Given;
import JGiven.Stages.Then;
import JGiven.Stages.When;
import com.tngtech.jgiven.junit.ScenarioTest;
import org.junit.Test;

public class SVGRectangleBDDTest extends ScenarioTest<Given, When, Then> {
    @Test
    public void drawRectangle() {
        given().anEmptyDrawing();
        when().aRectangleIsDrawn();
        then().drawingContainsRectangle();
    }

    @Test
    public void changeAction() {
        given().anEmptyDrawing().and().aRectangle();
        when().aRectangleRounded();
        then().roundedRectAction();
    }
}
