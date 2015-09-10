package com.hand.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hand.dao.AddressDaoI;
import com.hand.model.Address;
@Repository("addressDao")
public class AddressDaoImpl implements AddressDaoI{

	@Autowired
	private SessionFactory sessionFactory;
	
	public List getAddressList() {
		String hql = "FROM Address";
		Query query = sessionFactory.openSession().createQuery(hql);
		return query.list();
	}

	public Address getAddress(Short addressId) {
		String hql = "FROM Address Addr WHERE Addr.addressId = :addressId";
		Query query = sessionFactory.openSession().createQuery(hql);
		query.setParameter("addressId", addressId);
		return (Address) query.list().get(0);
	}

}
