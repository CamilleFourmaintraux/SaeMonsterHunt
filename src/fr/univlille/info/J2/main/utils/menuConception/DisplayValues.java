package fr.univlille.info.J2.main.utils.menuConception;

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

	public DisplayValues(double window_height, double window_width, double window_x ,double window_y ,double zoom, double gapX, double gapY) {
		this.windowHeight = window_height;
		this.windowWidth = window_width;
		this.windowX = window_x;
		this.windowY = window_y;
		this.zoom=zoom;
		this.gapX=gapX;
		this.gapY=gapY;
	}

	public double getWindowHeight() {
		return windowHeight;
	}

	public void setWindowHeight(double window_height) {
		this.windowHeight = window_height;
	}

	public double getWindowWidth() {
		return windowWidth;
	}

	public void setWindowWidth(double window_width) {
		this.windowWidth = window_width;
	}

	public double getZoom() {
		return zoom;
	}

	public void setZoom(double zoom) {
		this.zoom = zoom;
	}

	public double getGapX() {
		return gapX;
	}

	public void setGapX(double gapX) {
		this.gapX = gapX;
	}

	public double getGapY() {
		return gapY;
	}

	public void setGapY(double gapY) {
		this.gapY = gapY;
	}

	public double getWindowX() {
		return windowX;
	}

	public void setWindowX(double window_x) {
		this.windowX = window_x;
	}

	public double getWindowY() {
		return windowY;
	}

	public void setWindowY(double window_y) {
		this.windowY = window_y;
	}
}
