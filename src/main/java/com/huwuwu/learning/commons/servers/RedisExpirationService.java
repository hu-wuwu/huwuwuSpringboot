package com.huwuwu.learning.commons.servers;

import com.huwuwu.learning.commons.utils.RedisUtil;
import com.huwuwu.learning.commons.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisExpirationService {

    @Autowired
    private RedisUtil redisUtil;

    public void processingExpiredKey(String expiredKey) {
        // 如果是优惠券的key(一定要规范命名)
        if (expiredKey.startsWith("...")) {
            // 临时key,此key可以在业务处理完，然后延迟一定时间删除，或者不处理
            String tempKey = SignUtils.md5(expiredKey, "UTF-8");
            // 临时key不存在才设置值，key超时时间为10秒（此处相当于分布式锁的应用）
            Boolean exist = redisUtil.setIfAbsent(tempKey, "...", 10, TimeUnit.SECONDS);
            if (Boolean.TRUE.equals(exist)) {
                log.info("Business Handing...");

            } else {
                log.info("Other service is handing...");
            }
        } else {
            log.info("Expired keys without processing");
        }
    }

}
