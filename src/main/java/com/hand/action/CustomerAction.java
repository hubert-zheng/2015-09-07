package com.hand.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.hand.model.Customer;
import com.hand.service.AddressServiceI;
import com.hand.service.CusServiceI;
import com.hand.service.impl.AddressServiceImpl;
import com.hand.service.impl.CusServiceImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ValueStack;

public class CustomerAction extends ActionSupport{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4634175981265924020L;


	/**
	 * 注入CusService
	 */
	@Autowired
	private CusServiceImpl CusService;
	@Autowired
	private AddressServiceImpl addressService;
	
	//登录名
	private String firstName;
	private String lastName;
	
	//获取客户pagestart页的信息（20条）
	private String pagestart;
	//获取指定要删除/修改的客户id
	private String cusid;
	
	//获取从页面请求的customer的属性
	private String email;
	private Short addressId;
	
	
	
	public Short getAddressId() {
		return addressId;
	}
	public void setAddressId(Short addressId) {
		this.addressId = addressId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCusid() {
		return cusid;
	}
	public void setCusid(String cusid) {
		this.cusid = cusid;
	}
	public String getPagestart() {
		return pagestart;
	}
	public void setPagestart(String pagestart) {
		this.pagestart = pagestart;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
//	@Override
//	public String execute() throws Exception {
//		List list = CusService.getCusList(0);
//		List<Customer> cuslist = new ArrayList<Customer>();
//		for(int i = 0;i<list.size();i++){
//			Customer cus = (Customer)list.get(i);
//			System.out.println("firstName:"+cus.getFirstName());
//			System.out.println("address:"+cus.getAddress().getAddress());
//			cuslist.add(cus);
//		}
//		//值栈
//		//System.out.println("count:"+CusService.getCusCount());
//		
//		ValueStack stack = ActionContext.getContext().getValueStack();
//	    Map<String, Object> context = new HashMap<String, Object>();
//	    context.put("cuslist", list);
//	    context.put("cuscount", CusService.getCusCount()/20+1);
//	    stack.push(context);
//	    //System.out.println("Size of the valueStack: " + stack.size());
//		Customer cus = new Customer();
//		cus.setFirstName(firstName);
//		cus.setLastName(lastName);
//		//System.out.println(CusService.checkLogin(cus));
//		if(CusService.checkLogin(cus))
//		{
//		//取得session
//		ActionContext actionContext = ActionContext.getContext();
//		Map<String, Object> session = actionContext.getSession();
//		session.put("flag", "login");
//		return SUCCESS;
//		}
//		else return ERROR;
//	}
	
	public String login(){
		List list = CusService.getCusList(0);
		ValueStack stack = ActionContext.getContext().getValueStack();
	    Map<String, Object> context = new HashMap<String, Object>();
	    context.put("cuslist", list);
	    context.put("cuscount", CusService.getCusCount()/20+1);
	    stack.push(context);
	    //System.out.println("Size of the valueStack: " + stack.size());
		Customer cus = new Customer();
		cus.setFirstName(firstName);
		cus.setLastName(lastName);
		//System.out.println(CusService.checkLogin(cus));
		if(CusService.checkLogin(cus))
		{
		//取得session
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		session.put("flag", "login");
		return "login";
		}
		else return ERROR;
	}
	
	//分页ajax加载
	public void loadmore(){
		List<Customer> cuslist = CusService.getCusList((Integer.valueOf(pagestart)-1)*20);
//		Gson gson = new Gson();
//		String json = gson.toJson(cuslist);
//		HttpServletResponse resp = ServletActionContext.getResponse();
//		PrintWriter out;
//		try {
//			out = resp.getWriter();
//			out.println(json);
//			out.flush();
//			out.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		outputJson(cuslist);
	}
	
	public String logout(){
		ActionContext.getContext().getSession().clear();
		return "notlogin";
	}
	
	public boolean delete(){
		System.out.println("in Cus delete");
		System.out.println("will delete customer_id:"+cusid);
	
		return CusService.delCus(Short.valueOf(cusid));
	}
	
	public void addcus(){
		System.out.println("in Cus addcus");
		System.out.println(addressId+firstName+lastName+email+addressId);
		Customer cus = new Customer();
		cus.setFirstName(firstName);
		cus.setLastName(lastName);
		cus.setEmail(email);
		cus.setAddress(addressService.getAddress(addressId));
		cus.setActive(true);
		cus.setStoreId((byte) 1);
		cus.setCreateDate(new Date());
		cus.setLastUpdate(new Date());
		System.out.println("cus:id"+cus.getAddress().getAddress());
		boolean bool = CusService.addCus(cus);
		System.out.println("BOOL:"+bool);
		outputJson(bool);
		
		
	}
	
	public void updateCus(){
		System.out.println("in Cus updatecus");
		//取得持久化对象
		Customer cus = CusService.getCus(Short.valueOf(cusid));
		cus.setFirstName(firstName);
		cus.setLastName(lastName);
		cus.setAddress(addressService.getAddress(addressId));
		cus.setEmail(email);
		cus.setLastUpdate(new Date());
		boolean bool = CusService.editCus(cus);
		outputJson(bool);

	}
	
	
	
	public void outputJson(Object object){
		Gson gson = new Gson();
		String json = gson.toJson(object);
		HttpServletResponse resp = ServletActionContext.getResponse();
		PrintWriter out;
		try {
			out = resp.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
