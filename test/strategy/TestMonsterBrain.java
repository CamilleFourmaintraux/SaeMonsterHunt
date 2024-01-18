package strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import fr.univlille.info.J2.main.management.cells.Coordinate;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.info.J2.main.strategy.monster.astar.*;

public class TestMonsterBrain {
	@Test
    public void testFindPath() {
        boolean[][] maze = {
            { true, true, true },
            { true, false, true },
            { true, true, true }
        };
        ICoordinate start = new Coordinate(0, 0);
        ICoordinate end = new Coordinate(2, 2);

        List<ICoordinate> path = AStar.findPath(maze, start, end);

        assertNotNull(path);
        assertFalse(path.isEmpty());
        assertEquals(end, path.get(path.size() - 1));
    }

    @Test
    public void testHeuristic() {
        ICoordinate a = new Coordinate(0, 0);
        ICoordinate b = new Coordinate(3, 4);

        int heuristicValue = AStar.heuristic(a, b);

        assertEquals(5, heuristicValue);
    }

    @Test
    public void testReconstructPath() {
        Node endNode = new Node(new Coordinate(2, 2), 5, 0, new Node(new Coordinate(1, 1), 3, 2, new Node(new Coordinate(0, 0), 0, 0, null)));

        List<ICoordinate> reconstructedPath = AStar.reconstructPath(endNode);

        assertNotNull(reconstructedPath);
        assertFalse(reconstructedPath.isEmpty());
        assertEquals(new Coordinate(0, 0), reconstructedPath.get(0));
        assertEquals(new Coordinate(1, 1), reconstructedPath.get(1));
        assertEquals(new Coordinate(2, 2), reconstructedPath.get(2));
    }

    @Test
    public void testGetNeighbors() {
        boolean[][] maze = {
            { true, true, true },
            { true, false, true },
            { true, true, true }
        };
        ICoordinate center = new Coordinate(1, 1);

        List<ICoordinate> neighbors = AStar.getNeighbors(center, maze);

        assertNotNull(neighbors);
        assertFalse(neighbors.isEmpty());
        assertTrue(neighbors.contains(new Coordinate(0, 1)));
        assertTrue(neighbors.contains(new Coordinate(1, 0)));
        assertTrue(neighbors.contains(new Coordinate(1, 2)));
        assertTrue(neighbors.contains(new Coordinate(2, 1)));
    }
	
	 @Test
	    public void testConstructorAndGetters() {
	        // Création d'une instance de la classe Node
	        ICoordinate coordinate = new Coordinate(1, 2); // Utilisez une classe de coordonnées fictive pour les tests
	        Node node = new Node(coordinate, 10, 5, null);

	        // Vérification des valeurs initiales avec les getters
	        assertEquals(coordinate, node.getCoordinate());
	        assertEquals(10, node.getgCost());
	        assertEquals(5, node.gethCost());
	        assertNull(node.getParent());
	    }

	    @Test
	    public void testCompareTo() {
	        // Création de deux instances de la classe Node avec des coûts différents
	        ICoordinate coordinate1 = new Coordinate(1, 2);
	        Node node1 = new Node(coordinate1, 10, 5, null);

	        ICoordinate coordinate2 = new Coordinate(3, 4);
	        Node node2 = new Node(coordinate2, 8, 7, null);
	        
	        assertNotEquals(node1,node2);

	        assertEquals(0, node1.compareTo(node1)); // node1 a le même coût total (fCost) que lui-même
	    }
}
