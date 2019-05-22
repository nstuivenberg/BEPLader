package nl.hu.bep.group4.bifi.lader.implementations;

import nl.hu.bep.group4.bifi.lader.MysqlLader;
import nl.hu.bep.group4.bifi.lader.PersoonLader;

public class PersoonLaderImpl implements PersoonLader {
    private MysqlLader mysqlLader;

    public PersoonLaderImpl(MysqlLader mysqlLader) {
        this.mysqlLader = mysqlLader;
    }
}
