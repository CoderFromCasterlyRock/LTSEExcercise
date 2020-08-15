package com.ltse.excercise.output;

import com.ltse.excercise.data.RawTrade;
import com.ltse.excercise.data.TradeSerde;
import com.ltse.excercise.util.ExcerciseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public final class OutputGenerator {

    private final String miniFileName;
    private final String fullOrderFileName;

    private static final String DELIMITER   = ",";
    private static final Logger LOGGER      = LoggerFactory.getLogger( OutputGenerator.class.getSimpleName() );

    public OutputGenerator( String miniFileName, String fullOrderFileName ){
        this.miniFileName     = miniFileName;
        this.fullOrderFileName= fullOrderFileName;
    }


    public final void generate( List<RawTrade> trades ){

        StringBuilder miniBuilder  = new StringBuilder( trades.size() );
        StringBuilder fullBuilder  = new StringBuilder( trades.size() );

        for( RawTrade trade : trades ){
            addBrokerAndSequenceId( miniBuilder, trade );
            addTradeAsJson( fullBuilder, trade );
        }

        boolean fileWritten   = ExcerciseUtil.writeDataToFile( miniFileName, miniBuilder.toString() );
        if( fileWritten ){
            LOGGER.info("Successfully generated [{}]", miniFileName );
        }


        boolean fileWritten2   = ExcerciseUtil.writeDataToFile( fullOrderFileName, fullBuilder.toString() );
        if( fileWritten2 ){
            LOGGER.info("Successfully generated [{}]", fullOrderFileName );
        }

    }



    protected final void addBrokerAndSequenceId( StringBuilder builder, RawTrade trade ){
        builder.append( trade.getBroker() ).append( DELIMITER ).append(trade.getSequenceId() ).append(ExcerciseUtil.NEWLINE);
    }


    protected final void addTradeAsJson( StringBuilder builder, RawTrade trade ){
        builder.append( TradeSerde.deserialize(trade) ).append(ExcerciseUtil.NEWLINE );
    }


}
