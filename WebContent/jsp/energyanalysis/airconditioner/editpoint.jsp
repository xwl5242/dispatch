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
<!--  	<div id="editDialog"></div> -->
	<script type="text/javascript"> 
		
		$(function(){
			
			//设置datagrid宽高
			var hh = parent.$("#page").height()-55;
			var ww = parent.$("#page").width();
			
			//pname，采集点名称下拉框初始化
			$('#pname').combobox({
			    url:'<%=request.getContextPath()%>/ea/pnameListJson.do',
			    valueField:'ID',
			    textField:'TEXT'
			});
			var editIndex = undefined;
			function endEditing() {//该方法用于关闭上一个焦点的editing状态  
				if (editIndex == undefined) {  
					return true  
				}  
				if ($('#kvListGrid').datagrid('validateRow', editIndex)) {  
					$('#kvListGrid').datagrid('endEdit', editIndex);  
					editIndex = undefined;  
					return true;  
				} else {  
					return false;  
				}  
			}  
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
						{field:'V',title:'采集值',width:'25%',align:'center',editor:'text'}
// 						{field:'OPT',title:'操作',width:'20%',align:'center',
// 							formatter:function(value,index,row){
// 								return "<a style='color:blue' onclick='toEditDialog(0)'>修改</a>";
// 							}	
// 						}
			    ]],
			    onClickCell:function(index,field,value){
					debugger;
			    	if (endEditing()) {  
			    		 if(field=="V"){ // 判断是否是field为six列，如果不是固定某列的话，不需要判断   
			    		    $('#kvListGrid').datagrid('beginEdit', index);    
			    		        var ed = $('#kvListGrid').datagrid('getEditor', {index:index,field:field});    
			    		        $(ed.target).focus();    
			    		    }           
			    		    editIndex = index;  
			    		    //alert("点击触发editIndex:"+editIndex);  
			    		 }  
			    		else if(editIndex != undefined){//如果不相等，说明已经打开编辑器了，需要关闭编辑器  
			    		    $('#kvListGrid').datagrid('endEdit', editIndex);    
			    		    editIndex = undefined;  
			    		    //alert("关闭编辑器");  
			    		}  
			    },
			    onAfterEdit:function(index, row, changes){ // 关闭编辑器后触发  
					debugger;
			        var updated = $('#kvListGrid').datagrid('getChanges', 'updated');  // updated 是一个getChanges的属性，可以查看api  
			        //alert("onAfterEdit。。"+updated.length);  
			        if (updated.length < 1) {  // 如果编辑器中的数据已经修改，则length为1，否则为0，判断是否已经修改数据  
			            editRow = undefined;    
			            $('#kvListGrid').datagrid('unselectAll');    
			            return;    
			        } else {    
			            // 传值   
			            //alert("提交数据");  
			            submitForm(index, row, changes);  //这里ajax提交数据的方法  
			        }  
			    }
			});
			
		});
		
		//修改数据
		function submitForm(index, row, changes) {   
			debugger;
		    var resultId=row.V;  // 当前行中修改的数据值  
		    if(resultId==""){    
		        $.messager.alert('提醒', '没有录入数据！');    
		        $('#kvListGrid').datagrid('reload');    
		        return;    
		    }    
		    var r =/^(-?\d+)(\.\d+)?$/;//判断输入的是正整数    
		    if(!r.test(resultId)){    
		        $.messager.alert('提醒', '请输入数字！');    
		        return;    
		    }    
		    $.ajax({    
		            type : "post",    
		            async : false,    
		            url : "<%=request.getContextPath()%>/ea/edit.do",    
		            data : {    
		                "V" : resultId,
		                "T" : dateFormat(row.T),
		                "K" : row.K
		            },    
		            success : function(data) {    
		                if(data.flag==true){    
		                    //alert("保存成功");    
		                    $('#kvListGrid').datagrid('reload');    
		                }    
		            }    
		        })  
		}    
		
		/**
		 * 列表查询
		 * 根据查询条件刷新列表数据
		 */
		function searchKVListGrid(){
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
		
		/**
		* 日期格式化
		*/
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