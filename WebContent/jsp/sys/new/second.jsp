<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en" style="height: 112px; ">

	<head>
	<base href="<%=basePath%>">
	
	<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
	<script type="text/javascript" src="plugins/tab/js/framework.js"></script>
	<link href="plugins/tab/css/import_basic.css" rel="stylesheet" type="text/css"/>
	
	<link  rel="stylesheet" type="text/css" id="skin" prePath="plugins/tab/" /><!--默认相对于根目录路径为../，可添加prePath属性自定义相对路径，如prePath="<%=request.getContextPath()%>"-->
	<script type="text/javascript" charset="utf-8" src="plugins/tab/js/tab.js"></script>
	<style type="text/css">
	    .menu_box{
	        position:absolute;
	        background:#f5f5f5;
	        border:1px solid #979797;
	        z-index:999;
	    }
	    .menu_shadow {
			  z-index: 1000;
			  FILTER: progid:DXImageTransform.Microsoft.Blur(pixelRadius=2,MakeShadow=false,ShadowOpacity=0.2);
			  BACKGROUND: #ddd;
			  -moz-border-radius: 5px;
			  -webkit-border-radius: 5px;
			  -moz-box-shadow: 2px 2px 3px rgba(0, 0, 0, 0.2);
			  -webkit-box-shadow: 2px 2px 3px rgba(0, 0, 0, 0.2);
			  position: absolute;
       }
       .menu_item{
       		  padding-left:40px;
       		  
       }
       
	    .menu_line{
	    		background: url('plugins/tab/image/menu-line-y.gif') repeat-y;
				width: 2px;
				height:95px;
				position: absolute;
				left: 28px;
				top: 1px;
				z-index: 101;
	    }
	    .menu_text_over{
	       		background:#e9eff7;
	            border:1px solid #aecff7;
	            z-index: 9999;
	            
	            radius:2px;
	    }
	    
.l-tab-links-left{position:absolute; top:5px; left:0; width:17px; height:23px; overflow:hidden; background:url(plugins/tab/image/tabs-tools.gif) 0px 0px; z-index:13; cursor:pointer;}
.l-tab-links-right{ position:absolute; top:5px; right:0;width:17px; height:23px; overflow:hidden;background:url(plugins/tab/image/tabs-tools.gif) -51px 0px;z-index:13; cursor:pointer;}
.tab_more{position:absolute; top:5px; left:109px; width:17px; height:23px;}
	</style>
	<script type="text/javascript">
	  var width=0;
	  var tabListOffSetOrgin=0;
	  function tabEventShow(mid){
		  
		  var table=$("#"+mid);
		  var thisTabWidth=0;
		 
		  $.each(table,function(index,obj){
			  thisTabWidth=obj.offsetWidth;
			  
			  width=obj.offsetLeft+thisTabWidth;
			  
		  })
		  var widthAll=$(window).width();
		  
		  if(widthAll-width-30<0){
			  $(".l-tab-links-right").show();
			  $(".l-tab-links-left").show();
			  $(".benma_ui_tab").animate({ left: widthAll-width-30 });
			  tabListOffSetOrgin=widthAll-width-30;
		  }
		  
	  }
	  
	  function tabHideBase(){
		  $(".l-tab-links-right").hide();
		  $(".l-tab-links-left").hide();
		  $(".benma_ui_tab").animate({ left: 0});
	  }
	  
	  function nextTab(){
		  
		  var tabList=$(".benma_ui_tab");
		  var tabListOffSet=0;
		  $.each(tabList,function(index,obj){
			  tabListOffSet=obj.offsetLeft;
		  })
		  var offsetThisClick=0;
		  if(tabListOffSet-100<tabListOffSetOrgin){
			  offsetThisClick=tabListOffSetOrgin;
		  }else{
			  offsetThisClick=tabListOffSet-100;
		  }
		  $(".benma_ui_tab").animate({ left: offsetThisClick});
	  }
	  function preTab(){
		  
		  var tabList=$(".benma_ui_tab");
		  var tabListOffSet=0;
		  $.each(tabList,function(index,obj){
			  tabListOffSet=obj.offsetLeft;
		  })
		  var offsetThisClick=0;
		  if(tabListOffSet+100>0){
			  offsetThisClick=0;
		  }else{
			  offsetThisClick=tabListOffSet+100;
		  }
		  $(".benma_ui_tab").animate({ left: offsetThisClick});
	  }
	  
	  function tabHideByOneClose(mid){
		 
		  var tabList=$(".benma_ui_tab");
		  var tabListOffSet=0;
		  $.each(tabList,function(index,obj){
			  tabListOffSet=obj.offsetLeft;
		  })
		  var tab=$("#"+mid);
		  var thisTabWidth=0;
		  $.each(tab,function(index,obj){
			  thisTabWidth=obj.offsetWidth;
		  })
		  
		  if(tabListOffSet+thisTabWidth<0){
			  $(".benma_ui_tab").animate({ left: tabListOffSet+thisTabWidth});
		  }else{
			  $(".l-tab-links-right").hide();
			  $(".l-tab-links-left").hide();
			  $(".benma_ui_tab").animate({ left: 0});
		  }
		  
	  }
	 
	  
	</script>
	</head>
	
	
<body style="overflow-x:hidden;overflow-y:hidden; ">
<div id="tab_menu1" style="height:30px;background: #d8e4f3;" >
  <div class="l-tab-links-left" style="display:none" onclick="preTab()"><span></span></div>
  <div class="l-tab-links-right" style="display:none" onclick="nextTab()"><span></span></div>
  <div class="tab_more"></div>
</div>

<div id="tab_item1" class="menu_box" style="display:none;width:100px;height:96px;">
		<div class="menu_line"></div>
        <div class="menu_item" id="mm-refresh1"  onclick="refreshTab()" >
              	<div class="menu_item_text">刷新</div>
        </div>
		<div class="menu_item" id="mm-tabclose1" onclick="closeTab()">
				<div class="menu_item_text">关闭</div>
		</div>
		<div class="menu_item" id="mm-tabcloseall1" onclick="closeAllTab()">
		    	<div class="menu_item_text"> 关闭全部</div>
		</div>
		<div class="menu_item" id="mm-tabcloseother1" onclick="closeOtherTab()">
		    	<div class="menu_item_text">关闭其他</div>
		</div>
</div>
<div id="tab_shadow1" class="menu_shadow" style="display:none;width:102px;">
</div>
 
<div style="width:100%;margin-top: 5px;">
	<div id="page_seconde" style="width:100%;height:100%;margin-top: 5px;"></div>
</div>	
</body>
<script type="text/javascript">

$(document).ready(function(){
	$(".menu_item").bind("mouseover",function(){
		$(this).addClass("menu_text_over")
	})
	$(".menu_item").bind("mouseout",function(){
		$(this).removeClass("menu_text_over")
	})
})

function show(){
	$("#tab_item1").hide();
}

var tab;
var menu_url="";
var menu_title="";
var menu_id="";

var menu_id_arr=[];

function tabAddHandler(mid,mtitle,murl){
	
	menu_id_arr.push(mid);
	
	var obj=tab.add({
		id :mid,
		title :mtitle,
		url :murl,
		isClosed :true
	});
	tab.update({
		id :mid,
		title :mtitle,
		url :murl,
		isClosed :true
	});
	tab.activate(mid);
	tab.menuItem(mid,mtitle,murl);
	tabEventShow(mid);
}

function refreshTab(){
	tab.update({
		id :menu_id,
		title :menu_title,
		url :menu_url,
		isClosed :false
	});
	tab.activate(menu_id);
	
	tabEventShow(menu_id);
	$("#tab_item1").hide();
    $("#tab_shadow1").hide();
}

function closeTab(){
	tabHideByOneClose(menu_id);
	tab.close(menu_id);
	$("#tab_item1").hide();
    $("#tab_shadow1").hide();
    
}

function closeAllTab(){
	$.each(menu_id_arr,function(index,obj){
		tab.close(obj);
	})
	$("#tab_item1").hide();
    $("#tab_shadow1").hide();
    tabHideBase();
}

function closeOtherTab(){
	$.each(menu_id_arr,function(index,obj){
		if(menu_id!=obj){
			tab.close(obj);
		}
	})
	$("#tab_item1").hide();
    $("#tab_shadow1").hide();
    tabHideBase();
}


function cmainFrameT(){
		var hmainT = document.getElementById("page_seconde");
		var bheightT = document.documentElement.clientHeight;
		hmainT .style.width = '100%';
		hmainT .style.height = (bheightT  - 51) + 'px';
}
cmainFrameT();
window.onresize=function(){  
	cmainFrameT();
};


$(function() {
	 tab = new TabView({
		containerId :'tab_menu1',
		pageid :'page_seconde',
		cid :'tab1',
		position :"top"
		 
	});
	
	 var rightData = JSON.parse('${rightList}');
	 var firstId="";
	 $.each(rightData,function(i,v){
		 var url="";
		 if(v.righturl.indexOf("http:")>-1){
			 url=v.righturl;
		 }else{
			 url= "<%=path%>"+v.righturl;
		 }
		 tab.add( {
				id :'se'+v.id,
				title :v.rightname,
				url :url,
				isClosed :false
			});
		 if(i==0){
			 firstId='se'+v.id;
		 }
		 tabEventShow('se'+v.id);
	 });
	 tab.activate(firstId);
	
	
	
	TabView.prototype.menuItem=function(menuid,title,url){
       	var menu=$("#"+menuid)
		menu.bind("contextmenu",function(){ 
			    var e = event || window.event;
			    $("#tab_item1").css({"top":e.pageY,"left":e.pageX})
			    $("#tab_shadow1").css({"top":e.pageY,"left":e.pageX})
		        $("#tab_item1").show();
			    $("#tab_shadow1").show();
			    
			    menu_url=url;
			    menu_title=title;
			    menu_id=menuid;
			    return false;
		  });
	}
	tabEventShow(menu_id);
	
});


</script>
</html>

