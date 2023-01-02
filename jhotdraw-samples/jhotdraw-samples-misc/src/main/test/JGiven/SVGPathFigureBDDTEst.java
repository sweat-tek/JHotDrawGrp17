package JGiven;

import JGiven.Stages.Given;
import JGiven.Stages.Then;
import JGiven.Stages.When;
import com.tngtech.jgiven.junit.ScenarioTest;
import org.junit.Test;

public class SVGPathFigureBDDTEst extends ScenarioTest<Given, When, Then> {

    @Test
    public void drawLine(){
        given().anEmptyDrawing();
        when().aLineIsDrawn();
        then().drawingContainsLine();
    }

    @Test
    public void changeActions() {
        given().anEmptyDrawing().and().aLine();
        when().aLineActionIsChanged();
        then().lineActionRuleHasChanged();
    }
}
