package com.example;

public class Wrapper<T,R> {

	T body;
	Long reqTime;
	Long dbTime;
	boolean error;
	R request;
	public T getBody() {
		return body;
	}
	public void setBody(T body) {
		this.body = body;
	}
	
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public R getRequest() {
		return request;
	}
	public void setRequest(R request) {
		this.request = request;
	}
	public Long getReqTime() {
		return reqTime;
	}
	public void setReqTime(Long reqTime) {
		this.reqTime = reqTime;
	}
	public Long getDbTime() {
		return dbTime;
	}
	public void setDbTime(Long dbTime) {
		this.dbTime = dbTime;
	}
}
