package com.assignment.tradestore;
/**
 * Core Trade class which holds up values for each trade object.
 */
public class Trade {
    // variable for TradeId.
    private String tradeId;
    //variable for version.
    private String version;
    //Variable for counter party id.
    private String cpId;
    //Variable for booking id.
    private String bookId;
    //Variable for Maturity Date
    private String maturityDate;
    // Variable for created date.
    private String createdDate;
    //Variable for expiry flag.
    private boolean isExpired;

    /**
     * Constructor to initialize values for all variables of the class.
     * @param tradeId : Trade ID
     * @param version : Version
     * @param cpId : Counter Party Id
     * @param bookId : Booking Id
     * @param maturityDate : Maturity Date
     * @param createdDate : Created Date
     * @param isExpired : Expiry flag
     */
    public Trade (String tradeId, String version, String cpId, String bookId, String maturityDate,
           String createdDate, boolean isExpired) {
        this.tradeId = tradeId;
        this.version = version;
        this.cpId = cpId;
        this.bookId = bookId;
        this.maturityDate = maturityDate;
        this.createdDate = createdDate;
        this.isExpired = isExpired;
    }

    /**
     * Set's created date as system date which used to add valid trade record.
     * @param createdDate : System date
     */
    public void setCreatedDate (String createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Set's expiry flag based on validation to respctive record.
     * @param isExpired : expiry flag.
     */
    public void setExpired (boolean isExpired) {
        this.isExpired = isExpired;
    }

    /**
     * Get method for TradeId
     * @return : Trade Id
     */
    public String getTradeId () {
        return this.tradeId;
    }
    /**
     * Get version of trade
     * @return : Version of trade in String
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * Get counter party id
     * @return : counter party id in the form of String
     */
    public String getCpId() {
        return this.cpId;
    }

    /**
     * Get booking Id
     * @return : Booking id int the form of String
     */
    public String getBookId() {
        return this.bookId;
    }

    /**
     * Get Maturity Date
     * @return : Maturity date in form of String.
     */
    public String getMaturityDate() {
        return this.maturityDate;
    }

    /**
     * Get created date
     * @return : Created date in the form of String
     */
    public String getCreatedDate() {
        return this.createdDate;
    }

    /**
     * expired flag value
     * @return : boolean to identify expired flag
     */
    public boolean isExpired() {
        return this.isExpired;
    }
}
