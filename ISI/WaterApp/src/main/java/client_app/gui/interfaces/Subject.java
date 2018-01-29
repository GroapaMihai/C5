package client_app.gui.interfaces;

import client_app.gui.views.ViewType;

public interface Subject {
    void attachObserver(Observer observer);
    void notifyAllObservers(ViewType viewType, Object arg);
}
