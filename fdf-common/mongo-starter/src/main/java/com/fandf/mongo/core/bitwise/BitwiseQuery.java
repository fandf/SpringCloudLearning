package com.fandf.mongo.core.bitwise;

import com.fandf.mongo.core.BaseDao;
import com.fandf.mongo.core.BaseQuery;
import com.fandf.mongo.core.utils.Operator;

public class BitwiseQuery<T> extends BaseQuery<T> {

    public BitwiseQuery(BaseDao<T> dao) {
        super(dao);
    }

    public BitwiseQuery bitsAllSet(String key, int mask) {
        append(key, Operator.BITS_ALL_SET, mask);
        return this;
    }

    public BitwiseQuery bitsAllSet(String key, int[] position) {
        append(key, Operator.BITS_ALL_SET, position);
        return this;
    }

    public BitwiseQuery bitsAnySet(String key, int mask) {
        append(key, Operator.BITS_ANY_SET, mask);
        return this;
    }

    public BitwiseQuery bitsAnySet(String key, int[] position) {
        append(key, Operator.BITS_ANY_SET, position);
        return this;
    }

    public BitwiseQuery bitsAllClear(String key, int mask) {
        append(key, Operator.BITS_ALL_CLEAR, mask);
        return this;
    }

    public BitwiseQuery bitsAllClear(String key, int[] position) {
        append(key, Operator.BITS_ALL_CLEAR, position);
        return this;
    }

    public BitwiseQuery bitsAnyClear(String key, int mask) {
        append(key, Operator.BITS_ANY_CLEAR, mask);
        return this;
    }

    public BitwiseQuery bitsAnyClear(String key, int[] position) {
        append(key, Operator.BITS_ANY_CLEAR, position);
        return this;
    }

}
