<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>授权角色</title>
<link rel="stylesheet" id='easyuiTheme' type="text/css"
	href="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/icon.css">
<script type="text/javascript"
	src="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript"
	src="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<link href="<%= request.getContextPath()%>/common/css/base.css"
	rel="stylesheet" type="text/css">
<link href="<%= request.getContextPath()%>/common/css/ecc.css"
	rel="stylesheet" type="text/css">
<link href="<%= request.getContextPath()%>/common/css/table.css"
	rel="stylesheet" type="text/css">
</head>
<body>

<div id="cc" class="easyui-layout" style="height:100%;"> 
	<div data-options="region:'west'" style="width:472px;">
	<div class="easyui-layout" data-options="fit:true"> 
		<div data-options="region:'west',title:'未选择角色'" style="width:432px;">
	    	<div id="roleuserrightSearchPanel" style="width: 100%;">
					<form id="roleuserrightFromSearc">
						<table cellpadding="0" cellspacing="0" class="tbl_search_bg"
							width="100%">
							<tr>
								<td class="tbl_td_label" width="13%">角色名称：</td>
								<td width="20%"><input id='roleuserrightname' class="easyui-textbox"
									data-options="iconCls:'icon-search'" /></td>
							</tr>
							<tr style="text-align: right;">
								<td colspan="6"><a id="btn" 
									onclick='searchroleuserrightListGrid()' class="easyui-linkbutton"
									data-options="iconCls:'icon-search'">查询</a>&nbsp; <a id="btn"
									 onclick='formClear("roleuserrightFromSearc");'
									class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a>
									&nbsp;</td>
							</tr>
						</table>
					</form>
				</div>
				<table id='roleuserrightlistGrid' style=""></table>
	    </div>   
	     <div data-options="region:'center',title:'功能' "   align="center" >
				<div style="left:10px;;top:180px;position:absolute;">
					<input type="image" src="<%= request.getContextPath()%>/common/image/right.png" onclick="saveUserRoleRelation();"  />
				</div>
				<div style="left:10px;;top:150px;position:absolute;">
					<input type="image" src="<%= request.getContextPath()%>/common/image/left.png" onclick="deleteUserRoleRelation();" /><br> 
				</div>
			</div> 
	</div>
	</div>
    <div data-options="region:'center',title:'已选择角色'" style="width:432px;">
    	<div id="roleuserrightSearchPanelA" style="width: 100%;">
				<form id="roleuserrightFromSearcA">
					<table cellpadding="0" cellspacing="0" class="tbl_search_bg"
						width="100%">
						<tr>
							<td class="tbl_td_label" width="13%">角色名称：</td>
							<td width="20%"><input id='roleuserrightnameA' class="easyui-textbox"
								data-options="iconCls:'icon-search'" /></td>
						</tr>
						<tr style="text-align: right;">
							<td colspan="6"><a id="btn" 
								onclick='searchroleuserrightListGridA()' class="easyui-linkbutton"
								data-options="iconCls:'icon-search'">查询</a>&nbsp; <a id="btn"
								 onclick='formClear("roleuserrightFromSearcA");'
								class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a>
								&nbsp;</td>
						</tr>
					</table>
				</form>
			</div>
			<table id='roleuserrightlistGridA' style=""></table>
    </div>     
    <div data-options="region:'east',title:'已选角色权限树'" style="width:220px;">
    	<ul id='roleuserRightTree' ></ul>
    </div>   
    
    <div data-options="region:'south'" style="height:28px;">
    	<table cellpadding="0" cellspacing="0" class="tbl_search_bg" width="100%">
				<tr style="text-align: right;">  
						<td colspan="4"> 
						   <a  onclick='checkuserorgRightDgClocse()' class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">关闭</a>  &nbsp;
						</td>   
				</tr>
		</table> 
    </div> 
</div> 
<script type="text/javascript"> 
var selRows = $("#userRightlistGrid").datagrid("getSelections"); 
var userRightId=selRows[0].ID;
$(function(){
	$('#roleuserrightSearchPanel').panel({    
		  collapsible:false,
		  width:"100%",
		  title:"查询"
	});
	$('#roleuserrightlistGrid').datagrid({    
		url : '<%= request.getContextPath()%>/role/getRoleListByUser.do?cmd=getRoleListByUser&userId='+userRightId,  
		pageNumber:1,
		pageSize:pageNum,
		width:"100%",
		fit:false,
		height:gridHeight-100,   
		method:'post',  
		title:'未选角色列表',
		idField:"ID",
		ctrlSelect:true, 
		rownumbers:true,
		fitColumns: true,   
		pagination : true,
	    columns:[[
	        {field:'ID',checkbox:true},     
	        {field:'ROLENAME',title:'角色名称' ,width:50} ,
	        {field:'ROLEDESC',title:'角色描述',width:100}   
	    ]],  
	    onHeaderContextMenu: function(e, field){
			 e.preventDefault();
			 if (!cmenu){
			      createColumnMenu('roleuserrightlistGrid');
			 }
			 cmenu.menu('show', {
			      left:e.pageX,
			      top:e.pageY
			 });
	   	},onLoadSuccess:function(data){
	           var selRows = $("#roleuserrightlistGrid").datagrid ("getSelections"); 
	           if(selRows.length>0){
	           		 $("#roleuserrightlistGrid").datagrid("clearChecked");	
	           }
		   }
	});
	$('#roleuserrightlistGrid').datagrid({
		toolbar: '#roleuserrightlistGridTb'
	});
	$('#roleuserrightSearchPanelA').panel({    
		  collapsible:false,
		  width:"100%",
		  title:"查询"
	});
	$('#roleuserrightlistGridA').datagrid({    
		url : '<%= request.getContextPath()%>/role/getRoleListByUserRight.do?cmd=getRoleListByUserRight&userId='+userRightId,  
		pageNumber:1,
		pageSize:10,
		fit:false,
		height:340,
		width:"100%",
		method:'post',  
		title:'已选角色列表',
		idField:"ID",
		ctrlSelect:true, 
		rownumbers:true,
		fitColumns: true,   
		pagination : true,
	    columns:[[
	        {field:'ID',checkbox:true},     
	        {field:'ROLENAME',title:'角色名称' ,width:50} ,
	        {field:'ROLEDESC',title:'角色描述',width:100}   
	    ]],  
	    /* toolbar :[{  
			iconCls:'icon-eccleft',
			handler:function(){ 
				deleteUserRoleRelation();
			} 
		}], */
	    onHeaderContextMenu: function(e, field){
			 e.preventDefault();
			 if (!cmenu){
			      createColumnMenu('roleuserrightlistGridA');
			 }
			 cmenu.menu('show', {
			      left:e.pageX,
			      top:e.pageY
			 });
	   	},onLoadSuccess:function(data){
	           var selRows = $("#roleuserrightlistGridA").datagrid ("getSelections"); 
	           if(selRows.length>0){
	           		 $("#roleuserrightlistGridA").datagrid("clearChecked");	
	           }
		   }
	});
	$('#roleuserRightTree').tree({
	    url:'<%= request.getContextPath()%>/right/AllRightTreeData.do?cmd=AllRightTreeData', 
	    expandAll:true,
	    onLoadSuccess : function() {
	    	$('#roleuserRightTree').tree('expandAll'); 
	    	$.ajax({   
	            type: "post",    
	            url: "<%= request.getContextPath()%>/role/getRoleRightByUserId.do?cmd=getRoleRightByUserId",
	            data: {'userId':userRightId} ,//form表单序列化
	            dataType: "json", 
	            async: false,
	            success: function(data){  
	            	for(var i=0;i<data.length;i++){
	            		var cknode=$('#roleuserRightTree').tree('find',data[i].RIGHTID);
		    	    	if(cknode){
		    	    		$('#roleuserRightTree').tree('update', { 
			    	    		target: cknode.target,
			    	    		text:"<span class='icon-ok'>&nbsp;&nbsp;&nbsp;</span>"+cknode.text
			    	    	});
		    	    	}

	            	} 
	            },
	            error:function(){
	            	  $.messager.alert("警告","系统异常！",'warning'); 
	            }
	        });
	    }
	});
});  
function checkuserorgRightDgClocse(){
	$.messager.confirm('确认','您确认想要关闭吗？',function(r){    
	    if (r){ 
	    	$('#adduserroleRightDd').panel('close');
		}
 	});
}
/**
 * 列表查询
 * 根据查询条件刷新列表数据
 */
function searchroleuserrightListGrid(){
	var roleuserrightNameSearch =$("#roleuserrightname").val();
	$('#roleuserrightlistGrid').datagrid('load',{
		rolename: roleuserrightNameSearch
	});
}

/**
* 列表查询
* 根据查询条件刷新列表数据
*/
function searchroleuserrightListGridA(){
var roleuserrightNameSearchA =$("#roleuserrightnameA").val();
$('#roleuserrightlistGridA').datagrid('load',{
	rolename: roleuserrightNameSearchA
});
}
/**
 * 表单重置
 * 参数：表单id 
 */
function formClear(formId){
	$('#'+formId).form('clear');
}
function saveUserRoleRelation(){
	var roleRows = $("#roleuserrightlistGrid").datagrid("getSelections"); 
	if(roleRows.length<1){
		$.messager.alert("警告",'请选择角色！','warning');
		return false;
	}
	var roleIds = ''; 
	for(var i=0; i<roleRows.length; i++){
		if (roleIds != '') roleIds += ','; 
		roleIds += roleRows[i].ID;
	} 
	$.messager.progress();
	$.ajax({   
        type: "post",     
        url: "<%= request.getContextPath()%>/role/saveUserRole.do?cmd=saveUserRole",
        data: {'userId':userRightId,'roleIds':roleIds} ,
        dataType: "json",  
        success: function(data){  
        	$.messager.progress('close');
        	if(data.flag){
        		var roleuserrightNameSearch =$("#roleuserrightname").val();
        		$('#roleuserrightlistGrid').datagrid('load',{
        			rolename: roleuserrightNameSearch
        		});
        		var roleuserrightNameSearchA =$("#roleuserrightnameA").val();
        		$('#roleuserrightlistGridA').datagrid('load',{
        			rolename: roleuserrightNameSearchA
        		});
        		$('#roleuserRightTree').tree('reload');
        	}
        },
        error:function(){
        	  $.messager.alert("警告","系统异常！",'warning'); 
        }
    });
}
function deleteUserRoleRelation(){
	var roleRows = $("#roleuserrightlistGridA").datagrid("getSelections"); 
	if(roleRows.length<1){
		$.messager.alert("警告",'请选择角色！','warning');
		return false;
	}
	var roleIds = ''; 
	for(var i=0; i<roleRows.length; i++){
		if (roleIds != '') roleIds += ','; 
		roleIds += roleRows[i].ID;
	} 
	$.messager.progress();
	$.ajax({   
        type: "post",     
        url: "<%= request.getContextPath()%>/role/deleteUserRole.do?cmd=deleteUserRole",
        data: {'userId':userRightId,'roleIds':roleIds} ,
        dataType: "json",  
        success: function(data){  
        	$.messager.progress('close');
        	if(data.flag){
        		var roleuserrightNameSearch =$("#roleuserrightname").val();
        		$('#roleuserrightlistGrid').datagrid('load',{
        			rolename: roleuserrightNameSearch
        		});
        		var roleuserrightNameSearchA =$("#roleuserrightnameA").val();
        		$('#roleuserrightlistGridA').datagrid('load',{
        			rolename: roleuserrightNameSearchA
        		});
        		$('#roleuserRightTree').tree('reload');
        	}
        },
        error:function(){
        	  $.messager.alert("警告","系统异常！",'warning'); 
        }
    });
} 
</script>

</body>
</html>