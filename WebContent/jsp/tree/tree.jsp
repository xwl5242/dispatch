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
	<link rel="stylesheet" href="<%=path %>/plugins/zTree/3.5/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
	<script src="<%=path %>/plugins/zTree/3.5/jquery.ztree.core-3.5.js"></script>
	<script src="<%=path %>/plugins/zTree/3.5/jquery.ztree.excheck-3.5.js"></script>
    
    
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

		
		
		function init(){
			var url="<%=path%>/energy/loadUnitTreeForZTree.do";
			$.ajax({
				url:url,
				type:"post",
				dataType:"json",
				success:function(data){
					treeCheckBox("treeDemo",data);
					$.fn.zTree.getZTreeObj("treeDemo").expandAll(false);
				}
			})
			
		}
        
		
		function treeCheckBox(id,zNodes){
			$.fn.zTree.init($("#"+id), setting, zNodes);
			type =  { "Y" : "", "N" : "" };
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.setting.check.chkboxType = type;
		}
		
		function save(){
			
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var node=zTree.getCheckedNodes(true);
			var nameArr=new Array();
			var idArray=new Array();
			for(var i=0;i<node.length;i++){
				idArray.push(node[i].id);
				nameArr.push(node[i].name);
			}
			top.Dialog.transportData = nameArr.join(",");
			top.Dialog.transportId = idArray.join(",");
			top.Dialog.close()
		}
    
    </script>

    
</head>
<body onload="init()">

<div id="zhongxin" class=""> 
   <div class="zTreeDemoBackground left">
		<ul id="treeDemo" class="ztree"></ul>
	</div>
	
</div>	

<div>
	   <div id="bottomNav" style="position:absolute;left:100px;top:380px;"><a class="btn btn-mini btn-primary" onclick="save();">保存</a><a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a><br><br></div>
</div>
</body>
</html>
