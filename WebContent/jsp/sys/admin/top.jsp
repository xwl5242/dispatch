	<meta charset="utf-8" />
	<!--<title>${pd.SYSNAME}</title>-->
	<title>东环广场智能控制系统</title>
	<meta name="description" content="overview & stats" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
	<!-- basic styles -->
	<link href="static/css/bootstrap.min.css" rel="stylesheet" />
	<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
	<link rel="stylesheet" href="static/css/font-awesome.min.css" />
	<!-- page specific plugin styles -->
	<!-- 下拉框-->
	<link rel="stylesheet" href="static/css/chosen.css" />
	<!-- ace styles -->
	 <link rel="stylesheet" href="static/css/ace.min.css" />
	<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
	<link rel="stylesheet" href="static/css/ace-skins.min.css" /> 
	<link rel="stylesheet" href="static/css/search-custom.css" />
	<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
	<link rel="stylesheet" href="static/css/datepicker.css" /><!-- 日期框 -->
	<!--引入弹窗组件start-->
	<script type="text/javascript" src="plugins/attention/zDialog/zDrag.js"></script>
	<script type="text/javascript" src="plugins/attention/zDialog/zDialog.js"></script>
	<!--引入弹窗组件end-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	
	<link rel="stylesheet" href="plugins/toastr/toastr.min.css" />  
	<script type="text/javascript" src="plugins/toastr/toastr.min.js"></script>
	
	<link rel="stylesheet" href="<%=path %>/plugins/zTree/3.5/zTreeStyle.css" type="text/css">
	<script src="<%=path %>/plugins/zTree/3.5/jquery.ztree.core-3.5.js"></script>
	<script src="<%=path %>/plugins/zTree/3.5/jquery.ztree.excheck-3.5.js"></script>
	
	<!-- 引入自定义工具js开始 -->
	<script type="text/javascript" src="static/js/myjs/customTool.js"> </script>
	<!-- 引入自定义工具js结束 -->
	
	
	
	<!-- 引入bootstrapTable开始 -->
	<link rel="stylesheet" href="plugins/bootstrap_table/bootstrap-table.css">
	<script src="plugins/bootstrap_table/bootstrap-table.js"></script>
    <script src="plugins/bootstrap_table/extensions/export/bootstrap-table-export.js"></script>
    <script src="plugins/bootstrap_table/locale/bootstrap-table-zh-CN.js"></script>
    <!-- 引入bootstrapTable结束 -->

	<!--[if lt IE 9]>
        <script src="static/js/ie8/html5shiv.min.js"></script>
        <script src="static/js/ie8/respond.min.js"></script>
    <![endif]-->
	
	
	<script type="text/javascript">
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
	
	
	/**
	creatHtmlSelect(id,holder,data,selectId)
 	动态生成Select
 	参数：
 		id 写入现有空的select 的id （不可为空）
    holder 默认展示文字
      data 结果集 （不可为空）  其中结果集要求  key必须为    VALUE和TEXT 例如: {VALUE:"1",TEXT:"热水"}
  selectId 需要选中的value值
*/
function creatHtmlSelect(id,holder,data,selectId,changefn,isdisabled,title){
	 var _part=$("#"+id).parent();
    var _selectDom='<select '+ isdisabled +' class="chzn-select" name="'+id+'" id="'+id+'" data-placeholder="'+holder+'" style="vertical-align:top;"> ';
    var _optStr="";
    if(holder){
    	_optStr+="<option value=''></option>";
    }else{
    	_optStr+="<option value=''>请选择</option>";
    }
    if(selectId&&selectId!=""){
   	   for(var i=0;i<data.length;i++){  
              var xValue=data[i].VALUE;    
              var xText=data[i].TEXT;  
              if(xValue==selectId){ 
                  _optStr+="<option value='"+xValue+"' selected=true >"+xText+"</option>";

              }else{   
                  _optStr+="<option value='"+xValue+"'>"+xText+"</option>";

              }
         }
    }else{   
   	   for(var i=0;i<data.length;i++){  
              var xValue=data[i].VALUE;    
              var xText=data[i].TEXT;  
              _optStr+="<option value='"+xValue+"'>"+xText+"</option>";
         }
    }  
    _selectDom+=_optStr+"</select>"; 
    $(_part).html("");      
    $(_part).append(_selectDom);   
    if(changefn != undefined)$("#"+id).change(function(){changefn()}); 
    $("#"+id).chosen();
 }
     
  $(".Wdate").each(function(){
		if($(this).width() < 100){
			$(this).css("width","100px");
		}
	});
  
  $(function(){
	  $('td.divmatnrdesc').each(function(){
    	  var length=$(this).html().length;
    	  var maxLength=$(this).attr("maxLength");
	      if (length> maxLength) {
	    	  var tdwidth=maxLength*13+40;
	    	  $(this).css("width",tdwidth+"px");    
	    	   var  _text=$(this).text(); 
	    	   
	    	  $(this).live('mouseover', function(e) {
			        _tooltip = "<div id='tooltipdiv' style='max-width:600px;'> "+_text+"</div>";  
			           $("body").append(_tooltip);  
			           $("#tooltipdiv").show();
			        $("#tooltipdiv").css({
			        "top": (e.pageY+10) + "px",
			        "left":  (e.pageX +10) + "px"
			       }).show("fast");    
			      });
			      
			      $(this).live('mouseout', function(e) {
			       $("#tooltipdiv").remove();
			      });
			      
			      
			      $(this).live('mousemove', function(e) {
			       $("#tooltipdiv")
			       .css({
			        "top": (e.pageY+10 ) + "px",
			        "left":  (e.pageX+10)  + "px"
			       }).show();    
			      });
			      $(this).html($(this).html().substring(0,maxLength)+"..."); 
 		  }
      }); 

  })
   
  
        function queryParams(params) {
	    	return {rows: params.limit,page: params.pageNumber};
		} 
	    function getHeight() {
	        return $(window).height() - 100;
	    }
	    
	    
	   
	
	</script>
	<style>
	.divmatnrdesc{
	    overflow:hidden;
	    white-space:nowrap;
	    text-overflow:ellipsis;
	    
 	}
 	.pie_font {word-wrap: break-word; width:240px; font-size: 14px; position: relative; top: -40px;text-align:center;}
 	
	
	</style>