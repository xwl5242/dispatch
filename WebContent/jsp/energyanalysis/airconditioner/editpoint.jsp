<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>修改采集点数据</title>
<jsp:include page="/jsp/header.jsp" />
</head>
<body> 
	<div>
		<table style="height: 40px">  
			<tr>
	            <td class="tbl_td_label" width="5%">采集日期：</td>  
				<td width="40%"><input id="startTime" name='startTime'
					class="easyui-datebox" style="width:30%" data-options="editable:false" />
				~<input id="endTime" name='endTime'
					class="easyui-datebox" style="width:30%" data-options="editable:false" /></td>
				<td class="tbl_td_label" width="5%">点名称：</td> 
				<td width="20%">
					<select id="pname" class="easyui-combobox" style="width:80%"></select>
				</td>
				<td colspan="12" width="25%"> 
				   <a id="searchKVList" onclick='searchKVListGrid();' class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&nbsp;
				   <a id="clearKVListSearch"  onclick='formClear();' class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a>
				</td> 
			</tr>
		</table> 
	</div> 
 	<table id='kvListGrid' style="width：100%;" ></table> 
	<script type="text/javascript"> 
		
		$(function(){
			//$('#endTime').datebox('setText',getDate(0));
			//$('#startTime').datebox('setText',getDate(6*24*60*60*1000));
			var hh = parent.$("#page").height()-55;
			var ww = parent.$("#page").width();
			
			$('#pname').combobox({
			    url:'<%=request.getContextPath()%>/ea/pnameListJson.do',
			    valueField:'ID',
			    textField:'TEXT'
			});
			
			//初始化列表组建
			$('#kvListGrid').datagrid({    
				url : '<%=request.getContextPath()%>/ea/editKV.do',
				width:'100%',
				pageNumber:1,
				pageSize:pageNum,
				fit:false,
				height:hh,   
				method:'post',  
				title:'修改采集点数值',
				idField:"ID",
				ctrlSelect:true,
				rownumbers:true,
				fitColumns: true,   
				pagination : true,
				singleSelect:true,
			    columns:[[
						{field:'T',title:'采集时间',width:'25%',align:'center',
							formatter:function(value,index,row){
								return dateFormat(value);
							}	
						},
						{field:'K',title:'采集点名称',width:'25%',align:'center'},
						{field:'V',title:'采集值',width:'25%',align:'center'},
						{field:'OPT',title:'操作',width:'20%',align:'center',
							formatter:function(value,index,row){
								return "<a style='color:blue' href='editDetail.jsp'>修改</a>";
							}	
						}
			    ]]
			});
		});
		
		/**
		 * 列表查询
		 * 根据查询条件刷新列表数据
		 */
		function searchKVGrid(){
			var startTime =$('#startTime').datebox('getValue');
			var endTime =$('#endTime').datebox('getValue');
			var pname = $('#pname').combobox('getValue');
			$('#kvListGrid').datagrid('load',{
				startTime:startTime,
				endTime:endTime,
				pname:pname
			});
		}
		
		/**
		 * 表单重置
		 * 参数：表单id 
		 */
		function formClear(){
			$('#startTime').datebox('setValue','');
			$('#endTime').datebox('setValue','');
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
	</script>
</body> 
</html>