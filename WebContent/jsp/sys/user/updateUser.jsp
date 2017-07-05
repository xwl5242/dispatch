<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>用户管理</title> 
<jsp:include page="/jsp/header.jsp" />
</head>
<body id='body'>
	 <div id="userUpdatePanel" class="easyui-panel"  style="width:100%;" style="height: 100%;" data-options="collapsible:false">
	 	<form id="userUpdateform">
		  	<table cellpUpdateing="0" cellspacing="0" class="tbl_search_bg" width="100%">
		 		<tr>
					        <td class="tbl_td_label" width="15%">用户名称：</td> 
							<td  width="25%">								
								<input id='userId'  name='userId' type="hidden" />
								<input id="userNameupdate" name='userName' disabled="disabled"  class="easyui-textbox" data-options="required:true,editable:false,validType:'length[0,32]'"  />
							</td>   
							<td class="tbl_td_label" width="15%">登录账号：</td>  
							<td  width="25%"> 
								<input id="loginName" name='loginName' disabled="disabled"  class="easyui-textbox" data-options="iconCls:'icon-man',required:true,validType:'maxLength[32]'"/>
							</td>
				</tr>  
				<tr>
							<td class="tbl_td_label">用户状态：</td>  
							<td >  
								<input id="userStat" name='userStat' class="easyui-combobox"   data-options="editable:false,required:true,valueField:'ID',textField:'TEXT',
								url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=USERSTATE'" /> 	
							</td>
							<td class="tbl_td_label" >联系电话/手机：</td> 
							<td > 
								<input id='mobile' name='mobile' class="easyui-textbox" data-options="iconCls:'icon-mobile',validType:['mobileAndTel']"/> 
							</td>   
				</tr>   
				<tr> 
					        
							<td class="tbl_td_label">电子邮件：</td>   
							<td >
								<input id='mail' name='mail' class="easyui-textbox" data-options="iconCls:'icon-mail',validType:['email','length[0,32]']" />
							</td> 
								<td class="tbl_td_label"></td>   
							<td >
							</td>     
				</tr>  
				<tr style="text-align: right;">
					<td colspan="4"> 
					   <a id="btn"  onclick='saveUserData()' class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>&nbsp;
					   <a  onclick='UpdateUserDgClocseA()' class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">关闭</a>  &nbsp;
					</td> 
			</tr>
			</table>
		</form> 
	 </div>
	 <script type="text/javascript">
	 /**
	  * 关闭添加对话窗体
	  */
	function UpdateUserDgClocseA(){ 
		$.messager.confirm('确认','您确认想要关闭吗？',function(r){    
		    if (r){ 
		    	
		    	$('#updateUserDd').dialog("close");
			}
	 	});
		
	} 
	function UpdateUserDgClocse(){ 
	   	$('#updateUserDd').dialog("close");
	} 
	/**
	  * 保存user信息
	  */
	function saveUserData(){
		
		if(!$("#userUpdateform").form('validate')){
			return false;
		}
		$.messager.progress();//打开进度条
		$.ajax({ 
            type: "post", 
            url: "<%= request.getContextPath()%>/user/updateUser.do?cmd=updateUser",
            data:$('#userUpdateform').serialize() ,//form表单序列化
            dataType: "json", 
            success: function(data){ 
            	
            	 $.messager.progress('close');//关闭进度条
            	if(data.flag){ 
		    		 $('#userlistGrid').datagrid('reload');
				     $.messager.alert("提示",data.messager,'info',UpdateUserDgClocse()); 
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
	var selRows = $("#userlistGrid").datagrid("getSelections"); 
	$.ajax({ 
        type: "post", 
        url: "<%= request.getContextPath()%>/user/findUserById.do?cmd=findUserById",
        data: {"userId":selRows[0].ID},//form表单序列化
        dataType: "json", 
        success: function(data){
        	$('#userUpdateform').form('load',data);
        	$("#userId").val(data.id);
        },
        error:function(){ 
        	  $.messager.alert("警告","系统异常！",'warning'); 
        }
    }); 
</script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-easyui-1.4.1/custom/myvalidate.js"></script>
</body> 
</html>