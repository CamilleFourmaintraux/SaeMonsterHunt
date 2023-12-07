/**
 * Le package main.utils contient des classes et des méthodes utilitaires pour diverses opérations
 * au sein de l'application. Il comprend des fonctionnalités pour la gestion de l'observateur (Observer)
 * et du sujet (Subject) pour le modèle de conception Observer, ainsi que des méthodes utilitaires
 * pour la génération de nombres aléatoires et la création d'arrière-plans graphiques.
 */
package fr.univlille.info.J2.main.utils.patrons;

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
		this.subs=new ArrayList<>();
	}
	/**
	 * Attache un observateur au sujet.
	 *
	 * @param obs L'observateur a attacher.
	 */
	public void attach(Observer obs) {
		this.subs.add(obs);
	}

	/**
     * Détache un observateur du sujet.
	 *
	 * @param obs L'observateur a détacher.
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
     * des données associées � cette mise à jour.
     *
     * @param data Les données à transmettre aux observateurs.
	 */
	protected void notifyObservers(Object data) {
		for(Observer obs:this.subs) {
			obs.update(this,data);
		}
	}
}
