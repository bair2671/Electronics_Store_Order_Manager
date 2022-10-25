package app.server.model;

import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "Operatori")

public class Operator extends Angajat {
    public Operator() {}

    @Override
    public String toString() {
        return nume+" "+prenume+" / "+username;
    }

}