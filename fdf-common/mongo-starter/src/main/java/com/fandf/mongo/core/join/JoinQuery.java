package com.fandf.mongo.core.join;

import com.fandf.mongo.core.BaseAggregation;
import com.fandf.mongo.core.BaseDao;
import com.fandf.mongo.core.BaseQuery;
import com.fandf.mongo.core.agg.Lookup;
import com.fandf.mongo.core.annotations.Id;
import com.fandf.mongo.core.cache.FieldsCache;
import com.fandf.mongo.core.parallel.Parallelable;
import com.fandf.mongo.core.utils.MapperUtil;
import com.fandf.mongo.core.utils.Operator;
import com.fandf.mongo.core.utils.SortUtil;
import com.fandf.mongo.core.utils.StringUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class JoinQuery<L, R> implements Parallelable {

    protected final BaseDao<L> dao;

    protected String[] leftReturnFields;
    protected String[] rightReturnFields;
    protected String[] leftNotReturnFields;
    protected String[] rightNotReturnFields;

    protected String leftOrderBy;
    protected String rightOrderBy;
    protected int pageNumber;  //default value is zero
    protected int pageSize;  //default value is zero

    protected Class<R> rightColl;  //the right collection to join

    protected String leftKey;
    protected String rightKey;

    protected BaseQuery leftMatch;
    protected BaseQuery rightMatch;

    public JoinQuery(BaseDao<L> dao, Class<R> rightColl) {
        this.dao = dao;
        this.rightColl = rightColl;
    }

    public JoinQuery<L, R> keys(String leftKey, String rightKey) {
        if (StringUtil.isBlank(leftKey) || StringUtil.isBlank(rightKey)) {
            throw new IllegalArgumentException("Both leftKey and rightKey can NOT be null!!!");
        }
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        return this;
    }

    public JoinQuery<L, R> match(BaseQuery<L> leftMatch, BaseQuery<R> rightMatch) {
        this.leftMatch = leftMatch;
        this.rightMatch = rightMatch;
        return this;
    }

    public JoinQuery<L, R> returnFields(String[] leftReturnFields, String[] rightReturnFields) {
        this.leftReturnFields = leftReturnFields;
        this.rightReturnFields = rightReturnFields;
        return this;
    }

    public JoinQuery<L, R> notReturnFields(String[] leftNotReturnFields, String[] rightNotReturnFields) {
        this.leftNotReturnFields = leftNotReturnFields;
        this.rightNotReturnFields = rightNotReturnFields;
        return this;
    }

    public JoinQuery<L, R> returnLeftFieldsOnly() {
        List<String> columns = FieldsCache.getInstance().getAllColumnsName(dao.getEntityClass());
        this.leftReturnFields = columns.toArray(new String[0]);
        this.rightReturnFields = null;
        return this;
    }

    public JoinQuery<L, R> sort(String leftOrderBy, String rightOrderBy) {
        this.leftOrderBy = leftOrderBy;
        this.rightOrderBy = rightOrderBy;
        return this;
    }

    public JoinQuery<L, R> pageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public JoinQuery<L, R> pageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    @Override
    public List<JoinResult<L, R>> results() {
        BaseAggregation<L> agg = dao.aggregate();

        //match the left
        if (leftMatch != null) {
            agg.match(leftMatch);
        }

        //check left key
        Field leftField = FieldsCache.getInstance().getField(dao.getEntityClass(), leftKey);
        if (leftField != null) {
            Id leftId = leftField.getAnnotation(Id.class);
            if (leftId != null) {
                leftKey = Operator.ID;
            }
        }

        //check right key
        Field rightField = FieldsCache.getInstance().getField(rightColl, rightKey);
        if (rightField != null) {
            Id rightId = rightField.getAnnotation(Id.class);
            if (rightId != null) {
                rightKey = Operator.ID;
            }
        }

        //the as field
        Class<L> leftColl = dao.getEntityClass();
        String leftCollName = MapperUtil.getEntityName(leftColl);
        String rightCollName = MapperUtil.getEntityName(rightColl);
        String as = leftCollName + "_" + leftCollName.length() + "_" + rightCollName + "_" + rightCollName.length();  //make sure the as field does not exists

        //lookup
        agg.lookup(new Lookup(rightCollName, leftKey, rightKey, as));

        //unwind
        agg.unwindPreserveEmpty(as);

        //match the real right condition after lookup
        if (rightMatch != null) {
            DBObject cond = rightMatch.getCondition();
            DBObject realRightMatch = new BasicDBObject();
            Map map = cond.toMap();
            Set<Entry> set = map.entrySet();
            for (Entry entry : set) {
                String key = entry.getKey().toString();
                realRightMatch.put(as + "." + key, entry.getValue());
            }
            agg.match(realRightMatch);
        }

        //project retrun fields
        if (rightReturnFields == null) {
            if (leftReturnFields != null) {
                agg.projectInclude(leftReturnFields);
            }
        } else {
            int len = rightReturnFields.length;
            for (int i = 0; i < len; i++) {
                rightReturnFields[i] = as + "." + rightReturnFields[i];
            }
            if (leftReturnFields == null) {
                agg.projectInclude(rightReturnFields);
            } else {
                String[] returnFields = new String[leftReturnFields.length + rightReturnFields.length];
                System.arraycopy(leftReturnFields, 0, returnFields, 0, leftReturnFields.length);
                System.arraycopy(rightReturnFields, 0, returnFields, leftReturnFields.length, rightReturnFields.length);
                agg.projectInclude(returnFields);
            }
        }

        //project not return fields
        if (leftNotReturnFields != null) {
            agg.projectExclude(leftNotReturnFields);
        }
        if (rightNotReturnFields != null) {
            int len = rightNotReturnFields.length;
            for (int i = 0; i < len; i++) {
                rightNotReturnFields[i] = as + "." + rightNotReturnFields[i];
            }
            agg.projectExclude(rightNotReturnFields);
        }

        //sort the right
        if (rightOrderBy != null) {
            DBObject rightSort = SortUtil.getSort(rightOrderBy);
            DBObject realRightSort = new BasicDBObject();
            Map map = rightSort.toMap();
            Set<Entry> set = map.entrySet();
            for (Entry entry : set) {
                String key = entry.getKey().toString();
                realRightSort.put(as + "." + key, entry.getValue());
            }
            agg.sort(realRightSort);
        }

        //group and push
        List<String> columns = FieldsCache.getInstance().getAllColumnsName(leftColl);
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("_id:");
        sb.append("{"); //start of _id
        for (String col : columns) {
            sb.append(col).append(":").append("'$").append(col).append("',");
        }
        sb.deleteCharAt(sb.length() - 1); //delete the last comma(,)
        sb.append("}");  //end of _id
        sb.append(",");
        sb.append(as).append(":").append("{$push:'$").append(as).append("'}");
        sb.append("}");

        agg.group(sb.toString());

        //sort the left
        if (leftOrderBy != null) {
            DBObject leftSort = SortUtil.getSort(leftOrderBy);
            DBObject realLeftSort = new BasicDBObject();
            Map map = leftSort.toMap();
            Set<Entry> set = map.entrySet();
            for (Entry entry : set) {
                String key = entry.getKey().toString();
                realLeftSort.put("_id." + key, entry.getValue());
            }
            agg.sort(realLeftSort);
        }

        //skip and limit
        if (pageNumber > 0 && pageSize > 0) {
            agg.skip((pageNumber - 1) * pageSize).limit(pageSize);
        }

        //return JoinResult
        List<JoinResult<L, R>> list = new ArrayList<>();
        Iterable<DBObject> it = agg.results();
        for (DBObject dbo : it) {
            JoinResult<L, R> result = new JoinResult<>();
            DBObject _id = (DBObject) dbo.get("_id");
            L leftEntity = MapperUtil.fromDBObject(leftColl, _id);
            result.setLeftEntity(leftEntity);
            Object asArr = dbo.get(as);
            if (asArr != null) {
                Object arr = decodeArray(asArr);
                result.setRightEntity((R[]) arr);
            }
            list.add(result);
        }
        return list;

    }

    private Object decodeArray(Object val) {
        List list = (ArrayList) val;
        int size = list.size();
        Object arr = Array.newInstance(rightColl, size);
        for (int i = 0; i < size; i++) {
            Object item = list.get(i);
            if (item != null) {
                DBObject o = (DBObject) item;
                Array.set(arr, i, MapperUtil.fromDBObject(rightColl, o));
            } else {
                Array.set(arr, i, null);
            }
        }
        return arr;
    }

}
