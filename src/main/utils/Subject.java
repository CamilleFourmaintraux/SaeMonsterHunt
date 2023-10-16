package main.utils;

import java.util.ArrayList;

public abstract class Subject {
	protected ArrayList<Observer> subs;
	protected Subject() {
		this.subs=new ArrayList<Observer>();
	}
	public void attach(Observer obs) {
		this.subs.add(obs);
	}
	public void detach(Observer obs) {
		this.subs.remove(obs);
	}
	protected void notifyObservers() {
		for(Observer obs:this.subs) {
			obs.update(this);
		}
	}
	protected void notifyObservers(Object data) {
		for(Observer obs:this.subs) {
			obs.update(this,data);
		}
	}
}
