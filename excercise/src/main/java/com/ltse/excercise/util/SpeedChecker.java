package com.ltse.excercise.util;

import java.util.concurrent.TimeUnit;

public final class SpeedChecker{

    private int currIndex;
    private final int sizeAllowed;
    private final long[] timestamp;

    private static int MAX_SIZE_ALLOWED     = 1000;
    private static long _1_MIN_IN_MILLIS    = TimeUnit.MINUTES.toMillis( 1 );

    public SpeedChecker( int sizeAllowed ){
        this.currIndex      = 0;
        this.sizeAllowed    = validateSize(sizeAllowed);
        this.timestamp      = new long[ sizeAllowed ];
    }


    public final boolean canTrade( long tradeTimeMillis ){

        int currentIndex        = getCurrentIndex();
        long tradeTimeAtIndex   = timestamp[ currentIndex ];

        //Case 1: Slot is empty, take it, store the current time in the slot, increment to next index and send the Trade
        if( tradeTimeAtIndex == 0 ){
            timestamp[ currentIndex ] = tradeTimeMillis;
            ++currIndex;
            return true;
        }

        //Case 2: Slot has a time, compare it to see if 1 min has passed
        long timeElapsed        = ( tradeTimeMillis - tradeTimeAtIndex );
        boolean moreThan1MinAgo =  timeElapsed > _1_MIN_IN_MILLIS;

        //Yes, more than 1 min has passed, replace the older time with new time and send the Trade
        if( moreThan1MinAgo ){
            timestamp[ currentIndex ] = tradeTimeMillis;
            ++currIndex;
            return true;

        }else{
            //No. more than 1 min has not passed, we cannot send the trade
            timestamp[ currentIndex ] = tradeTimeMillis;
            ++currIndex;
            return false;
        }

    }


    protected final int getCurrentIndex( ){
        return currIndex % sizeAllowed;
    }


    private static final int validateSize( int sizeAllowed ){
        if( sizeAllowed <= 0 ){
            throw new IllegalStateException("Size Allowed to trade must be greater than 0!");
        }

        if( sizeAllowed > MAX_SIZE_ALLOWED ){
            throw new IllegalStateException("Size Allowed to trade cannot be greater than " + MAX_SIZE_ALLOWED);
        }

        return sizeAllowed;

    }
}
