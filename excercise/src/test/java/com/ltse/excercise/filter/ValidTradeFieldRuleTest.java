package com.ltse.excercise.filter;

import com.ltse.excercise.core.TestHelper;
import com.ltse.excercise.data.RawTrade;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;



public class ValidTradeFieldRuleTest {

    private final ValidTradeFieldsRule rule = new ValidTradeFieldsRule( TestHelper.TEST_SYMBOLS, TestHelper.TEST_BROKERS );


    @Test
    public void valid_trade(){
        RawTrade trade1 = new RawTrade( "time1", "broker1", "1", "2", "symbol1", "10", "99.0", "Buy" );
        assertThat( rule.isFilteredOut(trade1) ).isFalse();
    }


    @Test
    public void test_rule_filtering(){

        RawTrade valid1     = new RawTrade( "10/5/2017 10:00",  "Fidelity",         "1", "2", "BRIC",   "10", "99.0", "Buy" );
        RawTrade invalid1   = new RawTrade( "10/5/2017 10:00",  "broker1",          "1", "2", "symbol1", "10", "AAA", "Buy" );
        RawTrade valid2     = new RawTrade( "11/5/2017 10:00",  "Charles Schwab",   "1", "2", "CARD",   "10", "99.0", "Sell" );
        RawTrade invalid2   = new RawTrade( "10/5/2017 10:00",  "broker1",          "1", "2", "symbol1", "10", "99.0", "Short Sell" );
        RawTrade valid3     = new RawTrade( "12/5/2017 10:00",  "LPL Financial",    "1", "K", "BARK",   "10", "99.0", "Buy" );

        List<RawTrade> filtered = new ArrayList<>();
        List<RawTrade> list = TestHelper.listOf( valid1, invalid1, valid2, invalid2, valid3 );
        assertThat( list.size() ).isEqualTo( 5 );
        rule.applyRule( list, filtered );

        assertThat( list.size() ).isEqualTo( 3 );
        assertThat( filtered.size() ).isEqualTo( 2 );

        for( RawTrade trade : filtered ){
            System.out.println( "Filtered: " + trade );
        }
    }



    @Test
    public void empty_broker(){
        RawTrade trade1 = new RawTrade( "time1", "", "seq1", "type1", "symbol1", "qty1", "price1", "side1" );
        assertThat( rule.isFilteredOut(trade1) ).isTrue();
    }


    @Test
    public void empty_symbol(){
        RawTrade trade1 = new RawTrade( "time1", "broker1", "seq1", "type1", "", "qty1", "price1", "side1" );
        assertThat( rule.isFilteredOut(trade1) ).isTrue();
    }


    @Test
    public void empty_type(){
        RawTrade trade1 = new RawTrade( "time1", "broker1", "seq1", "", "symbol1", "qty1", "price1", "side1" );
        assertThat( rule.isFilteredOut(trade1) ).isTrue();
    }

    @Test
    public void type_not_K_OR_2(){
        RawTrade trade1 = new RawTrade( "time1", "broker1", "seq1", "L", "symbol1", "qty1", "price1", "side1" );
        assertThat( rule.isFilteredOut(trade1) ).isTrue();
    }


    @Test
    public void empty_quantity(){
        RawTrade trade1 = new RawTrade( "time1", "broker1", "seq1", "2", "symbol1", "", "price1", "side1" );
        assertThat( rule.isFilteredOut(trade1) ).isTrue();
    }


    @Test
    public void non_numeric_quantity(){
        RawTrade trade1 = new RawTrade( "time1", "broker1", "seq1", "2", "symbol1", "AA", "price1", "side1" );
        assertThat( rule.isFilteredOut(trade1) ).isTrue();
    }



    @Test
    public void empty_price(){
        RawTrade trade1 = new RawTrade( "time1", "broker1", "seq1", "2", "symbol1", "10", "", "side1" );
        assertThat( rule.isFilteredOut(trade1) ).isTrue();
    }


    @Test
    public void non_numeric_price(){
        RawTrade trade1 = new RawTrade( "time1", "broker1", "seq1", "2", "symbol1", "10", "AA", "side1" );
        assertThat( rule.isFilteredOut(trade1) ).isTrue();
    }

    @Test
    public void empty_side(){
        RawTrade trade1 = new RawTrade( "time1", "broker1", "", "2", "symbol1", "10", "99.0", "" );
        assertThat( rule.isFilteredOut(trade1) ).isTrue();
    }


    @Test
    public void side_not_buy_not_sell(){
        RawTrade trade1 = new RawTrade( "time1", "broker1", "AA", "2", "symbol1", "10", "99.0", "short sell" );
        assertThat( rule.isFilteredOut(trade1) ).isTrue();
    }


    @Test
    public void empty_sequenceId(){
        RawTrade trade1 = new RawTrade( "time1", "broker1", "", "2", "symbol1", "10", "price1", "side1" );
        assertThat( rule.isFilteredOut(trade1) ).isTrue();
    }


    @Test
    public void non_numeric_sequenceId(){
        RawTrade trade1 = new RawTrade( "time1", "broker1", "AA", "2", "symbol1", "10", "price1", "side1" );
        assertThat( rule.isFilteredOut(trade1) ).isTrue();
    }


}
