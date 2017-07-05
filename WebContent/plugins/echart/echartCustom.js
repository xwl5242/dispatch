
function PrivateEchart(){
	/**基本属性**/
	this.title="";
	this.xtitle="center";
	this.subtitle="";
	this.titlelink="";
	this.tooltip="item";
	this.calculable=true;
	this.toolbox=false;
	this.legendName=new Array();
	this.legendType=new Array();
	this.legendField=new Array();
	
	this.yaxisPostion=new Array();
	
	/**数据**/
	this.chartData="";

	
	/**Line和Bar属性**/
	this.targetField="";
	this.xaisData="";
	this.yaxisName="";
	this.yaxisUnit="";
	this.otherYaxisUnit="";
	this.otherYaxisName="";
	this.markPoint=new Array();
	this.markLine=new Array();
	
	/**饼图属性**/
	this.pieLegendOrient="";
	this.pieLegendPostion=""
    this.tooltipFormatter=false;
	this.pieRadius="55%";
	this.pieCenter=['50%', '45%'];
	this.pieName="数据来源";
	
	/**仪表盘**/
	this.max=100;
	this.min=0;
	this.name="";
	this.data=0;
	this.unit="";
	
	
	/**stack**/
	this.stack="";
	this.stackArray=new Array();
}

PrivateEchart.prototype.getGaugueChart=function(){
	var series="title : {text: '"+this.title+"' ,x:'center' }, series : [{min:"+this.min+",max:"+this.max+",type:'gauge',axisLine: {lineStyle: {width: 20 }} ,detail : {formatter:'{value}'},data:[{value: "+this.data+", name: '"+this.name+"'}] }]";
	
	return "{"+series+"}";
}

PrivateEchart.prototype.getLineBarChart=function(){
	
	var title=this.getTitleInfo();
	var tooltip=this.getTooltip();
	var calculable=this.getCalculable();
	var legend=this.getLegend();
	var xaxis=this.getXaxis();
	var yaxis=this.getYaxis();
	var series=this.getSeries();
	var toolbox=this.getToolBox();
	return "{"+title+toolbox+tooltip+calculable+legend+xaxis+yaxis+series+"}";
}

PrivateEchart.prototype.getPieChart=function(){
	var title=this.getTitleInfo();
	var tooltip=this.getTooltip();
	var calculable=this.getCalculable();
	var legend=this.getLegend();
	var toolbox=this.getToolBox();
	var series=this.getPieSeries();
	return "{"+title+toolbox+tooltip+calculable+legend+series+"}";
}

PrivateEchart.prototype.getTitleInfo=function(){
	var text="title:{";
	if(this.title!=""){
		text+="text:'"+this.title+"',x:'"+this.xtitle+"' , ";
	}
	if(this.subtitle!=""){
		text+=",subtext:'"+this.subtitle+"'";
	}
	if(this.titlelink!=""){
		text+=",link:'"+this.titlelink+"'";
	}
	return text+"},";
}
PrivateEchart.prototype.getTooltip=function(){
	var formatter="";
	if(this.tooltipFormatter){
		formatter+=" ,formatter: \"{a} <br/>{b} : {c} ({d}%)\" ";
	}
	return "tooltip: {  trigger: '"+this.tooltip+"'  "+formatter+" }, ";
}

PrivateEchart.prototype.getLegend=function(){
	var legendArr=this.legendName;
	var data="";
	for(var i=0;i<legendArr.length;i++){
		data+="{ name:'"+legendArr[i]+"', textStyle:{fontSize:'40px'}}";
		if(i<legendArr.length-1){
			data+=",";
		}
	}
	
	var otherJson="";
	
	if(this.pieLegendOrient!=""){
		otherJson+=",orient : '"+this.pieLegendOrient+"'";
	}
	if(this.pieLegendPostion!=""){
		otherJson+=",x : '"+this.pieLegendPostion+"' ,y:'bottom',";
	}
	
	return "legend: {data:["+data+"] "+otherJson+" }, ";
}

PrivateEchart.prototype.getCalculable=function(){
	return "calculable: "+this.calculable+",";
}

PrivateEchart.prototype.getToolBox=function(){
	return "toolbox: {show : "+this.toolbox+",feature : { mark : {show: true},dataView : {show: true, readOnly: false},saveAsImage : {show: true}}},";
}

PrivateEchart.prototype.getXaxis=function(){
	var data="";
	var num=(this.xaisData).length;
    $.each(this.xaisData, function(i,obj){
    	data+="'"+obj+"'";
    	if(i<num-1){
    		data+=",";
    	}
    })
	return "xAxis : [{type : 'category',data : ["+data+"]  } ], ";
}

PrivateEchart.prototype.getYaxis=function(){
	var jsonOther="";
	var otherYaxis="";
	if(this.yaxisName!=""){
		jsonOther+=",name : '"+this.yaxisName+"'";
	}
	if(this.yaxisUnit!=""){
		jsonOther+=",axisLabel :{  formatter: '{value}"+this.yaxisUnit+"'}";
	}
	if(this.otherYaxisName!=""){
		otherYaxis+=",{type : 'value',name : '"+this.otherYaxisName+"'";
	}
	if(this.otherYaxisUnit!=""){
		otherYaxis+=",axisLabel :{  formatter: '{value}"+this.otherYaxisUnit+"'}}";
	}
	
	
	return "yAxis : [{type : 'value'  "+jsonOther+"}  "+otherYaxis+" ], ";
}

PrivateEchart.prototype.getPieSeries=function(){
	var data=" ,backgroundColor: '#fafafa',data:[{value:335, name:'东城分公司'}, {value:310, name:'西城分公司'},{value:234, name:'朝一分公司'},{value:135, name:'海淀分公司'},{value:1548, name:'丰台分公司'}]";
	var series="series :[{name:'"+this.pieName+"',type:'pie',radius:'"+this.pieRadius+"',center: ['"+this.pieCenter[0]+"', '"+this.pieCenter[1]+"'] "+data+"}]";
	
	return series;
	
}

PrivateEchart.prototype.getScatterSeries=function(){
	var fieldArray=this.legendField;
	var nameArray=this.legendName;
	var typeArray=this.legendType;
	var dataJson=this.chartData;
	
	var series="";
	for(var i=0;i<nameArray.length;i++){
		var field=fieldArray[i];
		var name=nameArray[i];
		var type=typeArray[i];
		var data="";
		$.each(dataJson, function(iy,result){
			  data+="["+result[field]+"],";
		})
		data=data.substr(0, data.length-1);
	}
	return "series :["+series+"]";
}

PrivateEchart.prototype.getSeries=function(){
	var stackStr=this.stackArray.join(",");
	var typeArray=this.legendType;
	var fieldArray=this.legendField;
	var nameArray=this.legendName;
	var targetField=this.targetField;
	var dataJson=this.chartData;
	var xAsia=this.xaisData;
	
	var series="";
	for(var i=0;i<nameArray.length;i++){
		var field=fieldArray[i];
		var name=nameArray[i];
		var type=typeArray[i];
		var data="";
		$.each(xAsia, function(ix,obj){
			var xdata=obj;
			
			$.each(dataJson, function(iy,result){
				var targetNum=result[targetField];
				var yNum=result[field];
				if(xdata==targetNum){
					data+=yNum+",";
				}
				
			})
			
		})
		data=data.substr(0, data.length-1);
		
		var yAxisIndex="";
		
		if(this.yaxisPostion[i]=="1"){
			yAxisIndex+=",yAxisIndex: 1";
		}
		var line="";
		if(this.markLine.length>0&&this.markLine[i]=="true"){
			line = " ,markLine : { "+
			"                data : [ "+
			"                    {type : 'average', name: '平均值'} "+
			"                ] "+
			"            } ";
		}
		var point="";
		if(this.markPoint.length>0&&this.markPoint[i]=="true"){
			point=  " ,markPoint : { "+
			"                data : [ "+
			"                    {type : 'max', name: '最大值'}, "+
			"                    {type : 'min', name: '最小值'} "+
			"                ] "+
			"            } ";
		}
		
		var stackAppend="";
		if(stackStr.indexOf(name)!=-1){
			stackAppend="stack: '"+this.stack+"',";
		}
		
		
		series+="{name:'"+name+"',type:'"+type+"',"+stackAppend+" data:["+data+"] "+yAxisIndex+point+line+" }";
		
		if(i<nameArray.length-1){
	    	series+=",";
		}
	}
	return "series :["+series+"]";
}







//前台图形渲染界面， locat 路径信息  option必须为字符串方式
function renderChart(locat,id,option){
        	require.config({
                packages: [
                    {
                        name: 'echarts',
                        location: locat+'/plugins/echart/src',
                        main: 'echarts'
                    },
                    {
                        name: 'zrender',
                        location: locat+'/plugins/echart/zrender',
                        main: 'zrender'
                    }
                ]
            });
               require(
                    [
                        'echarts',
                        'echarts/chart/line',
                        'echarts/chart/bar',
                        'echarts/chart/pie',
                        'echarts/chart/gauge'
                    ],
                    function (ec) {
                    	if(document.getElementById(id)==undefined){
                    		return false;
                    	}
                        var myChart = ec.init(document.getElementById(id));
                        myChart.setOption(eval("(" + option + ")"));
                        
                    }
                    
                   
                )
                
                

}








function renderChartJson(locat,id,option){
	require.config({
        packages: [
            {
                name: 'echarts',
                location: locat+'/plugins/echart/src',
                main: 'echarts'
            },
            {
                name: 'zrender',
                location: locat+'/plugins/echart/zrender',
                main: 'zrender'
            }
        ]
    });
       require(
            [
                'echarts',
                'echarts/chart/line',
                'echarts/chart/bar',
                'echarts/chart/pie',
                'echarts/chart/gauge'
            ],
            function (ec) {
            	if(document.getElementById(id)==undefined){
            		return false;
            	}
                var myChart = ec.init(document.getElementById(id));
                myChart.setOption(option);
            }
        )

}

function GaugueChart(){
	this.leftMax=0;
	this.leftMin=0;
	this.leftName="";
	this.leftSplitNumber=0;
	this.leftValue=0;
	this.leftDataName="";
	
	
	this.rightMax=0;
	this.rightMin=0;
	this.rightName="";
	this.rightSplitNumber=0;
	this.rightValue=0;
	this.rightDataName="";
	
	this.centerMax=0;
	this.centerMin=0;
	this.centerName="";
	this.centerSplitNumber=0;
	this.centerValue=0;
	this.centerDataName="";
	
	this.title="";
	

	
}

GaugueChart.prototype.getGaugueOption=function(){
	var option="{ "+
	"    	    tooltip : { "+
	"    	        formatter: '{a} <br/>{c} {b}' "+
	"    	    },title : { text: '"+this.title+"', x:'center'}, "+
	"    	    series : [ "+
	"    	        { "+
	"    	            name:'"+this.centerName+"', "+
	"    	            type:'gauge', "+
	"    	            z: 3, "+
	"    	            min:0, "+
	"    	            max:"+this.centerMax+", "+
	"    	            splitNumber:"+this.centerSplitNumber+", "+
	"    	            axisLine: {             "+
	"    	                lineStyle: {        "+
	"    	                    width: 10 "+
	"    	                } "+
	"    	            }, "+
	"    	            axisTick: {             "+
	"    	                length :15,         "+
	"    	                lineStyle: {        "+
	"    	                    color: 'auto' "+
	"    	                } "+
	"    	            }, "+
	"    	            splitLine: {            "+
	"    	                length :20,          "+
	"    	                lineStyle: {        "+
	"    	                    color: 'auto' "+
	"    	                } "+
	"    	            }, "+
	"    	            title : { "+
	"    	                textStyle: {        "+
	"    	                    fontWeight: 'bolder', "+
	
	"    	                    fontStyle: 'italic' "+
	"    	                } "+
	"    	            }, "+
	"    	            detail : { "+
	"    	                textStyle: {        "+
	"    	                    fontWeight: 'bolder' "+
	"    	                } "+
	"    	            }, "+
	"    	            data:[{value: "+this.centerValue+", name: '"+this.centerDataName+"'}] "+
	"    	        }, "+
	"    	        { "+
	"    	            name:'"+this.leftName+"', "+
	"    	            type:'gauge', "+
	"    	            center : ['20%', '55%'],     "+
	"    	            radius : '50%', "+
	"    	            min:0, "+
	"    	            max:"+this.leftMax+", "+
	"    	            endAngle:45, "+
	"    	            splitNumber:"+this.leftSplitNumber+", "+
	"    	            axisLine: {             "+
	"    	                lineStyle: {        "+
	"    	                    width: 8 "+
	"    	                } "+
	"    	            }, "+
	"    	            axisTick: {             "+
	"    	                length :12,         "+
	"    	                lineStyle: {        "+
	"    	                    color: 'auto' "+
	"    	                } "+
	"    	            }, "+
	"    	            splitLine: {            "+
	"    	                length :20,          "+
	"    	                lineStyle: {        "+
	"    	                    color: 'auto' "+
	"    	                } "+
	"    	            }, "+
	"    	            pointer: { "+
	"    	                width:5 "+
	"    	            }, "+
	"    	            title : { "+
	"    	                offsetCenter: [0, '-50%'],        "+
	"    	            }, "+
	"    	            detail : { "+
	"    	                textStyle: {        "+
	"    	                    fontWeight: 'bolder' "+
	"    	                } "+
	"    	            }, "+
	"    	            data:[{value: "+this.leftValue+", name: '"+this.leftDataName+"'}] "+
	"    	        }, "+
	"    	        { "+
	"    	            name:'"+this.rightName+"', "+
	"    	            type:'gauge', "+
	"    	            center : ['75%', '55%'],     "+
	"    	            radius : '50%', "+
	"    	            min:0, "+
	"    	            max:"+this.rightMax+", "+
	"                   startAngle:120, "+
	"    	            endAngle:-45, "+
	"    	            splitNumber:"+this.rightSplitNumber+", "+
	"    	            axisLine: {             "+
	"    	                lineStyle: {        "+
	"    	                    color: [[0.2, '#228b22'],[0.8, '#48b'],[1, '#ff4500']],  "+
	"    	                    width: 8 "+
	"    	                } "+
	"    	            }, "+
	"    	            axisTick: {             "+
	"    	                splitNumber:5, "+
	"    	                length :10,         "+
	"    	                lineStyle: {        "+
	"    	                    color: 'auto' "+
	"    	                } "+
	"    	            }, "+
	"    	            splitLine: {           "+
	"    	                length :15,          "+
	"    	                lineStyle: {        "+
	"    	                    color: 'auto' "+
	"    	                } "+
	"    	            }, "+
	"    	            pointer: { "+
	"    	                width:2 "+
	"    	            }, "+
	"    	            title : { "+
	"    	                show: false "+
	"    	            }, "+
	"    	            detail : { "+
	"    	                show: false "+
	"    	            }, "+
	"    	            data:[{value: "+this.rightValue+", name: '"+this.rightDataName+"'}] "+
	"    	        } "+
	"    	    ] "+
	"    	} ";
	
	return option;
}




