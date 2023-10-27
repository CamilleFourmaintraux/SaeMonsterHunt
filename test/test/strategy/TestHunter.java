package test.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import main.maze.cells.Coordinate;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import main.strategy.hunter.Hunter;

public class TestHunter {

	//pas de test pour les m�thodes play & update car pas encore impl�ment�.
	
	@Test
	public void test_constructor_hunter() {
		// Instanciation d'un chasseur.
		ICoordinate coord_hunter = new Coordinate(2, 3);
		Hunter hunter = new Hunter(0, 0, coord_hunter);

		// Test de la bonne instanciation du chasseur.
		assertEquals(coord_hunter, hunter.getCoord());
		assertEquals(2, hunter.getRow());
		assertEquals(3, hunter.getCol());
		//assertTrue(hunter.isMonsterTurn()); //TODO
	}
	
	/*@Test
	public void test_initialize_traces() {
		int nbrRows = 3; int nbrCols = 4;

		Hunter hunter = new Hunter();
		hunter.initialize(nbrRows, nbrCols);

		int[][] traces = hunter.getTraces();

		// Test si le tableau de traces a �t� initialis� correctement.
		assertEquals(nbrRows, traces.length);
		assertEquals(nbrCols, traces[0].length);

		// Test si toutes les valeurs du tableau de traces sont � z�ro.
		for (int h = 0; h < nbrRows; h++) {
			for (int l = 0; l < nbrCols; l++) {
				assertEquals(0, traces[h][l]);
			}
		}
	}*///TODO
	
	/*@Test
    public void test_shoot_action() {
        // Instanciation d'un Hunter.
        Hunter hunter = new Hunter(0,0,new Coordinate(2,3));
        // Nouvelles coordonn�es de tir.
        ICoordinate newCoord = new Coordinate(2,4);
		// Appel � la m�thode shoot avec les nouvelles coordonn�es.
        hunter.shoot(newCoord);
        // V�rifiez si les propri�t�s de l'objet Hunter ont �t� mises � jour correctement.
        assertEquals(newCoord, hunter.getCoord());
        assertTrue(hunter.isMonsterTurn());
    }*/ //TODO
	
	/*
	@Test
    public void test_actualizeTraces_method() {
        // Instanciation d'un Hunter.
        Hunter hunter = new Hunter(5,5,null);
        // Cr�ation des coordonn�es
        ICoordinate coordinate = new Coordinate(1,2);
        int newTraceValue = 42;
        // Appel � la m�thode actualizeTraces avec les coordonn�es et la valeur de trace.
        hunter.actualizeTraces(coordinate, newTraceValue);
        // Test si la valeur de trace a �t� correctement mise � jour dans le tableau de traces.
        int[][] traces = hunter.getTraces();
        assertEquals(newTraceValue, traces[coordinate.getRow()][coordinate.getCol()]);
    }
    */ //TODO
}
