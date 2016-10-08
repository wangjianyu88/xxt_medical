package com.medical.common.datasource;
import org.aspectj.lang.JoinPoint;
public class DataSourceInterceptor {
	public void clearCustomerType(JoinPoint jp) {
		DatabaseContextHolder.clearCustomerType();
	}
}
