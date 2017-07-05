
		var initTop=0;
		var index=0;  
		function handle(delta) {
			return;
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
		    
		    
		    if(limitHeight>0){
		    	initTop=0;
		    }
		    
		  //  $("#sidebar").attr("style","margin-top:"+initTop+"px;");
		    
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
				
				mainFrame.tabAddHandler(id,MENU_NAME,TREE_URL+"&menuUrl="+MENU_URL);
			}else{
				mainFrame.tabAddHandler(id,MENU_NAME,TREE_URL+"?menuUrl="+MENU_URL);
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
			mainFrame.tabAddHandler(id,MENU_NAME,MENU_URL);
			
			if(MENU_URL != "druid/index.html"){
				
			}
		}


		function siMenu(id,fid,MENU_NAME,MENU_URL,TREE_URL){
			if(TREE_URL==""){
				siCommonMenu(id,fid,MENU_NAME,MENU_URL);
			}else{
				siTreeMenu(id,fid,MENU_NAME,MENU_URL,TREE_URL);
			}
		}
		
		
		
		function newTabMenu(id,fid,MENU_NAME,MENU_URL,TREE_URL){
			
			siCheckId(id,fid);
			if(MENU_URL.substr(0,1)=="/"){
				MENU_URL=MENU_URL.substr(1,MENU_URL.length);
			}
			if(TREE_URL.substr(0,1)=="/"){
				TREE_URL=TREE_URL.substr(1,TREE_URL.length);
			}
			if(TREE_URL.indexOf("?")!=-1){
				
				window.parent.tabAddHandler(id,MENU_NAME,MENU_URL);
			}else{
				window.parent.tabAddHandler(id,MENU_NAME,MENU_URL);
			}
		}
		
		/**
		toastr.success  成功提示
			   info     消息
			   warning  警告
			   error    错误
		参数   toastr.success('展示信息','标题') 
		//位置
		positionClass
		右上  toast-top-right
		左上 toast-top-left
		右下 toast-bottom-right
		左下 toast-bottom-left
		
		顶部全宽   toast-top-full-width
		底部全宽  toast-bottom-full-width
		顶部居中  toast-top-center
		底部居中 toast-bottom-center
	
	*/
	//toastr默认参数，只允许修改positionClass 
	toastr.options = {
			  "closeButton": true,
			  "debug": false,
			  "progressBar": true,  
			  "positionClass": "toast-top-right",  
			  "onclick": null,
			  "showDuration": "300",
			  "hideDuration": "1000",
			  "timeOut": "5000", 
			  "extendedTimeOut": "1000",
			  "showEasing": "swing",
			  "hideEasing": "linear",
			  "showMethod": "fadeIn",
			  "hideMethod": "fadeOut"
			}; 
	
	
	
	$(function(){	
		//菜单隐藏展开
		var tabs_i=0
		$('.vtitle').click(function(){
			var _self = $(this);
			var j = $('.vtitle').index(_self);
			tabs_i = j;
			//菜单本身就处于激活状态
			//菜单是展开的
			if($('.vtitle em').eq(tabs_i).hasClass('v02')){
				//菜单收起
				$('.vtitle em').eq(tabs_i).removeClass('v02').addClass('v01');
				$('.vcon').eq(tabs_i).slideUp();
				return;
			}
			
			
			var activeMenu = $('menu-active');
			activeMenu.removeClass('menu-active');
			
			$('.vtitle em').each(function(e){
				if(e==tabs_i){
					$('em',_self).removeClass('v01').addClass('v02');
				}else{
					$(this).removeClass('v02').addClass('v01');
				}
			});
			$('.vcon').slideUp().eq(tabs_i).slideDown();
		});
		
		$(".menu li").click(function(){
			if(!$(this).hasClass("menu-active")){
				$(".menu li.menu-active").removeClass("menu-active");
				$(this).addClass("menu-active");
			}
		});
		
		
		
		
		
	});