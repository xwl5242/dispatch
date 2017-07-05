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
        <div class="box_tit">A座COP同期比</div>
        <div class="sjwd_cont" id="ACOP">
        </div>
    </div>
    <div>
        <div class="box_tit">B座COP同期比</div>
        <div class="sjwd_cont" id="BCOP">
        </div>
    </div>

    <div>
        <div class="box_tit">A座COP趋势</div>
        <div class="sjwd_cont" id="ACOPtrend">
        </div>
    </div>
    <div>
        <div class="box_tit">B座COP趋势</div>
        <div class="sjwd_cont" id="BCOPtrend">
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
        
        //查询条件中的年份设置
        yearsSet();
        //路径配置
        require.config({
            paths: {
                echarts: 'http://echarts.baidu.com/build/dist'
            }
        });
        var ACOP,BCOP,ACOPtrend,BCOPtrend;
        var myACOP,myBCOP,myACOPtrend,myBCOPtrend;
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
                ACOP = ec.init(document.getElementById('ACOP'), theme);
                BCOP = ec.init(document.getElementById('BCOP'), theme);
                ACOPtrend = ec.init(document.getElementById('ACOPtrend'), theme);
                BCOPtrend = ec.init(document.getElementById('BCOPtrend'), theme);
               
                myACOP = {
					tooltip : {
						trigger : 'axis'
					},
					legend : {
						data : [ 'A1','A2','A3' ]
					},
					calculable : true,
					xAxis : [ {
						type : 'category',
						data : [ '2015年', '2016年', '2017年' ]
					} ],
					yAxis : [ {
						name : '',
						type : 'value'
					} ],
					series : [ {
						name : 'A1',
						type : 'bar',
						data : [ 0.15, 0.18, 0.12 ],
						itemStyle:{normal:{label:{show:true}}},
           	            markLine : {
           	                data : [
           	                    {type : 'average', name: '平均值'}
           	                ]
           	            }
					},{
						name : 'A2',
						type : 'bar',
						data : [ 0.14,0.19,0.10 ],
						itemStyle:{normal:{label:{show:true}}},
           	            markLine : {
           	                data : [
           	                    {type : 'average', name: '平均值'}
           	                ]
           	            }
					},{
						name : 'A3',
						type : 'bar',
						data : [ 0.18,0.13,0.16 ],
						itemStyle:{normal:{label:{show:true}}},
           	            markLine : {
           	                data : [
           	                    {type : 'average', name: '平均值'}
           	                ]
           	            }
					} ]
				};
                
                myBCOP = {
   					tooltip : {
   						trigger : 'axis'
   					},
   					legend : {
   						data : [ 'B1','B2','B3' ]
   					},
   					calculable : true,
   					xAxis : [ {
   						type : 'category',
   						data : [ '2015年', '2016年', '2017年' ]
   					} ],
   					yAxis : [ {
   						name : '',
   						type : 'value'
   					} ],
   					series : [ {
   						name : 'B1',
   						type : 'bar',
   						data : [ 0.16,0.11,0.19 ],
   						itemStyle:{normal:{label:{show:true}}},
           	            markLine : {
           	                data : [
           	                    {type : 'average', name: '平均值'}
           	                ]
           	            }
   					},{
   						name : 'B2',
   						type : 'bar',
   						data : [ 0.12,0.16,0.13 ],
   						itemStyle:{normal:{label:{show:true}}},
           	            markLine : {
           	                data : [
           	                    {type : 'average', name: '平均值'}
           	                ]
           	            }
   					},{
   						name : 'B3',
   						type : 'bar',
   						data : [ 0.17,0.15,0.10 ],
   						itemStyle:{normal:{label:{show:true}}},
           	            markLine : {
           	                data : [
           	                    {type : 'average', name: '平均值'}
           	                ]
           	            }
   					} ]
   				};
                
                myACOPtrend = {
               		tooltip : {
               	        trigger: 'axis'
               	    },
               	    legend: {
               	        data:['A1','A2','A3','度日数']
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
               	        	name:'cop',
               	            type : 'value',
               	            max:0.2
               	        },
               	     	{
               	        	name:'度日数',
               	            type : 'value',
               	            max:2
               	        }
               	    ],
               	    series : [
               	        {
               	            name:'A1',
               	            type:'line',
               	            data:[0.1,0.13,0.14,0.18,0.15,0.19,0.12],
               	            itemStyle:{normal:{label:{show:true}}},
               	            markLine : {
               	                data : [
               	                    {type : 'average', name: '平均值'}
               	                ]
               	            }
               	        },
               	        {
               	            name:'A2',
               	            type:'line',
               	            data:[0.16,0.13,0.19,0.13,0.18,0.13,0.14],
               	            itemStyle:{normal:{label:{show:true}}},
               	            markLine : {
               	                data : [
               	                    {type : 'average', name : '平均值'}
               	                ]
               	            }
               	        },
               	        {
               	            name:'A3',
               	            type:'line',
               	            data:[0.15,0.18,0.17,0.14,0.11,0.1,0.17],
               	            itemStyle:{normal:{label:{show:true}}},
               	            markLine : {
               	                data : [
               	                    {type : 'average', name : '平均值'}
               	                ]
               	            }
               	        },
	               	     {
	               	            name:'度日数',
	               	            type:'line',
	               	        	yAxisIndex:1,
	               	            data:[0.95,1.2,1.8,1.5,0.99,1.1,1.6],
	               	            itemStyle:{normal:{label:{show:true}}},
	               	            markLine : {
	               	                data : [
	               	                    {type : 'average', name : '平均值'}
	               	                ]
	               	            }
	               	        }
               	    ]
   				};
              
                myBCOPtrend = {
               		tooltip : {
               	        trigger: 'axis'
               	    },
               	    legend: {
               	        data:['B1','B2','B3','度日数']
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
               	        	name:'cop',
               	            type : 'value',
               	            max:0.2
               	        },
               	     	{
               	        	name:'度日数',
               	            type : 'value',
               	            max:2
               	        }
               	    ],
               	    series : [
               	        {
               	            name:'B1',
               	            type:'line',
               	            data:[0.1,0.13,0.14,0.18,0.15,0.19,0.12],
               	            itemStyle:{normal:{label:{show:true}}},
               	            markLine : {
               	                data : [
               	                    {type : 'average', name: '平均值'}
               	                ]
               	            }
               	        },
               	        {
               	            name:'B2',
               	            type:'line',
               	            data:[0.16,0.13,0.19,0.13,0.18,0.13,0.14],
               	            itemStyle:{normal:{label:{show:true}}},
               	            markLine : {
               	                data : [
               	                    {type : 'average', name : '平均值'}
               	                ]
               	            }
               	        },
               	        {
               	            name:'B3',
               	            type:'line',
               	            data:[0.15,0.18,0.17,0.14,0.11,0.1,0.17],
               	            itemStyle:{normal:{label:{show:true}}},
               	            markLine : {
               	                data : [
               	                    {type : 'average', name : '平均值'}
               	                ]
               	            }
               	        },
               	     	{
               	            name:'度日数',
               	            type:'line',
               	         	yAxisIndex:1,
               	            data:[0.95,1.2,1.8,1.5,0.99,1.1,1.6],
               	            itemStyle:{normal:{label:{show:true}}},
               	            markLine : {
               	                data : [
               	                    {type : 'average', name : '平均值'}
               	                ]
               	            }
               	        }
               	    ]
   				};
                
                ACOP.setOption(myACOP);
                BCOP.setOption(myBCOP);
                ACOPtrend.setOption(myACOPtrend);
                BCOPtrend.setOption(myBCOPtrend);
			});
	});
</script>
</body>
</html>