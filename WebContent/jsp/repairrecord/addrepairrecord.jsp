<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>新增维修记录</title>
<jsp:include page="/jsp/header.jsp" />
</head>
<body> 
	<div id="addrrPanel"  style="width:100%;"  >
		<form id="addrrForm">
			<table  cellpadding="0" cellspacing="0" class="tbl_search_bg" width="100%">  
				<tr>
					<td class="tbl_td_label"  width="5%">系统名称：</td> 
					<td  width="60%">
						<input id='sysName' name='sysName' class="easyui-textbox" />
					</td>
				</tr>
				<tr>
					<td class="tbl_td_label"  width="5%">设备编码：</td> 
					<td  width="60%">
						<input id='dCode' name='dCode' class="easyui-textbox" />
					</td>
				</tr>
				<tr>
					<td class="tbl_td_label"  width="5%">设备名称：</td> 
					<td  width="60%">
						<input id='dName' name='dName' class="easyui-textbox" />
					</td>
				</tr>
				<tr>
					<td class="tbl_td_label"  width="5%">故障时间：</td> 
					<td  width="60%">
						<input id='faultTime' name='faultTime' class="easyui-datetimebox" />
					</td>
				</tr>
				<tr>
					<td class="tbl_td_label"  width="5%">故障描述：</td> 
					<td  width="60%">
						<input id='faultDesc' name='faultDesc' class="easyui-textbox" />
					</td>
				</tr>
				<tr>
					<td class="tbl_td_label"  width="5%">维修时间：</td> 
					<td  width="60%">
						<input id='repairTime' name='repairTime' class="easyui-datetimebox" />
					</td>
				</tr>
				<tr>
					<td class="tbl_td_label"  width="5%">维修人：</td> 
					<td  width="60%">
						<input id='repairMan' name='repairMan' class="easyui-textbox" />
					</td>
				</tr>
				<tr>
					<td class="tbl_td_label"  width="5%">确认时间：</td> 
					<td  width="60%">
						<input id='confirmTime' name='confirmTime' class="easyui-datetimebox" />
					</td>
				</tr>
				<tr>
					<td class="tbl_td_label"  width="5%">备注：</td> 
					<td  width="60%">
						<input id='remark' name='remark' class="easyui-textbox" />
					</td>
				</tr>
				<tr style="text-align: right;">
					<td colspan="12"> 
						<a id="btn" href='javascript:saveRR()' class="easyui-linkbutton" data-options="iconCls:'icon-search'">保存</a>&nbsp;
						<a id="btn"  href='javascript:cancelRR();' class="easyui-linkbutton" data-options="iconCls:'icon-redo'">取消</a>  &nbsp;
					</td> 
				</tr>
			</table> 
		</form>
	</div> 
	<script type="text/javascript"> 
		$(function(){
		});
		
		function saveRR(){
			var sysName = $('#sysName').val();
			var dCode = $('#dCode').val();
			var dName = $('#dName').val();
			var faultTime = $('#faultTime').datetimebox('getText');
			var faultDesc = $('#faultDesc').val();
			var repairTime = $('#repairTime').datetimebox('getText');
			var repairMan = $('#repairMan').val();
			var confirmTime = $('#confirmTime').datetimebox('getText');
			var remark = $('#remark').val();
			if(sysName&&dCode&&dName&&faultTime&&faultDesc&&repairTime&&repairMan&&confirmTime&&remark){
				$.post('<%=request.getContextPath()%>/runRecord/addRR.do',{
					sysName:sysName,dCode:dCode,dName:dName,
					faultTime:faultTime,faultDesc:faultDesc,
					repairTime:repairTime,repairMan:repairMan,
					confirmTime:confirmTime,remark:remark
				},function(data){
					if(data.flag){
						$.messager.alert('info','保存成功！');
						$('#addRR').dialog('close');
						$('#addRR').dialog('refresh');
						$('#rrListGrid').datagrid('reload');
					}
				},'json');
			}else{
				$.messager.alert('提示','请补全维修信息！');
				return;
			}
		}
		
		function cancelRR(){
			$('#addRR').dialog('close');
			$('#addRR').dialog('refresh');
		}
	</script>
</body> 
</html>