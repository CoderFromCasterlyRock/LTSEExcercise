package com.ltse.excercise.util;

import com.ltse.excercise.util.SpeedChecker;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class SpeedCheckerTest {


    @Test
    public void _1_order_per_min_is_ok() {
        SpeedChecker checker = new SpeedChecker( 1 );
        assertThat( checker.canTrade( System.currentTimeMillis() ) ).isTrue();
    }


    @Test
    public void _3_order_per_min_is_ok() {
        SpeedChecker checker = new SpeedChecker( 3 );

        assertThat( checker.canTrade( System.currentTimeMillis() ) ).isTrue();
        assertThat( checker.canTrade( System.currentTimeMillis() ) ).isTrue();
        assertThat( checker.canTrade( System.currentTimeMillis() ) ).isTrue();
    }


    @Test
    public void _4_order_per_min_is_not_ok() {
        SpeedChecker checker = new SpeedChecker( 3 );

        assertThat( checker.canTrade( System.currentTimeMillis() ) ).isTrue();
        assertThat( checker.canTrade( System.currentTimeMillis() ) ).isTrue();
        assertThat( checker.canTrade( System.currentTimeMillis() ) ).isTrue();
        assertThat( checker.canTrade( System.currentTimeMillis() ) ).isFalse();

    }


    @Test
    public void _100_order_per_min_is_not_ok() {
        SpeedChecker checker = new SpeedChecker( 3 );

        assertThat( checker.canTrade( System.currentTimeMillis() ) ).isTrue();
        assertThat( checker.canTrade( System.currentTimeMillis() ) ).isTrue();
        assertThat( checker.canTrade( System.currentTimeMillis() ) ).isTrue();

        for( int i=0; i<97; i++ ) {
            assertThat(checker.canTrade(System.currentTimeMillis())).isFalse();
        }
    }



    @Test
    public void _100_order_per_min_is_ok_if_allowed() {
        SpeedChecker checker = new SpeedChecker( 100 );
        for( int i=0; i<100; i++ ) {
            assertThat( checker.canTrade( System.currentTimeMillis() ) ).isTrue();
        }
    }




    @Test(expected = IllegalStateException.class)
    public void invalid_if_size_less_or_equal_to_0() {
        new SpeedChecker( 0 ).canTrade( System.currentTimeMillis() );
    }



    @Test(expected = IllegalStateException.class)
    public void invalid_if_size_more_than_10_000() {
        new SpeedChecker( 10_001 ).canTrade( System.currentTimeMillis() );
    }

}
