package com.wsc.framework.utils;


import java.awt.DefaultKeyboardFocusManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import bsh.This;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.SortingParams;

public class RedisOperate {
	Jedis jedis; 
	
	
	public void delkey(String key){
		
		jedis.del(key);
	}
	
	public void delallkey(){
		jedis.flushAll();
	}
	
	public void setkey(String key,String content){
		jedis.set(key,content);
	}
	
	public String getkey(String key){
		return jedis.get(key).toString();
	}

	
	public RedisOperate(String ipaddress,int port,int timeout){
		jedis=new Jedis(ipaddress, port, timeout);
	}
}
