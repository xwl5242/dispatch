<%
	String pathl = request.getContextPath();
	String basePathl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+pathl+"/";
%>

 <script type="text/javascript">
		
		var initTop=0;
		var index=0;  
		function handle(delta) {
			
			var allTopLimit=$("#sidebar").height();
			
			var winHeight=$(window).height();
			 
			var limitHeight=winHeight-allTopLimit-50;
			
			
			
		    var s = delta ;
		        
		    var thisStep=15*delta;
		    
		        initTop=initTop+thisStep;
		        
		    if(initTop>0){
		    	initTop=0;
		    }
		    if(initTop<limitHeight&&limitHeight<0){
		    	initTop=limitHeight;
		    }
		    
		    $("#test").val(limitHeight+"---------"+winHeight+"----------"+allTopLimit)
		    
		    if(limitHeight>0){
		    	initTop=0;
		    }
		    
		    $("#sidebar").attr("style","margin-top:"+initTop+"px;");
		    
		}
		function wheel(event){
		    var delta = 0;
		    if (!event) event = window.event;
		    if (event.wheelDelta) {
		        delta = event.wheelDelta/120; 
		        if (window.opera) delta = -delta;
		    } else if (event.detail) {
		        delta = -event.detail/3;
		    }
		    if (delta)
		        handle(delta);
		}
		if (window.addEventListener)
		window.addEventListener('DOMMouseScroll', wheel, false);
		window.onmousewheel = document.onmousewheel = wheel;
		</script>  


		<!-- 本页面涉及的js函数，都在head.jsp页面中     -->
		<div id="sidebar"  >

				


				<ul class="nav nav-list">

					<li class="active" id="fhindex">
					  <a href="<%= request.getContextPath()%>/login/main.do"><i class="icon-dashboard"></i><span>首页</span></a>
					</li>



			<c:forEach items="${rightList}" var="right">
				<c:if test="${right.hasSubRight}">
				<li id="lm${right.id }">
					  <a style="cursor:pointer;" class="dropdown-toggle" >
						<i class="${right.picUrl == 'null' ? 'icon-retweet' : right.picUrl}"></i>
						<span>${right.rightname }</span>
						<b class="arrow icon-angle-down"></b>
					  </a>
					  <ul class="submenu">
							<c:forEach items="${right.subRight}" var="sub">
								
								<c:choose>
									<c:when test="${not empty sub.RIGHTURL}">
									<li id="z${sub.ID }">
									<a style="cursor:pointer;" target="mainFrame"  onclick="siMenu('z${sub.ID }','lm','${sub.RIGHTNAME }','${sub.RIGHTURL }','${sub.TREEURL }')"><i class="icon-double-angle-right"></i>${sub.RIGHTNAME }</a></li>
									</c:when>
									
								</c:choose>
								
							</c:forEach>
				  		</ul>
				</li>
				</c:if>
			</c:forEach>

				</ul><!--/.nav-list-->

				<div id="sidebar-collapse"><i class="icon-double-angle-left"></i></div>
				<input type="hidden" value="" id="test" />
			</div><!--/#sidebar-->
			
		

