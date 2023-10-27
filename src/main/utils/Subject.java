package main.utils;

import java.util.ArrayList;
/**
 * Classe abstraite repr�sentant un sujet observ� (observable) dans le mod�le de conception Observer-Observe.
 * Les classes d�riv�es de cette classe peuvent avoir des observateurs attach�s et notifier ces observateurs
 * en cas de modifications de leur �tat.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 *
 */
public abstract class Subject {
	/**
	 * Liste des observateurs attach�s � ce sujet.
	 */
	protected ArrayList<Observer> subs;
	/**
     * Constructeur par d�faut de la classe Subject.
     * Initialise la liste des observateurs � une nouvelle liste vide.	 
     */
	protected Subject() {
		this.subs=new ArrayList<Observer>();
	}
	/**
	 * Attache un observateur au sujet.
	 * 
	 * @param obs L'observateur � attacher.
	 */
	public void attach(Observer obs) {
		this.subs.add(obs);
	}
	
	/**
     * D�tache un observateur du sujet.
	 * 
	 * @param obs L'observateur � d�tacher.
	 */
	public void detach(Observer obs) {
		this.subs.remove(obs);
	}
	
	/**
     * Notifie tous les observateurs attach�s sans fournir de donn�es suppl�mentaires.
     * Chaque observateur est inform� de la mise � jour de l'�tat du sujet.
	 */
	protected void notifyObservers() {
		for(Observer obs:this.subs) {
			obs.update(this);
		}
	}
	
	/**
     * Notifie tous les observateurs attach�s en fournissant des donn�es suppl�mentaires.
     * Chaque observateur est inform� de la mise � jour de l'�tat du sujet et peut recevoir
     * des donn�es associ�es � cette mise � jour.
     * 
     * @param data Les donn�es � transmettre aux observateurs.
	 */
	protected void notifyObservers(Object data) {
		for(Observer obs:this.subs) {
			obs.update(this,data);
		}
	}
}
