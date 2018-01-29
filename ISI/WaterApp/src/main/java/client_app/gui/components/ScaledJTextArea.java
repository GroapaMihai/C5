package client_app.gui.components;

import javax.swing.JTextArea;

public class ScaledJTextArea extends JTextArea {
	private static final long serialVersionUID = 1L;

	public ScaledJTextArea(String text, int rows, int columns) {
        super(rows, columns);
        setText(text);
        setOpaque(false);
        setEditable(false);
        setBorder(null);
        setLineWrap(true);
        setWrapStyleWord(true);
        setFocusable(false);
    }
}
