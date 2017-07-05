<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>组织机构授权</title>
<jsp:include page="/jsp/header.jsp" />
</head>    
<body>  
		<div style="width: 100%">
				<form id="orgRightFromSearc">            
				<table  cellpadding="0" cellspacing="0" class="tbl_search_bg" width="100%">      
					<tr>  
						<td class="tbl_td_label">类型：</td>                              
						<td>                  
							<input id='managerTypeSearch' name='managerType' class="easyui-combobox" data-options="editable:false,valueField:'ID',textField:'TEXT',
								url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=OWNERTYPE&showNumber=1,2,3,9'"/>
						</td>                 
						<td class="tbl_td_label">名称：</td>            
						<td>                  
							<input id='orgNameSearch' name='orgName' class="easyui-textbox" />
						</td>	     
					</tr> 
					<tr style="text-align: right;">
						<td colspan="4">
									<a  onclick='searchOrgListGrid("orgRightTreeGrid")' class="easyui-linkbutton" 
								data-options="iconCls:'icon-search'">查询</a>
									<a id="btn"  onclick='formClear("orgRightFromSearc");'
								class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a>  &nbsp;
						</td>
					</tr>         
				</table>
			</form>				
		</div>
		
		<div id="orgRightListDb"></div>  
	 	<table id='orgRightTreeGrid' class="easyui-treegrid" style="width：100%;" ></table> 
<script type="text/javascript"> 
$(function(){   
	/**
	 * 初始化查询Panel
	 */
	$('#orgRightSearchPanel').panel({    
		  collapsible:true,
		  width:"100%",
		  title:"查询"
	}); 
	/**
	 * 初始化列表组建
	 */
	$('#orgRightTreeGrid').treegrid({    
			url : '<%= request.getContextPath()%>/orgright/getOrgTreeGrid.do?cmd=getOrgTreeGrid', 
			rownumbers: true,
			animate:true,
			collapsible:true,
			width:'100%',
			fit:false, 
			height:480, 
			method:'post',  
			title:'组织机构树表',
			idField:"ID", 
			treeField:'ORGNAME',
			rownumbers:true,
		    columns:[[
		        {field:'ID',hidden:true},     
		        {field:'ORGNAME',title:'机构名称',width:260},   
		        {field:'ORGCODE',title:'机构编号' ,width:120,align:'center'} ,
		        {field:'SORTNAME',title:'机构简称' ,width:120,align:'center'} ,
		        {field:'ORGTYPE',title:'组织类型',width:80,align:'center'}, 
		        {field:'MANAGERTYPE',title:'管理类型',width:100,align:'center'}
		    ]],  
		    toolbar :['-',{  
				text:'查看', 
				iconCls:'icon-search',
				handler:function(){ 
					var selRows = $("#orgRightTreeGrid").datagrid("getSelections"); 
		            if(selRows.length !=1){     
		                $.messager.alert("警告",'请选择一条记录！','warning'); 
		                return false;
		            } 
					$('#orgRightListDb').panel('open').dialog('refresh', '<%= request.getContextPath()%>/jsp/sys/orgright/viewOrgRight.jsp');
				} 
			},{  
				text:'授权', 
				iconCls:'icon-save',
				handler:function(){ 
					var selRows = $("#orgRightTreeGrid").datagrid("getSelections"); 
		            if(selRows.length !=1){     
		                $.messager.alert("警告",'请选择一条记录！','warning'); 
		                return false;
		            } 
					$('#orgRightListDb').panel('open').dialog('refresh', '<%= request.getContextPath()%>/jsp/sys/orgright/orgRightTree.jsp');
				} 
			},'-']
		});
});
$('#orgRightListDb').dialog({    
	title:'机构授权',
	width:300,
    height:400,  
    top: winTop,
    left: winLeft,
    modal:true, 
    minimizable:false,
    maximizable:false,
    iconCls:'icon-save',
    closed:true, 
    href:'<%= request.getContextPath()%>/jsp/sys/orgright/orgRightTree.jsp'  
});  


/**
 * 表单查询
 */
function searchOrgListGrid(dataId){       
	var orgName = $("#orgNameSearch").val();
	var managerType = $("#managerTypeSearch").combobox("getValue");
   	if(orgName!=""||managerType!=""){               
   		$('#orgRightTreeGrid').treegrid({
   			onLoadSuccess:function() {
	   		
		       	$.ajax({                
		           url:"<%=request.getContextPath()%>/org/selectTreeData.do?cmd=selectTreeData",
		           data:{"orgName":orgName,"managerType":managerType} ,
		           dataType:"json",
		           cache:false,
		           async:true,
		           success:function(data){
		        	   debugger;
		        	   
		               $.each(data,function(i, obj){
		            	   
		                   var n = $("#orgRightTreeGrid").treegrid('select',obj.ID);
		                                                             
		               });   
		           }     
		       	});
   			}
   		});
   	}else{
   		var node = $('#orgRightTreeGrid').treegrid('getChecked');
       	for(var i = 0;i<node.length;i++){
	   	    $("#orgRightTreeGrid").treegrid('uncheck',node[i].target);  
	   	    $(node[i].target).removeClass('myselected1');
       	}     
       	var node = $('#orgRightTreeGrid').treegrid('getRoot'); 
   		$('#orgRightTreeGrid').treegrid('select',node.target);
   }
   
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