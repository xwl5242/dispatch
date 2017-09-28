<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<HEAD>
	</HEAD>
	<BODY style="margin: 0px;">
		<img src="../static/images/jieshao.jpg" style="width:1318px;height:630px;overflow:auto;no-repeat center;" usemap="#mymap">
		<map name="mymap">
			<area alt="ea" shape="rect" coords="0,0,200,300" onclick="eaHtml()" />
			<area alt="weather" shape="rect" coords="900,0,1100,300" onclick="weatherHtml()">
			<area alt="warn" shape="rect" coords="440,330,640,630" onclick="warnHtml()">
			<area alt="roomTemp" shape="rect" coords="900,330,1100,630" onclick="roomTempHtml()">
		</map> 
		<script type="text/javascript" src="<%=path %>/static/main/js/jquery.js"></script>
		<script type="text/javascript" src="<%=path %>/plugins/attention/zDialog/zDrag.js"></script>
		<script type="text/javascript" src="<%=path %>/plugins/attention/zDialog/zDialog.js"></script>
		<script type="text/javascript" src="<%=path %>/static/dispatch/js/tendina.min.js"></script>
		<script type="text/javascript" src="<%=path %>/static/dispatch/js/common.js"></script>
		<script type="text/javascript" src="<%=path%>/static/js/myjs/head.js"></script>
		<script type="text/javascript">
			function eaHtml(){
	            siMenu('z737fc4c5cb3b40a68ca3c73316b82f1b','lm','通风系统电能耗分析','/jsp/energyanalysis/airconditioner/airsystem.jsp','');
	            siMenu('z748bf4d3f35c426eac203611cda7d4ee','lm','水泵系统电能耗分析','/jsp/energyanalysis/airconditioner/waterpumpsystem.jsp','');
	            siMenu('z8fe2a1cdbc0944adb160b3c25dead86f','lm','冷却塔电能耗分析','/jsp/energyanalysis/airconditioner/cooltower.jsp','');
	            siMenu('zaee752b964df4560adcc2734a4379c2b','lm','冷冻站单耗分析','/jsp/energyanalysis/airconditioner/coolstationDH.jsp','');
	            siMenu('ze8ae9beec66f4dc49e665d70c28b908d','lm','冷冻站能耗分析','/jsp/energyanalysis/airconditioner/coolstationNH.jsp','');
	            siMenu('z7760a5ad686a4311a877c8d3475b3036','lm','COP','/jsp/energyanalysis/airconditioner/cop.jsp','');
			
				top.mainFrame.tab.activate('z737fc4c5cb3b40a68ca3c73316b82f1b');
			}
			function weatherHtml(){
	            siMenu('z3d41715098b44baeb3ad07e0d31b0c06','lm','度日数分析','/jsp/weather/drsh.jsp','');
	            siMenu('z55e1c93c9d264cd58592adc80ab4a7fc','lm','平均温度','/jsp/weather/avgtemp.jsp','');
	            siMenu('zaa716fd834df4d3f956ffc71658bf772','lm','气象参数','/jsp/weather/weather.jsp','');
			}
			function warnHtml(){
				siMenu('z0001003','lm','报警统计','/jsp/warn/warnTotal.jsp','');
			}
			function roomTempHtml(){
				siMenu('zea22056c384e4b5babb6f4f993e9beb2','lm','室温分析','/jsp/energyanalysis/airconditioner/roomAndoutTemp.jsp','');
			}
		</script>
	</BODY>
</html>
