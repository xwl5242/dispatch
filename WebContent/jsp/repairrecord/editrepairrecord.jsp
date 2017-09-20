<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>修改维修记录</title>
<jsp:include page="/jsp/header.jsp" />
</head>
<body> 
	<div id="editrrPanel"  style="width:100%;"  >
		<form id="editrrForm">
			<table  cellpadding="0" cellspacing="0" class="tbl_search_bg" width="100%">  
				<tr>
					<td class="tbl_td_label"  width="5%">系统名称：</td> 
					<td  width="60%">
						<input id='esysName' name='esysName' class="easyui-textbox" />
					</td>
				</tr>
				<tr>
					<td class="tbl_td_label"  width="5%">设备编码：</td> 
					<td  width="60%">
						<input id='edCode' name='edCode' class="easyui-textbox" />
					</td>
				</tr>
				<tr>
					<td class="tbl_td_label"  width="5%">设备名称：</td> 
					<td  width="60%">
						<input id='edName' name='edName' class="easyui-textbox" />
					</td>
				</tr>
				<tr>
					<td class="tbl_td_label"  width="5%">故障时间：</td> 
					<td  width="60%">
						<input id='efaultTime' name='efaultTime' class="easyui-datetimebox" />
					</td>
				</tr>
				<tr>
					<td class="tbl_td_label"  width="5%">故障描述：</td> 
					<td  width="60%">
						<input id='efaultDesc' name='efaultDesc' class="easyui-textbox" />
					</td>
				</tr>
				<tr>
					<td class="tbl_td_label"  width="5%">维修时间：</td> 
					<td  width="60%">
						<input id='erepairTime' name='erepairTime' class="easyui-datetimebox" />
					</td>
				</tr>
				<tr>
					<td class="tbl_td_label"  width="5%">维修人：</td> 
					<td  width="60%">
						<input id='erepairMan' name='erepairMan' class="easyui-textbox" />
					</td>
				</tr>
				<tr>
					<td class="tbl_td_label"  width="5%">确认时间：</td> 
					<td  width="60%">
						<input id='econfirmTime' name='econfirmTime' class="easyui-datetimebox" />
					</td>
				</tr>
				<tr>
					<td class="tbl_td_label"  width="5%">备注：</td> 
					<td  width="60%">
						<input id='eremark' name='eremark' class="easyui-textbox" />
					</td>
				</tr>
				<tr style="text-align: right;">
					<td colspan="12"> 
						<a id="btn" href='javascript:saveRR()' class="easyui-linkbutton" data-options="iconCls:'icon-search'">保存</a>&nbsp;
						<a id="btn"  href='javascript:cancelRR();' class="easyui-linkbutton" data-options="iconCls:'icon-redo'">取消</a>  &nbsp;
					</td> 
				</tr>
			</table> 
			<input id="id" name="id" type="hidden" >
		</form>
	</div> 
	<script type="text/javascript"> 
		$(function(){
// 			debugger;
			var row = JSON.parse('<%=request.getParameter("rowRR")%>');
			$('#id').val(row.ID);
			$('#esysName').val(row.SYSNAME);
			$('#edCode').val(row.DCODE);
			$('#edName').val(row.DNAME);
			$('#efaultTime').val(row.FAULTTIME);
			$('#efaultDesc').val(row.FAULTDESC);
			$('#erepairTime').val(row.REPAIRTIME);
			$('#erepairMan').val(row.REPAIRMAN);
			$('#econfirmTime').val(row.CONFIRMTIME);
			$('#eremark').val(row.REMARK);
		});
		
		function saveRR(){
			var sysName = $('#esysName').val();
			var dCode = $('#edCode').val();
			var dName = $('#edName').val();
			var faultTime = $('#efaultTime').datetimebox('getText');
			var faultDesc = $('#efaultDesc').val();
			var repairTime = $('#erepairTime').datetimebox('getText');
			var repairMan = $('#erepairMan').val();
			var confirmTime = $('#econfirmTime').datetimebox('getText');
			var remark = $('#eremark').val();
			if(sysName&&dCode&&dName&&faultTime&&faultDesc&&repairTime&&repairMan&&confirmTime&&remark){
				$.post('<%=request.getContextPath()%>/runRecord/editRR.do',{
					sysName:sysName,dCode:dCode,dName:dName,
					faultTime:faultTime,faultDesc:faultDesc,
					repairTime:repairTime,repairMan:repairMan,
					confirmTime:confirmTime,remark:remark,id:$('#id').val()
				},function(data){
					if(data.flag){
						$.messager.alert('info','保存成功！');
						$('#editRR').dialog('close');
						$('#editRR').dialog('refresh');
						$('#rrListGrid').datagrid('reload');
					}
				},'json');
			}else{
				$.messager.alert('提示','请补全维修信息！');
				return;
			}
		}
		
		function cancelRR(){
			$('#editRR').dialog('close');
			$('#editRR').dialog('refresh');
		}
	</script>
</body> 
</html>