package main.utils;

import java.util.ArrayList;

public abstract class Subject {
	ArrayList<Observer> subs;
	public void attach(Observer obs) {
		this.subs.add(obs);
	}
	public void detach(Observer obs) {
		this.subs.remove(obs);
	}
	private void notifyObservers() {
		for(Observer obs:this.subs) {
			obs.update(this);
		}
	}
	private void notifyObservers(Object data) {
		for(Observer obs:this.subs) {
			obs.update(this,data);
		}
	}
}
