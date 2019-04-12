package sample.buildings;

import sample.RusName;

@RusName(r_name = "Легкоатлетический стадион")
public class athlete_stadium extends sport_fac {

    public String getteam() {
        return team;
    }
    @RusName(r_name = "УстКоманда")
    public void setteam(String team) {
        this.team = team;
    }

    @RusName(r_name = "Команда")
    private String team;


    public track gettrack_sector() {
        return track_sector;
    }

    @RusName(r_name = "УстСектор трека")
    public void settrack_sector(track track_sector) {
        this.track_sector = track_sector;
    }

    @RusName(r_name = "Сектор трека")
    private track track_sector;

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
