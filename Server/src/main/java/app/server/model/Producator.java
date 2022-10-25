package app.server.model;

import javax.persistence.Table;
import java.util.Objects;

@javax.persistence.Entity
@Table(name = "Producatori")

public class Producator extends Entity {
    private String nume;

    public Producator() {}

    public Producator(String nume) {
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        /*return "Producator{" +
                "nume='" + nume + '\'' +
                '}';*/
        return nume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producator that = (Producator) o;
        return Objects.equals(nume, that.nume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume);
    }
}