<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>用户管理</title> 
<jsp:include page="/jsp/header.jsp" />
</head>
<body id='body'>
	 <div id="userAddPanel" class="easyui-panel"  style="width:100%;" style="height:100%;" data-options="collapsible:false">
	 	<form id="userAddform">
		  	<table cellpadding="0" cellspacing="0" class="tbl_search_bg" width="100%">
		 		<tr>

					        <td class="tbl_td_label" width="15%">用户名称：</td> 
							<td  width="25%">								
								<input id='id' name='id' type="hidden" />
								<input id='sysEmpId' name=sysId type="hidden" />
								<input id="myUserName" name='userName'  class="easyui-textbox"  data-options="required:true,editable:false,buttonText:'选择',onClickButton:function(){selectEmp()}"/> 
 							</td>   
							<td class="tbl_td_label" width="15%">登录账号：</td>  
							<td  width="25%">  
								<input id="loginName" name='loginName'   class="easyui-textbox" data-options="iconCls:'icon-man',required:true,validType:['maxLength[32]','loginName']"/>
							</td> 
				</tr>  
				<tr>
							<td class="tbl_td_label">用户状态：</td>  
							<td > 
								<input id="userStat" name='userStat' class="easyui-combobox"   data-options="editable:false,required:true,valueField:'ID',textField:'TEXT',
								url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=USERSTATE'" /> 	
							</td>
							<td class="tbl_td_label" >登录密码：</td> 
							<td  >
								<input id="passWord" name='passWord' type="password" class="easyui-textbox" data-options="iconCls:'icon-lock',required:true,validType:'length[0,32]'"></input>  
							</td>   
				</tr>   
				<tr>  
					        <td class="tbl_td_label" >联系电话/手机：</td> 
							<td > 
								<input id='mobile' name='mobile' class="easyui-textbox" data-options="iconCls:'icon-mobile',validType:['mobileAndTel']"/> 
							</td>   
							<td class="tbl_td_label">电子邮件：</td>   
							<td >
								<input id='mail' name='mail' class="easyui-textbox" data-options="iconCls:'icon-mail',validType:['email','length[0,32]']" />
							</td>    
				</tr>  
				<tr style="text-align: right;">
					<td colspan="4"> 
					   <a id="btn" href="javascript:saveUserData()" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>&nbsp;
					   <a href="javascript:addUserDgClocseA()"  class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">关闭</a>  &nbsp;
					</td> 
			</tr>
			</table>
		</form> 
	 </div>
	 <div id="addUserEmpDd"></div> 
	 <script type="text/javascript">
	 
	
	 /**
	  * 关闭添加对话窗体
	  */
	function addUserDgClocseA(){ 
		$.messager.confirm('确认','您确认想要关闭吗？',function(r){    
		    if (r){ 
		    	$('#addUserDd').panel("close");
			}
	 	});
		
	} 
	function addUserDgClocse(){ 
    	$('#addUserDd').panel("close");
	} 
	
	
	
	 function checkLoginName(){
		 
		 $.ajax({
			 type:"post",
			 url:"<%= request.getContextPath()%>/user/checkLoginName.do?cmd=checkLoginName",
		     data:{"loginName":$("#loginName").val()}, 
		     dataType:"json",
		     success:function(data){
		    	 alert(data);
		     },
		     error:function(){
		    	 $.messager.progress('close');//关闭进度条
           	     $.messager.alert("警告","系统异常！",'warning'); 
		     }
		 });
	 }
	/**
	  * 保存user信息
	  */
	function saveUserData(){
		if(!$("#userAddform").form('validate')){
			return false;
		}
		$.messager.progress();//打开进度条
		$.ajax({ 
            type: "post", 
            url: "<%= request.getContextPath()%>/user/saveUser.do?cmd=saveUser",
            data: $('#userAddform').serialize() ,//form表单序列化
            dataType: "json", 
            success: function(data){
            	 $.messager.progress('close');//关闭进度条
            	if(data.flag){ 
		    		 $('#userlistGrid').datagrid('reload');
				     $.messager.alert("提示",data.messager,'info',addUserDgClocse()); 
		    	}else{
				     $.messager.alert("警告",data.messager,'warning'); 
		    	}
            },
            error:function(){
            	  $.messager.progress('close');//关闭进度条
            	  $.messager.alert("警告","系统异常！",'warning'); 
            }
        });
	}
	function selectEmp(){
		$('#addUserEmpDd').panel('open').dialog('refresh', '<%= request.getContextPath()%>/jsp/sys/user/userOnSelectEmp.jsp');
		sysSelectEmp.success=function(){     
			$("#sysEmpId").val(sysSelectEmp.data.ID);
			$("#myUserName").textbox("setText",sysSelectEmp.data.NAME);
			$("#myUserName").textbox("setValue",sysSelectEmp.data.NAME);
		};
	}
	
	 function myEmpSelect() {
		    $('#myEmpSelect').dialog({    
		    	title:'选择员工',
		    	width:900, 
		        height:520, 
		        top: winTop,
			    left: winLeft,
		        modal:true, 
		        minimizable:false,
		        maximizable:false,
		        iconCls:'icon-save',
		        closed:true, 
		        href:''  
		    }); 
		    this.flag=0;
		    this.data={};
		    this.open=function(){
				$('#myEmpSelect').panel('open').dialog('refresh', '<%= request.getContextPath()%>/jsp/sys/employee/sysOnSelectEmp.jsp');
		    };
		    this.close=function(){
		    	$('#myEmpSelect').panel('close');
		    }
		    this.setData=function(sdata){
		    	this.data=sdata;
		    };
		    this.getData=function(){
		    	return this.data;
		    };
		    this.success=function(){};
		}
	 
	 var sysSelectEmp =new myEmpSelect();  
	
$(function(){	
	$('#addUserEmpDd').dialog({    
		title:'选择员工',
		width:900,   
	    height:530,           
	    top: winTop,
	    left: winLeft,
	    modal:true, 
	    minimizable:false,
	    maximizable:false,
	    iconCls:'icon-save',
	    closed:true, 
	    href:'<%= request.getContextPath()%>/jsp/sys/user/userOnSelectEmp.jsp'  
	}); 
});	
</script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-easyui-1.4.1/custom/myvalidate.js"></script>
</body> 
</html>