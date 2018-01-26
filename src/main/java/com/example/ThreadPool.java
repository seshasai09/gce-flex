package com.example;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPool {
	
	private static ThreadPoolExecutor ex;
	
	private ThreadPool(){
		ex = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);
	}
	
	public static ThreadPoolExecutor getThreadPoolInstance(){
		if(ex==null){
			new ThreadPool();
		}
		
		return ex;
	}

}
