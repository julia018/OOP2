package sample.buildings;

import sample.Composition;
import sample.RusName;

@RusName(r_name = "Сектор трека")
public class track extends Composition {

    @RusName(r_name = "УстКоличество дорожек")
    public void setamount(String am) {
        switch (am) {
            case "менее 2-ух":
                this.amount = track_am.less2;
                break;
            case "от 2-ух до 5-ти":
                this.amount = track_am.two_five;
                break;
            case "более 5-ти":
                this.amount = track_am.greater_five;
                break;
            default:
                System.out.println("Problems!!!");
        }
    }

    public String getamount() {
        return amount.toString();
    }

    @RusName(r_name = "УстПротяженность дорожки")
    public void settr_length(String len) {
        this.tr_length = Float.parseFloat(len);
    }

    private enum track_am {
        @RusName(r_name = "менее 2-ух")
        less2,
        @RusName(r_name = "от 2-ух до 5-ти")
        two_five,
        @RusName(r_name = "более 5-ти")
        greater_five,
    }

    @RusName(r_name = "Количество дорожек")
    private track_am amount;

    public float gettr_length() {
        return tr_length;
    }

    @RusName(r_name = "Протяженность дорожки")
    private float tr_length;

}
