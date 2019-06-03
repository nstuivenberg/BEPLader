package nl.hu.bep.group4.bifi.lader.implementations;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import nl.hu.bep.group4.bifi.exceptions.GarbageDataException;
import nl.hu.bep.group4.bifi.lader.AdresLader;
import nl.hu.bep.group4.bifi.lader.KlantLader;
import nl.hu.bep.group4.bifi.lader.MysqlLader;
import nl.hu.bep.group4.bifi.lader.PersoonLader;
import nl.hu.bep.group4.bifi.model.Adres;
import nl.hu.bep.group4.bifi.model.Klant;
import nl.hu.bep.group4.bifi.model.Persoon;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class KlantLaderImplTest {

    private Klant klant;
    private Klant filledKlant;
    private Adres factuurAdres;
    private List<Persoon> persoonList;
    private List<Adres> adresList;

    @Mock
    private MysqlLader mysqlLader;

    @Mock
    private AdresLader adresLader;

    @Mock
    private PersoonLader persoonLader;

    @TestSubject
    private KlantLader klantLader = new KlantLaderImpl(adresLader, persoonLader, mysqlLader);


    public void setup() {
        EasyMockSupport.injectMocks(this);
        klant = new Klant(1);
        klant.setBedrijfsnaam("Overtoom");
        klant.setRechtsvorm("BV");

        factuurAdres = new Adres("STRAAT", "888", "1111AA", "OEZBEKIDROP",
                "WATDOETBICHIER");

        adresList = new ArrayList<>();

        Persoon a = new Persoon(1, "Nick", "Swers", "", "", "",
                Persoon.Geslacht.MAN);
        Persoon b = new Persoon(2, "Bo", "Breik", "", "", "",
                Persoon.Geslacht.VROUW);

        persoonList = new ArrayList<>();
        persoonList.add(a);
        persoonList.add(b);

        filledKlant = new Klant(klant.getId());
        filledKlant.setBedrijfsnaam(klant.getBedrijfsnaam());
        filledKlant.setRechtsvorm(klant.getRechtsvorm());
        filledKlant.setAdres(adresList);
        filledKlant.setFactuurAdres(factuurAdres);
        filledKlant.setContactPersonen(persoonList);

    }

    @Test
    public void testGetKlant() throws SQLException, ClassNotFoundException, IOException, GarbageDataException {
        setup();
        expect(mysqlLader.getKlant(1)).andReturn(klant);
        expect(adresLader.getAdressen(1)).andReturn(adresList);
        expect(adresLader.getFactuurAdres(1)).andReturn(factuurAdres);
        expect(persoonLader.getPersonen(1)).andReturn(persoonList);

        replay(mysqlLader);
        replay(adresLader);
        replay(persoonLader);

        Klant aKlant = klantLader.getKlant(1);

        assertEquals(filledKlant.getBedrijfsnaam(), aKlant.getBedrijfsnaam());
        assertEquals(filledKlant.getFactuurAdres(), aKlant.getFactuurAdres());
        assertEquals(filledKlant.getContactPersonen().size(), aKlant.getContactPersonen().size());

    }




}
