<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>修改采集点数据</title>
<jsp:include page="/jsp/header.jsp" />
</head>
<body> 
	<div>
		<table style="height: 40px;margin-top: 40px;">  
			<tr>
				<td class="tbl_td_label" width="5%">点名称：</td> 
				<td width="20%">
					<select id="pnames" class="easyui-combobox" style="width:80%"></select>
				</td>
	            <td class="tbl_td_label" width="10%">采集时间：</td>  
				<td width="40%"><input id="startTimes" name='startTimes'
					class="easyui-datebox" style="width:35%" data-options="editable:false" />
					~<input id="endTimes" name='endTimes'
					class="easyui-datebox" style="width:35%" data-options="editable:false" />
				</td>
				<td class="tbl_td_label" width="5%">采集值：</td> 
				<td width="20%">
					<input id="pvalue" class="easyui-numberbox" style="width:80%" data-options="precision:5" />
				</td>
			</tr>
			<tr style="text-align: right;">
				<td colspan="12" width="25%" style="padding-right: 35px;"> 
				   <a id="savePV" onclick='savePV();' class="easyui-linkbutton" data-options="iconCls:'icon-search'">保存</a>&nbsp;
				   <a id="closeED"  onclick='closeED();' class="easyui-linkbutton" data-options="iconCls:'icon-redo'">取消</a>
				</td> 
			</tr>
		</table> 
	</div> 
	<script type="text/javascript"> 
		
		$(function(){
			
			//pname，采集点名称下拉框初始化
			$('#pnames').combobox({
			    url:'<%=request.getContextPath()%>/ea/pnameListJson.do',
			    valueField:'ID',
			    textField:'TEXT'
			});
			
			$('#startTimes').datebox({
				onSelect:function(date){
					var end = new Date($('#endTimes').datebox('getValue')).getTime();
					if(end!=null&&end!=''){
						if(end<new Date(date).getTime()){
							$.messager.alert('提醒', '开始时间不能大于结束时间,请重新选择!');  
							$('#startTimes').datebox('setValue','');
							return ;
						}
					}
				}
			});
			
			$('#endTimes').datebox({
				onSelect:function(date){
					if($('#startTimes').datebox('getValue')==null||$('#startTimes').datebox('getValue')==''){
						$.messager.alert('提醒', '请先选择开始时间!'); 
						$('#endTimes').datebox('setValue','');
						return ;
					}
					var start = new Date($('#startTimes').datebox('getValue')).getTime();
					if(start!=null&&start!=''){
						if(start>new Date(date).getTime()+24*60*60*1000){
							$.messager.alert('提醒', '结束时间不能小于开始时间,请重新选择!');  
							$('#endTimes').datebox('setValue','');
							return ;
						}
					}
				}
			});
		});
		
		function savePV(){
			var pname = $('#pnames').combobox('getText');
			var startTime = $('#startTimes').datebox('getText');
			var endTime = $('#endTimes').datebox('getText');
			var pvalue = $('#pvalue').numberbox('getValue');
			if(pname==null||pname==''){
				$.messager.alert('info','请选择采集点！');
				return;
			}
			if(startTime==null||startTime==''){
				$.messager.alert('info','请选择采集开始时间！');
				return;
			}
			if(endTime==null||endTime==''){
				$.messager.alert('info','请选择采集结束时间！');
				return;
			}
			if(pvalue==null||pvalue==''){
				$.messager.alert('info','请填写修改值！');
				return;
			}
			$.post('<%=request.getContextPath()%>/ea/batchSavePV.do',{
				pname:pname,
				startTime:startTime,
				endTime:endTime,
				pvalue:pvalue
			},function(data){
				if(data.flag){
					$.messager.alert('info','保存成功！');
					$('#editDialog').dialog('close');
					$('#editDialog').dialog('destroy');
					$('#kvListGrid').datagrid('reload');
				}
			},'json');
		}
		
		function closeED(){
			$('#editDialog').dialog('close');
			$('#editDialog').dialog('destroy');
		}
		
		
	</script>
</body> 
</html>