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

	<div id="top" style="position:absolute;height: 30px; ">   
		
		<input type="hidden" id="cc" name="cc"/>
		<a   onclick='sureOrgData();' class="easyui-linkbutton" data-options="iconCls:'icon-ok'">确定</a>
		<a   onclick='tjOrgDgClocse();' class="easyui-linkbutton" data-options="iconCls:'icon-ok'">关闭</a>
	</div>
	<div id="bottom" style="padding-top: 30px;">
		<ul id='tjOrgtree'></ul>     
	</div>
       
	<script type="text/javascript">
	$('#tjOrgtree').tree({ 
	    url:'<%=request.getContextPath()%>/org/findTreeData.do?cmd=findTreeData',
	    cascadeCheck:false,
	    onLoadSuccess : function() {
	    	$('#tjOrgtree').tree('expandAll'); 
	    	var node = $('#tjOrgtree').tree('getRoot'); 
	    	$('#tjOrgtree').tree('select',node.target);
	    },
	    onSelect : function(node) {
	    	$("#cc").val(node.id);
		}
	});
	
	
	function tjOrgDgClocse(){ 
		$('#tjOrgDd').dialog("close");
	} 
	/* 
	 确定
	*/
	function sureOrgData(){
		 //赋值给隐藏的id
		var sureId = $('#cc').val(); 
		var nodes = $('#myOrgtree').tree('getChecked'); 
		var ids = null;
		for(var i=0;i<nodes.length;i++){
			ids += "," + nodes[i].id;
		}
		$.ajax({ 
		    type: "post", 
		    url: "<%=request.getContextPath()%>/org/tjOrg.do?cmd=tjOrg",
			data: {"sureId":sureId,"ids":ids},//form表单序列化
			dataType : "json",
			success : function(data) {
				$.messager.progress('close');//关闭进度条
				if (data.flag) {
					$('#myOrgtree').tree('reload');
					$.messager.alert("提示", data.messager, 'info',tjOrgDgClocse());
				} else {
					$.messager.alert("警告", data.messager, 'warning');
				}
			}
					
		});
	}
	</script>
</body>
</html>