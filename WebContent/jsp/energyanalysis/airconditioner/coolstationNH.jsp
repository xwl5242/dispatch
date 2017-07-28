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
    <!-- ECharts能文件引入 -->
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
			   <a id="searchLineTrend" onclick='searchNHTrend();' class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&nbsp;
			   <a id="clearLineTrendSearch"  onclick='formClear();' class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a>
			</td> 
        </tr>
    </table>
</div>
<div id="parentId" class="box">
    <div>
        <div class="box_tit">电能耗同期比</div>
        <div class="sjwd_cont" id="power">
        </div>
    </div>
    <div>
        <div class="box_tit">水能耗同期比</div>
        <div class="sjwd_cont" id="water">
        </div>
    </div>

    <div>
        <div class="box_tit">电能耗趋势</div>
        <div class="sjwd_cont" id="powertrend">
        </div>
    </div>
    <div>
        <div class="box_tit">水能耗趋势</div>
        <div class="sjwd_cont" id="watertrend">
        </div>
    </div>
    
    <div id="piediv">
        <div class="box_tit">水电成本比</div>
        <div class="sjwd_cont" id="waterpower">
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
        
        var divH = (hh-90)/1.5;
        $("#parentId > div").height(divH);
        $(".sjwd_cont").height(divH);
        
        var divW = (ww-65)/2;
        $("#parentId > div").width(divW);
        $(".sjwd_cont").width(divW);
        
        $("#waterpower").width(ww-50);
        $("#piediv").width(ww-50);
        //查询条件中的年份设置
        yearsSet();
		
		$('[name="2017"]').attr("checked",true);
		$('#years').combo('setText','2017');
		$('#endDate').datebox('setText',getDate(0));
		$('#startDate').datebox('setText',getDate(6*24*60*60*1000));
		
        renderEcharts();
	});
function searchNHTrend(){
	renderEcharts();
}
function renderEcharts(){
	//路径配置
    require.config({
        paths: {
            echarts: 'http://echarts.baidu.com/build/dist'
        }
    });
    var power,water,powertrend,watertrend,waterpower;
    var mypower,mywater,mypowertrend,mywatertrend,mywaterpower;
    // 使用
    require(
        [
            'echarts',
            'echarts/theme/macarons',
            'echarts/chart/pie',
            'echarts/chart/bar',
            'echarts/chart/line' 
        ],
        function (ec, theme) {
            // 基于准备好的dom，初始化echarts图表
            power = ec.init(document.getElementById('power'), theme);
            water = ec.init(document.getElementById('water'), theme);
            powertrend = ec.init(document.getElementById('powertrend'), theme);
            watertrend = ec.init(document.getElementById('watertrend'), theme);
            waterpower = ec.init(document.getElementById('waterpower'), theme);
           
            $.post('<%=path %>/ea/airsystem/air/tongbi.do',{
            	ys : $('#years').combo('getText'),
            	type : "'2','3','4','5','6'",
            	eaType:'NH'
            },function(data){
            	mypower = {
        				tooltip : {
        					trigger : 'axis'
        				},
        				legend : {
        					data:['A座','B座','总体']
        				},
        				calculable : true,
        				xAxis : [ {
        					type : 'category',
//         					data : [ '2015年', '2016年', '2017年' ]
        					data : data.yearList
        				} ],
        				yAxis : [ {
        					name : 'KWh',
        					type : 'value'
        				} ],
        				series : [
                      	        {
                         	            name:'A座',
                         	            type:'bar',
//                          	            data:[2.0, 4.9, 7.0],
                         	            data:data.A,
                         	         	itemStyle:{normal:{label:{show:true}}},
                         	            markLine : {
                         	                data : [
                         	                    {type : 'average', name: '平均值'}
                         	                ]
                         	            }
                         	        },
                         	        {
                         	            name:'B座',
                         	            type:'bar',
//                          	            data:[2.6, 5.9, 9.0],
                         	            data:data.B,
                         	            itemStyle:{normal:{label:{show:true}}},
                         	            markLine : {
                         	                data : [
                         	                    {type : 'average', name: '平均值'}
                         	                ]
                         	            }
                         	        },
                         	        {
                         	            name:'总体',
                         	            type:'bar',
//                          	            data:[4.6, 10.8, 16.0],
                         	            data:data.T,
                         	            itemStyle:{normal:{label:{show:true}}},
                         	            markLine : {
                         	                data : [
                         	                    {type : 'average', name: '平均值'}
                         	                ]
                         	            }
                         	        }
                         	    ]
        			};
            	power.setOption(mypower);
            },'json');
            
            $.post('<%=path %>/ea/airsystem/air/tongbi.do',{
            	ys : $('#years').combo('getText'),
            	type : "'8'",
            	eaType:'NH'
            },function(data){
            	mywater = {
            			tooltip : {
        					trigger : 'axis'
        				},
        				legend : {
        					data:['A座','B座','总体']
        				},
        				calculable : true,
        				xAxis : [ {
        					type : 'category',
//         					data : [ '2015年', '2016年', '2017年' ]
        					data : data.yearList
        				} ],
        				yAxis : [ {
        					name : 'T',
        					type : 'value'
        				} ],
        				series : [
                      	        {
                         	            name:'A座',
                         	            type:'bar',
//                          	            data:[2.0, 4.9, 7.0],
                         	            data:data.A,
                         	         	itemStyle:{normal:{label:{show:true}}},
                         	            markLine : {
                         	                data : [
                         	                    {type : 'average', name: '平均值'}
                         	                ]
                         	            }
                         	        },
                         	        {
                         	            name:'B座',
                         	            type:'bar',
//                          	            data:[2.6, 5.9, 9.0],
                         	            data:data.B,
                         	            itemStyle:{normal:{label:{show:true}}},
                         	            markLine : {
                         	                data : [
                         	                    {type : 'average', name: '平均值'}
                         	                ]
                         	            }
                         	        },
                         	        {
                         	            name:'总体',
                         	            type:'bar',
//                          	            data:[4.6, 10.8, 16.0],
                         	            data:data.T,
                         	            itemStyle:{normal:{label:{show:true}}},
                         	            markLine : {
                         	                data : [
                         	                    {type : 'average', name: '平均值'}
                         	                ]
                         	            }
                         	        }
                         	    ]
    				};
            	water.setOption(mywater);
            },'json');
            
            $.post('<%=path %>/ea/airsystem/air/qushi.do',{
            	startDate : $('#startDate').datebox('getValue'),
            	endDate : $('#endDate').datebox('getValue'),
            	type : "'2','3','4','5','6'",
            	eaType:'NH'
            },function(data){
            	mypowertrend = {
            			tooltip : {
	               	        trigger: 'axis'
	               	    },
	               	    legend: {
	               	        data:['A座','B座','总体']
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
	               	                formatter: '{value} KWh'
	               	            }
	               	        }
	               	    ],
	               	    series : [
	               	        {
	               	            name:'A座',
	               	            type:'line',
// 	               	            data:[11, 11, 15, 13, 12, 13, 10],
	               	            data:data.A,
	               	            itemStyle:{normal:{label:{show:true}}},
	               	            markLine : {
	               	                data : [
	               	                    {type : 'average', name: '平均值'}
	               	                ]
	               	            }
	               	        },
	               	        {
	               	            name:'B座',
	               	            type:'line',
// 	               	            data:[1, 2, 2, 5, 3, 2, 0],
	               	            data:data.B,
	               	            itemStyle:{normal:{label:{show:true}}},
	               	            markLine : {
	               	                data : [
	               	                    {type : 'average', name : '平均值'}
	               	                ]
	               	            }
	               	        },
	               	        {
	               	            name:'总体',
	               	            type:'line',
// 	               	            data:[11, 13, 17, 18, 15, 15, 10],
	               	            data:data.T,
	               	            itemStyle:{normal:{label:{show:true}}},
	               	            markLine : {
	               	                data : [
	               	                    {type : 'average', name : '平均值'}
	               	                ]
	               	            }
	               	        }
	               	    ]
        			};
            	powertrend.setOption(mypowertrend);
            },'json');
            
            $.post('<%=path %>/ea/airsystem/air/qushi.do',{
            	startDate : $('#startDate').datebox('getValue'),
            	endDate : $('#endDate').datebox('getValue'),
            	type : "'8'",
            	eaType:'NH'
            },function(data){
            	mywatertrend = {
            			tooltip : {
	               	        trigger: 'axis'
	               	    },
	               	    legend: {
	               	        data:['A座','B座','总体']
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
	               	                formatter: '{value} T'
	               	            }
	               	        }
	               	    ],
	               	    series : [
	               	        {
	               	            name:'A座',
	               	            type:'line',
// 	               	            data:[11, 11, 15, 13, 12, 13, 10],
	               	            data:data.A,
	               	            itemStyle:{normal:{label:{show:true}}},
	               	            markLine : {
	               	                data : [
	               	                    {type : 'average', name: '平均值'}
	               	                ]
	               	            }
	               	        },
	               	        {
	               	            name:'B座',
	               	            type:'line',
// 	               	            data:[1, 2, 2, 5, 3, 2, 0],
	               	            data:data.B,
	               	            itemStyle:{normal:{label:{show:true}}},
	               	            markLine : {
	               	                data : [
	               	                    {type : 'average', name : '平均值'}
	               	                ]
	               	            }
	               	        },
	               	        {
	               	            name:'总体',
	               	            type:'line',
// 	               	            data:[11, 13, 17, 18, 15, 15, 10],
	               	            data:data.T,
	               	            itemStyle:{normal:{label:{show:true}}},
	               	            markLine : {
	               	                data : [
	               	                    {type : 'average', name : '平均值'}
	               	                ]
	               	            }
	               	        }
	               	    ]
        			};
            	watertrend.setOption(mywatertrend);
            },'json');
            
            $.post('<%=path %>/ea/airsystem/air/pricePie.do',{
            	startDate : $('#startDate').datebox('getValue'),
            	endDate : $('#endDate').datebox('getValue')
            },function(data){
            	mywaterpower = {
                   		tooltip : {
                   	        trigger: 'item',
                   	        formatter: "{a} <br/>{b} : {c} ({d}%)"
                   	    },
                   	    legend: {
                   	        data:['A座水','A座电','B座水','B座电']
                   	    },
                   	    calculable : true,
                   	    series : [
                   	        {
                   	            type:'pie',
                   	            radius : '55%',
                   	            center: ['50%', '60%'],
                   	            data:data
                   	        }
                   	    ]
                   	};
        			waterpower.setOption(mywaterpower);
            },'json');
		});
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