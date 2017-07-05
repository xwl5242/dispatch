<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>授权组织机构</title>
<jsp:include page="/jsp/header.jsp" />
</head>
<body>  
<div  class="easyui-layout" data-options="fit:true" style="height: 100%;">
		<div data-options="region:'west',title:'组织机构列表',collapsible:false" style="width: 650px">
				<form id="myUserOrgRightFromSearch"  style="width: 100%;height: auto;">            
					<table>      
						<tr>  
							<td>类型：</td>                              
							<td>   
							    <input type="hidden" id="orgIdMyUser" value=""/>               
								<input id='managerTypeSearch' name='managerType' class="easyui-combobox" data-options="editable:false,valueField:'ID',textField:'TEXT',
									url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=OWNERTYPE&showNumber=1,3,9'"/>
							</td>                 
							<td>名称：</td>            
							<td>                  
								<input id='orgNameSearch' name='orgName' class="easyui-textbox" />
							</td>
							<td>
							    <a  onclick='searchOrgListGrid("myUserOrgRighGrid")' class="easyui-linkbutton" 
									data-options="iconCls:'icon-search'">查询</a>
								
							</td>	
							<td>
							   
								<a  onclick='formClear("myUserOrgRightFromSearch")' class="easyui-linkbutton" 
									data-options="iconCls:'icon-redo'">重置</a>
							</td>
								     
						</tr>          
					</table>
				</form>		
				<table id='myUserOrgRighGrid' style="width：100%;" ></table>					
		</div>
	<div   data-options="region:'center',title:'所选组织权限'" >
		<ul id='userorgRightTree' ></ul>
	</div>
	<div   data-options="region:'south'" style="height: 28px" > 
		<table cellpadding="0" cellspacing="0" class="tbl_search_bg" width="100%">
				<tr style="text-align: right;">  
						<td colspan="4"> 
						   <a   onclick='saveuserorgRight()' class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>&nbsp;
						   <a  onclick='checkuserorgRightDgClocseA()' class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">关闭</a>  &nbsp;
						</td>   
				</tr>
		</table> 
	</div>
<script type="text/javascript"> 
debugger;
var orgList=eval("(" + '${list}' + ")") ;
var code=""; 
$.each(orgList,function(index,obj){
	code=obj.ORGCODE;
});
var jsonData = null;
$(function(){
	 $('#myUserOrgRighGrid').datagrid({    
			url : '<%= request.getContextPath()%>/org/selectOrgList.do?cmd=selectOrgList',  
			width:'100%',
			pageNumber:1,
			pageSize:pageNum,
			fit:false,
			height:gridHeight,    
			title:'组织机构列表', 
			idField:"ID",
			striped:true,
			ctrlSelect:true, 
			rownumbers:true,
			fitColumns: true,   
			pagination : true,
			singleSelect : true,
			columns:[[
				{field:'ID',checkbox:true},     
				{field:'ORGNAME',title:'名称' ,width:100}, 
				{field:'SORTNAME',title:'简称' ,width:100},
		        {field:'JIANPIN',title:'简拼' ,width:100}
			]],
			onSelect:function(rowIndex,rowData){
				$("#orgIdMyUser").val(rowData.ID);
				$('#userorgRightTree').tree("reload"); 
			},
			 onLoadSuccess : function() {
				 $('#myUserOrgRighGrid').datagrid('selectRecord',code);
			 },
			onHeaderContextMenu: function(e, field){
				 e.preventDefault();
				 if (!cmenu){
				      createColumnMenu('myUserOrgRighGrid');
				 }
				 cmenu.menu('show', {
				      left:e.pageX,
				      top:e.pageY
				 });
		   	}
		});	
	
	<%-- $('#myUserOrgRightTree').tree({ 
	    url:'<%=request.getContextPath()%>/org/lodeTreeData.do?cmd=lodeTreeData&status=1',
//  	    checkbox:true,
	    cascadeCheck:false,
	    onLoadSuccess : function() {
	    	$('#myUserOrgRightTree').tree('expandAll');
	    	var n = $("#myUserOrgRightTree").tree('find',code);
	    	if(n){
	    		$("#orgIdMyUser").val(code);
                $("#myUserOrgRightTree").tree('select',n.target);  
                $('#userorgRightTree').tree("reload"); 
            } 
	    },
	    onSelect : function(node) {
	    	if(jsonData!=null){
	    		qxSelected(jsonData);
	    	}
	    	$(node.target).removeClass('myselected1');
	    	$("#orgIdMyUser").val(node.id);
	    	$('#userorgRightTree').tree("reload"); 
	    	
	    	  
		},
		onCheck:function(node){
			$("#orgIdMyUser").val(node.id);
			$('#userorgRightTree').tree("reload"); 
			
		}
	}); --%>
});
function formClear(formId){
	$('#'+formId).form('clear');
}
function saveuserorgRight(){ 
	var seluserRows = $("#userRightlistGrid").datagrid("getSelections");
	var selRows = $("#myUserOrgRighGrid").datagrid("getSelections");
	if(selRows.length !=1){     
    	$.messager.alert("警告",'请选择一个组织机构！','warning'); 
        return false;
    } 
	var orgIds = selRows[0].ID; 
// 	for(var i=0; i<nodes.length; i++){
// 		if (orgIds != '') orgIds += ','; 
// 		orgIds = nodes[i].id;
// 	} 
	debugger;
	$.messager.progress();
	$.ajax({  
        type: "post", 
        url: "<%= request.getContextPath()%>/userright/saveUserOrgRight.do?cmd=saveUserOrgRight",
        data: {'orgIds':orgIds,'userId':seluserRows[0].ID} ,//form表单序列化
        dataType: "json", 
        success: function(data){
        	 $.messager.progress('close');//关闭进度条
        	if(data.flag){ 
			     $.messager.alert("提示",data.messager,'info',checkuserorgRightDgClocse()); 
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
function checkuserorgRightDgClocse(){ 
	$('#adduserorgRightDd').dialog("close"); 
} 
function checkuserorgRightDgClocseA(){ 
	$.messager.confirm('确认','您确认想要关闭吗？',function(r){    
	    if (r){ 
			$('#adduserorgRightDd').dialog("close"); 
		}
 	});
} 
$('#userorgRightTree').tree({
    url:'<%= request.getContextPath()%>/right/AllRightTreeData.do?cmd=AllRightTreeData', 
   	height:400,
    expandAll:true,
    onLoadSuccess : function() {
    	$('#userorgRightTree').tree('expandAll'); 
    	var orgIds=$("#orgIdMyUser").val();
    	$.ajax({   
            type: "post",   
            url: "<%= request.getContextPath()%>/orgright/getRightByOrgId.do?cmd=getRightByOrgId",
            data: {'orgId':orgIds} ,//form表单序列化
            dataType: "json", 
            async: false,
            success: function(data){
            	var da = data.list;
            	for(var i=0;i<da.length;i++){
            		var cknode=$('#userorgRightTree').tree('find',da[i].RIGHTID);
            		if(cknode){
		    	    	$('#userorgRightTree').tree('update', { 
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





function searchOrgListGrid(dataId){  
	var orgName = $("#orgNameSearch").val();
	var managerType = $("#managerTypeSearch").combobox("getValue");
	$("#"+dataId).datagrid('load',{
		orgName: orgName,
		managerType :managerType
	});
}

function qxSelected(data){
	$.each(data,function(i, obj){
        var n = $("#myUserOrgRightTree").tree('find',obj.ID);
        if(n){
            $(n.target).removeClass('myselected1');
        }                                             
    });   
}





</script>
</body> 
</html>