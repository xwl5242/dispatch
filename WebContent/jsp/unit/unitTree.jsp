<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>用能管理</title>
<jsp:include page="/jsp/header.jsp" />
</head>  
<body>
	<div id="unitTreeId" class="easyui-layout" data-options="fit:true">
		<div data-options="region:'west',title:'用能单位树'" style="width: 410px">
			<form id="unitInfoFromSearc"  style="width:100%;height: auto;">            
				<table style="width:100%;height: auto;">      
					<tr>                   
						<td>性质：</td>                              
						<td>                  
							<input id='unitTypeSearch' name='unitType' class="easyui-combobox" data-options="editable:false,valueField:'ID',textField:'TEXT',
								url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=OWNERTYPE&showNumber=1,2,3,4,5,6,7,8,9,12'"/>
						</td>
						<td>名称：</td>            
						<td>                  
							<input id='unitNameSearch' name='unitName' class="easyui-textbox" />
						</td>	     
					</tr>          
				</table>
			</form>					
								&nbsp;&nbsp;<a  id="orgAdd" onclick="toSave_onClick()" class="easyui-linkbutton"
								data-options="iconCls:'icon-add'">新增</a> 
									<a  onclick="toUpadte_onClick()" class="easyui-linkbutton"
								data-options="iconCls:'icon-edit'">修改</a> 
									<a  onclick="toDelete_onClick()" class="easyui-linkbutton"
								data-options="iconCls:'icon-cancel'">不可用</a>
									<a  onclick="toReturn_onClick()" class="easyui-linkbutton"
								data-options="iconCls:'icon-ok'">可用</a>
									<a  onclick='searchUnitListGrid("myUnittree")' class="easyui-linkbutton" 
								data-options="iconCls:'icon-search'">查询</a>
									<a  onclick='formClear("unitInfoFromSearc")' class="easyui-linkbutton" 
								data-options="iconCls:'icon-redo'">重置</a>
									<!-- <a  onclick='tjUnitListGrid()' class="easyui-linkbutton" 
								data-options="iconCls:'icon-search'">调级</a> -->
			<div  style="position:relative;left:0px;top:0px;width:100%;height:85%;overflow-y:auto;">					      
			<ul id='myUnittree'></ul>
			</div>
		</div> 
		<div data-options="region:'center'">          
		<div  id="unitDivId" data-options="title:'用能单位详细'" style="clear: both;"> 
			<form id="unitform" >       
					<table   id="unitTblId" cellpadding="0" cellspacing="0" class="tbl_search_bg" width="100%">
						<tr>        
							<td class="tbl_td_label" width="15%">编号：</td>
							<td>
							<input type="hidden" id="aa" name="aa"/>
							<input type="hidden" id="bbb" name="bbb"/>
							<input type="hidden" id="status1" name="status1"/>
							<input id="myDbUnitId" name='id'   type="hidden"/>
							<input id="unitCode" name='unitCode' class="easyui-textbox"data-options="disabled:true,editable:false" />
							</td> 
							<td class="tbl_td_label">名称：</td>
							<td><input id='unitName' name='unitName'class="easyui-textbox"data-options="disabled:true,editable:false" /></td>
						 </tr>
						 
						 <tr>
							<td class="tbl_td_label">简称：</td>
							<td><input id='shortName' name='shortName'class="easyui-textbox"data-options="disabled:true,editable:false" /></td>
							<td class="tbl_td_label">简拼：</td>
							<td><input id='jianPin' name='jianPin'class="easyui-textbox"data-options="disabled:true,editable:false" /></td>
						 </tr>
						 
						<tr>
							<td class="tbl_td_label">性质：</td>
							<td>
								<input id="unitType" class="easyui-combobox" name="unitType"   data-options="disabled:true,editable:false,valueField:'ID',textField:'TEXT',url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=OWNERTYPE&showNumber=1,2,3,4,5,6,7,8,9,12 '" /> 
							</td>
							<td class="tbl_td_label">公司类型：</td>
							<td>
								<input id="companyType" class="easyui-textbox" name="companyType"   data-options="disabled:true,editable:false" /> 
							</td>
						</tr>
						<tr>
							<td class="tbl_td_label">排序：</td>
							<td>
								<input id="seq" name="seq" class="easyui-numberbox" data-options="disabled:true,editable:false" />
							</td>
							<td class="tbl_td_label">是否可用：</td>
							<td>
								<input id="status" name="status" class="easyui-textbox" data-options="disabled:true,editable:false" />
							</td>
						</tr>
						<tr>
<!-- 							<td class="tbl_td_label">地区：</td> -->
<!-- 							<td> -->
<%-- 								<input id="areaId" class="easyui-combogrid" name="areaname"   data-options="disabled:true,editable:false,idField:'ID',textField:'AREANAME', --%>
<%-- 								url:'<%= request.getContextPath()%>/unit/findArea.do?cmd=findArea',columns:[[     --%>
<%--         									{field:'AREANAME',title:'地区名称',width:130}]]" />  --%>
<!-- 							</td> -->
							<td class="tbl_td_label" width="13%">地址：</td>  
							<td>
								<input id='address' name='address'   class="easyui-textbox" data-options="disabled:true,editable:true,validType:'length[0,200]'"/> 
							</td>
						</tr>
						<tr>
							<td class="tbl_td_label" width="13%">备注：</td>  
							<td>
								<input id='remarks' name='remarks'   class="easyui-textbox" data-options="disabled:true,editable:true,validType:'length[0,200]'"/> 
							</td>
						</tr>
					</table>
					
			</form>
			</div>
			<div id="yndw" ></div>
			     <iframe id="yndws" frameborder="no" border="0"  width="100%" height="470px;">
			     </iframe>
			</div> 
			
			
		</div>
	</div>
	<div id="addUnit" style="background-color: #FAF7F7"></div>
	<div id="updateUnit"></div>
	<div id="tjUnit"></div>
	<script type="text/javascript">
	var jsonData = null;
	$('#myUnittree').tree({ 
	    url:'<%=request.getContextPath()%>/unit/AllUnitTreeData.do?cmd=AllUnitTreeData',
// 	    checkbox:true,
	    dnd:true,
	    cascadeCheck:false,   
	    onLoadSuccess : function() {
	    	  
	    	var node = $('#myUnittree').tree('getRoot'); 
	    	$('#myUnittree').tree('select',node.target);
	    },
	    onDrop : function(target,source){
	    	var pid = $('#myUnittree').tree("getNode",target).id;
	    	var id = source.id;
	    	$.ajax({ 
			    type: "post", 
			    url: "<%=request.getContextPath()%>/unit/tjUnit.do?cmd=tjUnit",
				data: {"sureId":pid,"ids":id},//form表单序列化
				dataType : "json",
				success : function(data) {
					$.messager.progress('close');//关闭进度条
					if (data.flag) {
						$('#myUnittree').tree('reload');
						$.messager.alert("提示", data.messager, 'info');
					} else {
						$.messager.alert("警告", data.messager, 'warning');
					}
				}
						
			});
	    },
	    onSelect : function(node) { 
	    	$("#aa").val(node.id);
	    	$("#bbb").val(node.unitType);
	    	$("#status1").val(node.status);
	    	if(jsonData!=null){
	    		qxSelected(jsonData);
	    	}
	    	$(node.target).removeClass('myselected1');
	    	if(node.unitType=="12"){
		    	$.ajax({     //机组
		            type: "post",  
		            url: "<%=request.getContextPath()%>/unit/findDetailsById.do?cmd=findDetailsById",
					data : {"detailsId" : node.id},//form表单序列化
					dataType : "json",
					success : function(data) { 
						$('#yndws').attr("src", "<%=request.getContextPath()%>/jsp/ieu/unit/JZWH.jsp");
						$('#unitform').form('load',data);
					}
				});
	    	}else
	    	if(node.unitType=="3"){
		    	$.ajax({     //热源
		            type: "post",  
		            url: "<%=request.getContextPath()%>/unit/findDetailsById.do?cmd=findDetailsById",
					data : {"detailsId" : node.id},//form表单序列化
					dataType : "json",
					success : function(data) { 
						
						$('#yndws').attr("src", "<%=request.getContextPath()%>/jsp/ieu/unit/detail.jsp");
						$('#unitform').form('load',data);
					}
				});
	    	}else if(node.unitType=="1"){
	    		$.ajax({     //用能
		            type: "post",  
		            url: "<%=request.getContextPath()%>/unit/findUnitInfoById.do?cmd=findUnitInfoById",
					data : {"id" : node.id},//form表单序列化
					dataType : "json",
					success : function(data) { 

						$('#yndws').attr("src", "<%=request.getContextPath()%>/jsp/ieu/unit/unitInfo.jsp");          
						$('#unitform').form('load',data);
					}
				});
	    	}else if(node.unitType=="4"){
	    		$.ajax({     //热力站
		            type: "post",  
		            url: "<%=request.getContextPath()%>/unit/findHeatById.do?cmd=findHeatById",
					data : {"heatId" : node.id},//form表单序列化
					dataType : "json",
					success : function(data) { 
						
						$('#yndws').attr("src", "<%=request.getContextPath()%>/jsp/ieu/unit/heat.jsp");
						$('#unitform').form('load',data);
					}
				});
	    	}else if(node.unitType=="5"){
	    		$.ajax({   //一次网  
		            type: "post",  
		            url: "<%=request.getContextPath()%>/unit/findGwById.do?cmd=findGwById",
					data : {"gwId" : node.id},//form表单序列化
					dataType : "json",
					success : function(data) { 
						$('#yndws').attr("src", "<%=request.getContextPath()%>/jsp/ieu/unit/gw.jsp");
						$('#unitform').form('load',data);
					}
				});
	    	}else if(node.unitType=="6"){
	    		$.ajax({     //二次网
		            type: "post",  
		            url: "<%=request.getContextPath()%>/unit/findGwById.do?cmd=findGwById",
					data : {"gwId" : node.id},//form表单序列化
					dataType : "json",
					success : function(data) { 
						$('#yndws').attr("src", "<%=request.getContextPath()%>/jsp/ieu/unit/gw.jsp");
						$('#unitform').form('load',data);
					}
				});
	    	}else if(node.unitType=="7"){
	    		$.ajax({   //楼栋 
		            type: "post",  
		            url: "<%=request.getContextPath()%>/unit/findLdById.do?cmd=findLdById",
					data : {"ldId" : node.id},//form表单序列化
					dataType : "json",
					success : function(data) { 
						$('#yndws').attr("src", "<%=request.getContextPath()%>/jsp/ieu/unit/ld.jsp");
						$('#unitform').form('load',data);
					}
				});
	    	}else if(node.unitType=="8"){
	    		$.ajax({   //支路  
		            type: "post",  
		            url: "<%=request.getContextPath()%>/unit/findGwById.do?cmd=findGwById",
					data : {"gwId": node.id},//form表单序列化
					dataType : "json",
					success : function(data) { 
						$('#yndws').attr("src", "");
						$('#unitform').form('load',data);
					}
				});
	    	}else if(node.unitType=="2"){
	    		$.ajax({   //热力厂
		            type: "post",            
		            url: "<%=request.getContextPath()%>/unit/findUnitInfoById.do?cmd=findUnitInfoById",
					data : {"id": node.id},//form表单序列化
					dataType : "json",
					success : function(data) { 
						$('#yndws').attr("src", "<%=request.getContextPath()%>/jsp/ieu/unit/heat.jsp");
						$('#unitform').form('load',data);
					}
				});
	    	}else if(node.unitType=="9"){
	    		$.ajax({   //热力厂
		            type: "post",            
		            url: "<%=request.getContextPath()%>/unit/findUnitInfoById.do?cmd=findUnitInfoById",
					data : {"id": node.id},//form表单序列化
					dataType : "json",
					success : function(data) { 
						$('#yndws').attr("src", "<%=request.getContextPath()%>/jsp/ieu/unit/heat.jsp");
						$('#unitform').form('load',data);
					}
				});   
	    	}
							}
	    
						});
	
  function  toSave_onClick(){
	  $('#addUnit').empty();
	  $('#addUnit').panel('open').dialog('refresh','<%= request.getContextPath()%>/jsp/ieu/unit/addUnit.jsp');
 }
 
  $('#addUnit').dialog({
	    
		title:'添加用能单位信息',
		width:800,
	    height:300, 
	    top: winTop,
	    left: winLeft,
	    modal:true, 
	    cache: false,
	    minimizable:false, 
	    maximizable:false,
	    iconCls:'icon-save',
	    closed:true, 
	    href:'<%= request.getContextPath()%>/jsp/ieu/unit/addUnit.jsp'  
	});  

  function toUpadte_onClick(){
	  var nodes = $('#bbb').val(); 
	  if(nodes=='1'){
		  $('#updateUnit').panel('open').dialog('refresh','<%= request.getContextPath()%>/jsp/ieu/unit/updateUnit.jsp');
	  }
	  if(nodes=='2'){     
		  $('#updateUnit').panel('open').dialog('refresh','<%= request.getContextPath()%>/jsp/ieu/unit/updateUnit.jsp');
	  }
	  if(nodes=='3'){
		  $('#updateUnit').panel('open').dialog('refresh','<%= request.getContextPath()%>/jsp/ieu/unit/updateUnit.jsp');
	  }
	  if(nodes=='4'){
		  $('#updateUnit').panel('open').dialog('refresh','<%= request.getContextPath()%>/jsp/ieu/unit/updateHeatStation.jsp');
	  }
	  if(nodes=='5'){     
		  $('#updateUnit').panel('open').dialog('refresh','<%= request.getContextPath()%>/jsp/ieu/unit/updateGW.jsp');
	  }
	  if(nodes=='6'){
		  $('#updateUnit').panel('open').dialog('refresh','<%= request.getContextPath()%>/jsp/ieu/unit/updateGW.jsp');
	  }
	  if(nodes=='7'){
		  $('#updateUnit').panel('open').dialog('refresh','<%= request.getContextPath()%>/jsp/ieu/unit/updateLD.jsp');
	  }
	  if(nodes=='8'){
		  $('#updateUnit').panel('open').dialog('refresh','<%= request.getContextPath()%>/jsp/ieu/unit/updateZL.jsp');
	  }
	  if(nodes=='9'){
		  $('#updateUnit').panel('open').dialog('refresh','<%= request.getContextPath()%>/jsp/ieu/unit/updateUnit.jsp');
	  }
	  if(nodes=='12'){
		  $('#updateUnit').panel('open').dialog('refresh','<%= request.getContextPath()%>/jsp/ieu/unit/updateJZWH.jsp');
	  }
  }
  $('#updateUnit').dialog({  
	    
		title:'修改用能信息',
		width:600,
	    height:'auto',     
	    top: winTop,
	    left: winLeft,
	    modal:true, 
	    minimizable:false, 
	    maximizable:false,
	    iconCls:'icon-edit',
	    closed:true, 
	    href:'',
	    onClose:function(){
	    	$("#seqFrame").hide();
	    }
	});
 
 function toDelete_onClick(){
	 var node = $('#bbb').val(); 
	 if(node=="1"||node=="2"||node=="3"||node=="9"){
		 $.messager.alert("提示","用能单位，热源，中心不能在此修改，请重新选择！"); 
		 return false;
	 }
	 var nodes = $('#myUnittree').tree('getChecked'); 
	 var ids = null;
	 if(nodes.length==0){
		 ids = $("#aa").val();
	 }else{    
		 for(var i=0;i<nodes.length;i++){
			 ids += "," + nodes[i].id;
			 if(nodes[i].unitType=="1"||nodes[i].unitType=="2"||nodes[i].unitType=="3"||nodes[i].unitType=="9"){
				 $.messager.alert("提示","用能单位，热源，中心数据不能在此修改，请重新选择！"); 
				 return false;
			 }
		 }
	 }
	 if(nodes==null){     
         $.messager.alert("警告",'请选择一条记录！','warning'); 
         return false;
     } 
	 if($("#status").val()=="不可用"){
		 $.messager.alert("提示",'此单位已为不可用状态，请选择可用单位进行修改！','warning'); 
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
		            		searchUnitListGrid("myUnittree");   
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
	 var node = $('#bbb').val(); 
	 if(node=="1"||node=="2"||node=="3"||node=="9"){
		 $.messager.alert("提示","用能单位，热源，中心数据不能在此修改，请重新选择！"); 
		 return false;
	 }
	 var nodes = $('#myUnittree').tree('getChecked'); 
	 var ids = null;
	 if(nodes.length==0){
		 ids = $("#aa").val();
	 }else{
		 for(var i=0;i<nodes.length;i++){
			 ids += "," + nodes[i].id;
			 if(nodes[i].unitType=="1"||nodes[i].unitType=="2"||nodes[i].unitType=="3"||nodes[i].unitType=="9"){
				 $.messager.alert("提示","用能单位，热源，中心数据不能在此修改，请重新选择！"); 
				 return false;
			 }
		 }
	 }
	 if(nodes==null){     
         $.messager.alert("警告",'请选择一条记录！','warning'); 
         return false;
     } 
	 if($("#status").val()=="可用"){
		 $.messager.alert("提示",'此单位已为可用状态，请选择不可用单位进行修改！','warning'); 
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
		            		searchUnitListGrid("myUnittree");   
						     $.messager.alert("提示",'已修改为可用','info'); 
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
  
 function  tjUnitListGrid(){
	 var node = $('#myUnittree').tree('getChecked');
	 if(node.length==0){
		 $.messager.alert("提示","请勾选要调级的用能单位！"); 
		 return false;
	 }
	  $('#tjUnit').empty();
	  $('#tjUnit').panel('open').dialog('refresh','<%= request.getContextPath()%>/jsp/ieu/unit/tjUnit.jsp');
}

 $('#tjUnit').dialog({
	    
		title:'用能单位调级',
		width:300,
	    height:400, 
	    top: ($(window).height()-300) * 0.5,
	    left: ($(window).width()-400) * 0.5,
	    modal:true, 
	    cache: false,
	    minimizable:false, 
	    maximizable:false,
	    iconCls:'icon-save',
	    closed:true, 
	    href:'<%= request.getContextPath()%>/jsp/ieu/unit/tjUnit.jsp'  
	});  
 
 
 /**
  * 表单查询
  */
 function searchUnitListGrid(dataId){ 
	var unitName = $("#unitNameSearch").val();
	var unitType = $("#unitTypeSearch").combobox("getValue");
	if(unitName!=""||unitType!=""){           
	 	$("#myUnittree").tree({onLoadSuccess:function() {
	 	$('#myUnittree').tree('expandAll');
   	  
	  
   	    var node = $('#myUnittree').tree('getChecked');
	    for(var i = 0;i<node.length;i++){       
			$("#myUnittree").tree('uncheck',node[i].target);
			$(node[i].target).removeClass('myselected1');
		}
	    $.ajax({    
	        url:"<%=request.getContextPath()%>/unit/SelectUnitTreeData.do?cmd=SelectUnitTreeData",
	        data:{"unitName":unitName,"unitType":unitType } ,
	        dataType:"json",
	        type:"post",
	        cache:false,
	        async:true,
	        success:function(data){
	        	jsonData = data;
	            $.each(data,function(i, obj){
	                var n = $("#myUnittree").tree('find',obj.ID);
	                if(n){
	                    $("#myUnittree").tree('check',n.target);  
	                    $(n.target).addClass('myselected1');
	                }                                             
	            });   
	        }     
	    });
	   }
	 }); 
    }else{
    	$("#myUnittree").tree({onLoadSuccess:function() {
    		var node = $('#myUnittree').tree('getRoot'); 
	    	$('#myUnittree').tree('select',node.target);
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
 
 
 
 function mybb(id,name){
	 window.top.ifTab(id,name);  
}
 function mybbUpdate(id,name){
	 debugger;
	 window.top.ifTab(id,name);  
}
 
 function mycc(id,name){
	 window.top.ifAAA(id,name);  
}
 function refreshTree(){
	 searchUnitListGrid("myUnittree"); 
 }
 
 function qxSelected(data){
		$.each(data,function(i, obj){
	        var n = $("#myUnittree").tree('find',obj.ID);
	        if(n){
	            $(n.target).removeClass('myselected1');
	        }                                             
	    });   
	}
	</script>
</body>


</html>