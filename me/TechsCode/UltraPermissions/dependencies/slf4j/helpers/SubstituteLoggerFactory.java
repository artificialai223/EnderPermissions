

package me.TechsCode.EnderPermissions.dependencies.slf4j.helpers;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import me.TechsCode.EnderPermissions.dependencies.slf4j.Logger;
import java.util.concurrent.ConcurrentHashMap;
import me.TechsCode.EnderPermissions.dependencies.slf4j.event.SubstituteLoggingEvent;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.Map;
import me.TechsCode.EnderPermissions.dependencies.slf4j.ILoggerFactory;

public class SubstituteLoggerFactory implements ILoggerFactory
{
    volatile boolean postInitialization;
    final Map<String, SubstituteLogger> loggers;
    final LinkedBlockingQueue<SubstituteLoggingEvent> eventQueue;
    
    public SubstituteLoggerFactory() {
        this.postInitialization = false;
        this.loggers = new ConcurrentHashMap<String, SubstituteLogger>();
        this.eventQueue = new LinkedBlockingQueue<SubstituteLoggingEvent>();
    }
    
    @Override
    public synchronized Logger getLogger(final String s) {
        SubstituteLogger substituteLogger = this.loggers.get(s);
        if (substituteLogger == null) {
            substituteLogger = new SubstituteLogger(s, this.eventQueue, this.postInitialization);
            this.loggers.put(s, substituteLogger);
        }
        return substituteLogger;
    }
    
    public List<String> getLoggerNames() {
        return new ArrayList<String>(this.loggers.keySet());
    }
    
    public List<SubstituteLogger> getLoggers() {
        return new ArrayList<SubstituteLogger>(this.loggers.values());
    }
    
    public LinkedBlockingQueue<SubstituteLoggingEvent> getEventQueue() {
        return this.eventQueue;
    }
    
    public void postInitialization() {
        this.postInitialization = true;
    }
    
    public void clear() {
        this.loggers.clear();
        this.eventQueue.clear();
    }
}
