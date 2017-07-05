<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/runqianReport4.tld" prefix="report" %>
<%
String params = "";
StringBuffer sb = new StringBuffer();
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
String startTime = request.getParameter("startTime");
if(null!=startTime&&"".equals(startTime)){
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(new Date());
	startTime = sf.format(calendar.getTime()).toString();
}
String endTime = startTime;
endTime+=" 23:59:59";
sb.append("startDate="+startTime+";");
sb.append("endDate="+endTime+";");
params = sb.toString();
%>
<html>
<head>
<title></title>
<jsp:include page="/jsp/header.jsp" />
</head>
<body> 
<FORM name="form1" id="form1" action="#">
<table id='a' cellpadding="0" cellspacing="0" width="100%" align="left" >  
		<tr align="left" >
	        <td  width="9.5%" align="right">日期：</td> 
			<td  width="5%" align="left">
				<input id='startTimeBox' style="width:155px" name="startTimeBox" class="easyui-datebox" />
				<input type="hidden" id='startTime' name="startTime" value="<%=startTime%>" style="width:100%" />
			</td>   
			<td class="tbl_td_input" width="67%" align="left">
	   			<a id="searchBtn" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
			</td>
		</tr>
</table>
</FORM>
<div style="height: 17px"></div>
<div id="rqDivId" style="margin-left:130px">
<report:html name="report1" reportFileName="gkrecord.raq" needPageMark=""
        params="<%=params %>" submit=""  width="-1"  height="-1"  needSaveAsExcel="yes" funcBarLocation="bottom"/>
</div>
</body>	 	
<script type="text/javascript"> 
$(document).ready(function(){
	$("#startTimeBox").datebox("setValue","<%=startTime%>");
	$("#startTime").val("<%=startTime%>");
	$("#searchBtn").bind("click",function(){
		$("#startTime").val($("#startTimeBox").datebox("getValue"));
		if(!isStartEndDate($("#startTime").val(),"<%=endTime%>")){
			$.messager.alert('警告','查询日期不能大于当前日期！','warning');
			return false;
		}else{
			form1.submit();
		}
	});
	
	function isStartEndDate(startDate,endDate){   
	    if(startDate.length>0&&endDate.length>0){ 
	     var allStartDate = new Date(startDate);   
	     var allEndDate = new Date();   
	     if(allStartDate.getTime()>allEndDate.getTime()){   
	      return false;   
	     }   
	    }   
	    return true;   
	   }
});

</script>
</html>