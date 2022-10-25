package app.server.model.validator;

import app.server.model.Produs;

public class ProdusValidator implements Validator<Produs>{
    @Override
    public void validate(Produs entity) throws ValidationException {
        String err = "";

        if(entity.getNume()==null)
            err+="Numele nu poate fi vid!\n";
        else if(entity.getNume().equals(""))
            err+="Numele nu poate fi vid!\n";

        if(entity.getProducator()==null)
            err+="Producatorul nu poate fi vid!\n";
        if(entity.getTip()==null)
            err+="Tipul nu poate fi vid!\n";
        if(entity.getPret()<0)
            err+="Pretul nu poate fi negativ!\n";

        if(!err.equals(""))
            throw new ValidationException(err);
    }
}
