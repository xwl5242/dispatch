
<div class="navbar navbar-inverse" style="position:relative;z-index:99;" >
		  <div class="navbar-inner">
		   <div class="container-fluid" >
			  <a class="brand" href="#"><small><i class="icon-leaf"></i> 能源管控中心</small> </a>
			  
			 
			  <ul class="nav ace-nav pull-right">
					
					<c:forEach items="${sysList}" var="sys">
					      <li class="grey">
								<a href="javascript:showBase('${sys.ID }','${sys.RIGHTURL }')" >
									
									<i class="${sys.PICURL == null ? 'icon-tasks' : sys.PICURL}"></i>
									<span class="badge">${sys.RIGHTNAME }</span>
								</a>
					      </li>
					</c:forEach>
					
					<li class="light-blue user-profile">
						<a class="user-menu dropdown-toggle" href="javascript:alert('预留功能,待开发');" data-toggle="dropdown">
							<img alt="FH" src="static/avatars/user.jpg" class="nav-user-photo" />
							<span id="user_info">
								
							</span>
							<i class="icon-caret-down"></i>
						</a>
						<ul id="user_menu" class="pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-closer" style="width:210px;">
							    <div class="frame-user-heading">
										<div class="frame-user-heading-main">
											<div style="margin-left:15px;">登陆账号:${sessionScope.ECCUSER.loginName}</div>
											<div style="margin-left:15px;">员工:${sessionScope.ECCEMP.name}&nbsp;&nbsp;&nbsp;&nbsp;部门:${sessionScope.ECCORG.orgName}</div>
											
										</div>
										
								</div>
							 <li><a onclick="updatePassword();" style="cursor:pointer;"><i class="icon-user"></i> 修改密码</a></li>
							 <li id="productCode"><a onclick="productCode();" style="cursor:pointer;"><i class="icon-cogs"></i> 代码生成</a></li>
							 <li id="navigation"><a onclick="navigation();" style="cursor:pointer;"><i class="icon-globe"></i> 收藏夹维护</a></li>
							 <li id="navigation"><a onclick="dataController();" style="cursor:pointer;"><i class="icon-list-alt"></i> 自定义查询</a></li>
							
							
							<!--<li class="divider"></li> -->
							<li><a onclick="exit()"><i class="icon-off"></i> 退出</a></li>
						</ul>
					</li>
			  </ul><!--/.ace-nav-->
		   </div><!--/.container-fluid-->
		  </div><!--/.navbar-inner-->
		</div><!--/.navbar-->
	
	
		<!--引入属于此页面的js -->
		<script type="text/javascript" src="static/js/myjs/head.js"></script>
		 <script type="text/javascript">
		    function updatePassword(){
				baseDialog("修改密码",'<%= request.getContextPath()%>/jsp/sys/updatePassWord.jsp',240,240);
			}
		    
		    function navigation(){
		    	siMenu("z999999","tab2_index1","收藏夹维护","/navigation/listNavigation.do","")
		    }
		    
		    function dataController(){
		    	siMenu("z999999","tab3_index1","自定义查询","/dataright/customRightSearch.do","")
		    	
		    }
		    function showUserBase(){
		    	baseDialog("用户基础信息",'<%= request.getContextPath()%>/jsp/sys/baseUser.jsp',240,240);
		    }
         </script>
         <style>
			.frame-user-heading {
			  font-family: 'MicroSoft Yahei',Arial,SimSun,Helvetica,sans-serif;
			  display: table;
			  width: 100%;
			  height: 60px;
			  line-height:30px;
			  color:#5e6a72;
			  background-color: #f1f3f4;
			  border-radius: 5px 5px 0 0;
			  border-bottom: 1px solid #ddd;
			  
			}
		</style>
		
