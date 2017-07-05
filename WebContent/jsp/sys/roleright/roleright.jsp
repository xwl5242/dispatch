<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>用户管理</title>
<jsp:include page="/jsp/header.jsp" />
</head>
<body>
	<div id="roleRightSearchPanel" style="width: 100%;">
		<form id="roleRightFromSearc">
			<table cellpadding="0" cellspacing="0" class="tbl_search_bg"
				width="100%">
				<tr>
					<td class="tbl_td_label" width="13%">角色名称：</td>
					<td width="20%"><input id='rolerightname' class="easyui-textbox"
						data-options="iconCls:'icon-search'" /></td>
				</tr> 
				<tr style="text-align: right;">
					<td colspan="6"><a id="btn" 
						onclick='searchroleRightListGrid()' class="easyui-linkbutton"
						data-options="iconCls:'icon-search'">查询</a>&nbsp; <a id="btn"
						 onclick='formClear("roleRightFromSearc");'
						class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a>
						&nbsp;</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="addroleRightDd"></div>
	<div id="viewroleRightDd"></div>
	<table id='roleRightlistGrid' style=""></table>
	<script type="text/javascript"> 
$(function(){   
	/**
	 * 初始化查询Panel
	 */
	$('#roleRightSearchPanel').panel({    
		  collapsible:true,
		  width:"100%",
		  title:"查询"
	}); 
	
	var title="";
	/**
	 * 初始化列表组建
	 */
	$('#roleRightlistGrid').datagrid({    
			url : '<%= request.getContextPath()%>/role/getRoleList.do?cmd=getRoleList',  
			width:'100%',
			pageNumber:1,
			pageSize:pageNum,
			fit:false,
			height:gridHeight,   
			method:'post',  
			title:'角色信息列表',
			idField:"ID",
			ctrlSelect:true, 
			rownumbers:true,
			fitColumns: true,   
			pagination : true,
		    columns:[[
		        {field:'ID',checkbox:true},     
		        {field:'ROLENAME',title:'角色名称' ,width:"20%"},
		        {field:'ROLEDESC',title:'角色描述',width:"20%"},   
		        {field:'USERNAME',title:'创建人' ,width:"20%"},
		        {field:'CREATETIME',title:'创建时间' ,width:"20%"}//CREATEMAN
		    ]],  
		    toolbar :['-',{  
				text:'查看',
				iconCls:'icon-search',
				handler:function(){ 
					var selRows = $("#roleRightlistGrid").datagrid("getSelections"); 
		            if(selRows.length !=1){     
		                $.messager.alert("警告",'请选择一条记录！','warning'); 
		                return false;
		            }  
		            title="查看";
					$('#viewroleRightDd').panel('open').dialog('refresh', '<%= request.getContextPath()%>/jsp/sys/roleright/viewRoleRight.jsp');
				} 
			},'-',{
				text:'授权', 
				iconCls:'icon-edit',
				handler:function(){ 
					var selRows = $("#roleRightlistGrid").datagrid("getSelections"); 
		            if(selRows.length !=1){     
		                $.messager.alert("警告",'请选择一条记录！','warning'); 
		                return false;
		            }  
		            title="角色授权";
					$('#addroleRightDd').panel('open').dialog('refresh', '<%= request.getContextPath()%>/jsp/sys/roleright/roleRightTree.jsp');
				}
			},'-'],
		    onHeaderContextMenu: function(e, field){
				 e.preventDefault();
				 if (!cmenu){
				      createColumnMenu('roleRightlistGrid');
				 }
				 cmenu.menu('show', {
				      left:e.pageX,
				      top:e.pageY
				 });
		   	},onLoadSuccess:function(data){
		           var selRows = $("#roleRightlistGrid").datagrid ("getSelections"); 
		           if(selRows.length>0){
		           		 $("#roleRightlistGrid").datagrid("clearChecked");	
		           }
			   }
		}); 
	
		/**
		 * 添加用户窗口
		 * 
		 */
		 
		 $('#addroleRightDd').dialog({    
				title:"角色授权",
				width:300,   
			    height:430,  
			    top: winTop,
			    left: winLeft,
			    modal:true, 
			    minimizable:false, 
			    maximizable:false,
			    iconCls:'icon-save',
			    closed:true, 
			    href:'<%= request.getContextPath()%>/jsp/sys/roleright/roleRightTree.jsp'  
			});  
		
		 $('#viewroleRightDd').dialog({    
				title:"查看",
				width:340,   
			    height:400,  
			    top: winTop,
			    left: winLeft,
			    modal:true, 
			    minimizable:false, 
			    maximizable:false,
			    iconCls:'icon-save',
			    closed:true, 
			    href:'<%= request.getContextPath()%>/jsp/sys/roleright/viewRoleRight.jsp'  
			});

});
/**
 * 列表查询
 * 根据查询条件刷新列表数据
 */
function searchroleRightListGrid(){
	var roleNameSearch =$("#rolerightname").val();
	$('#roleRightlistGrid').datagrid('load',{
		rolename: roleNameSearch
	});
}

/**
 * 表单重置
 * 参数：表单id 
 */
function formClear(formId){
	$('#'+formId).form('clear');
}
</script>
</body>
</html>