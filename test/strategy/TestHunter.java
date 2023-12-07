package strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Test;

import fr.univlille.info.J2.main.application.cells.CellEvent;
import fr.univlille.info.J2.main.application.cells.Coordinate;
import fr.univlille.info.J2.main.strategy.hunter.Hunter;

public class TestHunter {

	Hunter hunter = new Hunter(6, 6, new Coordinate(0,0),"Player",0);

	@Test
	public void test_constructor_hunter() {
		assertEquals(new Coordinate(0,0), hunter.getCoord());
		assertEquals(0, hunter.getRow());
		assertEquals(0, hunter.getCol());
	}

		//pas de test pour les methodes play & update car pas encore implemente.
		@Test
		public void test_play_hunter() {
			assertNotEquals(hunter.play(),null);
		}

		@Test
		public void test_update_hunter() {
			hunter.update(new CellEvent(null, 0, null));
		}


	@Test
	public void test_initialize_traces_hunter() {
		hunter.initialize(3 ,4);
		assertEquals(3, hunter.traces.length);
		assertEquals(4, hunter.traces[0].length);

		// Test si toutes les valeurs du tableau de traces sont bien a zero.
		for (int h = 0; h < 3; h++) {
			for (int l = 0; l < 4; l++) {
				assertEquals(-2, hunter.traces[h][l]);
			}
		}
	}

	@Test
	public void test_methods_trace_hunter() {
		assertEquals(-2, hunter.getTrace(new Coordinate(1,2)));
		hunter.setTrace(new Coordinate(1,2), 9);
		assertEquals(9, hunter.getTrace(new Coordinate(1,2)));
	}

	@Test
	public void test_setters_hunter() {
		assertEquals(new Coordinate(0,0),hunter.getCoord());
		hunter.setCoord(new Coordinate(9,9));
		assertEquals(new Coordinate(9,9), hunter.getCoord());
		hunter.setCoord(null);
		assertEquals(null, hunter.getCoord());
	}

}
