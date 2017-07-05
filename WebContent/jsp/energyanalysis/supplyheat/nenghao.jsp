<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    %>
    <jsp:include page="/jsp/header.jsp"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<%=request.getContextPath() %>/common/css/demo.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath() %>/common/css/other.css" rel="stylesheet" type="text/css"/>
    <title></title>
    <!-- ECharts单文件引入 -->
    <script src="http://echarts.baidu.com/build/dist/echarts.js"></script>

    <link type="text/css" rel="stylesheet" href="<%=path %>/static/main/css/common.css"/>
    <style type="text/css">
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
        <div class="box_tit">热能耗同期比</div>
        <div class="sjwd_cont" id="heat">
        </div>
    </div>
    <div>
        <div class="box_tit">热能耗趋势</div>
        <div class="sjwd_cont" id="heattrend">
        </div>
    </div>

    <div>
        <div class="box_tit">水能耗同期比</div>
        <div class="sjwd_cont" id="water">
        </div>
    </div>
    <div>
        <div class="box_tit">水能耗趋势</div>
        <div class="sjwd_cont" id="watertrend">
        </div>
    </div>
    
    <div>
        <div class="box_tit">电能耗同期比</div>
        <div class="sjwd_cont" id="power">
        </div>
    </div>
    <div>
        <div class="box_tit">电能耗趋势</div>
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
    	var hh = parent.$("#page_seconde").height();
        var ww = parent.$("#page_seconde").width();
        $(document.body).height(hh-10);
        $(document.body).width(ww-20);
        
        var divH = (hh-90)/2;
        $("#parentId > div").height(divH);
        $(".sjwd_cont").height(divH);
        
        var divW = (ww-65)/2;
        $("#parentId > div").width(divW);
        $(".sjwd_cont").width(divW);
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
                'echarts/chart/bar',
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
                //热能耗同期比
                heatChart = {
					tooltip : {
						trigger : 'axis'
					},
					legend : {
						data : [ '热能耗' ]
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
						name : '热能耗',
						type : 'bar',
						data : [ 8000, 6000, 4000 ],
						itemStyle:{normal:{label:{show:true}}},
           	            markLine : {
           	                data : [
           	                    {type : 'average', name: '平均值'}
           	                ]
           	            }
					} ]
				};
                
                //水能耗同期比
                waterChart = {
   					tooltip : {
   						trigger : 'axis'
   					},
   					legend : {
   						data : [ '水能耗' ]
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
   					series : [ {
   						name : '水能耗',
   						type : 'bar',
   						data : [ 8000, 6000, 4000 ],
   						itemStyle:{normal:{label:{show:true}}},
           	            markLine : {
           	                data : [
           	                    {type : 'average', name: '平均值'}
           	                ]
           	            }
   					} ]
   				};
                
                //电能耗同期比
                powerChart = {
   					tooltip : {
   						trigger : 'axis'
   					},
   					legend : {
   						data : [ '电能耗' ]
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
   						name : '电能耗',
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
                
                //热能耗趋势
                heattrendChart = {
               		tooltip : {
               	        trigger: 'axis'
               	    },
               	    legend: {
               	        data:['热能耗']
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
               	            name:'热能耗',
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
                
                //水能耗趋势
                watertrendChart = {
               		tooltip : {
               	        trigger: 'axis'
               	    },
               	    legend: {
               	        data:['水能耗']
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
               	            name:'水能耗',
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
              
                //点能耗趋势
                powertrendChart = {
               		tooltip : {
               	        trigger: 'axis'
               	    },
               	    legend: {
               	        data:['电能耗']
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
               	            name:'电能耗',
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
