<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
	<jsp:include page="/jsp/header.jsp" />
</head>
<body>
<div id="unitinfo" >
				<form id="unitinfoform" style="width: 100%;">                                                               
					<table   id="unitinfoTblId" cellpadding="0" cellspacing="0" class="tbl_search_bg"  width="100%">
						<!-- <tr>
							<td class="tbl_td_label">热力站总数量：</td>  
							<td>                 
								<input id='heatCount' name='heatCount' class="easyui-textbox" data-options="disabled:true"/> 
							</td>
							<td class="tbl_td_label" width="13%">热源总数量：</td>  
							<td>
								<input id='FEEDCOUNT' name='FEEDCOUNT'   class="easyui-textbox" data-options="disabled:true"/> 
							</td>
						 </tr> -->
						 
						 <tr>
							<!-- <td class="tbl_td_label">一次管网数量：</td>
							<td><input id='TOTALCOUNTGW' name='TOTALCOUNTGW'class="easyui-textbox"data-options="disabled:true,editable:false" /></td> -->
							<td class="tbl_td_label">总供热面积(m²)：</td>
							<td><input id='mjgjsum' name='mjgjsum'class="easyui-textbox"data-options="disabled:true,editable:false" /></td>
							<td class="tbl_td_label">集团性质：</td>
							<td>
							<input id="groupTypeUpda" class="easyui-combobox" name="groupType" data-options="disabled:true,editable:false,valueField:'ID',required:true,textField:'TEXT',  
										url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=GROUPTYPE'" />
							</td>
<!-- 							<td class="tbl_td_label" width="13%">采暖面积(m²)：</td>   -->
<!-- 							<td> -->
<!-- 								<input id='cnmj' name='cnmj'   class="easyui-numberbox" data-options="disabled:true,editable:false"/>  -->
<!-- 							</td> -->
						 </tr>
						 
<!-- 						<tr> -->
							
<!-- 						</tr> -->
					</table>
					
			</form> 
</div> 
<input id="unitId" type="hidden"/>
<table id="editUnit1Child" style="width:100%;height:320px;overflow: inherit;"></table>
<script type="text/javascript">
		
		(function($){
			function getCacheContainer(t){
				var view = $(t).closest('div.datagrid-view');
				var c = view.children('div.datagrid-editor-cache');
				if (!c.length){
					c = $('<div class="datagrid-editor-cache" style="position:absolute;display:none"></div>').appendTo(view);
				}
				return c;
			}
			function getCacheEditor(t, field){
				var c = getCacheContainer(t);
				return c.children('div.datagrid-editor-cache-' + field);
			}
			function setCacheEditor(t, field, editor){
				var c = getCacheContainer(t);
				c.children('div.datagrid-editor-cache-' + field).remove();
				var e = $('<div class="datagrid-editor-cache-' + field + '"></div>').appendTo(c);
				e.append(editor);
			}
			
			var editors = $.fn.datagrid.defaults.editors;
			for(var editor in editors){
				var opts = editors[editor];
				(function(){
					var init = opts.init;
					opts.init = function(container, options){
						var field = $(container).closest('td[field]').attr('field');
						var ed = getCacheEditor(container, field);
						if (ed.length){
							ed.appendTo(container);
							return ed.find('.datagrid-editable-input');
						} else {
							return init(container, options);
						}
					}
				})();
				(function(){
					var destroy = opts.destroy;
					opts.destroy = function(target){
						if ($(target).hasClass('datagrid-editable-input')){
							var field = $(target).closest('td[field]').attr('field');
							setCacheEditor(target, field, $(target).parent().children());
						} else if (destroy){
							destroy(target);
						}
					}
				})();
			}
		})(jQuery);
	</script>
	<script type="text/javascript">
	$(function(){
		var str="";
		$.ajax({
			url:"<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=PARAMETERSSTATUS",
	        dataType:"json",
	        success:function(data){
	        	
	        	str=data;
	        }
		});
		
		
		$('#editUnit1Child').datagrid({    
			url: '<%= request.getContextPath()%>/details/findDetailsByUnitId.do?cmd=findDetailsByUnitId',  
			width:'100%',
			pageNumber:1,
			pageSize:pageNum,
			fit:false,
			iconCls: 'icon-edit',
			height:gridHeight,   
			method:'post',  
			idField:"ID",
			striped:true,
			queryParams:{id:$('#unitId').val()},
			ctrlSelect:true, 
			rownumbers:true,
			fitColumns: false,   
			pagination : true,
			singleSelect:true,
			scrollbarSize:10,
			columns:[[
				{field:'ID',hidden:true}, 
				{field:'CHANGEDATE',title:'变更时间',width:'15%',align:'center',editor:{type:'datebox',options:{required: true,editable:false,validType:'length[0,20]'}}},
				{field:'CNMJ',title:'采暖面积(m²)',width:'18%',align:'center',editor:{type:'numberbox',options:{editable:true,method:'post',precision:2,validType:'doubleNumCheck[13,2]'}}},
				{field:'MJHEATAREA',title:'低区民建供热面积(m²)',width:'18%',align:'center',editor:{type:'numberbox',options:{editable:true,method:'post',precision:2,validType:'doubleNumCheck[13,2]'}}},
				{field:'GJHEATAREA',title:'低区公建供热面积(m²)',width:'18%',align:'center',editor:{type:'numberbox',options:{editable:true,method:'post',precision:2,validType:'doubleNumCheck[13,2]'}}},
				{field:'MJHEATAREAH',title:'高区民建供热面积(m²)',width:'18%',align:'center',editor:{type:'numberbox',options:{editable:true,method:'post',precision:2,validType:'doubleNumCheck[13,2]'}}},
				{field:'GJHEATAREAH',title:'高区公建供热面积(m²)',width:'18%',align:'center',editor:{type:'numberbox',options:{editable:true,method:'post',precision:2,validType:'doubleNumCheck[13,2]'}}},
				{field:'STATUS',title:'状态',width:'10%',align:'center',formatter:function(value,row){var key=""; for(var i=0;i<str.length;i++){if(str[i].ID==value){ key=str[i].TEXT }}  return key;} ,editor:{type:'combobox',options:{required: true,editable:false,method:'post',url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=PARAMETERSSTATUS',validType:'length[0,20]',valueField:'ID',textField:'TEXT'}}},
				{field:'REMARKS',title:'备注',width:'15%',align:'center',editor:{type:'textbox',options:{editable:true,method:'post',validType:'length[0,50]'}}}
			]],
			toolbar :['-',{  
				text:'添加',
				iconCls:'icon-add',
				handler:function(){
				if (endEditing()){
					var rows = $('#editUnit1Child').datagrid('getRows');
					var i = 0;
					for(var j = 0;j < rows.length;j++){
						if(rows[j].ID==""||rows[j].ID==null||rows[j].ID==undefined){
							i += 1;
						}     
					}  
					if(i>=1){
						$.messager.alert("提示", "只能添加一条数据，请先保存后再添加！");
						return false;
					} 
					$('#editUnit1Child').datagrid('appendRow',{REMARKS:''});
					editIndex = $('#editUnit1Child').datagrid('getRows').length-1;
					$('#editUnit1Child').datagrid('selectRow', editIndex)
							.datagrid('beginEdit', editIndex);
				}
			}
			},'-',{  
				text:'保存',
				iconCls:'icon-save',
				handler:	
					//保存
					function (){
						if (endEditing()){
							debugger;
							var rows = $('#editUnit1Child').datagrid('getChanges');
							if(rows.length<1){
								$.messager.alert("提示", "请填写数据后再进行保存！");
								return false;
							}
							var rowJsons = JSON.stringify(rows);
							if(rows.length>1){
								for(var i = 0;i<rows.length;i++){
									for(var j=rows.length-1;j>i;j--){
										if(rows[i].CHANGEDATE==rows[j].CHANGEDATE&&rows[i].STATUS=='1'&&rows[j].STATUS=='1'){
											$.messager.alert("警告",'同一变更时间，可用的分项计量只能有一条!','warning'); 
									    	return false;
										}
										
									}
								}
							}
							$.ajax({ 
				           		type: "post", 
					            url: "<%=request.getContextPath()%>/details/saveDetailsBatch.do?cmd=saveDetailsBatch",
								data : {detailsObj:JSON.stringify(rows),unitId:$("#unitId").val()},
								dataType : "json",
								success : function(data) {
									$.messager.progress('close');//关闭进度条
									if (data.flag) {
										$('#UnitlistGrid').datagrid('reload');
										$.ajax({     //用能
								            type: "post",  
								            url: "<%=request.getContextPath()%>/unit/findUnitInfoById.do?cmd=findUnitInfoById",
											data : {"id" : a1},//form表单序列化
											dataType : "json",
											success : function(data) { 
												$('#unitinfoform').form('load',data);
												$('#editUnit1Child').edatagrid('load');
											//	$('#unitTblId').datagrid('reload');
											}
										});
										$.messager.alert("提示",  data.messager, 'info',editUnit1ChildDgClocse() );
										//window.location.reload();  
									} else {
										$.messager.alert("警告", data.messager, 'warning');
										
									}
								}
							});
						}
						
					}
			},'-',{  
				text:'删除',
				iconCls:'icon-remove',
				handler://删除
					function (){
					debugger;
					var selRows = $("#editUnit1Child").datagrid("getSelections");
					if(selRows.length>0){
						if(selRows[0].ID!=undefined){     
							$.messager.alert("警告",'已保存数据不允许删除！','warning'); 
							return false;
						}
					}
				if (editIndex == undefined){
					 $.messager.alert("警告",'请选择一条记录！','warning'); 
					return;
			}else{
				$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
				    if (r){   
				    	$.messager.progress();//打开进度条
				    	$('#editUnit1Child').datagrid('cancelEdit', editIndex).datagrid('deleteRow', editIndex);
				    	editIndex = undefined;
				    	var rows = $('#editUnit1Child').datagrid('getChanges');
						$.ajax({ 
				            type: "post", 
				            url: "<%=request.getContextPath()%>/details/deleteChildInfo.do?cmd=deleteChildInfo",
				            data:{deleteChildObj:JSON.stringify(rows)} ,//form表单序列化
				            dataType: "json", 
				            success: function(data){ 
				            	 $.messager.progress('close');//关闭进度条
				            	if(data.flag){ 
				            		//$('#editUnit1ChildDd').datagrid('reload');
				            		$('#UnitlistGrid').datagrid('reload');
				            		$.messager.alert("提示", "删除成功", 'info');
						    	}else{
								     $.messager.alert("警告","系统异常",'warning'); 
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
		   	},
		    onClickRow:function onClickRow(index){
				if (editIndex != index){
					if (endEditing()){
						$('#editUnit1Child').datagrid('selectRow', index)
								.datagrid('beginEdit', index);
						editIndex = index;
					} else {
						$('#editUnit1Child').datagrid('selectRow', editIndex);
					}
				}
			}
		});



		
});
	//关闭窗口
	function editUnit1ChildDgClocse(){ 
		$('#ChildUintDd').dialog("close");
	} 
	var a1 = $('#aa', parent.document).val(); 
	$('#unitId').val(a1);
	var editIndex = undefined;
		//校验结束，停止编辑
		
	function endEditing(){
			if (editIndex == undefined){return true;};
			if ($('#editUnit1Child').datagrid('validateRow', editIndex)){
				$('#editUnit1Child').datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}
	
		//添加一行
		function append(){
			if (endEditing()){
				$('#editUnit1Child').datagrid('appendRow',{REMARKS:''});
				editIndex = $('#editUnit1Child').datagrid('getRows').length-1;
				$('#editUnit1Child').datagrid('selectRow', editIndex)
						.datagrid('beginEdit', editIndex);
			}
		}
		//删除
		function removeit(){
			if (editIndex == undefined){
				 $.messager.alert("警告",'请选择一条记录！','warning'); 
				return;
		}else{
			$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
			    if (r){   
			    	$.messager.progress();//打开进度条
			    	$('#editUnit1Child').datagrid('cancelEdit', editIndex).datagrid('deleteRow', editIndex);
			    	editIndex = undefined;
			    	var rows = $('#editUnit1Child').datagrid('getChanges');
					$.ajax({ 
			            type: "post", 
			            url: "<%=request.getContextPath()%>/details/deleteChildInfo.do?cmd=deleteChildInfo",
			            data:{deleteChildObj:JSON.stringify(rows)} ,//form表单序列化
			            dataType: "json", 
			            success: function(data){ 
			            	 $.messager.progress('close');//关闭进度条
			            	if(data.flag){ 
			            		$('#editUnit1ChildDd').datagrid('reload');
			            		$.messager.alert("提示", "删除成功", 'info');
					    	}else{
							     $.messager.alert("警告","系统异常",'warning'); 
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
		};
		//保存
		function accept(){
			
			if (endEditing()){
				var rows = $('#editUnit1Child').datagrid('getSelections');
			
				$.ajax({ 
	           		type: "post", 
		            url: "<%=request.getContextPath()%>/details/saveDetailsBatch.do?cmd=saveDetailsBatch",
					data : {detailsObj:JSON.stringify(rows),unitId:$("#unitId").val()},
					dataType : "json",
					success : function(data) {
						$.messager.progress('close');//关闭进度条 
						if (data.flag) {
							$('#UnitlistGrid').datagrid('reload');
							//$('#detailsUpdateform').form('load',data);
							
							$.messager.alert("提示",  data.messager, 'info',editUnit1ChildDgClocse() );
						} else {
							$.messager.alert("警告", data.messager, 'warning');
						}
					}
				});
			}
			
		}
		//重置
		function reject(){
			$('#editUnit1Child').datagrid('rejectChanges');
			editIndex = undefined;
		}
		
		$.ajax({     //用能
            type: "post",  
            url: "<%=request.getContextPath()%>/unit/findUnitInfoById.do?cmd=findUnitInfoById",
			data : {"id" : a1},//form表单序列化
			dataType : "json",
			success : function(data) { 
				$('#unitinfoform').form('load',data);
			}
		});
		
	</script>
	<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-easyui-1.4.1/custom/myvalidate.js"></script>
</body>

</html>
