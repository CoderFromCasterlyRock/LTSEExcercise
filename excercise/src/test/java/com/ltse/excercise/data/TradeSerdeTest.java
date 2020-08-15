package com.ltse.excercise.data;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class TradeSerdeTest {


    @Test
    public void parse_timestamp(){
        assertThat( TradeSerde.parseTimestamp("10/5/2017 10:00") ).isNotNull();
    }

    @Test
    public void serialize_deserialize() {
        RawTrade trade1     = new RawTrade( "10/5/2017 10:00",  "Fidelity", "1", "2", "BRIC",   "10", "99.0", "Buy" );
        String tradeJson    = TradeSerde.serializeToJson( trade1 );
        assertThat( tradeJson ).isNotEmpty();

        RawTrade trade2     = TradeSerde.deserializeFromJson( tradeJson );
        assertThat( trade2 ).isEqualToComparingFieldByField( trade1 );
    }




}
