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
    protected final boolean isFilteredOut( RawTrade trade ){

        if( trade == null ){
            return true;
        }

        if( isInvalid(trade.getBroker()) ){
            return true;
        }

        if( isInvalid(trade.getSymbol()) ){
            return true;
        }

        if( isTypeInvalid(trade.getType()) ){
            return true;
        }

        if( isInvalidInteger(trade.getQuantity()) ){
            return true;
        }

        if( isInvalidInteger(trade.getSequenceId()) ){
            return true;
        }

        if( isSideInvalid(trade.getSide()) ){
            return true;
        }

        if( isInvalidDouble(trade.getPrice()) ){
            return true;
        }

        return false;

    }


    private static final boolean isTypeInvalid( String type ){
        return !VALID_TYPE.contains(type);
    }


    private static final boolean isSideInvalid( String side ){
        return !VALID_SIDE.contains(side);
    }


}
