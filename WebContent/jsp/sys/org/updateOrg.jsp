<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>组织机构管理</title>
<link rel="stylesheet" id='easyuiTheme' type="text/css"
	href="<%=request.getContextPath()%>/js/jquery-easyui-1.4.1/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/js/jquery-easyui-1.4.1/icon.css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<link href="<%=request.getContextPath()%>/common/css/base.css"
	rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/common/css/ecc.css"
	rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/common/css/table.css"
	rel="stylesheet" type="text/css">
</head>
<body id='body'>
	 <div id="OrgAddPanel" class="easyui-panel"  style="width:100%;" style="height: 100%;" data-options="collapsible:false">
		<form id="Orgeitform">
			<table  cellpadding="0" cellspacing="0" class="tbl_search_bg" width="100%">
				<tr>
					<td class="tbl_td_label" width="15%">组织编码：</td>
					<td width="25%">
						<input id='id' name='id'  type="hidden" /> 
						<input id='orgparentId' name='parentId' value="" type="hidden" /> 
						<input id="orgCode" name='orgCode' class="easyui-textbox" readonly="readonly" data-options="validType:'maxLength[32]'" />
					</td>
					
					<td class="tbl_td_label" width="15%">组织名称：</td>
					<td width="25%">
						<input id="orgName" name='orgName' class="easyui-textbox" data-options="iconCls:'icon-man',required:true,validType:'maxLength[50]'" />
					</td>
				</tr>
				<tr>
				    
					<td class="tbl_td_label" width="15%">简称：</td>
					<td width="25%">
						<input id="sortName" name='sortName' class="easyui-textbox" data-options="validType:'maxLength[50]'" />
					</td>
					<td class="tbl_td_label" width="15%">简拼：</td>
					<td width="25%">
						<input id="jianPin" name='jianPin' class="easyui-textbox" data-options="iconCls:'icon-man',validType:'maxLength[50]'" />
					</td>
				</tr>
				<tr>
					<td class="tbl_td_label">组织类型：</td>
					<td width="25%">
						<input id="orgTypeUpda" class="easyui-combobox" name="orgType"   data-options="editable:false,valueField:'ID',required:true,textField:'TEXT',url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=ORGTYPE'" />
					</td>
					
					<td class="tbl_td_label">管理类型：</td>
					<td width="25%">
						<input id="managerTypeUpda" class="easyui-combobox" name="managerType" data-options="editable:false,valueField:'ID',required:true,textField:'TEXT',url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=OWNERTYPE&showNumber=1,2,3,9'"/>
					</td>
				</tr>
				<tr>
				    
					<td class="tbl_td_label" width="15%">排序：</td>
					<td width="25%">
						<input id="seq" name='seq' class="easyui-numberbox" data-options="required:true,validType:'length[0,4]'" /></td>
					</td>
					
					<td class="tbl_td_label" width="15%">公司类型：</td>
					<td width="25%">
						<input id="companyTypeUpda" class="easyui-combobox" name="companyType" data-options="editable:false,valueField:'ID',textField:'TEXT',url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=COMPANYTYPE'" />
					</td>
				</tr>
				<tr>
					<td class="tbl_td_label" width="15%">集团性质：</td>
					<td width="25%">
						<input type="hidden" id='isdelete' name='isDelete'/>
						<input id="groupTypeUpda" class="easyui-combobox" name="groupType" data-options="editable:false,valueField:'ID',required:true,textField:'TEXT',url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=GROUPTYPE'" />
					</td>
				</tr>
				<tr style="text-align: right;">
					<td colspan="4"><a id="btn"  onclick='updateOrgData()' class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>&nbsp;
					<a  onclick='addOrgDgClocseA()' class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">关闭</a> &nbsp;</td>
				</tr>
			</table>
		</form>
	 </div>
	 <script type="text/javascript">
	 /**
	  * 关闭修改对话窗体
	  */
	function addOrgDgClocseA(){ 
		$.messager.confirm('确认','您确认想要关闭吗？',function(r){    
		    if (r){ 
		    	 $('#eitOrgDd').dialog("close"); 
			}
	 	});
		
	}
	function addOrgDgClocse(){ 
    	$('#eitOrgDd').dialog("close"); 
	}
	 
	
	 
	 /**赋值id
	 */
    var myDbOrgId = $('#myDbOrgId').val();
	/**
	  * 修改Org信息
	  */
	function updateOrgData(){
	 	if(!$("#Orgeitform").form('validate')){
			return false;
		} 
		$.messager.progress();//打开进度条
		var orgName="";
		$.ajax({ 
            type: "post", 
            url: "<%= request.getContextPath()%>/org/updateOrg.do?cmd=updateOrg",
            data:$('#Orgeitform').serialize() ,//form表单序列化
            dataType: "json", 
            success: function(data){ 
            	 $.messager.progress('close');//关闭进度条

            		if(data.flag){ 
            			orgName=data.orgName;
            			var node=$('#myOrgtree').tree('find', $('#myDbOrgId').val());
            			if (node){
            				$('#myOrgtree').tree('update', {
            					target: node.target,
            					text: orgName,
            					checked:true
            				});
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
            			 searchOrgListGrid("myOrgtree");
	   				     $.messager.alert("提示",data.messager,'info',addOrgDgClocse());
	   				  //$('#orgform').window('refresh');
// 	   				  $("#OrglistGrid").tree("reload");
		   				
	   		    	}else{
	   				     $.messager.alert("警告",data.messager,'warning'); 
	   		    	} 
            	
	            }
	            ,
	            error:function(){
	            	  $.messager.progress('close');//关闭进度条
	            	  $.messager.alert("警告","系统异常！",'warning');
	            }

             });
		//$('#myOrgtree').tree('select', node.target);
		
      }

	if(myDbOrgId==""){
		$.messager.alert("警告","请选择一个节点修改！",'warning');
		addOrgDgClocse();
	}else{
		
		$.ajax({ 
			type: "post", 
			url: "<%=request.getContextPath()%>/org/findOrgById.do?cmd=getOrgnByOrgId",
			data : {"OrgId" :myDbOrgId},//form表单序列化
			dataType : "json",
			success : function(data) {
				$('#Orgeitform').form('load', data);
				$("#id").val(data.id);
				$("#isdelete").val(data.isDelete);
			}
		});
	}
</script>
	<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-easyui-1.4.1/custom/myvalidate.js"></script>
</body> 
</html>