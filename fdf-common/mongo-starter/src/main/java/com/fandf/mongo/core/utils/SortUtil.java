package com.fandf.mongo.core.utils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public final class SortUtil {
    
    public static String asc(String key){
        return new StringBuilder().append("{").append(key).append(":1").append("}").toString();
    }
    
    public static String desc(String key){
        return new StringBuilder().append("{").append(key).append(":-1").append("}").toString();
    }

    /**
     * convert order string to DBObject.
     * @param jsonString
     * @return
     */
    public static DBObject getSort(String jsonString) {
        jsonString = jsonString.trim();
        if (!jsonString.startsWith("{")) {
            jsonString = "{" + jsonString;
        }
        if (!jsonString.endsWith("}")) {
            jsonString = jsonString + "}";
        }
        return BasicDBObject.parse(jsonString);
    }

}
