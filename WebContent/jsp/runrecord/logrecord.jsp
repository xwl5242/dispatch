<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>日志查询</title>
<jsp:include page="/jsp/header.jsp" />
</head>
<body> 
	<div>
		<table style="height: 40px">  
			<tr>
	            <td class="tbl_td_label" width="5%">操作时间：</td>  
				<td width="40%"><input id="startEventTime" name='startEventTime'
					class="easyui-datetimebox" style="width:30%" data-options="editable:false" />
				~<input id="endEventTime" name='endEventTime'
					class="easyui-datetimebox" style="width:30%" data-options="editable:false" /></td>
				<td class="tbl_td_label" width="5%">日志类型：</td> 
				<td width="20%">
					<select style="width: 80%;" id="eventType" class="easyui-combobox">
						<option value="">全部日志</option>
						<option value="0">系统日志</option>
						<option value="1">操作日志</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="tbl_td_label"  width="5%">操作人：</td> 
				<td  width="20%">
					<input id='logOnUser' style="width:62%" class="easyui-textbox" name='logOnUser' />
				</td>
				<td class="tbl_td_label"  width="5%">操作PC：</td> 
				<td  width="20%">
					<input id='pcName' style="width:80%" class="easyui-textbox" name='pcName' />
				</td>
				<td colspan="12" width="25%"> 
				   <a id="searchLogList" onclick='searchLogListGrid();' class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&nbsp;
				   <a id="clearLogListSearch"  onclick='formClear();' class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a>
				</td> 
	        </tr>
		</table> 
	</div> 
 	<table id='rrListGrid' style="width：100%;" ></table> 
	<script type="text/javascript"> 
		
		$(function(){
			var hh = parent.parent.$("#page").height()-115;
			var ww = parent.parent.$("#page").width();
			//初始化列表组建
			$('#rrListGrid').datagrid({    
				url : '<%=request.getContextPath()%>/runRecord/queryLogList.do',
				width:'100%',
				pageNumber:1,
				pageSize:pageNum,
				fit:false,
				height:hh,   
				method:'post',  
				title:'日志查询信息列表',
				idField:"ID",
				ctrlSelect:true,
				rownumbers:true,
				fitColumns: true,   
				pagination : true,
				singleSelect:true,
			    columns:[[
						{field:'EVENTTIME',title:'日志时间',width:'15%',align:'center'},
						{field:'EVENTCONTENT',title:'日志内容',width:'15%',align:'center'},
						{field:'EVENTTYPE',title:'日志类型',width:'15%',align:'center',
							formatter:function(value,index,row){
								if(value=="0"){
									return "系统日志";
								}else if(value="1"){
									return "操作日志";
								}
							}
						},
						{field:'LOGONUSER',title:'操作人',width:'15%',align:'center'},
						{field:'PCNAME',title:'操作PC',width:'15%',align:'center'}
			    ]]
			});
		});
		
		/**
		 * 列表查询
		 * 根据查询条件刷新列表数据
		 */
		function searchLogListGrid(){
			var startEventTime =$('#startEventTime').datetimebox('getValue');
			var endEventTime =$('#endEventTime').datetimebox('getValue');
			var eventType = $('#eventType').combobox('getValue');
			var logOnUser = $('#logOnUser').textbox('getText');
			var pcName = $('#pcName').textbox('getText');
			$('#rrListGrid').datagrid('load',{
				startEventTime:startEventTime,
				endEventTime:endEventTime,
				eventType:eventType,
				logOnUser:logOnUser,
				pcName:pcName
			});
		}
		
		/**
		 * 表单重置
		 * 参数：表单id 
		 */
		function formClear(){
			$('#startEventTime').datetimebox('setValue','');
			$('#endEventTime').datetimebox('setValue','');
			$('#eventType').combobox('setValue','');
			$('#logOnUser').textbox('setText','');
			$('#pcName').textbox('setText','');
		}
		
	</script>
</body> 
</html>