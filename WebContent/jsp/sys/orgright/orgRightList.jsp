<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>员工管理</title>
<jsp:include page="/jsp/header.jsp" />
</head>
<body> 
	<div  class="easyui-layout" data-options="fit:true">
		<div data-options="region:'west',title:'组织机构授权'" style="width:100%">
			<form id="myOrgRightFromSearch"  style="width: 90%;height: auto;">            
				<table>      
					<tr>  
						<td>类型：</td>                              
						<td>                  
							<input id='managerTypeSearch' name='managerType' class="easyui-combobox" data-options="editable:false,valueField:'ID',textField:'TEXT',
								url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=OWNERTYPE&showNumber=1,2,3,9'"/>
						</td>                 
						<td>名称：</td>            
						<td>                  
							<input id='orgNameSearch' name='orgName' class="easyui-textbox" />
						</td>
						<td>
						    <a  onclick='searchOrgListGridT("myOrgRightGrid")' class="easyui-linkbutton" 
								data-options="iconCls:'icon-search'">查询</a>
							
						</td>	
						<td>
							<a  onclick='formClear("myOrgRightFromSearch")' class="easyui-linkbutton" 
								data-options="iconCls:'icon-redo'">重置</a>
						</td>
					</tr>          
				</table>
			</form>		
		<table id='myOrgRightGrid' style="width：100%;" ></table>
		<div id="orgRightListDb"></div>  
		</div>						
</div>		
<script type="text/javascript"> 
$(function(){ 
	 $('#myOrgRightGrid').datagrid({    
			url : '<%= request.getContextPath()%>/org/selectOrgList.do?cmd=selectOrgList',  
			width:'100%',
			pageNumber:1,
			pageSize:pageNum,
			fit:false,
			height:gridHeight,    
			title:'组织机构列表', 
			idField:"ID",
			striped:true,
			ctrlSelect:true, 
			rownumbers:true,
			fitColumns: true,   
			pagination : true,
			columns:[[
				{field:'ID',checkbox:true},     
				{field:'ORGNAME',title:'名称' ,width:100}, 
				{field:'SORTNAME',title:'简称' ,width:100},
		        {field:'JIANPIN',title:'简拼' ,width:100}
			]],
			toolbar : ['-',{  
				text:'授权',
				iconCls:'icon-add',
				handler:function(){
					var selRows = $('#myOrgRightGrid').datagrid("getSelections");
					if(selRows.length!=1){
						$.messager.alert("警告",'请选择一个组织机构！','warning'); 
						return false;
					}
					$('#orgRightListDb').dialog('refresh','<%= request.getContextPath()%>/jsp/sys/orgright/orgRightTree.jsp'); 
			    	$('#orgRightListDb').dialog('open');
			  }
			},'-',{  
				text:'查看权限', 
				iconCls:'icon-search',
				handler:function(){ 
					var selRows = $("#myOrgRightGrid").datagrid("getSelections"); 
		            if(selRows.length !=1){     
		            	$.messager.alert("警告",'请选择一个组织机构！','warning'); 
		                return false;
		            } 
		            $('#orgRightListDb').dialog('refresh', '<%= request.getContextPath()%>/jsp/sys/orgright/viewOrgRight.jsp');  
					$('#orgRightListDb').dialog('open');
				}
			}]
			
			,
			onHeaderContextMenu: function(e, field){
				 e.preventDefault();
				 if (!cmenu){
				      createColumnMenu('myOrgRightGrid');
				 }
				 cmenu.menu('show', {
				      left:e.pageX,
				      top:e.pageY
				 });
		   	}
		});	
});

$('#orgRightListDb').dialog({    
	title:'机构授权',
	width:300,
    height:430,  
    top: winTop,
    left: winLeft,
    modal:true, 
    minimizable:false,
    maximizable:false,
    iconCls:'icon-save',
    closed:true, 
    href:'<%= request.getContextPath()%>/jsp/sys/orgright/orgRightTree.jsp'  
});  
		

/**
 * 表单重置
 * 参数：表单id 
 */
function formClear(formId){
	$('#'+formId).form('clear');
}

/**
 * 表单查询
 */
function searchOrgListGridT(dataId){     
	var orgName = $("#orgNameSearch").val();
	var managerType = $("#managerTypeSearch").combobox("getValue");
	$("#"+dataId).datagrid('load',{
		orgName: orgName,
		managerType :managerType
	});
}
</script>
</body> 
</html>