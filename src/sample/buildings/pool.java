package sample.buildings;

import sample.RusName;

@RusName(r_name = "Бассейн")
public class pool extends sport_fac {


    public Boolean getdividers() {
        return dividers;
    }

    @RusName(r_name = "УстРазделители дорожек")
    public void setdividers(Boolean dividers) {
        this.dividers = dividers;
    }

    @RusName(r_name = "Разделители дорожек")
    private Boolean dividers;

    private enum desinfection {
        @RusName(r_name = "Хлором")
        chlor,
        @RusName(r_name = "Озоном")
        ozon,
        @RusName(r_name = "Натрием")
        natrium,
        @RusName(r_name = "Ультразвуком")
        ultravoice
    }

    public String getdes() {
        return des.toString();
    }

    @RusName(r_name = "УстСпособ обеззараживания воды")
    public void setdes(String des) {
        switch (des) {
            case "Хлором": this.des = desinfection.chlor;
                break;
            case "Озоном": this.des = desinfection.ozon;
                break;
            case "Натрием": this.des = desinfection.natrium;
                break;
            case "Ультразвуком": this.des = desinfection.ultravoice;
                break;
            default: System.out.println("Problems!!!");

        }
    }

    @RusName(r_name = "Способ обеззараживания воды")
    private desinfection des;
}
