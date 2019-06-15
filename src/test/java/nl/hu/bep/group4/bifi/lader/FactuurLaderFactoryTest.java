package nl.hu.bep.group4.bifi.lader;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import nl.hu.bep.group4.bifi.lader.factories.FactuurLaderFactory;

public class FactuurLaderFactoryTest {
	@Test
	public void testFactory() {
		assertNotNull(FactuurLaderFactory.createFactuurLader());
	}
}
