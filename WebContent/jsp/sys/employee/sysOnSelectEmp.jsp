<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>员工管理</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link rel="stylesheet" id='easyuiTheme' type="text/css" href="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/icon.css"> 
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/jquery.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
	<link href="<%= request.getContextPath()%>/common/css/base.css" rel="stylesheet" type="text/css">
		<link href="<%= request.getContextPath()%>/common/css/ecc.css" rel="stylesheet" type="text/css">
			<link href="<%= request.getContextPath()%>/common/css/table.css" rel="stylesheet" type="text/css">
</head>
<body> 
	<div  class="easyui-layout" data-options="fit:true">
		<div data-options="region:'west',title:'组织机构树'" style="width: 160px">
				<ul id='mysysOnSelectEmpOrgtree'></ul> 
		</div>
	<div   data-options="region:'center',title:'员工信息'" >
	 <div id="sysOnSelectEmpSearchPanel"  style="width:100%;" >  
		 <form id="sysOnSelectEmpFromSearc">
		  	<table  cellpadding="0" cellspacing="0" class="tbl_search_bg" width="100%">  
		 		<tr>
					        <td class="tbl_td_label"    width="13%">员工名称：</td> 
							<td  width="20%">
								<input id='EmpName' class="easyui-textbox" data-options="iconCls:'icon-search'"/>
							</td>   
							<td class="tbl_td_label" width="13%">工号：</td>  
							<td  width="20%">
								<input id='Empcode'  class="easyui-textbox"  data-options="iconCls:'icon-man'"/> 
							</td>
				</tr> 
				<tr >
							<td class="tbl_td_label" width="13%">性别：</td>
							<td  width="20%">
								<input id="EmpSex" class="easyui-combobox" name="EmpSex"   data-options="editable:false,valueField:'ID',textField:'TEXT',  
								url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=SEX'" />
							</td>
						<td style="text-align: right;" colspan="2"> 
						   <a id="btn"  onclick='searchsysOnSelectEmpListGrid()' class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&nbsp;
						   <a id="btn"  onclick='formClear("sysOnSelectEmpFromSearc");' class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a>  &nbsp;
						</td> 
				</tr>
			</table> 
		</form>
	 </div>
	 	<table id='sysOnSelectEmplistGrid' style="width：100%;" ></table> 
	 	<table cellpadding="0" cellspacing="0" class="tbl_search_bg" width="100%">
	 		<tr style="text-align: center;" >
		 		<td style="text-align: right;"> 
							   <a   onclick='OkEmpSelect()' class="easyui-linkbutton" data-options="iconCls:'icon-save'">确定</a>&nbsp;
				</td> 
	 		</tr>
	 	</table>
</div>
<script type="text/javascript"> 
$(function(){  
	var mysysOnSelectEmpOrgtreeUrl='<%=request.getContextPath()%>/org/lodeTreeData.do?cmd=lodeTreeData';
	var mysysOnSelectEmpOrgtreequeryParams={id:0};
	if(sysSelectEmp.flag==1){ 
		 mysysOnSelectEmpOrgtreequeryParams={id:'${sessionScope.ECCORG.id}'}; 
		 mysysOnSelectEmpOrgtreeUrl='<%=request.getContextPath()%>/org/lodeTreeDataByOrg.do?cmd=lodeTreeDataByOrg';
	} 
	$('#mysysOnSelectEmpOrgtree').tree({ 
	    url:mysysOnSelectEmpOrgtreeUrl, 
	    queryParams:mysysOnSelectEmpOrgtreequeryParams,
	    onSelect : function(node) {
	    	$('#sysOnSelectEmplistGrid').datagrid('load',{
	    		orgId: node.id
	    	});
		},
		onLoadSuccess : function() {
	    	var node = $('#mysysOnSelectEmpOrgtree').tree('getRoot');
	    	$('#mysysOnSelectEmpOrgtree').tree('select',node.target); 
	    } 
	});
	/**
	 * 初始化查询Panel
	 */
	$('#sysOnSelectEmpSearchPanel').panel({    
		  collapsible:true,
		  width:"100%",
		  title:"查询"
	}); 
	/**
	 * 初始化列表组建
	 */
	$('#sysOnSelectEmplistGrid').datagrid({    
			 url : '<%= request.getContextPath()%>/employee/getEmpLoyeeList.do?cmd=getEmpLoyeeList', 
			width:'100%',
			pageNumber:1,
			pageSize:10,
			fit:false,
			height:248,   
			method:'post',  
			title:'员工信息列表',
			idField:"ID",
			ctrlSelect:true,
			sortName:'CREATETIME',
			sortOrder:'desc',
			rownumbers:true,
			fitColumns: true,   
			pagination : true,
			onBeforeLoad:function(param){
				if(typeof(param.orgId) == "undefined"){
					return false;
				} 
			},
		    columns:[[
		  	        {field:'ID',hidden:true},     
			        {field:'NAME',title:'员工名称' ,width:100} ,
			        {field:'AGE',title:'年龄',widht:60},   
			        {field:'MOBILE',title:'联系电话',widht:120},
			        {field:'CODE',title:'工号' ,widht:80},
			        {field:'SEX',title:'性别' ,widht:60}
		    ]],
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
						$('#sysOnSelectEmplistGrid').datagrid('hideColumn', item.name);
						cmenu.menu('setIcon', {
							target: item.target,
							iconCls: 'icon-empty'
						});
					} else {
						$('#sysOnSelectEmplistGrid').datagrid('showColumn', item.name);
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
			var fields = $('#sysOnSelectEmplistGrid').datagrid('getColumnFields');
			for(var i=0; i<fields.length; i++){
				var field = fields[i];
				var columenOption=$('#sysOnSelectEmplistGrid').datagrid('getColumnOption',field);
				if(!columenOption.checkbox==true){
					var col = $('#sysOnSelectEmplistGrid').datagrid('getColumnOption', field);
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
 * 列表查询
 * 根据查询条件刷新列表数据
 */
function searchsysOnSelectEmpListGrid(){
	var EmpNameSearch =$("#EmpName").val();
	var EmpcodeSearch =$("#Empcode").val();
	var EmpSexSearch =$("#EmpSex").combobox("getValue");
	var node = $('#mysysOnSelectEmpOrgtree').tree('getSelected'); 
	$('#sysOnSelectEmplistGrid').datagrid('load',{
		EmpName: EmpNameSearch,
		Empcode: EmpcodeSearch,
		EmpSex :  EmpSexSearch ,
		orgId:node.id
	});
}
/**
 * 表单重置
 * 参数：表单id 
 */
function formClear(formId){
	$('#'+formId).form('clear');
}

function OkEmpSelect(){
	var selRows = $("#sysOnSelectEmplistGrid").datagrid("getSelections"); 
    if(selRows.length !=1){     
        $.messager.alert("警告",'请选择一条记录！','warning'); 
        return false;
    } 
    sysSelectEmp.setData(selRows[0]);
    sysSelectEmp.success(selRows[0]);
    sysSelectEmp.close();
}
</script>
</body> 
</html>