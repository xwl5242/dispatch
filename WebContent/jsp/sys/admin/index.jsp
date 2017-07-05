<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="top.jsp"%>

		<style type="text/css">
		.commitopacity{position:absolute; width:100%; height:100px; background:#7f7f7f; filter:alpha(opacity=50); -moz-opacity:0.8; -khtml-opacity: 0.5; opacity: 0.5; top:0px; z-index:99999;}
		</style>

</head>
<body style="overflow-x:hidden;overflow-y:hidden; ">

	<!-- 页面顶部¨ -->
	<%@ include file="head.jsp"%>

	<div class="container-fluid" id="main-container"  >
		<a  id="menu-toggler"><span></span></a>
		<!-- menu toggler -->

		<!-- 左侧菜单 -->
		
	    <%@ include file="left.jsp"%>
		
		
		

		<div id="main-content" class="clearfix">

			<div id="jzts" style="display:none; width:100%; position:fixed; z-index:99999999;">
			<div class="commitopacity" id="bkbgjz"></div>
				<div style="padding-left: 70%;padding-top: 1px;">
					<div style="float: left;margin-top: 3px;"><img src="static/images/loadingi.gif" /> </div>
					<div style="margin-top: -5px;"><h4 class="lighter block red">&nbsp;加载中 ...</h4></div>
				</div>
			</div>

			<div>
				<iframe name="mainFrame" id="mainFrame" frameborder="0" src="jsp/sys/admin/tab.jsp" style="margin:0 auto;width:100%;"></iframe>
			</div>

			<!-- 换肤	-->
			<div id="ace-settings-container">
				<div class="btn btn-app btn-mini btn-warning" id="ace-settings-btn">
					<i class="icon-cog"></i>
				</div>
				<div id="ace-settings-box">
					
						<c:forEach var="favorite" items="${favorite }">
							<div onclick="siMenu('z${favorite.RIGHTID }','lm','${favorite.RIGHTNAME }','${favorite.RIGHTURL }','')">
								<span >${favorite.RIGHTNAME }</span>
							</div>
						</c:forEach>
					
					
				</div>
			</div> 
			<!--/#ace-settings-container-->

		</div>
		<!-- #main-content -->
	</div>
	<form id="listForm" name="listForm">
	   <input type="hidden" id="rightId"  name="id" value="">
	   <input type="text" id="rightUrl" name="url" value="${url }">
	</form>
	<!--/.fluid-container#main-container-->
	<!-- basic scripts -->
		<!-- 引入 -->
		
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<!-- 引入 -->
		
		<script type="text/javascript" src="static/js/jquery.cookie.js"></script>
		<script type="text/javascript" src="static/js/myjs/menusf.js"></script>
		
		<!--引入属于此页面的js -->
		<script type="text/javascript" src="static/js/myjs/index.js"></script>
		
		<script type="text/javascript">
		
		
		
        function cmainFrame(){
			
			var hmain = document.getElementById("mainFrame");
			var bheight = document.documentElement.clientHeight;
			hmain .style.width = '100%';
			hmain .style.height = (bheight  - 21) + 'px';
			var bkbgjz = document.getElementById("bkbgjz");
			bkbgjz .style.height = (bheight  - 41) + 'px';
			
		}
        
        cmainFrame();
		window.onresize=function(){  
			cmainFrame();
		};
		
		
		function refreshFavorite(){
			
			var url="<%=path%>/login/refreshFavorite.do";
			
			 $.ajax({
		    		url:url,
		    		type:"post",
		    		
		    		dataType: "json", 
		            success: function(data){ 
		            	
		            	var html="";
		            	$.each(data,function(index,obj){
		            		html+="<div><span>"+obj.RIGHTNAME+"</span></div>";
		            	})
		            	
		            	$("#ace-settings-box").empty();
		            	
		            	$("#ace-settings-box").append(html);
		            }
		    		
		    	}) 
			
		}
		
		
		</script>
		
</body>
</html>
