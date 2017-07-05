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
			<form id='ggUnitCX'>
				<table>      
					<tr>                   
						
						<td>名称：</td>            
						<td>        
							         
							<input id='unitNameBaseSearchCX' name='unitName' class="easyui-textbox" />
						</td>	
						<td>
						     <a href="javascript:searchunitListGridCX('ggUnitCX','unitListGridCX')" id="searchParEdit" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
						</td> 
						<td>
						     <a id="clearParEdit"  onclick='formClear("ggUnitCX");' class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a> 
						</td> 
					</tr> 
				</table>
			</form>
			<table id='unitListGridCX' style="width：100%;" ></table>
	</div>
<script type="text/javascript">
var jsonData = null;
$(function(){ 
	 var unitType=$("#unitTypeDd").val();
	 if(unitType==undefined){
		 unitType="";
	 }
	 
	 $('#unitListGridCX').datagrid({    
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
					var selRows = $('#unitListGridCX').datagrid("getSelections");
					if(selRows.length!=1){
						$.messager.alert("提示","请选择单位后再确定！");
						return false;
					}
					transportValueCX(selRows[0].ID,selRows[0].UNITNAME,selRows[0].UNITTYPE,selRows[0].UNITTYPENAME);
					$('#danweiynDdCX').dialog('close');
// 					closeCollUnit();
				} 
			},'-'],
			onHeaderContextMenu: function(e, field){
				 e.preventDefault();
				 if (!cmenu){
				      createColumnMenu('unitListGridCX');
				 }
				 cmenu.menu('show', {
				      left:e.pageX,
				      top:e.pageY
				 });
		   	}
		});	
		
});

/**
 * 表单查询
 */
function searchunitListGridCX(formId,dataId){
	var unitname = $("#unitNameBaseSearchCX").val();
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