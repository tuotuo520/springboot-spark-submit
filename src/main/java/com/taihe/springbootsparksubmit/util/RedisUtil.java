package com.taihe.springbootsparksubmit.util;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.logging.log4j.spi.LoggerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @Description ：
 * @Author : Grayson
 * @Email : sunhui@yunliketech.com
 * @Date ：2020/6/28
 */
@Component
@CommonsLog
public class RedisUtil {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);
    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        Assert.notNull(key, "non null key required");
        try {
            return StringUtils.isEmpty(key) ? false : stringRedisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("Exception" + e);
            logger.info("记录用户 {} 摇号结果(MQ)：{}", key,key);
            return false;
        }
    }
}
