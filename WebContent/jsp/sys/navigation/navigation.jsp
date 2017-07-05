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
	
	<style>
	 
	.func-panel {
		background-color:#fff;
		box-shadow:0 0 3px #888;-moz-box-shadow:0 0 3px #888;-webkit-box-shadow:0 0 3px #888;
		border-radius:3px;
		padding:20px 0;
		color:#000;
		opacity:0.95;
		filter:alpha(opacity=95);
	}
	.func-row {
		display:table;
		width:100%;
		padding:10px 0;
	}
	.func-row:hover {
		background-color:#f2f5f7;
	}
	.func-row + .func-row {
		border-top:1px solid #f2f5f7;
	}
	.func-type-title {
		display:table-cell;
		width:120px;
		vertical-align:top;
		padding:10px 0;
		text-align:center;
		font-size:16px;
		font-weight:bold;
		color:darkred;
	}
	.func-item-panel {
		display:table-cell;
		padding-left:20px;
	}
	
	.func-item {
		width:185px;
		float:left;
		margin:5px 5px;
		overflow:hidden;
		text-overflow:ellipsis;
		white-space:nowrap;
	}
	.func-item > img {
		cursor:pointer;
	}
	.func-item:hover {
		
	}
	.func-link {
		display:inline-block;
		font-weight:bold;
		cursor:pointer;
		padding:5px;
	}
	.func-link:hover {
		background-color:#3679df;
		color:#fff;
		border-radius:8px;
	}
	 
	
	</style>
    <script type="text/javascript">
    function usercollectNav(id){
         var url="";
         var imgsrc="";
         var bool=$("#info"+id).val();
         if(bool=="false"){
        	 url="<%=path%>/navigation/removeFid.do";
        	 imgsrc="<%=path %>/static/images/star_3.png";
        	 $("#info"+id).val("true");
         }else{
        	 url="<%=path%>/navigation/addFid.do";
        	 imgsrc="<%=path %>/static/images/star.png";
        	 $("#info"+id).val("false");
         }
         
         
    	 $.ajax({
    		url:url,
    		type:"post",
    		data:{id:id},
    		dataType: "json", 
            success: function(data){ 
            	if(data.flag){
            		$("#img"+id).attr("src",imgsrc)
            		window.parent.parent.refreshFavorite();
            	}
            }
    		
    	}) 
    	
    	
    }
    
    $(function(){
    	$(".tab-pane").eq(0).attr("class","tab-pane active")
    })
    </script>
	</head>
<body>

<ul class="nav nav-tabs" id="myTab" style="margin-top:10px;">
          <c:forEach var="sys" items="${sysList }" varStatus="vs">
             <c:if test="${vs.index==0 }">
                  <li class="active"><a data-toggle="tab" href="#home${sys.ID }"><i class="blue icon-home bigger-110"></i> ${sys.RIGHTNAME }</a></li>
                  
             </c:if>
             <c:if test="${vs.index!=0 }">
                  <li class=""><a data-toggle="tab" href="#home${sys.ID }"><i class="blue icon-home bigger-110"></i> ${sys.RIGHTNAME }</a></li>
             </c:if> 
              
          </c:forEach>
              
</ul>

 <div class="tab-content">
	<c:forEach var="sys" items="${sysList }">
	              <div id="home${sys.ID }" class="tab-pane ">
			      <c:forEach var="menu" items="${rightList }">
			             
			            <c:if test="${menu.parentid==sys.ID }">
			                   
			                   <c:if test="${menu.hasSubRight}">
			                   
			                          <div class="func-row">
			                          		<div class="func-type-title">${menu.rightname }</div>
			                          		
			                          		<div class="func-item-panel">
				                          		     <c:forEach var="sub" items="${menu.subRight }"> 
					                          				<div class="func-item flag-function">
					                          				    
					                          				       <c:if test="${sub.FID==null }">
					                          				           <img src="<%=path %>/static/images/star_3.png" id="img${sub.ID }" class="func-collected" width="16" height="16" border="0" alt="收藏" title="加入收藏" onclick="usercollectNav('${sub.ID}')">
					                          				           <input id="info${sub.ID }" type="hidden" value="true"/>
					                          				       </c:if>
					                          				       <c:if test="${sub.FID!=null }">
					                          				           <img src="<%=path %>/static/images/star.png" id="img${sub.ID }" class="func-collected" width="16" height="16" border="0" alt="收藏" title="取消收藏" onclick="usercollectNav('${sub.ID}')">
					                          				           <input id="info${sub.ID }" type="hidden" value="false"/>
					                          				       </c:if>
					                          				   
																	
																	<span class="func-link" >${sub.RIGHTNAME }</span>
															</div>
													 </c:forEach>
											<div class="clearfix"></div>
											</div>
									  </div>
							    
			                   </c:if>
			                  
			            </c:if>
			      </c:forEach>
			      </div>
	</c:forEach>
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
