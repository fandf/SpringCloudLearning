package com.fandf.mongo.core.utils;

import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class StreamUtil {
    
    private static final Logger LOGGER = Logger.getLogger("com.fandf.mongo");
    
    public static void safeClose(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException ex) {
                LOGGER.log(Level.WARNING, "Error when close stream.", ex);
            }
        }
    }

}
