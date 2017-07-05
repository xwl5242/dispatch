<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/runqianReport4.tld" prefix="report" %>
<%
String params = "";
StringBuffer sb = new StringBuffer();
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
String startDate = request.getParameter("startDate");
if(null!=startDate&&"".equals(startDate)){
	startDate = sdf.format(new Date());
}
sb.append("startDate="+startDate);
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
				<input id='startDateBox' style="width:155px" name="startDateBox" class="easyui-datebox" />
				<input type="hidden" id='startDate' name="startDate" value="<%=startDate%>" style="width:100%" />
			</td>   
			<td class="tbl_td_input" width="67%" align="left">
	   			<a id="searchBtn" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
			</td>
		</tr>
</table>
</FORM>
<div style="height: 17px"></div>
<div id="rqDivId" style="margin-left:130px">
<report:html name="report1" reportFileName="DL.raq" needPageMark=""
        params="<%=params %>" submit=""  width="-1"  height="-1" needScroll="yes" scrollWidth="100%"  
        scrollHeight="90%" needSaveAsExcel="yes" funcBarLocation="bottom"/>
</div>
</body>	 	
<script type="text/javascript"> 
$(document).ready(function(){
	$("#startDateBox").datebox("setValue","<%=startDate%>");
	$("#startDate").val("<%=startDate%>");
	$("#searchBtn").bind("click",function(){
		$("#startDate").val($("#startDateBox").datebox("getValue"));
		form1.submit();
	});
});

</script>
</html>