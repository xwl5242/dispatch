<%@ page language="java"   contentType="text/html;charset=utf-8" %>
<%
	String head_path = request.getContextPath();
	String head_basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + head_path + "/";
%>
<link rel="stylesheet" id='easyuiTheme' type="text/css" href="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/icon.css"> 
<link href="<%= request.getContextPath()%>/common/css/base.css" rel="stylesheet" type="text/css">
<link href="<%= request.getContextPath()%>/common/css/ecc.css" rel="stylesheet" type="text/css">
<link href="<%= request.getContextPath()%>/common/css/table.css" rel="stylesheet" type="text/css">
<link href="<%= request.getContextPath()%>/common/css/atag.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/jquery.edatagrid.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-easyui-1.4.1/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/plugins/json/json2.js"></script>
<script type="text/javascript">
//分页条数控制
var pageNum=20;
//列表高度

var gridHeight=$(window).height()-160;

if(gridHeight<0){
	gridHeight=450;
}

var winTop=($(window).height()-240) * 0.5;
var winLeft=($(window).width()-600) * 0.5;

if(winTop<0){
	
	winTop=140;
}
if(winLeft<0){
	winLeft=340;
}

$.ajaxSetup({ 
		contentType : "application/x-www-form-urlencoded;charset=utf-8", 
		complete : function(XMLHttpRequest, textStatus) { 
			
			if(XMLHttpRequest.status=='5008'||XMLHttpRequest.status=='500'){
				
				window.location.href="<%= request.getContextPath()%>";
			}	      
		}   
	}); 
	
$.fn.serializeObject = function()  
{  
   var o = {};  
   var a = this.serializeArray();  
   $.each(a, function() {  
       if (o[this.name]) {  
           if (!o[this.name].push) {  
               o[this.name] = [o[this.name]];  
           }  
           o[this.name].push(this.value || '');  
       } else {  
           o[this.name] = this.value || '';  
       }  
   });  
   return o;  
};


/**
 * 创建列表右键菜单
 */
var cmenu;
function createColumnMenu(id){
	cmenu = $('<div/>').appendTo('body');
	cmenu.menu({ 
		onClick: function(item){
			if (item.iconCls == 'icon-ok'){
				var myGridMenuSize=$(".icon-ok",cmenu).size();
				if(myGridMenuSize==1){
					 $.messager.alert("警告",'已是最后一列，无法隐藏！','warning'); 
					 return;
				}
				$('#'+id).datagrid('hideColumn', item.name);
				cmenu.menu('setIcon', {
					target: item.target,
					iconCls: 'icon-empty'
				});
			} else {
				$('#'+id).datagrid('showColumn', item.name);
				cmenu.menu('setIcon', {
					target: item.target,
					iconCls: 'icon-ok'
				});
			}
		}
	});
	/**
	 * 动态创建列表右键菜单 菜单选项
	 */
	var fields = $('#'+id).datagrid('getColumnFields');
	for(var i=0; i<fields.length; i++){
		var field = fields[i];
		var columenOption=$('#'+id).datagrid('getColumnOption',field);
		if(!columenOption.checkbox==true){
			var col = $('#'+id).datagrid('getColumnOption', field);
			cmenu.menu('appendItem', {
				text: col.title,
				name: field,
				iconCls: 'icon-ok'
			});       
		}
	}
}



</script>