package app.server.repository.memory;

import app.server.model.AdministratorComenzi;
import app.server.repository.interfaces.AdministratorComenziRepo;

import java.util.Arrays;
import java.util.List;


public class AdministratorComenziMemRepo implements AdministratorComenziRepo {
    List<AdministratorComenzi> admini;

    public AdministratorComenziMemRepo(){
        AdministratorComenzi administratorComenzi = new AdministratorComenzi();
        administratorComenzi.setNume("Munteanu");
        administratorComenzi.setPrenume("Alexandru");
        administratorComenzi.setUsername("alex1");
        administratorComenzi.setPassword("alex1");
        administratorComenzi.setId(1);

        admini = Arrays.asList(administratorComenzi);
    }

    @Override
    public AdministratorComenzi findOneByUsername(String username) {
        for(AdministratorComenzi admin: admini){
            if(admin.getUsername().equals(username))
                return admin;
        }
        return null;
    }

    @Override
    public AdministratorComenzi findOne(int id) {
        return null;
    }

    @Override
    public Iterable<AdministratorComenzi> findAll() {
        return null;
    }

    @Override
    public AdministratorComenzi save(AdministratorComenzi entity) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(AdministratorComenzi entity) {

    }
}
