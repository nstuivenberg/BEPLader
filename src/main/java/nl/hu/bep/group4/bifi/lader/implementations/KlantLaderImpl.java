package nl.hu.bep.group4.bifi.lader.implementations;

import nl.hu.bep.group4.bifi.lader.*;

public class KlantLaderImpl implements KlantLader {
    private AdresLader adresLader;
    private PersoonLader persoonLader;
    private MysqlLader mysqlLader;

    public KlantLaderImpl(AdresLaderImpl adresLader, PersoonLader persoonLader, MysqlLader mysqlLader) {
        this.adresLader = adresLader;
        this.persoonLader = persoonLader;
        this.mysqlLader = mysqlLader;
    }


}
