package com.ltse.excercise.filter;

import com.ltse.excercise.data.RawTrade;
import java.util.List;


public final class ValidSymbolsRule extends FilterRule {

    public ValidSymbolsRule(List<String> symbols, List<String> brokers ){
        super(symbols, brokers, 2,"Only order for symbols traded on the exchange must be accepted.");
    }


    @Override
    protected final FilterResult isFilteredOut( int ruleNumber, RawTrade trade ){
        String symbol    = trade.getSymbol();
        boolean filtered = !getSymbols().contains( symbol );

        if( filtered ) {
            return FilterResult.filtered( ruleNumber, symbol + " not in symbols file", trade );
        }else{
            return FilterResult.OK( trade );
        }

    }



}
