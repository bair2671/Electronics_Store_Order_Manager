package app.server.model;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "ProduseComenzi")

public class ProdusComanda extends Entity {
    private Produs produs;
    private Comanda comanda;
    private int cantitate;

    public ProdusComanda() {}

    public ProdusComanda(Produs produs,Comanda comanda,int cantitate) {
        this.cantitate = cantitate;
        this.produs = produs;
        this.comanda = comanda;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produs")
    public Produs getProdus() {
        return produs;
    }

    public void setProdus(Produs produs) {
        this.produs = produs;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_comanda")
    public Comanda getComanda() {
        return comanda;
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }

    @Override
    public String toString() {
        return "ProdusComanda{" +
                "produs=" + produs +
                ", comanda=" + comanda +
                ", cantitate=" + cantitate +
                '}';
    }
}