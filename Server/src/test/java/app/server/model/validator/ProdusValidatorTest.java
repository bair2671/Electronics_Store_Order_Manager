package app.server.model.validator;

import app.server.model.Producator;
import app.server.model.Produs;
import app.server.model.TipProdus;
import junit.framework.TestCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProdusValidatorTest extends TestCase {
    ProdusValidator validator;

    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        validator = new ProdusValidator();
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    public void testValidate() {
        TipProdus tip = new TipProdus("Smartphone");
        Producator producator = new Producator("Apple");

        try {
            validator.validate(new Produs("iPhone 13",producator,tip,6200));
        } catch (Exception e) {
            fail();
        }

        assertThrows(ValidationException.class, () -> {
            validator.validate(new Produs("",null,null,-34));
        }, "Numele nu poate fi vid!\nProducatorul nu poate fi vid!\nTipul nu poate fi vid!\n" +
                "Pretul nu poate fi negativ!\n");

        assertThrows(ValidationException.class, () -> {
            validator.validate(new Produs("Galaxy A51",null,tip,-1));
        }, "Producatorul nu poate fi vid!\nPretul nu poate fi negativ!\n");
    }
}