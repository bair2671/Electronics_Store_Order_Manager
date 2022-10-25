package app.server.repository.memory;

import app.server.model.AdministratorStocuri;
import app.server.repository.interfaces.AdministratorStocuriRepo;

import java.util.Arrays;
import java.util.List;


public class AdministratorStocuriMemRepo implements AdministratorStocuriRepo {
    List<AdministratorStocuri> admini;

    public AdministratorStocuriMemRepo(){
        AdministratorStocuri administratorStocuri = new AdministratorStocuri();
        administratorStocuri.setNume("Popa");
        administratorStocuri.setPrenume("Adrian");
        administratorStocuri.setUsername("adi1");
        administratorStocuri.setPassword("adi1");
        administratorStocuri.setId(1);

        admini = Arrays.asList(administratorStocuri);
    }

    @Override
    public AdministratorStocuri findOneByUsername(String username) {
        for(AdministratorStocuri admin: admini){
            if(admin.getUsername().equals(username))
                return admin;
        }
        return null;
    }

    @Override
    public AdministratorStocuri findOne(int id) {
        return null;
    }

    @Override
    public Iterable<AdministratorStocuri> findAll() {
        return null;
    }

    @Override
    public AdministratorStocuri save(AdministratorStocuri entity) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(AdministratorStocuri entity) {

    }
}
