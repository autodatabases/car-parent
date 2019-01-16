package com.emate.shop.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class RedisUtil {
	
	public static boolean isRedisOk = false;

	private static RedisTemplate<String, Object> redisTemplate;

	/**
	 * TODO: 初始化RedisTemplate
	 */
	public static void initRedisTemplate(Environment environment) {
		
		if(Objects.nonNull(environment.getProperty("redis.host"))){
			
			Log4jHelper.getLogger().info("init redis template with: " + 
					environment.getProperty("redis.host")
			+ ":"+environment.getProperty("redis.port"));
			
			JedisPoolConfig poolConfig =  new JedisPoolConfig();
			poolConfig.setMaxIdle(Integer.parseInt(environment.getProperty("redis.maxIdle")));
			poolConfig.setMaxTotal(Integer.parseInt(environment.getProperty("redis.maxTotal")));
			poolConfig.setMaxWaitMillis(Long.parseLong(environment.getProperty("redis.maxWait")));
			poolConfig.setTestOnBorrow(true);
			
			JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
			connectionFactory.setPoolConfig(poolConfig);
			connectionFactory.setPort(Integer.parseInt(environment.getProperty("redis.port")));
			connectionFactory.setHostName(environment.getProperty("redis.host"));
			connectionFactory.setPassword(environment.getProperty("redis.pass"));
			connectionFactory.afterPropertiesSet();
			
			
			//jedisPool = new JedisPool(poolConfig,"",1);
			redisTemplate = new RedisTemplate<String,Object>();
			redisTemplate.setConnectionFactory(connectionFactory);
			
			StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
			JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
			redisTemplate.setKeySerializer(stringRedisSerializer);
			redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
			redisTemplate.setHashKeySerializer(stringRedisSerializer);
			redisTemplate.setHashValueSerializer(jdkSerializationRedisSerializer);
			redisTemplate.setEnableDefaultSerializer(false);
			redisTemplate.afterPropertiesSet();
			isRedisOk = true;
			Log4jHelper.getLogger().info("init redis template is ok. ");
		}else{
			Log4jHelper.getLogger().info("no redis config fond.");
		}
	}

	/**
	 * @param <T>
	 * 			@description 获取 redis中的对象 @param @return void @throws
	 */
	public static <T> T redisQueryObject(String key) {
		ValueOperations<String, Object> ops = redisTemplate.opsForValue();
		return (T) ops.get(key);
	}

	/**
	 * @description 删除redis中指定的key @param @return void @throws
	 */
	public static void redisDeleteKey(String key) {
		redisTemplate.delete(key);
	}

	/**
	 * @description 保存对象到redis @param key @return object @throws
	 */
	public static void redisSaveObject(String key, Object object) {
		ValueOperations<String, Object> ops = redisTemplate.opsForValue();
		ops.getAndSet(key, object);
	}

	/**
	 * @description 保存对象到redis @param key @return object @throws
	 */
	public static void redisSaveObject(String key, Object object, int time) {
		ValueOperations<String, Object> ops = redisTemplate.opsForValue();
		ops.set(key, object, time, TimeUnit.MINUTES);
	}

	/**
	 * 缓存List数据
	 * 
	 * @param key
	 *            缓存的键值
	 * @param dataList
	 *            待缓存的List数据
	 * @return 缓存的对象
	 */
	public static <T> ListOperations<String, T> setCacheList(String key, List<T> dataList) {
		ListOperations listOperation = redisTemplate.opsForList();
		if (null != dataList) {
			int size = dataList.size();
			for (int i = 0; i < size; i++) {

				listOperation.rightPush(key, dataList.get(i));
			}
		}

		return listOperation;
	}

	/**
	 * 获得缓存的list对象
	 * 
	 * @param key
	 *            缓存的键值
	 * @return 缓存键值对应的数据
	 */
	public static <T> List<T> getCacheList(String key) {
		List<T> dataList = new ArrayList<T>();
		ListOperations<String, Object> listOperation = redisTemplate.opsForList();
		Long size = listOperation.size(key);

		for (int i = 0; i < size; i++) {
			dataList.add((T) listOperation.leftPop(key));
		}

		return dataList;
	}

	/**
	 * 缓存Set
	 * 
	 * @param key
	 *            缓存键值
	 * @param dataSet
	 *            缓存的数据
	 * @return 缓存数据的对象
	 */
	public static <T> BoundSetOperations<String, T> setCacheSet(String key, Set<T> dataSet) {
		BoundSetOperations<String, Object> setOperation = redisTemplate.boundSetOps(key);
		/*
		 * T[] t = (T[]) dataSet.toArray(); setOperation.add(t);
		 */

		Iterator<T> it = dataSet.iterator();
		while (it.hasNext()) {
			setOperation.add(it.next());
		}

		return (BoundSetOperations<String, T>) setOperation;
	}

	/**
	 * 获得缓存的set
	 * 
	 * @param <T>
	 * @param key
	 * @param operation
	 * @return
	 */
	public static <T> Set<T> getCacheSet(
			String key/* ,BoundSetOperations<String,T> operation */) {
		Set<T> dataSet = new HashSet<T>();
		BoundSetOperations<String, Object> operation = redisTemplate.boundSetOps(key);

		Long size = operation.size();
		for (int i = 0; i < size; i++) {
			dataSet.add((T) operation.pop());
		}
		return dataSet;
	}

	/**
	 * 缓存Map
	 * 
	 * @param key
	 * @param dataMap
	 * @return
	 */
	public static <T> HashOperations<String, String, T> setCacheMap(String key, Map<String, T> dataMap) {

		HashOperations hashOperations = redisTemplate.opsForHash();
		if (null != dataMap) {

			for (Map.Entry<String, T> entry : dataMap.entrySet()) {

				/*
				 * System.out.println("Key = " + entry.getKey() + ", Value = " +
				 * entry.getValue());
				 */
				hashOperations.put(key, entry.getKey(), entry.getValue());
			}

		}

		return hashOperations;
	}

	/**
	 * 获得缓存的Map
	 * 
	 * @param key
	 * @param hashOperation
	 * @return
	 */
	public static <T> Map<Object, T> getCacheMap(
			String key/* ,HashOperations<String,String,T> hashOperation */) {
		Map<Object, T> map = (Map<Object, T>) redisTemplate.opsForHash().entries(key);
		/* Map<String, T> map = hashOperation.entries(key); */
		return map;
	}

	/**
	 * 缓存Map
	 * 
	 * @param key
	 * @param dataMap
	 * @return
	 */
	public static <T> HashOperations<String, Integer, T> setCacheIntegerMap(String key, Map<Integer, T> dataMap) {
		HashOperations hashOperations = redisTemplate.opsForHash();
		if (null != dataMap) {

			for (Map.Entry<Integer, T> entry : dataMap.entrySet()) {

				/*
				 * System.out.println("Key = " + entry.getKey() + ", Value = " +
				 * entry.getValue());
				 */
				hashOperations.put(key, entry.getKey(), entry.getValue());
			}

		}

		return hashOperations;
	}

	/**
	 * 获得缓存的Map
	 * 
	 * @param key
	 * @param hashOperation
	 * @return
	 */
	public static <T> Map<Object, T> getCacheIntegerMap(
			String key/* ,HashOperations<String,String,T> hashOperation */) {
		Map<Object, T> map = (Map<Object, T>) redisTemplate.opsForHash().entries(key);
		/* Map<String, T> map = hashOperation.entries(key); */
		return map;
	}
}