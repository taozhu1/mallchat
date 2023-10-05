package com.abin.mallchat.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

import static com.abin.mallchat.common.common.config.ThreadPoolConfig.MALLCHAT_EXECUTOR;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ThreadPoolTests {

    @Qualifier(MALLCHAT_EXECUTOR)
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Test
    public void doTest() {
        Thread thread = new Thread(() -> {
            throw new RuntimeException("1==1");
        });

        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = (t, e) -> {
            log.error("Exception in thread ", e);
        };
        thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
        thread.start();

//        threadPoolTaskExecutor.execute(thread);
    }
}
