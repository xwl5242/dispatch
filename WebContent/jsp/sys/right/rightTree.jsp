<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>权限管理</title>
<jsp:include page="/jsp/header.jsp" />
</head>
    <body>
     <div id="rightTreeId" class="easyui-layout" data-options="fit:true">
		<div data-options="region:'west',title:'权限管理树'" style="width: 280px">
		 			<div style="width: 100%;">
									<table>      
		 			    <form id="rightFromSearc"  style="width: 100%;">            
										<tr>                   
											<td>名称：</td>                 
											<td>                  
												<input id='rightNameSearch' name='rightName' class="easyui-textbox" />
											</td>	     
										</tr>          
								</form>								
									</table>
								&nbsp;&nbsp;<a  id="orgAdd" onclick="rightSave_onClick()" class="easyui-linkbutton"
								data-options="iconCls:'icon-add'">新增</a> 
									<a  onclick="toUpadte_onClick()" class="easyui-linkbutton"
								data-options="iconCls:'icon-edit'">修改</a> 
									<a  onclick="toDelete_onClick()" class="easyui-linkbutton"
								data-options="iconCls:'icon-cancel'">删除</a> 
									<a  onclick='searchRightListGrid("myRightTree")' class="easyui-linkbutton" 
								data-options="iconCls:'icon-search'">查询</a>
		 			</div>
		 			<div style="position:relative;left:0px;top:0px;width: 100%;height:88%;overflow-y:auto;">
		 			    	<ul id='myRightTree'></ul>
		 			</div>
								
							
		</div>
		<div data-options="region:'center',title:'权限管理详细'" >
			<form id="rightform">
					<table cellpadding="0" cellspacing="0" class="tbl_search_bg" width="100%">
						<tr>
							<td class="tbl_td_label" width="15%">权限名称：</td>
							<td>
							<input id="myDbRightId" name='id'  type="hidden"/>
							<input id="childnums" name='childnums'  type="hidden"/>
							<input id="rightname" name='rightname' style="width: 240px" class="easyui-textbox"data-options="editable:false,disabled:true"/>
							</td>
						</tr>
						
						<tr>
							<td class="tbl_td_label">资源链接：</td>
							<td><input id='righturl' name='righturl' style="width: 240px" class="easyui-textbox"data-options="editable:false,disabled:true" /></td>
						 </tr>
						 <tr >
							<td class="tbl_td_label">左侧树链接：</td>
							<td><input id='treeUrl' name='treeUrl' style="width: 240px" class="easyui-textbox"data-options="editable:false,disabled:true"/>
							</td>
						</tr>
						<tr>
							<td class="tbl_td_label">权限类型：</td>
							<td>
								<input id="righttype" class="easyui-combobox" style="width: 240px" name="righttype"   data-options="editable:false,disabled:true,valueField:'ID',textField:'TEXT',
								url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=RIGHTTYPE'" /> 
							</td> 
						</tr>
						<tr>
							<td class="tbl_td_label">权限描述：</td>
							<td><input id='rightdesc' name='rightdesc' style="width: 240px" class="easyui-textbox"data-options="editable:false,disabled:true" />
							</td>
						</tr>
						<tr>
							<td class="tbl_td_label">创建时间：</td>
							<td><input id='createTime' name='createtime' style="width: 240px" class="easyui-textbox"data-options="editable:false,disabled:true" />
							</td>
						</tr>
						<tr>
							<td class="tbl_td_label">创建人：</td>
							<td><input id='createman' name='createman' style="width: 240px" class="easyui-textbox"data-options="editable:false,disabled:true" />
							</td>
						</tr>
						<tr>
							<td class="tbl_td_label">排序：</td>
							<td><input id='seq' name='seq' style="width: 240px" class="easyui-numberbox"data-options="editable:false,disabled:true" />
							</td>
						</tr>
						
						<tr >
							<td class="tbl_td_label">图片：</td>
							<td><input id='picurl' name='picUrl' style="width: 240px" class="easyui-textbox"data-options="editable:false,disabled:true"/>
							</td>
						</tr>
						
					</table>
					
			</form>
			<div id="addRightDd"></div> 
			<div id="eitRightDd"></div> 
			<table id='RightlistGrid' style=""></table>
			
		</div>
		<div data-options="region:'center',title:'权限管理详细'"></div>
	</div>
	<script type="text/javascript">

	var jsonData = null;
	
	/*初始化权限树形图  */
	$('#myRightTree').tree({ 
	    url:'<%= request.getContextPath()%>/right/AllRightTreeData.do?cmd=AllRightTreeData',
	    
	    onLoadSuccess : function() {
	    	$('#myRightTree').tree('expandAll'); 
	    	var node = $('#myRightTree').tree('getRoot'); 
	    	$('#myRightTree').tree('select',node.target);
	    },
	    onDrop : function(target,source){
	    	var pid = $('#myRightTree').tree("getNode",target).id;
	    	var id = source.id;
	    	
	    	
	    },
	    onSelect : function(node) {
	    	if(jsonData!=null){
	    		qxSelected(jsonData);
	    	}
	    	$.ajax({ 
	            type: "post", 
	            url: "<%=request.getContextPath()%>/right/findRightById.do?cmd=findRightById",
				data : {"RightId" :node.id},//form表单序列化
				dataType : "json",
				success : function(data) {
					$('#rightform').form('load', data);
					
			     	$("#myDbRightId").val(data.id);
			     	
						}
					});
				}
	    
	}); 
	
	/* 跳转权限新增页面并将Id传过去 */
	
	  function  rightSave_onClick(){ 
			  $('#addRightDd').empty();
		  	  $('#addRightDd').panel('open').dialog('refresh','<%= request.getContextPath()%>/jsp/sys/right/addRight.jsp');
      }
	
      $('#addRightDd').dialog({
    	
		title:'添加权限信息',
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
	    href:'<%= request.getContextPath()%>/jsp/sys/right/addRight.jsp'  
	}); 
      
      
      /*连接到修改页面*/
      
      function toUpadte_onClick(){
	 // $('#eitRightDd').dialog('open');
	  			$('#eitRightDd').panel('open').dialog('refresh','<%= request.getContextPath()%>/jsp/sys/right/updateRight.jsp');
      }
	  $('#eitRightDd').dialog({    
			title:'修改权限信息',
			width:600,
		    height:'auto', 
		    top: winTop,
		    left: winLeft,
		    modal:true, 
		    minimizable:false, 
		    maximizable:false,
		    iconCls:'icon-edit',
		    closed:true, 
		    href:'<%= request.getContextPath()%>/jsp/sys/right/updateRight.jsp',
		    onClose:function(){
		    	$("#seqFrame").hide();
		    }
		});

	  
	  function toDelete_onClick(){
		 	if($('#myDbRightId').val()==""){
		 		 $.messager.alert("警告","请选择一个节点删除！",'warning');
		 	}else{
		 		$.messager.confirm('确认','您确认想要删除记录吗？',function(r){
		 		if(r){
	 			$.ajax({ 
	 	         	type: "post", 
	 	         	url: "<%= request.getContextPath()%>/right/selectChildRight.do?cmd=selectChildRight",
	 	         	data:{'rightId':$('#myDbRightId').val()},
	 	         	dataType: "json", 
	 	         	success: function(data){
		 	         		if(data.flag){
			 	         		$.messager.confirm('提示',data.messager,function(r){
			 	         			if(r){
			 	         				$.messager.progress();
				 	         			$.ajax({ 
				 	         	         	type: "post", 
				 	         	         	url: "<%= request.getContextPath()%>/right/deleteRight.do?cmd=deleteRight",
				 	         	         	data:{'rightId':$('#myDbRightId').val()},//form表单序列化
				 	         	         	dataType: "json", 
				 	         	         	success: function(data){ 
				 	         	         		$.messager.progress('close');
				 	         	         	   		 $('#myRightTree').tree('reload');
				 	         	        	 	  	 $.messager.progress('close');//关闭进度条
				 	         	        			 $.messager.alert("提示",data.messager,'info');
				 	         	         	},
					 	         	         error:function(){
					 	         	         	  $.messager.progress('close');//关闭进度条
					 	         	         	  $.messager.alert("警告","系统异常！",'warning'); 
					 	         	         }
				 	         	     });
			 	         				
			 	         			}
			 	         		});
				 	         }else{
				 	        	$.messager.progress();
				 	        	$.ajax({ 
		 	         	         	type: "post", 
		 	         	         	url: "<%= request.getContextPath()%>/right/deleteRight.do?cmd=deleteRight",
		 	         	         	data:{'rightId':$('#myDbRightId').val()},//form表单序列化
		 	         	         	dataType: "json", 
		 	         	         	success: function(data){ 
		 	         	         		$.messager.progress('close');
		 	         	        	 	     $('#myRightTree').tree('reload');
		 	         	        			 $.messager.alert("提示",data.messager,'info');
		 	         	         },
		 	         	         error:function(){
		 	         	         	  $.messager.progress('close');//关闭进度条
		 	         	         	  $.messager.alert("警告","系统异常！",'warning'); 
		 	         	         }
		 	         	     });
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
	
	  
	  /**
	   * 表单查询
	   */
	  function searchRightListGrid(dataId){        
	 	var rightName = $("#rightNameSearch").val();
	    if(rightName!=""){
		 	$("#myRightTree").tree({onLoadSuccess:function() {
			 	$('#myRightTree').tree('expandAll'); 
		    	var node = $('#myRightTree').tree('getChecked');
			     for(var i = 0;i<node.length;i++){
			 	    $("#myRightTree").tree('uncheck',node[i].target);
			 	    $(node[i].target).removeClass('myselected1');
			     }
			     $.ajax({    
			         url:"<%=request.getContextPath()%>/right/selectRightTreeData.do?cmd=selectRightTreeData",
			         data:{'rightName':rightName},
			         dataType:"json",
			         type:"post",
			         cache:false,
			         async:true,
			         success:function(data){
			        	 jsonData = data;
			             $.each(data,function(i, obj){
			                 var n = $("#myRightTree").tree('find',obj.ID);
			                 if(n){
		 	                     $("#myRightTree").tree('check',n.target);  
			                     $(n.target).addClass('myselected1');
			                 }                                             
			             });   
			         }     
			     });
		 	}});
	    }else{
	    	$("#myRightTree").tree({onLoadSuccess:function() {
	    		$('#myRightTree').tree('expandAll');
	    		var node = $('#myRightTree').tree('getRoot'); 
		    	$('#myRightTree').tree('select',node.target);
	    	}
	    	})
	    }
	     
	     
	  }	
	  function qxSelected(data){
			$.each(data,function(i, obj){
		        var n = $("#myRightTree").tree('find',obj.ID);
		        if(n){
		            $(n.target).removeClass('myselected1');
		        }                                             
		    });   
		}
</script>
</body> 

	
</html>