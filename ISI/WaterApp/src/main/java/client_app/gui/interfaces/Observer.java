package client_app.gui.interfaces;

import client_app.gui.views.ViewType;

public interface Observer {
    void update(ViewType viewType, Object arg);
}
