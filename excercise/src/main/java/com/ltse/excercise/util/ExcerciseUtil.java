package com.ltse.excercise.util;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

/**
 * Collection of helper methods and constants
 */


public final class ExcerciseUtil{

    private ExcerciseUtil( ){}

    public static final String NEWLINE = "\n";


    public static final List<String> loadDataFromFile(String fileName ){

        List<String> list   = null;
                                 
        try{            
            URI uri         = ExcerciseUtil.class.getClassLoader( ).getResource( fileName ).toURI( );            
            list            = Files.readAllLines( Paths.get( uri ) );

        }catch( URISyntaxException | IOException e ){
            throw new RuntimeException("FAILED to load file " + fileName );
        }
        
        return list;
    
    }


    public static final boolean writeDataToFile( String fileName, String data ){

        boolean writtenOk   = false;

        try{
            Path filePath   = Paths.get( fileName );
            Files.write( filePath, data.getBytes() );
            writtenOk       = true;

        }catch( IOException e ){
            throw new RuntimeException("FAILED to write file " + fileName );
        }

        return writtenOk;

    }



    public static final boolean isInvalidInteger( String number ){
        boolean isInvalid   = true;
        try{
            Integer.parseInt(number);
            isInvalid       = false;
        }catch (NumberFormatException e ){}

        return isInvalid;

    }


    public static final boolean isInvalidDouble( String number ){
        boolean isInvalid  = true;
        try{
            Double.parseDouble(number);
            isInvalid      = false;
        }catch (NumberFormatException e ){}

        return isInvalid;

    }


    public static final boolean isInvalid( String value ){
        return ( value == null || value.trim().isEmpty() );
    }

    
    public static final Set<String> SetFrom( String ... values ){
        Set<String> set = new HashSet<>( values.length );
        for( String value : values ){
            set.add( value );
        }
        
        return set;
    }

}
