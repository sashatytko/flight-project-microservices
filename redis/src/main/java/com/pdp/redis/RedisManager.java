package com.pdp.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.sync.RedisCommands;

public class RedisManager {
	private static RedisCommands<String, String> redisCommands;

	public static RedisCommands<String, String> getRedis() {
		if (redisCommands == null) {
			synchronized (RedisManager.class) {
				if (redisCommands == null) {
					initRedisClient();
				}
			}
		}
		return redisCommands;
	}

	private static void initRedisClient() {
		var redisClient = RedisClient.create("redis://localhost:6379");
		redisCommands = redisClient.connect().sync();
	}
}
