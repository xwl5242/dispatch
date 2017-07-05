<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>添加预警管理信息</title> 
<jsp:include page="/jsp/header.jsp" />
</head>
<body id='body'>
	 <div id="ManagerAddPanel" class="easyui-panel"  style="width:100%;" style="height:100%;" data-options="collapsible:false">
	 	<form id="ManagerAddform">
		  	<table cellpadding="0" cellspacing="0" class="tbl_search_bg" width="100%">
		  		<tr>
					<td class="tbl_td_label" >预警设置名称：</td> 
					<td> 
						<input id="name" name="name" class="easyui-textbox" data-options="required:true"/>
					</td>
					<td class="tbl_td_label" >报警点类型：</td> 
					<td  >
						<input id="dianType" class="easyui-combobox" name="dianType"  data-options="valueField:'id',textField:'text',data: [{id: '0',text: '采集点'},{id: '1',text: '中间点'}],onSelect:function(){changeCollType()}" /> 
					</td> 
				</tr> 
				<tr>
					<td class="tbl_td_label" >单位：</td> 
					<td > 
						<input id="unitTypeDd" type="hidden" />
						<input id="temp-unitId" name="temp-unitId" type="hidden" style="display:none;"/>
						<input id="collUnit" name="collUnit" type="hidden" style="display:none;"/>
						<input id="unitName" name='unitName' class="easyui-textbox"  data-options="editable:false,required:true,buttonText:'选择',onClickButton:function(){showynDanweiDialog()}"/>
					</td>	 
					<td class="tbl_td_label" width="15%">采集点/中间点：</td>  
					<td  width="25%">	
						<input id="collId" name="collId" type="hidden" style="display:none;"></input>	
						<input id="collectName" name="collectName" class="easyui-textbox" data-options="editable:false,required:true,buttonText:'选择',onClickButton:function(){checkDWADD()}"/>
					</td>   
				</tr> 
		 		<tr>
		 			<td class="tbl_td_label" >报警等级：</td> 
					<td  >
						<input id="warnLevel" name="warnLevel" class="easyui-combobox"  data-options="required:true,editable:false,valueField:'ID',textField:'TEXT',
							url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=WARNLEVEL'" />
					</td>	
					<td class="tbl_td_label" >报警类型：</td> 
					<td  >
						<input id="warnTypeAdd" name="warnType" class="easyui-combobox"  data-options="required:true,editable:false,valueField:'ID',textField:'TEXT',
						url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=WARNTYPE'" />  
					</td> 	
				</tr>
				<tr>
					<td class="tbl_td_label" >报警方式：</td> 
					<td id="ana">
						<input id="anaWarnFun" name="anaWarnFun" class="easyui-combobox" style="width: 133px;" data-options="required:true,editable:false,valueField:'ID',textField:'TEXT',
							url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=ANAWARN'" />
					</td> 
					<td id="onoff" style="display: none;">
						<input id="onoffWarnFun" name="onoffWarnFun" class="easyui-combobox" style="width: 133px;" data-options="required:true,editable:false,valueField:'ID',textField:'TEXT',
							url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=ONOFFWARN'" />
					</td>
		 			<td class="tbl_td_label" >报警值：</td> 
					<td  >
						<input id="warnValue" name="warnValue" class="easyui-textbox" />
					</td>		
				</tr>
				<tr>
					<td class="tbl_td_label" >状态：</td> 
					<td  >
						<select id="status" name="status" class="easyui-combobox" style="width: 133px;">
							<option value="0">禁用</option>
							<option value="1">启用</option>
						</select>
					</td>
				</tr>
				<tr style="text-align: right;">
					<td colspan="4"> 
					   <a id="btn"  href='javascript:saveManager()' class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>&nbsp;
					   <a  onclick='addManagerClose()' class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">关闭</a>  &nbsp;
					</td> 
				</tr>
			</table>
		</form> 
	 </div>
	 <div id="danweiynDd"></div>
	 <div id="addColl"></div>
	 <div id="addParameter"></div>
	 <script type="text/javascript">
	 function changeCollType(){
	 	$('#collUnit').textbox({value:''});
		$('#unitName').textbox({value:''});
		$("#collUnit").next("span").hide();
		$('#collId').textbox({value:''});
		$('#collectName').textbox({value:''});
		$("#collId").next("span").hide();
	 }
	 
	 $("#warnTypeAdd").combobox({
		 onSelect:function(r){
			 if(r.ID==1){
				 $("#onoff").show();
				 $("#ana").hide();
			 }else if(r.ID==2){
				 $("#onoff").hide();
				 $("#ana").show();
			 }
		 }
	 });
	 
	 /**
	  * 关闭添加对话窗体
	  */
	function addManagerClose(){ 
		$.messager.confirm('确认','您确认想要关闭吗？',function(r){    
		    if (r){ 
		    	$('#addManagerDd').dialog("close");
		    	$('#addManagerDd').html('');
			}
	 });
	} 
	 /**
	  * 关闭添加对话窗体
	  */
	function addManagerCloseA(){ 
		   $('#addManagerDd').dialog("close");
		   $('#addManagerDd').html('');
	} 

	  
	function saveManager(){
		var json=JSON.stringify($("#ManagerAddform").serializeObject());
		$.messager.progress();//打开进度条
		$.ajax({
    		type: "post", 
            url: "<%= request.getContextPath()%>/manager/checkValueByManager.do?cmd=checkValueByManager",
            data: {"json":json} ,//form表单序列化
            dataType: "json", 
            success: function(data){
            	$.messager.progress('close');//关闭进度条
				if(!data.flag){
					 $.messager.alert("提示",data.messager,'info'); 
				}else{
					$.ajax({ 
			            type: "post", 
			            url: "<%= request.getContextPath()%>/manager/saveManager.do?cmd=saveManager",
			            data: {json:json,"type":$("input[name='dianType']").val()},//form表单序列化
			            dataType: "json", 
			            success: function(data){
			            	 $.messager.progress('close');//关闭进度条
			            	if(data.flag){ 
					    		 $('#ManagerlistGrid').datagrid('reload');
					    		 $('#addManagerDd').html('');
							     $.messager.alert("提示",data.messager,'info',addManagerCloseA()); 
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
            },
            error:function(){
          	  $.messager.progress('close');//关闭进度条
          	  $.messager.alert("警告","系统异常！",'warning'); 
          }
    	});
	}
	
	$('#danweiynDd').dialog({    
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
	    href:'<%= request.getContextPath()%>/jsp/warn/manager/unitBaseTree.jsp'  
	});
	$('#addColl').dialog({    
		title:'选择采集点',
		width:900,
	    height:500, 
	    top: winTop,
	    left: winLeft,
	    modal:true, 
	    minimizable:false,
	    maximizable:false,
	    iconCls:'icon-save',
	    closed:true, 
	    href:'<%= request.getContextPath()%>/jsp/warn/manager/addParaColl.jsp'  
	});
	$('#addParameter').dialog({    
		title:'选择中间点',
		width:900,
	    height:500, 
	    top: winTop,
	    left: winLeft,
	    modal:true, 
	    minimizable:false,
	    maximizable:false,
	    iconCls:'icon-save',
	    closed:true, 
	    href:'<%= request.getContextPath()%>/jsp/warn/manager/parametersList.jsp'  
	});
	
	function clearColl(){
		$('#collId').val('');
		$('#collectName').textbox('clear');
	 }
	 function showynDanweiDialog(){
		 var collType = $("input[name='dianType']").val();
		 if(collType==''){
			 $.messager.alert("警告","请先选择类型！",'warning');
			 return false;
		 }
	 	 $('#danweiynDd').dialog('refresh','<%= request.getContextPath()%>/jsp/warn/manager/unitBaseTree.jsp'); 
		 $('#danweiynDd').dialog('open');
	 }
	 
	 function transportValue(val,text){
		$('#collUnit').textbox({value:val});
		$('#unitName').textbox({value:text});
		$("#collUnit").next("span").hide();
		clearColl();
	 }
	 function transportValuePar(val,text){
		$('#collId').textbox({value:val});
		$('#collectName').textbox({value:text});
		$("#collId").next("span").hide();
	 }
	 function checkDWADD(){
		var collType = $("input[name='dianType']").val();
		var unitId = $("#collUnit").val();
		var unitName = $("#unitName").val();
		if(unitName==""||unitId==""){
			$.messager.alert("警告","请选择单位！",'warning');
			return false;
		}
		if(collType=="1"){
			$("#temp-unitId").val(unitId);
			$('#addParameter').dialog('refresh','<%= request.getContextPath()%>/jsp/warn/manager/parametersList.jsp'); 
		    $('#addParameter').dialog('open');
		}else if(collType=="0"){
			$("#temp-unitId").val(unitId);
			$('#addColl').dialog('refresh','<%= request.getContextPath()%>/jsp/warn/manager/addParaColl.jsp'); 
		    $('#addColl').dialog('open');
		}
	}
</script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-easyui-1.4.1/custom/myvalidate.js"></script>
</body> 
</html>