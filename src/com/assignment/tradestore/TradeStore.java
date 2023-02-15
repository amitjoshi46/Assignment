package com.assignment.tradestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to perform validation on given trade, transmit valid trades and print final result.
 */
public class TradeStore {
    // Map to be used for validation and transmission purpose.
    private Map <String, Map> tradeIdMap;

    /**
     * Default constructor to initialize tradeMap.
     */
    public TradeStore () {
        this.tradeIdMap = new HashMap<>();
    }

    /**
     * Public constructor to initialize trade map to perform desired actions.
     * @param tradeIdMap: TradeMap to be action on
     */
    public TradeStore (Map<String, Map> tradeIdMap) {
        this.tradeIdMap = tradeIdMap;
    }

    /**
     * Method to get Updated Trade Map which contains Map of Trades.
     * @return : Map of Trade Maps.
     */
    public Map <String, Map> getTradeIdMap () {
        return this.tradeIdMap;
    }

    /**
     * Method to use for transmission on given trade against TradeMap
     * @param trade : Trade Object which needs to be validate and transmit
     * @throws RuntimeException : throws runtime exception for any invalid transmit as per validation rule.
     */
    public void transmitTrade (Trade trade) throws RuntimeException {
        if (performVersionValidation(trade)) {
            System.out.println("Successful Trade...!!!");
        } else {
            System.out.println("Unsuccessful Trade...!!!");
        }
    }

    /**
     * Method to validate Trade
     * @param trade : Trade object to be validated
     * @return : Boolean based on validation rules
     */
    public boolean performVersionValidation (Trade trade) {

        if (isTradeIdExists(trade)){
            if (isVersionExists(trade)) {
                // Valid case as both Trade Id and Version is available, go for update trade...!!!
                updateTradeIdMap (trade);
                return true;
            } else if (isValidVersion(trade)) {
                // Valid case as trades version is higher than existing version, go for add trade...!!!
                addTradesToTradeIdMap(trade);
                return true;
            } else {
                // Trade version is lower than existing one, throw exception...!!!
                throw new RuntimeException("Given Trade Version is not Valid...!!!");
            }

        } else {
            // Valid case as altogether new Trade Id has been requested, go for add trade..!!!
            addTradesToTradeIdMap (trade);
        }
        return false;
    }

    /**
     * Method to add new trade either against existing trade id, or against new trade id.
     * @param trade : Trade object to be added.
     */

    public void addTradesToTradeIdMap (Trade trade) {
        Map <String, Trade> newTradeMap;
        if (isTradeIdExists(trade)) {
            newTradeMap = this.tradeIdMap.get(trade.getTradeId());
        } else {
            newTradeMap = new HashMap<>();
        }
        newTradeMap.put(trade.getVersion(), trade);
        this.tradeIdMap.put(trade.getTradeId(), newTradeMap);
    }

    /**
     * Method to override existing trades in case of valid version and expiry. Also method override expiry flag in case of trade is expired.
     * @param trade : trade object to transmit.
     */
    public void updateTradeIdMap (Trade trade) {
        Map <String, Trade> tradeVersionMap = this.tradeIdMap.get(trade.getTradeId());
        if (performMaturityValidation (trade)) {
            tradeVersionMap.put(trade.getVersion(), trade);
        } else {
            Trade amendedTrade = tradeVersionMap.get(trade.getVersion());
            amendedTrade.setExpired(true);
            tradeVersionMap.put(trade.getVersion(), amendedTrade);
        }
        this.tradeIdMap.put(trade.getTradeId(), tradeVersionMap);
    }

    /**
     * Method to perform Maturity date validation on given trade against existing data.
     * @param trade : Trade object
     * @return : True if today's date is equal or lower than maturity date.
     */
    public boolean performMaturityValidation (Trade trade) {
        SimpleDateFormat sdf = new SimpleDateFormat("DD/MM/YYYY");
        Date today = null;
        String todayFormat = null;
        Date tradeMaturityDate = null;
        try{
            todayFormat = sdf.format(new Date());
            today = sdf.parse(todayFormat);
            tradeMaturityDate = sdf.parse(trade.getMaturityDate());
            if (today.compareTo(tradeMaturityDate) <= 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method to check if given trade version is already exists or not ?
     * @param trade : trade to be validated
     * @return : True if trade Exists else false.
     */
    public boolean isVersionExists (Trade trade) {
        if (this.tradeIdMap.get(trade.getTradeId()) != null) {
            if (this.tradeIdMap.get(trade.getTradeId()).get(trade.getVersion()) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to validate version number. Returns true if version is equal's or higher than existing trade, else return false.
     * @param trade : trade object on which version validation to be performed.
     * @return : True if valid version, else false.
     */
    public boolean isValidVersion (Trade trade) {
        boolean isValidVersion = false;
        if (isTradeIdExists (trade)) {
            Map <String, Trade> versionIdMap = this.tradeIdMap.get(trade.getTradeId());
            for (Map.Entry <String, Trade> entry : versionIdMap.entrySet()) {
                if (Integer.parseInt(trade.getVersion()) > Integer.parseInt(entry.getValue().getVersion())) {
                    // this is valid version as given version is grater than existing.
                    isValidVersion = true;
                }
            }
        }
        // return valid version flag.
        return isValidVersion;
    }

    /**
     * Method to test if given trade is requested against existing trade id or not.
     * @param trade : Trade object to evaluate trade id.
     * @return : true if trade requested against existing trade id, else false.
     */

    public boolean isTradeIdExists (Trade trade) {
        if (this.tradeIdMap.get(trade.getTradeId()) != null) {
            return true;
        }
        return false;
    }
}
