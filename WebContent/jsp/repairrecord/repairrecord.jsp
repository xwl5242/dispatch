<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>维修记录</title>
<jsp:include page="/jsp/header.jsp" />
</head>
<body> 
	<div id="rrSearchPanel"  style="width:100%;"  >
		<form id="rrFromSearc">
			<table  cellpadding="0" cellspacing="0" class="tbl_search_bg" width="100%">  
				<tr>	
					<td class="tbl_td_label" width="5%">维修时间：</td>
					<td width="40%"><input id="sTime" name='sTime'
						class="easyui-datetimebox" style="width:30%" data-options="editable:false" />
					~<input id="eTime" name='eTime'
						class="easyui-datetimebox" style="width:30%" data-options="editable:false" /></td>
						
					<td class="tbl_td_label"  width="5%">设备名称：</td> 
					<td  width="20%">
						<input id='dNamesear' name='dNamesear' class="easyui-textbox"/>
					</td>
				</tr> 
				<tr style="text-align: right;">
					<td colspan="12"> 
						<a id="btn" href='javascript:searchRRListGrid()' class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&nbsp;
						<a id="btn"  href='javascript:formClear("rrFromSearc");' class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a>  &nbsp;
					</td> 
				</tr>
			</table> 
		</form>
	</div> 
 	<table id='rrListGrid' style="width：100%;" ></table> 
 	<div id="addRR"></div>
 	<div id="editRR"></div>
	<script type="text/javascript"> 
	
		var gridHeight = parent.$("#page").height()-83;
		$(function(){
			var ww = parent.$("#page").width();
			
			//初始化查询Panel
			$('#rrSearchPanel').panel({    
				  collapsible:true,
				  width:ww,
				  title:"查询"
			}); 
			//初始化列表组建
			$('#rrListGrid').datagrid({    
				url : '<%=request.getContextPath()%>/runRecord/repairRecords.do',
				width:'100%',
				pageNumber:1,
				pageSize:pageNum,
				fit:false,
				height:gridHeight,   
				method:'post',  
				title:'维修记录信息列表',
				idField:"ID",
				ctrlSelect:true,
				rownumbers:true,
				fitColumns: true,   
				pagination : true,
				singleSelect:true,
				toolbar: [{
					text:'新增',
					iconCls:'icon-add',
					handler:function(){
						addRR('addRR','新增','addrepairrecord.jsp');
					}
				},'-',{
					text:'删除',
					iconCls:'icon-remove',
					handler:function(){
						removeRR();
					}
				}],
			    columns:[[
						{field:'ID',checkbox:true}, 
						{field:'SYSNAME',title:'系统名称',width:'10%',align:'center'},
						{field:'DCODE',title:'设备编号',width:'10%',align:'center'},
						{field:'DNAME',title:'设备名称',width:'10%',align:'center'},
						{field:'FAULTTIME',title:'故障时间',width:'15%',align:'center'},
						{field:'FAULTDESC',title:'故障描述',width:'15%',align:'center'},
						{field:'REPAIRTIME',title:'维修时间',width:'15%',align:'center'},
						{field:'REPAIRMAN',title:'维修人',width:'10%',align:'center'},
						{field:'CONFIRMTIME',title:'确认时间',width:'15%',align:'center'},
				        {field:'REMARK',title:'备注',width:'10%',align:'center'},
				        {field:'opt',title:'操作',width:'15%',align:'center',
				        	formatter:function(value,row,index){
				        		var rowJson = JSON.stringify(row);
				        		window.rowJson = rowJson;
				        		return "<a style='color:blue' onclick='editRR()'>修改</a>&nbsp;&nbsp;<a style='color:blue' onclick='removeRR(\""+row.ID+"\")'>删除</a>";
				        	}	
				        }
			    ]]
			});
		});
		
		/**
		 * 列表查询
		 * 根据查询条件刷新列表数据
		 */
		function searchRRListGrid(){
			var sTime =$('#sTime').datetimebox('getText');
			var eTime =$('#eTime').datetimebox('getText');
			var dName = $("#dNamesear").val();
			$('#rrListGrid').datagrid('load',{
				sTime:sTime,
				eTime:eTime,
				dName:dName
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

		function addRR(id,title,url){
			$('#'+id).dialog({
	    	    title: title,
	    	    width: 600,
	    	    height: 300,
	    	    closed: false,
	    	    cache: false,
	    	    href: url,
	    	    modal: true
	    	}).dialog('open');
		}
		function editRR(){
			var url = 'editrepairrecord.jsp?rowRR='+window.rowJson;
			addRR('editRR','修改',url);
		}
		function removeRR(id){
			var ids = [];
			if(id){
				ids.push(id);
			}else{
				var rows = $('#rrListGrid').datagrid('getSelections');
				if(rows.length==0){
					$.messager.alert('提示','请选择一条记录！');
					return;
				}
				for(var i=0;i<rows.length;i++){
					ids.push(rows[i].ID);
				}
			}
			$.messager.confirm('操作确认','是否确认操作删除？',function(r){
			    if (r){
			    	$.post('<%=request.getContextPath()%>/runRecord/removeRR.do',{
			    		ids:ids.join()
			    	},function(data){
			    		if(data.flag){
			    			$('#rrListGrid').datagrid('reload');
			    			$.messager.alert('提示','删除成功！');
			    		}
			    	},'json');
			    }
			});
		}
	</script>
</body> 
</html>