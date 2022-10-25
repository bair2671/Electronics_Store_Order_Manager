package app.server.model.validator;

import app.server.model.Angajat;

import java.util.regex.Pattern;

public class AngajatValidator implements Validator<Angajat>{
    @Override
    public void validate(Angajat entity) throws ValidationException {
        if(entity.getNume()==null)
            throw new ValidationException("Numele nu poate fi vid!");
        if(entity.getPrenume()==null)
            throw new ValidationException("Prenumele nu poate fi vid!");
        if (!Pattern.matches("[a-zA-Z]+",entity.getPrenume()))
            throw new ValidationException("Prenumele trebuie sa contina doar litere!");
        if (!Pattern.matches("[a-zA-Z]+",entity.getNume()))
            throw new ValidationException("Numele trebuie sa contina doar litere!");
        if(entity.getPassword()==null)
            throw new ValidationException("Parola nu poate fi vida!");
        if(entity.getPassword().length()<3)
            throw new ValidationException("Parola nu poate avea mai putin de 3 caractere!");
        if(entity.getUsername()==null)
            throw new ValidationException("Username-ul nu poate fi vid!");
        if(entity.getUsername().length()<3)
            throw new ValidationException("Username-ul nu poate avea mai putin de 3 caractere!");
    }
}
