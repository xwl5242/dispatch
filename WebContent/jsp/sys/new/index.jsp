<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    
    <title>东环广场智能控制系统</title>
    <meta name="description" content="overview & stats" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
	
    <link type="text/css" rel="stylesheet" href="<%=path %>/static/main/css/frame_user.css" />
	
	<script type="text/javascript" src="<%=path %>/static/main/js/jquery.js"></script>
	<script type="text/javascript" src="<%=path %>/static/main/js/styleswitch.js"></script>
	<!--引入弹窗组件start-->
	<script type="text/javascript" src="<%=path %>/plugins/attention/zDialog/zDrag.js"></script>
	<script type="text/javascript" src="<%=path %>/plugins/attention/zDialog/zDialog.js"></script>
	<!--引入弹窗组件end-->
	<link rel="stylesheet" href="<%=path %>/plugins/toastr/toastr.min.css" />  
	<script type="text/javascript" src="<%=path %>/plugins/toastr/toastr.min.js"></script>
	<!-- 引入自定义工具js开始 -->
	<script type="text/javascript" src="<%=path %>/static/js/myjs/customTool.js"> </script>
	
	
<link href="<%=path %>/static/dispatch/css/indexstyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path %>/static/dispatch/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/static/dispatch/js/tendina.min.js"></script>
<script type="text/javascript" src="<%=path %>/static/dispatch/js/common.js"></script>
<script type="text/javascript" src="<%=path%>/static/js/myjs/head.js"></script>
	<!-- 引入自定义工具js结束 -->
	
	<script type="text/javascript">
	function showMessage(){
		if($(".frame-user").is(":hidden")){
			$(".frame-user").show();
		}else{
			$(".frame-user").hide();
			$(".theme-box").toggle("normal");
		}
		$(".theme-box").hide();
	}
	function closeSys(){
		$.ajax({ 
		    type: "post", 
		    url: "<%=path%>/login/removeSession.do?cmd=removeSession",
		    data:{} ,//form表单序列化
		    dataType: "json", 
		    success: function(data){ 
		    	if(data.flag){ 
		    		 window.location.href=locat+"/";
		    	}else{
		    		alert("会话过期,请重新登录");
		    	} 
		    },
		    error:function(){
		    	alert("会话过期,请重新登录" );
		    	window.location.href=locat+"/";
		    }
		});
	}
	function updatePassword(){
		$(".theme-box").hide();
		baseDialog("修改密码",'<%=path%>/jsp/sys/updatePassWord.jsp',240,240);
	}
	
	$(function(){
		
		$.post('<%=path%>/runRecord/indexParams.do',{},function(data){
			$('#wdAsd').html("温度："+data.wd+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp湿度："+data.sd);
			$('#pmAfl').html("PM2.5："+data.pm+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp风力："+data.fs);
		},'json');
		
		$('.theme-brick').click(function(){
			switchStylestyle(this.getAttribute("rel"));
			return false;
		});
		var c = readCookie('style');
		if (c) switchStylestyle(c);
	})
	
	function nowDate(){
		var now = new Date();
	    var year = now.getFullYear();
	    var month = now.getMonth();
	    var date = now.getDate();
	    var day = now.getDay();
	    var hour = now.getHours();
	    var minu = now.getMinutes();
	    var sec = now.getSeconds();
	    var week;
	    month = month+1;
	    if(month<10)month="0"+month;
	    if(date<10)date="0"+date;
	    if(hour<10)hour="0"+hour;
	    if(minu<10)minu="0"+minu;
	    if(sec<10)sec="0"+sec;
	    var arr_week = new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
	    week = arr_week[day];
	    var time = "";
	    time = year+"/"+month+"/"+date+" "+week+" "+hour+":"+minu+":"+sec;
		$("#nowDate").text(time);
		setTimeout("nowDate();", 1000); 
	}
	$(window).load(function() {
		nowDate();
	});
	
	function changeSystem(id,url,num,name){
		var temp="/login/secondMain.do?id="+id+"&num="+num;
		siCommonMenu("z"+id,"lm",name,temp);
		return;
		$("#rightType").val(num);
		$("#rightId").val(id);
		$("#rightUrl").val(url);
		
		var target=document.listForm;
		    target.method="post";
		    target.action="<%=path%>/login/secondMain.do";
		    target.submit();
        	<%-- <a  href="javascript:siMenu('z${right.id }','lm','${right.rightname }','/jsp/sys/new/indexmonitor.jsp','')">${right.rightname } --%>
	}
	$(function(){
		
		//绑定菜单显示隐藏
		$("#left_sq").click(function (){
			if($(".left_menu").is(":hidden")){
				$(".left_menu").show();
				$(this).addClass("left_sq").removeClass("right_sq");
				$(".cont_right").css("margin-left",220);
			}else{
				$(this).addClass("right_sq").removeClass("left_sq");
				$(".cont_right").css("margin-left",20);
				$(".left_menu").hide();
			}
		});
		 
	});
	function showAllName(id,name){
		$("#"+id).html(name);
	}
	function showJIANCHEN(id){
		var htmls = $("#"+id).html();
		if(htmls.length>8){
			htmls = htmls.substring(0,8)+"...";
		}
		$("#"+id).html(htmls);
	}
	</script>
</head>
<body  style="overflow-x:hidden;overflow-y:hidden; ">
<!--顶部-->
<div class="index_top">
   	<div>
       	 <div class="index_logo"></div>
           <!--<div id="warnDataDetail" class="alert" style="cursor: pointer;">
           	<div id="warnNum">10</div>
           </div> -->
           <div class="top_right font_shadow">
<!--            <div style="width: 185px;float: left;"> -->
<!--            		<span id="nowDate" style="width: 185px"></span> -->
<!--            		<span id="wd" style="width: 185px"></span> -->
<!--            		<span id="sd" style="width: 185px"></span> -->
<!--            		<span id="pm" style="width: 185px"></span> -->
<!--            		<span id="fl" style="width: 185px"></span> -->
<!--            	</div> -->
           	<div>
           		<ul style="margin-top: 12px;">
           			<li id="nowDate" style="margin-bottom:5px;font-weight: bold;"></li>
           			<li id="wdAsd" style="margin-bottom:5px;font-weight: bold;"></li>
           			<li id="pmAfl" style="margin-bottom:5px;font-weight: bold;"></li>
           		</ul>
           	</div>
               <div class="index_user font_shadow" onclick="showMessage()" >
               	<div>
                   	${sessionScope.ECCUSER.loginName}<br />
                   	${sessionScope.ECCUSER.userName}
                   </div>
               </div>
               <div>
               	<a class="close" onclick="closeSys()"></a>
               </div>
           </div>
       </div>
</div>
<!-- 左侧菜单 -->
	<div class="content_box">
		<div id="left_sq" class="left_sq"></div>
    	<div class="left_menu box_shadow">
            <ul id="menu">
                <c:forEach items="${sysList}" var="sys" varStatus="sysStatus">
                 <li id="secondMenu${sysStatus.count}" class="childUlLi">
				 	<a id="${sys.ID }">${sys.RIGHTNAME }<c:if test="${sys.hasSubRight}"><div></div></c:if></a>
				 	<c:if test="${sys.hasSubRight}">
				 	<ul class="menu_zk">
				 		<c:forEach items="${sys.subRight}" var="right" varStatus="rightStatus">
                        <li>
                        	<c:choose>
                        		<c:when test="${not empty right.subRight}">
                        			<a onmouseout="showJIANCHEN('${right.id }')" onmouseover="showAllName('${right.id }','${right.rightname}')" id="${right.id }" href="javascript:changeSystem('${right.id }','${right.righturl }','2','${right.rightname }')">
                        				<c:choose>
	                        				<c:when test="${fn:length(right.rightname) > 8 }">
	                        					<c:out value="${fn:substring(right.rightname, 0, 8)}..."></c:out>
	                        				</c:when>
	                        				<c:otherwise>  
										      <c:out value="${right.rightname}" />  
										    </c:otherwise>
										</c:choose> 
                        			</a>
                        		</c:when>
                        		<c:otherwise>
                        			<a onmouseout="showJIANCHEN('${right.id }')" onmouseover="showAllName('${right.id }','${right.rightname}')" id="${right.id }" href="javascript:siMenu('z${right.id }','lm','${right.rightname }','${right.righturl }','')" >
										<c:choose>
											<c:when test="${fn:length(right.rightname) > 8 }">
	                        					<c:out value="${fn:substring(right.rightname, 0, 8)}..."></c:out>
	                        				</c:when>
	                        				<c:otherwise>  
										      <c:out value="${right.rightname}" />  
										    </c:otherwise> 
										</c:choose>
									</a>
                        		</c:otherwise>
                        	</c:choose>
                        </li>
				 		</c:forEach>
				 		</ul>
				 	</c:if> 
				 </li>
			 	</c:forEach>
<!-- 			 	<li class="childUlLi"> -->
<!--                      <a  href="javascript:openBig()">运行参数</a> -->
<!--                 </li> -->
        	</ul>
        </div>
 </div>       
 <!-- 右边主界面 -->       
  <div class="cont_right">
  	<iframe name="mainFrame" id="mainFrame" frameborder="0" src="<%=path %>/jsp/sys/admin/tab.jsp" style="margin:0 auto;width:100%;"></iframe>
  </div>

	<form id="listForm" name="listForm">
	   <input type="hidden" id="rightId"  name="id" value="">
	   <input type="hidden" id="rightUrl" name="url" value="${url}">
	   <input type="hidden" id="rightType"  name="rType" value="">
	</form>
	
	<div class="frame-user" style="display:none;">
	<div><div class="triangle-left"></div></div>
	<div class="frame-user-main">
		<div class="frame-user-heading">
			<div class="frame-user-heading-main">
				<div style="font-size: 20px;">${usernamev}
					<span style="font-weight: normal; font-size: 10px; margin-right:10px;float:right;">${orgname}</span>
				</div>
				
			</div>
			<div class="frame-user-heading-quit">
				<img src="<%=path %>/static/main/images/arrow-right-door16.png" width="16" height="16" border="0" alt="关闭" style="cursor:pointer;" title="退出系统" onclick="closeSys()">
			</div>	
		</div>
		<div class="frame-user-body">
			<div class="frame-user-function">
				<div class="frame-user-function-item" id="_renshika" onclick="updatePassword()">
					更改密码
				</div> 
			</div>
		</div>
	</div>
</div>
	<script type="text/javascript">
	function cmainFrame(){
	    var vWidth=$(window).width()-180;
		document.getElementById("mainFrame").height=$(window).height()-80;
		document.getElementById("mainFrame").width=vWidth;
	}
	cmainFrame();
	window.onresize=function(){  
		cmainFrame();
	};

	function gotoSR(){
		document.getElementById("box_mid1").style.display='block';
		document.getElementById("box_mid").style.display='none';
		document.getElementById("mainFrame1").src="<%=path %>/jsp/sys/admin/sr.jsp";
	}
	function openBig(){
		window.open("<%=path %>/dp/dp.do");
		//window.open("<%=path %>/jsp/weather/dp.html");
	}
	function openQX(){
		window.open("<%=path %>/jsp/weather/getWeatherForLine.jsp");
	}
	function openGis(){
		window.open("/../TJ_RLpro_Use/mapview.html");
	}
	function openYJ(){
		window.open("/dispatch/jsp/dispatch/event/eventDataList.jsp");
	}

	</script>
	
		
</body>
</html>