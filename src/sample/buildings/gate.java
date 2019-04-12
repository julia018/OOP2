package sample.buildings;

import sample.Composition;
import sample.RusName;

@RusName(r_name = "Ворота")
public class gate extends Composition {

    @RusName(r_name = "Высота")
    private float height;

    @RusName(r_name = "Ширина")
    private float width;

    @RusName(r_name = "Подвижность")
    private Boolean mobility;

    public float getheight() {
        return height;
    }

    @RusName(r_name = "УстВысота")
    public void setheight(String height) {
        this.height = Float.parseFloat(height);
    }

    public float getwidth() {
        return width;
    }

    @RusName(r_name = "УстШирина")
    public void setwidth(String width) {
        this.width = Float.parseFloat(width);
    }

    public Boolean getmobility() {
        return mobility;
    }

    @RusName(r_name = "УстПодвижность")
    public void setmobility(Boolean mobility) {
        this.mobility = mobility;
    }
}
