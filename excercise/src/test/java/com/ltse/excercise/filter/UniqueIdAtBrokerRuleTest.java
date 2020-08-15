package com.ltse.excercise.filter;

import com.ltse.excercise.core.TestHelper;
import com.ltse.excercise.data.RawTrade;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class UniqueIdAtBrokerRuleTest {

    private final UniqueIdAtBrokerRule rule = new UniqueIdAtBrokerRule( TestHelper.TEST_SYMBOLS, TestHelper.TEST_BROKERS );


    @Test
    public void filter_repeating_sequence_id(){

        RawTrade valid1     = new RawTrade( "10/5/2017 10:00",  "Fidelity","1", "2", "BRIC","10", "99.0", "Buy" );
        RawTrade invalid1   = new RawTrade( "10/5/2017 10:00",  "Fidelity","1", "2", "BRIC","11", "99.1", "Buy" );
        RawTrade valid2     = new RawTrade( "11/5/2017 10:00",  "Fidelity","1", "2", "CARD","12", "99.2", "Sell" );
        RawTrade invalid2   = new RawTrade( "10/5/2017 10:00",  "Fidelity","2", "2", "BRIC","13", "99.3", "Sell" );
        RawTrade valid3     = new RawTrade( "12/5/2017 10:00",  "Fidelity","2", "K", "BARK","14", "99.4", "Buy" );

        List<RawTrade> filtered = new ArrayList<>();
        List<RawTrade> list     = TestHelper.listOf( valid1, invalid1, valid2, invalid2, valid3 );
        assertThat( list.size() ).isEqualTo( 5 );
        rule.applyRule( list, filtered);

        assertThat( list.size() ).isEqualTo( 2 );
        assertThat( filtered.size() ).isEqualTo( 3 );

        for( RawTrade trade : filtered ){
            System.out.println( "Filtered: " + trade );
        }
    }



    @Test
    public void dont_filter_repeating_sequence_id_for_different_brokers(){

        RawTrade trade1 = new RawTrade( "10/5/2017 10:00",  "Fidelity1","1", "2", "BRIC","10", "99.0", "Buy" );
        RawTrade trade2 = new RawTrade( "10/5/2017 10:00",  "Fidelity2","1", "2", "BRIC","11", "99.1", "Buy" );
        RawTrade trade3 = new RawTrade( "11/5/2017 10:00",  "Fidelity3","1", "2", "CARD","12", "99.2", "Sell" );
        RawTrade trade4 = new RawTrade( "10/5/2017 10:00",  "Fidelity4","1", "2", "BRIC","13", "99.3", "Sell" );
        RawTrade trade5 = new RawTrade( "12/5/2017 10:00",  "Fidelity5","1", "K", "BARK","14", "99.4", "Buy" );

        List<RawTrade> filtered = new ArrayList<>();
        List<RawTrade> list = TestHelper.listOf( trade1, trade2, trade3, trade4, trade5 );
        assertThat( list.size() ).isEqualTo( 5 );
        rule.applyRule( list, filtered );

        assertThat( list.size() ).isEqualTo( 5 );
        assertThat( filtered.size() ).isEqualTo( 0 );

    }


    @Test
    public void non_repeating_sequence_id_for_same_broker(){

        RawTrade trade1 = new RawTrade( "10/5/2017 10:00",  "Fidelity","1", "2", "BRIC","10", "99.0", "Buy" );
        RawTrade trade2 = new RawTrade( "10/5/2017 10:00",  "Fidelity","2", "2", "BRIC","11", "99.1", "Buy" );
        RawTrade trade3 = new RawTrade( "11/5/2017 10:00",  "Fidelity","3", "2", "CARD","12", "99.2", "Sell" );
        RawTrade trade4 = new RawTrade( "10/5/2017 10:00",  "Fidelity","4", "2", "BRIC","13", "99.3", "Sell" );
        RawTrade trade5 = new RawTrade( "12/5/2017 10:00",  "Fidelity","5", "K", "BARK","14", "99.4", "Buy" );

        List<RawTrade> filtered = new ArrayList<>();
        List<RawTrade> list = TestHelper.listOf( trade1, trade2, trade3, trade4, trade5 );
        assertThat( list.size() ).isEqualTo( 5 );
        rule.applyRule( list, filtered);

        assertThat( list.size() ).isEqualTo( 5 );
        assertThat( filtered.size() ).isEqualTo( 0 );
    }



}
