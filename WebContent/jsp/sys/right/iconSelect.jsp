<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
	<%@ include file="../../sys/admin/top.jsp"%> 

	<script src="<%=path %>/plugins/echart/js/esl/esl.js"></script>
	<script src="<%=path %>/plugins/echart/echartCustom.js"></script>
    
    <script type="text/javascript">
         function iconSelect(icon){
				window.parent.writeImage(icon);
         }
    </script>
    
   
    
</head>
<body >

<table id="table_report" class="table table-striped table-bordered table-hover">
				
				
										
				<tbody>
					
				
							<tr>
								
								        <td class='center' style="width: 30px;"><li onclick="iconSelect('icon-search')"><i class="icon-retweet"></i></li></td>
										<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-star')"><i class="icon-star"></i></li></td>
										<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-heart')"><i class="icon-heart"></i></li></td>
										<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-envelope')"><i class="icon-envelope"></i></li></td>
										<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-search')"><i class="icon-search"></i></li></td>
										<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-user')"><i class="icon-user"></i></li></td>
								
							</tr>
						
					        <tr>
								
								        <td class='center' style="width: 30px;"><li onclick="iconSelect('icon-film')"><i class="icon-film"></i></li></td>
										<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-th-large')"><i class="icon-th-large"></i></li></td>
										<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-ok')"><i class="icon-ok"></i></li></td>
										<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-remove')"><i class="icon-remove"></i></li></td>
										<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-off')"><i class="icon-off"></i></li></td>
										<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-signal')"><i class="icon-signal"></i></li></td>
								
							</tr>
							<tr>
								
								        <td class='center' style="width: 30px;"><li onclick="iconSelect('icon-cog')"><i class="icon-cog"></i></li></td>
										<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-trash')"><i class="icon-trash"></i></li></td>
										<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-home')"><i class="icon-home"></i></li></td>
										<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-time')"><i class="icon-time"></i></li></td>
										<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-road')"><i class="icon-road"></i></li></td>
										<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-inbox')"><i class="icon-inbox"></i></li></td>
								
							</tr>
							
							<tr>
							    		<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-play-circle')"><i class="icon-play-circle"></i></li></td>
							    		<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-repeat')"><i class="icon-repeat"></i></li></td>
							    		<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-refresh')"><i class="icon-refresh"></i></li></td>
							    		<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-lock')"><i class="icon-lock"></i></li></td>
							    		<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-headphones')"><i class="icon-headphones"></i></li></td>
							    		<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-qrcode')"><i class="icon-qrcode"></i></li></td>
							</tr>
							
							<tr>
							    		<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-barcode')"><i class="icon-barcode"></i></li></td>
							    		<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-book')"><i class="icon-book"></i></li></td>
							    		<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-bookmark')"><i class="icon-bookmark"></i></li></td>
							    		<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-camera')"><i class="icon-camera"></i></li></td>
							    		<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-picture')"><i class="icon-picture"></i></li></td>
							    		<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-map-marker')"><i class="icon-map-marker"></i></li></td>
							</tr>
							
							<tr>
							    		<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-fire')"><i class="icon-fire"></i></li></td>
							    		<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-bell')"><i class="icon-bell"></i></li></td>
							    		<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-globe')"><i class="icon-globe"></i></li></td>
							    		<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-folder-close')"><i class="icon-folder-close"></i></li></td>
							    		<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-hdd')"><i class="icon-hdd"></i></li></td>
							    		<td class='center' style="width: 30px;"><li onclick="iconSelect('icon-shopping-cart')"><i class="icon-shopping-cart"></i></li></td>
							    		
							</tr>
							<tr>
							            <td class='center' style="width: 30px;"><li onclick="iconSelect('icon-plane')"><i class="icon-plane"></i></li></td>
							            <td class='center' style="width: 30px;"><li onclick="iconSelect('icon-magnet')"><i class="icon-magnet"></i></li></td>
							            <td class='center' style="width: 30px;"><li onclick="iconSelect('icon-comment')"><i class="icon-comment"></i></li></td>
							            <td class='center' style="width: 30px;"><li onclick="iconSelect('icon-calendar')"><i class="icon-calendar"></i></li></td>
							            <td class='center' style="width: 30px;"><li onclick="iconSelect('icon-gift')"><i class="icon-gift"></i></li></td>
							            <td class='center' style="width: 30px;"><li onclick="iconSelect('icon-leaf')"><i class="icon-leaf"></i></li></td>
							            
							</tr>
							<tr>
							           <td class='center' style="width: 30px;"><li onclick="iconSelect('icon-tasks')"><i class="icon-tasks"></i></li></td>
							            <td class='center' style="width: 30px;"><li onclick="iconSelect('icon-filter')"><i class="icon-filter"></i></li></td>
							            <td class='center' style="width: 30px;"><li onclick="iconSelect('icon-bullhorn')"><i class="icon-bullhorn"></i></li></td>
							            <td class='center' style="width: 30px;"><li onclick="iconSelect('icon-certificate')"><i class="icon-certificate"></i></li></td>
							            <td class='center' style="width: 30px;"><li onclick="iconSelect('icon-briefcase')"><i class="icon-briefcase"></i></li></td>
							            <td class='center' style="width: 30px;"><li onclick="iconSelect('icon-list-alt')"><i class="icon-list-alt"></i></li></td>
							</tr>
					
				
				</tbody>
</table>












  
		
		
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<!-- 引入 -->
		<script type="text/javascript">
		
  		
		</script>
		
		
		<style type="text/css">
		li {list-style-type:none;}
		</style>
		<ul class="navigationTabs">
            <li><a></a></li>
            <li></li>
        </ul>
	</body>
</html>
