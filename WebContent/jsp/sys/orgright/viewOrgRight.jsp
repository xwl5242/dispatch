<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>查看机构授权</title>
<link rel="stylesheet" id='easyuiTheme' type="text/css" href="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/icon.css"> 
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/jquery.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
	<link href="<%= request.getContextPath()%>/common/css/base.css" rel="stylesheet" type="text/css">
		<link href="<%= request.getContextPath()%>/common/css/ecc.css" rel="stylesheet" type="text/css">
			<link href="<%= request.getContextPath()%>/common/css/table.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div  class="easyui-panel" style="width:100%;height:330px"  >
		<ul id='orgRightTree'></ul>
	</div>
	<table cellpadding="0" cellspacing="0" class="tbl_search_bg" width="100%">
			<tr style="text-align: right;"> 
					<td colspan="4"> 
					   <a  onclick='checkOrgRightDgClocse()' class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">关闭</a>  &nbsp;
					</td>   
			</tr>
	</table> 
	<script type="text/javascript">
	/*初始化权限树形图  */ 
	var selRows = $("#myOrgRightGrid").datagrid("getSelections")
	var orgId = selRows[0].ID;
	$('#orgRightTree').tree({
	    url:'<%= request.getContextPath()%>/right/AllRightTreeData.do?cmd=AllRightTreeData', 
	    expandAll:true,
	    onLoadSuccess : function() {
	    	$('#orgRightTree').tree('expandAll'); 
	    	$.ajax({   
	            type: "post",   
	            url: "<%= request.getContextPath()%>/orgright/getRightByOrgId.do?cmd=getRightByOrgId",
	            data: {'orgId':orgId} ,//form表单序列化
	            dataType: "json", 
	            success: function(data){
	            	var da = data.list;
	            	for(var i=0;i<da.length;i++){
	            		var cknode=$('#orgRightTree').tree('find',da[i].RIGHTID);
	    	    		if(cknode){
			    	    	$('#orgRightTree').tree('update', {
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
	    },    
	    onCheck:function(node,checked){
	    	if(checked){
	    		var parentNode=$("#orgRightTree").tree("getParent",node.target);
		    	if(parentNode==null){
		    		return;
		    	} 
		    	$('#orgRightTree').tree('check',parentNode.target);   
		    	var childrens=$("#orgRightTree").tree("getChildren",parentNode);  
	    	}else{  
		    	var childrens=$("#orgRightTree").tree("getChildren",node.target);
		    	for(var i=0;i<childrens.length;i++){
		    		var chNode=childrens[i]; 
		    			$('#orgRightTree').tree('uncheck',chNode.target); 
		    	}    
	    	}  
	    } 
	}); 
	function checkOrgRightDgClocse(){ 
		$('#orgRightListDb').dialog("close"); 
	} 
</script>
</body>
</html>