<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>添加用能</title>
	<jsp:include page="/jsp/header.jsp" />
</head>
<body>
		<div  id="unitDivId" data-options="title:'用能单位详细'" style="height:30px;"> 
			<form id="unitform" >       
					<table   id="unitTblId" cellpadding="0" cellspacing="0" height="30px;" class="tbl_search_bg" width="100%">
						<tr> 
							<td class="tbl_td_label">性质：</td>
							<td>
								<input id="unitType1" class="easyui-combobox" name="unitType"  data-options="required:true,editable:false,valueField:'ID',textField:'TEXT',
								url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=OWNERTYPE&showNumber=4,5,6,7,8,12' ,onSelect:function(rec){
									var a = rec.ID;
									if(a=='12'){
										$('#yndws1').attr('src', '<%=request.getContextPath()%>/jsp/ieu/unit/addJZWH.jsp');
									}
									if(a=='4'){
										$('#yndws1').attr('src', '<%=request.getContextPath()%>/jsp/ieu/unit/addHeat1.jsp');
									}
									if(a=='5'){
										$('#yndws1').attr('src', '<%=request.getContextPath()%>/jsp/ieu/unit/addGW.jsp');
									}
									if(a=='6'){
										$('#yndws1').attr('src', '<%=request.getContextPath()%>/jsp/ieu/unit/addSecGW.jsp');
									}
									if(a=='7'){
										$('#yndws1').attr('src', '<%=request.getContextPath()%>/jsp/ieu/unit/addLD.jsp');
									}
									if(a=='8'){
										$('#yndws1').attr('src', '<%=request.getContextPath()%>/jsp/ieu/unit/addZL.jsp');
									}
								}" /> 
							</td>  
							<td class="tbl_td_label">是否可用：</td>
							<td><input id='status' name='status' value='可用' class="easyui-combobox" data-options="disabled:true,required:true,editable:false,valueField:'ID',textField:'TEXT',
								url:'<%= request.getContextPath()%>/jsp/sys/datadic/dataDicUtil.jsp?typecode=PARAMETERSSTATUS'" />
							</td>    
						 </tr>
					</table>
					
			</form>
			  
			</div>
			 <div id="yndw1" style="display:none;" ></div>
			   
			     <iframe id="yndws1" name="yndws1"   frameborder="no" border="0"  width="100%" height="220px;">
			     </iframe>
			  
			
			<div id="danweiDd"></div>
			<div id="areaDd"></div>
			<div id="detailDd"></div>
	<script type="text/javascript">

	
	function myaa(id,name){
		 window.top.ifTb(id,name);  
	}
	
	function addGWDgClocse(){ 
		$('#addUnit').dialog("close");
	} 
	
	function showDetalDialog(){
		$('#detailDd').dialog('refresh','<%= request.getContextPath()%>/jsp/cost/yndanweiList.jsp'); 
	    $('#detailDd').dialog('open');
	}
	$('#detailDd').dialog({    
		title:'选择热源',
		width:600,
	    height:'auto', 
	    top: winTop,
	    left: winLeft,
	    modal:true, 
	    minimizable:false,
	    maximizable:false,
	    iconCls:'icon-save',
	    closed:true, 
	    href:'<%= request.getContextPath()%>/jsp/cost/yndanweiList.jsp'  
	});
	
	function showVillageDialog(){
		$('#danweiDd').dialog('refresh','<%= request.getContextPath()%>/jsp/ieu/unit/danweiList.jsp'); 
	    $('#danweiDd').dialog('open');
	}
	$('#danweiDd').dialog({    
		title:'选择小区',
		width:600,
	    height:'auto', 
	    top: winTop,
	    left: winLeft,
	    modal:true, 
	    minimizable:false,
	    maximizable:false,
	    iconCls:'icon-save',
	    closed:true, 
	    href:'<%= request.getContextPath()%>/jsp/ieu/unit/danweiList.jsp'  
	});
	
	function showAreaDialog(){
		$('#areaDd').dialog('refresh','<%= request.getContextPath()%>/jsp/ieu/unit/areaChoeseTree.jsp'); 
	    $('#areaDd').dialog('open');
	}
	$('#areaDd').dialog({    
		title:'选择地区',
		width:380,
	    height:400, 
	    top: winTop,
	    left: winLeft,
	    modal:true, 
	    minimizable:false,
	    maximizable:false,
	    iconCls:'icon-save',
	    closed:true, 
	    href:'<%= request.getContextPath()%>/jsp/ieu/unit/areaChoeseTree.jsp'  
	});
	
	function showMsg(type,data,msgtype,closed){
		$.messager.alert(type,data,msgtype,closed); 
	}
	</script>
</body>


</html>