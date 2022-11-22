package com.fandf.mongo.core.utils;

public final class DataType {

    public static boolean isString(Class type) {
        return type.equals(String.class);
    }

    public static boolean isInteger(Class type) {
        return type.equals(int.class);
    }

    public static boolean isIntegerObject(Class type) {
        return type.equals(Integer.class);
    }

    public static boolean isLong(Class type) {
        return type.equals(long.class);
    }

    public static boolean isLongObject(Class type) {
        return type.equals(Long.class);
    }

    public static boolean isShort(Class type) {
        return type.equals(short.class);
    }

    public static boolean isShortObject(Class type) {
        return type.equals(Short.class);
    }

    public static boolean isByte(Class type) {
        return type.equals(byte.class);
    }

    public static boolean isByteObject(Class type) {
        return type.equals(Byte.class);
    }

    public static boolean isFloat(Class type) {
        return type.equals(float.class);
    }

    public static boolean isFloatObject(Class type) {
        return type.equals(Float.class);
    }

    public static boolean isDouble(Class type) {
        return type.equals(double.class);
    }

    public static boolean isDoubleObject(Class type) {
        return type.equals(Double.class);
    }

    public static boolean isBoolean(Class type) {
        return type.equals(boolean.class);
    }

    public static boolean isBooleanObject(Class type) {
        return type.equals(Boolean.class);
    }

    public static boolean isChar(Class type) {
        return type.equals(char.class);
    }

    public static boolean isCharObject(Class type) {
        return type.equals(Character.class);
    }

    public static boolean isDate(Class type) {
        return type.equals(java.util.Date.class);
    }

    public static boolean isTimestamp(Class type) {
        return type.equals(java.sql.Timestamp.class);
    }

    public static boolean isBigDecimal(Class type) {
        return type.equals(java.math.BigDecimal.class);
    }

    /**
     * Only List and ArrayList are supported now.
     *
     * @param type
     * @return
     */
    public static boolean isListType(Class type) {
        return type.equals(java.util.List.class) || type.equals(java.util.ArrayList.class);
    }

    /**
     * Only Set and HashSet supported now.
     *
     * @param type
     * @return
     */
    public static boolean isSetType(Class type) {
        return type.equals(java.util.Set.class) || type.equals(java.util.HashSet.class);
    }

    /**
     * Only Map and HashMap are supported now.
     *
     * @param type
     * @return
     */
    public static boolean isMapType(Class type) {
        return type.equals(java.util.Map.class) || type.equals(java.util.HashMap.class);
    }

    /**
     * Only Queue, Deque and LinkedList is supported now.
     *
     * @param type
     * @return
     */
    public static boolean isQueueType(Class type) {
        return type.equals(java.util.Queue.class) || type.equals(java.util.Deque.class) || type.equals(java.util.LinkedList.class);
    }

    /**
     * Only Collection is supported now.
     *
     * @param type
     * @return
     */
    public static boolean isCollectionType(Class type) {
        return type.equals(java.util.Collection.class);
    }

}
