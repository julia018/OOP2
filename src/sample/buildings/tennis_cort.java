package sample.buildings;

import sample.RusName;

@RusName(r_name = "Теннисный корт")
public class tennis_cort extends sport_fac {

    public Boolean getchecker() {
        return checker;
    }

    @RusName(r_name = "УстДатчик касания сетки")
    public void setchecker(Boolean dividers) {
        this.checker = dividers;
    }

    @RusName(r_name = "Датчик касания сетки")
    private Boolean checker;

    private enum covering {
        @RusName(r_name = "Хард")
        hard,
        @RusName(r_name = "Грунт")
        grount,
        @RusName(r_name = "Трава")
        grass,
        @RusName(r_name = "Паркет")
        parket
    }

    public String getcover() {
        return cover.toString();
    }

    @RusName(r_name = "УстПокрытие")
    public void setcover(String cov) {
        switch (cov) {
            case "Хард": this.cover = covering.hard;
                break;
            case "Грунт": this.cover = covering.grount;
                break;
            case "Трава": this.cover = covering.grass;
                break;
            case "Паркет": this.cover = covering.parket;
                break;
            default: System.out.println("Problems!!!");

        }
    }

    @RusName(r_name = "Покрытие")
    private covering cover;
}
