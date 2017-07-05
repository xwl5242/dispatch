<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>机构授权</title>
<link rel="stylesheet" id='easyuiTheme' type="text/css" href="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/icon.css"> 
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/jquery.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
	<link href="<%= request.getContextPath()%>/common/css/base.css" rel="stylesheet" type="text/css">
		<link href="<%= request.getContextPath()%>/common/css/ecc.css" rel="stylesheet" type="text/css">
			<link href="<%= request.getContextPath()%>/common/css/table.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div>
		<a onclick='selectAll("orgRightTree")' class="easyui-linkbutton" data-options="iconCls:'icon-save'">全选</a>
		<a onclick='unSelectAll("orgRightTree")' class="easyui-linkbutton" data-options="iconCls:'icon-save'">反选</a>
	</div>
	<div  class="easyui-panel" style="width:100%;height:330px"  >
		<ul id='orgRightTree'></ul>
	</div>
	<table cellpadding="0" cellspacing="0" class="tbl_search_bg" width="100%">
			<tr style="text-align: right;"> 
					<td colspan="4"> 
					   <a   onclick='saveOrgRight()' class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>&nbsp;
					   <a  onclick='checkOrgRightDgClocseA()' class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">关闭</a>  &nbsp;
					</td>   
			</tr>
	</table> 
	<script type="text/javascript">
	/*初始化权限树形图  */ 
	var idList = null;
	var selRows = $("#myOrgRightGrid").datagrid("getSelections")
	var orgId = selRows[0].ID; 
	
	$('#orgRightTree').tree({
	    url:'<%= request.getContextPath()%>/right/AllRightTreeData.do?cmd=AllRightTreeData', 
	    checkbox:true,
	    cascadeCheck:false,
	    onLoadSuccess : function() {
	    	$('#orgRightTree').tree('expandAll'); 
	    	$.ajax({   
	            type: "post",   
	            url: "<%= request.getContextPath()%>/orgright/getRightByOrgId.do?cmd=getRightByOrgId",
	            data: {'orgId':orgId} ,//form表单序列化
	            dataType: "json", 
	            success: function(data){ 
	            	var da = data.list;
	            	idList = data.list1;
	            	for(var i=0;i<da.length;i++){
	            		var cknode=$('#orgRightTree').tree('find',da[i].RIGHTID);
	            		if(cknode){
			    	    	$('#orgRightTree').tree('check',cknode.target);  
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
	function saveOrgRight(){         
 		var nodes = $('#orgRightTree').tree('getChecked');
		var rightIds = '';
		for(var i=0; i<nodes.length; i++){
			if (rightIds != '') rightIds += ','; 
			rightIds += nodes[i].id;
		} 
		$.messager.progress();
		$.ajax({  
            type: "post", 
            url: "<%= request.getContextPath()%>/orgright/checkOrgRight.do?cmd=checkOrgRight",
            data: {'rightIds':rightIds,'orgId':orgId} ,//form表单序列化
            dataType: "json", 
            success: function(data){
            	 $.messager.progress('close');//关闭进度条
            	if(data.flag){ 
		    		 $('#userlistGrid').datagrid('reload');
				     $.messager.alert("提示",data.messager,'info',checkOrgRightDgClocse()); 
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
	function checkOrgRightDgClocseA(){ 
		$.messager.confirm('确认','您确认想要关闭吗？',function(r){    
		    if (r){ 
		    	$('#orgRightListDb').dialog("close"); 
			}
	 	});
		
	} 
	function checkOrgRightDgClocse(){ 
    	$('#orgRightListDb').dialog("close"); 
	} 
	
	function selectAll(treeMenu){
        for ( var i = 0; i < idList.length; i++) {  
            var node = $('#' + treeMenu).tree('find', idList[i].ID);//查找节点 
            if(node!=null){
	            $('#' + treeMenu).tree('check', node.target);//将得到的节点选中 
            }
	    } 
	}
	
	function unSelectAll(treeMenu){
        for ( var i = 0; i < idList.length; i++) {  
            var node = $('#' + treeMenu).tree('find', idList[i].ID);//查找节点 
            if(node!=null){
	            $('#' + treeMenu).tree('uncheck', node.target);//将得到的节点选中 
            }
	    } 
	}
</script>
</body>
</html>