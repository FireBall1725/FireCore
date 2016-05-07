package com.fireball1725.firecore.common.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogHelper {
    private String modName;
    private Logger logger;

    public LogHelper(String modName) {
        this.modName = modName;
        this.logger = LogManager.getLogger(modName);
    }

    public void log(Level logLevel, String message) {
        this.logger.log(logLevel, "[" + modName + "] " + message);
    }

    public void all(String message) {
        log(Level.ALL, message);
    }

    public void debug(String message) {
        log(Level.DEBUG, message);
    }

    public void trace(String message) {
        log(Level.TRACE, message);
    }

    public void fatal(String message) {
        log(Level.FATAL, message);
    }

    public void error(String message) {
        log(Level.ERROR, message);
    }

    public void warn(String message) {
        log(Level.WARN, message);
    }

    public void info(String message) {
        log(Level.INFO, message);
    }

    public void off(String message) {
        log(Level.OFF, message);
    }

    public void internal(String message) {
        if (Platform.isDevEnv())
            log(Level.INFO, message);
    }
}
