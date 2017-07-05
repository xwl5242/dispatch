$(function() {
	if(typeof($.cookie('menusf')) == "undefined"){
		$("#sidebar").attr("class","menu-min");
		$("#sidebar").attr("style","");
	}else{
		$("#sidebar").attr("class","");
		$("#sidebar").attr("style","height:96%;overflow-y:auto;overflow-x:hidden;")
	}
});

//保存缩放菜单状态
function menusf(){
	
	if(document.getElementsByName('menusf')[0].checked){
		$.cookie('menusf', '', { expires: -1 });
		$("#sidebar").attr("class","menu-min");
		$("#sidebar").attr("style","");
	}else{
		$.cookie('menusf', 'ok');
		$("#sidebar").attr("class","");
		$("#sidebar").attr("style","height:96%;overflow-y:auto;overflow-x:hidden;")
	}
}
