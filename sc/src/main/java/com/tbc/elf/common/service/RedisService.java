package com.tbc.elf.common.service;

import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCommands;

import javax.annotation.Resource;

/**
 * Redis based service class, all be sure to use the service to the operation of the Redis class
 *
 * @author ELF@TEAM
 * @since 2016-2-24 14:33:29
 */
@Service
public class RedisService {
    @Resource
    private JedisCommands jedisCommands;

    /**
     * Set the string value as value of the key. The string can't be longer than 1073741824 bytes (1GB).
     * <p/>
     * Time complexity: O(1)
     *
     * @param key   the Key
     * @param value the value
     * @return Status code reply
     */
    public String set(String key, String value) {
        return jedisCommands.set(key, value);
    }

    /**
     * Get the value of the specified key. If the key does not exist null is returned. If the value
     * stored at key is not a string an error is returned because GET can only handle string values.
     * <p/>
     * Time complexity: O(1)
     *
     * @param key the Key
     * @return Bulk reply
     */
    public String get(String key) {
        return jedisCommands.get(key);
    }
}
