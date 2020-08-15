package com.ltse.excercise.filter;

import com.ltse.excercise.data.RawTrade;
import com.ltse.excercise.util.SpeedChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public final class BrokerSpeedLimitRule extends FilterRule {

    private final Map<String, SpeedChecker> speedCheckerMap;

    private static final Logger LOGGER      = LoggerFactory.getLogger( BrokerSpeedLimitRule.class.getSimpleName() );


    public BrokerSpeedLimitRule(List<String> symbols, List<String> brokers ){
        super(symbols, brokers, 3,"Each broker may only submit three orders per minute.");

        this.speedCheckerMap= createSpeedCheckerMap( 3, brokers );
    }


    @Override
    protected final boolean isFilteredOut(  RawTrade trade ){

        String brokerName   = trade.getBroker();
        SpeedChecker checker= speedCheckerMap.get( brokerName );
        if( checker == null ){
            LOGGER.error("No SpeedChecker configured for Broker [{}]. Is broker is most likely not in the broker file.", brokerName );
            return false;
        }

        return checker.canTrade( System.currentTimeMillis() );
    }




    protected final  Map<String, SpeedChecker> createSpeedCheckerMap( int tradeAllowedPerMin, List<String> brokers ){
        Map<String, SpeedChecker> map = new HashMap<>( brokers.size() );

        for( String broker : brokers ){
            map.put( broker, new SpeedChecker(tradeAllowedPerMin) );
        }

        return map;
    }



}
