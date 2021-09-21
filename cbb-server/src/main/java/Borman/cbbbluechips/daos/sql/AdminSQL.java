package Borman.cbbbluechips.daos.sql;

public class AdminSQL {

    public static final String deleteAllTeams = "DELETE FROM teams WHERE Team_ID > 0";

    public static final String insertFromSportsDataAPI = "INSERT INTO teams (Name, Nickname, Is_Out, Seed, Current_Market_Price, Wins, Losses, " +
            "Logo_URL, Short_Display_Name, Sports_Data_Team_ID) VALUES (:school, :name, false, 0, 5000, :wins, :losses, :teamLogoUrl, :shortDisplayName, :teamId);";

    public static final String updateTeamInfo = "UPDATE teams SET Wins = :wins, Losses = :losses, Sports_Data_Team_ID = :teamId WHERE Name = :school";

    public static final String resetSeeds = "UPDATE teams SET seed = '0' WHERE Team_ID > 0";

    public static final String updateSeeds = "UPDATE teams SET seed = :newSeed WHERE Name = :teamName";

    public static final String updateLockedAndEliminated = "UPDATE teams SET Is_Out = :out, Is_Locked = :locked WHERE Name = :teamName";

    public static final String UPDATE_TEAMS_SET_CURRENT_MARKET_PRICE = "UPDATE teams SET Current_Market_Price = :price WHERE Name = :teamName";

    public static final String ARCHIVE_PRICE_UPDATE_RENEW = "UPDATE price_history SET Price = :price WHERE Team_ID = :teamId AND Round_ID = :roundId";

    public static final String archivePriceUpdateCreate = "INSERT INTO price_history (Team_ID, Round_ID, Price) VALUES (:teamId, :roundId, :price)";

    public static final String checkForRoundPriceExists = "SELECT * FROM price_history Where Team_ID = :teamId AND Round_ID = :roundId";

}
