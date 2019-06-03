package nl.hu.bep.group4.bifi.lader.implementations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import nl.hu.bep.group4.bifi.exceptions.GarbageDataException;
import nl.hu.bep.group4.bifi.lader.MysqlLader;
import nl.hu.bep.group4.bifi.lader.PersoonLader;
import nl.hu.bep.group4.bifi.model.Persoon;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersoonLaderImplTest {

    private Persoon a;
    private Persoon b;
    private List<Persoon> persoonList;

    @Mock
    private MysqlLader mysqlLader;

    @TestSubject
    private PersoonLader persoonLader = new PersoonLaderImpl(mysqlLader);


    //This is gonna be fucked in Wercker
    @BeforeEach
    public void setup() {
        EasyMockSupport.injectMocks(this);
        a = new Persoon(1, "Nick", "Stuivenber", "", "", "", Persoon.Geslacht.MAN);
        b = new Persoon(2, "Bo", "Stuivenber", "", "", "", Persoon.Geslacht.VROUW);

        persoonList = new ArrayList<>();
        persoonList.add(a);
        persoonList.add(b);
    }

    @Test
    public void testPersoonLaderGetPersoon() throws ClassNotFoundException, SQLException, GarbageDataException {
        //List<Adres> adresList = new ArrayList<>();
        //adresList.add(new Adres("A", "0125", "6211AA", "Mestreeg", "456"));
        //adresList.add(new Adres("B", "0125", "6211AA", "Mestreeg", "456"));

        //List<Persoon> persoonList = new ArrayList<>();
        //persoonList.add(a);

        //Klant klant = new Klant(1);
        //klant.setBedrijfsnaam("Overtoom");

        //expect(mysqlLader.getAdressen(1)).andReturn(adresList);
        //expect(mysqlLader.getKlant(1)).andReturn(klant);
        expect(mysqlLader.getPersoon(1)).andReturn(a);
        replay(mysqlLader);


        assertEquals(a, persoonLader.getPersoon(1));
    }

    @Test
    public void testPersoonLaderGetPersonen() throws GarbageDataException, SQLException, ClassNotFoundException {
        expect(mysqlLader.getPersonen(1)).andReturn(persoonList);
        assertEquals(persoonList.size(), 2);
        assertEquals(persoonList.get(0), a);
        assertEquals(persoonList.get(1), b);
    }


}
