package sample.buildings;

import sample.Composition;
import sample.RusName;

@RusName(r_name = "Поле")
public class field extends Composition {

    public String getgr_type() {
        return gr_type.toString();
    }

    @RusName(r_name = "УстГазон")
    public void setgr_type(String gr_t) {
        if(gr_t.equals("Искусственный")) {
            this.gr_type = grass.synthetic;
        }
        else {
            this.gr_type = grass.natural;
        }

    }

    public Boolean getfence() {
        return fence;
    }

    @RusName(r_name = "УстОграждение")
    public void setfence(Boolean fence) {
        this.fence = fence;
    }

    private enum grass {
        @RusName(r_name = "Искусственный")
        synthetic,
        @RusName(r_name = "Натуральный")
        natural
    }

    @RusName(r_name = "Газон")
    private grass gr_type;

    @RusName(r_name = "Ограждение")
    private Boolean fence;
}
