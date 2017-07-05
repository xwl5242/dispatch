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
	 
	 
	 diag.Title =title
	 diag.URL =url
	 diag.Width = vwidth;
	 diag.Height = vheight;
	 diag.CancelEvent = function(){ //关闭事件
		diag.close();
	 };
	 diag.show();
}