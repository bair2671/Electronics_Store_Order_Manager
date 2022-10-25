package app.server.model.validator;

import app.server.model.Client;
import junit.framework.TestCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClientValidatorTest extends TestCase {
    ClientValidator validator;

    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        validator = new ClientValidator();

    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    public void testValidate() {

        try {
            validator.validate(new Client("Andrei","Roxana","0774433221","roxana@yahoo.com"));
        } catch (Exception e) {
            fail();
        }

        assertThrows(ValidationException.class, () -> {
            validator.validate(new Client("","","",""));
        }, "Numele nu poate fi vid!\nPrenumele nu poate fi vid!\nNumarul de telefon nu poate fi vid!\n"+
                "Emailul nu poate fi vid!\n");

        assertThrows(ValidationException.class, () -> {
            validator.validate(new Client(null,"",null,""));
        }, "Numele nu poate fi vid!\nPrenumele nu poate fi vid!\nNumarul de telefon nu poate fi vid!\n"+
                "Emailul nu poate fi vid!\n");

        assertThrows(ValidationException.class, () -> {
            validator.validate(new Client("","Vasile","","roxana@yahoo.com"));
        }, "Numele nu poate fi vid!\nNumarul de telefon nu poate fi vid!\n");

        assertThrows(ValidationException.class, () -> {
            validator.validate(new Client("Andrei%1234","#Roxana*","07744332rt","roxana"));
        }, "Numele este invalid!\nPrenumele este invalid!\n" +
                "Numarul de telefon trebuie sa fie format doar din cifre!\nAdresa de email este invalida!\n");

        assertThrows(ValidationException.class, () -> {
            validator.validate(new Client("Andrei","#Roxana","0774433221","roxana"));
        }, "Prenumele este invalid!\nAdresa de email este invalida!\n");

        assertThrows(ValidationException.class, () -> {
            validator.validate(new Client("Andrei","Roxana","0774433233323","roxana@yahoo.com"));
        }, "Numarul de telefon trebuie sa fie format doar din cifre!\n");

        assertThrows(ValidationException.class, () -> {
            validator.validate(new Client("Andrei%1234","#Roxana*","",null));
        }, "Numele este invalid!\nPrenumele este invalid!\nNumarul de telefon nu poate fi vid!\n" +
                "Emailul nu poate fi vid!\n");
    }
}