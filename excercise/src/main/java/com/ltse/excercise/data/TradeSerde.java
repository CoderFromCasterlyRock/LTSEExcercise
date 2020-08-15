package com.ltse.excercise.data;

import com.google.gson.Gson;
import org.slf4j.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public final class TradeSerde{

    private static final Gson GSON              = new Gson();
    private static final DateTimeFormatter DTF  = DateTimeFormatter.ofPattern("dd/M/yyyy HH:mm");
    private static final Logger LOGGER          = LoggerFactory.getLogger( TradeSerde.class.getSimpleName() );


    public static final RawTrade deserialize(String data, String delimiter ){

        int index               = -1;
        RawTrade rawTrade       = null;

        try {

            //-1 is to preserve empty space, otherwise we will fail to parse if a trade is missing the last field (side)
            String[] dataArray  = data.split( delimiter, -1 );

            String stamp        = dataArray[++ index];
            String broker       = dataArray[++ index];
            String sequenceId   = dataArray[++ index];
            String type         = dataArray[++ index];
            String symbol       = dataArray[++ index];
            String quantity     = dataArray[++ index];
            String price        = dataArray[++ index];
            String side         = dataArray[++ index];

            rawTrade            = new RawTrade( stamp, broker, sequenceId, type, symbol, quantity, price, side );

        }catch( Exception e ) {
            LOGGER.warn("FAILED to parse [{}]", data, e );
        }

        return rawTrade;
    }


    public static final String serializeToJson( RawTrade trade ){
        return GSON.toJson( trade );
    }

    public static final RawTrade deserializeFromJson( String tradeInJson ){
        return GSON.fromJson( tradeInJson, RawTrade.class );
    }


    public static final LocalDateTime parseTimestamp(String timestamp ){
        try{
            return LocalDateTime.parse( timestamp, DTF );
        }catch( Exception e ){
            LOGGER.warn("FAILED to parse timestamp [{}]", timestamp, e );
        }

        return null;
    }

}
