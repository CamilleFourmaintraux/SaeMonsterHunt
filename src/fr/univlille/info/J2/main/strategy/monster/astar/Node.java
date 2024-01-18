package fr.univlille.info.J2.main.strategy.monster.astar;

import fr.univlille.iutinfo.cam.player.perception.ICoordinate;



/**
 * Représente un nœud dans l'algorithme A* utilisé par la stratégie du monstre.
 * Chaque nœud contient une coordonnée, les coûts g et h, ainsi qu'un nœud parent.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 * 
 */
public class Node implements Comparable<Node> {
	/**
     * Coordonnée du nœud dans la grille.
     */
    private ICoordinate coordinate;
    /**
     * Coût du chemin du début à ce nœud / Estimation du coût du chemin de ce nœud à la destination (heuristique).
     */
    private int gCost;
	private int hCost;
    /**
     * Nœud parent dans le chemin optimal.
     */
    private Node parent;

    /**
     * Constructeur de la classe Node.
     *
     * @param coordinate Coordonnée du nœud dans la grille.
     * @param gCost      Coût du chemin du début à ce nœud.
     * @param hCost      Estimation du coût du chemin de ce nœud à la destination (heuristique).
     * @param parent     Nœud parent dans le chemin optimal.
     */
    public Node(ICoordinate coordinate, int gCost, int hCost, Node parent) {
        this.setCoordinate(coordinate);
        this.setgCost(gCost);
        this.sethCost(hCost);
        this.setParent(parent);
    }

    /**
     * Compare ce nœud à un autre nœud en fonction du coût total (fCost).
     *
     * @param other L'autre nœud à comparer.
     * @return Une valeur négative, zéro ou une valeur positive si ce nœud a un coût total
     *         inférieur, égal ou supérieur à celui de l'autre nœud.
     */
    @Override
    public int compareTo(Node other) {
        int fCost = getgCost() + gethCost();
        int otherFCost = other.getgCost() + other.gethCost();
        return Integer.compare(fCost, otherFCost);
    }

	public ICoordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(ICoordinate coordinate) {
		this.coordinate = coordinate;
	}

	public int getgCost() {
		return gCost;
	}

	public void setgCost(int gCost) {
		this.gCost = gCost;
	}

	public int gethCost() {
		return hCost;
	}

	public void sethCost(int hCost) {
		this.hCost = hCost;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}
}
