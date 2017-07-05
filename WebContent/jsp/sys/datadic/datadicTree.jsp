<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>数据字典</title>
<jsp:include page="/jsp/header.jsp" />
</head>
<body> 
	<div  class="easyui-layout" data-options="fit:true">
		<div data-options="region:'west',title:'字典分类树'" style="width: 250px">
			<ul id='datadictree'></ul> 
		</div>
	    <div   data-options="region:'center',title:'字典信息'" >
		 <div id="datadicSearchPanel"  style="width:100%;"  > 
			 <form id="datadicFromSearc">
			  	<table  cellpadding="0" cellspacing="0" class="tbl_search_bg" width="100%">  
			 		<tr>
				        <td class="tbl_td_label"    width="13%">类型编码：</td> 
						<td  width="20%">
							<input type="hidden" id="parid1" name="parid1"/>
							<input id='TYPECODE'  class="easyui-textbox" data-options="iconCls:'icon-search'"/>
						</td>   
						<td class="tbl_td_label"    width="13%">字典值：</td> 
						<td  width="20%">
							
							<input id='dicname'  class="easyui-textbox" data-options="iconCls:'icon-search'"/>
						</td>   
					</tr> 
					<tr style="text-align: right;">
						<td colspan="6"> 
						   <a id="btn"  onclick='searchdatadicListGrid()' class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&nbsp;
						   <a id="btn"  onclick='formClear();' class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a>  &nbsp;
						</td> 
					</tr>
				</table> 
			</form>
		</div>
 		<div id="adddatadicDd"></div> 
 		<div id="updatedatadicDd"></div> 
	 	<table id='datadiclistGrid' style="width：100%;" ></table> 
	 </div>
</div>
<script type="text/javascript"> 
$(function(){   
	$('#datadictree').tree({ 
	    url:'<%=request.getContextPath()%>/datadic/AllDataDicTreeData.do?cmd=AllDataDicTreeData', 
	    onSelect : function(node) {
	    	$("#parid1").val(node.id);
	    	$('#datadiclistGrid').datagrid('load',{
	    		parid: node.id
	    	});
		},
		onLoadSuccess : function() {
	    	var node = $('#datadictree').tree('find',1);
	    	$('#datadictree').tree('select',node.target); 
	    } 
	});
	/**
	 * 初始化查询Panel
	 */
	$('#datadicSearchPanel').panel({    
		  collapsible:true,
		  width:"100%",
		  title:"查询"
	}); 
	/**
	 * 初始化列表组建
	 */
	$('#datadiclistGrid').datagrid({    
		    url : '<%= request.getContextPath()%>/datadic/getdatadicList.do?cmd=getdatadicList', 
			width:'100%',
			pageNumber:1,
			pageSize:pageNum,
			fit:false,
			height:gridHeight,   
			method:'post',  
			title:'数据字典列表',
			idField:"ID",
			ctrlSelect:true,
			rownumbers:true,
			pagination : true,
			onBeforeLoad:function(param){
				if(typeof(param.parid) == "undefined"){
					return false;
				} 
			},
		    columns:[[
		  	        {field:'ID',checkbox:true},  
		  	      	{field:'TYPECODE',title:'类型编码' ,width:'25%',align:'center'},
		  	      	{field:'KEYNAME',title:'字典编码',width:'11%',align:'center'},
			        {field:'DICNAME',title:'字典值' ,width:'25%',align:'center'} ,
			        {field:'ISSELECTED',title:'是否为默认选项' ,width:'15%',align:'center'} ,
			        {field:'DICSORT',title:'排序',width:'15%',align:'center'}
		    ]],  
		    toolbar :['-',{  
				text:'添加',
				iconCls:'icon-add',
				handler:function(){ 
					$('#adddatadicDd').dialog('refresh', '<%= request.getContextPath()%>/jsp/sys/datadic/addDatadic.jsp');  
					$('#adddatadicDd').dialog('open');
				} 
			},'-',{
				text:'修改', 
				iconCls:'icon-edit',
				handler:function(){ 
					var selRows = $("#datadiclistGrid").datagrid("getSelections"); 
		            if(selRows.length !=1){     
		                $.messager.alert("警告",'请选择一条记录！','warning'); 
		                return false;
		            } 
		            $('#updatedatadicDd').dialog('refresh', '<%= request.getContextPath()%>/jsp/sys/datadic/updateDataDic.jsp');  
					$('#updatedatadicDd').dialog('open');
				}
			},'-',{ 
				text:'删除',
				iconCls:'icon-cancel',
				handler:function(){
					    
						var selRows = $("#datadiclistGrid").datagrid("getSelections"); 
			            if(selRows.length !=1){     
			                $.messager.alert("警告",'请选择一条记录！','warning'); 
			                return false;
			            }else{
			            	$.messager.confirm('确认','您确认想要删除记录吗？',function(r){
			            		if(r){
						            $.ajax({ 
						              	type: "post", 
						              	url: "<%= request.getContextPath()%>/datadic/findChildCounts.do?cmd=selectChildRight",
						              	data:{'id':selRows[0].ID},
						              	dataType: "json", 
						              	success: function(data){
						              		if(data.flag){
						              			$.messager.confirm('提示',data.messager,function(r){
						              				if (r){    
												    	$.messager.progress();//打开进度条
												    	 var ids ="";//声明的是一个数组或集合
												            $.each( selRows, function(){
													            ids+=this.ID+",";
												            } );
														$.ajax({ 
												            type: "post", 
												            url: "<%= request.getContextPath()%>/datadic/deleteDic.do?cmd=deleteDic",
												            data:{"ids":ids} ,//form表单序列化
												            dataType: "json", 
												            success: function(data){ 
												            	 $.messager.progress('close');//关闭进度条
												            	if(data.flag){ 
												            		 $("#datadictree").tree("reload");
														    		 $('#datadiclistGrid').datagrid('load');
														    		 $.messager.alert("提示",data.messager,'info'); 
														    		 $("#datadiclistGrid").datagrid("clearSelections");
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
						              		}else{
						              			if (r){    
											    	$.messager.progress();//打开进度条
											    	 var ids ="";//声明的是一个数组或集合
											            $.each( selRows, function(){
												            ids+=this.ID+",";
											            } );
													$.ajax({ 
											            type: "post", 
											            url: "<%= request.getContextPath()%>/datadic/deleteDic.do?cmd=deleteDic",
											            data:{"ids":ids} ,//form表单序列化
											            dataType: "json", 
											            success: function(data){ 
											            	 $.messager.progress('close');//关闭进度条
											            	if(data.flag){ 
											            		 $("#datadictree").tree("reload");
													    		 $('#datadiclistGrid').datagrid('load');
													    		 $.messager.alert("提示",data.messager,'info'); 
													    		 $("#datadiclistGrid").datagrid("clearSelections");
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
						              		}
						              	}
						            });
			            		}
			            	});
			            }
			            
						 
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
				      createColumnMenu('datadiclistGrid');
				 }
				 cmenu.menu('show', {
				      left:e.pageX,
				      top:e.pageY
				 });
		   	},onLoadSuccess:function(data){
		           var selRows = $("#datadiclistGrid").datagrid ("getSelections"); 
		           if(selRows.length>0){
		           		 $("#datadiclistGrid").datagrid("clearChecked");	
		           }
			   }
		});
	
		/**
		 * 添加员工窗口
		 * 
		 */
		$('#adddatadicDd').dialog({    
			title:'添加数据字典',
			width:600,
		    height:'auto', 
		    top: winTop,
		    left: winLeft,
		    modal:true, 
		    minimizable:false,
		    maximizable:false,
		    iconCls:'icon-save',
		    closed:true, 
		    href:'<%= request.getContextPath()%>/jsp/sys/datadic/addDatadic.jsp'  
		});  
		
		$('#updatedatadicDd').dialog({    
			title:'修改数据字典',
			width:600,
		    height:'auto', 
		    top: winTop,
		    left: winLeft,
		    modal:true, 
		    minimizable:false,
		    maximizable:false,
		    iconCls:'icon-save',
		    closed:true, 
		    href:'<%= request.getContextPath()%>/jsp/sys/datadic/updateDataDic.jsp'  
		});  
});
/**
 * 列表查询
 * 根据查询条件刷新列表数据
 */

function searchdatadicListGrid(){
	var TYPECODE = $("#TYPECODE").textbox("getText");
	var parid1 = $("#parid1").val();
	
	$('#datadiclistGrid').datagrid('load',{
		parid: parid1,
		typecode: TYPECODE,
		dicname:$("#dicname").textbox("getText")
	});          
}

/**
 * 表单重置
 * 参数：表单id 
 */
function formClear(){
	$("#TYPECODE").textbox('clear');
	$("#dicname").textbox("clear");
	//$('#'+formId).form('clear');
}
</script>
</body> 
</html>