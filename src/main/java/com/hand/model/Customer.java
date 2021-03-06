package com.hand.model;

// default package
// Generated 2015-9-8 23:11:21 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

/**
* Customer generated by hbm2java
*/
@Entity
@Table(name="customer") //映射数据库表

public class Customer implements java.io.Serializable {

     @Id  //主键
     @GeneratedValue(strategy=GenerationType.IDENTITY) //主键自增策略
    @Column(name="customer_id") //映射数据库字段
    private Short customerId;
    @ManyToOne(targetEntity=Address.class)  //多对一（1Customer有1个address，但一个address有多个Customer）
    @JoinColumn(name="address_id")  // 外键
    private Address address;
    @Column(name="store_id")
    private byte storeId;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="email")
    private String email;
    @Column(name="active")
    private boolean active;
    @Column(name="create_date")
    private Date createDate;
    @Column(name="last_update")
    private Date lastUpdate;
    //mappedBy映射payment的属性customer上的注解JoinColumn，是属性而非字段 
    @OneToMany(mappedBy = "customer",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)  
    private Set<Payment> payments = new HashSet<Payment>();
    @OneToMany(mappedBy = "customer",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)  
    private Set<Rental> rentals = new HashSet<Rental>();

	public Customer() {
     }

     public Customer(Address address, byte storeId, String firstName, String lastName, boolean active, Date createDate,
               Date lastUpdate) {
          this.address = address;
          this.storeId = storeId;
          this.firstName = firstName;
          this.lastName = lastName;
          this.active = active;
          this.createDate = createDate;
          this.lastUpdate = lastUpdate;
     }

     public Customer(Address address, byte storeId, String firstName, String lastName, String email, boolean active,
               Date createDate, Date lastUpdate,Set payments,Set rentals) {
          this.address = address;
          this.storeId = storeId;
          this.firstName = firstName;
          this.lastName = lastName;
          this.email = email;
          this.active = active;
          this.createDate = createDate;
          this.lastUpdate = lastUpdate;
          this.payments = payments;
          this.rentals = rentals;
          
     }

     public Short getCustomerId() {
          return this.customerId;
     }

     public void setCustomerId(Short customerId) {
          this.customerId = customerId;
     }

     public Address getAddress() {
          return this.address;
     }

     public void setAddress(Address address) {
          this.address = address;
     }

     public byte getStoreId() {
          return this.storeId;
     }

     public void setStoreId(byte storeId) {
          this.storeId = storeId;
     }

     public String getFirstName() {
          return this.firstName;
     }

     public void setFirstName(String firstName) {
          this.firstName = firstName;
     }

     public String getLastName() {
          return this.lastName;
     }

     public void setLastName(String lastName) {
          this.lastName = lastName;
     }

     public String getEmail() {
          return this.email;
     }

     public void setEmail(String email) {
          this.email = email;
     }

     public boolean isActive() {
          return this.active;
     }

     public void setActive(boolean active) {
          this.active = active;
     }

     public Date getCreateDate() {
          return this.createDate;
     }

     public void setCreateDate(Date createDate) {
          this.createDate = createDate;
     }

     public Date getLastUpdate() {
          return this.lastUpdate;
     }

     public void setLastUpdate(Date lastUpdate) {
          this.lastUpdate = lastUpdate;
     }
     public Set getPayments() {
 		return payments;
 	}

 	public void setPayments(Set payments) {
 		this.payments = payments;
 	}

 	public Set getRentals() {
 		return rentals;
 	}

 	public void setRentals(Set rentals) {
 		this.rentals = rentals;
 	}

}
