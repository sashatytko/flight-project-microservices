package com.pdp.redis;

public enum RedisPrefix {

	ORDER("order.");

	String prefix;

	RedisPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		return prefix;
	}
}
