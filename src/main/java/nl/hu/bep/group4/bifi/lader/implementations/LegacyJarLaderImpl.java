package nl.hu.bep.group4.bifi.lader.implementations;

import java.io.IOException;
import java.util.Map;

import ADDRLOOKUPER.AddressLookerUPAlreadyCloosed;
import ADDRLOOKUPER.AddressLookerUPAlreadyLookinUP;
import ADDRLOOKUPER.AddressLookerUPAlreadyReadyToLookUP;
import ADDRLOOKUPER.AddressLookerUpInWrongStateException;
import nl.hu.bep.group4.bifi.lader.LegacyJarLader;
import nl.hu.bep.group4.bifi.model.Adres;

public class LegacyJarLaderImpl implements LegacyJarLader {
	private LegacyJarWrapper wrapper;
	
	public LegacyJarLaderImpl(LegacyJarWrapper wrapper) {
		this.wrapper = wrapper;
	}

	@Override
	public Adres laadAdres(String sleutel) throws IOException {
		Map<String, String> adresDataMap = null;
		try {
			adresDataMap = wrapper.laadAdres(sleutel);
		} catch (AddressLookerUPAlreadyLookinUP | AddressLookerUPAlreadyReadyToLookUP | AddressLookerUpInWrongStateException | AddressLookerUPAlreadyCloosed e) {
			throw new IOException("adres lookup mislukt", e);
		}
		if(adresDataMap == null) {
			return null;
		}
		return new Adres(adresDataMap.get("STRAAT"), adresDataMap.get("HUISNUMMER"), adresDataMap.get("POSTCODE"), adresDataMap.get("plaats"), null);
	}
}
