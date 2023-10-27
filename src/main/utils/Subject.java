package main.utils;

import java.util.ArrayList;
/**
 * Classe abstraite représentant un sujet observé (observable) dans le modèle de conception Observer-Observe.
 * Les classes dérivées de cette classe peuvent avoir des observateurs attachés et notifier ces observateurs
 * en cas de modifications de leur état.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 *
 */
public abstract class Subject {
	/**
	 * Liste des observateurs attachés à ce sujet.
	 */
	protected ArrayList<Observer> subs;
	/**
     * Constructeur par défaut de la classe Subject.
     * Initialise la liste des observateurs à une nouvelle liste vide.	 
     */
	protected Subject() {
		this.subs=new ArrayList<Observer>();
	}
	/**
	 * Attache un observateur au sujet.
	 * 
	 * @param obs L'observateur à attacher.
	 */
	public void attach(Observer obs) {
		this.subs.add(obs);
	}
	
	/**
     * Détache un observateur du sujet.
	 * 
	 * @param obs L'observateur à détacher.
	 */
	public void detach(Observer obs) {
		this.subs.remove(obs);
	}
	
	/**
     * Notifie tous les observateurs attachés sans fournir de données supplémentaires.
     * Chaque observateur est informé de la mise à jour de l'état du sujet.
	 */
	protected void notifyObservers() {
		for(Observer obs:this.subs) {
			obs.update(this);
		}
	}
	
	/**
     * Notifie tous les observateurs attachés en fournissant des données supplémentaires.
     * Chaque observateur est informé de la mise à jour de l'état du sujet et peut recevoir
     * des données associées à cette mise à jour.
     * 
     * @param data Les données à transmettre aux observateurs.
	 */
	protected void notifyObservers(Object data) {
		for(Observer obs:this.subs) {
			obs.update(this,data);
		}
	}
}
