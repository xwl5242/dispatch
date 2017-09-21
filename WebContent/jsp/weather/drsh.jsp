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
        <div class="box_tit">度日数同期比</div>
        <div class="sjwd_cont" id="DRSH">
        </div>
    </div>
    <div>
        <div class="box_tit">度日数趋势</div>
        <div class="sjwd_cont" id="DRSHtrend">
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
    	var hh = parent.$("#page").height();
        var ww = parent.$("#page").width();
        $(document.body).height(hh-10);
        $(document.body).width(ww-20);
        
        var divH = (hh-90);
        $("#parentId > div").height(divH);
        $(".sjwd_cont").height(divH);
        
        var divW = (ww-65);
        $("#parentId > div").width(divW/2);
        $(".sjwd_cont").width(divW/2);
		
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
                DRSH = ec.init(document.getElementById('DRSH'), theme);
                DRSHtrend = ec.init(document.getElementById('DRSHtrend'), theme);
               
                $.post('<%=path %>/ea/airsystem/air/drsh.do',{
                	ys : $('#years').combo('getText'),
                	startDate : $('#startDate').datebox('getValue'),
                	endDate : $('#endDate').datebox('getValue')
                },function(data){
	                myDRSH = {
						tooltip : {
							trigger : 'axis'
						},
						legend : {
							data : [ '度日数' ]
						},
						calculable : true,
						xAxis : [ {
							type : 'category',
							data : data.yearList
						} ],
						yAxis : [ {
							name : '',
							type : 'value'
						} ],
						series : [ {
							name : '度日数',
							type : 'bar',
							data : data.drsh,
							barWidth: '60',
							itemStyle:{normal:{label:{show:true}}},
	           	            markLine : {
	           	                data : [
	           	                    {type : 'average', name: '平均值'}
	           	                ]
	           	            }
						}]
					};
	                
	                DRSH.setOption(myDRSH);
                },'json');
                
                $.post('<%=path %>/ea/airsystem/air/DRSHtrend.do',{
                	startDate : $('#startDate').datebox('getValue'),
                	endDate : $('#endDate').datebox('getValue')
                },function(data){
	                myDRSHtrend = {
	               		tooltip : {
	               	        trigger: 'axis'
	               	    },
	               	    legend: {
	               	        data:data.yearList
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
	               	            data : data.yearDateList
	               	        }
	               	    ],
	               	    yAxis : [
	               	        {
	               	            type : 'value'
	               	        }
	               	    ],
	               	    series : [
	               	        {
	               	            name:data.yearList[0],
	               	            type:'line',
	               	         	symbol:'none',
	               	            data:data.trend1,
	               	            itemStyle:{normal:{label:{show:true}}},
	               	            markLine : {
	               	                data : [
	               	                    {type : 'average', name: '平均值'}
	               	                ]
	               	            }
	               	        },
	               	     {
	               	            name:data.yearList[1],
	               	            type:'line',
	               	         	symbol:'none',
	               	            data:data.trend2,
	               	            itemStyle:{normal:{label:{show:true}}},
	               	            markLine : {
	               	                data : [
	               	                    {type : 'average', name: '平均值'}
	               	                ]
	               	            }
	               	        },
	               	     {
	               	            name:data.yearList[2],
	               	            type:'line',
	               	         	symbol:'none',
	               	            data:data.trend3,
	               	            itemStyle:{normal:{label:{show:true}}},
	               	            markLine : {
	               	                data : [
	               	                    {type : 'average', name: '平均值'}
	               	                ]
	               	            }
	               	        }
	               	    ]
	   				};
	                DRSHtrend.setOption(myDRSHtrend);
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