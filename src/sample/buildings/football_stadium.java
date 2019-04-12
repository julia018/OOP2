package sample.buildings;

import sample.RusName;

@RusName(r_name = "Футбольный стадион")
public class football_stadium extends stadium {
    public String getteam() {
        return team;
    }
    @RusName(r_name = "УстКоманда")
    public void setteam(String team) {
        this.team = team;
    }

    @RusName(r_name = "Команда")
    private String team;

    public field getpl_field() {
        return pl_field;
    }

    @RusName(r_name = "УстПоле")
    public void setpl_field(field pl_field) {
        this.pl_field = pl_field;
    }

    @RusName(r_name = "Поле")
    private field pl_field;

    @RusName(r_name = "Ворота")
    private gate pl_gate;

    public gate getpl_gate() {
        return pl_gate;
    }

    @RusName(r_name = "УстВорота")
    public void setpl_gate(gate pl_gate) {
        this.pl_gate = pl_gate;
    }


}
