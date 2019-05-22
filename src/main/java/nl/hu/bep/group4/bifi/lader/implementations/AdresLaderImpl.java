package nl.hu.bep.group4.bifi.lader.implementations;

import nl.hu.bep.group4.bifi.lader.LegacyJarLader;
import nl.hu.bep.group4.bifi.lader.MysqlLader;

public class AdresLaderImpl {
    private LegacyJarLader legacyJarLader;
    private MysqlLader mysqlLader;

    public AdresLaderImpl(LegacyJarLader legacyJarLader, MysqlLader mysqlLader) {
        this.legacyJarLader = legacyJarLader;
        this.mysqlLader = mysqlLader;
    }
}
