package app.server.server;

import app.server.model.*;
import app.server.repository.hibernate.*;
import app.server.services.AppException;
import junit.framework.TestCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.*;

public class ServiceTest extends TestCase {
    @Mock
    OperatorHbRepo operatorHbRepo;
    @Mock
    AdministratorComenziHbRepo administratorComenziHbRepo;
    @Mock
    AdministratorStocuriHbRepo administratorStocuriHbRepo;
    @Mock
    ClientHbRepo clientHbRepo;
    @Mock
    ComandaHbRepo comandaHbRepo;
    @Mock
    ProducatorHbRepo producatorHbRepo;
    @Mock
    ProdusComandaHbRepo produsComandaHbRepo;
    @Mock
    ProdusHbRepo produsHbRepo;
    @Mock
    StocProdusHbRepo stocProdusHbRepo;
    @Mock
    TipProdusHbRepo tipProdusHbRepo;

    @Mock
    Operator operator;
    @Mock
    AdministratorComenzi administratorComenzi;
    @Mock
    AdministratorStocuri administratorStocuri;

    @InjectMocks
    private Service service;

    Client client;
    Comanda comanda;
    Produs produs;
    Producator producator;
    StocProdus stocProdus;
    TipProdus tipProdus;
    ProdusComanda produsComanda;

    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();

        MockitoAnnotations.initMocks(this);
        service = new Service(operatorHbRepo,administratorComenziHbRepo,administratorStocuriHbRepo,clientHbRepo,
                comandaHbRepo,producatorHbRepo,produsComandaHbRepo,produsHbRepo,stocProdusHbRepo,tipProdusHbRepo);

        Mockito.when(operator.getId()).thenReturn(1);
        Mockito.when(operator.getNume()).thenReturn("Popescu");
        Mockito.when(operator.getPrenume()).thenReturn("Ion");
        Mockito.when(operator.getUsername()).thenReturn("ion1");
        Mockito.when(operator.getPassword()).thenReturn("ion1");

        Mockito.when(administratorComenzi.getId()).thenReturn(1);
        Mockito.when(administratorComenzi.getNume()).thenReturn("Munteanu");
        Mockito.when(administratorComenzi.getPrenume()).thenReturn("Alexandru");
        Mockito.when(administratorComenzi.getUsername()).thenReturn("alex1");
        Mockito.when(administratorComenzi.getPassword()).thenReturn("alex1");

        Mockito.when(administratorStocuri.getId()).thenReturn(1);
        Mockito.when(administratorStocuri.getNume()).thenReturn("Popa");
        Mockito.when(administratorStocuri.getPrenume()).thenReturn("Adrian");
        Mockito.when(administratorStocuri.getUsername()).thenReturn("adi1");
        Mockito.when(administratorStocuri.getPassword()).thenReturn("adi1");

        client = new Client("Ionescu","Paul","0707070707","paul@yahoo.com");
        client.setId(1);

        producator = new Producator("Samsung");
        producator.setId(1);

        tipProdus = new TipProdus("Smartphone");
        tipProdus.setId(1);

        produs = new Produs("Galaxy A51", producator , tipProdus , 1200);
        produs.setId(1);

        stocProdus = new StocProdus(produs,20);
        stocProdus.setId(1);

        comanda = new Comanda(LocalDateTime.of(2022,5,1,12,0),client,operator,produs.getPret());
        comanda.setId(1);

        produsComanda = new ProdusComanda(produs,comanda,1);
        produsComanda.setId(1);

        //OperatorRepo
        Mockito.when(operatorHbRepo.findOneByUsername("ion1") ).thenReturn(operator);
        Mockito.when(operatorHbRepo.findOneByUsername(not(eq("ion1"))) ).thenReturn(null);

        //AdministratorComenzi
        Mockito.when(administratorComenziHbRepo.findOneByUsername("alex1") ).thenReturn(administratorComenzi);
        Mockito.when(administratorComenziHbRepo.findOneByUsername(not(eq("alex1")))  ).thenReturn(null);

        //AdministratorStocuri
        Mockito.when(administratorStocuriHbRepo.findOneByUsername("adi1") ).thenReturn(administratorStocuri);
        Mockito.when(administratorStocuriHbRepo.findOneByUsername(not(eq("adi1")))).thenReturn(null);

        //ClientRepo
        Mockito.when(clientHbRepo.save(any(Client.class))).then(i-> i.getArguments()[0]);

        //ComandaRepo
        Mockito.when(comandaHbRepo.findAll()).thenReturn(Arrays.asList(comanda));

        //ProducatorRepo
        Mockito.when(producatorHbRepo.findAll()).thenReturn(Arrays.asList(producator));

        //ProdusComandaRepo
        Mockito.when(produsComandaHbRepo.findAll()).thenReturn(Arrays.asList(produsComanda));

        //ProdusRepo
        Mockito.when(produsHbRepo.findAll()).thenReturn(Arrays.asList(produs));

        //StocProdusRepo
        Mockito.when(stocProdusHbRepo.findOneByProdus(produs.getId())).thenReturn(stocProdus);

        //TipProdusRepo
        Mockito.when(tipProdusHbRepo.findAll()).thenReturn(Arrays.asList(tipProdus));

        new Service(operatorHbRepo,administratorComenziHbRepo,administratorStocuriHbRepo,clientHbRepo,
                comandaHbRepo,producatorHbRepo,produsComandaHbRepo,produsHbRepo,stocProdusHbRepo,tipProdusHbRepo);

    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    public void testLogin() {
        Angajat angajat;

        try {
            angajat = service.login("inexistent","ion1","ion1");
            assertNull(angajat);
        } catch (Exception e) {
            fail();
        }

        //Operator
        try {
            angajat = service.login("operator","ion1","ion1");
            assertEquals(angajat,(Angajat)operator);
        } catch (Exception e) {
            fail();
        }

        assertThrows(AppException.class, () -> {
            service.login("operator","ion1","gresit");
        }, "Parola incorecta!");

        assertThrows(AppException.class, () -> {
            service.login("operator","inexistent","gresit");
        }, "Nu exista operator cu acest username!");


        //AdminstratorComenzi
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

    public void testFindOneOperator() {
    }

    public void testFindOneOperatorByUsername() {
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
    }

    public void testFindOneClientByEmail() {
    }

    public void testFindOneStocByProdus() {
    }

    public void testFindOneComandaByOperatorAndClient() {
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
}