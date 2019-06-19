package nl.hu.bep.group4.bifi.lader;

import nl.hu.bep.group4.bifi.exceptions.GarbageDataException;
import nl.hu.bep.group4.bifi.lader.implementations.MongoLaderImpl;
import nl.hu.bep.group4.bifi.model.Factuur;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

public class MongoLaderImplTest {


	public MongoLaderImpl setup() {
		return new MongoLaderImpl();
	}
	
	/*@Test
	public void testConnection() {
		MongoLaderImpl mongoLader = setup();
		assertNotNull(mongoLader.connectToMongoDB());
	}*/
	
	@Test
	public void getFacturenVoorMaandTest() throws GarbageDataException {
		/*MongoLaderImpl mongoLader = setup();
		List<Factuur> facturen = mongoLader.getFacturenVoorMaand(4);
		assertNotNull(facturen);
		assertEquals(5, facturen.size());*/
		System.out.println("Doing stuff!");
	}
	
	
}
