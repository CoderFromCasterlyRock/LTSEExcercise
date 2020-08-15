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


    public static final List<String> loadDataFromFile( String fileName ){

        InputStream instream    = null;
        BufferedReader bstream  = null;
        List<String> list       = new ArrayList<>();

        try{

            instream            = ExcerciseUtil.class.getClassLoader( ).getResourceAsStream( "/" + fileName);
            if( instream == null ){
                instream        = ExcerciseUtil.class.getClassLoader().getResourceAsStream( fileName );
            }

            String line         = null;
            bstream             = new BufferedReader(new InputStreamReader(instream) );
            while( (line = bstream.readLine() ) != null ){
                list.add( line );
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to load file from " + fileName );

        }finally{
            try {
                if( instream != null ) instream.close();
            } catch (IOException e) {}

            try {
                if( bstream != null ) bstream.close();
            } catch (IOException e) {}

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
