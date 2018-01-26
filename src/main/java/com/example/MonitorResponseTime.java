package com.example;

public class MonitorResponseTime<R,T> implements Runnable {
	
	private Wrapper<T,R> wrapper;
	
	public MonitorResponseTime(Wrapper<T,R> wrapper){
		this.wrapper = wrapper;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//System.out.println("time taken to execute is "+(double)time/1000000000L);
		ServiceDao sdao = new ServiceDao();
		 sdao.addMetrics(wrapper);
	}

}
