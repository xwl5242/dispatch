var locat = (window.location+'').split('/'); 
$(function(){if('main'== locat[3]){locat =  locat[0]+'//'+locat[2];}else{locat =  locat[0]+'//'+locat[2]+'/'+locat[3];};});


//菜单状态切换
var fmid = "fhindex";
var mid = "fhindex";

function siCheckId(id,fid){
	if(id != mid){
		$("#"+mid).removeClass();
		mid = id;
	}
	if(fid != fmid){
		$("#"+fmid).removeClass();
		fmid = fid;
	}
	$("#"+fid).attr("class","active open");
	$("#"+id).attr("class","active");
}

function siTreeMenu(id,fid,MENU_NAME,MENU_URL,TREE_URL){
	siCheckId(id,fid);
	if(MENU_URL.substr(0,1)=="/"){
		MENU_URL=MENU_URL.substr(1,MENU_URL.length);
	}
	if(TREE_URL.substr(0,1)=="/"){
		TREE_URL=TREE_URL.substr(1,TREE_URL.length);
	}
	if(TREE_URL.indexOf("?")!=-1){
		
		top.mainFrame.tabAddHandler(id,MENU_NAME,TREE_URL+"&menuUrl="+MENU_URL);
	}else{
		top.mainFrame.tabAddHandler(id,MENU_NAME,TREE_URL+"?menuUrl="+MENU_URL);
	}
	
	
	if(MENU_URL != "druid/index.html"){
		jzts();
	}
}

function siCommonMenu(id,fid,MENU_NAME,MENU_URL){
	siCheckId(id,fid);
	if(MENU_URL.substr(0,1)=="/"){
		MENU_URL=MENU_URL.substr(1,MENU_URL.length);
	}
	top.mainFrame.tabAddHandler(id,MENU_NAME,MENU_URL);
	
	if(MENU_URL != "druid/index.html"){
		jzts();
	}
}


function siMenu(id,fid,MENU_NAME,MENU_URL,TREE_URL){
	if(TREE_URL==""){
		siCommonMenu(id,fid,MENU_NAME,MENU_URL);
	}else{
		siTreeMenu(id,fid,MENU_NAME,MENU_URL,TREE_URL);
	}
}



var USER_ID;

$(function(){
	/*$.ajax({
		type: "POST",
		url: locat+'/head/getUname.do?tm='+new Date().getTime(),
    	data: encodeURI(""),
		dataType:'json',
		//beforeSend: validateData,
		cache: false,
		success: function(data){
			//alert(data.list.length);
			 $.each(data.list, function(i, list){
				 
				 //登陆者资料
				 $("#user_info").html('<small>Welcome</small> '+list.NAME+'');
				 
				 
				 USER_ID = list.USER_ID;//用户ID
				 hf(list.SKIN);//皮肤
				 
				 if(list.USERNAME != 'admin'){
					 $("#adminmenu").hide();	//隐藏菜单设置
					 $("#adminzidian").hide();	//隐藏数据字典
					 $("#systemset").hide();	//隐藏系统设置
					 $("#productCode").hide();	//隐藏代码生成
				 }
				 
			 });
		}
	});*/
});

function hf(b){
	
	var a=$(document.body);
	a.attr("class",a.hasClass("navbar-fixed")?"navbar-fixed":"");
	if(b!="default"){
		a.addClass(b);
	}if(b=="skin-1"){
		$(".ace-nav > li.grey").addClass("dark");
	}else{
		$(".ace-nav > li.grey").removeClass("dark");
	}
	if(b=="skin-2"){
			$(".ace-nav > li").addClass("no-border margin-1");
			$(".ace-nav > li:not(:last-child)").addClass("white-pink")
			.find('> a > [class*="icon-"]').addClass("pink").end()
			.eq(0).find(".badge").addClass("badge-warning");
	}else{
			$(".ace-nav > li").removeClass("no-border").removeClass("margin-1");
			$(".ace-nav > li:not(:last-child)").removeClass("white-pink")
			.find('> a > [class*="icon-"]').removeClass("pink").end()
			.eq(0).find(".badge").removeClass("badge-warning");
	}
	if(b=="skin-3"){
		$(".ace-nav > li.grey").addClass("red").find(".badge").addClass("badge-yellow");
	}else{
		$(".ace-nav > li.grey").removeClass("red").find(".badge").removeClass("badge-yellow");
	}
}

//修改个人资料
function editUserH(){
	 //jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="个人资料";
	 diag.URL = locat+'/user/goEditU.do?USER_ID='+USER_ID+'&fx=head';
	 diag.Width = 225;
	 diag.Height = 389;
	 diag.CancelEvent = function(){ //关闭事件
		diag.close();
	 };
	 diag.show();
}

//系统设置
function editSys(){
	 //jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="系统设置";
	 diag.URL = locat+'/head/goSystem.do';
	 diag.Width = 600;
	 diag.Height = 596;
	 diag.CancelEvent = function(){ //关闭事件
		diag.close();
	 };
	 diag.show();
}

//代码生成
function productCode(){
	 //jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="代码生成器";
	 diag.URL = locat+'/jsp/sys/admin/productCode.jsp';
	 diag.Width = 800;
	 diag.Height = 450;
	 diag.CancelEvent = function(){ //关闭事件
		hangge();
		diag.close();
	 };
	 diag.show();
}

//数据字典
function zidian(){
	 //jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="数据字典";
	 diag.URL = locat+'/dictionaries.do?PARENT_ID=0';
	 diag.Width = 799;
	 diag.Height = 460;
	 diag.CancelEvent = function(){ //关闭事件
		diag.close();
	 };
	 diag.show();
	 
}

//菜单
function menu(){
	 //jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="菜单编辑";
	 diag.URL = locat+'/menu.do';
	 diag.Width = 720;
	 diag.Height = 390;
	 diag.CancelEvent = function(){ //关闭事件
		diag.close();
	 };
	 diag.show();
	 
}

//切换菜单
function changeMenu(){
	window.location.href=locat+'/main/yes';
}



//清除加载进度
function hangge(){
	$("#jzts").hide();
}

//显示加载进度
function jzts(){
	$("#jzts").show();
}

function showBase(id,url){
	
	$("#rightId").val(id);
	$("#rightUrl").val(url);
	
	var target=document.listForm;
	    target.method="post";
	    target.action=locat+"/login/main.do";
	    target.submit();
	
}



function exit(){
	
	$.ajax({ 
	    type: "post", 
	    url: locat+"/login/removeSession.do?cmd=removeSession",
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




