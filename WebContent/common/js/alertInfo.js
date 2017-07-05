function alertInfo(type,msg,tvar,funName){
			if(1==tvar){
				$.artDialog({lock: true,title: '提示',icon: type,content: msg,ok:function(){
					eval(funName+"()");
				}});
			}else{
				$.artDialog({lock: true,title: '提示',icon: type,content: msg,time: 5,button: [
				  {
				       name: '确定',
				       callback: function () {
				        return true
				       },
				       focus: true
				     }
				]});
			}
	}
function alertInfo1(type,msg,tvar,funName){
	if(1==tvar){
		$.artDialog({lock: true,title: '提示',icon: type,content: msg,ok:function(){
			eval(funName+"()");
		}});
	}else{
		$.artDialog({lock: true,title: '提示',icon: type,content: msg,button: [
		  {
		       name: '确定',
		       callback: function () {
		        return true
		       },
		       focus: true
		     }
		]});
	}
}
function artInfoConfirm(msg, okfun){
    var artDlg = $.artDialog({
        title: "确认信息",
        lock: true,
        icon: "ask",
        content: msg,
        ok: okfun,
        okVal: '是',
        cancelVal: '否',
        cancel: true
    });
    return artDlg;
}
function inoperative(records){
	 for(index in records){
			if(records[index] == null){
				alertInfo("warning","【合计】不能进行任何操作！");
				return true;
			};
		 }
}
// 比较时间
function compareDate(startD,endD,i){
    var str = parseFloat((Date.parse(endD.replace(/-/g, "/")) - Date.parse(startD.replace(/-/g, "/")))/(24*60*60*1000)).toFixed(0);
    if (str <= i && str >= 0) {
        return true;
    }
    return false;
}