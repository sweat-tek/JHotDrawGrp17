package JGiven;

import JGiven.Stages.Given;
import JGiven.Stages.Then;
import JGiven.Stages.When;
import com.tngtech.jgiven.junit.ScenarioTest;
import org.junit.Test;

public class SVGEllipseBDDTest extends ScenarioTest<Given, When, Then> {

    @Test
    public void performTranslation(){
        given().aDrawing().and().anSVGEllipse();
        when().translationIsPerformed();
        then().ellipseHasBeenTranslated();
    }
}
