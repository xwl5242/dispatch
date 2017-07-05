<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>用户管理</title> 
<link rel="stylesheet" id='easyuiTheme' type="text/css" href="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/icon.css"> 
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/jquery.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
	<link href="<%= request.getContextPath()%>/common/css/base.css" rel="stylesheet" type="text/css">
		<link href="<%= request.getContextPath()%>/common/css/ecc.css" rel="stylesheet" type="text/css">
			<link href="<%= request.getContextPath()%>/common/css/table.css" rel="stylesheet" type="text/css">
</head>
<body id='body'>
	 <div id="roleAddPanel" class="easyui-panel"  style="width:100%;" style="height: 100%;" data-options="collapsible:false">
	 	<form id="roleAddform">
		  	<table cellpadding="0" cellspacing="0" class="tbl_search_bg" width="100%">
		 		<tr>
					        <td class="tbl_td_label" width="15%">角色名称：</td> 
							<td  width="25%">								
								<input id='id' name='id' type="hidden" />
								<input id="rolename" name='rolename' class="easyui-textbox" data-options="required:true,validType:'maxLength[50]'"  />
							</td>   
							<td class="tbl_td_label" width="15%">角色描述：</td>  
							<td  width="25%"> 
								<input id="roledesc" name='roledesc' class="easyui-textbox" data-options="iconCls:'icon-man',required:true,validType:'maxLength[500]'"/>
							</td> 
				</tr>  
				<tr style="text-align: right;">
					<td colspan="4"> 
					   <a id="btn"  onclick='saveroleData()' class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>&nbsp;
					   <a  onclick='addroleDgClocseA()' class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">关闭</a>  &nbsp;
					</td> 
			</tr>
			</table>
		</form> 
	 </div>
	 <script type="text/javascript">
	 /**
	  * 关闭添加对话窗体
	  */
	function addroleDgClocseA(){ 
		$.messager.confirm('确认','您确认想要关闭吗？',function(r){    
		    if (r){ 
		    	
		    	$('#addroleDd').dialog("close");
			}
	 	});
		
	} 
	function addroleDgClocse(){ 
    	$('#addroleDd').dialog("close");
	} 
	/**
	  * 保存role信息
	  */
	function saveroleData(){
		
		if(!$("#roleAddform").form('validate')){
			return false;
		}
		$.messager.progress();//打开进度条
		$.ajax({ 
            type: "post", 
            url: "<%= request.getContextPath()%>/role/saveRole.do?cmd=saveRole",
            data: $('#roleAddform').serialize() ,//form表单序列化
            dataType: "json", 
            success: function(data){
            	 $.messager.progress('close');//关闭进度条
            	if(data.flag){ 
		    		 $('#rolelistGrid').datagrid('reload');
				     $.messager.alert("提示",data.messager,'info',addroleDgClocse()); 
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
</script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-easyui-1.4.1/custom/myvalidate.js"></script>
</body> 
</html>