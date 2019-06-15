package nl.hu.bep.group4.bifi.lader;

import nl.hu.bep.group4.bifi.exceptions.GarbageDataException;
import nl.hu.bep.group4.bifi.lader.implementations.MysqlLaderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import static java.lang.System.getProperty;

class getKlantMysqlLaderImplTest {

    MysqlLaderImpl sql = null;

    @BeforeEach
    public void beforeEach(){
         sql = new MysqlLaderImpl();
    }

    @Test
    public void getAdresTest() throws ClassNotFoundException, SQLException{
        MysqlLaderImpl sql = new MysqlLaderImpl();

        sql.getAdressen(1);
    }

    @Test
    public void getPersonenTest() throws ClassNotFoundException, SQLException{
        MysqlLaderImpl sql = new MysqlLaderImpl();

        sql.getPersonen(1);
    }

    @Test
    public void getKlantTest() throws ClassNotFoundException, SQLException{
        MysqlLaderImpl sql = new MysqlLaderImpl();

        sql.getKlant(1);
    }

    @Test
    public void getPersoonTest() throws ClassNotFoundException, SQLException, GarbageDataException {
        MysqlLaderImpl sql = new MysqlLaderImpl();

        sql.getPersoon(1);
    }

    @Test
    public void testEnviroment(){
       System.out.print( System.getenv("BEP_MySQLUs ername"));
    }
}
