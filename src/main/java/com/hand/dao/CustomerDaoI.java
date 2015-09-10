package com.hand.dao;

import java.util.List;

import com.hand.model.Customer;

public interface CustomerDaoI {
	/**
	 * 获取客户信息列表
	 * @return
	 */
	List getCusList(int pagestart);
	
	List getCus(Customer cus);
	
	Customer getCuswithId(Short cusid);
	
	Integer getCustomerCount();
	//删除指定用户信息(先删外键的payment、rental中关联，在删customer)
	Integer deleteCusinPayment(Customer cus);
	Integer deleteCusinRental(Customer cus);
	Integer deleteCus(Customer cus);
	
	//添加用户信息
	Short addCus(Customer cus);
	
	//修改指定用户信息
	void updateCus(Customer cus);
}
