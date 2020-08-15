package com.ltse.excercise.output;

import com.ltse.excercise.data.RawTrade;
import com.ltse.excercise.data.TradeSerde;
import com.ltse.excercise.util.ExcerciseUtil;

import java.util.List;
import static com.ltse.excercise.util.ExcerciseUtil.*;

public final class OutputGenerator {

    private final String miniFileName;
    private final String fullOrderFileName;

    private static final String DELIMITER       = ",";
    private static final String MINI_HEADER     = "broker,sequence";
    private static final String FULL_HEADER     = "Time stamp,broker,sequence,type,Symbol,Quantity,Price,Side";

    public OutputGenerator( String miniFileName, String fullOrderFileName ){
        this.miniFileName     = miniFileName;
        this.fullOrderFileName= fullOrderFileName;
    }


    public final void generate( List<RawTrade> trades ){
        generateMiniOrder( trades );
        generateFullOrder( trades );
    }


    protected final void generateMiniOrder( List<RawTrade> trades ){

        StringBuilder miniBuilder = new StringBuilder(16 * trades.size());
        miniBuilder.append(MINI_HEADER).append( NEWLINE );

        for( RawTrade trade : trades ){
            miniBuilder.append(trade.getBroker()).append(DELIMITER).append(trade.getSequenceId()).append(NEWLINE);
        }

        writeToFile(miniFileName, miniBuilder.toString());

    }


    protected final void generateFullOrder( List<RawTrade> trades ){

       StringBuilder fullBuilder  = new StringBuilder( 256 * trades.size() );
       fullBuilder.append( FULL_HEADER ).append(NEWLINE);

       for( RawTrade trade : trades ){
           fullBuilder.append( TradeSerde.serializeToJson(trade) ).append(NEWLINE );
        }

        writeToFile( fullOrderFileName, fullBuilder.toString() );

    }


    protected final void writeToFile( String fileName, String data ){
        ExcerciseUtil.writeDataToFile( fileName, data );
    }

}
