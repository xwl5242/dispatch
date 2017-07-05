<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>数据字典编辑</title> 
<link rel="stylesheet" id='easyuiTheme' type="text/css" href="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/icon.css"> 
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/jquery.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
	<link href="<%= request.getContextPath()%>/common/css/base.css" rel="stylesheet" type="text/css">
		<link href="<%= request.getContextPath()%>/common/css/ecc.css" rel="stylesheet" type="text/css">
			<link href="<%= request.getContextPath()%>/common/css/table.css" rel="stylesheet" type="text/css">
</head>
<body id='body'>
	 <div id="datadicAddPanel" class="easyui-panel"  style="width:100%;" style="height: 100%;" data-options="collapsible:false">
	 	<form id="datadicUpdateform">
				<table cellpadding="0" cellspacing="0" class="tbl_search_bg"
				width="100%">
				<tr>
					<td class="tbl_td_label" width="15%">类型编码：</td>
					<td width="25%">
					<input id='id' name='id'  type="hidden" /> 
					<input id='parid1' name='parid'  type="hidden" /> 
					<input id="typecode1" name='typecode' class="easyui-textbox"
						data-options="required:true,validType:'maxLength[50]'" /></td>
					<td class="tbl_td_label">排序：</td>
						<td width="25%">
						<input id="dicsort" name='dicsort'
						class="easyui-numberbox"
						data-options="required:true,validType:'length[0,2]'" />
					</td>
				</tr>
				<tr>
					<td class="tbl_td_label">字典编码：</td>
						<td width="25%">
						<input id="keyname" name='keyname'
						class="easyui-textbox"
						data-options="required:true,validType:'maxLength[20]'" />
					</td>
					<td class="tbl_td_label" width="15%">字典值：</td>
					
					<td width="25%"><input id="dicname" name='dicname'
						class="easyui-textbox"
						data-options="required:true,validType:'maxLength[50]'" />
					</td>
				</tr>
				<tr>
					<td class="tbl_td_label">是否为默认选项：</td>
						<td width="25%">
						<input id="isSelected" name="isSelected" class="easyui-combobox" data-options="required:true,editable:false,valueField: 'label',textField: 'value',data: [{label: 'true',value: '是'   },{label: 'false',value: '否'}]" />


					</td>
					
				</tr>
				<tr style="text-align: right;">
					<td colspan="4"><a id="btn"  onclick='checkIsSelected()'
						class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>&nbsp;
						<a  onclick='updDataDicDgClocseA()' class="easyui-linkbutton"
						data-options="iconCls:'icon-cancel'">关闭</a> &nbsp;</td>
				</tr>
			</table>
		</form> 
	 </div>
	 <script type="text/javascript">
	 /**
	  * 关闭添加对话窗体
	  */

	  function updDataDicDgClocse(){ 
    	  $('#updatedatadicDd').dialog("close");
	  } 
	  function updDataDicDgClocseA(){ 
		  $.messager.confirm('确认','您确认想要关闭吗？',function(r){    
			    if (r){ 
			    	$('#updatedatadicDd').dialog("close");
				}
		 	});
			
		} 
      
	  function checkIsSelected(){
	    	 if(!$("#datadicUpdateform").form('validate')){
					return false;
			  } 
	    	 var url="<%=request.getContextPath()%>/datadic/checkIsSelected.do?cmd=checkIsSelected";
	    	 
	    	 $.ajax({type:"post",
	    		 	 url:url,
	    		 	 data:$('#datadicUpdateform').serialize(),
	    		 	 dataType:"json",
	    		 	 success:function(data){
	    		 		 if(data.flag){
	    		 			updatedatadicData();
	    		 		 }else{
	    		 			$.messager.alert("警告", data.messager, 'warning');
	    		 		 }
	    		 	 }
	    	 })
	     }
	  
	function updatedatadicData(){
		var json=JSON.stringify($("#datadicUpdateform").serializeObject());
		$.messager.progress();//打开进度条
		$.ajax({ 
            type: "post", 
            url: "<%= request.getContextPath()%>/datadic/updateDic.do?cmd=updateDic",
            data:{json:json},//form表单序列化
            dataType: "json", 
            success: function(data){ 
            	$.messager.progress('close');//关闭进度条
    			if(data.flag){ 
    				$('#datadiclistGrid').datagrid('reload');
    				$("#datadictree").tree("reload");
    				$.messager.alert("提示",data.messager,'info',updDataDicDgClocse());
   		    	}else{
   				     $.messager.alert("警告",data.messager,'warning'); 
   		    	} 
            }
            ,
            error:function(){
            	  $.messager.progress('close');//关闭进度条
            	  $.messager.alert("警告","系统异常！",'warning'); 
            } })}
		var gridSelecedid = $("#datadiclistGrid").datagrid("getSelections")[0].ID; 
		$.ajax({ 
	        type: "post", 
	        url: "<%=request.getContextPath()%>/datadic/findDataDicById.do?cmd=findDataDicById",
			data : {"id" : gridSelecedid},//form表单序列化
			dataType : "json",
			success : function(data) {
				$('#datadicUpdateform').form('load', data);
				if($('#parid1').val()!='1'){
					 $('#typecode1').textbox('readonly');
				}
			}
		});
   	
	
</script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-easyui-1.4.1/custom/myvalidate.js"></script>
</body> 
</html>