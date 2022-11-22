package com.fandf.mongo.core.join;


public class JoinResult<L, R> {

    private L leftEntity;
    private R[] rightEntity;

    public void setLeftEntity(L leftEntity) {
        this.leftEntity = leftEntity;
    }

    public void setRightEntity(R[] rightEntity) {
        this.rightEntity = rightEntity;
    }

    public L getLeftEntity() {
        return leftEntity;
    }

    public R[] getRightEntity() {
        return rightEntity;
    }

}
