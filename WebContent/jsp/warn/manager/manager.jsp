<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>预警管理</title>
<jsp:include page="/jsp/header.jsp" />
</head>
<body> 
	<div id="ManagerSearchPanel">
		<form id="ManagerFromSearc">
			<table  cellpadding="0" cellspacing="0" class="tbl_search_bg" width="100%">  
				<tr>	
					<td class="tbl_td_label" width="5%">类型：</td>
					<td width="20%">
						<select id="type" name="type" class="easyui-combobox" style="width: 133px;">
							<option selected="selected" value="0">采集点</option>
<!-- 							<option value="1">中间点</option> -->
						</select>
					</td>
					<td class="tbl_td_label" width="5%">报警类型：</td>
					<td width="20%">
						<input id="warnType" name="warnType" class="easyui-combobox"  data-options="editable:false,valueField:'ID',textField:'TEXT',
								url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=WARNTYPE'" />
					</td>
					<td class="tbl_td_label"  width="5%">站点名称：</td> 
					<td  width="20%">
						<input id='unitId' name="unitId" class="easyui-combobox" data-options="editable:false"/>
					</td>
					<td class="tbl_td_label" width="5%">采集点：</td>
					<td width="20%">
						<input id='collIdCX' class="easyui-textbox" />
					</td>
				</tr> 
				<tr style="text-align: right;">
					<td colspan="12"> 
						<a id="btn" href='javascript:searchManagerListGrid()' class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&nbsp;
						<a id="btn"  href='javascript:formClear("ManagerFromSearc");' class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a>  &nbsp;
					</td> 
				</tr>
			</table> 
		</form>
	</div> 
	<div id="danweiynDdCX"></div>
	<div id="addManagerDd"></div> 
	<div id="updateManagerDd"></div>
 	<table id='ManagerlistGrid' style="width：100%;" ></table> 
	<script type="text/javascript"> 
		var gridHeight = parent.$("#page").height()-83;
		$(function(){
			
			var unitid='<%=request.getParameter("unitid")%>';

			//从运行监控子级菜单具体站进入报警设置页面是 unitid为具体的站的ID；从运行监控子级菜单报警设置进入时unitid为null
			if(unitid!=null&&unitid!="null"){
				$('#unitId').combobox({disabled: true});//从具体站进入报警设置页面时，站不能再被选择，需禁用
			}else{
				$('#unitId').combobox({disabled: false});
			}
			
			$('#unitId').combobox({ 
				 url:'<%= request.getContextPath()%>/warning/queryUnit.do?cmd=queryUnit',
				 valueField:'ID',
				 textField:'UNITNAME',
				 onLoadSuccess:function (){
					 //从运行监控子级菜单具体的换热站进入报警设置页面，根据选择的菜单名称（换热站），查询该换热站的相关信息 
					 if(unitid!=null&&unitid!="null"){
						$('#unitId').combobox('setValues', unitid);
						searchManagerListGrid();
					 }
				 }
			 }); 
			
			var ww = parent.$("#page").width();
			/**
			 * 初始化查询Panel
			 */
			$('#ManagerSearchPanel').panel({    
				  collapsible:true,
				  width:ww,
				  title:"查询"
			}); 
		
		/**
		 * 初始化列表组建
		 */
		$('#ManagerlistGrid').datagrid({    
			url : '<%= request.getContextPath()%>/manager/listManager.do?cmd=getlistManager',
			width:'100%',
			pageNumber:1,
			pageSize:pageNum,
			fit:false,
			height:gridHeight,   
			method:'post',  
			title:'报警设置列表',
			idField:"ID",
			ctrlSelect:true,
			rownumbers:true,
			fitColumns: true,   
			pagination : true,
			singleSelect:true,
		    columns:[[
					{field:'ID',checkbox:true}, 
					{field:'COLLID',hidden:true},
					{field:'NAME',title:'预警设置名称',width:'12%',align:'center'},
			        {field:'UNITNAME',title:'单位',width:'12%',align:'center'}, 
			        {field:'COLLNAME',title:'报警点',width:'12%',align:'center'}, 
			        {field:'TYPE',title:'报警点类型',width:'6%',align:'center',
			        	formatter: function(value,row,index){
							if(value==0){
								return "采集点";
							}else if(value==1){
								return "中间点";
							}
			        	}
			        }, 
			        {field:'WARNVALUE',title:'报警值',width:'6%',align:'center'},
			        {field:'WARNTYPE',title:'报警类型' ,width:'8%',align:'center'},
			        {field:'WARNLEVEL',title:'报警等级',width:'8%',align:'center'}, 
			        {field:'ANAWARNFUN',title:'模拟量报警方式',width:'10%',align:'center'}, 
			        {field:'ONOFFWARNFUN',title:'开关量报警方式',width:'10%',align:'center'}, 
			        {field:'STATUS',title:'状态',width:'4%',align:'center',
			        	formatter: function(value,row,index){
							if(value==0){
								return "禁用";
							}else if(value==1){
								return "启用";
							}
			        	}
			        }
		    ]],  
		    toolbar :['-',{  
				text:'新增',
				iconCls:'icon-add',
				handler:function(){ 
		            $('#addManagerDd').dialog('refresh','<%= request.getContextPath()%>/jsp/warn/manager/addManager.jsp');
		    		$('#addManagerDd').dialog('open');
			  }
			},'-',{  
				text:'修改', 
				iconCls:'icon-edit',
				handler:function(){ 
					var selRows = $("#ManagerlistGrid").datagrid("getSelections"); 
		            if(selRows.length !=1){     
		                $.messager.alert("警告",'请选择一条记录！','warning'); 
		                return false;
		            } 
		            $('#updateManagerDd').dialog('refresh', '<%= request.getContextPath()%>/jsp/warn/manager/updateManager.jsp');  
					$('#updateManagerDd').dialog('open');
				}
			},'-',{
				text:'删除', 
				iconCls:'icon-cancel',
				handler:function(){
					var selRows = $("#ManagerlistGrid").datagrid("getSelections"); 
		            if(selRows.length !=1){     
		                $.messager.alert("警告",'请选择一条记录！','warning'); 
		                return false;
		            }
		            $.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
					    if (r){   
					    	$.messager.progress();//打开进度条
							$.ajax({ 
					            type: "post", 
					            url: "<%= request.getContextPath()%>/manager/deleteManager.do?cmd=deleteManager",
					            data:{"id":selRows[0].ID} ,//form表单序列化
					            dataType: "json", 
					            success: function(data){ 
					            	 $.messager.progress('close');//关闭进度条
					            	if(data.flag){ 
					            		
							    		 $('#ManagerlistGrid').datagrid('reload');
							    		 $("#ManagerlistGrid").datagrid("clearSelections");
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
			},'-'],
		    onHeaderContextMenu: function(e, field){
				 e.preventDefault();
				 if (!cmenu){
				      createColumnMenu('ManagerlistGrid');
				 }
				 cmenu.menu('show', {
				      left:e.pageX,
				      top:e.pageY
				 });
		   	}
		});
		
		
			/**
			 * 添加标库窗口
			 * 
			 */
			$('#addManagerDd').dialog({    
				title:'新增',
				width:700,
			    height:'auto', 
			    top: winTop,
			    left: winLeft,
			    modal:true, 
			    minimizable:false,
			    maximizable:false,
			    iconCls:'icon-save',
			    closed:true, 
			    href:'<%= request.getContextPath()%>/jsp/warn/manager/addManager.jsp'  
			}); 
			$('#updateManagerDd').dialog({    
				title:'修改',
				width:700,
			    height:'auto', 
			    top: winTop,
			    left: winLeft,
			    modal:true, 
			    minimizable:false,
			    maximizable:false,
			    iconCls:'icon-save',
			    closed:true, 
			    href:'<%= request.getContextPath()%>/jsp/warn/manager/updateManager.jsp'  
			}); 
		});
		/**
		 * 列表查询
		 * 根据查询条件刷新列表数据
		 */
		function searchManagerListGrid(){
			var unitIdSearch =$("#unitId").combobox("getValue");
			var collname =$('#collIdCX').val();
			var warnType =$("#warnType").combobox("getValue");
			var type =$("#type").combobox("getValue");
			$('#ManagerlistGrid').datagrid('load',{
				unitId:unitIdSearch,
				collName:collname,
				warnType:warnType,
				type:type
			});
		}
		
		
		//·······选择单位页面开始·······
		function showynDanweiDialogCx(){
			$('#danweiynDdCX').dialog('refresh','<%= request.getContextPath()%>/jsp/cost/unitBaseTreeCX.jsp'); 
		    $('#danweiynDdCX').dialog('open');
		}
		
		function transportValueCX(val,text,unittype,unitTypeName){
			$('#unitIdSear').textbox({value:val});
			$('#unitNameSear').textbox({value:text});
			$("#unitIdSear").next("span").hide();
		}
		
		$('#danweiynDdCX').dialog({    
			title:'添加单位信息',
			width:600,
		    height:'auto', 
		    top: winTop,
		    left: winLeft,
		    modal:true, 
		    minimizable:false,
		    maximizable:false,
		    iconCls:'icon-save',
		    closed:true, 
		    href:'<%= request.getContextPath()%>/jsp/cost/unitBaseTreeCX.jsp'  
		});
		//·······选择单位页面结束·······
		
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