<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>报警统计</title>
<jsp:include page="/jsp/header.jsp" />
</head>
<body> 
	<div id="warnSearchPanel"  style="width:100%;"  >
		<form id="warnFromSearc">
			<table  cellpadding="0" cellspacing="0" class="tbl_search_bg" width="100%">  
				<tr>	
					<td class="tbl_td_label" width="15%">报警开始时间：</td>
					<td width="40%"><input id="sTime" name='sTime'
						class="easyui-datetimebox" style="width:30%" data-options="editable:false" />
					~<input id="eTime" name='eTime'
						class="easyui-datetimebox" style="width:30%" data-options="editable:false" /></td>
						
					<td class="tbl_td_label"  width="5%">点名称：</td> 
					<td  width="20%">
						<input id='pointName' name="pointName" class="easyui-textbox" />
					</td>
					
					<td class="tbl_td_label"  width="5%">报警状态：</td> 
					<td  width="20%">
						<select id="status" name="status" class="easyui-combobox" style="width: 133px;">
							<option value=""></option>
							<option value="2">已结束</option>
							<option value="1">报警中</option>
						</select>
					</td>
				</tr> 
				<tr style="text-align: right;">
					<td colspan="12"> 
						<a id="btn" href='javascript:searchWarnListGrid()' class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&nbsp;
						<a id="btn"  href='javascript:formClear("warnFromSearc");' class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a>  &nbsp;
					</td> 
				</tr>
			</table> 
		</form>
	</div> 
 	<table id='warnListGrid' style="width：100%;" ></table> 
	<script type="text/javascript"> 
		var gridHeight = parent.$("#page").height()-110;
		$(function(){
			var ww = parent.$("#page").width()-10;
			/**
			 * 初始化查询Panel
			 */
			$('#warnSearchPanel').panel({    
				  collapsible:true,
				  width:ww,
				  title:"查询"
			}); 
			
			$('#sTime').datetimebox({
				onSelect:function(date){
					var end = new Date($('#eTime').datetimebox('getValue')).getTime();
					if(end!=null&&end!=''){
						if(end<new Date(date).getTime()){
							$.messager.alert('提醒', '开始时间不能大于结束时间,请重新选择!');  
							$('#sTime').datetimebox('setValue','');
							return ;
						}
					}
				}
			});
			
			$('#eTime').datetimebox({
				onSelect:function(date){
					if($('#sTime').datetimebox('getValue')==null||$('#sTime').datetimebox('getValue')==''){
						$.messager.alert('提醒', '请先选择开始时间!'); 
						$('#eTime').datetimebox('setValue','');
						return ;
					}
					var start = new Date($('#sTime').datetimebox('getValue')).getTime();
					if(start!=null&&start!=''){
						if(start>new Date(date).getTime()){
							$.messager.alert('提醒', '结束时间不能小于开始时间,请重新选择!');  
							$('#eTime').datetimebox('setValue','');
							return ;
						}
					}
				}
			});
		
			/**
			 * 初始化列表组建
			 */
			$('#warnListGrid').datagrid({    
				url : '<%= request.getContextPath()%>/warning/listWarnData.do?cmd=listWarnData',
				width:ww,
				pageNumber:1,
				pageSize:pageNum,
				fit:false,
				height:gridHeight,   
				method:'post',  
				title:'报警记录信息列表',
				idField:"ALARMID",
				ctrlSelect:true,
				rownumbers:true,
				fitColumns: true,   
				pagination : true,
				singleSelect:true,
			    columns:[[
			              //点名，点描述，报警值，开始时间，结束时间，报警时长
						{field:'AlarmId',checkbox:true}, 
						{field:'TagName',title:'点名称',width:'20%',align:'center'},
						{field:'TagDesc',title:'点描述',width:'25%',align:'center'},
						{field:'AlarmValue',title:'报警值',width:'10%',align:'center'},
						{field:'AlarmStartTime',title:'开始时间',width:'15%',align:'center'},
						{field:'AlarmEndTime',title:'结束时间',width:'15%',align:'center',
							formatter: function(value,row,index){
								if(row.STATUS=='1'){
									return "";
								}else{
									return value;
								}
							}
						},
						{field:'DURATION',title:'报警时长',width:'10%',align:'center',
							formatter: function(value,row,index){
								var st = row.AlarmStartTime;
								var et = row.AlarmEndTime;
								
								st = st.replace(/-/g,"/");
								et = et.replace(/-/g,"/");
								st = st.substring(0,st.length-4);
								et = et.substring(0,et.length-4);
								
								var s = new Date(st).getTime();
								var e = new Date(et).getTime();

								if(row.STATUS=='1'){
									return "";
								}else{
				        			return formatDuration(e-s);
								}
				        	}
						}
			    ]],
			    onHeaderContextMenu: function(e, field){
					 e.preventDefault();
					 if (!cmenu){
					      createColumnMenu('warnListGrid');
					 }
					 cmenu.menu('show', {
					      left:e.pageX,
					      top:e.pageY
					 });
			   	}
			});
		});
		
		/**
		 * 列表查询
		 * 根据查询条件刷新列表数据
		 */
		function searchWarnListGrid(){
			var sTime =$('#sTime').datetimebox('getValue');
			var eTime =$('#eTime').datetimebox('getValue');
			var pointName = $("#pointName").textbox("getText");
			var status = $("#status").combobox("getValue");
			$('#warnListGrid').datagrid('load',{
				sTime:sTime,
				eTime:eTime,
				pointName:pointName,
				status:status
			});
		}
		
		/**
		 * 表单重置
		 * 参数：表单id 
		 */
		function formClear(formId){
			$('#'+formId).form('clear');
		}
		
		function dateFormat(date){
			if(date==null || date=="" || date=="null"){
				return "";
			}else{
				var date = new Date(date);
				var y = date.getFullYear();
				var m = parseInt(date.getMonth()+1)<10?"0"+parseInt(date.getMonth()+1):parseInt(date.getMonth()+1);
				var d = date.getDate()<10?"0"+date.getDate():date.getDate();
				var h = date.getHours()<10?"0"+date.getHours():date.getHours();
				var min = date.getMinutes()<10?"0"+date.getMinutes():date.getMinutes();
				var s = date.getSeconds()<10?"0"+date.getSeconds():date.getSeconds();
				return y+"-"+m+"-"+d+" "+h+":"+min+":"+s;
			}
		}
		
		//时间差
		function formatDuration(time){
			time = parseInt(time/1000);
			var second = time%60;
			var min = parseInt(time/60)%60;
			var hour = parseInt(time/3600);
			if(second<10){
				second = "0"+second;
			}
			if(min<10){
				min = "0"+min;
			}
			if(hour<10){
				hour = "0"+hour;
			}
			return hour+":"+min+":"+second;
		}

	</script>
</body> 
</html>