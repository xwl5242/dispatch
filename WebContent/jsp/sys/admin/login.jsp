<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>东环广场智能控制系统</title>
<meta charset="UTF-8" />   
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
	<%-- <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/login/css/ddlogin.css">   --%>
<%-- <link href="<%= request.getContextPath()%>/static/dispatch/css/loginstyle.css" rel="stylesheet" type="text/css" /> --%>
<link href="<%= request.getContextPath()%>/static/login2/css/login_style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="static/js/jquery-1.5.1.min.js"></script>
	<!--[if lt IE 9]>
        <script src="static/js/ie8/html5shiv.min.js"></script>
        <script src="static/js/ie8/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<div class="login_logo"></div>
    <div class="login_box">
    	<div class="login_tit">
        	用户登录
        </div>
        <div class="login_username">
        	<input type="text" name="loginname" id="loginname" placeholder="请输入用户名"/>
        </div>
        <div class="login_password">
        	<input type="password" name="password" id="password" placeholder="请输入密名"/>
        </div>
        <div class="login_btn">
        	<input type="checkbox" id="saveid"/> <span>记住密码</span>
            <a id="to-recover" onclick="severCheck();">登 录</a>
        </div>
    </div>
	<script type="text/javascript">
	
		//服务器校验
		function severCheck(){
			if(check()){
				
				var loginname = $("#loginname").val();
				var password = $("#password").val();
				debugger;
				$.ajax({
					type: "POST",
					url: "<%= path%>/login/newCheckLogin.do?cmd=newCheckLogin",
			    	data: {'loginName':loginname,'password':password,'tm':'1'},
					dataType:'json',
					
					success: function(data){
						if(data.flag){
							saveCookie();
							//window.location.href="main/index";
							window.location.href="<%= path%>/login/main.do";
						}else if(!data.flag){
							$("#loginname").tips({
								side : 1,
								msg : data.messager,
								bg : '#FF5080',
								time : 3
							});
							
							
							$("#loginname").focus();
						}else{
							$("#loginname").tips({
								side : 1,
								msg : "缺少参数",
								bg : '#FF5080',
								time : 15
							});
							$("#loginname").focus();
						}
					}
				});
			}
		}
	
		

		$(document).keyup(function(event) {
			if (event.keyCode == 13) {
				$("#to-recover").trigger("click");
			}
		});

		function genTimestamp() {
			var time = new Date();
			return time.getTime();
		}

		

		//客户端校验
		function check() {

			if ($("#loginname").val() == "") {

				$("#loginname").tips({
					side : 2,
					msg : '用户名不得为空',
					bg : '#AE81FF',
					time : 3
				});

				$("#loginname").focus();
				return false;
			} else {
				$("#loginname").val(jQuery.trim($('#loginname').val()));
			}

			if ($("#password").val() == "") {

				$("#password").tips({
					side : 2,
					msg : '密码不得为空',
					bg : '#AE81FF',
					time : 3
				});

				$("#password").focus();
				return false;
			}
			

			$("#loginbox").tips({
				side : 1,
				msg : '正在登录 , 请稍后 ...',
				bg : '#68B500',
				time : 1
			});

			return true;
		}

		function savePaw() {
			if (!$("#saveid").attr("checked")) {
				$.cookie('loginname', '', {
					expires : -1
				});
				$.cookie('password', '', {
					expires : -1
				});
				$("#loginname").val('');
				$("#password").val('');
			}
		}

		function saveCookie() {
			if ($("#saveid").attr("checked")) {
				$.cookie('loginname', $("#loginname").val(), {
					expires : 7
				});
				$.cookie('password', $("#password").val(), {
					expires : 7
				});
			}
		}
		function downLoad(){
		   window.location.href="<%=path%>/upload/chrome.exe";
		}
		
		$(function() {
			var loginname = $.cookie('loginname');
			var password = $.cookie('password');
			if (typeof(loginname) != "undefined"
					&& typeof(password) != "undefined") {
				$("#loginname").val(loginname);
				$("#password").val(password);
				$("#saveid").attr("checked", true);
				
			}
		});
	</script>
	<script>
		//TOCMAT重启之后 点击左侧列表跳转登录首页 
		if (window != top) {
			top.location.href = location.href;
		}
		
	</script>

	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/jquery-1.7.2.js"></script>
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript" src="static/js/jquery.cookie.js"></script>
</body>

</html>