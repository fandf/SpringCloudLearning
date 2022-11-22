package com.fandf.mongo.core.agg;

public final class Lookup {

    public final static String FROM = "from";
    public final static String LOCAL_FIELD = "localField";
    public final static String FOREIGN_FIELD = "foreignField";
    public final static String AS = "as";

    public String from;
    public String localField;
    public String foreignField;
    public String as;

    /**
     * constructor
     *
     * @param from         the collection in the same database to perform the join with.
     * @param localField   the field from the documents input to the $lookup stage.
     * @param foreignField the field from the documents in the from collection.
     * @param as           the name of the new array field to add to the input documents.
     */
    public Lookup(String from, String localField, String foreignField, String as) {
        this.from = from;
        this.localField = localField;
        this.foreignField = foreignField;
        this.as = as;
    }

}
