package ro.transport.demo.domain;

public class Color {
    private Long id;
    private String name;
    private String hex;
    private String rgb;

    public Color() {

    }

    public Color(String name, String hex, String rgb) {
        this.name = name;
        this.hex = hex;
        this.rgb = rgb;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public String getRgb() {
        return rgb;
    }

    public void setRgb(String rgb) {
        this.rgb = rgb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Color color = (Color) o;

        if (!id.equals(color.id)) return false;
        if (!name.equals(color.name)) return false;
        if (!hex.equals(color.hex)) return false;
        return rgb.equals(color.rgb);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + hex.hashCode();
        result = 31 * result + rgb.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Color{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hex='" + hex + '\'' +
                ", rgb='" + rgb + '\'' +
                '}';
    }
}
