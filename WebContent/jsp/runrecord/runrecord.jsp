<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>运行记录</title>
<jsp:include page="/jsp/header.jsp" />
</head>
<body> 
	<div>
		<form id="RCForm">
		<table style="height: 40px">  
			<tr>
	            <td class="tbl_td_label" width="6%">运行时间：</td>  
				<td width="45%"><input id="sTime" name='sTime'
					class="easyui-datetimebox" style="width:30%" data-options="editable:false" />
				~<input id="eTime" name='eTime'
					class="easyui-datetimebox" style="width:30%" data-options="editable:false" /></td>
					
				<td class="tbl_td_label"  width="6%">系统名称：</td> 
				<td  width="10%">
					<select id='dName' name='dName' class="easyui-combobox" data-options="editable:false">
						<option value=''></option>
						<option value='A1'>A座标层</option>
						<option value='A2'>A座非标层</option>
						<option value='B1'>B座标层</option>
						<option value='B2'>B座非标层</option>
					</select>
				</td>
				<td class="tbl_td_label"  width="6%">设备名称：</td> 
				<td  width="15%">
					<input id="kName" name="kName" class="easyui-textbox" />
				</td>
				<td colspan="12" width="13%"> 
				   <a id="searchLineTrend" onclick='searchRC();' class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&nbsp;
				   <a id="clearLineTrendSearch"  onclick="formClear('RCForm');" class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a>
				</td> 
	        </tr>
		</table> 
		</form>
	</div> 
 	<table id='rrListGrid' style="width：100%;" ></table> 
	<script type="text/javascript"> 
		
		$(function(){
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
			$('#eTime').datetimebox('setText',getDate(0)+" 23:59:59");
			$('#sTime').datetimebox('setText',getDate(6*24*60*60*1000)+" 00:00:00");
			$('#eTime').datetimebox('setValue',getDate(0)+" 23:59:59");
			$('#sTime').datetimebox('setValue',getDate(6*24*60*60*1000)+" 00:00:00");
			var hh = parent.parent.$("#page").height()-95;
			var ww = parent.parent.$("#page").width();
			//初始化列表组建
			$('#rrListGrid').datagrid({    
				url : '<%=request.getContextPath()%>/runRecord/query.do',
				width:'100%',
				pageNumber:1,
				pageSize:pageNum,
				fit:false,
				height:hh,   
				method:'post',  
				title:'维修记录信息列表',
				idField:"ID",
				ctrlSelect:true,
				rownumbers:true,
				fitColumns: true,   
				pagination : true,
				singleSelect:true,
			    columns:[[
						{field:'SYSNAME',title:'系统名称',width:'10%',align:'center',
							formatter: function(value,row,index){
								var at = row.AT;
								var ab = row.AB;
								var pre = '';var sub = '';
								if(at=='1'){
									sub = '标层';
								}else if(at=='2'){
									sub = '非标层';
								}
								if(ab=='A'){
									pre = 'A座';
								}else if(ab=='B'){
									pre = 'B座';
								}
								return pre+sub;
							}
						},
						{field:'DNAME',title:'设备名称',width:'10%',align:'center'},
						{field:'ST',title:'启动时间',width:'15%',align:'center',
							formatter:function(value,row,index){
								return dateFormat(value);
							}
						},
						{field:'ET',title:'停止时间',width:'15%',align:'center',
							formatter:function(value,row,index){
								return dateFormat(value);
							}
						},
 						{field:'DURATION',title:'累计运行时长',width:'15%',align:'center',
 							formatter: function(value,row,index){
 								return formatDuration((new Date(row.ET)-new Date(row.ST)));
 							}
 						}
				        
			    ]]
			});
		});
		
		/**
		 * 列表查询
		 * 根据查询条件刷新列表数据
		 */
		function searchRC(){
			var sTime =$('#sTime').datetimebox('getValue');
			var eTime =$('#eTime').datetimebox('getValue');
			var dName = $("#dName").combobox("getValue");
			var kName = $("#kName").textbox("getText");
			$('#rrListGrid').datagrid('load',{
				sTime:sTime,
				eTime:eTime,
				dName:dName,
				kName:kName
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
		function getDate(lt) {
		var longtime = new Date().getTime()-lt;
		var date = new Date(longtime);
		var seperator1 = "-";
		var month = date.getMonth() + 1;
		var strDate = date.getDate();
		if (month >= 1 && month <= 9) {
			month = "0" + month;
		}
		if (strDate >= 0 && strDate <= 9) {
			strDate = "0" + strDate;
		}
		var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
		return currentdate;
	}
	</script>
</body> 
</html>