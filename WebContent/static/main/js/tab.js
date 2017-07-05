(function($){
   $.doTab = function(tab)  {
	   if(tab.hasClass("active")){
		return ;
	   }
	   $(".tab.active").removeClass("active");
	   $(".tab-data.active").removeClass("active");
	   
	   tab.addClass("active");
	   
	   var id = tab.attr("for");
	   $(".tab-data#"+id).addClass("active");
	   
   }
})(jQuery);
$(function(){
	$(".tab").bind("click",function(){
		var _self = $(this);
		$.doTab(_self);
	});
	
})