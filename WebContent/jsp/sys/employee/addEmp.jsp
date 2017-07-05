<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>员工管理</title>
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
	<div id="EmpAddPanel" class="easyui-panel" style="width: 100%;"
		style="height: 100%;" data-options="collapsible:false">
		<form id="EmpAddform">
			<table cellpadding="0" cellspacing="0" class="tbl_search_bg"
				width="100%">
				<tr>
					<td class="tbl_td_label" width="15%">员工名称：</td>
					<td width="25%"><input id='id' name='id' type="hidden" /> <input
						id="EmpLoyeeName" name='EmpLoyeeName' class="easyui-textbox"
						data-options="required:true,validType:'maxLength[32]'" /></td>
					<td class="tbl_td_label" width="15%">员工年龄：</td>
					<td width="25%"><input id="EmpLoyeeAge" name='EmpLoyeeAge'
						class="easyui-numberbox"
						data-options="iconCls:'icon-man',required:true,validType:'maxLength[32]'" />
					</td>
				</tr>
				<tr>
					<td class="tbl_td_label">性别：</td>
					<td><input id="EmpLoyeeSex" name='EmpLoyeeSex'
						class="easyui-combobox"
						data-options="editable:false,required:true,valueField:'ID',textField:'TEXT',
								url:'<%=request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=SEX'" />
					</td>
					<td class="tbl_td_label">工号：</td> 
					<td><input id="EmpLoyeeCode" name='EmpLoyeeCode'
						type="easyui-textbox" class="easyui-textbox"
						data-options="iconCls:'icon-lock',required:true,validType:'length[0,20]'"></input>
					</td>
				</tr>
				<tr>
					<td class="tbl_td_label">联系电话/手机：</td>
					<td><input id='EmpLoyeeMobile' name='EmpLoyeeMobile'
						class="easyui-textbox"
						data-options="iconCls:'icon-mobile',validType:'mobileAndTel'" /></td>
					<td class="tbl_td_label"></td>
					<td><input id='EmpLoyeeOrgcode' name='EmpLoyeeOrgcode' type='hidden' />
					</td>
				</tr> 
				<tr style="text-align: right;">
					<td colspan="4"><a id="btn"  onclick='saveEmpData()'
						class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>&nbsp;
						<a  onclick='addEmpDgClocseA()' class="easyui-linkbutton"
						data-options="iconCls:'icon-cancel'">关闭</a> &nbsp;</td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
	var node = $('#myEmpOrgtree').tree('getSelected'); 
	$("#EmpLoyeeOrgcode").val(node.id);
	 /** 
	  * 关闭添加对话窗体
	  */
	function addEmpDgClocseA(){ 
		$.messager.confirm('确认','您确认想要关闭吗？',function(r){    
		    if (r){ 
		    	$('#addEmpDd').dialog("close");
			}
	 	});
	} 
	function addEmpDgClocse(){ 
    	$('#addEmpDd').dialog("close");
	} 
	/**
	  * 保存Emp信息
	  */
	function saveEmpData(){
		
		 if(!$("#EmpAddform").form('validate')){
			return false;
		}
<%-- 
				if(!$("#EmpLoyeeMobile").val().match(/^1[3|4|5|8][0-9]\d{4,8}$/)){
			alert("123");
			return false;
			} 
		--%>
		$.messager.progress();//打开进度条
		$.ajax({ 
	            type: "post", 
	            url: "<%=request.getContextPath()%>/employee/saveEmpLoyee.do?cmd=saveEmpLoyee",
				data : $('#EmpAddform').serialize(),//form表单序列化
				dataType : "json",
				success : function(data) {
					$.messager.progress('close');//关闭进度条
					if (data.flag) {
						$('#EmplistGrid').datagrid('reload');
						$.messager.alert("提示", data.messager, 'info',addEmpDgClocse());
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