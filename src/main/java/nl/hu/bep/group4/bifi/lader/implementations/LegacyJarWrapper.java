package nl.hu.bep.group4.bifi.lader.implementations;

import java.util.Map;

import ADDRLOOKUPER.AddressLookerUPAlreadyCloosed;
import ADDRLOOKUPER.AddressLookerUPAlreadyLookinUP;
import ADDRLOOKUPER.AddressLookerUPAlreadyReadyToLookUP;
import ADDRLOOKUPER.AddressLookerUpInWrongStateException;
import ADDRLOOKUPER.LOOKUP_AdDDR;

public class LegacyJarWrapper {
	public Map<String, String> laadAdres(String sleutel) throws AddressLookerUpInWrongStateException, AddressLookerUPAlreadyLookinUP, AddressLookerUPAlreadyReadyToLookUP, AddressLookerUPAlreadyCloosed {
		LOOKUP_AdDDR.scanStart();
		Map<String, String> result = LOOKUP_AdDDR.scanForward(sleutel);
		LOOKUP_AdDDR.scanStop();
		return result;
	}
}
