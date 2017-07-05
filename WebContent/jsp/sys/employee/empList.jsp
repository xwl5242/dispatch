<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>员工管理</title>
<jsp:include page="/jsp/header.jsp" />
</head>
<body> 
<div  class="easyui-layout" data-options="fit:true">
		<div data-options="region:'west',title:'组织机构树'" style="width: 260px">
			<form id="empFromSearch"  style="width: 100%;height: auto;">            
				<table>      
					<tr>  
						<td>类型：</td>                              
						<td>                  
							<input id='managerTypeSearch' name='managerType' class="easyui-combobox" data-options="editable:false,valueField:'ID',textField:'TEXT',
								url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=OWNERTYPE&showNumber=1,2,3,9'"/>
						</td> 
					</tr>
					<tr>                
						<td>名称：</td>            
						<td>                  
							<input id='orgNameSearch' name='orgName' class="easyui-textbox" />
						</td>	     
					</tr>  
					<tr>
					   <td colspan="2" align="center">
					   		<a  onclick='searchOrgListGrid("myEmpOrgtree")' class="easyui-linkbutton" 
								data-options="iconCls:'icon-search'">查询</a>
									<a  onclick='formClear("empFromSearch")' class="easyui-linkbutton" 
								 data-options="iconCls:'icon-redo'">重置</a>	
					   </td>
					   
					</tr>        
				</table>
			</form>		
				
			<div  style="position:relative;left:0px;top:0px;width:100%;height:80%;overflow-y:auto;">	
			          <ul id='myEmpOrgtree'></ul> 				
			</div>	
		</div>
	<div   data-options="region:'center',title:'员工信息'" >         
	 <div id="EmpSearchPanel"  style="width:100%;"  > 
		 <form id="EmpFromSearc">
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
							<td class="tbl_td_label" width="13%">性别：</td>
							<td  width="20%">
								<input id="EmpSex" class="easyui-combobox" name="EmpSex"   data-options="editable:false,valueField:'ID',textField:'TEXT',  
								url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=SEX'" />
							</td>
				</tr> 
				<tr style="text-align: right;">
						<td colspan="6"> 
						   <a id="btn"  onclick='searchEmpListGrid()' class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&nbsp;
						   <a id="btn"  onclick='formClear("EmpFromSearc");' class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a>  &nbsp;
						</td> 
				</tr>
			</table> 
		</form>
	 </div>
 	<div id="addEmpDd"></div> 
 	<div id="editEmpDd"  style="display:none;"></div> 
	 	<table id='EmplistGrid' style="width：100%;" ></table> 
</div>
<script type="text/javascript"> 
var jsonData = null;
$(function(){   
	
	$('#myEmpOrgtree').tree({ 
	    url:'<%=request.getContextPath()%>/org/lodeTreeData.do?cmd=lodeTreeData&status=1',
// 	    checkbox:true,
	    cascadeCheck:false,
	    onLoadSuccess : function() {
	    	var node = $('#myEmpOrgtree').tree('getRoot'); 
	    	$('#myEmpOrgtree').tree('select',node.target);
	    },
	    onSelect : function(node) {
	    	$('#EmplistGrid').datagrid('load',{
	    		orgId: node.id
	    	});
	    	if(jsonData!=null){
	    		qxSelected(jsonData);
	    	}
	    	$(node.target).removeClass('myselected1');
		}
	});
	
	/**
	 * 初始化查询Panel
	 */
	$('#EmpSearchPanel').panel({    
		  collapsible:true,
		  width:"100%",
		  title:"查询"
	}); 
	/**
	 * 初始化列表组建
	 */
	$('#EmplistGrid').datagrid({    
			 url : '<%= request.getContextPath()%>/employee/getEmpLoyeeList.do?cmd=getEmpLoyeeList', 
			width:'100%',
			pageNumber:1,
			pageSize:pageNum,
			fit:false,
			height:gridHeight,   
			method:'post',  
			title:'员工信息列表',
			idField:"ID",
			ctrlSelect:true,
			
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
		  	        {field:'ID',checkbox:true},     
			        {field:'NAME',title:'员工名称' ,width:100} ,
			        {field:'AGE',title:'年龄',width:100},   
			        {field:'MOBILE',title:'联系电话',width:100},
			        {field:'CODE',title:'工号' ,width:100},
			        {field:'SEX',title:'性别' ,width:100},
	 				{field:'ORGCODE',title:'所属组织' ,width:100},
			        {field:'CREATETIME',title:'创建时间' ,width:100},
		            {field:'CREATEMAN',title:'创建人' ,width:100}
			         
		    ]],  
		    toolbar :['-',{  
				text:'添加',
				iconCls:'icon-add',
				handler:function(){ 
					var node = $('#myEmpOrgtree').tree('getSelected'); 
					if(node==null){
						$.messager.alert("提示","请选择单位！");      
						return false;
					}
					$('#addEmpDd').show(); 
					$('#editEmpDd').hide();
					$('#addEmpDd').dialog('refresh', '<%= request.getContextPath()%>/jsp/sys/employee/addEmp.jsp');  
					$('#addEmpDd').dialog('open');
				} 
			},'-',{
				text:'修改', 
				iconCls:'icon-edit',
				handler:function(){ 
					var selRows = $("#EmplistGrid").datagrid("getSelections"); 
		            if(selRows.length !=1){     
		                $.messager.alert("警告",'请选择一条记录！','warning'); 
		                return false;
		            } 
		            $('#editEmpDd').show(); 
					$('#addEmpDd').hide();
		            $('#editEmpDd').dialog('refresh', '<%= request.getContextPath()%>/jsp/sys/employee/updateEmp.jsp');  
					$('#editEmpDd').dialog('open');
				}
			},'-',{ 
				text:'删除',
				iconCls:'icon-cancel',
				handler:function(){
						var selRows = $("#EmplistGrid").datagrid("getSelections"); 
			            if(selRows.length <1){     
			                $.messager.alert("警告",'请选择一条记录！','warning'); 
			                return false;
			            } 
			            
						$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
						    if (r){    
						    	$.messager.progress();//打开进度条
						    	 var ids ="";//声明的是一个数组或集合
						            $.each( selRows, function(){
							            ids+=this.ID+",";
						            } );
								$.ajax({ 
						            type: "post", 
						            url: "<%= request.getContextPath()%>/employee/deleteEmpLoyee.do?cmd=deleteEmpLoyee",
						            data:{"EmpLoyeeId":ids} ,//form表单序列化
						            dataType: "json", 
						            success: function(data){ 
						            	 $.messager.progress('close');//关闭进度条
						            	if(data.flag){ 
								    		 $('#EmplistGrid').datagrid('reload');
										     $.messager.alert("提示",data.messager,'info'); 
										     $('#EmplistGrid').datagrid('clearChecked');
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
						$('#EmplistGrid').datagrid('hideColumn', item.name);
						cmenu.menu('setIcon', {
							target: item.target,
							iconCls: 'icon-empty'
						});
					} else {
						$('#EmplistGrid').datagrid('showColumn', item.name);
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
			var fields = $('#EmplistGrid').datagrid('getColumnFields');
			for(var i=0; i<fields.length; i++){
				var field = fields[i];
				var columenOption=$('#EmplistGrid').datagrid('getColumnOption',field);
				if(!columenOption.checkbox==true){
					var col = $('#EmplistGrid').datagrid('getColumnOption', field);
					cmenu.menu('appendItem', {
						text: col.title,
						name: field,
						iconCls: 'icon-ok'
					});       
				}
			}
		}
		/**
		 * 添加员工窗口
		 * 
		 */
		$('#addEmpDd').dialog({    
			title:'添加员工',
			width:600,
		    height:'auto', 
		    top: winTop,
		    left: winLeft,
		    modal:false, 
		    minimizable:false,
		    maximizable:false,
		    iconCls:'icon-save',
		    closed:true, 
		    href:'<%= request.getContextPath()%>/jsp/sys/employee/addEmp.jsp'  
		});  
		
		
		$('#editEmpDd').dialog({    
			title:'修改员工信息',
			width:600,
		    height:'auto', 
		    top: winTop,
		    left: winLeft,
		    modal:false, 
		    minimizable:false,
		    maximizable:false,
		    iconCls:'icon-save',
		    closed:true, 
		    href:'<%= request.getContextPath()%>/jsp/sys/employee/updateEmp.jsp'  
		});  
		
		
});


		
/**
 * 列表查询
 * 根据查询条件刷新列表数据
 */
function searchEmpListGrid(){
	var EmpNameSearch =$("#EmpName").val();
	var EmpcodeSearch =$("#Empcode").val();
	var EmpSexSearch =$("#EmpSex").combobox("getValue");
	var node = $('#myEmpOrgtree').tree('getSelected'); 
	$('#EmplistGrid').datagrid('load',{
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

/**
 * 表单查询
 */
function searchOrgListGrid(dataId){       
	$('#myOrgtree').tree('expandAll');     
	var orgName = $("#orgNameSearch").val();
	var managerType = $("#managerTypeSearch").combobox("getValue");
   if(orgName!=""||managerType!=""){               
   	$('#myEmpOrgtree').tree({
   		onLoadSuccess:function() {
   		 	$('#myEmpOrgtree').tree('expandAll');
		   	var node = $('#myEmpOrgtree').tree('getChecked');
       		for(var i = 0;i<node.length;i++){
   	    		 $("#myEmpOrgtree").tree('uncheck',node[i].target);  
   	    		 $(node[i].target).removeClass('myselected1');
       		}
		       $.ajax({                
		           url:"<%=request.getContextPath()%>/org/selectTreeData.do?cmd=selectTreeData",
		           data:{"orgName":orgName,"managerType":managerType} ,
		           dataType:"json",
		           type:'post',
		           cache:false,
		           async:true,
		           success:function(data){
		        	   jsonData = data;
		               $.each(data,function(i, obj){
		                   var n = $("#myEmpOrgtree").tree('find',obj.ID);
		                   if(n){
		                       $("#myEmpOrgtree").tree('check',n.target);  
		                       $(n.target).addClass('myselected1');
		                   }                                             
		               });   
		           }     
		       });
   		}
   	});
   }
   
}

function qxSelected(data){
	$.each(data,function(i, obj){
        var n = $("#myEmpOrgtree").tree('find',obj.ID);
        if(n){
            $(n.target).removeClass('myselected1');
        }                                             
    });   
}
</script>
</body> 
</html>