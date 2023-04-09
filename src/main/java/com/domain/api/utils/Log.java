package com.domain.api.utils;

import org.apache.log4j.Logger;

/**
 * 日志
 */
public class Log {
    private static final Logger log = Logger.getLogger(Log.class.getName());

    public static void startTestCase(String name){
        log.info("-------------------------------开始执行---------------------------------------");
        log.info("*******************            "+name+"            *******************");
    }
    public static void endTestCase(String name){
        log.info("*******************            "+"执行结束"+"       *******************");
        log.info("-----------------------------------------------------------------------------");
    }
    public static void info(String message,Boolean flag){
        if (GlobalSettings.getProperty("log.info").equals("true")){
            log.info("******************* "+message+" *******************");
        }
    }
    public static void info(String message){
            log.info("******************* "+message+" *******************");
    }
    public static void warn(String message){
        log.warn(message);
    }
    public static void error(String message){
        log.error("******************* "+"\033[31;4m"+message+"\033[0m"+" *******************");
    }
    public static void fatal(String message){
        log.fatal("*******************            "+"\033[31;4m"+message+"\033[0m"+"     *******************");
    }
    public static void debug(String message){
        log.debug(message);
    }
}
