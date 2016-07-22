package com.john.utils;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * Redis distributed lock implementation.
 * 
 * @author Alois Belaska <alois.belaska@gmail.com>
 */
public class JedisLock {

	private RedisTemplate<String, String> redisTemplate;

	/**
	 * Lock key path.
	 */
	private String lockKey;

	/**
	 * Lock expiration in miliseconds.
	 */
	private int expireMsecs = 60 * 1000;

	/**
	 * Acquire timeout in miliseconds.
	 */
	private int timeoutMsecs = 10 * 1000;

	private boolean locked = false;

	private int parentHashCode = 0;

	/**
	 * Detailed constructor with default acquire timeout 10000 msecs and lock expiration of 60000 msecs.
	 * 
	 * @param jedis
	 * @param lockKey
	 *            lock key (ex. account:1, ...)
	 */
	public JedisLock(RedisTemplate<String, String> redisTemplate, String lockKey) {
		this.redisTemplate = redisTemplate;
		this.lockKey = lockKey;
	}

	/**
	 * Detailed constructor with default lock expiration of 60000 msecs.
	 * 
	 * @param jedis
	 * @param lockKey
	 *            lock key (ex. account:1, ...)
	 * @param timeoutSecs
	 *            acquire timeout in miliseconds (default: 10000 msecs)
	 */
	public JedisLock(RedisTemplate<String, String> redisTemplate, String lockKey, int timeoutMsecs) {
		this(redisTemplate, lockKey);
		this.timeoutMsecs = timeoutMsecs;
	}

	/**
	 * Detailed constructor.
	 * 
	 * @param jedis
	 * @param lockKey
	 *            lock key (ex. account:1, ...)
	 * @param timeoutSecs
	 *            acquire timeout in miliseconds (default: 10000 msecs)
	 * @param expireMsecs
	 *            lock expiration in miliseconds (default: 60000 msecs)
	 */
	public JedisLock(RedisTemplate<String, String> redisTemplate, String lockKey, int timeoutMsecs, int expireMsecs) {
		this(redisTemplate, lockKey, timeoutMsecs);
		this.expireMsecs = expireMsecs;
	}

	/**
	 * Detailed constructor with default acquire timeout 10000 msecs and lock expiration of 60000 msecs.
	 * 
	 * @param lockKey
	 *            lock key (ex. account:1, ...)
	 */
	public JedisLock(String lockKey) {
		this(null, lockKey);
	}

	/**
	 * Detailed constructor with default lock expiration of 60000 msecs.
	 * 
	 * @param lockKey
	 *            lock key (ex. account:1, ...)
	 * @param timeoutSecs
	 *            acquire timeout in miliseconds (default: 10000 msecs)
	 */
	public JedisLock(String lockKey, int timeoutMsecs) {
		this(null, lockKey, timeoutMsecs);
	}

	/**
	 * Detailed constructor.
	 * 
	 * @param lockKey
	 *            lock key (ex. account:1, ...)
	 * @param timeoutSecs
	 *            acquire timeout in miliseconds (default: 10000 msecs)
	 * @param expireMsecs
	 *            lock expiration in miliseconds (default: 60000 msecs)
	 */
	public JedisLock(String lockKey, int timeoutMsecs, int expireMsecs) {
		this(null, lockKey, timeoutMsecs, expireMsecs);
	}

	/**
	 * @return lock key
	 */
	public String getLockKey() {
		return lockKey;
	}

	/**
	 * @return the locked
	 */
	public boolean isLocked() {
		return locked;
	}

	/**
	 * @return the parentHashCode
	 */
	public int getParentHashCode() {
		return parentHashCode;
	}

	/**
	 * @param parentHashCode the parentHashCode to set
	 */
	public void setParentHashCode(int parentHashCode) {
		this.parentHashCode = parentHashCode;
	}

	/**
	 * Acquire lock.
	 * 
	 * @param jedis
	 * @return true if lock is acquired, false acquire timeouted
	 * @throws InterruptedException
	 *             in case of thread interruption
	 */
	public synchronized boolean acquire() throws InterruptedException {
		return acquire(redisTemplate);
	}

	/**
	 * Acquire lock.
	 * 
	 * @param jedis
	 * @return true if lock is acquired, false acquire timeouted
	 * @throws InterruptedException
	 *             in case of thread interruption
	 */
	public synchronized boolean acquire(RedisTemplate<String, String> redisTemplate) throws InterruptedException {
		int timeout = timeoutMsecs;
		while (timeout >= 0) {
			long expires = System.currentTimeMillis() + expireMsecs + 1;
			String expiresStr = String.valueOf(expires);
			
			if (redisTemplate.opsForValue().setIfAbsent(lockKey, expiresStr)) {
				// lock acquired
				locked = true;
				return true;
			}

			String currentValueStr = redisTemplate.opsForValue().get(lockKey);
			if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
				// lock is expired

				String oldValueStr = redisTemplate.opsForValue().getAndSet(lockKey, expiresStr);
				if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
					// lock acquired
					locked = true;
					return true;
				}
			}

			timeout -= 100;
			Thread.sleep(100);
			System.out.println("线程【" + Thread.currentThread().getName() + "】acquire在等待......");
		}

		return false;
	}

	/**
	 * Acqurired lock release.
	 */
	public synchronized void release() {
		release(redisTemplate);
	}

	/**
	 * Acqurired lock release.
	 */
	public synchronized void release(RedisTemplate<String, String> redisTemplate) {
		if (locked) {
			redisTemplate.delete(lockKey);
			locked = false;
		}
	}
}
