package Borman.cbbbluechips.models.requests;

public class UpdatePointSpreadRequest {

    private String teamId;
    private String newPointSpread;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getNewPointSpread() {
        return newPointSpread;
    }

    public void setNewPointSpread(String newPointSpread) {
        this.newPointSpread = newPointSpread;
    }
}