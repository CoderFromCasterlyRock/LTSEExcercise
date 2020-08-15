package com.ltse.excercise.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Collection of helper methods and constants
 */


public final class ExcerciseUtil{

    private ExcerciseUtil( ){}

    public static final String NEWLINE = "\n";
    private static final Logger LOGGER = LoggerFactory.getLogger( ExcerciseUtil.class.getSimpleName() );


    public static List<String> loadDataFromFile( String fileName ){

        List<String> list       = new ArrayList<>();

        try( InputStream in = ExcerciseUtil.class.getClassLoader().getResourceAsStream(fileName) ){
            try( BufferedReader reader = new BufferedReader(new InputStreamReader(in)) ){
                String line;
                while( (line = reader.readLine()) != null ){
                    list.add( line );
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to load file from " + fileName );
        }

        return list;

    }


    public static void writeDataToFile( String fileName, String data ){

        try{
            Path filePath   = Paths.get( fileName );
            Files.write( filePath, data.getBytes() );
            LOGGER.info("Successfully generated output file at [{}]", filePath.toAbsolutePath() );

        }catch( IOException e ){
            throw new RuntimeException("FAILED to write file " + fileName );
        }

    }

    
    public static boolean isInvalidInteger( String number ){
        boolean isInvalid   = true;
        try{
            Integer.parseInt(number);
            isInvalid       = false;
        }catch (NumberFormatException e ){}

        return isInvalid;

    }


    public static boolean isInvalidDouble( String number ){
        boolean isInvalid  = true;
        try{
            Double.parseDouble(number);
            isInvalid      = false;
        }catch (NumberFormatException e ){}

        return isInvalid;

    }


    public static boolean isInvalid( String value ){
        return ( value == null || value.trim().isEmpty() );
    }

    
    public static Set<String> SetFrom( String ... values ){
        Set<String> set = new HashSet<>( values.length );
        for( String value : values ){
            set.add( value );
        }
        
        return set;
    }

}
