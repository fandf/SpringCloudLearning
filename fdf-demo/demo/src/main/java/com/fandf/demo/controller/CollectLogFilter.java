package com.fandf.demo.controller;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import com.google.common.collect.Sets;

import java.util.Set;

public class CollectLogFilter extends Filter<ILoggingEvent> {

    public static final String MARK = "[collector]";
    public static final Set<Level> FILTER_NOT_LEVELS = Sets.newHashSet(Level.INFO);

    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (event.getLoggerName().contains(MARK) && !FILTER_NOT_LEVELS.contains(event.getLevel())) {
            return FilterReply.ACCEPT;
        } else {
            return FilterReply.DENY;
        }
    }
}