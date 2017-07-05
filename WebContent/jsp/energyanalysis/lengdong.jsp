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
<link href="<%=request.getContextPath() %>/common/css/other.css" rel="stylesheet" type="text/css" />
<title></title>
    <script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
	 <link type="text/css" rel="stylesheet" href="<%=path %>/static/main/css/common.css" /> 
	 <style type="text/css">
		 .sjwd_hea	{ width:100%; height:40px; background:#f5f5f5;}
		 .sjwd_hea > div	{ float:left; color:#fff; font-size:16px; line-height:40px; margin-right:10px;}
		 .sjwd_cont	{ height:320px; width:100%; background:#fff; border:1px solid #e4e6eb; box-sizing: border-box;}
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
            <td class="tbl_td_label" width="10%">采暖季：</td>  
			<td  width="15%">
				<select id='node' name='node' class='easyui-combobox'>
					<option value='1'>2016-11-16~2017-03-20</option>
				</select>
			</td>
			<td colspan="12" width="25%"> 
			   <a id="searchLineTrend" onclick='searchLineTrend();' class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&nbsp;
			   <a id="clearLineTrendSearch"  onclick='formClear();' class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a>
			</td> 
			<td class="tbl_td_label" width="10%"></td>  
			<td class="tbl_td_label" width="10%"></td>  
        </tr>
        <tr>
        </tr>
    </table>
</div>
	<div id="parentId" class="box">
       <div>
        	<div class="box_tit">水</div>
             <div class="sjwd_cont" id="weter"> </div>
        </div>
        <div>
        	<div class="box_tit">电</div>
             <div class="sjwd_cont" id="power"> </div>
        </div>
    </div>
	<div id="parentId" class="box2">
        <div >
        	<div class="box_tit">气</div>
             <div class="sjwd_cont" id="gas"> </div>
        </div>
    </div>
<script type="text/javascript">
$(document).ready(function(){
	var ww = parent.parent.$('#mainFrame').width()-60;
	var hh = parent.$("#page").height()-50;
	$(document.body).height(hh);
	var divH = hh*0.96/2;
	$("#parentId > div").height(divH);
	$(".sjwd_cont").height(divH);
	$("#weter").width(ww/2);
	$("#power").width(ww/2);
	$("#gas").width(ww);
});
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
	         var myWeter = ec.init(document.getElementById('weter'),theme);
	         var myPower = ec.init(document.getElementById('power'),theme);
	         var myGas = ec.init(document.getElementById('gas'),theme);
			 var water = {
					 tooltip : {
					      trigger: 'axis'
					    },
					  legend: {
					    data:['本季水单耗(t/万㎡)','上季水单耗(t/万㎡)']
					  },
					  calculable : true,
					  xAxis : [
					    {
					      type : 'category',
					      data : ['冷冻站1','冷冻站2','冷冻站3','冷冻站4']
					    }
					  ],
					  yAxis : [
					    {
					      type : 'value'
					    }
					  ],
					  series : [
					    {
					      name:'本季水单耗(t/万㎡)',
					      type:'bar',
					      itemStyle: {
					        normal: {
					          label: {
					            show: true,
					            formatter: '{c}'
					          }
					        }
					      },
					      data:[210,342,321,312]
					    },
					    {
					      name:'上季水单耗(t/万㎡)',
					      type:'bar',
					      itemStyle: {
					        normal: {
					          label: {
					            show: true,
					            formatter: '{c}'
					          }
					        }
					      },
					      data:[192,213,210,123]
					    }
					  ]
					};
			 var power = {
					 tooltip : {
					      trigger: 'axis'
					    },
					  legend: {
					    data:['本季水单耗(t/万㎡)','上季水单耗(t/万㎡)']
					  },
					  calculable : true,
					  xAxis : [
					    {
					      type : 'category',
					      data : ['冷冻站1','冷冻站2','冷冻站3','冷冻站4']
					    }
					  ],
					  yAxis : [
					    {
					      type : 'value'
					    }
					  ],
					  series : [
					    {
					      name:'本季水单耗(t/万㎡)',
					      type:'bar',
					      itemStyle: {
					        normal: {
					          label: {
					            show: true,
					            formatter: '{c}'
					          }
					        }
					      },
					      data:[210,342,321,312]
					    },
					    {
					      name:'上季水单耗(t/万㎡)',
					      type:'bar',
					      itemStyle: {
					        normal: {
					          label: {
					            show: true,
					            formatter: '{c}'
					          }
					        }
					      },
					      data:[192,213,210,123]
					    }
					  ]
					};
			 var gas = {
					 tooltip : {
					      trigger: 'axis'
					    },
					  legend: {
					    data:['本季水单耗(t/万㎡)','上季水单耗(t/万㎡)']
					  },
					  calculable : true,
					  xAxis : [
					    {
					      type : 'category',
					      data : ['冷冻站1','冷冻站2','冷冻站3','冷冻站4']
					    }
					  ],
					  yAxis : [
					    {
					      type : 'value'
					    }
					  ],
					  series : [
					    {
					      name:'本季水单耗(t/万㎡)',
					      type:'bar',
					      itemStyle: {
					        normal: {
					          label: {
					            show: true,
					            formatter: '{c}'
					          }
					        }
					      },
					      data:[210,342,321,312]
					    },
					    {
					      name:'上季水单耗(t/万㎡)',
					      type:'bar',
					      itemStyle: {
					        normal: {
					          label: {
					            show: true,
					            formatter: '{c}'
					          }
					        }
					      },
					      data:[192,213,210,123]
					    }
					  ]
					};
			 myWeter.setOption(water);
			 myPower.setOption(power);
			 myGas.setOption(gas);
	    }
	);
</script>
</body>
</html>