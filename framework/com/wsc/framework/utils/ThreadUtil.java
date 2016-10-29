package com.wsc.framework.utils;

/**
 * Thread线程类
 */
public class ThreadUtil {

	public ThreadUtil() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 等待一段时间
	 * @param time 等待时间，时间为毫秒
	 */
	public static void sleep(long time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
