<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="/jsp/header.jsp" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>采集点管理</title>
</head>
<body>
<div id="collUnitTree" class="easyui-layout" data-options="fit:true">
	<!-- 左侧单位树 -->
	<div data-options="region:'west',title:'单位树'" style="width: 270px;">
		<form id="unitInfoFromSearc"  style="width: 100%;height: auto;">            
			<table>      
<!-- 				<tr>                    -->
<!-- 					<td class="tbl_td_label">性质：</td>                               -->
<!-- 					<td>                   -->
<%-- 						<input id='unitTypeSearch' name='unitType' class="easyui-combobox" data-options="editable:false,valueField:'ID',textField:'TEXT', --%>
<%-- 							url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=OWNERTYPE&showNumber=1,2,3,4,5,6,7,8,9,12'"/> --%>
<!-- 					</td> -->
<!-- 				</tr> -->
				<tr>	
					<td class="tbl_td_label">名称：</td>            
					<td style="padding-right: 50px;">                  
						<input id='unitNameSearch' name='unitName' class="easyui-textbox" />
					</td>	     
				</tr>
			</table>
		</form>	
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a onclick='searchUnitListGrid("myColltree")' class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
		<a onclick='formClear("unitInfoFromSearc")' class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a>	
		<!-- 左侧单位树 -->
		<div style="position:relative;left:0px;top:0px;width:100%;height:80%;overflow-y:auto;">
			<ul id='myColltree'></ul>
		</div>
	</div>
	<!-- 页面右侧部分 -->
	<div data-options="region:'center'" style="width: 35%">
		<div class="easyui-layout" data-options="fit:true">    
			<div data-options="region:'west',title:'采集点选择界面'" style="width: 90%">
				<form id="collInfoFromSearc">
				  	<table  cellpadding="0" cellspacing="0" class="tbl_search_bg" width="100%">  
				 		<tr>
							<td class="tbl_td_label" width="13%">名称：</td>  
							<td  width="20%">
								<input id='collInfoFromSearc-collectName'  class="easyui-textbox"  /> 
							</td>
							<td class="tbl_td_label" width="13%">状态：</td>
							<td  width="20%">
								<input id="collInfoFromSearc-status" class="easyui-combobox"  data-options="editable:false,valueField:'ID',textField:'TEXT',
								url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=COLLINFOSTATUS'" />  
							</td>
						</tr>
						<tr style="text-align: right;">
								<td colspan="6"> 
								   <a id="serchColl"  onclick='searchCollListGrid("collInfoFromSearc","collListGrid")' class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&nbsp;
								   <a id="clearColl"  onclick='formClear("collInfoFromSearc");' class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a>  &nbsp;
								</td> 
						</tr>
					</table> 
				</form>
				<table id='collListGrid' style="width：100%;" ><input id="cifs-unitId" name="cifs-unitId" type="hidden" /></table> 
			</div>
			<div data-options="region:'center',title:'功能' "   align="center" >
				<div style="left:10px;;top:150px;position:absolute;">
					<input type="image" src="<%= request.getContextPath()%>/common/image/left.png" onclick="addColl();" /><br> 
					
				</div>
				<div style="left:10px;;top:180px;position:absolute;">
					 
					<input type="image" src="<%= request.getContextPath()%>/common/image/right.png" onclick="removeColl();"  />
				</div>
			</div>
		</div>
	</div>
	<div data-options="region:'east',title:'采集点维护界面' "  style="width: 40%">
		 <form id="collEditFromSearc">
		     <table  cellpadding="0" cellspacing="0" class="tbl_search_bg" width="100%">  
			        <tr>
					<td class="tbl_td_label" width="13%">名称：</td>  
					<td  width="20%">
						<input id='collEditFromSearc-collectName'  class="easyui-textbox"  /> 
					</td>
<!-- 					<td class="tbl_td_label" width="13%">状态：</td> -->
<!-- 					<td  width="20%"> -->
<%-- 						<input id="collEditFromSearc-status" class="easyui-combobox"  data-options="editable:false,valueField:'ID',textField:'TEXT', --%>
<%-- 						url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=COLLINFOSTATUS'" />    --%>
<!-- 					</td> -->
				</tr>
				<tr style="text-align: right;">
					<td colspan="6"> 
					   <a id="searchCollEdit"  onclick='searchCollListGrid("collEditFromSearc","collEditListGrid")' class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&nbsp;
					   <a id="clearCollEdit"  onclick='formClear("collEditFromSearc");' class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a>  &nbsp;
					</td> 
				</tr>
			</table> 
		</form>
		<div id="updateUnitCollDd"></div>
		<table id='collEditListGrid' style="width：100%;" ></table> 
	</div>
</div>
<script type="text/javascript">
var gridHeight = parent.$("#page_seconde").height()-87;

var jsonData = null;
//左侧树结构相关操作开始
function searchUnitListGrid(dataId){ 
	var unitName = $("#unitNameSearch").val();
	var unitType = $("#unitTypeSearch").combobox("getValue");
    if(unitName!=""||unitType!=""){    
	   $("#myColltree").tree({onLoadSuccess:function() {
		 	$('#myColltree').tree('expandAll');
	    var node = $('#myColltree').tree('getChecked');
	    for(var i = 0;i<node.length;i++){       
			$("#myColltree").tree('uncheck',node[i].target);
			$(node[i].target).removeClass('myselected1');
		}      
		    
	    $.ajax({    
	        url:"<%=request.getContextPath()%>/unit/SelectUnitTreeData.do?cmd=SelectUnitTreeData",
	        data:{"unitName":unitName,"unitType":unitType } ,
	        dataType:"json",
	        cache:false,
	        async:true,
	        success:function(data){
	        	jsonData = data;
	            $.each(data,function(i, obj){
	                var n = $("#myColltree").tree('find',obj.ID);
	                if(n){
	                    $("#myColltree").tree('check',n.target);  
	                    $(n.target).addClass('myselected1');
	                }                                             
	            });   
	        }     
	    });
	   }
	   });
   }else{
	   $("#myColltree").tree({onLoadSuccess:function() {
   			var node = $('#myColltree').tree('getRoot'); 
	    	$('#myColltree').tree('select',node.target);
	   	}
	   });
   }
}
//左侧树选中操作
function qxSelected(data){
	$.each(data,function(i, obj){
        var n = $("#myColltree").tree('find',obj.ID);
        if(n){
            $(n.target).removeClass('myselected1');
        }                                             
    });   
}
/**
 * 左侧树结构相关操作结束
 */

$(function(){ 
	
	//采集点维护管理左侧的单位树
	$('#myColltree').tree({ 
	    url:'<%=request.getContextPath()%>/unit/getUnitTreeData.do?cmd=getUnitTreeData', 
	    cascadeCheck:false,
	    onLoadSuccess : function() {
	    	var node = $('#myColltree').tree('getRoot'); 
	    	$('#myColltree').tree('select',node.target);
	    },
	    onSelect : function(node) {
	    	$('#collListGrid').datagrid('load',{
	    		unitId: node.id
	    	});
	    	$('#collEditListGrid').datagrid('load',{
	    		unitId: node.id
	    	});
	    	$("#cifs-unitId").val(node.id);
	    	if(jsonData!=null){
	    		qxSelected(jsonData);
	    	}
	    	$(node.target).removeClass('myselected1');
		}
	});
	//页面中间采集点选择页面列表
	$('#collListGrid').datagrid({    
		url : '<%= request.getContextPath()%>/coll/queryCollInfoList.do?cmd=queryCollInfoList',  
		width:'100%',
		pageNumber:1,
		pageSize:pageNum,
		fit:false,
		height:gridHeight,   
		method:'post',  
		title:'采集点信息列表',
		striped:true,      
		ctrlSelect:true, 
		rownumbers:true,
		fitColumns: true,   
		pagination : true,
	    columns:[[
			{field:'ID',checkbox:true},     
			{field:'DATANAME',title:'名称' ,width:'44%',align:'center'},
	        {field:'COLLNAME',title:'转换名称' ,width:'50%',align:'center'}
	    ]],
	    toolbar :['-',{ 
	    	text:'修改转换名称',
			iconCls:'icon-add',
			handler:function(){ 
				var selRows = $("#collListGrid").datagrid("getSelections"); 
	            if(selRows.length !=1){     
	                $.messager.alert("警告",'请选择一条记录！','warning'); 
	                return false;
	            } 
				$('#updateUnitCollDd').dialog('refresh', '<%= request.getContextPath()%>/jsp/collInfo/updateUnitColl.jsp');  
				$('#updateUnitCollDd').dialog('open');
			} 
		},'-'],
		 onHeaderContextMenu: function(e, field){
			 e.preventDefault();
			 if (!cmenu){
			      createColumnMenu('collListGrid');
			 }
			 cmenu.menu('show', {
			      left:e.pageX,
			      top:e.pageY
			 });
	   	},onLoadSuccess:function(data){
           var selRows = $("#collListGrid").datagrid ("getSelections"); 
           if(selRows.length>0){
           		 $("#collListGrid").datagrid("clearChecked");	
           }
	   }
	});	
	//页面右侧采集点维护页面列表
	$('#collEditListGrid').datagrid({    
		url : '<%= request.getContextPath()%>/coll/searchCollInfo.do?cmd=searchCollInfo',  
		width:'100%',
		pageNumber:1,
		pageSize:pageNum,
		fit:false,
		height:gridHeight,   
		method:'post',  
		title:'采集点信息列表',
		striped:true,
		ctrlSelect:true, 
		rownumbers:true,
		fitColumns: true,   
		pagination : true,
		columns:[[
			{field:'DATAID',checkbox:true},
			{field:'DATANAME',title:'编号' ,width:'42%',align:'center'},
			{field:'DATANAME',title:'名称' ,width:'42%',align:'center'}
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
	   	},
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
					$('#collListGrid').datagrid('hideColumn', item.name);
					cmenu.menu('setIcon', {
						target: item.target,
						iconCls: 'icon-empty'
					});
					$('#collEditListGrid').datagrid('hideColumn', item.name);
					cmenu.menu('setIcon', {
						target: item.target,
						iconCls: 'icon-empty'
					});
				} else {
					$('#collListGrid').datagrid('showColumn', item.name);
					cmenu.menu('setIcon', {
						target: item.target,
						iconCls: 'icon-ok'
					});
					$('#collEditListGrid').datagrid('showColumn', item.name);
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
		var fields = $('#collEditListGrid').datagrid('getColumnFields');
		for(var i=0; i<fields.length; i++){
			var field = fields[i];
			var columenOption=$('#collEditListGrid').datagrid('getColumnOption',field);
			if(!columenOption.checkbox==true){
				var col = $('#collEditListGrid').datagrid('getColumnOption', field);
				cmenu.menu('appendItem', {
					text: col.title,
					name: field,
					iconCls: 'icon-ok'
				});       
			}
		}
		var fields1 = $('#collListGrid').datagrid('getColumnFields');	
		for(var i=0; i<fields1.length; i++){
			var field = fields1[i];
			var columenOption=$('#collListGrid').datagrid('getColumnOption',field);
			if(!columenOption.checkbox==true){
				var col = $('#collListGrid').datagrid('getColumnOption', field);
				cmenu.menu('appendItem', {
					text: col.title,
					name: field,
					iconCls: 'icon-ok'
				});       
			}
		}
	}
});	

/**
 * 表单查询
 */
function searchCollListGrid(formId,dataId){
	var collectName = $("#"+formId+"-collectName").val();
	var unit = $("#cifs-unitId").val();
	$("#"+dataId).datagrid('load',{
		unitId: unit,
		collectName: collectName
	});
	
}

/**
 * 添加
 */
function addColl(){
	
	var selRows = $("#collEditListGrid").datagrid("getSelections"); 
	var unitId = $("#cifs-unitId").val();
    if(selRows.length <1){     
        $.messager.alert("警告",'请选择一条记录！','warning'); 
        return false;
    } 
    for(var i = 0 ; i < selRows.length ; i++){  //循环checkbox组
        if(selRows[i].STATUS=="关闭"){
        	 $.messager.alert("提示","选择数据中包含关闭状态的数据!",'info'); 
        	return false;
        }
	}
    var CollIds ="";
    var collname ="";
    for(var i = 0 ; i < selRows.length ; i++){  //循环checkbox组
    	CollIds=CollIds+selRows[i].DATAID+",";  //加入选中的checkbox
    	collname=collname+selRows[i].DATANAME+",";
	}
    CollIds = CollIds.substring(0,CollIds.length-1);
    collname = collname.substring(0,collname.length-1);
	$.messager.progress();//打开进度条
	$.ajax({ 
        type: "post", 
        url: "<%= request.getContextPath()%>/coll/saveCollUnitInfo.do?cmd=saveCollUnitInfo&unitId="+unitId,
        data:{"collectId":CollIds,'collname':collname} ,//form表单序列化
        dataType: "json", 
        success: function(data){ 
        	 $.messager.progress('close');//关闭进度条
        	if(data.flag){ 
        		  
	    		 $('#collListGrid').datagrid('reload');
	    		 $('#collEditListGrid').datagrid('reload');
	    		 $("#collListGrid").datagrid("clearSelections");
	    		 $("#collEditListGrid").datagrid("clearSelections");
			     $.messager.alert("提示",data.messager,'info'); 
	    		// window.location.reload();
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
/**
 * 删除操作
 */
function removeColl(){
	var selRows = $("#collListGrid").datagrid("getSelections"); 
    if(selRows.length <1){     
        $.messager.alert("警告",'请选择一条记录！','warning'); 
        return false;
    } 
	$.messager.progress();//打开进度条
	$.ajax({ 
        type: "post", 
        url: "<%= request.getContextPath()%>/coll/deleteCollUnitInfo.do?cmd=deleteCollUnitInfo",
        data:{"collId":findSelections("collListGrid")} ,//form表单序列化
        dataType: "json", 
        success: function(data){ 
        	 $.messager.progress('close');//关闭进度条
        	if(data.flag){ 
	    		 $('#collListGrid').datagrid('reload');
	    		 $('#collEditListGrid').datagrid('reload');
	    		 $("#collListGrid").datagrid("clearSelections");
	    		 $("#collEditListGrid").datagrid("clearSelections");
			     $.messager.alert("提示",data.messager,'info'); 
			     //window.location.reload();
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

/**
 * 修改转换名称页面
 */
$('#updateUnitCollDd').dialog({    
	title:'修改转换名称',
	width:600,
    height:'auto', 
    top: winTop,
    left: winLeft,
    modal:true, 
    minimizable:false,
    maximizable:false,
    iconCls:'icon-save',
    closed:true, 
    href:'<%= request.getContextPath()%>/jsp/collInfo/updateUnitColl.jsp'  
}); 


/**
 * 功能描述：从列表中找出选中的id值列表
 */
function findSelections(checkboxName) {  //从列表中找出选中的id值列表
	var selRows = $("#"+checkboxName).datagrid("getSelections"); //通过name取出所有的checkbox
    var ids = "";  //定义id值的数组
    for(var i = 0 ; i < selRows.length ; i++){  //循环checkbox组
            ids=ids+"'"+selRows[i].ID+"',";  //加入选中的checkbox
    }
    return ids.substring(0, ids.length-1);
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