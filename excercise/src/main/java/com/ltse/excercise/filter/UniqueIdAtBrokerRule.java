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
    protected final FilterResult isFilteredOut( int ruleNumber, RawTrade trade ){

        String brokerName           = trade.getBroker();
        String sequenceId           = trade.getSequenceId();
        Set<String> idSetForBroker  = brokerToIdMap.getOrDefault( brokerName, new HashSet<>( 1024 ) );

        if( idSetForBroker.contains(sequenceId) ){
            return FilterResult.filtered( ruleNumber, "Sequence " + sequenceId + " already exists for " + brokerName, trade );
        }

        idSetForBroker.add( sequenceId );
        brokerToIdMap.put(brokerName, idSetForBroker );

        return FilterResult.OK( trade );
    }


}

