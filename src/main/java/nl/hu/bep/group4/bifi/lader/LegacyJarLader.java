package nl.hu.bep.group4.bifi.lader;

import java.io.IOException;

import nl.hu.bep.group4.bifi.model.Adres;

public interface LegacyJarLader {
	Adres laadAdres(String sleutel) throws IOException;
}
