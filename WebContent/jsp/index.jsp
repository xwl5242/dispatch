<%@ page language="java" contentType="text/html; charset=UTF-8" 
pageEncoding="UTF-8"%>
<% 
	String tempName = request.getParameter("viewname");
	if(null==tempName||"".equals(tempName)){
		tempName = "admin";
	}
	String viewName = new String(tempName.getBytes("iso-8859-1"),"utf-8");
%>
<html>
	<HEAD>
		<TITLE>Home Page</TITLE>
		<META name=GENERATOR content="PCAuto 6.0">
		<META name="Microsoft Theme" content="global 101, default">
		<META name="Microsoft Border" content="tl, default">
		<STYLE>
			UNKNOWN {
				BACKGROUND-COLOR: rgb(255,255,255); COLOR: rgb(128,128,128)
			}
			UNKNOWN {
				MARGIN-LEFT: 1px
			}
			UNKNOWN {
				PADDING-LEFT: 1px; MARGIN-LEFT: 1px; MARGIN-RIGHT: 1px
			}
			BODY {
				MARGIN: 0px
			}
		</STYLE>
	</HEAD>

	<BODY>
<!-- 	   <OBJECT id="drawcomControl" classid="clsid:223DADA1-2CC9-4312-AE8D-35E711626152" CODEBASE="drawcom.cab#version=8,0,1,21" width="1680" height="1050"> -->
<!-- 			<PARAM NAME="_Version" VALUE="131072"> -->
<!-- 			<PARAM NAME="_ExtentX" VALUE="18450"> -->
<!-- 			<PARAM NAME="_ExtentY" VALUE="23566"> -->
<!-- 			<PARAM NAME="_StockProps" VALUE="0"> -->
<!-- 			<PARAM NAME="ServerAddress" VALUE="123.117.171.86"> -->
<!-- 			<PARAM NAME="DataAddress" VALUE="123.117.171.86"> -->
<!-- 			<PARAM NAME="ViewName" VALUE="登录页面"> -->
<!-- 			<PARAM NAME="FullScreen" VALUE="false"> -->
<!-- 			<PARAM NAME="UserLevel" VALUE="-1"> -->
<!-- 			<PARAM NAME="LocalDataSource" VALUE="0"> -->
<!-- 			<PARAM NAME="WebPort" VALUE="8000"> -->
<!-- 			<PARAM NAME="NetType" VALUE="0"> -->
<!-- 			<PARAM NAME="IISSubPath" VALUE=""> -->
<!-- 		</OBJECT> -->
		<OBJECT id="drawcomControl" codeBase="drawcom.cab#version=8,0,1,21" classid="clsid:223DADA1-2CC9-4312-AE8D-35E711626152" width="1680" height="1050">
			<PARAM NAME="_Version" VALUE="131072">
			<PARAM NAME="_ExtentX" VALUE="18450">
			<PARAM NAME="_ExtentY" VALUE="23566">
			<PARAM NAME="_StockProps" VALUE="0">
			<PARAM NAME="ServerAddress" VALUE="182.50.124.179">
			<PARAM NAME="ViewName" VALUE="">
			<PARAM NAME="FullScreen" VALUE="false">
			<PARAM NAME="UserLevel" VALUE="-1">
			<PARAM NAME="LocalDataSource" VALUE="0">
			<PARAM NAME="WebPort" VALUE="7889">
			<PARAM NAME="NetType" VALUE="0">
			<PARAM NAME="DataAddress" VALUE="182.50.124.179">
			<PARAM NAME="IISSubPath" VALUE="">
		</OBJECT>
		<script>
			viewname = '<%=viewName%>';
			if("admin"==viewname) viewname = "登陆页面";
			var activeXCon = document.getElementById("drawcomControl");
			activeXCon.ViewName = viewname;

		</script>
	</BODY>
</html>
