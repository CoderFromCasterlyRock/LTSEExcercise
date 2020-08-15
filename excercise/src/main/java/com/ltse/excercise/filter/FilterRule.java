package com.ltse.excercise.filter;

import com.ltse.excercise.data.RawTrade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

import static com.ltse.excercise.util.ExcerciseUtil.*;

public abstract class FilterRule {

    private final List<String> symbols;
    private final List<String> brokers;
    private final int ruleNumber;
    private final String ruleDefinition;


    private static final Logger LOGGER      = LoggerFactory.getLogger( FilterRule.class.getSimpleName() );

    public FilterRule( List<String> symbols, List<String> brokers, int ruleNumber, String ruleDefinition ){
        this.symbols        = symbols;
        this.brokers        = brokers;
        this.ruleNumber     = ruleNumber;
        this.ruleDefinition = ruleDefinition;
    }


    protected abstract boolean isFilteredOut( RawTrade trade );


    protected final List<String> getSymbols( ){
        return symbols;
    }

    protected final List<String> getBrokers( ){
        return brokers;
    }


    public final void applyRule( List<RawTrade> trades, List<RawTrade> filteredList ){

        LOGGER.info("Applying rule #{} [{}]", ruleNumber, ruleDefinition );

        int originalTradeSize       = trades.size();
        Iterator<RawTrade> iterator = trades.iterator();

        while( iterator.hasNext() ){
            RawTrade trade = iterator.next();

            if( isFilteredOut(trade) ){
                filteredList.add( trade );
                //Remove the trade from the provided list as it was filtered out.
                iterator.remove();
            }

        }

        int finalTradeSize       = trades.size();
        LOGGER.info("Input [{}], Output [{}], Filtered [{}] trades {}", originalTradeSize, finalTradeSize, (originalTradeSize-finalTradeSize), NEWLINE);

    }



}
