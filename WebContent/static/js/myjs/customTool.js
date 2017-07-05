

/**自定义弹出窗口start**/

/**
 * 基本弹出窗口
 * @param title 标题
 * @param url   路径
 * @param vwidth 宽度
 * @param vheight 高度
 */
function baseDialog(title,url,vwidth,vheight){
	if(title==undefined){
		title="new window";
	}
	if(url==undefined){
		url="";
	}
	if(vwidth==undefined){
		vwidth=300;
	}
	if(vheight==undefined){
		vheight=300;
	}
	var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title =title
	 diag.URL =url
	 diag.Width = vwidth;
	 diag.Height = vheight;
	 diag.CancelEvent = function(){ //关闭事件
		diag.close();
	 };
	 diag.show();
}





function dialogAdd(title,url,vwidth,vheight,page){
	
	if(title==undefined){
		title="new window";
	}
	if(url==undefined){
		url="";
	}
	if(vwidth==undefined){
		vwidth=500;
	}
	if(vheight==undefined){
		vheight=500;
	}
	
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title =title;
	 diag.URL = url;
	 diag.Width = vwidth;
	 diag.Height = vheight;
	 diag.CancelEvent = function(){ //关闭事件
		 if(page == '0'){
			 setTimeout("self.location=self.location",100);
		 }else{
			 nextPage(page);
		 }
		diag.close();
	 };
	 diag.show();
}

function dialogEdit(title,url,vwidth,vheight,page){
	if(title==undefined){
		title="new window";
	}
	if(url==undefined){
		url="";
	}
	if(vwidth==undefined){
		vwidth=500;
	}
	if(vheight==undefined){
		vheight=500;
	}
	
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title =title;
	 diag.URL = url;
	 diag.Width = vwidth;
	 diag.Height = vheight;
	 diag.CancelEvent = function(){ //关闭事件
		 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin')!=undefined){
			 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
				 nextPage(page);
			}
		 }
		
		diag.close();
	 };
	 diag.show();
}

function nextPage(page){ 
	if(true && document.forms[0]){
		var url = document.forms[0].getAttribute("action");
		if(url.indexOf('?')>-1){
			  url += "&currentPage=";
		}else{
			  url += "?currentPage=";
		}
	    url = url + page + "&showCount=10";
	    document.forms[0].action = url;
	    document.forms[0].submit();
	}else{
		var url = document.location+'';
		if(url.indexOf('?')>-1){
			if(url.indexOf('currentPage')>-1){
				var reg = /currentPage=\d*/g;
				url = url.replace(reg,'currentPage=');
			}else{
				url += "&currentPage=";
			}
		}else{
			url += "?currentPage=";
		}
		url = url + page + "&showCount=10";
		document.location = url;
	}
}

/**自定义弹出窗口结束end**/



/**
 * 基本弹出窗口
 * @param ids       id
 * @param message   提示

 */
function checkInputEmpty(ids,message){
	var bool=true;
	if(ids.length!=message.length){
		alert("提示符过少");
	}
	for(var i=0;i<ids.length;i++){
		if($("#"+ids[i]).val()==""){
			
			$("#"+ids[i]).tips({
				side:3,
	            msg:message[i],
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#"+ids[i]).focus();
			bool=false;
			break;
		}
	}
	
	return bool;
	
}