<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>用户管理</title>
<jsp:include page="/jsp/header.jsp" />
</head>
<body>
	<div id="roleSearchPanel" style="width: 100%;">
		<form id="roleFromSearc">
			<table cellpadding="0" cellspacing="0" class="tbl_search_bg"
				width="100%">
				<tr>
					<td class="tbl_td_label" width="13%">角色名称：</td>
					<td width="20%"><input id='rolename' class="easyui-textbox"
						data-options="iconCls:'icon-search'" /></td>
				</tr>
				<tr style="text-align: right;">
					<td colspan="6"><a id="btn" 
						onclick='searchroleListGrid()' class="easyui-linkbutton"
						data-options="iconCls:'icon-search'">查询</a>&nbsp; <a id="btn"
						 onclick='formClear("roleFromSearc");'
						class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a>
						&nbsp;</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="addroleDd"></div>
	<div id="updateRoleDd"></div>
	<table id='rolelistGrid' style=""></table>
	<script type="text/javascript"> 
$(function(){   
	/**
	 * 初始化查询Panel
	 */
	$('#roleSearchPanel').panel({    
		  collapsible:true,
		  width:"100%",
		  title:"查询"
	}); 
	/**
	 * 初始化列表组建
	 */
	$('#rolelistGrid').datagrid({    
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
		        {field:'ROLENAME',title:'角色名称' ,width:"25%",align:"center"} ,
		        {field:'ROLEDESC',title:'角色描述',width:"25%",align:"center"},   
		        {field:'USERNAME',title:'创建人' ,width:"25%",align:"center"},
		        {field:'CREATETIME',title:'创建时间' ,width:"22%",align:"center"}//CREATEMAN
		    ]],  
		    toolbar :['-',{  
				text:'添加',
				iconCls:'icon-add',
				handler:function(){ 
					$('#addroleDd').dialog('refresh', '<%= request.getContextPath()%>/jsp/sys/role/addRole.jsp');  
					$('#addroleDd').dialog('open');
				} 
			},'-',{
				text:'修改', 
				iconCls:'icon-edit',
				handler:function(){ 
					var selRows = $("#rolelistGrid").datagrid("getSelections"); 
		            if(selRows.length !=1){     
		                $.messager.alert("警告",'请选择一条记录！','warning'); 
		                return false;
		            } 
		            $('#updateRoleDd').dialog('refresh', '<%= request.getContextPath()%>/jsp/sys/role/updateRole.jsp');  
					$('#updateRoleDd').dialog('open');
				}
			},'-',{ 
				text:'删除',
				iconCls:'icon-cancel',
				handler:function(){
						var selRows = $("#rolelistGrid").datagrid("getSelections"); 
			            if(selRows.length <1){     
			                $.messager.alert("警告",'请选择一条记录！','warning'); 
			                return false;
			            } 

						$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
						    if (r){    
						    	$.messager.progress();//打开进度条
						    	var ids ="";//声明的是一个数组或集合
						    	 $.each( selRows, function(){
							            ids+=this.ID+",";
						            } );
								$.ajax({ 
						            type: "post", 
						            url: "<%= request.getContextPath()%>/role/deleteRole.do?cmd=deleteRole",
						            data:{"RoleId":ids} ,//form表单序列化
						            dataType: "json", 
						            success: function(data){ 
						            	 $.messager.progress('close');//关闭进度条
						            	if(data.flag){ 
								    		 $('#rolelistGrid').datagrid('reload');
										     $.messager.alert("提示",data.messager,'info'); 
										     $('#rolelistGrid').datagrid('clearChecked');
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
						}); 
				}
			},'-',{
				text:'导出',
			    iconCls: 'icon-excel', 
			    handler: function(){ 
			 	   $.messager.confirm("确定",'确定导出？',function(r){
			            if(r){
			                $.messager.alert("信息",'导出成功','info');
			            }else{
			                return false; 
			            }
			        });
			    }
		    },'-'],
		    onHeaderContextMenu: function(e, field){
				 e.preventDefault();
				 if (!cmenu){
				      createColumnMenu();
				 }
				 cmenu.menu('show', {
				      left:e.pageX,
				      top:e.pageY
				 });
		   	},
		    onLoadSuccess:function(row){//当表格成功加载时执行 
		   		var selRows = $("#rolelistGrid").datagrid("getSelections"); 
		   		if(selRows.length>0){
		   			$("#rolelistGrid").datagrid("clearChecked");	
			   	}
		  }
		});
	
		/**
		 * 创建列表右键菜单
		 */
		var cmenu;
		function createColumnMenu(){
			cmenu = $('<div/>').appendTo('body');
			cmenu.menu({ 
				onClick: function(item){
					if (item.iconCls == 'icon-ok'){
						var myGridMenuSize=$(".icon-ok",cmenu).size();
						if(myGridMenuSize==1){
							 $.messager.alert("警告",'已是最后一列，无法隐藏！','warning'); 
							 return;
						}
						$('#rolelistGrid').datagrid('hideColumn', item.name);
						cmenu.menu('setIcon', {
							target: item.target,
							iconCls: 'icon-empty'
						});
					} else {
						$('#rolelistGrid').datagrid('showColumn', item.name);
						cmenu.menu('setIcon', {
							target: item.target,
							iconCls: 'icon-ok'
						});
					}
				}
			});
			/**
			 * 动态创建列表右键菜单 菜单选项
			 */
			var fields = $('#rolelistGrid').datagrid('getColumnFields');
			for(var i=0; i<fields.length; i++){
				var field = fields[i];
				var columenOption=$('#rolelistGrid').datagrid('getColumnOption',field);
				if(!columenOption.checkbox==true){
					var col = $('#rolelistGrid').datagrid('getColumnOption', field);
					cmenu.menu('appendItem', {
						text: col.title,
						name: field,
						iconCls: 'icon-ok'
					});       
				}
			}
		}
		/**
		 * 添加角色窗口
		 * 
		 */
		$('#addroleDd').dialog({    
			title:'增加角色',
			width:600,
		    height:'auto', 
		    top: winTop,
		    left: winLeft,
		    modal:true, 
		    minimizable:false,
		    maximizable:false,
		    iconCls:'icon-save',
		    closed:true, 
		    href:'<%= request.getContextPath()%>/jsp/sys/role/addrole.jsp'  
		});  
		/**
		 * 编辑角色窗口
		 * 
		 */
		$('#updateRoleDd').dialog({    
			title:'修改角色',
			width:600,
		    height:'auto', 
		    top: winTop,
		    left: winLeft,
		    modal:true, 
		    minimizable:false,
		    maximizable:false,
		    iconCls:'icon-edit',
		    closed:true, 
		    href:'<%= request.getContextPath()%>/jsp/sys/role/updateRole.jsp'  
		}); 
});
/**
 * 列表查询
 * 根据查询条件刷新列表数据
 */
function searchroleListGrid(){
	var roleNameSearch =$("#rolename").val();
	//alert(roleNameSearch+"kkd");
	//var loginNameSearch =$("#loginName").val();
	//var roleStatSearch =$("#roleStat").combobox("getValue");
	
	$('#rolelistGrid').datagrid('load',{
		rolename: roleNameSearch
		//roleStat:roleStatSearch 
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