package nl.hu.bep.group4.bifi.lader;


import nl.hu.bep.group4.bifi.lader.implementations.MysqlLaderImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MysqlLaderImplTest {

    @Test
    public void testConnection(){
        MysqlLaderImpl sql = new MysqlLaderImpl();

        sql.connectDatabase();
    }
}
