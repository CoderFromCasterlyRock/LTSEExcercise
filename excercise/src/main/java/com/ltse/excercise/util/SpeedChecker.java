package com.ltse.excercise.util;

import java.time.Duration;
import java.time.LocalDateTime;


public final class SpeedChecker{

    private int currIndex;
    private final int sizeAllowed;
    private final int timeDurationMinute;
    private final LocalDateTime[] circularTimeArray;

    public SpeedChecker( int sizeAllowed ){
        this( sizeAllowed, 1 );
    }

    public SpeedChecker( int sizeAllowed, int timeDurationMinute ){
        this.currIndex          = 0;
        this.timeDurationMinute = timeDurationMinute;
        this.sizeAllowed        = validateSize(sizeAllowed);
        this.circularTimeArray  = new LocalDateTime[ sizeAllowed ];
    }


    public final boolean canTrade( LocalDateTime tradeTime ){

        int currentIndex            = getCurrentIndex();
        LocalDateTime timeAtIndex   = circularTimeArray[ currentIndex ];

        //Case 1: Current slot is empty, store the trade time, increment index and send the Trade
        if( timeAtIndex == null ){
            circularTimeArray[currentIndex] = tradeTime;
            ++currIndex;
            return true;
        }

        //Case 2: Current slot has a time, compare it to see if enough time has passed
        long elapsedTimeMinute      = Duration.between( timeAtIndex, tradeTime ).toMinutes();
        boolean enoughTimePassed    = (elapsedTimeMinute >= timeDurationMinute);

        //If enough time has passed them replace the older time with new time, move the index and send the trade.
        if( enoughTimePassed ){
            circularTimeArray[currentIndex] = tradeTime;
            ++currIndex;
        }

        return enoughTimePassed;

    }


    protected final int getCurrentIndex( ){
        return currIndex % sizeAllowed;
    }


    private static int validateSize( int sizeAllowed ){
        if( sizeAllowed <= 0 ){
            throw new IllegalStateException("Size Allowed to trade must be greater than 0!");
        }

        return sizeAllowed;
    }

}
