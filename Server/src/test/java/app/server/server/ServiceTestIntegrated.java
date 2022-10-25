package app.server.server;

import app.server.model.*;
import app.server.repository.interfaces.*;
import app.server.repository.memory.*;
import app.server.services.AppException;
import junit.framework.TestCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServiceTestIntegrated extends TestCase {
    private Service service;
    
    private OperatorRepo operatorRepo;
    private AdministratorComenziRepo administratorComenziRepo;
    private AdministratorStocuriRepo administratorStocuriRepo;
    private ClientRepo clientRepo;
    private ComandaRepo comandaRepo;
    private ProducatorRepo producatorRepo;
    private ProdusComandaRepo produsComandaRepo;
    private ProdusRepo produsRepo;
    private StocProdusRepo stocProdusRepo;
    private TipProdusRepo tipProdusRepo;

    private Operator operator;
    private AdministratorComenzi administratorComenzi;
    private AdministratorStocuri administratorStocuri;
    
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        operatorRepo = new OperatorMemRepo();
        administratorComenziRepo = new AdministratorComenziMemRepo();
        administratorStocuriRepo = new AdministratorStocuriMemRepo();
        clientRepo = new ClientMemRepo();
        comandaRepo = new ComandaMemRepo();
        producatorRepo = new ProducatorMemRepo();
        produsComandaRepo = new ProdusComandaMemRepo();
        produsRepo = new ProdusMemRepo();
        stocProdusRepo = new StocProdusMemRepo();
        tipProdusRepo = new TipProdusMemRepo();

        service = new Service(operatorRepo,administratorComenziRepo,administratorStocuriRepo,clientRepo,comandaRepo,
                producatorRepo,produsComandaRepo,produsRepo,stocProdusRepo,tipProdusRepo);

        operator = new Operator();
        operator.setNume("Popescu");
        operator.setPrenume("Ion");
        operator.setUsername("ion1");
        operator.setPassword("ion1");
        operator.setId(1);

        administratorComenzi = new AdministratorComenzi();
        administratorComenzi.setNume("Munteanu");
        administratorComenzi.setPrenume("Alexandru");
        administratorComenzi.setUsername("alex1");
        administratorComenzi.setPassword("alex1");
        administratorComenzi.setId(1);

        administratorStocuri = new AdministratorStocuri();
        administratorStocuri.setNume("Popa");
        administratorStocuri.setPrenume("Adrian");
        administratorStocuri.setUsername("adi1");
        administratorStocuri.setPassword("adi1");
        administratorStocuri.setId(1);

    }
    
    @AfterEach
    public void tearDown() throws Exception {
    }

    public void testLogin() {
        Angajat angajat;

        try {
            angajat = service.login("inexistent","ion1","ion1");
            assertEquals(angajat,null);
        } catch (Exception e) {
            fail();
        }

        //Operator
        try {
            angajat = service.login("operator","ion1","ion1");
            assertEquals(angajat, operator);
        } catch (Exception e) {
            fail();
        }

        assertThrows(AppException.class, () -> {
            service.login("operator","ion1","gresit");
        }, "Parola incorecta!");

        assertThrows(AppException.class, () -> {
            service.login("operator","inexistent","gresit");
        }, "Nu exista operator cu acest username!");


        //AdministratorComenzi
        try {
            angajat = service.login("administrator comenzi","alex1","alex1");
            assertEquals(angajat,(Angajat)administratorComenzi);
        } catch (Exception e) {
            fail();
        }

        assertThrows(AppException.class, () -> {
            service.login("administrator comenzi","alex1","gresit");
        }, "Parola incorecta!");

        assertThrows(AppException.class, () -> {
            service.login("administrator comenzi","inexistent","gresit");
        }, "Nu exista administrator comenzi cu acest username!");


        //AdministratorStocuri
        try {
            angajat = service.login("administrator stocuri","adi1","adi1");
            assertEquals(angajat,(Angajat)administratorStocuri);
        } catch (Exception e) {
            fail();
        }

        assertThrows(AppException.class, () -> {
            service.login("administrator stocuri","adi1","gresit");
        }, "Parola incorecta!");

        assertThrows(AppException.class, () -> {
            service.login("administrator stocuri","inexistent","gresit");
        }, "Nu exista administrator stocuri cu acest username!");
    }

    public void testAddObserver() {
    }

    public void testLogout() {
    }

    public void testFindOneOperator() {
    }

    public void testFindOneOperatorByUsername() {
    }

    public void testChangeObserver() {
    }

    public void testFindAllProduse() {
    }

    public void testFindAllTipuri() {
    }

    public void testFindAllProducatori() {
    }

    public void testFindComenziByStatus() {
    }

    public void testFindProduseComenziByComanda() {
    }

    public void testAddClient() {
        try{
            service.addClient(new Client("Radu","Andreea","0772233444","andreea@gmail.com"));
            assertEquals(((List<Client>)clientRepo.findAll()).size(),1);
            assertEquals(((List<Client>) clientRepo.findAll()).get(0).getEmail(),"andreea@gmail.com");
        } catch (Exception e) {
            fail();
        }

        assertThrows(AppException.class, () -> {
            service.addClient(new Client("","","",""));
        }, "Numele nu poate fi vid!\nPrenumele nu poate fi vid!\nNumarul de telefon nu poate fi vid!\n"+
                "Emailul nu poate fi vid!\n");

        assertThrows(AppException.class, () -> {
            service.addClient(new Client(null,"",null,""));
        }, "Numele nu poate fi vid!\nPrenumele nu poate fi vid!\nNumarul de telefon nu poate fi vid!\n"+
                "Emailul nu poate fi vid!\n");

        assertThrows(AppException.class, () -> {
            service.addClient(new Client("","Vasile","","roxana@yahoo.com"));
        }, "Numele nu poate fi vid!\nNumarul de telefon nu poate fi vid!\n");

        assertThrows(AppException.class, () -> {
            service.addClient(new Client("Andrei%1234","#Roxana*","07744332rt","roxana"));
        }, "Numele este invalid!\nPrenumele este invalid!\n" +
                "Numarul de telefon trebuie sa fie format doar din cifre!\nAdresa de email este invalida!\n");

        assertThrows(AppException.class, () -> {
            service.addClient(new Client("Andrei","#Roxana","0774433221","roxana"));
        }, "Prenumele este invalid!\nAdresa de email este invalida!\n");

        assertThrows(AppException.class, () -> {
            service.addClient(new Client("Andrei","Roxana","0774433233323","roxana@yahoo.com"));
        }, "Numarul de telefon trebuie sa fie format doar din cifre!\n");

        assertThrows(AppException.class, () -> {
            service.addClient(new Client("Andrei%1234","#Roxana*","",null));
        }, "Numele este invalid!\nPrenumele este invalid!\nNumarul de telefon nu poate fi vid!\n" +
                "Emailul nu poate fi vid!\n");
    }

    public void testFindOneClientByEmail() {
    }

    public void testFindOneStocByProdus() {
    }

    public void testAddProdusComanda() {
    }

    public void testAddComanda() {
    }

    public void testFindOneComandaByOperatorAndClient() {
    }

    public void testUpdateComanda() {
    }

    public void testUpdateStoc() {
    }

    public void testUpdateProdus() {
    }

    public void testFindOneProdusByProducatorAndNume() {
    }

    public void testFindOneProducatorByName() {
    }

    public void testFindOneTipByName() {
    }

    public void testAddProducator() {
    }

    public void testAddTip() {
    }

    public void testAddProdus() {
    }

    public void testAddStoc() {
    }

    public void testDeleteStoc() {
    }

    public void testNotifyObservers() {
    }
}