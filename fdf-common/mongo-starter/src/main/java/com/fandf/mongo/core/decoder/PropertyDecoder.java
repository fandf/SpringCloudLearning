package com.fandf.mongo.core.decoder;

import com.fandf.mongo.core.annotations.Default;
import com.fandf.mongo.core.annotations.Property;
import com.fandf.mongo.core.exception.BaseException;
import com.fandf.mongo.core.utils.DataType;
import com.fandf.mongo.core.utils.FieldUtil;
import com.mongodb.DBObject;
import org.bson.types.Decimal128;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.Map.Entry;

@SuppressWarnings("unchecked")
public class PropertyDecoder extends AbstractDecoder {

    public PropertyDecoder(Field field, DBObject dbo) {
        super(field);
        String fieldName = field.getName();
        Property property = field.getAnnotation(Property.class);
        if (property != null) {
            String name = property.name();
            if (!name.equals(Default.NAME)) {
                fieldName = name;
            }
        }
        value = dbo.get(fieldName);
    }

    @Override
    public void decode(Object obj) {
        Class<?> type = field.getType();
        try {
            if (type.isArray()) {
                decodeArray(obj, type.getComponentType());
            } else if (type.isEnum()) {
                decodeEnum(obj, (Class<Enum>) type);
            } else {
                decodePrimitive(obj, type);
            }
        } catch (IllegalAccessException | IllegalArgumentException ex) {
            throw new BaseException(ex.getMessage());
        }
    }

    private void decodeArray(Object obj, Class comType) throws IllegalArgumentException, IllegalAccessException {
        if (DataType.isByte(comType)) {
            field.set(obj, (byte[]) value);
        } else {
            Object arr = convertToArrayValue(comType, (ArrayList) value);
            field.set(obj, arr);
        }
    }

    private void decodeEnum(Object obj, Class<Enum> type) throws IllegalArgumentException, IllegalAccessException {
        field.set(obj, Enum.valueOf(type, (String) value));
    }

    private void decodePrimitive(Object obj, Class type) throws IllegalArgumentException, IllegalAccessException {
        //When value is number, it's default to Double and Integer, must cast to Float, Short and byte.
        //It's OK to set integer value to long field.
        if (DataType.isFloat(type)) {
            field.setFloat(obj, Float.parseFloat(value.toString()));
        } else if (DataType.isFloatObject(type)) {
            field.set(obj, Float.valueOf(value.toString()));
        } else if (DataType.isShort(type)) {
            field.setShort(obj, Short.parseShort(value.toString()));
        } else if (DataType.isShortObject(type)) {
            field.set(obj, Short.valueOf(value.toString()));
        } else if (DataType.isByte(type)) {
            field.setByte(obj, Byte.parseByte(value.toString()));
        } else if (DataType.isByteObject(type)) {
            field.set(obj, Byte.valueOf(value.toString()));
        }
        //process List and Collection.
        else if (DataType.isListType(type) || DataType.isCollectionType(type)) {
            List src = (ArrayList) value;
            List list = new ArrayList();
            processCollection(src, list);
            field.set(obj, list);
        }
        //convert for Set. default type is com.mongodb.BasicDBList(extends ArrayList)
        else if (DataType.isSetType(type)) {
            List src = (ArrayList) value;
            Set set = new HashSet();
            processCollection(src, set);
            field.set(obj, set);
        }
        //convert for Queue. default type is com.mongodb.BasicDBList(extends ArrayList)
        else if (DataType.isQueueType(type)) {
            List src = (ArrayList) value;
            Queue queue = new LinkedList();
            processCollection(src, queue);
            field.set(obj, queue);
        }
        //process Map.
        else if (DataType.isMapType(type)) {
            processMapType(obj);
        }
        //convert for char. default type is String "X"
        else if (DataType.isChar(type)) {
            field.setChar(obj, value.toString().charAt(0));
        }
        //convert for Timestamp. default type is Date
        else if (DataType.isTimestamp(type)) {
            Date date = (Date) value;
            Timestamp ts = new Timestamp(date.getTime());
            field.set(obj, ts);
        }
        //convert for BigDecimal. defualt type is Decimal128
        else if (DataType.isBigDecimal(type)) {
            Decimal128 decimal = (Decimal128) value;
            field.set(obj, decimal.bigDecimalValue());
        } else {
            field.set(obj, value);  //for others: String, int/Integer, long/Long, double/Double, boolean/Boolean and Date
        }
    }

    private void processMapType(Object obj) throws IllegalArgumentException, IllegalAccessException {
        //for Map<K,V>, first to check the type of V
        ParameterizedType paramType = (ParameterizedType) field.getGenericType();
        Type[] types = paramType.getActualTypeArguments();
        //3 different types of V
        boolean isArray = false;
        boolean isCollection = false;
        boolean isPrimitive = false;
        Class vType = null;
        Class elementType = null;
        //in JDK6, type[1] of array, is instanceof GenericArrayType
        if (types[1] instanceof GenericArrayType) {
            isArray = true;
            GenericArrayType g = (GenericArrayType) types[1];
            elementType = (Class) g.getGenericComponentType();
        } else if (types[1] instanceof ParameterizedType) {
            isCollection = true;
            ParameterizedType p = (ParameterizedType) types[1];
            vType = (Class) p.getRawType();
            elementType = (Class) p.getActualTypeArguments()[0];
        } else {
            //in JDK8, type[1] of array, is a class, not array
            Class<?> actualType = FieldUtil.getClassOfType(types[1]);
            if (actualType.isArray()) {
                isArray = true;
                elementType = actualType.getComponentType();
            } else {
                isPrimitive = true;
            }
        }
        //decode value by different type of V
        if (isArray) {
            Map src = (Map) value;
            Map map = new HashMap();
            Set<Entry> entrySet = src.entrySet();
            for (Entry entry : entrySet) {
                Object k = entry.getKey();
                List v = (ArrayList) entry.getValue();
                Object arr = convertToArrayValue(elementType, v);
                map.put(k, arr);
            }
            field.set(obj, map);
        } else if (isCollection) {
            Map src = (Map) value;
            Map map = new HashMap();
            Set<Entry> entrySet = src.entrySet();
            if (DataType.isListType(vType) || DataType.isCollectionType(vType)) {
                for (Entry entry : entrySet) {
                    Object k = entry.getKey();
                    List v = (ArrayList) entry.getValue();
                    List list = new ArrayList();
                    moveCollectionElement(elementType, v, list);
                    map.put(k, list);
                }
            } else if (DataType.isSetType(vType)) {
                for (Entry entry : entrySet) {
                    Object k = entry.getKey();
                    List v = (ArrayList) entry.getValue();
                    Set set = new HashSet();
                    moveCollectionElement(elementType, v, set);
                    map.put(k, set);
                }
            } else if (DataType.isQueueType(vType)) {
                for (Entry entry : entrySet) {
                    Object k = entry.getKey();
                    List v = (ArrayList) entry.getValue();
                    Queue queue = new LinkedList();
                    moveCollectionElement(elementType, v, queue);
                    map.put(k, queue);
                }
            }
            field.set(obj, map);
        } else if (isPrimitive) {
            //if the V is privitive, the map has no need to convert.
            //whatever the V is Short, Float, BigDecimal or others.
            field.set(obj, value);
        }
    }

    private Object convertToArrayValue(Class comType, List val) {
        int size = val.size();
        if (comType.isArray()) {
            //if each element is still an arry
            Object arr = Array.newInstance(comType, size);
            Class subType = comType.getComponentType();
            for (int i = 0; i < size; i++) {
                Object item = val.get(i);
                Object result = convertToArrayValue(subType, (ArrayList) item);
                Array.set(arr, i, result);
            }
            return arr;
        } else if (DataType.isString(comType)) {
            String[] arr = new String[size];
            for (int i = 0; i < size; i++) {
                arr[i] = val.get(i).toString();
            }
            return arr;
        } else if (DataType.isInteger(comType)) {
            int[] arr = new int[size];
            for (int i = 0; i < size; i++) {
                arr[i] = Integer.parseInt(val.get(i).toString());
            }
            return arr;
        } else if (DataType.isIntegerObject(comType)) {
            Integer[] arr = new Integer[size];
            for (int i = 0; i < size; i++) {
                arr[i] = Integer.valueOf(val.get(i).toString());
            }
            return arr;
        } else if (DataType.isLong(comType)) {
            long[] arr = new long[size];
            for (int i = 0; i < size; i++) {
                arr[i] = Long.parseLong(val.get(i).toString());
            }
            return arr;
        } else if (DataType.isLongObject(comType)) {
            Long[] arr = new Long[size];
            for (int i = 0; i < size; i++) {
                arr[i] = Long.valueOf(val.get(i).toString());
            }
            return arr;
        } else if (DataType.isShort(comType)) {
            short[] arr = new short[size];
            for (int i = 0; i < size; i++) {
                arr[i] = Short.parseShort(val.get(i).toString());
            }
            return arr;
        } else if (DataType.isShortObject(comType)) {
            Short[] arr = new Short[size];
            for (int i = 0; i < size; i++) {
                arr[i] = Short.valueOf(val.get(i).toString());
            }
            return arr;
        } else if (DataType.isByteObject(comType)) {
            Byte[] arr = new Byte[size];
            for (int i = 0; i < size; i++) {
                arr[i] = Byte.valueOf(val.get(i).toString());
            }
            return arr;
        } else if (DataType.isFloat(comType)) {
            float[] arr = new float[size];
            for (int i = 0; i < size; i++) {
                arr[i] = Float.parseFloat(val.get(i).toString());
            }
            return arr;
        } else if (DataType.isFloatObject(comType)) {
            Float[] arr = new Float[size];
            for (int i = 0; i < size; i++) {
                arr[i] = Float.valueOf(val.get(i).toString());
            }
            return arr;
        } else if (DataType.isDouble(comType)) {
            double[] arr = new double[size];
            for (int i = 0; i < size; i++) {
                arr[i] = Double.parseDouble(val.get(i).toString());
            }
            return arr;
        } else if (DataType.isDoubleObject(comType)) {
            Double[] arr = new Double[size];
            for (int i = 0; i < size; i++) {
                arr[i] = Double.valueOf(val.get(i).toString());
            }
            return arr;
        } else if (DataType.isBoolean(comType)) {
            boolean[] arr = new boolean[size];
            for (int i = 0; i < size; i++) {
                arr[i] = Boolean.parseBoolean(val.get(i).toString());
            }
            return arr;
        } else if (DataType.isBooleanObject(comType)) {
            Boolean[] arr = new Boolean[size];
            for (int i = 0; i < size; i++) {
                arr[i] = Boolean.valueOf(val.get(i).toString());
            }
            return arr;
        } else if (DataType.isChar(comType)) {
            char[] arr = new char[size];
            for (int i = 0; i < size; i++) {
                arr[i] = val.get(i).toString().charAt(0);
            }
            return arr;
        } else if (DataType.isCharObject(comType)) {
            Character[] arr = new Character[size];
            for (int i = 0; i < size; i++) {
                arr[i] = val.get(i).toString().charAt(0);
            }
            return arr;
        } else if (DataType.isDate(comType)) {
            Date[] arr = new Date[size];
            for (int i = 0; i < size; i++) {
                arr[i] = (Date) val.get(i);
            }
            return arr;
        } else if (DataType.isTimestamp(comType)) {
            Timestamp[] arr = new Timestamp[size];
            for (int i = 0; i < size; i++) {
                arr[i] = (Timestamp) val.get(i);
            }
            return arr;
        } else if (DataType.isBigDecimal(comType)) {
            BigDecimal[] arr = new BigDecimal[size];
            for (int i = 0; i < size; i++) {
                Decimal128 decimal = (Decimal128) val.get(i);
                arr[i] = decimal.bigDecimalValue();
            }
            return arr;
        } else {
            return null;
        }
    }

    private void processCollection(List src, Collection target) {
        //for List<T>, first to check the type of T
        ParameterizedType paramType = (ParameterizedType) field.getGenericType();
        Type[] types = paramType.getActualTypeArguments();
        //3 different types of T
        boolean isArray = false;
        boolean isCollection = false;
        boolean isPrimitive = false;
        Class tType = null;
        Class elementType = null;
        //in JDK 6, type[0] of array, is instanceof GenericArrayType
        if (types[0] instanceof GenericArrayType) {
            isArray = true;
            GenericArrayType g = (GenericArrayType) types[0];
            elementType = (Class) g.getGenericComponentType();
        } else if (types[0] instanceof ParameterizedType) {
            isCollection = true;
            ParameterizedType p = (ParameterizedType) types[0];
            tType = (Class) p.getRawType();
            elementType = (Class) p.getActualTypeArguments()[0];
        } else {
            //in JDK8, type[0] of array, is a class, not array
            Class<?> actualType = FieldUtil.getClassOfType(types[0]);
            if (actualType.isArray()) {
                isArray = true;
                elementType = actualType.getComponentType();
            } else {
                isPrimitive = true;
            }
        }
        //decode value by different type of T
        if (isArray) {
            //each element of collection is array
            List temp = new ArrayList();
            for (Object o : src) {
                List item = (ArrayList) o;
                Object arr = convertToArrayValue(elementType, item);
                temp.add(arr);
            }
            for (Object o : temp) {
                target.add(o);
            }
        } else if (isCollection) {
            //each element of collection is still a collection
            List temp = new ArrayList();
            if (DataType.isListType(tType) || DataType.isCollectionType(tType)) {
                for (Object o : src) {
                    List item = (ArrayList) o;
                    List list = new ArrayList();
                    moveCollectionElement(elementType, item, list);
                    temp.add(list);
                }
            } else if (DataType.isSetType(tType)) {
                for (Object o : src) {
                    List item = (ArrayList) o;
                    Set set = new HashSet();
                    moveCollectionElement(elementType, item, set);
                    temp.add(set);
                }
            } else if (DataType.isQueueType(tType)) {
                for (Object o : src) {
                    List item = (ArrayList) o;
                    Queue queue = new LinkedList();
                    moveCollectionElement(elementType, item, queue);
                    temp.add(queue);
                }
            }
            //
            for (Object o : temp) {
                target.add(o);
            }
        } else if (isPrimitive) {
            //each element of collection is primitive
            Class actualType = (Class) types[0];
            moveCollectionElement(actualType, src, target);
        }
    }

    private void moveCollectionElement(Class actualType, List src, Collection target) {
        if (DataType.isShortObject(actualType)) {
            for (Object o : src) {
                target.add(Short.valueOf(o.toString()));
            }
        } else if (DataType.isByteObject(actualType)) {
            for (Object o : src) {
                target.add(Byte.valueOf(o.toString()));
            }
        } else if (DataType.isFloatObject(actualType)) {
            for (Object o : src) {
                target.add(Float.valueOf(o.toString()));
            }
        } else if (DataType.isCharObject(actualType)) {
            for (Object o : src) {
                target.add(o.toString().charAt(0));
            }
        } else if (DataType.isBigDecimal(actualType)) {
            for (Object o : src) {
                Decimal128 decimal = (Decimal128) o;
                target.add(decimal.bigDecimalValue());
            }
        } else {
            for (Object o : src) {
                target.add(o);
            }
        }
    }

}
