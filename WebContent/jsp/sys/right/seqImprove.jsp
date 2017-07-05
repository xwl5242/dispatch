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

    <script type="text/javascript">
        var arrIndex=new Array();
        var arrText=new Array();
        function up(){
        	
        	if($("#select1").val().length>1){
        		alert("请选择一个进行调整");
        		return false;
        	}
        	$("#seqImprove", parent.document).val("1");
        	var checkNums=($("#select1").val())[0];
        	if(checkNums==null){
        		return false;
        	}
        	var checkIndex=0;
        	for(var i=0;i<arrIndex.length;i++){
        		if(arrIndex[i]==checkNums){
        			checkIndex=i;
        		}
        	}
        	if(checkIndex==0){
        		return false;
        	}
        	var changeIndex=checkIndex-1;
        	
        	changeText(checkIndex,changeIndex,checkNums);
        }
        
        function changeText(checkIndex,changeIndex,checkNums){
			var checkText=arrText[checkIndex];
        	
        	var changeNums=arrIndex[changeIndex];
        	var changeText=arrText[changeIndex];
        	
        	arrIndex[checkIndex]=changeNums;
        	arrText[checkIndex]=changeText;
        	
        	arrIndex[changeIndex]=checkNums;
        	arrText[changeIndex]=checkText;
        	
        	var html="";
        	for(var i=0;i<arrIndex.length;i++){
        		var selected = "";
        		if(checkNums==arrIndex[i]){
        			selected="selected";
        		}
        		html+="<option value='"+arrIndex[i]+"' "+selected+">"+arrText[i]+"</option>";
        	}
        	$("#select1").empty();
        	$("#select1").append(html);
        }
        
        function down(){
        	
        	if($("#select1").val().length>1){
        		alert("请选择一个进行调整");
        		return false;
        	}
        	$("#seqImprove", parent.document).val("1");
        	var checkNums=($("#select1").val())[0];
        	if(checkNums==null){
        		return false;
        	}
        	var checkIndex=0;
        	for(var i=0;i<arrIndex.length;i++){
        		if(arrIndex[i]==checkNums){
        			checkIndex=i;
        		}
        	}
        	if(checkIndex==arrIndex.length-1){
        		return false;
        	}
        	var changeIndex=checkIndex+1;
        	changeText(checkIndex,changeIndex,checkNums);
        }
        
        
        function initData(url,pid){ 
        	
        	$.ajax({
        		type: "post", 
                url: url,
                data:{pid:pid},//form表单序列化
                dataType: "json", 
                success: function(data){
                	var html="";
                	$.each(data,function(index,obj){
                		arrIndex.push(obj.ID);
                		arrText.push(obj.RIGHTNAME);
                		html+="<option value='"+obj.ID+"'>"+obj.RIGHTNAME+"</option>";
                	});
                	$("#select1").empty();
                	$("#select1").append(html);
                }
                
        	});
        }
        function save(url,rval,id){
        	debugger;
        	for(var i=0;i<arrIndex.length;i++){
        		if(arrIndex[i]==rval){
        			$("#"+id,parent.document).val(i+1);
        			break;
        		}
        	}
        	return ;
        	$.ajax({
        		type: "post", 
                url: url,
                data:{ids:arrIndex.join("-")},//form表单序列化
                dataType: "json", 
                success: function(data){
                	/* if(data.flag){
                		window.parent.changeSeq();
                	} */
                }
        	});
        }
        
    </script>
    
    <style type="text/css">
      body{
      
        background-color: #f7f7f5;
      }
    
    </style>
    
</head>
<body >
<div style="width:60%;float:left;">
<select multiple="multiple" id="select1" style="width:300px;height:130px;">
            <option value="1">选项1</option>
            <option value="2">选项2</option>
            <option value="3">选项3</option>
            <option value="4">选项4</option>
            <option value="5">选项5</option>
            <option value="6">选项6</option>
            <option value="7">选项7</option>
</select>
</div>
<div style="float:left;height:130px;">
	 <ul style="margin:auto">
	     <li onclick="up()" class="icon-arrow-up">上移</li><br/>
	     <li onclick="down()" class="icon-arrow-down">下移</li>
	     
	 </ul>
     
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