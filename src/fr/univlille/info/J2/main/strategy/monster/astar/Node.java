package fr.univlille.info.J2.main.strategy.monster.astar;

import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

public class Node implements Comparable<Node> {
    ICoordinate coordinate;
    int gCost, hCost;
    Node parent;

    Node(ICoordinate coordinate, int gCost, int hCost, Node parent) {
        this.coordinate = coordinate;
        this.gCost = gCost;
        this.hCost = hCost;
        this.parent = parent;
    }

    @Override
    public int compareTo(Node other) {
        int fCost = gCost + hCost;
        int otherFCost = other.gCost + other.hCost;
        return Integer.compare(fCost, otherFCost);
    }
}
