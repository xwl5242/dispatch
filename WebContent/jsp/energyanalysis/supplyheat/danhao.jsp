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
        	<td width="20%"></td> 
            <td class="tbl_td_label" width="5%">年份：</td>  
			<td width="25%">
				<input id="years" style="width: 155px" />
				<div id="sp" style="display: none;">
					<div style="color:#99BBE8;background:#fafafa;padding:5px;">选择年份</div>
					<div style="padding:5px">
						<input type="checkbox" name="2015" value="2015"><span>2015</span><br/>
						<input type="checkbox" name="2016" value="2016"><span>2016</span><br/>
						<input type="checkbox" name="2017" value="2017"><span>2017</span><br/>
					</div>
				</div>
			</td>
            <td class="tbl_td_label" width="5%">周期：</td>
            <td width="25%">
                <input id='startDate' name="startDate" class="easyui-datebox" style="width: 155px" data-options="editable:false"/>
                 ~ <input id='endDate' name="startDate" class="easyui-datebox" style="width: 155px" data-options="editable:false"/>
            </td>
            <td colspan="12" width="20%"> 
			   <a id="searchLineTrend" onclick='searchLineTrend();' class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&nbsp;
			   <a id="clearLineTrendSearch"  onclick='formClear();' class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a>
			</td> 
        </tr>
    </table>
</div>
<div id="parentId" class="box">
    <div>
        <div class="box_tit">热单耗同期比</div>
        <div class="sjwd_cont" id="heat">
        </div>
    </div>
    <div>
        <div class="box_tit">热单耗趋势</div>
        <div class="sjwd_cont" id="heattrend">
        </div>
    </div>

    <div>
        <div class="box_tit">水单耗同期比</div>
        <div class="sjwd_cont" id="water">
        </div>
    </div>
    <div>
        <div class="box_tit">水单耗趋势</div>
        <div class="sjwd_cont" id="watertrend">
        </div>
    </div>
    
    <div>
        <div class="box_tit">电单耗同期比</div>
        <div class="sjwd_cont" id="power">
        </div>
    </div>
    <div>
        <div class="box_tit">电单耗趋势</div>
        <div class="sjwd_cont" id="powertrend">
        </div>
    </div>
</div>
<script type="text/javascript">
	
	function formClear(){
		$('#years').combo('setValue', '');
		$('#years').combo('setText', '');
		$('#startDate').datebox('setValue','');
		$('#endDate').datebox('setValue','');
	}

	//查询条件中的年份设置
	function yearsSet(){
        $('#years').combo({  
        	panelHeight:100,
		    multiple:true  
		}); 
        $('#sp').show();
        $('#sp').appendTo($('#years').combo('panel'));
        $('#sp input').click(function(){
        	var s = '';
			var v = $(this).val();
			var ystring = $('#years').combo('getText');
			if(ystring!=null&&ystring!=''){
				//选中
				if($(this).is(':checked')){
					if(!ystring.match(v)){
						s = $('#years').combo('getText')+'|'+$(this).next('span').text();
					}
				}else{//取消选中
					if(ystring.match(v)){
						if(ystring.indexOf(v)!=0){
							if(ystring.match('|')){
								ystring = ystring.replace('|'+v,'');
							}
						}else{
							if(ystring.match('|')){
								ystring = ystring.replace(v+'|','');
							}
						}
						ystring = ystring.replace(v,'');
						s = ystring;
					}
				}
			}else{
				s = $(this).next('span').text();
			}
			$('#years').combo('setValue', s).combo('setText', s);
		});
	}
    $(function () {
        var hh = parent.parent.$("#page").height() + 250;
        $(document.body).height(hh - 300);
        var divH = hh * 0.96 / 3;
        $("#parentId > div").height(divH);
        $(".sjwd_cont").height(divH);
        //查询条件中的年份设置
        yearsSet();
        //路径配置
        require.config({
            paths: {
                echarts: 'http://echarts.baidu.com/build/dist'
            }
        });
        var heatDiv,heattrendDiv,waterDiv,watertrendDiv,powerDiv,powertrendDiv;
        var heatChart,heattrendChart,waterChart,watertrendChart,powerChart,powertrendChart;
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
                heatDiv = ec.init(document.getElementById('heat'), theme);
                heattrendDiv = ec.init(document.getElementById('heattrend'), theme);
                waterDiv = ec.init(document.getElementById('water'), theme);
                watertrendDiv = ec.init(document.getElementById('watertrend'), theme);
                powerDiv = ec.init(document.getElementById('power'), theme);
                powertrendDiv = ec.init(document.getElementById('powertrend'), theme);
               
                //热单耗同期比
                heatChart = {
					tooltip : {
						trigger : 'axis'
					},
					legend : {
						data : [ '南空调','北空调','裙房','公寓高区','公寓低区' ]
					},
					calculable : true,
					xAxis : [ {
						type : 'category',
						data : [ '2015年', '2016年', '2017年' ]
					} ],
					yAxis : [ {
						name : 'GJ/万㎡',
						type : 'value'
					} ],
					series : [ {
						name : '南空调',
						type : 'bar',
						data : [ 8000, 6000, 4000 ],
						itemStyle:{normal:{label:{show:true}}},
           	            markLine : {
           	                data : [
           	                    {type : 'average', name: '平均值'}
           	                ]
           	            }
					},
					{
						name : '北空调',
						type : 'bar',
						data : [ 2000, 3500, 2900 ],
						itemStyle:{normal:{label:{show:true}}},
           	            markLine : {
           	                data : [
           	                    {type : 'average', name: '平均值'}
           	                ]
           	            }
					},
					{
						name : '裙房',
						type : 'bar',
						data : [ 4000, 2800, 3500 ],
						itemStyle:{normal:{label:{show:true}}},
           	            markLine : {
           	                data : [
           	                    {type : 'average', name: '平均值'}
           	                ]
           	            }
					},
					{
						name : '公寓高区',
						type : 'bar',
						data : [ 3400, 6000, 4000 ],
						itemStyle:{normal:{label:{show:true}}},
           	            markLine : {
           	                data : [
           	                    {type : 'average', name: '平均值'}
           	                ]
           	            }
					},
					{
						name : '公寓低区',
						type : 'bar',
						data : [ 2200, 3600, 4000 ],
						itemStyle:{normal:{label:{show:true}}},
           	            markLine : {
           	                data : [
           	                    {type : 'average', name: '平均值'}
           	                ]
           	            }
					}]
				};
                
                //水单耗同期比
                waterChart = {
   					tooltip : {
   						trigger : 'axis'
   					},
   					legend : {
   						data : [ '南空调','北空调','裙房','公寓高区','公寓低区'  ]
   					},
   					calculable : true,
   					xAxis : [ {
   						type : 'category',
   						data : [ '2015年', '2016年', '2017年' ]
   					} ],
   					yAxis : [ {
   						name : 't/万㎡',
   						type : 'value'
   					} ],
   					series : [ 
   					{
						name : '南空调',
						type : 'bar',
						data : [ 3500, 4000, 3860 ],
						itemStyle:{normal:{label:{show:true}}},
           	            markLine : {
           	                data : [
           	                    {type : 'average', name: '平均值'}
           	                ]
           	            }
					},
					{
						name : '北空调',
						type : 'bar',
						data : [ 2500, 3200, 2890 ],
						itemStyle:{normal:{label:{show:true}}},
           	            markLine : {
           	                data : [
           	                    {type : 'average', name: '平均值'}
           	                ]
           	            }
					},
					{
						name : '裙房',
						type : 'bar',
						data : [ 3400, 4300, 4000 ],
						itemStyle:{normal:{label:{show:true}}},
           	            markLine : {
           	                data : [
           	                    {type : 'average', name: '平均值'}
           	                ]
           	            }
					},{
						name : '公寓高区',
						type : 'bar',
						data : [ 3500, 6000, 4000 ],
						itemStyle:{normal:{label:{show:true}}},
           	            markLine : {
           	                data : [
           	                    {type : 'average', name: '平均值'}
           	                ]
           	            }
					},{
						name : '公寓低区',
						type : 'bar',
						data : [ 3500, 5200, 4000 ],
						itemStyle:{normal:{label:{show:true}}},
           	            markLine : {
           	                data : [
           	                    {type : 'average', name: '平均值'}
           	                ]
           	            }
					}]
   				};
                
                //电单耗同期比
                powerChart = {
   					tooltip : {
   						trigger : 'axis'
   					},
   					legend : {
   						data : [ '电单耗' ]
   					},
   					calculable : true,
   					xAxis : [ {
   						type : 'category',
   						data : [ '2015年', '2016年', '2017年' ]
   					} ],
   					yAxis : [ {
   						name : 'KWh/万㎡',
   						type : 'value'
   					} ],
   					series : [ {
   						name : '电单耗',
   						type : 'bar',
   						data : [ 8000, 6000, 4000 ],
   						itemStyle:{normal:{label:{show:true}}},
   						markLine : {
   							itemStyle : {
   								normal : {
   									color : '#FF2220'
   								}
   							},
   							data : [ {
   								type : 'average',
   								name : '平均值'
   							} ]
   						}
   					} ]
   				};
                
                //热单耗趋势
                heattrendChart = {
               		tooltip : {
               	        trigger: 'axis'
               	    },
               	    legend: {
               	        data:['南空调','北空调','裙房','公寓高区','公寓低区' ]
               	    },
               	    calculable : true,
               	    xAxis : [
               	        {
               	            type : 'category',
               	            boundaryGap : false,
               	            data : ['2017-04-01','2017-04-02','2017-04-03','2017-04-04','2017-04-05','2017-04-06','2017-04-07']
               	        }
               	    ],
               	    yAxis : [
               	        {
               	            type : 'value',
               	            name : 'GJ/万㎡'
               	        }
               	    ],
               	    series : [
               	        {
               	            name:'南空调',
               	            type:'line',
               	            data:[6000,5300,5600,5500,4950,5000,5890],
               	            itemStyle:{normal:{label:{show:true}}},
               	            markLine : {
               	                data : [
               	                    {type : 'average', name: '平均值'}
               	                ]
               	            }
               	        },{
               	            name:'北空调',
               	            type:'line',
               	            data:[5550,5300,5120,5500,4950,5000,5320],
               	            itemStyle:{normal:{label:{show:true}}},
               	            markLine : {
               	                data : [
               	                    {type : 'average', name: '平均值'}
               	                ]
               	            }
               	        },{
               	            name:'裙房',
               	            type:'line',
               	            data:[5430,5300,5600,5120,4950,5000,5420],
               	            itemStyle:{normal:{label:{show:true}}},
               	            markLine : {
               	                data : [
               	                    {type : 'average', name: '平均值'}
               	                ]
               	            }
               	        },{
               	            name:'公寓高区',
               	            type:'line',
               	            data:[4300,4160,4330,4640,4950,5000,4890],
               	            itemStyle:{normal:{label:{show:true}}},
               	            markLine : {
               	                data : [
               	                    {type : 'average', name: '平均值'}
               	                ]
               	            }
               	        },{
               	            name:'公寓低区',
               	            type:'line',
               	            data:[5400,4780,5600,4900,4950,5000,5110],
               	            itemStyle:{normal:{label:{show:true}}},
               	            markLine : {
               	                data : [
               	                    {type : 'average', name: '平均值'}
               	                ]
               	            }
               	        }
               	    ]	
                };
                
                //水单耗趋势
                watertrendChart = {
               		tooltip : {
               	        trigger: 'axis'
               	    },
               	    legend: {
               	        data:['南空调','北空调','裙房','公寓高区','公寓低区' ]
               	    },
               	    calculable : true,
               	    xAxis : [
               	        {
               	            type : 'category',
               	            boundaryGap : false,
               	            data : ['2017-04-01','2017-04-02','2017-04-03','2017-04-04','2017-04-05','2017-04-06','2017-04-07']
               	        }
               	    ],
               	    yAxis : [
               	        {
               	            type : 'value',
               	            name : 't/万㎡'
               	        }
               	    ],
               	    series : [
               	        {
               	            name:'南空调',
               	            type:'line',
               	            data:[6000,5300,5600,5500,4950,5000,5890],
               	            itemStyle:{normal:{label:{show:true}}},
               	            markLine : {
               	                data : [
               	                    {type : 'average', name: '平均值'}
               	                ]
               	            }
               	        },{
               	            name:'北空调',
               	            type:'line',
               	            data:[5550,5300,5120,5500,4950,5000,5320],
               	            itemStyle:{normal:{label:{show:true}}},
               	            markLine : {
               	                data : [
               	                    {type : 'average', name: '平均值'}
               	                ]
               	            }
               	        },{
               	            name:'裙房',
               	            type:'line',
               	            data:[5430,5300,5600,5120,4950,5000,5420],
               	            itemStyle:{normal:{label:{show:true}}},
               	            markLine : {
               	                data : [
               	                    {type : 'average', name: '平均值'}
               	                ]
               	            }
               	        },{
               	            name:'公寓高区',
               	            type:'line',
               	            data:[4300,4160,4330,4640,4950,5000,4890],
               	            itemStyle:{normal:{label:{show:true}}},
               	            markLine : {
               	                data : [
               	                    {type : 'average', name: '平均值'}
               	                ]
               	            }
               	        },{
               	            name:'公寓低区',
               	            type:'line',
               	            data:[5400,4780,5600,4900,4950,5000,5110],
               	            itemStyle:{normal:{label:{show:true}}},
               	            markLine : {
               	                data : [
               	                    {type : 'average', name: '平均值'}
               	                ]
               	            }
               	        }
               	    ]	
                };
              
                //点单耗趋势
                powertrendChart = {
               		tooltip : {
               	        trigger: 'axis'
               	    },
               	    legend: {
               	        data:['电单耗']
               	    },
               	    calculable : true,
               	    xAxis : [
               	        {
               	            type : 'category',
               	            boundaryGap : false,
               	            data : ['2017-04-01','2017-04-02','2017-04-03','2017-04-04','2017-04-05','2017-04-06','2017-04-07']
               	        }
               	    ],
               	    yAxis : [
               	        {
               	            type : 'value',
               	            name : 'KWh/万㎡'
               	        }
               	    ],
               	    series : [
               	        {
               	            name:'电单耗',
               	            type:'line',
               	            data:[11, 11, 15, 13, 12, 13, 10],
               	            itemStyle:{normal:{label:{show:true}}},
               	            markLine : {
               	                data : [
               	                    {type : 'average', name: '平均值'}
               	                ]
               	            }
               	        }
               	    ]	
                };
                
				heatDiv.setOption(heatChart);
				heattrendDiv.setOption(heattrendChart);
				waterDiv.setOption(waterChart);
				watertrendDiv.setOption(watertrendChart);
				powerDiv.setOption(powerChart);
				powertrendDiv.setOption(powertrendChart);
			});
	});
</script>
</body>
</html>
