<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>权限菜单编辑</title> 
<link rel="stylesheet" id='easyuiTheme' type="text/css" href="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/icon.css"> 
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/jquery.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
	<link href="<%= request.getContextPath()%>/common/css/base.css" rel="stylesheet" type="text/css">
	<link href="<%= request.getContextPath()%>/common/css/ecc.css" rel="stylesheet" type="text/css">
	<link href="<%= request.getContextPath()%>/common/css/table.css" rel="stylesheet" type="text/css">
	<link href="static/css/bootstrap.min.css" rel="stylesheet" />
	<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
	<link rel="stylesheet" href="static/css/font-awesome.min.css" />
</head>
<body id='body'>
	 <div id="RightAddPanel" class="easyui-panel"  style="width:100%;" style="height: 100%;" data-options="collapsible:false">
	 	<form id="rightUpdateform">
			<table cellpadding="0" cellspacing="0" class="tbl_search_bg"
				width="100%">
				<tr>
					<td class="tbl_td_label" width="15%">权限名称：</td>
					<td width="25%">
					
					<input id='rightId' name='id'  type="hidden" /> 
					<input id='parentid' name='parentid'  type="hidden" /> 
					<input id="rightname" name='rightname' class="easyui-textbox"
						data-options="required:true,validType:'maxLength[50]'" />
						</td>
					<td class="tbl_td_label" width="15%">资源链接：</td>
					
					<td width="25%"><input id="righturl" name='righturl'
						class="easyui-textbox"
						data-options="required:true,validType:'maxLength[150]'" />
					</td>
				</tr>
				<tr>
					<td class="tbl_td_label">权限类型：</td>
						<td width="25%">
						<input id="rightType" class="easyui-combobox" name="righttype"   data-options="editable:false,valueField:'ID',textField:'TEXT',  
								url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=RIGHTTYPE'" />
					</td>
					<td class="tbl_td_label" width="15%">权限描述：</td>
					<td width="25%"><input id="rightDesc" name='rightdesc'
						class="easyui-textbox"
						data-options="required:true,validType:'maxLength[500]'" />
					</td>
					
				</tr>
				
					<tr>
					<td class="tbl_td_label">权限状态：</td>
						<td width="25%">
						<input id="rightStat" class="easyui-combobox" name="rightstat"   data-options="editable:false,valueField:'ID',textField:'TEXT',  
								url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=RIGHTSTAT'" />
					</td>
					<td class="tbl_td_label">排序：</td>
					<td width="25%">
						<input id="seqTransport" type="hidden" value="">
						<input id="seqUpdate" class="easyui-numberbox" name="seq"   data-options="required:true,validType:'length[0,4]'" />
					</td>
				</tr>
				<tr>
					<td class="tbl_td_label">左侧树链接：</td>
					<td width="25%">
						<input id="treeUrl" class="easyui-textbox" name="treeUrl"   data-options="validType:'length[0,100]'" />
					</td>
				
				</tr>
				<tr id="imgDiv">
				    <td class="tbl_td_label">图片：</td>
				    <td width="25%">
				      <input id='iconSelect' name="picUrl" class="easyui-textbox" data-options="buttonText:'选择',onClickButton:function(){showImg()}" />

				    </td>
				</tr>
				
				<tr style="text-align: right;">
					<td colspan="4"><a id="btn"  onclick='updateRightData()'
						class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>&nbsp;
						<a  onclick='addOrgDgClocseA()' class="easyui-linkbutton"
						data-options="iconCls:'icon-cancel'">关闭</a> &nbsp;</td>
				</tr>
			</table>
			
		</form>
		<input type="hidden" value="0" id="seqImprove"/>
<!-- 		<iframe src="seqImprove.jsp" id="seqFrame" name="seqFrame" frameborder="no" border="0" style="margin:0 auto;width:100%;display:none;"></iframe>  -->
		<iframe src="iconSelect.jsp" id="imgFrame" frameborder="no" border="0" style="margin:0 auto;width:100%;display:none;"></iframe>
		<iframe src="tjseq.jsp" id="seqFrame" name="seqFrame" frameborder="no" border="0" style="margin:0 auto;width:100%;display:none;"></iframe>
	 </div>
	 <script type="text/javascript">
	 
	$(function(){
		var nums=$("#childnums").val();
		
		if(nums!="0.0"){
			$("#imgDiv").show();
		}else{
			$("#imgDiv").hide();
		}
		
	});
	
	function showSeq(){
		var i = $("#seqImprove").val();
		if(i>0){
			return false;
		}
		$("#seqImprove").val(1);
		$("#imgFrame").hide();
		$("#seqFrame").show();
		seqFrame.window.initData("<%= request.getContextPath()%>/right/selectRightByPid.do?cmd=selectRightByPid",$('#parentid').val()); 
	}
	function setValue(seq){
		 $("#seqUpdate").numberbox("setValue",seq);
	 }
	
	 /**
	  * 关闭添加对话窗体
	  */
	 function showImg(){
		 $("#seqFrame").hide();
		 $("#imgFrame").show();
	 }
	 function writeImage(icon){
		$("#iconSelect").textbox('setValue', icon);

	 }
	  function addOrgDgClocseA(){ 
		  $.messager.confirm('确认','您确认想要关闭吗？',function(r){    
			    if (r){ 
			    	 $("#imgFrame").hide();
				     $("#seqFrame").hide();
					 $('#eitRightDd').dialog("close"); 
				}
		 	});
		     
		} 
	  function addOrgDgClocse(){ 
    	 $("#imgFrame").hide();
	     $("#seqFrame").hide();
		 $('#eitRightDd').dialog("close"); 
		} 
	  
	  function changeSeq(){
		  $.messager.alert("提示","保存成功",'info');
	  }
      
	/**
	  *修改权限信息
	  */
	function updateRightData(){
		
		if($("#seqImprove").val()=="1"){
			seqFrame.window.save('<%= request.getContextPath()%>/right/updateRightSeq.do?cmd=updateRightSeq','sys_right'); 
		}
		

// 		if($("#seqTransport").val()!=""){
// 			$("#seqUpdate").numberbox("setValue",$("#seqTransport").val());
// 		};
// 		var rightName="";
		var json=JSON.stringify($("#rightUpdateform").serializeObject());
	 	if(!$("#rightUpdateform").form('validate')){
			return false;
		} 
		$.messager.progress();//打开进度条
		$.ajax({ 
            type: "post", 
            url: "<%= request.getContextPath()%>/right/updateRight.do?cmd=updateRight",
            data:{json:json},//form表单序列化 $('#rightUpdateform').serialize() 
            dataType: "json", 
            async: true,
            success: function(data){ 
            	$.messager.progress('close');//关闭进度条
//             	rightName=data.rightName;
    			var node=$('#myRightTree').tree('find', $('#rightId').val());
    			if(data.flag){ 
	    			if (node){
	    				 $("#myRightTree").tree("reload");
	    			} 
    				$.messager.alert("提示",data.messager,'info',addOrgDgClocse());
   		    	}else{
   				     $.messager.alert("警告",data.messager,'warning'); 
   		    	} 
            }
            ,
            error:function(){
            	  $.messager.progress('close');//关闭进度条
            	  $.messager.alert("警告","系统异常！",'warning'); 
            } });}
    
	if($("#myDbRightId").val()==""){
		$.messager.alert("警告","请选择一个节点修改！",'warning');
		addOrgDgClocse();
	}else{
		$.ajax({ 
	        type: "post", 
	        url: "<%=request.getContextPath()%>/right/findRightById.do?cmd=findRightById",
			data : {"RightId" : $("#myDbRightId").val()},//form表单序列化
			dataType : "json",
			success : function(data) {
				$('#rightUpdateform').form('load', data);
			}
		});
	}
   	
	
</script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-easyui-1.4.1/custom/myvalidate.js"></script>
</body> 
</html>