package app.client.wrraper;

import app.server.model.Produs;

public class ProdusWrraper extends Produs {
    private int nrExemplare;

    public ProdusWrraper(Produs produs,int nrExemplare) {
        super(produs.getNume(), produs.getProducator(), produs.getTip(), produs.getPret());
        super.setId(produs.getId());
        this.nrExemplare = nrExemplare;
    }

    public int getNrExemplare() {
        return nrExemplare;
    }

    public void setNrExemplare(int nrExemplare) {
        this.nrExemplare = nrExemplare;
    }

    public Produs getProdus() {
        Produs produs = new Produs(getNume(),getProducator(),getTip(),getPret());
        produs.setId(getId());
        return produs;
    }
}