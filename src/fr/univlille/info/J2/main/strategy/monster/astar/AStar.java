package fr.univlille.info.J2.main.strategy.monster.astar;

import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import fr.univlille.info.J2.main.management.cells.Coordinate;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

/**
 * Cette classe implémente l'algorithme A* (A-star) pour trouver un chemin dans un labyrinthe.
 * L'algorithme est utilisé pour trouver le chemin optimal entre deux points, en tenant compte
 * des obstacles présents dans le labyrinthe.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 * 
 */
public class AStar {
	/**
     * La limite maximale du compteur utilisé pour éviter une exécution infinie de l'algorithme.
     */
	public static final int MAX_CPT = 10000;
	
	/**
     * Recherche un chemin optimal entre les coordonnées de départ et d'arrivée dans le labyrinthe donné.
     *
     * @param maze  	Labyrinthe représenté par un tableau de boolean indiquant les emplacements des murs.
     * @param start 	Coordonnées de départ.
     * @param end   	Coordonnées d'arrivée.
     * @return Liste d'objets ICoordinate représentant le chemin optimal trouvé.
     */
	public static List<ICoordinate> findPath(boolean[][] maze, ICoordinate start, ICoordinate end) {
		int cpt = 0;
		PriorityQueue<Node> openSet = new PriorityQueue<>();
        Set<ICoordinate> closedSet = new HashSet<>();
        Map<ICoordinate, Node> nodeMap = new HashMap<>();

        Node startNode = new Node(start, 0, heuristic(start, end), null);
        openSet.add(startNode);
        nodeMap.put(start, startNode);
        while (!openSet.isEmpty()&&cpt<MAX_CPT) {
        	cpt++;
            Node currentNode = openSet.poll();
            if (currentNode.coordinate.equals(end)) {
                return reconstructPath(currentNode);
            }

            closedSet.add(currentNode.coordinate);
            for (ICoordinate neighbor : getNeighbors(currentNode.coordinate, maze)) {
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                int tentativeGCost = currentNode.gCost + 1;
                Node neighborNode = nodeMap.getOrDefault(neighbor,
                        new Node(neighbor, Integer.MAX_VALUE, heuristic(neighbor, end), null));

                if (!closedSet.contains(neighbor) && tentativeGCost < neighborNode.gCost) {
                    neighborNode.parent = currentNode;
                    neighborNode.gCost = tentativeGCost;

                    if (!openSet.contains(neighborNode)) {
                        openSet.add(neighborNode);
                    }
                }
                nodeMap.put(neighbor, neighborNode);
            }
        }
        return new ArrayList<>();
    }

	/**
     * Calcule l'heuristique entre deux coordonnées.
     * L'heuristique utilisée est la distance de Manhattan.
     *
     * @param a Première coordonnée.
     * @param b Deuxième coordonnée.
     * @return Valeur de l'heuristique entre les deux coordonnées.
     */
    static int heuristic(ICoordinate a, ICoordinate b) {
        // Heuristic function (Manhattan distance for simplicity)
        return (int)Math.round(Math.sqrt((Math.pow(Math.abs(a.getRow() - b.getRow()), 2)+Math.pow(Math.abs(a.getCol()- b.getCol()),2))));
    }

    /**
     * Reconstruit le chemin à partir du nœud final en remontant à travers les parents.
     *
     * @param node Nœud final.
     * @return Liste d'objets ICoordinate représentant le chemin reconstruit.
     */
    static List<ICoordinate> reconstructPath(Node node) {
        List<ICoordinate> path = new ArrayList<>();
        while (node != null) {
            path.add(node.coordinate);
            node = node.parent;
        }
        Collections.reverse(path);
        return path;
    }

    /**
     * Obtient les coordonnées voisines valides d'une coordonnée donnée dans le labyrinthe.
     *
     * @param coordinate Coordonnée pour laquelle obtenir les voisins.
     * @param maze       Labyrinthe représenté par un tableau de boolean indiquant les emplacements des murs.
     * @return Liste d'objets ICoordinate représentant les voisins valides.
     */
    static List<ICoordinate> getNeighbors(ICoordinate coordinate, boolean[][] maze) {
        List<ICoordinate> neighbors = new ArrayList<>();
        int[] dx = { -1, 0, 1, 0, -1, 1, 1, -1 }; // 8 directions including diagonals
        int[] dy = { 0, 1, 0, -1, 1, 1, -1, -1 };

        for (int i = 0; i < dx.length; i++) {
            int newRow = coordinate.getRow() + dx[i];
            int newCol = coordinate.getCol() + dy[i];

            if (newRow >= 0 && newRow < maze.length && newCol >= 0 && newCol < maze[0].length && maze[newRow][newCol]) {
                neighbors.add(new Coordinate(newRow, newCol));
            }
        }
        return neighbors;
    }
    
    /*//Example
    public static void main(String[] args) {
        
        boolean[][] maze = Maze.DEFAULT_MAP; // Replace this with your randomly generated maze
        
        
        int rows = maze.length;
        int cols = maze[0].length;
        
        System.out.println(Maze.toString(maze));
        
        ICoordinate start = new Coordinate(0, 0); // Replace with your monster's starting position
        ICoordinate end = new Coordinate(rows - 1, cols - 1); // Replace with your exit position

        System.out.println("(" + start.getRow() + ", " + start.getCol() + ")");
        System.out.println("(" + end.getRow() + ", " +end.getCol() + ")");

        List<ICoordinate> path = findPath(maze, start, end);
        if (!path.isEmpty()) {
            System.out.println("Path found:");
            for (ICoordinate coord : path) {
                System.out.println("(" + coord.getRow() + ", " + coord.getCol() + ")");
            }
        } else {
            System.out.println("No path found!");
        }
        
        System.out.println("END");
    }*/
    
    
}
