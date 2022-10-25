package app.server.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@javax.persistence.Entity
@Table(name = "Comenzi")

public class Comanda extends Entity {
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private StatusComanda status;
    private LocalDateTime data;
    private Client client;
    private Operator operator;
    private float valoare;

    public Comanda() {}

    public Comanda(LocalDateTime data, Client client, Operator operator,float valoare) {
        this.status = StatusComanda.IN_ASTEPTARE;
        this.data = data;
        this.client = client;
        this.operator = operator;
        this.valoare = valoare;
    }

    public Comanda(LocalDateTime data, Client client, Operator operator,float valoare,StatusComanda status) {
        this.status = status;
        this.data = data;
        this.client = client;
        this.operator = operator;
        this.valoare = valoare;
    }

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    public StatusComanda getStatus() {
        return status;
    }

    public void setStatus(StatusComanda status) {
        this.status = status;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client")
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_operator")
    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public float getValoare() {
        return valoare;
    }

    public void setValoare(float valoare) {
        this.valoare = valoare;
    }

    @Override
    public String toString() {
        return "Comanda{" +
                "status=" + status +
                ", data=" + data +
                ", client=" + client +
                ", operator=" + operator +
                '}';
    }
}