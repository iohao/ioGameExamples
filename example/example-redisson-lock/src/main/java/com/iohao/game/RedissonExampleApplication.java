package com.iohao.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
//开启线程池
@EnableAsync
public class RedissonExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedissonExampleApplication.class, args);

        //模拟多线程环境竞争锁
//        ExecutorsDistributeLock executorsDistributeLock = SpringUtil.getBean(ExecutorsDistributeLock.class);
//        executorsDistributeLock.testRlock();

        //使用Spring默认线程池，即@EnableAsync模拟的多线程环境竞争锁
//        SpringMultiThreadDistributeLock springMultiThreadDistributeLock = SpringUtil.getBean(SpringMultiThreadDistributeLock.class);

        //模拟多线程消费
//        springMultiThreadDistributeLock.testRlock();

        //模拟多线程tryLock
//        springMultiThreadDistributeLock.testTryLock();

        //模拟多线程lock
//        springMultiThreadDistributeLock.testLock();

    }
}
