package com.fandf.mongo.core.parallel;

import java.util.concurrent.Callable;

public class ParallelTask implements Callable<Iterable> {
    
    private Parallelable query;
    
    public ParallelTask(Parallelable query){
        this.query = query;
    }

    @Override
    public Iterable call() throws Exception {
        return query.results();
    }
    
}
