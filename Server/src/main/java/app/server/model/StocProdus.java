package app.server.model;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "StocuriProduse")

public class StocProdus extends Entity {
    private Produs produs;
    private int stoc;

    public StocProdus() {}

    public StocProdus(Produs produs, int stoc) {
        this.produs = produs;
        this.stoc = stoc;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_produs")
    public Produs getProdus() {
        return produs;
    }

    public void setProdus(Produs produs) {
        this.produs = produs;
    }

    public int getStoc() {
        return stoc;
    }

    public void setStoc(int stoc) {
        this.stoc = stoc;
    }

    @Override
    public String toString() {
        return "StocProdus{" +
                "produs=" + produs +
                ", stoc=" + stoc +
                '}';
    }
}