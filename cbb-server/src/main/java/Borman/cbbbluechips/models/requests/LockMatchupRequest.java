package Borman.cbbbluechips.models.requests;

public class LockMatchupRequest {

    private String team1Id;
    private String team2Id;
    private boolean lock;

    public String getTeam1Id() {
        return team1Id;
    }

    public void setTeam1Id(String team1Id) {
        this.team1Id = team1Id;
    }

    public String getTeam2Id() {
        return team2Id;
    }

    public void setTeam2Id(String team2Id) {
        this.team2Id = team2Id;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    @Override
    public String toString() {
        return "LockMatchupRequest{" +
                "team1Id='" + team1Id + '\'' +
                ", team2Id='" + team2Id + '\'' +
                ", lock=" + lock +
                '}';
    }
}