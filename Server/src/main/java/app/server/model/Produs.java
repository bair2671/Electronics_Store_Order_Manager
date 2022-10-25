package app.server.model;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "Produse")

public class Produs extends Entity {
    private String nume;
    private Producator producator;
    private TipProdus tip;
    private float pret;

    public Produs() {}

    public Produs(String nume, Producator producator, TipProdus tip, float pret) {
        this.nume = nume;
        this.producator = producator;
        this.tip = tip;
        this.pret = pret;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producator")
    public Producator getProducator() {
        return producator;
    }

    public void setProducator(Producator producator) {
        this.producator = producator;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tip")
    public TipProdus getTip() {
        return tip;
    }

    public void setTip(TipProdus tip) {
        this.tip = tip;
    }

    public float getPret() {
        return pret;
    }

    public void setPret(float pret) {
        this.pret = pret;
    }

    @Override
    public String toString() {
        return "Produs{" +
                "nume='" + nume + '\'' +
                ", producator=" + producator +
                ", tip=" + tip +
                ", pret=" + pret +
                '}';
    }
}