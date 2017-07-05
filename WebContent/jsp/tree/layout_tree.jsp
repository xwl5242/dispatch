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
	<%@ include file="../sys/admin/top.jsp"%> 
	
	<link rel="stylesheet" href="plugins/zTree/3.5/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="static/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="plugins/zTree/3.5/jquery.ztree.core-3.5.js"></script>
	<script src="<%=path %>/plugins/zTree/3.5/jquery.ztree.excheck-3.5.js"></script>
</head>
<body style="overflow-x:hidden;overflow-y:hidden; ">


   		

<div class="container-fluid" id="main-container">
	        <table style="width:100%;" border="0">
	        	  
	              <tr>
					<td style="width:22%;" valign="top" bgcolor="#F9F9F9">
						<div style="width:100%;">
							名称：<input value="" ttype="text"/><br/>
							 <div style="margin:5px;text-align:right;" ><a class="btn btn-mini btn-primary" onclick="save();">查询</a><a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">重置</a></div>
							<ul id="leftTree" class="ztree"></ul>
						</div>
					</td>
					
					<td style="width:78%;" valign="top" >
						<iframe name="treeFrame" id="treeFrame" frameborder="0" src="${menuUrl }" onclick="" style="margin:0 auto;width:100%;"></iframe>
					</td>
				</tr>
			</table>

</div>
	
<script type="text/javascript">
     var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};

        init();
		
		function init(){
			var url="<%=path%>/energy/loadUnitTreeForZTree.do";
			$.ajax({
				url:url,
				type:"post",
				dataType:"json",
				success:function(data){
					treeCheckBox("leftTree",data);
					$.fn.zTree.getZTreeObj("leftTree").expandAll(false);
				}
			})
			
		}
        
		
		function treeCheckBox(id,zNodes){
			$.fn.zTree.init($("#"+id), setting, zNodes);
			type =  { "Y" : "", "N" : "" };
			var zTree = $.fn.zTree.getZTreeObj(id);
			zTree.setting.check.chkboxType = type;
			//$.fn.zTree.init($("#"+id), setting, zNodes);
		}
		
		function treeFrameT(){
			
			var hmainT = document.getElementById("treeFrame");
			var bheightT = document.documentElement.clientHeight;
			
			hmainT.style.width = '100%';
			hmainT.style.height = (bheightT+25) + 'px';
		}
		treeFrameT();
		window.onresize=function(){  
			treeFrameT();
		};
    
    </script>

		
</body>
</html>
