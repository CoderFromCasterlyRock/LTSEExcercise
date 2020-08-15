package com.ltse.excercise.core;

import com.ltse.excercise.data.RawTrade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestHelper {

    public static List<String> TEST_SYMBOLS = Arrays.asList( "BARK", "CARD", "BRIC", "LGHT", "HOOF" );
    public static List<String> TEST_BROKERS = Arrays.asList( "Fidelity", "AXA Advisors", "Charles Schwab");


    public static List<RawTrade> listOf( RawTrade ... trades ){
        List<RawTrade> list = new ArrayList<>();
        for( RawTrade trade : trades ){
            list.add( trade );
        }

        return list;
    }


}
