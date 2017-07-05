<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>运行记录</title>
<jsp:include page="/jsp/header.jsp" />
</head>
<body> 
	<div>
		<table style="height: 40px">  
			<tr>
	            <td class="tbl_td_label" width="10%">运行时间：</td>  
				<td width="40%"><input id="sTime" name='sTime'
					class="easyui-datetimebox" style="width:30%" data-options="editable:false" />
				~<input id="eTime" name='eTime'
					class="easyui-datetimebox" style="width:30%" data-options="editable:false" /></td>
					
				<td class="tbl_td_label"  width="5%">设备名称：</td> 
				<td  width="20%">
					<input id='dId' name='dId' class="easyui-combobox" data-options="editable:false"/>
				</td>
				<td colspan="12" width="25%"> 
				   <a id="searchLineTrend" onclick='searchLineTrend();' class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&nbsp;
				   <a id="clearLineTrendSearch"  onclick='formClear();' class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a>
				</td> 
	        </tr>
		</table> 
	</div> 
 	<table id='rrListGrid' style="width：100%;" ></table> 
	<script type="text/javascript"> 
		
		$(function(){
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
						{field:'ID',checkbox:true}, 
						{field:'DID',hidden:true}, 
						{field:'SYSNAME',title:'系统名称',width:'10%',align:'center',
							formatter: function(value,row,index){
								var str = row.REMARK;
								var i = str.lastIndexOf("\\");
								str = str.substring(0,i);
								str = str.substring(str.lastIndexOf("\\")+1);
								if(str=="abc"){
									str = "A座标层"+"\\"+row.DNAME.toUpperCase();
								}else if(str=="afbc"){
									str = "A座非标层"+"\\"+row.DNAME.toUpperCase();
								}else if(str=="bbc"){
									str = "B座标层"+"\\"+row.DNAME.toUpperCase();
								}else if(str=="bfbc"){
									str = "B座非标层"+"\\"+row.DNAME.toUpperCase();
								}
								return str;
							}
						},
						{field:'DNAME',title:'设备名称',width:'10%',align:'center'},
						{field:'STARTTIME',title:'启动时间',width:'15%',align:'center',
							formatter: function(value,row,index){
								if (row.YXSTATUS=='1'){
									return dateFormat(row.TIME);
								} 
							}
						},
						{field:'STOPTIME',title:'停止时间',width:'15%',align:'center',
							formatter: function(value,row,index){
								if (row.YXSTATUS=='0'){
									return dateFormat(row.TIME);
								}
							}
						},
// 						{field:'DURATION',title:'启动时长',width:'15%',align:'center',
// 							formatter: function(value,row,index){
// 								if (row.YXSTATUS=='1'){
// 									return dateFormat(row.TIME);
// 								} 
// 							}
// 						},
				        {field:'REMARK',title:'备注',width:'10%',align:'center'}
			    ]]
			});
		});
		
		/**
		 * 列表查询
		 * 根据查询条件刷新列表数据
		 */
		function searchWarnListGrid(){
			var sTime =$('#sTime').datetimebox('getValue');
			var eTime =$('#eTime').datetimebox('getValue');
			var dId = $("#dId").combobox("getText");
			$('#rrListGrid').datagrid('load',{
				unitId:unitid,
				sTime:sTime,
				eTime:eTime,
				unitName:unitName,
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