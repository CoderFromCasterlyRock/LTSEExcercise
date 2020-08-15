package com.ltse.excercise.filter;

import com.ltse.excercise.data.RawTrade;
import java.util.List;


public final class ValidSymbolsRule extends FilterRule {

    public ValidSymbolsRule(List<String> symbols, List<String> brokers ){
        super(symbols, brokers, 2,"Only order for symbols traded on the exchange must be accepted.");
    }


    @Override
    protected final boolean isFilteredOut( RawTrade trade ){
       return !getSymbols().contains( trade.getSymbol() );
    }



}
