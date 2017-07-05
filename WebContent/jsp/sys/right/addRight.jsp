<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>新增权限菜单</title>
<link rel="stylesheet" id='easyuiTheme' type="text/css"
	href="<%=request.getContextPath()%>/js/jquery-easyui-1.4.1/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/js/jquery-easyui-1.4.1/icon.css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<link href="<%=request.getContextPath()%>/common/css/base.css"
	rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/common/css/ecc.css"
	rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/common/css/table.css"
	rel="stylesheet" type="text/css">
</head>
<body id='body'>
	<div id="RightAddPanel" class="easyui-panel" style="width: 100%;"
		style="height: 100%;" data-options="collapsible:false">
		<form id="RightAddform">
			<table cellpadding="0" cellspacing="0" class="tbl_search_bg"
				width="100%">
				<tr>
					<td class="tbl_td_label" width="15%">权限名称：</td>
					<td width="25%">
					<input id='id' name='id'  type="hidden" /> 
					<input id='parentId' name='parentid'  type="hidden" /> 
					<input id="rightName" name='rightname' class="easyui-textbox"
						data-options="required:true,validType:'maxLength[50]'" /></td>
					<td class="tbl_td_label" width="15%">资源链接：</td>
					
					<td width="25%"><input id="rightUrl" name='righturl'
						class="easyui-textbox"
						data-options="iconCls:'icon-man',required:true,validType:'maxLength[150]'" />
					</td>
				</tr>
				<tr>
					<td class="tbl_td_label">权限类型：</td>
						<td width="25%">
						<input id="rightType" class="easyui-combobox" name="righttype"   data-options="required:true,editable:false,valueField:'ID',textField:'TEXT',  
								url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=RIGHTTYPE'" />
					</td>
					<td class="tbl_td_label" width="15%">权限描述：</td>
					<td width="25%"><input id="rightDesc" name='rightdesc'
						class="easyui-textbox"
						data-options="iconCls:'icon-man',required:true,validType:'maxLength[500]'" />
					</td>
					
				</tr>
				
				<tr>
					<td class="tbl_td_label">权限状态：</td>
					<td width="25%">
						<input id="rightStat" class="easyui-combobox" name="rightstat"   data-options="required:true,editable:false,valueField:'ID',textField:'TEXT',  
								url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=RIGHTSTAT'" />
					</td>
					<td class="tbl_td_label">排序：</td>
					<td width="25%">
						<input id="seq" class="easyui-numberbox" name="seq"   data-options="required:true,validType:'length[0,4]'" />
					</td>
				</tr>
				<tr>
					<td class="tbl_td_label">左侧树链接：</td>
					<td width="25%">
						<input id="treeUrl" class="easyui-textbox" name="treeUrl"   data-options="validType:'length[0,100]'" />
					</td>
				
				</tr>
				
				<tr style="text-align: right;">
					<td colspan="4"><a id="btn"  onclick='saveRightData()'
						class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>&nbsp;
						<a  onclick='addRightDgClocseA()' class="easyui-linkbutton"
						data-options="iconCls:'icon-cancel'">关闭</a> &nbsp;</td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
	 //赋值给隐藏的id
		 var myDbRightId = $('#myDbRightId').val(); 
		 $('#parentId').val(myDbRightId); 
		
		//关闭增加权限信息窗口
	 function addRightDgClocseA(){ 
		    $.messager.confirm('确认','您确认想要关闭吗？',function(r){    
			    if (r){ 
			    	$('#addRightDd').dialog("close");
				}
		 	});
			
		}
	 function addRightDgClocse(){ 
    	$('#addRightDd').dialog("close");
	}
		
		function checkFileType(img){
			
		}
	 
	 /* 保存权限信息
	 **/
	 function saveRightData(){
			 var json=JSON.stringify($("#RightAddform").serializeObject());
			 if(!$("#RightAddform").form('validate')){
				return false;
			} 
			$.messager.progress();//打开进度条
			$.ajax({ 
		            type: "post", 
		            url: "<%=request.getContextPath()%>/right/saveRight.do?cmd=saveRight",
					data: {json:json},//form表单序列化 $('#RightAddform').serialize()
					dataType : "json",
					success : function(data) {
					$.messager.progress('close');//关闭进度条
					if (data.flag) {
						$.messager.alert("提示", data.messager, 'info',addRightDgClocse());
			    		$("#myRightTree").tree("reload");
					} else {
						$.messager.alert("警告", data.messager, 'warning');
						}
					}
					
				});
	}
	 
	 
	  
	 
	
	</script>
	<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-easyui-1.4.1/custom/myvalidate.js"></script>
</body>
</html>