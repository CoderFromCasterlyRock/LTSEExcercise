package com.ltse.excercise.filter;

import com.ltse.excercise.data.RawTrade;

public final class FilterResult {

    private final boolean filtered;
    private final int ruleNumber;
    private final String reason;
    private final String tradeInfo;


    public FilterResult( boolean filtered, int ruleNumber, String reason, RawTrade trade ){
        this.filtered   = filtered;
        this.ruleNumber = ruleNumber;
        this.reason     = reason;
        this.tradeInfo  = (trade == null) ? "" : trade.toString();
    }


    public static final FilterResult OK(  RawTrade trade ){
        return new FilterResult( false, -1, "", trade );
    }


    public static final FilterResult filtered(int ruleNumber, String reason, RawTrade trade ){
        return new FilterResult( true, ruleNumber, reason, trade );
    }


    public final boolean isFiltered(){
        return filtered;
    }


    public final int getRuleNumber() {
        return ruleNumber;
    }


    public final String getReason() {
        return reason;
    }

    public final String getTradeInfo() {
        return tradeInfo;
    }


    @Override
    public final String toString() {

        StringBuilder sb = new StringBuilder("FilterResult [");
        sb.append("Filtered=").append(filtered)
        .append(", ruleNumber=").append(ruleNumber)
        .append(", reason=").append(reason)
        .append(", ").append(tradeInfo)
        .append(']');

        return sb.toString();
    }

}
