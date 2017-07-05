;(function($){
/**
 * jqGrid Chinese Translation for v4.2
 * henryyan 2011.11.30
 * http://www.wsria.com
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
 *
 * update 2011.11.30
 *		add double u3000 SPACE for search:odata to fix SEARCH box display err when narrow width from only use of eq/ne/cn/in/lt/gt operator under IE6/7
**/
$.jgrid = {
	defaults : {
		recordtext: "{0} - {1}\u3000共 {2} 条",	// 共字前是全角空格
		emptyrecords: "无数据显示",
		loadtext: "读取中...",
		pgtext : " {0} 共 {1} 页",
		datatype: "json",
		jsonReader: {repeatitems: false},
		prmNames: {page:"queryParam.currentPage", rows:"queryParam.maxRows", sort: "queryParam.sortIndex",order: "queryParam.sortOrder"},
		height: "100%",
		autowidth: false,
		multiselect: true,
		viewrecords: true,
		loadComplete: function() {
			$('tr.jqgrow:odd', this).addClass('myAltRowClass');//加入奇偶行效果
			//改变多重表头样式column group的效果样式
			$('#gbox_' + this.id + ' th.ui-th-column-header[role="columnheader"]').removeClass('ui-state-default').addClass('myAltColumnHeader');
		},
		loadui: 'disable'  //disable the "loading..." prompt in ajax processing.
	},
	search : {
		caption: "搜索...",
		Find: "查找",
		Reset: "重置",
		odata : ['等于\u3000\u3000', '不等\u3000\u3000', '小于\u3000\u3000', '小于等于','大于\u3000\u3000','大于等于',
			'开始于','不开始于','属于\u3000\u3000','不属于','结束于','不结束于','包含\u3000\u3000','不包含','空值于\u3000\u3000','非空值'],
		groupOps: [	{ op: "AND", text: "所有" },	{ op: "OR",  text: "任一" }	],
		matchText: " 匹配",
		rulesText: " 规则"
	},
	edit : {
		addCaption: "添加记录",
		editCaption: "编辑记录",
		bSubmit: "提交",
		bCancel: "取消",
		bClose: "关闭",
		saveData: "数据已改变，是否保存？",
		bYes : "是",
		bNo : "否",
		bExit : "取消",
		msg: {
			required:"此字段必需",
			number:"请输入有效数字",
			minValue:"输值必须大于等于 ",
			maxValue:"输值必须小于等于 ",
			email: "这不是有效的e-mail地址",
			integer: "请输入有效整数",
			date: "请输入有效时间",
			url: "无效网址。前缀必须为 ('http://' 或 'https://')",
			nodefined : " 未定义！",
			novalue : " 需要返回值！",
			customarray : "自定义函数需要返回数组！",
			customfcheck : "Custom function should be present in case of custom checking!"

		}
	},
	view : {
		caption: "查看记录",
		bClose: "关闭"
	},
	del : {
		caption: "删除",
		msg: "删除所选记录？",
		bSubmit: "删除",
		bCancel: "取消"
	},
	nav : {
		edittext: "",
		edittitle: "编辑所选记录",
		addtext:"",
		addtitle: "添加新记录",
		deltext: "",
		deltitle: "删除所选记录",
		searchtext: "",
		searchtitle: "查找",
		refreshtext: "",
		refreshtitle: "刷新表格",
		alertcap: "注意",
		alerttext: "请选择记录",
		viewtext: "",
		viewtitle: "查看所选记录"
	},
	col : {
		caption: "选择列",
		bSubmit: "确定",
		bCancel: "取消"
	},
	errors : {
		errcap : "错误",
		nourl : "没有设置url",
		norecords: "没有要处理的记录",
		model : "colNames 和 colModel 长度不等！"
	},
	formatter : {
		integer : {thousandsSeparator: " ", defaultValue: '0'},
		number : {decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2, defaultValue: '0.00'},
		currency : {decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2, prefix: "", suffix:"", defaultValue: '0.00'},
		date : {
			dayNames:   [
				"Sun", "Mon", "Tue", "Wed", "Thr", "Fri", "Sat",
		         "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
			],
			monthNames: [
				"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
				"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
			],
			AmPm : ["am","pm","AM","PM"],
			S: function (j) {return j < 11 || j > 13 ? ['st', 'nd', 'rd', 'th'][Math.min((j - 1) % 10, 3)] : 'th'},
			srcformat: 'Y-m-d',
			newstformat: 'Y-m-d H:i:s',
			masks : {
				ISO8601Long:"Y-m-d H:i:s",
				ISO8601Short:"Y-m-d",
				ShortDate: "Y/j/n",
				LongDate: "l, F d, Y",
				FullDateTime: "l, F d, Y g:i:s A",
				MonthDay: "F d",
				ShortTime: "g:i A",
				LongTime: "g:i:s A",
				SortableDateTime: "Y-m-d\\TH:i:s",
				UniversalSortableDateTime: "Y-m-d H:i:sO",
				YearMonth: "F, Y"
			},
			reformatAfterEdit : false
		},
		baseLinkUrl: '',
		showAction: '',
		target: '',
		checkbox : {disabled:true},
		idName : 'id'
	}
};


/**
*
* The jqgrid postData recognized by format: { name1: value1, name2: value2 ...}
* The function is jquery extents function to serialize all value and name of input tags <br/>
* which be contained one designated parent tag.
* For example: <br/>
*  var params = $('#oneFormId').serializeJson(); <br/>
	$('#oneGridId').jqGrid('setPostData', params);<br/>
*
* @returns jqgrid format post data js object. If there is not input tag found, it will return object {}.
*/
$.fn.serializeJqgrid = function() {
	var arr = this.serializeArray();
	if(arr.length > 0) {
		var ret = "";
		for (i = 0; i < arr.length; i++)  {
			if(arr[i].value != null && arr[i].value.length > 0) {
				ret += '"' + arr[i].name + '":"' +  arr[i].value + '",';
			}
		}
		if(ret.length > 0) {
			return eval( '({' + ret.substr(0, ret.length - 1) + '})');
		}
	}
	return eval('({})');
};

/**
 * function : 自定义格式化JqGrid列表数值
 * v : 数值, n : 显示小数点后的有效数字长度
 * author : tan
 */
$.fn.formatNumByGrid = function(v,n) {
	if(v==null){
		//如果数值为空,则显示为空
		return '&nbsp;';
	}
	//验证是否为数字,非数字则如实显示
	var pattern = /^\d+(\.\d+)?$/;
    if(!pattern.test(v)){
    	return v;
    }
	//如果是数字,则格式化保留小数点后两位
	if(v == 0){
		return 0;
	}else{
		var val = parseFloat(v);
		return val.toFixed(n);
	}
};

})(jQuery);

//Automatic set JQGrid table width to fit with client area.
function autoWidthJqgrid() {
	var grids = $('.ui-jqgrid-btable:visible');
    if (grids.size() > 0) {
        grids.each(function(index) {
            var gridId = $(this).attr('id');
            var gridParent = $('#gbox_' + gridId).parent();
            //改变表头样式
            $('#gbox_' + gridId + ' th.ui-th-column[role="columnheader"]').removeClass('ui-state-default').addClass('myAltColumnHeader');
            gridParent.resize(function(e){
            	$('#' + gridId).setGridWidth(gridParent.innerWidth() - 2);
            });
          //改变多重表头样式column group的效果样式
    		$('#gbox_' + gridId + ' th.ui-th-column-header[role="columnheader"]').removeClass('ui-state-default').addClass('myAltColumnHeader');
        });
    }
};

//Automatic set JQGrid table width to fit with client area.
function autoJqgridStyle() {
	var grids = $('.ui-jqgrid-btable:visible');
    if (grids.size() > 0) {
        grids.each(function(index) {
            var gridId = $(this).attr('id');
            var gridParent = $('#gbox_' + gridId).parent();
            //改变表头样式
            $('#gbox_' + gridId + ' th.ui-th-column[role="columnheader"]').removeClass('ui-state-default').addClass('myAltColumnHeader');
            //改变多重表头样式column group的效果样式
    		$('#gbox_' + gridId + ' th.ui-th-column-header[role="columnheader"]').removeClass('ui-state-default').addClass('myAltColumnHeader');
        });

    }

};

//Automatic set JQGrid table width to fit with client area.
function autoJqgridLineBreaksStyle() {
	var grids = $('.ui-jqgrid-btable:visible');
    if (grids.size() > 0) {
        grids.each(function(index) {
            var gridId = $(this).attr('id');
            var gridParent = $('#gbox_' + gridId).parent();
            /**表格折行**/
            $('#gbox_' + gridId + ' div.ui-jqgrid-sortable').removeClass('ui-jqgrid-sortable').addClass('ui-jqgrid-sortable_line_breaks');
            /**小计 合计 统一修改样式*/
            /**小计折行 **/
            //$('tr.jqfoot[role="row"]').removeClass('jqfoot').addClass('jqfoot_line_breaks');
            /**合计折行**/
            //$('tr.footrow').removeClass('footrow').addClass('footrow-line-breaks');
        });

    }

};
///小计合计完成率
function  initTotalRate(context,jqgirdId,jsonList){

	initSubTotal(context,jqgirdId,jsonList);
	initTotal(context,jqgirdId,jsonList);
}
//合计红绿灯完成率
function initTotal(context,jqgirdId,jsonList){
	$('.ui-jqgrid-view').each(function(){
		$('.footrow', this).each(function(){
			var flag = true;
			for(var i =0 ;i < jsonList.length; i++){
				var pararatename = jsonList[i].rateName;//itemArray.split('-')[0];//完成率参数名称
				var paramplan = jsonList[i].plan;//计划参数名称
				var paramactual = jsonList[i].actual;//实际完成率参数名称
				var standardFlag = jsonList[i].comapFlag;//比较参数符号 > <
				var standardRate = jsonList[i].standardRate;//标准比率
				var palnVal = $('td[aria-describedby='+jqgirdId+'_'+paramplan+']',this).html();
				var actualVal = $('td[aria-describedby='+jqgirdId+'_'+paramactual+']',this).html();
				//实际/和计划 两个 为空 或者 值 为0 红灯
				if(palnVal==null || $.trim(palnVal) == '' || parseFloat(palnVal)  == 0){
					$('td[aria-describedby='+jqgirdId+'_'+pararatename+']', this).html("100.00");
					continue;
				}
				var compareRate = comparRate(actualVal.replace(/,/g,""),palnVal.replace(/,/g,""),2);
				$('td[aria-describedby='+jqgirdId+'_'+pararatename+']', this).html(compareRate);
				if(eval(parseFloat(compareRate)+standardFlag+parseFloat(standardRate))){
					flag = false;
				}
			}
			if(!flag){
				   $('td', this).eq(0).html('<img title="异常" src="'+context+'/images/redlight.png"></img>');

				}else{
				 $('td', this).eq(0).html('<img title="正常" src="'+context+'/images/greenlight.png"></img>');
				}

	 });
});
}
//小计红绿灯完成率
function initSubTotal(context,jqgirdId,jsonList){
$('.ui-jqgrid-view').each(function(){
		$('.jqfoot', this).each(function(){
			var flag = true;
			for(var i =0 ;i < jsonList.length; i++){
				var pararatename = jsonList[i].rateName;//itemArray.split('-')[0];//完成率参数名称
				var paramplan = jsonList[i].plan;//计划参数名称
				var paramactual = jsonList[i].actual;//实际完成率参数名称
				var standardFlag = jsonList[i].comapFlag;//比较参数符号 > <
				var standardRate = jsonList[i].standardRate;//标准比率
				var palnVal = $('td[aria-describedby='+jqgirdId+'_'+paramplan+']',this).html();
				var actualVal = $('td[aria-describedby='+jqgirdId+'_'+paramactual+']',this).html();
				//实际/和计划 两个 为空 或者 值 为0 红灯
				if(palnVal==null || $.trim(palnVal) == '' || parseFloat(palnVal)  == 0){
					$('td[aria-describedby='+jqgirdId+'_'+pararatename+']', this).html("100.00");
					continue;
				}
				var compareRate = comparRate(actualVal.replace(/,/g,""),palnVal.replace(/,/g,""),2);
				$('td[aria-describedby='+jqgirdId+'_'+pararatename+']', this).html(compareRate);
				if(eval(parseFloat(compareRate)+standardFlag+parseFloat(standardRate))){
					flag = false;
				}
			}
			if(!flag){
				   $('td', this).eq(0).html('<img title="异常" src="'+context+'/images/redlight.png"></img>');

				}else{
				 $('td', this).eq(0).html('<img title="正常" src="'+context+'/images/greenlight.png"></img>');
				}

	 });
});
}
function exportExcelByJqGrid(id,urlExport){
	var colsName = getColName(id);
	var colsLable = getColLabel(id);
	jQuery("#"+id).excelExport({url:urlExport,colsName:colsName,colsLable:colsLable});
}
function getColName(id){
	var b = jQuery("#"+id)[0];
	var params = b.p.colModel;
	var cols ="";
		for ( var i = 1; i < params.length; i++) {
			if(params[i].name!='id'){
			cols += "," + params[i].name;
			}
		}
	cols=cols.substr(1,cols.length);
	return cols;
}
function getColLabel(id){
	var b = jQuery("#"+id)[0];
	var params = b.p.colModel;
	var cols = "";
		for ( var i = 1; i < params.length; i++) {
			if(params[i].name!='id'){
			cols += "," + params[i].label;
			}
		}
		cols=cols.substr(1,cols.length) ;
	return cols;
}
//比率 保留两位小数
function comparRate(v1,v2,n){
	 var comparRate = 0;
        if(v2 == 0){
        	comparRate = (v1*100/1).toFixed(n);
        }else{
        	comparRate = (v1*100/v2).toFixed(n);
        }
		return comparRate;
}