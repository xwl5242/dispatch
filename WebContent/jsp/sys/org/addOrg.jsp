<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>组织机构管理</title>
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
	<div id="OrgAddPanel" class="easyui-panel" style="width: 100%;"
		style="height: 100%;" data-options="collapsible:false">
		<form id="OrgAddform">
			<table cellpadding="0" cellspacing="0" class="tbl_search_bg"
				width="100%">
				<tr>
				    
					<td class="tbl_td_label" width="15%">组织编码：</td>
					<td width="25%">
					<input id='roleId' name='id'  type="hidden" /> 
					<input id='orgparentId' name='orgparentId'  type="hidden" /> 
					<input id="orgCode" name='orgCode' class="easyui-textbox" data-options="disabled:true,validType:'maxLength[32]'" /></td>
					<td class="tbl_td_label" width="15%">组织名称：</td>
					
					<td width="25%"><input id="orgName" name='orgName'
						class="easyui-textbox"
						data-options="iconCls:'icon-man',required:true,validType:'maxLength[50]'" />
					</td>
				</tr>
				<tr>
				    
					<td class="tbl_td_label" width="15%">简称：</td>
					<td width="25%">
					<input id="sortName" name='sortName' class="easyui-textbox"
						data-options="validType:'maxLength[50]'" /></td>
					<td class="tbl_td_label" width="15%">简拼：</td>
					
					<td width="25%"><input id="jianPin" name='jianPin'
						class="easyui-textbox"
						data-options="iconCls:'icon-man',validType:'maxLength[50]'" />
					</td>
				</tr>
				<tr>
					<td class="tbl_td_label">组织类型：</td>
						<td width="25%">
						<input id="orgTypeAdd" class="easyui-combobox" name="orgType"   data-options="editable:false,valueField:'ID',required:true,textField:'TEXT',  
								url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=ORGTYPE'" />
					</td>
					
					<td class="tbl_td_label">管理类型：</td>
						<td width="25%">
						<input id="managerTypeAdd" class="easyui-combobox" name="managerType" data-options="editable:false,valueField:'ID',required:true,textField:'TEXT',  
								url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=OWNERTYPE&showNumber=1,2,3,9'" />
					</td>
				</tr>
				<tr>
				    
					<td class="tbl_td_label" width="15%">排序：</td>
					<td width="25%">
					<input id="seq" name='seq' class="easyui-numberbox"
						data-options="required:true,validType:'length[0,4]'" /></td>
					<td class="tbl_td_label" width="15%">公司类型：</td>
					<td width="25%">
					<input id="companyTypeAdd" class="easyui-combobox" name="companyType" data-options="editable:false,valueField:'ID',textField:'TEXT',  
								url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=COMPANYTYPE'" />
					</td>
				</tr>
				<tr>
					<td class="tbl_td_label" width="15%">集团性质：</td>
					<td width="25%">
					<input id="groupTypeAdd" class="easyui-combobox" name="groupType" data-options="editable:false,valueField:'ID',required:true,textField:'TEXT',  
								url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=GROUPTYPE&selected=true'" />
					</td>
				</tr>
				<tr style="text-align: right;">
					<td colspan="4"><a id="btn"  onclick='saveOrgData()'
						class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>&nbsp;
						<a  onclick='addOrgDgClocseA()' class="easyui-linkbutton"
						data-options="iconCls:'icon-cancel'">关闭</a> &nbsp;</td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
	
	
	 function addOrgDgClocseA(){ 
		   $.messager.confirm('确认','您确认想要关闭吗？',function(r){    
			    if (r){ 
			    	$('#addOrgDd').dialog("close");
				}
		 	});
			
		} 
	 function addOrgDgClocse(){ 
    	$('#addOrgDd').dialog("close");
	} 
	 
	
	
	 function saveOrgData(){
		 //赋值给隐藏的id
		 var myDbOrgId = $('#myDbOrgId').val(); 
		 $('#orgparentId').val(myDbOrgId); 
		
			 if(!$("#OrgAddform").form('validate')){
				return false;
			} 
			$.messager.progress();//打开进度条
			$.ajax({ 
		            type: "post", 
		            url: "<%=request.getContextPath()%>/org/saveOrg.do?cmd=saveOrg",
					data: $('#OrgAddform').serialize(),//form表单序列化
					dataType : "json",
					success : function(data) {
						$.messager.progress('close');//关闭进度条
					 	if (data.flag) {
					 		searchOrgListGrid("myOrgtree");
					    	$.messager.alert("提示", data.messager, 'info',addOrgDgClocse());
						} else {
							$.messager.alert("警告", data.messager, 'warning');
						}
					}
					
				});
			/* alert($('#orgparentId').val());
			var parNode=$('#myOrgtree').tree('find',$('#orgparentId').val()); 
	 		 $('#myOrgtree').tree('select',parNode); 
				addOrgDgClocse(); */
				/* var node = $('#myOrgtree').tree('getRoot'); 
		    	$('#myOrgtree').tree('select',node.target); */
			}
	 
	
	
	</script>
	<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-easyui-1.4.1/custom/myvalidate.js"></script>
</body>
</html>