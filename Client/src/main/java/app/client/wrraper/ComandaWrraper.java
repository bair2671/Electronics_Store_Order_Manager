package app.client.wrraper;

import app.client.utils.MyDateTimeFormatter;
import app.server.model.Comanda;

public class ComandaWrraper extends Comanda {
    private String dateString;

    public ComandaWrraper(Comanda comanda){
        super(comanda.getData(), comanda.getClient(), comanda.getOperator(), comanda.getValoare());
        this.setId(comanda.getId());
        this.dateString = MyDateTimeFormatter.getInstance().formatDateClassic(comanda.getData());
    }

    public String getDateString() {
        return dateString;
    }

    public Comanda getComanda(){
        Comanda comanda = new Comanda(getData(),getClient(),getOperator(),getValoare());
        comanda.setId(getId());
        return  comanda;
    }
}
