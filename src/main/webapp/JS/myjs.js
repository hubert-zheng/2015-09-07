$(document).ready(function(){
	
	var page_num = 1;//默认在第一页
	
	//ajax跨域
	var localObj = window.location;
	var contextPath = localObj.pathname.split("/")[1];
	var basePath = localObj.protocol+"//"+localObj.host+"/"+contextPath;
	var server_context=basePath;
	//console.log(server_context);
	
	//加载页面时默认请求第一页的列表
	
	
	//分页栏的点击事件
	$(".fenye").find(".paging").click(function(){
		page_num = parseInt($(this).attr("id"));
		getJson(page_num);
	});
	
	//点击上一页分页按钮
	$(".fenye").find(".last_page").click(function(){
		page_num =parseInt(page_num)-1;
		//alert(page_num);
		getJson(page_num);
	});
	//点击下一页分页按钮
	$(".fenye").find(".next_page").click(function(){
		page_num =parseInt(page_num)+1;
		//console.log(page_num);
		//alert("pn:"+pn);
		//alert(page_num);
		getJson(page_num);
	});
	
	
	/*
	 * 根据传入的页面，请求第page_页的内容
	 */
	function getJson(page_){
		//console.log(page_num);
		$.getJSON(
				server_context+"/Customer_loadmore.action",
				{pagestart:page_},
				function(json)
				{	//输出数据到控制台
					//console.log(json);
					
					//var obj = eavl(json);
					//转换为对象
					//var json_obj = JSON.parse(json,function(){
					//for(var i =0 ; i<json.length;i++){
					var str = "";
					//补全th
//					str +="<th>操作</th>";
//					str +="<th>First Name</th>";
//					str +="<th>Last Name</th>";
//					str +="<th>Address</th>";
//					str +="<th>Email</th>";
//					str +="<th>Customerid</th>";
//					str +="<th>lastupdate</th>";
					if (json.length) {
							
							//遍历
							//for(var i=0;i<json.length;i++){
							$.each(json,function(index,array){
								//将数据插入到表格中
								str += '<tr>';
								str += '<td><button type="button" class="edit btn btn-primary" data-toggle="modal" data-target="#myModal">编辑</button> <button type="button" class="del btn btn-primary">删除</button></td>';
								str += '<td>'+array.firstName+'</td>';
								str += '<td>'+array.lastName+'</td>';
								str += '<td>'+array.address.address+'</td>';
								str += '<td>'+array.email+'</td>';
								str += '<td class="cus_id">'+array.customerId+'</td>';
								str += '<td>'+array.lastUpdate+'</td>';
								str += '</tr>';
							});
							//}
							console.log(str);
							$("table").empty();
							$("table").append(str);
							str = null;
							//为刚插入的del和edit两个按钮绑定事件
							
							
						}
					//})
					//}
				}
		);
	}
	
	//点击列表的删除超链接
	$(".usertable").find(".del").click(function(){
		var $allListElements= $(this).parent();
		var customer_id = $allListElements.siblings(".cus_id").text();
		//alert(customer_id);
		delCus(customer_id);
	})
	/*
	 * 根据传入的Customer_id请求删除该用户信息
	 */
	function delCus(cus_id){
		$.ajax({
			url:server_context+"/Customer_delete.action",
			data:"cusid="+cus_id,
			dataType:'json',
			error:function(){console.log("ajax,error!");alert("ajax,error!")},
			success:function(json)
			{
				console.log(json);
				if(JSON.stringify(json)=="true"){alert("删除成功")}
				else{alert("删除失败，请重新操作")}
			
			}
		});
	}
	
	
	//点击编辑按钮,将数据加载到模态框
	$(".usertable").find(".edit").click(function(){
		var thistr = $(this).parent(); //查找当前父集
		var editfirstName = thistr.siblings(".CusfirstName").text();
		var editlastName = thistr.siblings(".CuslastName").text();
		var editaddress = thistr.siblings(".Cusaddress").text();
		var editemail = thistr.siblings(".Cusemail").text();
		var editcusid = thistr.siblings(".cus_id").text();
		//alert(editcusid);
		var dditlastUpdate = thistr.siblings(".CuslastUpdate").text();
		
		$("#select_addr").text(editaddress); 
		$("#select_addr").append('<span class="hidden">'+thistr.siblings(".cus_id").text()+'</span>');
		$("#firstName").val(editfirstName);//可输入的input框的用val
		$("#lastName").val(editlastName);
		$("#email").val(editemail);
		$("#model_cusid").text(editcusid);
		
 	})
 	
 	//点击下拉菜单按钮，将请求的address加载到下拉列表中
	$(".dropdown").find("#select_addr").click(function(){
		console.log(server_context+"/Address_getaddress.action");
		$.ajax({
			url:server_context+"/Address_getaddress.action",
			dataType:'json',
			error:function(){console.log("ajax,error!")},
			success:function(json)
			{
				console.log(json);
				
				var str = "";
				if(json.length){
					$.each(json,function(index,array){
						//str += '<li> <a href="#" id = "'+array.addressId+'">'+array.address+'</a></li>';
						str += '<li> <a href="#" id="'+array.addressId+'">'+array.address+'</a></li>';
					})
					
				}
				//json.length跳出添加元素
				$(".dropdown").find(".dropdown-menu").empty();
				$(".dropdown").find(".dropdown-menu").append(str);
				//给刚添加的a标签绑定点击事件
				$(".dropdown-menu li a").bind("click", function (){
					//alert("in li a bind");
					//alert($(this).text());
					//alert($(this).attr("id"));
					$("#select_addr").text($(this).text());
					$("#select_addr").append('<span class="hidden">'+$(this).attr("id")+'</span>');
				})
			}
		});
	})
	
	
	//点击save change将模态框中的数据用ajax请求到后台保存到数据库中
	$("#submit").click(function(){
		var modelfooter = $(this).parent(".modal-footer");//当前按钮父集，即modelfooter
		var modelbody = modelfooter.siblings(".modal-body");
		var editfirstName = modelbody.find("#firstName").val();
		//alert(editfirstName);
		var editlastName = modelbody.find("#lastName").val();
		var editemail = modelbody.find("#email").val();
		//var editaddress = modelbody.find("#select_addr").text();
		var editaddressId = modelbody.find("span").text();
		var editcusid = $("#model_cusid").text();
		//alert(editcusid);
		//alert(editaddressId);
		$.ajax({
			url:server_context+"/Customer_updateCus.action",
			data:{
				firstName:editfirstName,
				lastName:editlastName,
				email:editemail,
				addressId:editaddressId,
				cusid:editcusid
				},
			dataType:'json',
			error:function(){alert("ajax error!!!")},
			success:function(json){
					//alert(json);
					console.log(json);
					if(JSON.stringify(json)=="true"){alert("修改成功")}
					else alert("修改失败");
					
				}
					
			});
		
	})
})


	
