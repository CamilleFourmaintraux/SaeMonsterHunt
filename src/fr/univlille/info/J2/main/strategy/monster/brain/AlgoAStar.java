package fr.univlille.info.J2.main.strategy.monster.brain;

import java.util.List;
import java.util.PriorityQueue;
import java.util.ArrayList;

public class AlgoAStar {
	
	private static final int MOVING_COST = 1;
	
	private int heuristic(Node actuel, Node destination) {
        return Math.abs(actuel.getCol() - destination.getCol()) + Math.abs(actuel.getRow() - destination.getRow());
    }
	
	public List<Node> think (Node[][] grid, Node start, Node goal) {
        PriorityQueue<Node> openSet = new PriorityQueue<>((a, b) -> a.getCostTotal() - b.getCostTotal()); //Lambda avec comparaison entre deux nodes a et b
        start.setCostStartToThis(0);
        start.setCostTotal(start.getCostStartToThis() + heuristic(start, goal));
        openSet.add(start);
        
        List<Node> path;
        Node current;
        while (!openSet.isEmpty()) {
            current = openSet.poll();
            if (current == goal) {
                path = new ArrayList<>();
                while (current != null) {
                    path.add(0, current);
                    current = current.getPrevious();
                }
                return path;
            }

            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) continue; // Ignorer le nœud actuel

                    int newX = current.getCol() + i;
                    int newY = current.getRow() + j;

                    if (newX >= 0 && newX < grid.length && newY >= 0 && newY < grid[0].length) {
                        Node neighbor = grid[newX][newY];
                        if (!neighbor.isTraversable()) continue; // Si le voisin n'est pas accessible

                        /*int newCost = current.g + ((i != 0 && j != 0) ? MOVE_DIAGONAL_COST : MOVE_STRAIGHT_COST);
                         * On peut imaginer des coût ifférents en fonction de si on se déplace diagonalement ou en ligne.*/
                        int newCost = current.getCostStartToThis() + MOVING_COST;
                        if (newCost < neighbor.getCostStartToThis()) {
                            neighbor.setPrevious(current);
                            neighbor.setCostStartToThis(newCost);
                            neighbor.setCostTotal(neighbor.getCostStartToThis() + heuristic(neighbor, goal));

                            if (!openSet.contains(neighbor)) {
                                openSet.add(neighbor);
                            }
                        }
                    }
                }
            }
        }

        return null; // Aucun chemin trouvé
    }
}
