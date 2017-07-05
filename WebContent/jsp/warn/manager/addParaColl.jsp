<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/header.jsp" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>采集点列表</title>
</head>
<body>     
	<div id="collUnitTree" class="easyui-layout" data-options="fit:true" style="height：500px;">
			<form id="ParcollFromSearc">
				<table cellpadding="0" cellspacing="0" class="tbl_search_bg"
					width="100%">
					<tr>
						<td class="tbl_td_label" width="13%">编号：</td>
						<td width="20%"><input id='ParcollFromSearc-collectCode'
							class="easyui-textbox" /></td>
						<td class="tbl_td_label" width="13%">名称：</td>
						<td width="20%"><input id='ParcollFromSearc-collectName'
							class="easyui-textbox" /></td>
					</tr>
					<tr>
						<td class="tbl_td_label" width="13%">位置：</td>
						<td width="20%"><input id='ParcollFromSearc-collectLocation'
							class="easyui-textbox" /></td>
					</tr>
					<tr style="text-align: right;">
						<td colspan="6"><a id="serchColl"
							onclick='searchCollListGrid("ParcollFromSearc","PCListGridAdd")'
							class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&nbsp;
							<a id="clearColl" onclick='formClear("ParcollFromSearc");'
							class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a>
							&nbsp;</td>
					</tr>
				</table> 
			</form>
			<table id='PCListGridAdd' style="width：100%;"></table> 
				<input id="unit" name="unit" type="hidden" />
	</div>

	<script type="text/javascript">
	var unitid = $('#temp-unitId').val();
	$("#unit").val(unitid);
$(function(){ 
		$('#PCListGridAdd').datagrid({    
			url : '<%= request.getContextPath()%>/coll/queryCollInfoList.do?cmd=queryCollInfoList',  
			width:'100%',
			pageNumber:1,
			pageSize:pageNum,
			queryParams:{unitId:$('#unit').val()},
			singleSelect:true,
			fit:false,
			height:310,   
			method:'post',  
			title:'采集点列表',
			idField:"ID",
			striped:true,
			ctrlSelect:true, 
			rownumbers:true,
			fitColumns: true,   
			pagination : true,
			columns:[[
				{field:'ID',checkbox:true},     
				{field:'UNITNAME',title:'换热站/锅炉房' ,width:'20%'}, 
		        {field:'COLLNAME',title:'名称' ,width:'20%'},
		        {field:'STATUS',title:'状态',width:'10%',
		        	formatter: function(value,row,index){
						if (row.STATUS==1){
							return "正常";
						} else {
							return "关闭";
						}
					}
				}
			]],
			hideColumn:[[{field:'ID'}]],
			toolbar :['-',{  
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){ 
		            makesure();
			  }
			}],
		    onHeaderContextMenu: function(e, field){
				 e.preventDefault();
				 if (!cmenu){
				      createColumnMenu('PCListGridAdd');
				 }
				 cmenu.menu('show', {
				      left:e.pageX,
				      top:e.pageY
				 });
		   	}
		});	
});	
function searchCollListGrid(){
	var CollectCode =$('#ParcollFromSearc-collectCode').val();
	var collectName =$('#ParcollFromSearc-collectName').val();
	var collectLocation =$('#ParcollFromSearc-collectLocation').val();
	$('#ManagerlistGrid').datagrid('load',{
		CollectCode:CollectCode,
		collectName:collectName,
		collectLocation:collectLocation
	});
}
/**
 * 表单重置
 * 参数：表单id 
 */
function formClear(formId){
	$('#'+formId).form('clear');
}

function makesure(){
	
	var selRows = $("#PCListGridAdd").datagrid("getSelections"); 
    if(selRows.length !=1){     
        $.messager.alert("警告",'请选择一条记录！','warning'); 
        return false;
    } 
	$('#collId').textbox({value:selRows[0].ID});
	$('#collectName').textbox({value:selRows[0].COLLNAME});
	$("#collId").next("span").hide();
	$('#addColl').dialog('close');
}

/**
 * 关闭添加对话窗体
 */
function addPCdgClocse(){ 
	$('#addParCollDd').dialog("close");
}

</script>
</body>
</html>