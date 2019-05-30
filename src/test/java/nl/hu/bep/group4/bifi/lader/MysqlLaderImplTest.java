package nl.hu.bep.group4.bifi.lader;

import nl.hu.bep.group4.bifi.lader.implementations.MysqlLaderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

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
    public void getPersoonTest() throws ClassNotFoundException, SQLException{
        MysqlLaderImpl sql = new MysqlLaderImpl();

        sql.getPersonen(1);
    }

    @Test
    public void getKlantTest() throws ClassNotFoundException, SQLException{
        MysqlLaderImpl sql = new MysqlLaderImpl();

        sql.getKlant(1);
    }
}
