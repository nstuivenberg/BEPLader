package nl.hu.bep.group4.bifi.lader.implementations;

import java.util.List;

import nl.hu.bep.group4.bifi.lader.MysqlLader;
import nl.hu.bep.group4.bifi.lader.PersoonLader;
import nl.hu.bep.group4.bifi.model.Persoon;

public class PersoonLaderImpl implements PersoonLader {
    private MysqlLader mysqlLader;

    public PersoonLaderImpl(MysqlLader mysqlLader) {
        this.mysqlLader = mysqlLader;
    }

	@Override
	public List<Persoon> getPersoon(int klantId) {
		// TODO Auto-generated method stub
		return null;
	}
}
