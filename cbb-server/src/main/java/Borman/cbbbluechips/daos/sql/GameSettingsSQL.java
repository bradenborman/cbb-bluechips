package Borman.cbbbluechips.daos.sql;

public class GameSettingsSQL {

       public static final String getCurrentRound = "Select Current_Round FROM game_info";

       public static final String updateCurrentRound = "UPDATE game_info SET 'Current_Round' = :round) WHERE Year = '2019';";

       public static final String SELECT_LAST_PRICE_CHANGE = "Select Last_Price_Change FROM game_info";

}
