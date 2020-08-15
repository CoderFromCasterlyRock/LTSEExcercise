package com.ltse.excercise.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class ExcerciseLauncher {

    private final static Logger LOGGER	= LoggerFactory.getLogger( ExcerciseLauncher.class.getSimpleName() );

    static{
        Thread.setDefaultUncaughtExceptionHandler( new Thread.UncaughtExceptionHandler(){
            @Override
            public void uncaughtException( Thread thread, Throwable ex ){
                LOGGER.warn("CAUGHT unhandled exception in Thread [{}]", thread.getName() );
                LOGGER.warn("Exception: ", ex );
            }
        });
    }



    public static void main( String args [] ){
        new ExcerciseManager().process();
    }


}
