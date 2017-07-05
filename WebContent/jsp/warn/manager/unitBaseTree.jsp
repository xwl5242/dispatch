<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>单位树</title>
<jsp:include page="/jsp/header.jsp" />
</head>
<body>
	<div style="height: 100%;">
			<form id='ggUnit'>
				<table>      
					<tr>                   
						
						<td>名称：</td>            
						<td>        
							         
							<input id='unitNameBaseSearch' name='unitName' class="easyui-textbox" />
						</td>	
						<td>
						     <a href="javascript:searchUnitListGrid('ggUnit','unitListGrid')" id="searchParEdit" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
						</td> 
						<td>
						     <a id="clearParEdit"  onclick='formClear("ggUnit");' class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a> 
						</td> 
					</tr> 
				</table>
			</form>
			<table id='unitListGrid' style="width：100%;" ></table>
	</div>
<script type="text/javascript">
var jsonData = null;
$(function(){ 
	 var unitType=$("#unitTypeDd").val();
	 if(unitType==undefined){
		 unitType="";
	 }
	 
	 $('#unitListGrid').datagrid({    
			url : '<%= request.getContextPath()%>/unit/selectUnitList.do?cmd=selectUnitList&unitType='+unitType,  
			width:'100%',
			pageNumber:1,
			pageSize:pageNum,
			fit:false,
			height:gridHeight,    
			title:'用能单位列表', 
			idField:"ID",
			striped:true,
			ctrlSelect:true, 
			rownumbers:true,
			fitColumns: true,   
			pagination : true,
			columns:[[
				{field:'ID',checkbox:true},     
				{field:'UNITCODE',title:'编号' ,width:100}, 
				{field:'UNITNAME',title:'名称' ,width:100},
		        {field:'SHORTNAME',title:'简称' ,width:100},
		        {field:'JIANPIN',title:'简拼' ,width:100},
		        {field:'UNITTYPENAME',title:'性质' ,width:100}
			]],
			hideColumn:[[
	  			{field:'UNITTYPE'}, 
			]],
			toolbar :['-',{  
				text:'确认',
				iconCls:'icon-ok',
				handler:function(){ 
					var selRows = $('#unitListGrid').datagrid("getSelections");
					if(selRows.length!=1){
						$.messager.alert("提示","请选择单位后再确定！");
						return false;
					}
					transportValue(selRows[0].ID,selRows[0].UNITNAME,selRows[0].UNITTYPE,selRows[0].UNITTYPENAME);
					$('#danweiynDd').dialog('close');
					closeCollUnit();
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
						$('#unitListGrid').datagrid('hideColumn', item.name);
						cmenu.menu('setIcon', {
							target: item.target,
							iconCls: 'icon-empty'
						});
					} else {
						$('#unitListGrid').datagrid('showColumn', item.name);
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
			var fields = $('#unitListGrid').datagrid('getColumnFields');
			for(var i=0; i<fields.length; i++){
				var field = fields[i];
				var columenOption=$('#unitListGrid').datagrid('getColumnOption',field);
				if(!columenOption.checkbox==true){
					var col = $('#unitListGrid').datagrid('getColumnOption', field);
					cmenu.menu('appendItem', {
						text: col.title,
						name: field,
						iconCls: 'icon-ok'
					});       
				}
			}
		}
});

/**
 * 表单查询
 */
function searchUnitListGrid(formId,dataId){
	var unitname = $("#unitNameBaseSearch").val();
	$("#"+dataId).datagrid('load',{
		unitname: unitname
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