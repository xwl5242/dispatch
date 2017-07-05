<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
<title>修改用能信息</title>
	<jsp:include page="/jsp/header.jsp" />
</head>

<body>
<div id="ynOfunitDd" >
	<form id="ynupdateunitFrom">
		<table  cellpadding="0" cellspacing="0" class="tbl_search_bg" width="100%">  
			<tr>		
				<td class="tbl_td_label" width="15%">编号：</td> 
				<td width="15%">
					<input type="hidden" id="id" name="id"/>
					<input type="hidden" id="unitid" name="unitId"/>
					<input id='unitCode' name="unitCode" class="easyui-textbox" data-options="disabled:true,disabled:true,required:true,disabled:true"/>
				</td>
				<td class="tbl_td_label" width="13%">名称：</td> 
				<td width="20%">
					<input id='unitName' name="unitName" class="easyui-textbox" data-options="disabled:true,required:true,textField:'text',validType:'maxLength[50]'"/>
				</td> 
			</tr>
			<tr>	
				<td class="tbl_td_label" width="13%">简称：</td>  
				<td  width="5%">
					<input id='shortName' name="shortName" class="easyui-textbox" data-options="disabled:true,required:true,textField:'text',validType:'maxLength[50]'"/> 
				</td> 
				<td class="tbl_td_label">简拼：</td>
				<td>
					<input id='jianPin' name='jianPin'class="easyui-textbox" data-options="disabled:true,editable:false" />
				</td> 
				
			</tr>
			<tr>  
				<td class="tbl_td_label">是否可用：</td>
				<td><input id='status' name='status' class="easyui-combobox" data-options="disabled:true,editable:false,valueField:'ID',textField:'TEXT',
					url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=PARAMETERSSTATUS'" />
				</td> 
				<td class="tbl_td_label" width="13%">性质：</td> 
				<td width="5%">
					<input id='unitType' name="unitType" class="easyui-combobox" data-options="disabled:true,required:true,editable:false,valueField:'ID',textField:'TEXT',url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=OWNERTYPE&showNumber=1,2,3,4,5,6,7,8,9 '"/>
				</td> 
			</tr>
			
			<tr>
				
				<td class="tbl_td_label">地区：</td>
				<td>
					<input id="areaIdSer" name="areaId"  type="hidden" />
					<input id="areaNameSer" class="easyui-textbox" name="areaname"   data-options="buttonText:'选择',onClickButton:function(){showAreaDialog()}"/> 
				</td>
				<td class="tbl_td_label" width="13%">地址：</td>  
				<td>
					<input id='address' name='address'   class="easyui-textbox" data-options="multiline:true,editable:true,validType:'length[0,200]'" style="height: 40px;"/> 
				</td>
			</tr> 
			<tr>
				<td class="tbl_td_label" width="13%">排序：</td>  
				<td width="5%">
					<input id="seqUpdate" class="easyui-numberbox" name="seq"   data-options="required:true,validType:'length[0,4]'" />  
				</td>
				<td class="tbl_td_label" width="13%">备注：</td>  
				<td width="5%" colspan="5">
					<input id='remarks' name="remarks" class="easyui-textbox" data-options="validType:'maxLength[500]'"/> 
				</td>
			</tr>
			<tr>
				<td class="tbl_td_label" width="13%">供热类型：</td>  
				<td>
					<input id='heatingType' name='heatingType'   class="easyui-combobox" data-options="valueField:'ID',textField:'TEXT',url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=HEATINGTYPE'"/> 
				</td>
				<td class="tbl_td_label">集团性质：</td>
				<td>
				<input id="groupTypeUpda" class="easyui-combobox" name="groupType" data-options="disabled:true,editable:false,valueField:'ID',required:true,textField:'TEXT',  
							url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=GROUPTYPE'" />
				</td>
			</tr>
		</table> 
	</form>
	<input type="hidden" id="count" value="0"/>
	<iframe src="<%= request.getContextPath()%>/jsp/sys/right/tjseq.jsp" id="seqFrame" name="seqFrame" frameborder="no" border="0" style="margin:0 auto;width:100%;display:none;"></iframe>
</div>
	
 
	<div align="center">
		<a id="btn"  onclick='updateld();' class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>&nbsp;
		<a id="btn"  onclick='updateUnitBtnClocse();' class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">关闭</a>&nbsp;
	</div>
	<div id="areaDd"></div>
	<script type="text/javascript">
	 function showSeq(){
		 	var i = $("#count").val();
			if(i>0){
				return false;
			}
			$("#count").val(1);
			$("#seqFrame").show();
			seqFrame.window.initData("<%= request.getContextPath()%>/unit/getUnitInfoById.do?cmd=getUnitInfoById",$('#unitid').val()); 
		}
	
	 function setValue(seq){
		 $("#seqUpdate").numberbox("setValue",seq);
	 }
	
	function showAreaDialog(){
		$('#areaDd').dialog('refresh','<%= request.getContextPath()%>/jsp/ieu/unit/areaChoeseTreeUpdate.jsp'); 
	    $('#areaDd').dialog('open');
	}
	$('#areaDd').dialog({    
		title:'添加地区信息',
		width:600,
	    height:'auto', 
	    top: winTop,
	    left: winLeft,
	    modal:true, 
	    minimizable:false,
	    maximizable:false,
	    iconCls:'icon-save',
	    closed:true, 
	    href:'<%= request.getContextPath()%>/jsp/ieu/unit/areaChoeseTreeUpdate.jsp'  
	});
	function mybbUpdate(id,name){
		$("#areaIdSer").val(id);
		$("#areaNameSer").val(name);
	}
	
		//关闭按钮
		function updateUnitBtnClocse(){ 
			$.messager.confirm('确认','您确认想要关闭吗？',function(r){
				if(r){
					$('#updateUnit').dialog("close");
					}
				});
		} 
		//修改
		function updateld(){
			if(!$("#ynupdateunitFrom").form('validate')){
				return false;
			} 
			var count = $("#count").val();
		 	if(count=='1'){
			 	seqFrame.window.save('<%= request.getContextPath()%>/right/updateRightSeq.do?cmd=updateRightSeq','ECC_IEU_UNITINFO',$("#id").val()); 
			 	seqFrame.window.save('<%= request.getContextPath()%>/right/updateRightSeq.do?cmd=updateRightSeq','SYS_ORG',$("#id").val()); 
		 	}
		 	 $.messager.progress();
			$.ajax({ 
	            type: "post",  
	            url: "<%= request.getContextPath()%>/unit/updateYN.do?cmd=updateYN", 
	            data:$('#ynupdateunitFrom').serialize(),//form表单序列化
	            dataType: "json", 
	            success: function(data){ 
	            	$.messager.progress('close');//关闭进度条
	            	if(data.flag){ 
	            		searchUnitListGrid("myUnittree"); 
					     $.messager.alert("提示",data.messager,'info',$('#updateUnit').dialog("close")); 
			    	}else{
					     $.messager.alert("警告",data.messager,'warning'); 
			    	} 
	            },
	            error:function(){
	            	  $.messager.progress('close');//关闭进度条
	            	  $.messager.alert("警告","系统异常！",'warning'); 
	            }
	        });
			
		}
		
		
	var a1 = $('#aa').val(); 
	$.ajax({ 
        type: "post", 
        url: "<%= request.getContextPath()%>/unit/findUnitInfoById.do?cmd=findUnitInfoById",
        data: {"id":a1},//form表单序列化
        dataType: "json", 
        success: function(data){
        	$('#ynupdateunitFrom').form('load',data);
        	$("#id").val(data.id);
        },
        error:function(){ 
        	$.messager.alert("警告","系统异常！",'warning'); 
        }
    }); 
	</script>
	<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-easyui-1.4.1/custom/myvalidate.js"></script>
</body>
	
</html>