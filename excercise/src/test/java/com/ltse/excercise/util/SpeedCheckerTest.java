package com.ltse.excercise.util;

import com.ltse.excercise.data.RawTrade;
import com.ltse.excercise.data.TradeSerde;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import static org.assertj.core.api.Assertions.assertThat;


public class SpeedCheckerTest {


    @Test
    public void _4_order_per_min_is_not_ok() {
        SpeedChecker checker = new SpeedChecker( 3 );

        assertThat( checker.canTrade( LocalDateTime.now() ) ).isTrue();
        assertThat( checker.canTrade( LocalDateTime.now() ) ).isTrue();
        assertThat( checker.canTrade( LocalDateTime.now() ) ).isTrue();
        assertThat( checker.canTrade( LocalDateTime.now() ) ).isFalse();
    }


    @Test
    public void issue(){
        RawTrade trade1 = new RawTrade( "10/5/2017 10:00",  "Edward Jones", "1", "k", "HOOF",   "150", "39.1", "Buy" );
        RawTrade trade2 = new RawTrade( "10/5/2017 10:00",  "Edward Jones", "2", "2", "BARK",   "400", "88.3", "Buy" );
        RawTrade trade3 = new RawTrade( "10/5/2017 10:00",  "Edward Jones", "3", "2", "LOUD",   "400", "10.96", "Sell" );
        RawTrade trade4 = new RawTrade( "10/5/2017 10:01",  "Edward Jones", "4", "2", "YLLW",   "200", "11.3", "Sell" );

        SpeedChecker checker = new SpeedChecker( 3 );

        System.out.println( checker.canTrade(TradeSerde.parseTimestamp(trade1.getTimestamp())) );
        System.out.println( checker.canTrade(TradeSerde.parseTimestamp(trade2.getTimestamp())) );
        System.out.println( checker.canTrade(TradeSerde.parseTimestamp(trade3.getTimestamp())) );
        System.out.println( checker.canTrade(TradeSerde.parseTimestamp(trade4.getTimestamp())) );


    }


    @Test
    public void can_send_more_orders_after_time_limit_has_passed(){
        //3 trades allowed in 100 ms
        SpeedChecker checker = new SpeedChecker( 3 );

        //Send 3 trades, they should pass
        assertThat( checker.canTrade( LocalDateTime.now() ) ).isTrue();
        assertThat( checker.canTrade( LocalDateTime.now() ) ).isTrue();
        assertThat( checker.canTrade( LocalDateTime.now() ) ).isTrue();
        //4rth trade is rejected
        assertThat( checker.canTrade( LocalDateTime.now() ) ).isFalse();

        //Wait for 1 minute to pass
        System.out.println("Pausing for 1 min to simulate speed test time reset.");
        LockSupport.parkNanos( TimeUnit.MINUTES.toNanos(1));

        //Send 3 more which should go through
        assertThat( checker.canTrade( LocalDateTime.now() ) ).isTrue();
        assertThat( checker.canTrade( LocalDateTime.now() ) ).isTrue();
        assertThat( checker.canTrade( LocalDateTime.now() ) ).isTrue();

        //4rth trade is rejected
        assertThat( checker.canTrade( LocalDateTime.now() ) ).isFalse();

    }



    @Test
    public void _100_order_per_min_is_not_ok() {
        SpeedChecker checker = new SpeedChecker( 3 );

        assertThat( checker.canTrade( LocalDateTime.now() ) ).isTrue();
        assertThat( checker.canTrade( LocalDateTime.now() ) ).isTrue();
        assertThat( checker.canTrade( LocalDateTime.now() ) ).isTrue();

        for( int i=0; i<97; i++ ) {
            assertThat(checker.canTrade(LocalDateTime.now())).isFalse();
        }
    }



    @Test
    public void _100_order_per_min_is_ok_if_allowed() {
        SpeedChecker checker = new SpeedChecker( 100 );
        for( int i=0; i<100; i++ ) {
            assertThat( checker.canTrade( LocalDateTime.now() ) ).isTrue();
        }
    }


    @Test
    public void _1_order_per_min_is_ok() {
        SpeedChecker checker = new SpeedChecker( 1 );
        assertThat( checker.canTrade( LocalDateTime.now() ) ).isTrue();
    }


    @Test
    public void _3_order_per_min_is_ok() {
        SpeedChecker checker = new SpeedChecker( 3 );

        assertThat( checker.canTrade( LocalDateTime.now() ) ).isTrue();
        assertThat( checker.canTrade( LocalDateTime.now() ) ).isTrue();
        assertThat( checker.canTrade( LocalDateTime.now() ) ).isTrue();
    }

    @Test(expected = IllegalStateException.class)
    public void invalid_if_size_less_or_equal_to_0() {
        new SpeedChecker( 0 ).canTrade( LocalDateTime.now() );
    }



}
