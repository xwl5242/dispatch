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
		<div  class="easyui-panel" style="width:100%;height:340px"  >
				<ul id='mysysOnSelectOrgtree'></ul> 
		</div>
		<table cellpadding="0" cellspacing="0" class="tbl_search_bg" width="100%">
	 		<tr style="text-align: center;" >
		 		<td style="text-align: right;"> 
							   <a   onclick='OkOrgSelect()' class="easyui-linkbutton" data-options="iconCls:'icon-save'">确定</a>&nbsp;
				</td> 
	 		</tr>
	 	</table>
</div>
<script type="text/javascript"> 
$(function(){  
	var mysysOnSelectOrgtreeUrl='<%=request.getContextPath()%>/org/lodeTreeData.do?cmd=lodeTreeData';
	var mysysOnSelectOrgtreequeryParams={id:0};
	if(sysSelectOrg.flag==1){ 
		 mysysOnSelectOrgtreequeryParams={id:'${sessionScope.ECCORG.id}'}; 
		 mysysOnSelectOrgtreeUrl='<%=request.getContextPath()%>/org/lodeTreeDataByOrg.do?cmd=lodeTreeDataByOrg';
	} 
	$('#mysysOnSelectOrgtree').tree({  
	    url:mysysOnSelectOrgtreeUrl, 
	    queryParams:mysysOnSelectOrgtreequeryParams,
		onLoadSuccess : function() {
	    	var node = $('#mysysOnSelectOrgtree').tree('getRoot');
	    	$('#mysysOnSelectOrgtree').tree('select',node.target); 
	    }  
	});
});

function OkOrgSelect(){
	var nodes = $("#mysysOnSelectOrgtree").tree("getSelected"); 
    if(nodes ==null){     
        $.messager.alert("警告",'请选择一条记录！','warning'); 
        return false;
    } 
    sysSelectOrg.setData(nodes); 
    sysSelectOrg.success(nodes); 
    sysSelectOrg.close();    
}
</script>
</body> 
</html>