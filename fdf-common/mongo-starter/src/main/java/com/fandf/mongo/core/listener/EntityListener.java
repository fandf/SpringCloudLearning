package com.fandf.mongo.core.listener;


import com.fandf.mongo.core.BaseEntity;

public interface EntityListener {

    /**
     * Notified that an entity has been inserted.
     *
     * @param entity the inserted object
     */
    public void entityInserted(BaseEntity entity);

    /**
     * Notified that an entity has been updated.
     *
     * @param entity the updated object
     */
    public void entityUpdated(BaseEntity entity);

    /**
     * Notified that an entity has been deleted.
     *
     * @param entity the deleted object
     */
    public void entityDeleted(BaseEntity entity);

}
