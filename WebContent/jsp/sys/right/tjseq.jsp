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
	<script type="text/javascript" src="plugins/jqueryUI.js"></script>

    <script type="text/javascript">
    function changeNum(){
    	        $("#changeList li").each(function(){
    	            var indexNum=$("#changeList li").index(this);
    	            var a=(indexNum+1)>9?(indexNum+1):("0"+(indexNum+1));
    	            $(this).find(".num").text(a);
    	      });
    	}
    
    	
    $(function(){
    	$('#changeList').sortable().bind('sortupdate',function() {
    	    changeNum();
    	});
    });
    
    
    
        function initData(url,pid){ 
        	
        	$.ajax({
        		type: "post", 
                url: url,
                data:{pid:pid},//form表单序列化
                dataType: "json", 
                success: function(data){
                	$.each(data,function(index,obj){
                		$("#changeList").append("<li id="+obj.ID+">"+obj.SEQ+"   "+obj.NAME+"</li>");
                		$("#changeList li").css({"height":"20px","padding-top":"10px"});
                	});
                	$('#changeList').sortable().bind('sortupdate',function() {
                	    changeNum();
                	});
                }
                
        	});
        	
        	
        }
        var ids = "";
        function save(url,tableName,id){
        	var i = 0;
        	$("#changeList li").each(function(index,obj){
        	    var y = obj.id;
        	    if(y==id){
        	    	i = index+1;
        	    }
        	    ids += "-"+y;
        	});
        	
        	window.parent.setValue(i);
        	
        	$.ajax({
        		type: "post", 
                url: url,
                data:{ids:ids,tableName:tableName},//form表单序列化
                dataType: "json", 
                success: function(data){
                }
        	});
        }
    </script>
    
    
</head>
<body >
<div style="width:60%;float:left;">
	<ul id="changeList"></ul>
</div>



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