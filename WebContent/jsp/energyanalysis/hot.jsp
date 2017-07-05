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
    <title>Insert title here</title>
    <!-- ECharts单文件引入 -->
    <script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
    <!-- 引入bootstrapTable开始 -->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/plugins/bootstrap_table/bootstrap-table.css">
    <script src="<%=request.getContextPath() %>/plugins/bootstrap_table/bootstrap-table.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/bootstrap_table/extensions/export/bootstrap-table-export.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/bootstrap_table/locale/bootstrap-table-zh-CN.js"></script>
    <!-- 引入bootstrapTable结束 -->
    <link type="text/css" rel="stylesheet" href="<%=path %>/static/main/css/common.css"/>
</head>
<body>
<div class="znrw_box">
    <div>
    	<!-- 查询条件 -->
        <table align="center" height="10px;">
        	<tr>
                <td class="tbl_td_label" width="10%">换热站：</td>  
				<td  width="15%">
					<select id='node' name='node' class='easyui-combobox'>
						<option value='1'>换热站1</option>
						<option value='2'>换热站2</option>
						<option value='3'>换热站3</option>
					</select>
				</td>
                <td class="tbl_td_label" width="10%">开始日期：</td>  
				<td  width="15%">
					<input id='startdate' name="startdate" class="easyui-datetimebox" /> 
				</td>
				<td class="tbl_td_label" width="10%">结束日期：</td>
				<td  width="15%" >
					<input id='enddate' name="enddate" class="easyui-datetimebox" /> 
				</td>
				<td colspan="12" width="25%"> 
				   <a id="searchLineTrend" onclick='searchLineTrend();' class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&nbsp;
				   <a id="clearLineTrendSearch"  onclick='formClear();' class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a>
				</td> 
			</tr>
        </table>
    </div>
    <!-- 能源成本分布 -->
    <div class="sjwd">
        <div class="sjwd_hea">
            <div class="bingtu_icon"></div>
            <div> 能源成本分布</div>
        </div>
        <div class="sjwd_cont" id="hotId">
        </div>
    </div>
    <!-- 供热与温度曲线图 -->
    <div class="wdqs">
        <div class="sjwd_hea">
            <div class=" sjwd_icon"></div>
            <div> 供热与温度曲线图</div>
        </div>
        <div class="sjwd_cont" id="main">
        </div>
    </div>
    <!-- 换热站各单耗 -->
    <div class="sjwd">
        <div class="sjwd_hea">
            <div class="jksj_icon"></div>
            <div> 换热站各单耗</div>
        </div>
        <div>
            <div style="width: 100%px;">
                <span style="font-size: 20px">类型选择：</span><span style="font-size: 15px">
		             <input type="radio" name="dh" value="1" onclick="onClickRadio(this.value)" checked="checked">热单耗
		             <input type="radio" name="dh" value="2" onclick="onClickRadio(this.value)">电单耗
		             <input type="radio" name="dh" value="3" onclick="onClickRadio(this.value)">水单耗
		             <input type="radio" name="dh" value="4" onclick="onClickRadio(this.value)">气单耗
	             </span>
            </div>
            <div class="sjwd_cont" id="dhbar"></div>
        </div>
    </div>
    <!-- 换热站各单耗与往年同温度比较 -->
    <div class="wdqs">
        <div class="sjwd_hea">
            <div class="jksj_icon"></div>
            <div> 换热站各单耗与往年同温度比较</div>
        </div>
        <div>
            <div style="width: 100%px;">
                <span style="font-size: 20px">类型选择：</span><span style="font-size: 15px">
	             	<input type="radio" name="wdh" value="1" onclick="wonClickRadio(this.value)" checked="checked">热单耗
	             	<input type="radio" name="wdh" value="2" onclick="wonClickRadio(this.value)">电单耗
	             	<input type="radio" name="wdh" value="3" onclick="wonClickRadio(this.value)">水单耗
	             	<input type="radio" name="wdh" value="4" onclick="wonClickRadio(this.value)">气单耗
	             </span>
            </div>
            <div class="sjwd_cont" id="wdhbar"></div>
        </div>
    </div>
    
    <!-- 换热站统计信息 -->
    <div class="jksj">
        <div class="sjwd_hea">
            <div class="liebiao_icon"></div>
            <div> 换热站统计信息</div>
        </div>
        <div>
            <table id="dg" class="divmatnrdesc" data-id-field="id" data-page-list="[10, 25, 50, 100]"></table>
        </div>
    </div>
    <script type="text/javascript">
        $(document).ready(function () {
        	//获取页面的宽
            var ww = parent.parent.parent.$('#mainFrame').width() - 60;
        	//设置各个模块的宽
            $("#hotId").width(ww/2);
            $("#main").width(ww/2);
            $("#dhbar").width(ww/2);
            $("#wdhbar").width(ww/2);
            //设置默认日期
            $("#startDate").val(getBeforeDate(5));
            $("#endDate").val(getBeforeDate(0));
        });
        
		// 路径配置
	    require.config({
		    paths: {
		        echarts: 'http://echarts.baidu.com/build/dist'
		    }
		});
	   
        //能源成本分布 
	    require(
	        [
	            'echarts',
	            'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
	        ],
	        function (ec) {
	            var myChart = ec.init(document.getElementById('hotId'));
	            var option = {
            	    tooltip : {
            	        trigger: 'item',
            	        formatter: "{a} <br/>{b} : {c} ({d}%)"
            	    },
            	    legend: {
            	        data:['水','电','热']
            	    },
            	    calculable : true,
            	    series : [
            	        {
            	            name:'访问来源',
            	            type:'pie',
            	            radius : '55%',
            	            center: ['50%', '60%'],
            	            data:[
            	                {value:33, name:'水'},
            	                {value:310, name:'电'},
            	                {value:24, name:'热'}
            	            ]
            	        }
            	    ]
            	};
				// 为echarts对象加载数据 
	            myChart.setOption(option); 
			}
	    );	
        
        //供热与温度曲线图
        require(
	        [
	            'echarts',
	            'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
	        ],
	        function (ec) {
	            var myChart1 = ec.init(document.getElementById('main'));
	            var option1 = {
	        		    tooltip: {
	        		        trigger: 'axis'
	        		      },
	        		    legend: {
	        		      data: ['预测供热量', '计划供热量', '实际供热量', '室内平均温度', '平均温度'],
	        		      x: 'left'
	        		    },
	        		    calculable: true,
	        		    xAxis: [
	        		      {
	        		        type: 'category',
	        		        boundaryGap: false,
	        		        splitLine: false,
	        		        data:['2017-02-20','2017-02-21','2017-02-22','2017-02-23','2017-02-24']
	        		      }
	        		    ],
	        		    yAxis: [
	        		      {
	        		        type: 'value',
	        		        name: '热量',
	        		        axisLabel: {formatter: '{value}GJ'}
	        		      },
	        		      {
	        		        type: 'value',
	        		        name: '温度',
	        		        axisLabel: {formatter: '{value}℃'}
	        		      }
	        		    ],
	        		    series: [
	        		      {
	        		        name: '预测供热量',
	        		        type: 'line',
	        		        yAxisIndex: 0,
	        		        data:[210,223,232,212,211]
	        		      },
	        		      {
	        		        name: '计划供热量',
	        		        type: 'line',
	        		        yAxisIndex: 0,
	        		        data: [200,243,244,222,213]
	        		      },
	        		      {
	        		        name: '实际供热量',
	        		        type: 'line',
	        		        yAxisIndex: 0,
	        		        data: [207,244,238,222,214]
	        		      },
	        		      {
	        		        name: '室内平均温度',
	        		        type: 'line',
	        		        yAxisIndex: 1,
	        		        data: [4,3,6,7,2]
	        		      }, {
	        		        name: '平均温度',
	        		        type: 'line',
	        		        yAxisIndex: 1,
	        		        data: [-1,-2,0,-1,-3]
	        		      }
	        		    ]
	        		};
				// 为echarts对象加载数据 
	            myChart1.setOption(option1); 
			}
	    );	
        
        //换热站各单耗
        require(
	        [
	            'echarts',
	            'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
	        ],
	        function (ec) {
	            var myChart2 = ec.init(document.getElementById('dhbar'));
	            var option2 = {
	        		    tooltip : {
	        		        trigger: 'axis'
	        		    },
	        		    legend: {
	        		        data:['热单耗','折算热单耗']
	        		    },
	        		    calculable : true,
	        		    xAxis : [
	        		        {
	        		            type : 'category',
	        		            data:['2017-02-20','2017-02-21','2017-02-22','2017-02-23','2017-02-24']        }
	        		    ],
	        		    yAxis : [
	        		        {
	        		            type : 'value',
	        		            name: '热单耗(1GJ/万㎡)'
	        		        }
	        		    ],
	        		    series : [
	        		        {
	        		            name:'热单耗',
	        		            type:'bar',
	        		            data:[180000,200000,195000,231000,220000],
// 	        		            markPoint : {
// 	        		                data : [
// 	        		                    {type : 'max', name: '最大值'},
// 	        		                    {type : 'min', name: '最小值'}
// 	        		                ]
// 	        		            },
	        		            markLine : {
	        		                data : [
	        		                    {type : 'average', name: '平均值'}
	        		                ]
	        		            }
	        		        },
	        		        {
	        		            name:'折算热单耗',
	        		            type:'bar',
	        		            data:[2300,2530,2680,2420,2660],
// 	        		            markPoint : {
// 	        		                data : [
// 	        		                    {type : 'max', name: '最大值'},
// 	        		                    {type : 'min', name: '最小值'}
// 	        		                ]
// 	        		            },
	        		            markLine : {
	        		                data : [
	        		                    {type : 'average', name : '平均值'}
	        		                ]
	        		            }
	        		        }
	        		    ]
	        		};
				// 为echarts对象加载数据 
	            myChart2.setOption(option2); 
			}
	    );	
        
        //换热站各单耗与往年同温度比较
        require(
	        [
	            'echarts',
	            'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
	        ],
	        function (ec) {
	            var myChart3 = ec.init(document.getElementById('wdhbar'));
	            var option3 = {
	        		    tooltip : {
	        		        trigger: 'axis'
	        		    },
	        		    legend: {
	        		        data:['热单耗','去年同温度热单耗']
	        		    },
	        		    calculable : true,
	        		    xAxis : [
	        		        {
	        		            type : 'category',
	        		            data:['2017-02-20','2017-02-21','2017-02-22','2017-02-23','2017-02-24']        }
	        		    ],
	        		    yAxis : [
	        		        {
	        		            type : 'value',
	        		            name: '热单耗(1GJ/万㎡)'
	        		        }
	        		    ],
	        		    series : [
	        		        {
	        		            name:'热单耗',
	        		            type:'bar',
	        		            data:[180000,200000,195000,231000,220000],
// 	        		            markPoint : {
// 	        		                data : [
// 	        		                    {type : 'max', name: '最大值'},
// 	        		                    {type : 'min', name: '最小值'}
// 	        		                ]
// 	        		            },
	        		            markLine : {
	        		                data : [
	        		                    {type : 'average', name: '平均值'}
	        		                ]
	        		            }
	        		        },
	        		        {
	        		            name:'去年同温度热单耗',
	        		            type:'bar',
	        		            data:[568,583,575,630,600],
// 	        		            markPoint : {
// 	        		                data : [
// 	        		                    {type : 'max', name: '最大值'},
// 	        		                    {type : 'min', name: '最小值'}
// 	        		                ]
// 	        		            },
	        		            markLine : {
	        		                data : [
	        		                    {type : 'average', name : '平均值'}
	        		                ]
	        		            }
	        		        }
	        		    ]
	        		};
				// 为echarts对象加载数据 
	            myChart3.setOption(option3); 
			}
	    );	
        
        //换热站统计信息
        var $table = $('#dg'),
            selections = [];
        $(function () {
            $table.bootstrapTable({
                height: getHeight() - 30,
                method: 'post',
                url: "<%=path %>/dataDaily/getJsonTable.do?type=1",
                dataType: "json",
                striped: true,
                pagination: true,
                queryParamsType: "limit",
                contentType: "application/x-www-form-urlencoded",
                pageSize: 20,
                pageNumber: 1,
                queryParams: queryParams,
                showColumns: false, //不显示下拉框（选择显示的列）
                sidePagination: "server", //服务端请求
                responseHandler: responseHandler,
                columns: [
                    [
                        {
                            title: '热力站名称',
                            field: 'UNITNAME',
                            align: 'center'
                        }, {
                        title: '采暖面积(m²)',
                        field: 'AREA',
                        align: 'center'
                    }, {
                        title: '计划耗热量(GJ)',
                        field: 'HOTPLAN',
                        align: 'center'
                    }, {
                        title: '耗热量(GJ)',
                        field: 'HOTSUMANALYSIS',
                        align: 'center'
                    }, {
                        title: '热单耗(GJ/m²)',
                        field: 'HOTONEANALYSIS',
                        align: 'center'
                    }, {
                        title: '折算热单耗(GJ/m²)',
                        field: 'ZSHOTONEANALYSIS',
                        align: 'center'
                    }, {
                        title: '计划耗电量(kWh)',
                        field: 'POWERPLAN',
                        align: 'center'
                    }, {
                        title: '耗电量(kWh)',
                        field: 'POWERSUMANALYSIS',
                        align: 'center'
                    }, {
                        title: '电单耗(kWh/m²)',
                        field: 'POWERONEANALYSIS',
                        align: 'center'
                    }, {
                        title: '折算电单耗(kWh/m²)',
                        field: 'ZSPOWERONEANALYSIS',
                        align: 'center'
                    }, {
                        title: '计划耗水量(t)',
                        field: 'WATERPLAN',
                        align: 'center'
                    }, {
                        title: '耗水量(t)',
                        field: 'WATERSUMANALYSIS',
                        align: 'center'
                    }, {
                        title: '水单耗(t/万m²)',
                        field: 'WATERONEANALYSIS',
                        align: 'center'
                    }, {
                        title: '折算水单耗(t/万m²)',
                        field: 'ZSWATERONEANALYSIS',
                        align: 'center'
                    }, {
                        title: '平均室温(℃)',
                        field: 'AVGROOMTEMPERATURE',
                        align: 'center'
                    }, {
                        title: '天气平均温度(℃)',
                        field: 'AVGTEAIRMPERATURE',
                        align: 'center'
                    }
                    ]
                ]
            });

        });
        function responseHandler(res) {
            debugger;
            return {
                "rows": res.rows,
                "total": res.total
            };
        }
        function queryParams(params) {
            $("#order").val(params.order);
            $("#sort").val(params.sort);
            return {
                rows: params.limit,
                page: params.pageNumber,
                unitid: $("#orgcode").val(),
                centercode: $("#centercode").val(),
                zType: $("#z_id").val(),
                stationCode: $("#stationCode").val(),
                dateType: $("#date_id").val(),
                startTime: $("#startDate").val(),
                endTime: $("#endDate").val(),
                DATATYPE: $("#DATATYPE").val(),
                order: params.order,
                sort: params.sort
            };
        }
        function getHeight() {
            var hi = $(window).height() - 200;
            return $(window).height() - hi;
        }
        
        //获取今天的前几天
        function getBeforeDate(n) {
            var n = n;
            var d = new Date();
            var year = d.getFullYear();
            var mon = d.getMonth() + 1;
            var day = d.getDate();
            if (day <= n) {
                if (mon > 1) {
                    mon = mon - 1;
                }
                else {
                    year = year - 1;
                    mon = 12;
                }
            }
            d.setDate(d.getDate() - n);
            year = d.getFullYear();
            mon = d.getMonth() + 1;
            day = d.getDate();
            s = year + "-" + (mon < 10 ? ('0' + mon) : mon) + "-" + (day < 10 ? ('0' + day) : day)+" 00:00:00";
            return s;
        }
    </script>
</div>
</body>
</html>