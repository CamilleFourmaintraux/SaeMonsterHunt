package strategy;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;

import fr.univlille.info.J2.main.strategy.hunter.IAeasyHunter;

public class TestHunterStrategy {
	IAeasyHunter ia = new IAeasyHunter();
	@Test
    void test_Strategy_Easy_Hunter() {
		ia.initialize(10, 10);
		assertNotNull(ia.play());
		ia.update(null);
	}
}
