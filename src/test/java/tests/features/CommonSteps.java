package tests.features;

import io.cucumber.java.en.Given;
import tests.RootClass;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonSteps extends RootClass {

    @Given("^empty step$")
    public void emptyStep() {
        System.out.println("Empty Step!");
        assertTrue(true, "Empty Step!");
    }
}
