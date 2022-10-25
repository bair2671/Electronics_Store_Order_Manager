package app.server.repository.memory;

import app.server.model.Client;
import app.server.repository.interfaces.ClientRepo;

import java.util.ArrayList;
import java.util.List;


public class ClientMemRepo implements ClientRepo {
    private List<Client> clienti;
    private int maxId;

    public ClientMemRepo() {
        this.clienti = new ArrayList<>();
        this.maxId = 0;
    }

    @Override
    public Client findOneByEmail(String email) {
        return null;
    }

    @Override
    public Client findOne(int id) {
        return null;
    }

    @Override
    public Iterable<Client> findAll() {
        return clienti;
    }

    @Override
    public Client save(Client entity) {
        entity.setId(maxId+1);
        clienti.add(entity);
        return entity;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(Client entity) {

    }
}
