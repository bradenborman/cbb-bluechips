package Borman.cbbbluechips.models;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Matchup implements Comparable {

    private Team team1;
    private Team team2;
    private LocalDateTime startTimeDateTime;
    private String startTime;


    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public LocalDateTime getStartTimeDateTime() {
        return startTimeDateTime;
    }

    public void setStartTimeDateTime(LocalDateTime startTimeDateTime) {
        this.startTimeDateTime = startTimeDateTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Override
    public int compareTo(Object other) {
        if (other instanceof Matchup) {
            //
            Matchup match1 = (Matchup) other;
            LocalDateTime now = LocalDateTime.now();

            if (match1.getStartTimeDateTime().isAfter(now) && this.getStartTimeDateTime().isAfter(now)) {

                long secs1 = ChronoUnit.SECONDS.between(this.getStartTimeDateTime(), now);
                long secs2 = ChronoUnit.SECONDS.between(match1.getStartTimeDateTime(), now);
                if(secs2 < secs1)
                    return -1;

                return 1;

            } else if (!match1.getStartTimeDateTime().isAfter(now) && this.getStartTimeDateTime().isAfter(now)) {
                return -1;
            }

        }
        return 0;
    }


    @Override
    public String toString() {
        return "Matchup{" +
                "team1=" + team1 +
                ", team2=" + team2 +
                ", startTimeDateTime=" + startTimeDateTime +
                ", startTime='" + startTime + '\'' +
                '}';
    }
}