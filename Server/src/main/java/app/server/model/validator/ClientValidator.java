package app.server.model.validator;

import app.server.model.Client;

import java.util.regex.Pattern;

public class ClientValidator implements Validator<Client>{
    @Override
    public void validate(Client entity) throws ValidationException {
        String err = "";

        if(entity.getNume()==null )
            err += "Numele nu poate fi vid!\n";
        else if(entity.getNume().equals(""))
            err += "Numele nu poate fi vid!\n";
        else if (!Pattern.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$",entity.getNume()))
            err += "Numele este invalid!\n";

        if(entity.getPrenume()==null )
            err += "Prenumele nu poate fi vid!\n";
        else if(entity.getPrenume().equals(""))
            err += "Prenumele nu poate fi vid!\n";
        else if (!Pattern.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$",entity.getPrenume()))
            err += "Prenumele este invalid\n";

        if(entity.getTelefon()==null)
            err += "Numarul de telefon nu poate fi vid!\n";
        else if(entity.getTelefon().equals(""))
            err += "Numarul de telefon nu poate fi vid!\n";
        else if(!Pattern.matches("[0-9]+",entity.getTelefon()))
            err += "Numarul de telefon trebuie sa fie format doar din cifre!\n";
        else if(entity.getTelefon().length()!=10)
            err += "Numarul de telefon trebuie sa aiba 10 cifre!\n";

        if(entity.getEmail()==null)
            err += "Emailul nu poate fi vid!\n";
        else if(entity.getEmail().equals(""))
            err += "Emailul nu poate fi vid!\n";
        else if(!Pattern.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$",entity.getEmail()))
            err += "Adresa de email este invalida!\n";

        if(!err.equals(""))
            throw new ValidationException(err);
    }
}
