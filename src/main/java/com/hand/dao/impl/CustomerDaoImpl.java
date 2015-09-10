package com.hand.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hand.dao.CustomerDaoI;
import com.hand.model.Customer;
import com.hand.model.Payment;
import com.hand.model.Rental;

@Repository("CusDao")
public class CustomerDaoImpl implements CustomerDaoI{

	/**
	 * 使用@Autowired注解将sessionFactory注入到UserDaoImpl中，省略了get/set方法
	 */
	@Autowired
	private SessionFactory sessionFactory;
	
	public List getCusList(int pagestart) {
		
		
		//String sql = "SELECT cus.customer_id,cus.first_name,cus.last_name,cus.email,cus.last_update,ad.address FROM customer cus,address ad limit 0,10";
		//Query query = sessionFactory.openSession().createSQLQuery(sql);
		//String sql = "SELECT cus.customer_id,cus.first_name,cus.last_name,cus.email,cus.last_update,ad.address FROM customer cus left join address ad on ad.address_id=cus.address_id limit 0,10";
		//原生sql
		//Query cr = sessionFactory.openSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(Customer.class));
		//Criteria cr1 = sessionFactory.openSession().createCriteria(Address.class);
		//关联Address表
		//Criteria addCriteria =  cr.createCriteria("Address", "ad");
		//cr.createAlias("address", "address",CriteriaSpecification.LEFT_JOIN);
//		cr.createAlias("Address", "ad");
//		cr.add(Restrictions.eqProperty("ad.address_id"));  
		//cr.add(Restrictions.eq("address","address_id"));
		//cr.createCriteria("address");
		
//		cr.add(Restrictions.in("ad.address", ));
		//关联表条件查询（address_id=1）
		//cr.add(Expression.eq("address_id","1"));
		//关联表条件查询（address=***）
		//addCriteria.add(Expression.like("address","***"));
		
		String hql = "FROM Customer";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		//分页  从pahestart开始  每次取20行
		query.setFirstResult(pagestart);
		query.setMaxResults(20);
		return query.list();
		//return sessionFactory.openSession().createCriteria(Customer.class).list();
	
	}

	
	
	public List getCus(Customer cus) {
	     String sql = "select * from customer where first_name = ? and last_name = ?";
//		 Criteria cr = sessionFactory.openSession().createCriteria(Customer.class);
//		 cr.add(Restrictions.eq("firstName", cus.getFirstName()));
//		 cr.add(Restrictions.eq("lastName", cus.getLastName()));
		
		 Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		 //下标从0开始
	     query.setParameter(0, cus.getFirstName());
		 query.setParameter(1, cus.getLastName());
		 //System.out.println(cr.list());
		 //for(int i = 0;i<query.list().size();i++){
			
		// }
		 //query.list().
		 return query.list();
	}

	public Integer getCustomerCount() {
		String sql = "select count(customer_id) from customer";
		Query query = sessionFactory.openSession().createSQLQuery(sql);
		return Integer.valueOf(query.list().get(0).toString());
	}

	public Integer deleteCusinPayment(Customer cus) {
//		String hql = "DELETE FROM Payment WHERE customer_id = :cusid";
//		Query query = sessionFactory.openSession().createQuery(hql);
//		query.setParameter("cusid",cus.getCustomerId());
//		return query.executeUpdate();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		System.out.println("in CusDao deleteCusinPayment");
		try {
			tx = session.beginTransaction();  
			Payment loadpayment = (Payment) session.load(Payment.class, cus.getCustomerId());
			session.delete(loadpayment);
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			System.out.println("commit error");
	         e.printStackTrace();
	         return 0;
		}finally{
			 session.close();  
		}
		return 1;
		
	}
	public Integer deleteCusinRental(Customer cus){
//		String hql = "DELETE FROM Rental WHERE customerId = :cusid";
//		Query query = sessionFactory.openSession().createQuery(hql);
//		query.setParameter("cusid",cus.getCustomerId());
//		return query.executeUpdate();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		System.out.println("in CusDao deleteCusinRental");
		try {
			tx = session.beginTransaction();  
			Rental loadrental = (Rental) session.load(Rental.class, cus.getCustomerId());
			session.delete(loadrental);
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			System.out.println("commit error");
	         e.printStackTrace(); 
		}finally{
			 session.close();  
		}
		return 1;
		
	}
	public Integer deleteCus(Customer cus) {
//		String hql = "DELETE FROM Customer WHERE customerId = :cusid";
//		Query query = sessionFactory.openSession().createQuery(hql);
//		query.setParameter("cusid", cus.getCustomerId());
		System.out.println(cus.getCustomerId());
//		try{
//		sessionFactory.getCurrentSession().delete(cus);
//		}catch(Exception e){
//			e.printStackTrace();
//			System.out.println("delete exception");
//		}
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		System.out.println("in CusDao deleteCus");
		try {
			tx = session.beginTransaction();  
			Customer loadcus = (Customer) session.load(Customer.class, cus.getCustomerId());
			session.delete(loadcus);
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			System.out.println("commit error");
	         e.printStackTrace(); 
		}finally{
			 session.close();  
		}
		return 1;
	}
	//添加用户信息
	public Short addCus(Customer cus) {
		Short result = 0;
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();  
			result = (Short) session.save(cus);
			tx.commit();
		}
		catch(Exception e){
			if(tx!=null)tx.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		//System.out.println(i);
		//result = i;
		System.out.println(result);
		return result;
	}
	//修改用户信息
	public void updateCus(Customer cus) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		System.out.println("in CusDao updateCus");
		try {
			tx = session.beginTransaction();  
			session.update(cus);
			tx.commit();
		}catch(Exception e){
			if(tx!=null)tx.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		
		
		
	}



	public Customer getCuswithId(Short cusid) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Customer cus = null;
		try {
			tx = session.beginTransaction();  
			cus = (Customer)session.get(Customer.class, cusid);
			tx.commit();
		}catch(Exception e){
			if(tx!=null)tx.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		return cus;
	}

}
