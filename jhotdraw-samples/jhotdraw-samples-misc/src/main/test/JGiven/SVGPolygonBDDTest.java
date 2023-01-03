package JGiven;

import JGiven.Stages.Given;
import JGiven.Stages.Then;
import JGiven.Stages.When;
import com.tngtech.jgiven.junit.ScenarioTest;
import org.junit.Test;

public class SVGPolygonBDDTest extends ScenarioTest<Given, When, Then> {

    @Test
    public void drawPolygon(){
        given().anEmptyDrawing();
        when().aPolygonIsDrawn();
        then().drawingContainsPolygon();
    }

    @Test
    public void createAnArc() {
        given().anEmptyDrawing().and().aPolygon();
        when().anArcIsAdded();
        then().thereAreMoreNodes();
    }
}
