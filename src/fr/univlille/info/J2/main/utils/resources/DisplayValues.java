package fr.univlille.info.J2.main.utils.resources;

/**
 * La classe DisplayValues représente les valeurs de configuration pour l'affichage, notamment
 * la taille de la fenêtre, la position, le niveau de zoom, et les décalages horizontaux et verticaux.
 *
 * Elle offre des méthodes pour accéder et modifier ces valeurs.
 *
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 * 
 * 
 */
public class DisplayValues {
	/**
	 * Constante pour la taille minimale des fenêtres
	 */
	public static final int WINDOWS_MIN_SIZE = 500;
	/**
	 * Hauteur de la fenêtre (500 oar défaut).
	 */
	private double windowHeight;
	/**
	 * Largeur de la fenêtre (500 oar défaut).
	 */
	private double windowWidth;

	/**
	 * Position x de la fenetre
	 */
	private double windowX;
	/**
	 * Position y de la fenetre
	 */
	private double windowY;
	
	/**
	 * niveau de zoom sur le labyrinthe
	 */
	private double zoom;
	
	/**
	 * décalement horizontal du labyrinthe
	 */
	private double gapX;
	/**
	 * décalement vertical du labyrinthe
	 */
	private double gapY;

	 /**
     * Constructeur de la classe DisplayValues.
     *
     * @param window_height Hauteur de la fenêtre.
     * @param window_width  Largeur de la fenêtre.
     * @param window_x      Position x de la fenêtre.
     * @param window_y      Position y de la fenêtre.
     * @param zoom          Niveau de zoom sur le labyrinthe.
     * @param gapX          Décalage horizontal du labyrinthe.
     * @param gapY          Décalage vertical du labyrinthe.
     */
	public DisplayValues(double window_height, double window_width, double window_x ,double window_y ,double zoom, double gapX, double gapY) {
		this.windowHeight = window_height;
		this.windowWidth = window_width;
		this.windowX = window_x;
		this.windowY = window_y;
		this.zoom=zoom;
		this.gapX=gapX;
		this.gapY=gapY;
	}

	 /**
     * Obtient la hauteur de la fenêtre.
     *
     * @return La hauteur de la fenêtre.
     */
    public double getWindowHeight() {
        return windowHeight;
    }

    /**
     * Modifie la hauteur de la fenêtre.
     *
     * @param window_height La nouvelle hauteur de la fenêtre.
     */
    public void setWindowHeight(double window_height) {
        this.windowHeight = window_height;
    }

    /**
     * Obtient la largeur de la fenêtre.
     *
     * @return La largeur de la fenêtre.
     */
    public double getWindowWidth() {
        return windowWidth;
    }

    /**
     * Modifie la largeur de la fenêtre.
     *
     * @param window_width La nouvelle largeur de la fenêtre.
     */
    public void setWindowWidth(double window_width) {
        this.windowWidth = window_width;
    }

    /**
     * Obtient le niveau de zoom sur le labyrinthe.
     *
     * @return Le niveau de zoom.
     */
    public double getZoom() {
        return zoom;
    }

    /**
     * Modifie le niveau de zoom sur le labyrinthe.
     *
     * @param zoom Le nouveau niveau de zoom.
     */
    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

    /**
     * Obtient le décalage horizontal du labyrinthe.
     *
     * @return Le décalage horizontal.
     */
    public double getGapX() {
        return gapX;
    }

    /**
     * Modifie le décalage horizontal du labyrinthe.
     *
     * @param gapX Le nouveau décalage horizontal.
     */
    public void setGapX(double gapX) {
        this.gapX = gapX;
    }

    /**
     * Obtient le décalage vertical du labyrinthe.
     *
     * @return Le décalage vertical.
     */
    public double getGapY() {
        return gapY;
    }

    /**
     * Modifie le décalage vertical du labyrinthe.
     *
     * @param gapY Le nouveau décalage vertical.
     */
    public void setGapY(double gapY) {
        this.gapY = gapY;
    }

    /**
     * Obtient la position x de la fenêtre.
     *
     * @return La position x de la fenêtre.
     */
    public double getWindowX() {
        return windowX;
    }

    /**
     * Modifie la position x de la fenêtre.
     *
     * @param window_x La nouvelle position x de la fenêtre.
     */
    public void setWindowX(double window_x) {
        this.windowX = window_x;
    }

    /**
     * Obtient la position y de la fenêtre.
     *
     * @return La position y de la fenêtre.
     */
    public double getWindowY() {
        return windowY;
    }

    /**
     * Modifie la position y de la fenêtre.
     *
     * @param window_y La nouvelle position y de la fenêtre.
     */
    public void setWindowY(double window_y) {
        this.windowY = window_y;
    }
}
