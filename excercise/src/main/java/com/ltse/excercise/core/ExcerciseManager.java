package com.ltse.excercise.core;

import static com.ltse.excercise.util.ExcerciseUtil.*;

import com.ltse.excercise.filter.*;
import com.ltse.excercise.output.OutputGenerator;
import org.slf4j.*;
import java.util.*;
import com.ltse.excercise.data.TradeSerde;
import com.ltse.excercise.data.RawTrade;

/**
 * CLass which parses the input files, applies rules and generate output.
 *
 * @author vsingh
 */

public final class ExcerciseManager{

    private final List<String> symbols;
    private final List<String> brokers;
    private final String tradeFile;
    private final FilterRule[] filterRules;

    private static final Logger LOGGER      = LoggerFactory.getLogger( ExcerciseManager.class.getSimpleName() );

    ExcerciseManager( ){
        this( "symbols.txt", "firms.txt", "trades.csv");
    }

    public ExcerciseManager( String symbolFile, String brokerFile, String tradesFile ){
        this.tradeFile  = tradesFile;
        this.symbols    = loadSymbols( symbolFile );
        this.brokers    = loadBrokers( brokerFile );
        this.filterRules= createRules( symbols, brokers );

    }


    protected final void process( ){
        long startTime          = System.currentTimeMillis();

        List<RawTrade> trades   = loadRawTrades( true, tradeFile );
        List<RawTrade> filtered = new ArrayList<>( trades.size() );

        filterTrades( trades, filtered );
        processOutput( trades, filtered );

        LOGGER.info("Processed trades in [{}] millis", (System.currentTimeMillis() - startTime ));
    }


    protected final void filterTrades( List<RawTrade> trades, List<RawTrade> filtered ){
        LOGGER.info("Apply [{}] filtering rules to [{}] trades.", filterRules.length, trades.size() );

        for( FilterRule rule : filterRules ){
            rule.applyRule( trades, filtered );
        }
    }



    protected final void processOutput( List<RawTrade> trades, List<RawTrade> filtered ){
        LOGGER.info("Generating output.");

        new OutputGenerator( "AcceptedBrokerId.txt", "AcceptedFullOrder.txt").generate( trades );
        new OutputGenerator(  "RejectedBrokerId.txt", "RejectedFullOrder.txt").generate( filtered );
    }


    public static final FilterRule[] createRules( List<String> symbols, List<String> brokers ){
        return new FilterRule[]{
                new ValidTradeFieldsRule( symbols, brokers ),
                new ValidSymbolsRule( symbols, brokers ),
                new BrokerSpeedLimitRule( symbols, brokers ),
                new UniqueIdAtBrokerRule( symbols, brokers )};

    }


    public static final List<String> loadSymbols( String fileName ){
        List<String> symbols = loadDataFromFile( fileName );
        if( symbols.isEmpty() ){
            throw new IllegalStateException("There are no symbols in " + fileName );
        }

        LOGGER.info( "Loaded [{}] symbols from [{}]", symbols.size(), fileName );
        return symbols;
    }


    public static final List<String> loadBrokers( String fileName ){
        List<String> brokers = loadDataFromFile( fileName );
        if( brokers.isEmpty() ){
            throw new IllegalStateException("There are no brokers in " + fileName );
        }

        LOGGER.info( "Loaded [{}] brokers from [{}]", brokers.size(), fileName );
        return brokers;
    }


    public static final List<RawTrade> loadRawTrades( boolean skipHeader, String fileName ){

        String delimiter        = ",";
        List<String> rawData    = loadDataFromFile( fileName);
        if( rawData.isEmpty() ){
            throw new IllegalStateException("There are no trades in " + fileName );
        }

        //Trade file has header which we skip.
        int startingIndex       = (skipHeader) ? 1 : 0;
        List<RawTrade> trades   = new ArrayList<>( );

        for( int i=startingIndex; i< rawData.size(); i++ ){
            RawTrade rawTrade   = TradeSerde.serialize( rawData.get(i), delimiter );
            trades.add( rawTrade );
        }

        LOGGER.info( "Loaded [{}] trades from [{}] {}", trades.size(), fileName, NEWLINE );

        return trades;

    }

}
