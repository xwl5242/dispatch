<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>用户授权</title>
<jsp:include page="/jsp/header.jsp" />
</head>
<body> 
	 <div id="userRightSearchPanel"  style="width:100%;"  >
		 <form id="userRightSearch">
		  	<table   cellpadding="0" cellspacing="0" class="tbl_search_bg" width="100%">  
		 		<tr>
					        <td class="tbl_td_label"    width="13%">用户名称：</td> 
							<td  width="20%">
								<input id='userRightName' class="easyui-textbox" data-options="iconCls:'icon-search'"/>
							</td>   
							<td class="tbl_td_label" width="13%">登录账号：</td>  
							<td  width="20%">
								<input id='loginRightName'  class="easyui-textbox"  data-options="iconCls:'icon-man'"/> 
							</td>
							<td class="tbl_td_label" width="13%">用户状态：</td>
							<td  width="20%">
								<input id="userRightStat" class="easyui-combobox" name="userStat"   data-options="editable:false,valueField:'id',textField:'text',
								url:'<%= request.getContextPath()%>/jsp/sys/user/combobox.json'" />  
							</td>
				</tr> 
				<tr style="text-align: right;">
						<td colspan="6"> 
						   <a   onclick='searchuserRightListGrid()' class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&nbsp;
						   <a   onclick='formClear("userRightSearch");' class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a>  &nbsp;
						</td> 
				</tr>
			</table> 
		</form>
	 </div>
 <div id="adduserRightDd"></div> 
 <div id="adduserorgRightDd" ></div>
 <div id="adduserroleRightDd"></div>
	 	<table id='userRightlistGrid' style="width：100%;" ></table> 
<script type="text/javascript"> 
$(function(){   
	/**
	 * 初始化查询Panel
	 */
	$('#userRightSearchPanel').panel({    
		  collapsible:true,
		  width:"100%",
		  title:"查询"
	}); 
	/**
	 * 初始化列表组建
	 */
	$('#userRightlistGrid').datagrid({    
			url : '<%= request.getContextPath()%>/user/getUserList.do?cmd=getUserList',  
			width:'100%',
			pageNumber:1,
			pageSize:pageNum,
			fit:false,
			height:gridHeight,   
			method:'post',  
			title:'用户信息列表',
			idField:"ID",
			striped:true,
			ctrlSelect:true, 
			rownumbers:true,
			fitColumns: true,   
			pagination : true,
		    columns:[[
		        {field:'ID',checkbox:true},     
		        {field:'USERNAME',title:'用户名称' ,width:100} ,
		        {field:'LOGINNAME',title:'登录账号',width:100},   
		        {field:'USERSTAT',title:'用户状态',width:100},    
		        {field:'MOBILE',title:'联系电话' ,width:100},
		        {field:'MAIL',title:'电子邮箱' ,width:100},
		        {field:'LASTLOGINTIME',title:'上次登录时间' ,width:100},
		        {field:'LOGINCOUNT',title:'登录次数' ,width:100},
		        {field:'CREATETIME',title:'创建时间' ,width:100},
		        {field:'CREATEMAN',title:'创建人' ,width:100}
		    ]],  
		    toolbar :['-',{  
				text:'授权权限',
				iconCls:'icon-edit',
				handler:function(){ 
					var selRows = $("#userRightlistGrid").datagrid("getSelections"); 
		            if(selRows.length !=1){     
		                $.messager.alert("警告",'请选择一条记录！','warning'); 
		                return false;
		            } 
					$('#adduserRightDd').panel('open').dialog('refresh', '<%= request.getContextPath()%>/jsp/sys/userright/userrighttree.jsp');
				} 
			},'-',{
				text:'授权组织', 
				iconCls:'icon-edit',
				handler:function(){ 
					var selRows = $("#userRightlistGrid").datagrid("getSelections"); 
		            if(selRows.length !=1){     
		                $.messager.alert("警告",'请选择一条记录！','warning'); 
		                return false;
		            } 
		            var id=selRows[0].ID;
					$('#adduserorgRightDd').panel('open').dialog('refresh', '<%= request.getContextPath()%>/userright/getOrgByUserId.do?id='+id);
				} 
			},'-',{ 
				text:'授权角色',
			    iconCls: 'icon-edit', 
			    handler: function(){ 
			    	var selRows = $("#userRightlistGrid").datagrid("getSelections"); 
		            if(selRows.length !=1){     
		                $.messager.alert("警告",'请选择一条记录！','warning'); 
		                return false;
		            } 
		            
					$('#adduserroleRightDd').panel('open').dialog('refresh', '<%= request.getContextPath()%>/jsp/sys/userright/userroleright.jsp');
				}
			
		    },'-'],
		    onHeaderContextMenu: function(e, field){
				 e.preventDefault();
				 if (!cmenu){
				      createColumnMenu('userRightlistGrid');
				 }
				 cmenu.menu('show', {
				      left:e.pageX,
				      top:e.pageY
				 });
		   	},onLoadSuccess:function(data){
		           var selRows = $("#userRightlistGrid").datagrid ("getSelections"); 
		           if(selRows.length>0){
		           		 $("#userRightlistGrid").datagrid("clearChecked");	
		           }
			   }
		});
	
		/**
		 * 添加用户窗口
		 * 
		 */
		$('#adduserRightDd').dialog({    
			title:'权限授权',
			width:'300px', 
			height:'auto', 
			top: winTop,
		    left: winLeft,
		    modal:true,   
		    minimizable:false,
		    maximizable:false,
		    iconCls:'icon-save',
		    closed:true, 
		    href:''  
		});  
		/**
		 */
		$('#adduserorgRightDd').dialog({    
			title:'组织授权',
			width:'1024px',    
			height:500,   
			top: winTop,
		    left: winLeft,
		    modal:true,   
		    minimizable:false,
		    maximizable:false,
		    iconCls:'icon-save',
		    closed:true, 
		    href:''  
		});
		$('#adduserroleRightDd').dialog({    
			title:'角色授权',
			width:'1100px',      
			height:530,   
			top: winTop,
		    left: winLeft,
		    modal:true,   
		    minimizable:false,
		    maximizable:false,
		    iconCls:'icon-save',
		    closed:true, 
		    href:''  
		});
});
/**
 * 列表查询
 * 根据查询条件刷新列表数据
 */
function searchuserRightListGrid(){
	var userNameSearch =$("#userRightName").val();
	var loginNameSearch =$("#loginRightName").val();
	var userStatSearch =$("#userRightStat").combobox("getValue");
	
	$('#userRightlistGrid').datagrid('load',{
		userName: userNameSearch,
		loginName: loginNameSearch,
		userStat:userStatSearch 
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