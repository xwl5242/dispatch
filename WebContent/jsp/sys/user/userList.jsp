<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>用户管理</title>
<jsp:include page="/jsp/header.jsp" />
</head>
<body> 
	 <div id="userSearchPanel"  style="width:100%;"  >
		 <form id="userFormSearch">
		  	<table id='a'  cellpadding="0" cellspacing="0" class="tbl_search_bg" width="100%">  
		 		<tr>
					        <td class="tbl_td_label"    width="13%">用户名称：</td> 
							<td  width="20%">
								<input id='userName' class="easyui-textbox" data-options="iconCls:'icon-search'"/>
							</td>   
							<td class="tbl_td_label" width="13%">登录账号：</td>  
							<td  width="20%">
								<input id='loginName'  class="easyui-textbox"  data-options="iconCls:'icon-man'"/> 
							</td>
							<td class="tbl_td_label" width="13%">用户状态：</td>
							<td  width="20%">
								<input id="userStat" class="easyui-combobox" name="userStat"   data-options="editable:false,valueField:'ID',textField:'TEXT',
								url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=USERSTATE'" />  
							</td>
				</tr> 
				<tr style="text-align: right;">
						<td colspan="6"> 
						   <a   onclick='searchUserListGrid()' class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&nbsp;
						   <a   onclick='formClear("userFormSearch");' class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a>  &nbsp;
						</td> 
				</tr>
			</table> 
		</form>
	 </div>
 <div id="addUserDd"></div> 
 <div id="updateUserDd"></div> 
	 	<table id='userlistGrid' style="width：100%;" ></table> 
<script type="text/javascript"> 
$(function(){   
	/**
	 * 初始化查询Panel
	 */
	$('#userSearchPanel').panel({    
		  collapsible:true,
		  width:"100%",
		  title:"查询"
	}); 
	/**
	 * 初始化列表组建
	 */
	
	$('#userlistGrid').datagrid({    
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
		        {field:'CREATETIME',title:'创建时间' ,width:100}
		    ]],  
		    toolbar :['-',{  
				text:'添加',
				iconCls:'icon-add',
				handler:function(){ 
					
					$('#addUserDd').panel('open').dialog('refresh', '<%= request.getContextPath()%>/jsp/sys/user/addUser.jsp');
				} 
			},'-',{
				text:'修改', 
				iconCls:'icon-edit',
				handler:function(){ 
					var selRows = $("#userlistGrid").datagrid("getSelections"); 
		            if(selRows.length !=1){     
		                $.messager.alert("警告",'请选择一条记录！','warning'); 
		                return false;
		            } 
		            
					$('#updateUserDd').panel('open').dialog('refresh', '<%= request.getContextPath()%>/jsp/sys/user/updateUser.jsp');
				}
			},'-',{ 
				text:'启用',
			    iconCls: 'icon-ok', 
			    handler: function(){ 
			    	var selRows = $("#userlistGrid").datagrid("getSelections"); 
		            if(selRows.length !=1){     
		                $.messager.alert("警告",'请选择一条记录！','warning'); 
		                return false;
		            } 
		            if(selRows[0].USERSTAT=="1"||selRows[0].USERSTAT=="启用"){
		            	 $.messager.alert("警告",'该用户已启用！','warning'); 
			                return false;
		            }
					$.messager.confirm('确认','您确认想要启用这个用户吗？',function(r){    
					    if (r){    
					    	$.messager.progress();//打开进度条
							$.ajax({ 
					            type: "post", 
					            url: "<%= request.getContextPath()%>/user/enableUser.do?cmd=enableUser",
					            data:{"userId":selRows[0].ID} ,//form表单序列化
					            dataType: "json", 
					            success: function(data){ 
					            	 $.messager.progress('close');//关闭进度条
					            	if(data.flag){ 
							    		 $('#userlistGrid').datagrid('reload');
									     $.messager.alert("提示",data.messager,'info'); 
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
					}); 
				}
			
		    },'-',{
				text:'停用',
			    iconCls: 'icon-no', 
			    handler: function(){ 
			    	var selRows = $("#userlistGrid").datagrid("getSelections"); 
		            if(selRows.length !=1){     
		                $.messager.alert("警告",'请选择一条记录！','warning'); 
		                return false;
		            } 
		            if(selRows[0].USERSTAT=="2"||selRows[0].USERSTAT=="停用"){
		            	 $.messager.alert("警告",'该用户已停用！','warning'); 
			                return false;
		            }
					$.messager.confirm('确认','您确认想要停用这个用户吗？',function(r){    
					    if (r){    
					    	$.messager.progress();//打开进度条
							$.ajax({ 
					            type: "post", 
					            url: "<%= request.getContextPath()%>/user/disableUser.do?cmd=disableUser",
					            data:{"userId":selRows[0].ID} ,//form表单序列化
					            dataType: "json", 
					            success: function(data){ 
					            	 $.messager.progress('close');//关闭进度条
					            	if(data.flag){ 
							    		
									     $.messager.alert("提示",data.messager,'info'); 
									     $('#userlistGrid').datagrid('reload');
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
					}); 
				}
		    },'-',{
				text:'导出',
			    iconCls: 'icon-excel', 
			    handler: function(){ 
			 	   $.messager.confirm("确定",'确定导出？',function(r){
			            if(r){
			                $.messager.alert("信息",'导出成功','info');
			            }else{
			                return false; 
			            }
			        });
			    }
		    },'-'],
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
						$('#userlistGrid').datagrid('hideColumn', item.name);
						cmenu.menu('setIcon', {
							target: item.target,
							iconCls: 'icon-empty'
						});
					} else {
						$('#userlistGrid').datagrid('showColumn', item.name);
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
			var fields = $('#userlistGrid').datagrid('getColumnFields');
			for(var i=0; i<fields.length; i++){
				var field = fields[i];
				var columenOption=$('#userlistGrid').datagrid('getColumnOption',field);
				if(!columenOption.checkbox==true){
					var col = $('#userlistGrid').datagrid('getColumnOption', field);
					cmenu.menu('appendItem', {
						text: col.title,
						name: field,
						iconCls: 'icon-ok'
					});       
				}
			}
		}
		/**
		 * 添加用户窗口
		 * 
		 */
		
});

$('#addUserDd').dialog({    
	title:'添加用户',
	width:600,
    height:'auto', 
    top: winTop,
    left: winLeft,
    modal:true, 
    minimizable:false,
    maximizable:false,
    iconCls:'icon-save',
    closed:true, 
    href:'<%= request.getContextPath()%>/jsp/sys/user/addUser.jsp'  
});
$('#updateUserDd').dialog({    
	title:'编辑用户',
	width:600,
    height:'auto', 
    top: winTop,
    left: winLeft,
    modal:true, 
    minimizable:false,
    maximizable:false,
    iconCls:'icon-edit',
    closed:true, 
    href:'<%= request.getContextPath()%>/jsp/sys/user/updateUser.jsp'  
}); 
/**
 * 列表查询
 * 根据查询条件刷新列表数据
 */
function searchUserListGrid(){
	var userNameSearch =$("#userName").val();
	var loginNameSearch =$("#loginName").val();
	var userStatSearch =$("#userStat").combobox("getValue");
	
	$('#userlistGrid').datagrid('load',{
		userName: userNameSearch,
		loginName: loginNameSearch,
		userStat:userStatSearch 
	});
}

function myEmpSelect() {
    $('#myEmpSelect').dialog({    
    	title:'选择员工',
    	width:600, 
        height:420, 
        top: winTop,
	    left: winLeft,
        modal:true, 
        minimizable:false,
        maximizable:false,
        iconCls:'icon-save',
        closed:true, 
        href:''  
    }); 
    this.flag=0;
    this.data={};
    this.open=function(){
		$('#myEmpSelect').panel('open').dialog('refresh', '<%= request.getContextPath()%>/jsp/sys/employee/sysOnSelectEmp.jsp');
    };
    this.close=function(){
    	$('#myEmpSelect').panel('close');
    }
    this.setData=function(sdata){
    	this.data=sdata;
    };
    this.getData=function(){
    	return this.data;
    };
    this.success=function(){};
}

var sysSelectEmp =new myEmpSelect(); 

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