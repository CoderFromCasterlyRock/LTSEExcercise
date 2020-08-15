package com.ltse.excercise.filter;

import com.ltse.excercise.data.RawTrade;
import com.ltse.excercise.data.TradeSerde;
import com.ltse.excercise.util.SpeedChecker;

import java.time.LocalDateTime;
import java.util.*;


public final class BrokerSpeedLimitRule extends FilterRule {

    private final int tradeAllowedCount;
    private final Map<String, SpeedChecker> speedCheckerMap;

    public BrokerSpeedLimitRule( List<String> symbols, List<String> brokers ){
        this( 3, symbols, brokers );
    }

    public BrokerSpeedLimitRule( int tradeAllowedCount, List<String> symbols, List<String> brokers ){
        super(symbols, brokers, 3,"Each broker may only submit three orders per minute.");

        this.tradeAllowedCount  = tradeAllowedCount;
        this.speedCheckerMap    = createSpeedCheckerMap( tradeAllowedCount, brokers );
    }


    @Override
    protected final FilterResult isFilteredOut( int ruleNumber, RawTrade trade ){

        String brokerName       = trade.getBroker();
        SpeedChecker checker    = speedCheckerMap.get( brokerName );
        if( checker == null ){
            return FilterResult.filtered( ruleNumber, "No SpeedChecker configured for Broker", trade );
        }

        LocalDateTime tradeTime = TradeSerde.parseTimestamp( trade.getTimestamp() );
        if( tradeTime == null ){
            return FilterResult.filtered( ruleNumber, "Failed to parse timestamp therefore can't check speed", trade );
        }

        boolean canSendTrade    = checker.canTrade( tradeTime );
        if( canSendTrade ){
            return FilterResult.OK( trade );
        }else{
            return FilterResult.filtered( ruleNumber, "Trade violates speed limit", trade );
        }

    }


    protected final  Map<String, SpeedChecker> createSpeedCheckerMap( int tradeAllowedPerMin, List<String> brokers ){
        Map<String, SpeedChecker> map = new HashMap<>( brokers.size() );

        for( String broker : brokers ){
            map.put( broker, new SpeedChecker(tradeAllowedPerMin) );
        }

        return map;
    }

}
