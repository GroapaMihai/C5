package client_app.gui.components;

import java.awt.*;

public enum Fonts {
    TAHOMA_BOLD_16(new Font("Tahoma", Font.BOLD, 16)),
    TAHOMA_ITALIC_16(new Font("Tahoma", Font.ITALIC, 16)),
    TAHOMA_PLAIN_16(new Font("Tahoma", Font.PLAIN, 16)),
    TAHOMA_BOLD_18(new Font("Tahoma", Font.BOLD, 18)),
    SERIF_BOLD_26(new Font("Serif", Font.BOLD, 26));

    private Font font;

    Fonts(Font font) {
        this.font = font;
    }

    public Font getFont() {
        return font;
    }
}
