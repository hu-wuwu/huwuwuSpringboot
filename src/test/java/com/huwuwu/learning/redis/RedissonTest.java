package com.huwuwu.learning.redis;
 
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;

import com.huwuwu.learning.LearningApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
 
/**
 *
 * @ClassName RedissonTest
 * @Description Redisson测试用例
 *
 */
@Slf4j
@SpringBootTest(classes = LearningApplication.class)
public class RedissonTest {
 
    @Resource
    private RedissonClient redissonClient;
 
    @Resource
    private ThreadPoolTaskExecutor executor;
 
    // redisson分布式锁的key
    private static final String LOCK_TEST_KEY = "redisson:lock:test";
 
    int n = 500;
 
    /**
     * 分布式锁测试用例
     */
    @Test
    public void lockTest1() {
        // 利用 循环+多线程 模仿高并发请求
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                // 这里获取公平锁，遵循先进先出原则，方便测试
                RLock fairLock = redissonClient.getFairLock(LOCK_TEST_KEY);
 
                try {
                    // 尝试加锁
                    // waitTimeout 尝试获取锁的最大等待时间，超过这个值，则认为获取锁失败
                    // leaseTime   锁的持有时间,超过这个时间锁会自动失效（值应设置为大于业务处理的时间，确保在锁有效期内业务能处理完）
                    boolean lock = fairLock.tryLock(3000, 30, TimeUnit.MILLISECONDS);
                    if (lock){
                        log.info("线程:" + Thread.currentThread().getName() + "获得了锁");
                        //业务处理
                        log.info("剩余数量:{}", --n);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
 
                } finally {
                    log.info("线程:" + Thread.currentThread().getName() + "准备释放锁");
                    // 注意，无论出现任何情况，都要主动解锁
                    fairLock.unlock();
                }
            });
        }
 
        try {
            // ->_-> 这里使当前方法占用的线程休息20秒，不要立即结束
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}