<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>组织机构管理</title>
<jsp:include page="/jsp/header.jsp" />
</head>
<body>
	<div id="orgTreeId" class="easyui-layout" data-options="fit:true">
		<div data-options="region:'west',title:'组织机构树'" style="width: 280px">
				<form id="orgFromSearc"  style="width:100%;height: auto;">            
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
						<td colspan="2">
									<a  id="orgAdd" onclick="toSave_onClick()" class="easyui-linkbutton"
								data-options="iconCls:'icon-add'">新增</a> 
									<a  onclick="toUpadte_onClick()" class="easyui-linkbutton"
								data-options="iconCls:'icon-edit'">修改</a> 
									<a  onclick="toDelete_onClick()" class="easyui-linkbutton"
								data-options="iconCls:'icon-cancel'">不可用</a>
						</td>
					</tr>   
					<tr>
						<td colspan="2">
									<a  onclick="toReturn_onClick()" class="easyui-linkbutton"
								data-options="iconCls:'icon-ok'">可用</a>
									<a  onclick='searchOrgListGrid("myOrgtree")' class="easyui-linkbutton" 
								data-options="iconCls:'icon-search'">查询</a>
									<a  onclick='formClear("orgFromSearc")' class="easyui-linkbutton" 
								 data-options="iconCls:'icon-redo'">重置</a>
						</td>
					</tr>       
				</table>
			</form>				
			<div style="position:relative;left:0px;top:0px;width: 100%;height:76%;overflow-y:auto;">						
				<ul id='myOrgtree'></ul>
			</div>								 
		</div>
		<div  id="orgDivId" data-options="region:'center',title:'组织机构详细'" >
			<form id="orgform">
					<table   id="orgTblId" cellpadding="0" cellspacing="0" class="tbl_search_bg"width="100%">
						<tr>
							<td class="tbl_td_label" width="15%">组织编码：</td>
							<td>
							<input id="myDbOrgId" name='id'   type="hidden"/>
							<input id="dd" name='dd'   type="hidden"/>
							<input id="status" name='status'   type="hidden"/>
							<input id="orgCode" name='orgCode' class="easyui-textbox"data-options="disabled:true,editable:false" />
							</td> 
						</tr>
						
						<tr>
							<td class="tbl_td_label">组织名称：</td>
							<td><input id='orgName' name='orgName'class="easyui-textbox"data-options="disabled:true,editable:false" /></td>
						 </tr>
						 
						 <tr>
							<td class="tbl_td_label">简称：</td>
							<td><input id='sortName' name='sortName'class="easyui-textbox"data-options="disabled:true,editable:false" /></td>
						 </tr>
						 <tr>
							<td class="tbl_td_label">简拼：</td>
							<td><input id='jianPin' name='jianPin'class="easyui-textbox"data-options="disabled:true,editable:false" /></td>
						 </tr>
						 
						<tr>
							<td class="tbl_td_label">组织类型：</td>
							<td>
								<input id="orgType" class="easyui-combobox" name="orgType"   data-options="disabled:true,editable:false,valueField:'ID',textField:'TEXT',
								url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=ORGTYPE'" /> 
							</td>
						</tr>
						<tr>
							<td class="tbl_td_label">管理类型：</td>
							<td>
								<input id="managerType" class="easyui-combobox" name="managerType"   data-options="disabled:true,editable:false,valueField:'ID',textField:'TEXT',
								url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=OWNERTYPE&showNumber=1,2,3,9'" /> 
							</td>
						</tr>
						<tr>
							<td class="tbl_td_label">公司类型：</td>
							<td>
								<input id="companyType" class="easyui-combobox" name="companyType"   data-options="disabled:true,editable:false,valueField:'ID',textField:'TEXT',
								url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=COMPANYTYPE'" /> 
							</td>
						</tr>
						<tr>
							<td class="tbl_td_label">集团性质：</td>
							<td>
							<input id="groupTypeUpda" class="easyui-combobox" name="groupType" data-options="disabled:true,editable:false,valueField:'ID',required:true,textField:'TEXT',  
										url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=GROUPTYPE'" />
							</td>
						</tr>
						<tr>
							<td class="tbl_td_label">创建人：</td>
							<td><input id='createMan' name='createMan'class="easyui-textbox"data-options="disabled:true,editable:false" />
							</td>
						</tr>
						<tr>
							<td class="tbl_td_label">创建时间：</td>
							<td><input id='createTime' name='createTime'class="easyui-textbox"data-options="disabled:true,editable:false" />
							</td>
						</tr>
						<tr>
							<td class="tbl_td_label">排序：</td>
							<td><input id='seq' name='seq'class="easyui-numberbox"data-options="disabled:true,editable:false" />
							</td>
						</tr>
						<tr>
							<td class="tbl_td_label">是否可用：</td>
							<td>
								<input id="isDelete" name='isDelete' class="easyui-combobox"   data-options="editable:false,disabled:true,valueField:'ID',textField:'TEXT',
								url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=PARAMETERSSTATUS'" /> 
							</td>
						</tr>
					</table>
					
			</form>
			<div id="addOrgDd"></div> 
			<div id="eitOrgDd"></div> 
			<div id="tjOrgDd"></div>
			<div style="display:none;">
			   <table id='OrglistGrid' ></table>
			</div>
			
		</div>
		<div data-options="region:'center',title:'组织机构详细'"></div>
	</div>
	<script type="text/javascript">
	var jsonData = null;
	$('#myOrgtree').tree({ 
	    url:'<%=request.getContextPath()%>/org/lodeTreeData.do?cmd=lodeTreeData',
	    cascadeCheck:false,
// 	    checkbox:true,
	    dnd:true,
	    onLoadSuccess : function() {
	    	var node = $('#myOrgtree').tree('getRoot'); 
	    	$('#myOrgtree').tree('select',node.target);
	    },
	    onDrop : function(target,source){
	    	var pid = $('#myOrgtree').tree("getNode",target).id;
	    	var id = source.id;
	    	$.ajax({ 
	            type: "post", 
	            url: "<%=request.getContextPath()%>/org/tjOrg.do?cmd=tjOrg",
				data : {"pid" : pid,"id":id},//form表单序列化
				dataType : "json",
				success : function(data) { 
					$.messager.progress('close');//关闭进度条
					if (data.flag) {
						$('#myOrgtree').tree('reload');
						$.messager.alert("提示", data.messager, 'info');
					} else {
						$.messager.alert("警告", data.messager, 'warning');
					}
				}
			});
	    },
	    onSelect : function(node) {
	    	$("#dd").val(node.id);
	    	$("#status").val(node.status);
	    	if(jsonData!=null){
	    		qxSelected(jsonData);
	    	}
	    	$(node.target).removeClass('myselected1');
	    	$.ajax({ 
	            type: "post", 
	            url: "<%=request.getContextPath()%>/org/findOrgById.do?cmd=getOrgnByOrgId",
				data : {"OrgId" : node.id},//form表单序列化
				dataType : "json",
				success : function(data) { 
					
					$('#orgform').form('load',data);
				}
			});
		}
	});
	
  function  toSave_onClick(){
	  $('#addOrgDd').empty();
	  $('#addOrgDd').panel('open').dialog('refresh','<%= request.getContextPath()%>/jsp/sys/org/addOrg.jsp');
 }
  
  $('#addOrgDd').dialog({
	    
		title:'添加组织机构信息',
		width:600,
	    height:'auto', 
	    top: winTop,
	    left: winLeft,
	    modal:true, 
	    cache: false,
	    minimizable:false, 
	    maximizable:false,
	    iconCls:'icon-save',
	    closed:true, 
	    href:'<%= request.getContextPath()%>/jsp/sys/org/addOrg.jsp'  
	});  

  function toUpadte_onClick(){
	  
	  $('#eitOrgDd').panel('open').dialog('refresh','<%= request.getContextPath()%>/jsp/sys/org/updateOrg.jsp');
  }
  $('#eitOrgDd').dialog({  
	    
		title:'修改机构信息',
		width:600,
	    height:'auto', 
	    top: winTop,
	    left: winLeft,
	    modal:true, 
	    minimizable:false, 
	    maximizable:false,
	    iconCls:'icon-edit',
	    closed:true, 
	    href:'<%= request.getContextPath()%>/jsp/sys/org/updateOrg.jsp'  
	});

 
 function toDelete_onClick(){
	 debugger;
	 var nodes = $('#myOrgtree').tree('getSelected'); 
	 var ids = null;
	 var status = null;
	 if(nodes.length==0){
		 ids = $("#dd").val();
		 status = $("#status").val();
		 if(status=='0'){
			 $.messager.alert("警告",'只有可用的记录可以进行此操作！','warning'); 
	         return false;
		 }
	 }else{
		 ids= nodes.id;
		 status = nodes.status;
		 if(status=='2'){
			 $.messager.alert("警告",'只有可用的记录可以进行此操作！','warning'); 
	         return false;
		 }
		
	 }
	 if(nodes==null){     
         $.messager.alert("警告",'请选择一条记录！','warning'); 
         return false;
     } 
		$.messager.confirm('确认','您确认想要修改为不可用吗？',function(r){    
		    if (r){    
		    	$.messager.progress();//打开进度条
				$.ajax({ 
		            type: "post", 
		            url: "<%= request.getContextPath()%>/org/deleteOrg.do?cmd=deleteOrg",
		            data:{"id":ids,"status":"2"} ,//form表单序列化
		            dataType: "json", 
		            success: function(data){ 
		            	 $.messager.progress('close');//关闭进度条
		            	if(data.flag){ 
		            		searchOrgListGrid("myOrgtree");
						     $.messager.alert("提示",'已修改为不可用!','info'); 
				    	}else{
						     $.messager.alert("警告",'修改失败！','warning'); 
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
  
 function toReturn_onClick(){
	 var nodes = $('#myOrgtree').tree('getChecked'); 
	 var ids = null;
	 var status = null;
	 if(nodes.length==0){
		 ids = $("#dd").val();
		 status = $("#status").val();
		 if(status=='1'){
			 $.messager.alert("警告",'只有不可用的记录可以进行此操作！','warning'); 
	         return false;
		 }
	 }else{
		 ids=nodes.id;
		 status = nodes.status;
		 if(status=='1'){
			 $.messager.alert("警告",'只有不可用的记录可以进行此操作！','warning'); 
	         return false;
		 }    
	 }
	 if(nodes==null){     
         $.messager.alert("警告",'请选择一条记录！','warning'); 
         return false;
     } 
		$.messager.confirm('确认','您确认想要修改为可用吗？',function(r){    
		    if (r){    
		    	$.messager.progress();//打开进度条
				$.ajax({ 
		            type: "post", 
		            url: "<%= request.getContextPath()%>/org/deleteOrg.do?cmd=deleteOrg",
		            data:{"id":ids,"status":"1"} ,//form表单序列化
		            dataType: "json", 
		            success: function(data){ 
		            	 $.messager.progress('close');//关闭进度条
		            	if(data.flag){ 
		            		searchOrgListGrid("myOrgtree");
						     $.messager.alert("提示",'已修改为可用','info'); 
				    	}else{
						     $.messager.alert("警告",'修改失败!','warning'); 
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
 
 function  toTjOrg_onClick(){
	 var nodes = $('#myOrgtree').tree('getChecked'); 
	 if(nodes.length==0){
		 $.messager.alert("提示","请勾选要调级的组织机构！"); 
		 return false;
	 }  
	  $('#tjOrgDd').empty();
	  $('#tjOrgDd').panel('open').dialog('refresh','<%= request.getContextPath()%>/jsp/sys/org/tjOrg.jsp');
}
     
 $('#tjOrgDd').dialog({     
	    
		title:'机构调级',
		width:300,
	    height:400, 
	    top: winTop,
	    left: winLeft,
	    modal:true, 
	    cache: false,   
	    minimizable:false, 
	    maximizable:false,
	    iconCls:'icon-save',
	    closed:true, 
	    href:'<%= request.getContextPath()%>/jsp/sys/org/tjOrg.jsp'  
	});  
  
 /**
  * 表单查询
  */
 function searchOrgListGrid(dataId){    
	//$('#myOrgtree').tree('expandAll');     
	var orgName = $("#orgNameSearch").val();
	var managerType = $("#managerTypeSearch").combobox("getValue");
    if(orgName!=""||managerType!=""){               
    	$('#myOrgtree').tree({
    		onLoadSuccess:function() {
    		 	$('#myOrgtree').tree('expandAll');
    		
    	var node = $('#myOrgtree').tree('getChecked');
        for(var i = 0;i<node.length;i++){
    	    $("#myOrgtree").tree('uncheck',node[i].target);  
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
                    var n = $("#myOrgtree").tree('find',obj.ID);
                    if(n){
                        $("#myOrgtree").tree('check',n.target);  
                        $(n.target).addClass('myselected1');
                    }                                             
                });   
            }     
        });
    		}
    	});
    }else{
    	$('#myOrgtree').tree({
    		onLoadSuccess:function() {
    			var node = $('#myOrgtree').tree('getRoot'); 
    	    	$('#myOrgtree').tree('select',node.target);
    		}
    	});
    }
    
 }
 
 /**
  * 表单重置
  * 参数：表单id 
  */
 function formClear(formId){
 	$('#'+formId).form('clear');
 }
 
 function qxSelected(data){
		$.each(data,function(i, obj){
	        var n = $("#myOrgtree").tree('find',obj.ID);
	        if(n){
	            $(n.target).removeClass('myselected1');
	        }                                             
	    });   
	}
 
 $('#OrglistGrid').datagrid({    
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
	</script>
</body>


</html>