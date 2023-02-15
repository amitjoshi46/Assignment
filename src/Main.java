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

        Trade trade = new Trade("T2", "2", "CP-1", "BK1",
                "20/11/2019", "20/11/2015", false);
        TradeStore tradeStore = new TradeStore(tradeIdMap);
        //TODO: Get this separated for different cases as parameter
        tradeStore.transmitTrade(trade);
        // Printing full trade objects post transmission...!!!
        for (Map.Entry <String, Map> tradeIdentry : tradeStore.getTradeIdMap().entrySet()) {
            Map <String, Trade> entryVersionMap = tradeIdentry.getValue();
            for (Map.Entry <String, Trade> versionIdEntry : entryVersionMap.entrySet()) {
                System.out.println("Trade Version Values are " +versionIdEntry.getValue().getTradeId()+ " "
                        +versionIdEntry.getValue().getVersion()+ " "
                +versionIdEntry.getValue().isExpired());
            }
        }
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