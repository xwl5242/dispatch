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
        <div class="box_tit">A座平均COP趋势</div>
        <div class="sjwd_cont" id="AvgACOPtrend">
        </div>
    </div>
    <div>
        <div class="box_tit">B座平均COP趋势</div>
        <div class="sjwd_cont" id="AvgBCOPtrend">
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
    	var hh = parent.$("#page_seconde").height()==null?parent.$("#page").height():parent.$("#page_seconde").height();
        var ww = parent.$("#page_seconde").width()==null?parent.$("#page").width():parent.$("#page_seconde").width();
        $(document.body).height(hh-10);
        $(document.body).width(ww-20);
        
        var divH = (hh-105);
        $("#parentId > div").height(divH/1.5);
        $(".sjwd_cont").height(divH/1.5);
        
        var divW = (ww-65);
        $("#parentId > div").width(divW);
        $(".sjwd_cont").width(divW);
		
		$('#startDate').datebox({
			onSelect:function(date){
				var end = new Date($('#endDate').datebox('getValue')).getTime();
				if(end!=null&&end!=''){
					if(end<new Date(date).getTime()){
						$.messager.alert('提醒', '开始时间不能大于结束时间,请重新选择!');  
						$('#startDate').datebox('setValue','');
						return ;
					}
				}
			}
		});
		
		$('#endDate').datebox({
			onSelect:function(date){
				if($('#startDate').datebox('getValue')==null||$('#startDate').datebox('getValue')==''){
					$.messager.alert('提醒', '请先选择开始时间!'); 
					$('#endDate').datebox('setValue','');
					return ;
				}
				var start = new Date($('#startDate').datebox('getValue')).getTime();
				if(start!=null&&start!=''){
					if(start>new Date(date).getTime()+24*60*60*1000){
						$.messager.alert('提醒', '结束时间不能小于开始时间,请重新选择!');  
						$('#endDate').datebox('setValue','');
						return ;
					}
				}
			}
		});
		
        //查询条件中的年份设置
        yearsSet();
        
        $('[name="2017"]').attr("checked",true);
		$('#years').combo('setText','2017');
		$('#endDate').datebox('setText',getDate(0));
		$('#endDate').datebox('setValue',getDate(0));
		$('#startDate').datebox('setText',getDate(6*24*60*60*1000));
		$('#startDate').datebox('setValue',getDate(6*24*60*60*1000));
		renderEchart();
	});
    
    function renderEchart(){
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
                'echarts/chart/bar',
                'echarts/chart/line',
                'echarts/chart/scatter'
            ],
            function (ec, theme) {
                // 基于准备好的dom，初始化echarts图表
                AvgACOPtrend = ec.init(document.getElementById('AvgACOPtrend'), theme);
                AvgBCOPtrend = ec.init(document.getElementById('AvgBCOPtrend'), theme);
                ACOPtrend = ec.init(document.getElementById('ACOPtrend'), theme);
                BCOPtrend = ec.init(document.getElementById('BCOPtrend'), theme);
               
                $.post('<%=path %>/ea/airsystem/air/copLine4Avg.do',{
                	startDate : $('#startDate').datebox('getValue'),
                	endDate : $('#endDate').datebox('getValue')
                },function(data){
                	myAvgACOPtrend = {
    	               		tooltip : {
    	               	        trigger: 'axis'
    	               	    },
    						color:['#48cda6','#fd87ab','#00ff00'],
    	               	    legend: {
    	               	        data:['A1','A2','A3']
    	               	    },
    						toolbox: {
    							show : true,
    							feature : {
    								dataZoom : {
    									show : true,
    									title : {
    										dataZoom : '区域缩放',
    										dataZoomReset : '区域缩放后退'
    									}
    								},
    								restore : {show: true},
    								saveAsImage : {show: true}
    							}
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
    	               	            type : 'value'
    	               	        }
    	               	    ],
    	               	    series : [
    	               	        {
    	               	            name:'A1',
    	               	            type:'line',
    	               	         	symbol:'none',
    	               	            data:data.A1,
    	               	         	itemStyle : {  
    					              normal : {  
    					                lineStyle:{  
    					                  color:'#48cda6'  
    					                },
    					                label:{show:true}
    					              }  
    					            },  
    	               	            markLine : {
    	               	                data : [
    	               	                    {type : 'average', name: '平均值'}
    	               	                ]
    	               	            }
    	               	        },
    	               	        {
    	               	            name:'A2',
    	               	            type:'line',
    	               	         	symbol:'none',
    	               	            data:data.A2,
    	               	         	itemStyle : {  
    					              normal : {  
    					                lineStyle:{  
    					                  color:'#fd87ab'  
    					                },
    					                label:{show:true}
    					              }  
    					            },  
    	               	            markLine : {
    	               	                data : [
    	               	                    {type : 'average', name : '平均值'}
    	               	                ]
    	               	            }
    	               	        },
    	               	        {
    	               	            name:'A3',
    	               	            type:'line',
    	               	         	symbol:'none',
    	               	            data:data.A3,
    	               	         	itemStyle : {  
    					              normal : {  
    					                lineStyle:{  
    					                  color:'#00ff00'  
    					                },
    					                label:{show:true}
    					              }  
    					            },  
    	               	            markLine : {
    	               	                data : [
    	               	                    {type : 'average', name : '平均值'}
    	               	                ]
    	               	            }
    	               	        }
    	               	    ]
    	   				};
    	              
    	                myAvgBCOPtrend = {
    	               		tooltip : {
    	               	        trigger: 'axis'
    	               	    },
    						color:['#48cda6','#fd87ab','#00ff00'],
    	               	    legend: {
    	               	        data:['B1','B2','B3']
    	               	    },
    						toolbox: {
    							show : true,
    							feature : {
    								dataZoom : {
    									show : true,
    									title : {
    										dataZoom : '区域缩放',
    										dataZoomReset : '区域缩放后退'
    									}
    								},
    								restore : {show: true},
    								saveAsImage : {show: true}
    							}
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
    	               	            type : 'value'
    	               	        }
    	               	    ],
    	               	    series : [
    	               	        {
    	               	            name:'B1',
    	               	            type:'line',
    	               	         	symbol:'none',
    	               	            data:data.B1,
    	               	         	itemStyle : {  
    					              normal : {  
    					                lineStyle:{  
    					                  color:'#48cda6'  
    					                },
    					                label:{show:true}
    					              }  
    					            },  
    	               	            markLine : {
    	               	                data : [
    	               	                    {type : 'average', name: '平均值'}
    	               	                ]
    	               	            }
    	               	        },
    	               	        {
    	               	            name:'B2',
    	               	            type:'line',
    	               	         	symbol:'none',
    	               	            data:data.B2,
    	               	         	itemStyle : {  
    					              normal : {  
    					                lineStyle:{  
    					                  color:'#fd87ab'  
    					                },
    					                label:{show:true}
    					              }  
    					            },  
    	               	            markLine : {
    	               	                data : [
    	               	                    {type : 'average', name : '平均值'}
    	               	                ]
    	               	            }
    	               	        },
    	               	        {
    	               	            name:'B3',
    	               	            type:'line',
    	               	         	symbol:'none',
    	               	            data:data.B3,
    	               	         	itemStyle : {  
    					              normal : {  
    					                lineStyle:{  
    					                  color:'#00ff00'  
    					                },
    					                label:{show:true}
    					              }  
    					            },  
    	               	            markLine : {
    	               	                data : [
    	               	                    {type : 'average', name : '平均值'}
    	               	                ]
    	               	            }
    	               	        }
    	               	    ]
    	   				};
    	                AvgACOPtrend.setOption(myAvgACOPtrend);
    	                AvgBCOPtrend.setOption(myAvgBCOPtrend);
                },'json');
                
                $.post('<%=path %>/ea/airsystem/air/copLine.do',{
                	startDate : $('#startDate').datebox('getValue'),
                	endDate : $('#endDate').datebox('getValue')
                },function(data){
	                myACOPtrend = {
	               		tooltip : {
	               	        trigger: 'axis'
	               	    },
						color:['#48cda6','#fd87ab','#00ff00'],
	               	    legend: {
	               	        data:['A1','A2','A3']
	               	    },
						toolbox: {
							show : true,
							feature : {
								dataZoom : {
									show : true,
									title : {
										dataZoom : '区域缩放',
										dataZoomReset : '区域缩放后退'
									}
								},
								restore : {show: true},
								saveAsImage : {show: true}
							}
						},
	               	    calculable : true,
	               	    xAxis : [
	               	        {
	               	            type : 'category',
	               	            boundaryGap : false,
	               	            data : data.yearList,
	               	         	axisLabel:{  
								 interval:Math.ceil(data.yearList.length/7),//横轴信息全部显示  
								 //rotate:-30,//-30度角倾斜显示  
								 formatter:function(value){
									 return value.substring(0,value.length-3);
								 }
								}
	               	        }
	               	    ],
	               	    yAxis : [
	               	        {
	               	            type : 'value'
	               	        }
	               	    ],
	               	    series : [
	               	        {
	               	            name:'A1',
	               	            type:'line',
	               	         	symbol:'none',
	               	            data:data.A1,
	               	         	itemStyle : {  
					              normal : {  
					                lineStyle:{  
					                  color:'#48cda6'  
					                },
					                label:{show:true}
					              }  
					            },  
	               	            markLine : {
	               	                data : [
	               	                    {type : 'average', name: '平均值'}
	               	                ]
	               	            }
	               	        },
	               	        {
	               	            name:'A2',
	               	            type:'line',
	               	         	symbol:'none',
	               	            data:data.A2,
	               	         	itemStyle : {  
					              normal : {  
					                lineStyle:{  
					                  color:'#fd87ab'  
					                },
					                label:{show:true}
					              }  
					            },  
	               	            markLine : {
	               	                data : [
	               	                    {type : 'average', name : '平均值'}
	               	                ]
	               	            }
	               	        },
	               	        {
	               	            name:'A3',
	               	            type:'line',
	               	         	symbol:'none',
	               	            data:data.A3,
	               	         	itemStyle : {  
					              normal : {  
					                lineStyle:{  
					                  color:'#00ff00'  
					                },
					                label:{show:true}
					              }  
					            },  
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
						color:['#48cda6','#fd87ab','#00ff00'],
	               	    legend: {
	               	        data:['B1','B2','B3']
	               	    },
						toolbox: {
							show : true,
							feature : {
								dataZoom : {
									show : true,
									title : {
										dataZoom : '区域缩放',
										dataZoomReset : '区域缩放后退'
									}
								},
								restore : {show: true},
								saveAsImage : {show: true}
							}
						},
	               	    calculable : true,
	               	    xAxis : [
	               	        {
	               	            type : 'category',
	               	            boundaryGap : false,
	               	            data : data.yearList,
		               	        axisLabel:{  
									 interval:Math.ceil(data.yearList.length/7),//横轴信息全部显示  
									 //rotate:-30,//-30度角倾斜显示  
									 formatter:function(value){
										 return value.substring(0,value.length-3);
									 }
								}
	               	        }
	               	    ],
	               	    yAxis : [
	               	        {
	               	            type : 'value'
	               	        }
	               	    ],
	               	    series : [
	               	        {
	               	            name:'B1',
	               	            type:'line',
	               	         	symbol:'none',
	               	            data:data.B1,
	               	         	itemStyle : {  
					              normal : {  
					                lineStyle:{  
					                  color:'#48cda6'  
					                },
					                label:{show:true}
					              }  
					            },  
	               	            markLine : {
	               	                data : [
	               	                    {type : 'average', name: '平均值'}
	               	                ]
	               	            }
	               	        },
	               	        {
	               	            name:'B2',
	               	            type:'line',
	               	         	symbol:'none',
	               	            data:data.B2,
	               	         	itemStyle : {  
					              normal : {  
					                lineStyle:{  
					                  color:'#fd87ab'  
					                },
					                label:{show:true}
					              }  
					            },  
	               	            markLine : {
	               	                data : [
	               	                    {type : 'average', name : '平均值'}
	               	                ]
	               	            }
	               	        },
	               	        {
	               	            name:'B3',
	               	            type:'line',
	               	         	symbol:'none',
	               	            data:data.B3,
	               	         	itemStyle : {  
					              normal : {  
					                lineStyle:{  
					                  color:'#00ff00'  
					                },
					                label:{show:true}
					              }  
					            },  
	               	            markLine : {
	               	                data : [
	               	                    {type : 'average', name : '平均值'}
	               	                ]
	               	            }
	               	        }
	               	    ]
	   				};
	                ACOPtrend.setOption(myACOPtrend);
	                BCOPtrend.setOption(myBCOPtrend);
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
    function searchLineTrend(){
    	renderEchart();
    }
</script>
</body>
</html>