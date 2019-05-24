package nl.hu.bep.group4.bifi.lader;


import nl.hu.bep.group4.bifi.lader.implementations.MysqlLaderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MysqlLaderImplTest {

    MysqlLaderImpl sql = null;

    @BeforeEach
    public void beforeEach(){
         sql = new MysqlLaderImpl();
    }
    @Test
    public void testConnection(){

        sql.connectDatabase();
    }

    @Test
    public void testGetAllKlanten(){
        MysqlLaderImpl sql = new MysqlLaderImpl();

        try {
            sql.getAllKlanten();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
