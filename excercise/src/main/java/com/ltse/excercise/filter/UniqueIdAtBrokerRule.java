package com.ltse.excercise.filter;

import com.ltse.excercise.data.RawTrade;
import java.util.*;


public final class UniqueIdAtBrokerRule extends FilterRule {

    private final Map<String, Set<String>> brokerToIdMap;


    public UniqueIdAtBrokerRule( List<String> symbols, List<String> brokers ){
        super(symbols, brokers, 4,"Within a single broker’s trades ids must be unique.");

        this.brokerToIdMap  = new HashMap<>( brokers.size() );
    }


    @Override
    protected final boolean isFilteredOut( RawTrade trade  ){

        String brokerName           = trade.getBroker();
        String sequenceId           = trade.getSequenceId();
        Set<String> idSetForBroker  = brokerToIdMap.getOrDefault( brokerName, new HashSet<>( 1024 ) );

        if( idSetForBroker.contains(sequenceId) ){
            return true;
        }

        idSetForBroker.add( sequenceId );
        brokerToIdMap.put(brokerName, idSetForBroker );

        return false;
    }


}

