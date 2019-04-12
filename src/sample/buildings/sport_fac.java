package sample.buildings;

import sample.RusName;

public abstract class sport_fac {

    @RusName(r_name = "Расположение")
    private String location;

    @RusName(r_name = "Вместимость")
    private int capacity;

    @RusName(r_name = "Кол-во этажей")
    private int fl_amount;

    private enum light {
        @RusName(r_name = "Искусственное")
        synthetic,
        @RusName(r_name = "Естественное")
        natural
    }

    @RusName(r_name = "Освещение")
    private light light_type;

    @RusName(r_name = "Название")
    private String name;

    public String getname() {
        return name;
    }

    @RusName(r_name = "УстНазвание")
    public void setname(String name) {
        this.name = name;
    }

    public String getlocation() {
        return location;
    }

    @RusName(r_name = "УстРасположение")
    public void setlocation(String location) {
        this.location = location;
    }

    public int getcapacity() {
        return capacity;
    }

    @RusName(r_name = "УстВместимость")
    public void setcapacity(String capacity) {
        this.capacity = Integer.parseInt(capacity);
    }

        public int getfl_amount() {
        return fl_amount;
    }

    @RusName(r_name = "УстКол-во этажей")
    public void setfl_amount(String fl_amount) {
        this.fl_amount = Integer.parseInt(fl_amount);
    }

    public String getlight_type() {
        return this.light_type.toString();
    }

    @RusName(r_name = "УстОсвещение")
    public void setlight_type(String l_t) {
        if (l_t.equals("Искусственное")) {
            this.light_type = light.synthetic;
        }
        else {
            this.light_type =  light.natural;
        }
    }


}
