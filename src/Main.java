import com.assignment.tradestore.Trade;
import com.assignment.tradestore.TradeStore;

import java.util.Map;
import java.util.HashMap;
import java.util.Date;

/**
 * Main class to demonstrate user actions for Trades.
 */

public class Main {
    public static void main(String[] args) {
        // validate if we got enough trade parameters, else throw exception.
        if (args.length < 5) throw new RuntimeException("Not Enough full trade parameters..!!!");
        // Populate Sample data as provided in example.
        Map <String, Map> tradeIdMap = populateTradeIdMap (args);
        TradeStore tradeStore = new TradeStore(tradeIdMap);
        Trade trade = getMockedTradeObject (5);
        tradeStore.transmitTrade(trade);
        // Printing full trade objects post transmission...!!!
        for (Map.Entry <String, Map> tradeIdentry : tradeStore.getTradeIdMap().entrySet()) {
            Map <String, Trade> entryVersionMap = tradeIdentry.getValue();
            for (Map.Entry <String, Trade> versionIdEntry : entryVersionMap.entrySet()) {
                System.out.println("Post Transmit Trade Version Values are " +versionIdEntry.getValue().getTradeId()+ " "
                        +versionIdEntry.getValue().getVersion()+ " " +versionIdEntry.getValue().getCpId()+ " "
                        +versionIdEntry.getValue().getBookId()+ " " +versionIdEntry.getValue().getMaturityDate()+ " "
                        +versionIdEntry.getValue().getCreatedDate()+ " " +versionIdEntry.getValue().isExpired());
            }
        }
    }

    /**
     * Method to prepare Trade Mock Object which will send for transmission. This method get's different case as input
     * to demonstrate different use cases.
     * @param caseNumber : Use Case number
     * @return : Trade Object which is mocked for transmission.
     */
    public static Trade getMockedTradeObject (int caseNumber) {
        Trade trade;

        switch (caseNumber) {
            case 1 :
                // Trade has lower version than existing stored trade. This should result exception.
                trade = new Trade("T3", "2", "CP-3", "B2",
                        "20/05/2014", "15/02/2023", true);
                break;
            case 2 :
                // Given Trade Version is same, this should result override existing entry in stored Trade Store.
                // updated maturity date in this example.
                trade = new Trade("T1", "1", "CP-1", "B1", "22/05/2030", "15/02/2023", false);
                break;
            case 3:
                // if Maturity date is lesser than today's date.
                // This should be unsuccessful trade, also updates the expiry flag accordingly.
                trade = new Trade("T1", "1", "CP-1", "B1", "22/05/2019", "15/02/2023", false);
                break;
            case 4:
                // If higher version of existing trade ID has been provided.
                // Transaction should be successful along with new trade entry against existing trade id.
                trade = new Trade("T2", "3", "CP-1", "B1", "22/05/2024", "15/02/2023", false);
                break;
            case 5:
                // If new trade id itself got introduced during trade.
                // Transaction should be successful along with new Trade id as outcome.
                trade = new Trade("T4", "2", "CP-1", "B1", "22/05/2024", "15/02/2023", false);
                break;
            default:
                throw new IllegalArgumentException("Argument doesn't match with any case");
        }
        return trade;
    }

    /**
     * Method to populate existing Trade data to simulate sample data.
     * @param args : input trade arguments.
     * @return Map : Trade Id's Map which contains Map of unique Trades
     */
    public static Map <String, Map> populateTradeIdMap (String args []) {
        Map <String, Map> tradeIdMap = null;
        Map <String, Trade> tradeVersionMap = null;

        for (int i=0; i<args.length; i=i+5) {
            //Validate if any of required parameter is empty, if so throw exception.
            if (args[i].isEmpty() || args[i + 1].isEmpty() || args[i + 2].isEmpty() || args[i + 3].isEmpty() || args[i + 4].isEmpty()) {
                throw new RuntimeException("One of the required parameter of Trade is Empty..!!!");
            }
            // if Trade Map is null, then initialise it.
            if (tradeIdMap == null) {
                tradeIdMap = new HashMap<>();
            }
            if (!tradeIdMap.containsKey(args[i])) {
                // initialise new Map object to populate trade values.
                tradeVersionMap = new HashMap<>();
            }
            Date todaysDate = new Date();
            Trade trade = new Trade(args[i], args[i+1], args[i+2], args[i+3], args[i+4], todaysDate.toString(), false);
            tradeVersionMap.put(args[i+1], trade);
            tradeIdMap.put(args[i], tradeVersionMap);
        }
        return tradeIdMap;
    }
}