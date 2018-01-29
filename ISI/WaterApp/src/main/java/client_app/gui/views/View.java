package client_app.gui.views;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import client_app.gui.interfaces.Subject;
import client_app.gui.interfaces.Observer;

public abstract class View extends JPanel implements Subject {
	private static final long serialVersionUID = 1L;

	protected ViewType viewType;
	protected int width;
	protected int height;
    protected List<Observer> observers;

    public View() {
    	observers = new ArrayList<>();
    }

    public void attachObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyAllObservers(ViewType viewType, Object arg) {
        for (Observer observer : observers) {
            observer.update(viewType, arg);
        }
    }

	public String getViewName() {
		return viewType.getViewName();
	}

	public Dimension getViewDimension() {
		return new Dimension(width, height);
	}

	public abstract void populateView(Object arg);
}
