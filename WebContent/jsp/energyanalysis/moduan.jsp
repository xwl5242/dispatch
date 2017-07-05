<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    %>
    <jsp:include page="/jsp/header.jsp"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript">window.jQuery || document.write("<script src='<%=path%>/static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
    <link href="<%=request.getContextPath() %>/common/css/demo.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath() %>/common/css/other.css" rel="stylesheet" type="text/css"/>
    <title></title>
    <!-- ECharts单文件引入 -->
    <script src="http://echarts.baidu.com/build/dist/echarts.js"></script>

    <link type="text/css" rel="stylesheet" href="<%=path %>/static/main/css/common.css"/>
    <style type="text/css">
        .sjwd_hea {
            width: 100%;
            height: 40px;
            background: #f5f5f5;
        }

        .sjwd_hea > div {
            float: left;
            color: #fff;
            font-size: 16px;
            line-height: 40px;
            margin-right: 10px;
        }

        .sjwd_cont {
            height: 400px;
            width: 100%;
            background: #fff;
            border: 1px solid #e4e6eb;
            box-sizing: border-box;
        }
    </style>
</head>
<body style="background:#b6d1ed;">
<div style="margin-top: 10px;">
    <table height="20px;">
        <tr>

            <td class="tbl_td_label" width="3%">采暖季：</td>  
			<td width="12%">
				<select id='node' name='node' class='easyui-combobox'>
					<option value='1'>2016-11-16~2017-03-20</option>
				</select>
			</td>

            <td class="tbl_td_label" width="4%">采暖季开始：</td>
            <td width="10%">
                <input id='startDateCnj' name="startDate" class="easyui-datebox" style="width: 155px" data-options="editable:false"/>
            </td>
            <td class="tbl_td_label" width="4%">采暖季结束：</td>
            <td width="10%">
                <input id='endDateCnj' name="startDate" class="easyui-datebox" style="width: 155px" data-options="editable:false"/>
            </td>

<!--             <td id="TempSource2" class="tbl_td_label" width="28%"> -->
<!--                 <span style="font-size: 15px"> -->
<!--                     <span style="font-size: 15px" id="radio2"> -->
<!-- 					<input type="radio" name="type2" value="0" checked="checked">全部 -->
<!--                     <input type="radio" name="type2" value="1">换热站 -->
<!-- 					<input type="radio" name="type2" value="2">末端系统 -->
<!-- 					</span> -->
<!--                 </span> -->
<!--                 <input id='unit' name="unit" class="easyui-combobox" data-options="editable:false"/> -->
<!--             </td> -->

            <td class="tbl_td_label" width="3%">日期：</td>
            <td width="10%">
                <input id='startDate' name="startDate" class="easyui-datebox" style="width: 155px" data-options="editable:false"/>
            </td>
            <td colspan="12" width="16%"> 
			   <a id="searchLineTrend" onclick='searchLineTrend();' class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&nbsp;
			   <a id="clearLineTrendSearch"  onclick='formClear();' class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a>
			</td> 
        </tr>
        <tr>

        </tr>
    </table>
</div>
<div id="parentId" class="box">
    <div>
        <div class="box_tit">末端系统本季度补水量分析</div>
        <div class="sjwd_cont" id="gBsl1">
        </div>
    </div>
    <div>
        <div class="box_tit">末端系统补水量趋势</div>
        <div class="sjwd_cont" id="glf">
        </div>
    </div>

    <div>
        <div class="box_tit">换热站本季度补水量分析</div>
        <div class="sjwd_cont" id="hBsl1">
        </div>
    </div>
    <div>
        <div class="box_tit">换热站补水量趋势</div>
        <div class="sjwd_cont" id="hrz">
        </div>
    </div>
</div>
<script type="text/javascript">

    $(function () {
        var hh = parent.$("#page").height() + 250;
        $(document.body).height(hh - 300);
        var divH = hh * 0.96 / 3;
        $("#parentId > div").height(divH);
        $(".sjwd_cont").height(divH);

        //路径配置
        require.config({
            paths: {
                echarts: 'http://echarts.baidu.com/build/dist'
            }
        });
        var gBsl1, hBsl1, optGlf, optHrz;
        var myGBsl1, myHBsl1, myGlf, myhrz;
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
            function (ec, theme) {
                // 基于准备好的dom，初始化echarts图表
                myGBsl1 = ec.init(document.getElementById('gBsl1'), theme);
                myHBsl1 = ec.init(document.getElementById('hBsl1'), theme);
                myGlf = ec.init(document.getElementById('glf'), theme);
                myhrz = ec.init(document.getElementById('hrz'), theme);
                gBsl1 = {
               		tooltip: {
               		      trigger: 'axis'
               		    },
               		  legend: {
               		    data: ['']
               		  },
               		  calculable: true,
               		  xAxis: [
               		    {
               		      type: 'category',
               		      data: ['末端系统1','末端系统2','末端系统3','末端系统4']
               		    }
               		  ],
               		  yAxis: [
               		    {
               		      type: 'value'
               		    }
               		  ],
               		  series: [
               		    {
               		      name: '数量',
               		      type: 'bar',
               		      data: [8000,6000,4000,2000],
               		      markLine: {
               		        itemStyle: {
               		          normal: {
               		            color: '#FF2220'
               		          }
               		        },
               		        data: [
               		          {type: 'average', name: '平均值'}
               		        ]
               		      }
               		    }
               		  ]
                };
                hBsl1 = {
                		tooltip: {
                		      trigger: 'axis'
                		    },
                		  legend: {
                		    data: ['']
                		  },
                		  calculable: true,
                		  xAxis: [
                		    {
                		      type: 'category',
                		      data: ['末端系统1','末端系统2','末端系统3','末端系统4','末端系统5']
                		    }
                		  ],
                		  yAxis: [
                		    {
                		      type: 'value'
                		    }
                		  ],
                		  series: [
                		    {
                		      name: '数量',
                		      type: 'bar',
                		      data: [8000,6000,4000,2000,1000],
                		      markLine: {
                		        itemStyle: {
                		          normal: {
                		            color: '#FF2220'
                		          }
                		        },
                		        data: [
                		          {type: 'average', name: '平均值'}
                		        ]
                		      }
                		    }
                		  ]
                };

                optGlf = {
                		tooltip: {
                		      trigger: 'axis'
                		    },
                		  legend: {
                		    data: ['实际值', '计划值', '平均温度'],
                		    x: 'right'
                		  },

                		  calculable: true,
                		  xAxis: [
                		    {
                		      type: 'category',
                		      boundaryGap: false,
                		      splitLine: false,
                		      data:['2017-02-24','2017-02-25','2017-02-26','2017-02-27']
                		    }
                		  ],
                		  yAxis: [
                		    {
                		      type: 'value',
                		      name: '值',
                		      axisLabel: {
                		        formatter: '{value} '
                		      }
                		    }, {
                		      name: '平均温度',
                		      type: 'value',
                		      axisLabel: {
                		        formatter: '{value} '
                		      }
                		    }
                		  ],
                		  series: [
                		    {
                		      name: '实际值',
                		      type: 'line',
                		      data: [300, 300,300,300]
                		    },
                		    {
                		      name: '计划值',
                		      type: 'line',
                		      data: [242, 190,287,205]
                		    },
                		    {
                		      name: '平均温度',
                		      type: 'line',
                		      yAxisIndex: 1,
                		      data: [2, 4,1,3]
                		    }

                		  ]
                };
                optHrz = {

                		tooltip: {
                		      trigger: 'axis'
                		    },
                		  legend: {
                		    data: ['实际值', '计划值', '平均温度'],
                		    x: 'right'
                		  },

                		  calculable: true,
                		  xAxis: [
                		    {
                		      type: 'category',
                		      boundaryGap: false,
                		      splitLine: false,
                		      data:['2017-02-24','2017-02-25','2017-02-26','2017-02-27']
                		    }
                		  ],
                		  yAxis: [
                		    {
                		      type: 'value',
                		      name: '值',
                		      axisLabel: {
                		        formatter: '{value} '
                		      }
                		    }, {
                		      name: '平均温度',
                		      type: 'value',
                		      axisLabel: {
                		        formatter: '{value} '
                		      }
                		    }
                		  ],
                		  series: [
                		    {
                		      name: '实际值',
                		      type: 'line',
                		      data: [300, 300,300,300]
                		    },
                		    {
                		      name: '计划值',
                		      type: 'line',
                		      data: [242, 190,287,205]
                		    },
                		    {
                		      name: '平均温度',
                		      type: 'line',
                		      yAxisIndex: 1,
                		      data: [2, 4,1,3]
                		    }

                		  ]
                };
                myGBsl1.setOption(gBsl1);
                myHBsl1.setOption(hBsl1);
                myGlf.setOption(optGlf);
                myhrz.setOption(optHrz);
            }
        );
    });


</script>
</body>
</html>
