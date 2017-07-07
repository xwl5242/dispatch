<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="/jsp/header.jsp" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">window.jQuery || document.write("<script src='<%=path%>/static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<link href="<%=request.getContextPath() %>/common/css/demo.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath() %>/common/css/other.css" rel="stylesheet" type="text/css" />
<title></title>
    <script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
	 <link type="text/css" rel="stylesheet" href="<%=path %>/static/main/css/common.css" /> 
	 <style type="text/css">
		 .sjwd_cont	{ background:#fff; border:1px solid #e4e6eb; box-sizing: border-box;}
		 .box	{ width:100%; margin-top:1%;} 
		 .box > div	{ background:#fff; border:1px solid #aecfe7; float:left; height:100%; width:48.5%; margin-left:1%; box-sizing: border-box; margin-bottom:1%; text-align:center; }
		 .box2 > div	{ background:#fff; border:1px solid #aecfe7; float:left; height:100%; width:98%; margin-left:1%; box-sizing: border-box; margin-bottom:1%; text-align:center; }
		 .box_tit	{ font-size:14px; color:#1169b6; position: absolute; margin-left: 10px; margin-top: 5px;}
	</style>
</head>
<body style="background:#b6d1ed;" >
<div>
    <table height="20px;">
        <tr>
            <td class="tbl_td_label" width="10%">日期：</td>  
			<td  width="50%">
				<input id='startDate' name="startDate" class="easyui-datebox" style="width: 155px" data-options="editable:false"/>
                 ~ <input id='endDate' name="startDate" class="easyui-datebox" style="width: 155px" data-options="editable:false"/>
            </td>
			<td colspan="12" width="25%"> 
			   <a id="searchLineTrend" onclick='searchWeatherTrend();' class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&nbsp;
			   <a id="clearLineTrendSearch"  onclick='formClear();' class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a>
			</td> 
			<td class="tbl_td_label" width="10%"></td>  
			<td class="tbl_td_label" width="10%"></td>  
        </tr>
        <tr>
        </tr>
    </table>
</div>
	<div class="box">
       <div>
        	<div class="box_tit">气温趋势</div>
             <div class="sjwd_cont" id="avgtemp"> </div>
        </div>
    </div>
<script type="text/javascript">
$(document).ready(function(){
	var ww = parent.parent.$('#mainFrame').width()-60;
	var hh = parent.$("#page").height()-80;
	$(document.body).height(hh);
	$("#avgtemp").height(hh);
	$("#avgtemp").width(ww);
	$(".box > div").height(hh);
	$(".box > div").width(ww);
	renderEcharts();
});
function searchWeatherTrend(){
	renderEcharts();
}
function renderEcharts(){
	//路径配置
	require.config({
	    paths: {
	        echarts: 'http://echarts.baidu.com/build/dist'
	    }
	});
	// 使用
	require(
	    [
	        'echarts',
	        'echarts/theme/macarons',  
	        'echarts/chart/pie',
	        'echarts/chart/bar',
	        'echarts/chart/gauge',
	        'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
	    ],
	    function (ec , theme) {
	         // 基于准备好的dom，初始化echarts图表
	        var avgtemp = ec.init(document.getElementById('avgtemp'),theme);
			 
	        $.post('<%=path %>/runRecord/tempTrend.do',{
	        	startDate:$('#startDate').datebox('getValue'),
	        	endDate:$('#endDate').datebox('getValue')
	        },function(data){
	        	var myavgtemp = {
						tooltip : {
			       	        trigger: 'axis'
			       	    },
			       	    legend: {
			       	        data:['平均气温','最高气温','最低气温']
			       	    },
			       	    calculable : true,
			       	    xAxis : [
			       	        {
			       	            type : 'category',
			       	            boundaryGap : false,
			       	            data : data.yearList
			       	        }
			       	    ],
			       	    yAxis : [
			       	        {
			       	            type : 'value',
			       	            axisLabel : {
			       	                formatter: '{value} 摄氏度'
			       	            }
			       	        }
			       	    ],
			       	    series : [
			       	        {
			       	            name:'平均气温',
			       	            type:'line',
			       	            data:data.avg,
			       	            itemStyle:{normal:{label:{show:true}}}
			       	        },
			       	        {
			       	            name:'最高气温',
			       	            type:'line',
			       	            data:data.max,
			       	            itemStyle:{normal:{label:{show:true}}}
			       	        },
			       	     	{
			       	            name:'最低气温',
			       	            type:'line',
			       	            data:data.min,
			       	            itemStyle:{normal:{label:{show:true}}}
			       	        }
			       	    ]
					};
				avgtemp.setOption(myavgtemp);
	        },'json'); 
	    });
}
function formClear(){
	$('#startDate').datebox('setValue','');
	$('#startDate').datebox('setText','');
	$('#endDate').datebox('setValue','');
	$('#endDate').datebox('setText','');
}
</script>
</body>
</html>