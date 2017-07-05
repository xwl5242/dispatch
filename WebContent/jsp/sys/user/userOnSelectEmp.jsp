<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>员工管理</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<jsp:include page="/jsp/header.jsp" />
</head>
<body> 
	<div  class="easyui-layout" data-options="fit:true" style="height: 100%;">
		<div data-options="region:'west',title:'组织机构树'" style="width: 260px">
				<form id="unitInfoFromSearc"  style="width: 230px;height: auto;">            
					<table>      
						<tr>                   
							<td class="tbl_td_label">性质：</td>                              
							<td>                  
								<input id='unitTypeSearch' name='unitType' class="easyui-combobox" data-options="editable:false,valueField:'ID',textField:'TEXT',
									url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=OWNERTYPE&showNumber=1,2,3,9'"/>
							</td>
						</tr>
						<tr>	
							<td class="tbl_td_label">名称：</td>            
							<td style="padding-right: 35px;">                  
								<input id='unitNameSearch' name='unitName' class="easyui-textbox" />
							</td>	     
						</tr>
						<tr>
							           
							<td  colspan="2" align="center">                  
								<a  onclick='searchUnitListGrid("myuserOnSelectEmpOrgtree")' class="easyui-linkbutton" 
				data-options="iconCls:'icon-search'">查询</a>
					<a  onclick='formClear("unitInfoFromSearc")' class="easyui-linkbutton" 
				data-options="iconCls:'icon-redo'">重置</a>	
							</td>
						</tr>
					</table>
				</form>	
				<div  style="position:relative;left:0px;top:0px;width: 250px;height:76%;margin:5px;overflow-y:auto;">
		 			<ul id='myuserOnSelectEmpOrgtree'></ul>
		 		</div>
		</div>
	<div   data-options="region:'center',title:'员工信息'" >
	 <div id="sysOnSelectEmpSearchPanel"  style="width:100%;" >  
		 <form id="sysOnSelectEmpFromSearc">
		  	<table  cellpadding="0" cellspacing="0" class="tbl_search_bg" width="100%">  
		 		<tr>
					        <td class="tbl_td_label"    width="13%">员工名称：</td> 
							<td  width="20%">
								<input id='EmpUserName' class="easyui-textbox" data-options="iconCls:'icon-search'"/>
							</td>   
							<td class="tbl_td_label" width="13%">工号：</td>  
							<td  width="20%">
								<input id='EmpUsercode'  class="easyui-textbox"  data-options="iconCls:'icon-man'"/> 
							</td>
				</tr>     
				<tr >
							<td class="tbl_td_label" width="13%">性别：</td>
							<td  width="20%">
								<input id="EmpUserSex" class="easyui-combobox" name="EmpSex"   data-options="editable:false,valueField:'ID',textField:'TEXT',  
								url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=SEX'" />
							</td>
						<td style="text-align: right;" colspan="2"> 
						   <!-- <a id="btn"  onclick='searchuserOnSelectEmplistGrid()' class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&nbsp;
						   <a id="btn"  onclick='formClear("sysOnSelectEmpFromSearc");' class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a>  &nbsp; -->
							<a class="easyui-linkbutton" href="javascript:searchuserOnSelectEmplistGrid()" data-options="iconCls:'icon-search'">查询</a>&nbsp;
							<a class="easyui-linkbutton" href="javascript:formClear('sysOnSelectEmpFromSearc');" data-options="iconCls:'icon-redo'">重置</a>&nbsp;
						</td> 
				</tr>
			</table> 
		</form>
	 </div>
	 	<table id='userOnSelectEmplistGrid' style="width：100%;" ></table> 
	 	<table cellpadding="0" cellspacing="0" class="tbl_search_bg" width="100%">
	 		<tr style="text-align: center;" >
		 		<td style="text-align: right;"> 
							   <a   onclick='OkEmpSelect()' class="easyui-linkbutton" data-options="iconCls:'icon-save'">确定</a>&nbsp;
				</td> 
	 		</tr>
	 	</table>
</div>
<script type="text/javascript"> 
var jsonData = null;
$(function(){  
	var myuserOnSelectEmpOrgtreeUrl='<%=request.getContextPath()%>/org/lodeTreeData.do?cmd=findTreeData&status=1';
	var myuserOnSelectEmpOrgtreequeryParams={id:0};
	if(sysSelectEmp.flag==1){ 
		 myuserOnSelectEmpOrgtreequeryParams={id:'${sessionScope.ECCORG.id}'}; 
		 myuserOnSelectEmpOrgtreeUrl='<%=request.getContextPath()%>/org/lodeTreeDataByOrg.do?cmd=lodeTreeDataByOrg';
	} 
	$('#myuserOnSelectEmpOrgtree').tree({ 
	    url:myuserOnSelectEmpOrgtreeUrl, 
	    queryParams:myuserOnSelectEmpOrgtreequeryParams,
	    onSelect : function(node) {
	    	if(jsonData!=null){
	    		qxSelected(jsonData);
	    	}
	    	$('#userOnSelectEmplistGrid').datagrid('load',{
	    		orgId: node.id
	    	});
		},
		onLoadSuccess : function() {
	    	var node = $('#myuserOnSelectEmpOrgtree').tree('getRoot');
	    	$('#myuserOnSelectEmpOrgtree').tree('select',node.target); 
	    } 
	});
	/**
	 * 初始化查询Panel
	 */
	$('#sysOnSelectEmpSearchPanel').panel({    
		  collapsible:true,
		  width:"100%",
		  title:"查询"
	}); 
	/**
	 * 初始化列表组建
	 */
	$('#userOnSelectEmplistGrid').datagrid({    
			url : '<%= request.getContextPath()%>/employee/getEmpLoyeeList.do?cmd=getEmpLoyeeList', 
			width:'100%',
			pageNumber:1,
			pageSize:10,
			fit:false,
			height:248,   
			method:'post',  
			title:'员工信息列表',
			idField:"ID",
			ctrlSelect:true,
			sortName:'CREATETIME',
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
		  	        {field:'ID',hidden:true},     
			        {field:'NAME',title:'员工名称' ,width:100} ,
			        {field:'AGE',title:'年龄',width:60},   
			        {field:'MOBILE',title:'联系电话',width:120},
			        {field:'CODE',title:'工号' ,width:80},
			        {field:'SEX',title:'性别' ,width:60}
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
						$('#userOnSelectEmplistGrid').datagrid('hideColumn', item.name);
						cmenu.menu('setIcon', {
							target: item.target,
							iconCls: 'icon-empty'
						});
					} else {
						$('#userOnSelectEmplistGrid').datagrid('showColumn', item.name);
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
			var fields = $('#userOnSelectEmplistGrid').datagrid('getColumnFields');
			for(var i=0; i<fields.length; i++){
				var field = fields[i];
				var columenOption=$('#userOnSelectEmplistGrid').datagrid('getColumnOption',field);
				if(!columenOption.checkbox==true){
					var col = $('#userOnSelectEmplistGrid').datagrid('getColumnOption', field);
					cmenu.menu('appendItem', {
						text: col.title,
						name: field,
						iconCls: 'icon-ok'
					});       
				}
			}
		}
});


function searchUnitListGrid(dataId){ 
	var unitName = $("#unitNameSearch").val();
	var unitType = $("#unitTypeSearch").combobox("getValue");
   if(unitName!=""||unitType!=""){    
	   $("#myuserOnSelectEmpOrgtree").tree({onLoadSuccess:function() {
		 	$('#myuserOnSelectEmpOrgtree').tree('expandAll');
	   var node = $('#myuserOnSelectEmpOrgtree').tree('getChecked');
	    for(var i = 0;i<node.length;i++){       
			$("#myuserOnSelectEmpOrgtree").tree('uncheck',node[i].target);
			$(node[i].target).removeClass('myselected1');
		}      
		    
	    $.ajax({    
	        url:"<%=request.getContextPath()%>/org/selectTreeData.do?cmd=selectTreeData",
            data:{"orgName":unitName,"managerType":unitType} ,
	        dataType:"json",
	        type:'post',
	        cache:false,
	        async:true,
	        success:function(data){
	        	jsonData = data;
	            $.each(data,function(i, obj){
	                var n = $("#myuserOnSelectEmpOrgtree").tree('find',obj.ID);
	                if(n){
	                    $("#myuserOnSelectEmpOrgtree").tree('check',n.target);
	                    $(n.target).addClass('myselected1');
	                }                                             
	            }); 
	            //
	        }     
	    });
	   }
	   });
   }else{
	   var node = $('#myuserOnSelectEmpOrgtree').tree('getChecked');
	    for(var i = 0;i<node.length;i++){       
			$("#myuserOnSelectEmpOrgtree").tree('uncheck',node[i].target);
			$(node[i].target).removeClass('myselected1');
		}  
	    var node = $('#myuserOnSelectEmpOrgtree').tree('getRoot'); 
    	$('#myuserOnSelectEmpOrgtree').tree('select',node.target);
   } 
   
}


/**
 * 列表查询
 * 根据查询条件刷新列表数据
 */
function searchuserOnSelectEmplistGrid(){
	var EmpNameSearch =$("#EmpUserName").val();
	var EmpcodeSearch =$("#EmpUsercode").val();
	var EmpSexSearch =$("#EmpUserSex").combobox("getValue");
	var node = $('#myuserOnSelectEmpOrgtree').tree('getSelected'); 
	$('#userOnSelectEmplistGrid').datagrid('load',{
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

function OkEmpSelect(){
	
	var selRows = $("#userOnSelectEmplistGrid").datagrid("getSelections"); 
    if(selRows.length !=1){     
        $.messager.alert("警告",'请选择一条记录！','warning'); 
        return false;
    } 
    sysSelectEmp.setData(selRows[0]);
    sysSelectEmp.success(selRows[0]);
    
    $('#addUserEmpDd').dialog("close");
}
function qxSelected(data){
	$.each(data,function(i, obj){
        var n = $("#myuserOnSelectEmpOrgtree").tree('find',obj.ID);
        if(n){
            $(n.target).removeClass('myselected1');
        }                                             
    });   
}

</script>
</body> 
</html>