package com.ltse.excercise.data;


public final class RawTrade {

    private final String timestamp;
    private final String broker;
    private final String sequenceId;
    private final String type;
    private final String symbol;
    private final String quantity;
    private final String price;
    private final String side;


    public RawTrade( String timestamp, String broker, String sequenceId, String type, String symbol, String quantity, String price, String side ){
        this.timestamp  = timestamp;
        this.broker     = broker;
        this.sequenceId = sequenceId;
        this.type       = type;
        this.symbol     = symbol;
        this.quantity   = quantity;
        this.price      = price;
        this.side       = side;
    
    }


    public final String getTimestamp( ){
        return timestamp;
    }


    public final String getBroker( ){
        return broker;
    }


    public final String getSequenceId( ){
        return sequenceId;
    }


    public final String getType( ){
        return type;
    }


    public final String getSymbol( ){
        return symbol;
    }


    public final String getQuantity( ){
        return quantity;
    }


    public final String getPrice( ){
        return price;
    }


    public final String getSide( ){
        return side;
    }


    @Override
    public final String toString( ){
        
        StringBuilder builder = new StringBuilder(128 );        
        builder.append( "Trade [timestamp=" ).append( timestamp ).append( ", broker=" ).append( broker )
        .append( ", sequenceId=" ).append( sequenceId ).append( ", type=" ).append( type )
        .append( ", symbol=" ).append( symbol ).append( ", quantity=" ).append( quantity )
        .append( ", price=" ).append( price ).append( ", side=" ).append( side ).append( "]" );
        
        return builder.toString( );
    
    }
    
    
    
    

}
