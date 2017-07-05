<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改采集点名称</title>
</head>
<body id='body'>
	 <div id="UnitCollUpdPanel" class="easyui-panel"  style="width:100%;" style="height:100%;" data-options="collapsible:false">
	 	<form id="UnitCollUpdform">
		  	<table cellpadding="0" cellspacing="0" class="tbl_search_bg" width="100%">
		 		<tr>
			        <td class="tbl_td_label" width="15%">名称：</td> 
					<td  width="40%">								
						<input id='ID' name='ID' type="hidden" />
						<input id='unitId' name='unitId' type="hidden" />
						<input style="width: 95%" id="collectName" name='collectName' class="easyui-validatebox" readonly="readonly" data-options="validType:'maxLength[100]'"  />
					</td>   
					<td class="tbl_td_label" width="15%">转换名称：</td>  
					<td  width="40%"> 
						<input style="width: 95%" id="convertCollName" name='convertCollName' class="easyui-validatebox" data-options="validType:'maxLength[100]'"/>
					</td> 
				</tr>  
				<tr style="text-align: right;">
					<td colspan="4"> 
					   <a id="btn"  onclick='updateUnitCollData()' class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>&nbsp;
					   <a  onclick='unitCollDgClocse()' class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">关闭</a>  &nbsp;
					</td> 
				</tr>
			</table>
		</form> 
	 </div>
	 <script type="text/javascript">
	 
	 var selRows = $("#collListGrid").datagrid("getSelections"); 
		$.ajax({ 
	        type: "post", 
	        url: "<%= request.getContextPath()%>/coll/queryUnitCollById.do?cmd=queryUnitCollById",
	        data: {"ID":selRows[0].ID},//form表单序列化
	        dataType: "json", 
	        success: function(data){
	        	$("#collectName").val(data.collName);
	        	$("#convertCollName").val(data.collName);
	        	$("#ID").val(data.id);
	        	$("#unitId").val(data.unitId);
	        },
	        error:function(){ 
	        	  $.messager.alert("警告","系统异常！",'warning'); 
	        }
	    });
	 
	 /**
	  * 关闭添加对话窗体
	  */
	function unitCollDgClocse(){ 
		$.messager.confirm('确认','您确认想要关闭吗？',function(r){    
		    if (r){ 
				$('#updateUnitCollDd').dialog("close");
			}
	 });
	} 
	/**
	  * 保存role信息
	  */
	function updateUnitCollData(){
		if(!$("#UnitCollUpdform").form('validate')){
			return false;
		}
		$.messager.progress();//打开进度条
		$.ajax({ 
            type: "post", 
            url: "<%= request.getContextPath()%>/coll/upateUnitCollInfo.do?cmd=upateUnitCollInfo",
            data:{
				id:$("#ID").val(),
				collName:$("#convertCollName").val(),
				unitId:$("#unitId").val()
            } ,
            dataType: "json", 
            success: function(data){ 
            	 $.messager.progress('close');//关闭进度条
            	if(data.flag){ 
		    		 $('#collEditListGrid').datagrid('reload');
		    		 $('#collListGrid').datagrid('reload');
				     $.messager.alert("提示",data.messager,'info',$('#updateUnitCollDd').dialog("close")); 
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
	 
</script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-easyui-1.4.1/custom/myvalidate.js"></script>
</body>
</html>