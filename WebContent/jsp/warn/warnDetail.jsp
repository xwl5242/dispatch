<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>报警明细</title>
<jsp:include page="/jsp/header.jsp" />
</head>
<body> 
	<div id="warnSearchPanel"  style="width:100%;"  >
		<form id="warnFromSearc">
			<table  cellpadding="0" cellspacing="0" class="tbl_search_bg" width="100%">  
				<tr>	
					<td class="tbl_td_label" width="5%">报警时间：</td>
					<td width="40%"><input id="sTime" name='sTime'
						class="easyui-datetimebox" style="width:30%" data-options="editable:false" />
					~<input id="eTime" name='eTime'
						class="easyui-datetimebox" style="width:30%" data-options="editable:false" /></td>
					<td class="tbl_td_label"  width="5%">系统名称：</td> 
					<td  width="20%">
						<input id='unitId' name="unitId" class="easyui-combobox" data-options="editable:false"/>
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
		var unitid='<%=request.getParameter("unitid")%>';
		var gridHeight = parent.$("#page").height()-83;
		$(function(){
			
			//从运行监控子级菜单具体站进入报警记录页面是 unitid为具体的站的ID；从运行监控子级菜单报警记录进入时unitid为null
			if(unitid!=null&&unitid!="null"){
				$('#unitId').combobox({disabled: true});//从具体站进入报警设置页面时，站不能再被选择，需禁用
			}else{
				$('#unitId').combobox({disabled: false});
			}
			
			$('#unitId').combobox({ 
				 url:'<%= request.getContextPath()%>/warn/queryUnit.do?cmd=queryUnit',
				 valueField:'ID',
				 textField:'UNITNAME',
				 onLoadSuccess:function (){
					//从运行监控子级菜单具体站进入报警记录页面是 unitid为具体的站的ID；从运行监控子级菜单报警记录进入时unitid为null
					 if(unitid!=null&&unitid!="null"){
						$('#unitId').combobox('setValues', unitid);
						searchWarnListGrid();
					 }
				 }
			 }); 
			
			var ww = parent.$("#page").width();
			/**
			 * 初始化查询Panel
			 */
			$('#warnSearchPanel').panel({    
				  collapsible:true,
				  width:ww,
				  title:"查询"
			}); 
		
			/**
			 * 初始化列表组建
			 */
			$('#warnListGrid').datagrid({    
				url : '<%= request.getContextPath()%>/warning/listWarnData.do?cmd=listWarnData',
				width:'100%',
				pageNumber:1,
				pageSize:pageNum,
				fit:false,
				height:gridHeight,   
				method:'post',  
				title:'报警明细列表',
				idField:"ID",
				ctrlSelect:true,
				rownumbers:true,
				fitColumns: true,   
				pagination : true,
				singleSelect:true,
			    columns:[[
						{field:'ID',checkbox:true}, 
						{field:'UNITNAME',title:'系统名称',width:'12%',align:'center'},
						{field:'POINTNAME',title:'点名（力控）',width:'12%',align:'center'},
						{field:'POINTDESC',title:'点描述（力控）',width:'22%',align:'center'},
						{field:'STARTTIME',title:'开始时间',width:'18%',align:'center'},
						{field:'ENDTIME',title:'结束时间',width:'18%',align:'center'},
						{field:'WARNDURATION',title:'报警时长',width:'6%',align:'center'},
						{field:'WARNSETVALUE',title:'报警设定值',width:'6%',align:'center'},
						{field:'COLLVALUE',title:'采集值',width:'6%',align:'center'}
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
			var unitName = $("#unitId").combobox("getText");
			$('#warnListGrid').datagrid('load',{
				unitId:unitid,
				sTime:sTime,
				eTime:eTime,
				unitName:unitName
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