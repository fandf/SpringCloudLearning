//package com.fandf.demo.dataarchive.api;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.*;
//
//public class FutureDemo {
//
//    public static void main(String[] args) throws InterruptedException {
//        // 存放异步返回结果的List
//        List<FutureTask<Integer>> list = new ArrayList<>();
//        // 线程池
//        ExecutorService executor = new ThreadPoolExecutor(5,10,10, TimeUnit.SECONDS,new ArrayBlockingQueue<>(5));
//
//        final List<String> results = new ArrayList<String>();
//
//
//        try{
//            // 弄5个线程，其中第二个线程的时候，让线程调用回调函数，停止主引用
//            Task task = new Task(10, new Callback() {
//                @Override
//                public void callbackmsq(String callback) throws Exception {
//                    if(callback != null ){
//                        executor.shutdown();
//                    }
//                }
//            });
//            FutureTask<Integer> futureTask = new FutureTask<Integer>(task);
//            executor.submit(futureTask);
//            list.add(futureTask);
//
//            Thread.sleep(1000);
//            Task task1 = new Task(11, new Callback() {
//                @Override
//                public void callbackmsq(String callback) throws Exception {
//                    if(callback != null ){
//                        results.add("程序停了") ;
//                        executor.shutdown();
//                    }
//                }
//            });
//            FutureTask<Integer> futureTask1 = new FutureTask<Integer>(task1);
//            executor.submit(futureTask1);
//            list.add(futureTask1);
//
//            Thread.sleep(1000);
//            Task task2 = new Task(12, new Callback() {
//                @Override
//                public void callbackmsq(String callback) throws Exception{
//                    if(callback != null ){
//                        results.add("程序停了") ;
//                        executor.shutdown();
//                    }
//                }
//            });
//            FutureTask<Integer> futureTask2 = new FutureTask<Integer>(task2);
//            executor.submit(futureTask2);
//            list.add(futureTask2);
//
//            Thread.sleep(1000);
//            Task task3 = new Task(13, new Callback() {
//                @Override
//                public void callbackmsq(String callback) throws Exception{
//                    if(callback != null ){
//                        results.add("程序停了") ;
//                        executor.shutdown();
//                    }
//                }
//            });
//            FutureTask<Integer> futureTask3 = new FutureTask<Integer>(task3);
//            executor.submit(futureTask3);
//            list.add(futureTask3);
//
//            Thread.sleep(1000);
//            Task task4 = new Task(14, new Callback() {
//                @Override
//                public void callbackmsq(String callback) throws Exception{
//                    if(callback != null ){
//                        results.add("程序停了") ;
//                        executor.shutdown();
//                    }
//                }
//            });
//            FutureTask<Integer> futureTask4 = new FutureTask<Integer>(task4);
//            executor.submit(futureTask4);
//            list.add(futureTask4);
//
//            executor.shutdown();
//        }catch (Exception e){
//            e.printStackTrace();
//            System.out.println("异步执行结果" + results);
//        }
//
//
//        try {
//            for(FutureTask<Integer> item:list){
//                System.out.println( "task运行结果" +item.get());
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println( "所有任务执行完毕" );
//    }
//
//}
//
