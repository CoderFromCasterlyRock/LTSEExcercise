package com.ltse.excercise.filter;

import com.ltse.excercise.data.RawTrade;
import com.ltse.excercise.util.ExcerciseUtil;

import java.util.List;
import java.util.Set;

import static com.ltse.excercise.util.ExcerciseUtil.*;

public final class ValidTradeFieldsRule extends FilterRule {

    private static final Set<String> VALID_TYPE = ExcerciseUtil.SetFrom("2", "K");
    private static final Set<String> VALID_SIDE = ExcerciseUtil.SetFrom("Buy", "Sell");

    public ValidTradeFieldsRule( List<String> symbols, List<String> brokers ){
        super( symbols, brokers, 1, "Must have valid values for Broker, Symbol, Type, Quantity, Sequence Id, Side, Price");
    }


    @Override
    protected final FilterResult isFilteredOut( int ruleNumber, RawTrade trade ){

        if( trade == null ){
            return FilterResult.filtered( ruleNumber, "Trade is null", null );
        }

        if( isInvalid(trade.getBroker()) ){
            return FilterResult.filtered( ruleNumber, "Broker is invalid", trade );
        }

        if( isInvalid(trade.getSymbol()) ){
            return FilterResult.filtered( ruleNumber, "Symbol is invalid", trade );
        }

        if( isTypeInvalid(trade.getType()) ){
            return FilterResult.filtered( ruleNumber, "TradeType is invalid", trade );
        }

        if( isInvalidInteger(trade.getQuantity()) ){
            return FilterResult.filtered( ruleNumber, "Quantity is invalid", trade );
        }

        if( isInvalidInteger(trade.getSequenceId()) ){
            return FilterResult.filtered( ruleNumber, "SequenceId is invalid", trade );
        }

        if( isSideInvalid(trade.getSide()) ){
            return FilterResult.filtered( ruleNumber, "Side is invalid", trade );
        }

        if( isInvalidDouble(trade.getPrice()) ){
            return FilterResult.filtered( ruleNumber, "Price is invalid", trade );
        }

        return FilterResult.OK( trade );

    }

    protected final boolean isTypeInvalid( String type ){
        return !VALID_TYPE.contains(type);
    }

    protected final boolean isSideInvalid( String side ){
        return !VALID_SIDE.contains(side);
    }


}
