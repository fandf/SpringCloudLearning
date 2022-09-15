package com.fandf.demo.eventbus;

import com.github.rholder.retry.*;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class HiEventBus {

    private static Callable<Boolean> dealEvent = new Callable<Boolean>() {
        @Override
        public Boolean call() throws Exception {
            return true;
        }
    };

    public static class EventBusListener {
        @Subscribe
        public void onEvent(CustomerChangedEvent event) throws ExecutionException, RetryException {
            Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                    // 出现异常时，会重试
                    .retryIfException()
                    // 失败后，隔2秒后重试
                    .withWaitStrategy(WaitStrategies.fixedWait(2, TimeUnit.SECONDS))
                    // 重试3次后，仍未成功，就不再重试
                    .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                    .build();
            dealEvent(event);
            retryer.call(dealEvent);
            System.out.println(event.getMessage());
        }

        private void dealEvent(CustomerChangedEvent event) {
        }

        @Subscribe
        public void onEvent2(CustomerChangedEvent event) {
            System.out.println(event.getMessage());
        }

    }

    public static class CustomerChangedEvent {

        public String getMessage() {
            return "change message";
        }
    }

    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        eventBus.register(new EventBusListener());
        eventBus.post(new CustomerChangedEvent());



    }
}