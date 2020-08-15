package com.ltse.excercise.filter;

import com.ltse.excercise.data.RawTrade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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


    protected abstract FilterResult isFilteredOut( int ruleNumber, RawTrade trade );

    protected final List<String> getSymbols( ){
        return symbols;
    }

    protected final List<String> getBrokers( ){
        return brokers;
    }


    public final List<FilterResult> applyRule( List<RawTrade> trades, List<RawTrade> filteredList ){

        LOGGER.info("Applying rule #{} [{}]", ruleNumber, ruleDefinition );

        int originalTradeSize       = trades.size();
        List<FilterResult> results  = new ArrayList<>( );
        Iterator<RawTrade> iterator = trades.iterator();

        while( iterator.hasNext() ){
            RawTrade trade      = iterator.next();
            FilterResult result = isFilteredOut( ruleNumber, trade);

            if( result.isFiltered() ){
                //Remove the trade from the provided list as it was filtered out.
                iterator.remove();

                //Add the filtered trade to the filtered list
                filteredList.add( trade );
            }

            results.add( result );
        }

        int finalTradeSize       = trades.size();
        LOGGER.info("Input [{}], Output [{}], Filtered [{}] trades.", originalTradeSize, finalTradeSize, (originalTradeSize-finalTradeSize) );

        return results;

    }



}
