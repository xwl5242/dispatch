<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
	<link type="text/css" rel="stylesheet" href="<%=path %>/static/main/css/common.css" /> 
    <script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
	<style type="text/css">
		 .sjwd_cont	{ height:320px; width:100%; background:#fff; border:1px solid #e4e6eb; box-sizing: border-box;}
		 .box	{ width:100%; margin-top:1%;} 
		 .box > div	{ background:#fff; border:1px solid #aecfe7; float:left; height:100%; width:48.5%; margin-left:1%; box-sizing: border-box; margin-bottom:1%; text-align:center; }
		 .box2 > div	{ background:#fff; border:1px solid #aecfe7; float:left; height:100%; width:98%; margin-left:1%; box-sizing: border-box; margin-bottom:1%; text-align:center; }
		 .box_tit	{ font-size:14px; color:#1169b6; position: absolute; margin-left: 10px; margin-top: 5px;}
	</style>
	
	<!-- 引入bootstrapTable开始 -->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/plugins/bootstrap_table/bootstrap-table.css">
    <script src="<%=request.getContextPath() %>/plugins/bootstrap_table/bootstrap-table.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/bootstrap_table/extensions/export/bootstrap-table-export.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/bootstrap_table/locale/bootstrap-table-zh-CN.js"></script>
    <!-- 引入bootstrapTable结束 -->
</head>
<body style="background:#b6d1ed;" >
<div>
    <table height="20px;">
        <tr>
            <td class="tbl_td_label" width="10%">日期：</td>  
			<td  width="15%">
				<input id="weatherDate" name="weatherDate" class="easyui-datebox" style="width: 155px" data-options="editable:false"/>
			</td>
			<td colspan="12" width="25%"> 
			   <a id="searchLineTrend" onclick='searchWeather();' class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&nbsp;
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
        	<div class="box_tit">24小时内气温趋势</div>
            <div class="sjwd_cont" id="temptrend"></div>
	    	<div class="box_tit">24小时内气象参数</div>
	        <div class="sjwd_cont" style="padding-top: 35px">
	        	<table id="dg" data-id-field="id" data-page-list="[10, 25, 50, 100]"> </table>
	     	</div>
	     </div>
    </div>
<script type="text/javascript">
$(document).ready(function(){
	var ww = parent.$("#page").width()-35;
	var hh = parent.$("#page").height()-50;
	$(document.body).height(hh);
	var divH = hh/2;
	$(".box > div").height(divH);
	$(".box > div").width(ww);
	$(".sjwd_cont").height(divH);
	$("#temptrend").width(ww);
	
	$("#dg").height(divH);
	$("#dg").width(ww);
	
	$('#weatherDate').datebox('setText',getDate(0));
	renderEcharts();
	
	var $table = $('#dg'),
    selections = [];
    $table.bootstrapTable({
        height: divH-60,
        method: 'post',
        url: "<%=path %>/runRecord/weatherJson.do",
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        striped: true,
        pagination: false,
        pageSize: 24,
        pageNumber: 1,
//         pageList: [10, 25, 50, 100], 
        queryParams: queryParams,
//         showColumns: true, //不显示下拉框（选择显示的列）
        sidePagination: "server", //服务端请求
        responseHandler: responseHandler,
        columns: [
            {
                title: '时间',
                field: 'hh',
                align: 'center'
            }, {
                title: '风速',
                field: 'fs',
                align: 'center'
            }, {
                title: '风向',
                field: 'fx',
                align: 'center'
            }, {
                title: '湿度',
                field: 'sd',
                align: 'center'
            }, {
                title: '温度',
                field: 'wd',
                align: 'center'
            }, {
                title: 'PM2.5',
                field: 'pm',
                align: 'center'
            }
        ]
    });
});

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
	         var temptrend = ec.init(document.getElementById('temptrend'),theme);
		$.post('<%=path %>/runRecord/avgTemp.do',{
			curDate:$('#weatherDate').datebox('getValue')
		},function(data){
			var mytemptrend = {
					tooltip : {
		       	        trigger: 'axis'
		       	    },
		       	    legend: {
		       	        data:['平均气温']
		       	    },
		       	    calculable : true,
		       	    xAxis : [
		       	        {
		       	            type : 'category',
		       	            boundaryGap : false,
		       	            data : [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23],
		       	     		axisLabel : {
			                	formatter: '{value} 时'
			            	}
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
		       	            data:data.y,
		       	            itemStyle:{normal:{label:{show:true}}},
		       	            markLine : {
		       	                data : [
		       	                    {type : 'average', name : '平均值'}
		       	                ]
		       	            }
		       	        }
		       	    ]
				};
			temptrend.setOption(mytemptrend);
		},'json');	 
	});
}

function responseHandler(res) {
    return {
        "rows": res.rows,
        "total": res.total
    };
}
function queryParams(params) {
    return {
        rows: this.pageSize,
        page: this.pageNumber,
        curDate:$('#weatherDate').datebox('getValue'),
    };
}
function searchWeather(){
	renderEcharts();
	$('#dg').bootstrapTable('refresh');
}
function formClear(){
	$('#weatherDate').datebox('setValue','');
	$('#weatherDate').datebox('setText','');
}
function getDate(lt) {
		var longtime = new Date().getTime()-lt;
		var date = new Date(longtime);
		var seperator1 = "-";
		var month = date.getMonth() + 1;
		var strDate = date.getDate();
		if (month >= 1 && month <= 9) {
			month = "0" + month;
		}
		if (strDate >= 0 && strDate <= 9) {
			strDate = "0" + strDate;
		}
		var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
		return currentdate;
	}
</script>
</body>
</html>