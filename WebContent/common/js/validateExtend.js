 $.extend($.fn.validatebox.defaults.rules, {    
			mobile: {    
		        validator: function(value){    
					            return (/^1[3|4|5|8][0-9]\d{4,8}$/.test(value)) && value.length==11;    
					        },    
		        message: '不是完整的11位手机号'   
		    }, 
		    // 验证中文
		    CHS: {
		        validator: function (value) {
					            return /^[\u0391-\uFFE5]+$/.test(value);
					        },
		        message: '只能输入汉字'
		    },
		    // 验证英语
		    english : {
		    	validator : function(value) {
					    		return /^[A-Za-z]+$/i.test(value);
					    	},
		    	message : '请输入英文'
		    },
		    //国内邮编验证
		    zipcode: {
		        validator: function (value) {
					            var reg = /^[1-9]\d{5}$/;
					            return reg.test(value);
					        },
		        message: '邮编必须是非0开始的6位数字.'
		    },
		    // 验证IP地址
		    ip : {
		    	validator : function(value) {
		    					return /\d+\.\d+\.\d+\.\d+/.test(value);
		    				},
		    	message : 'IP地址格式不正确'
		    },
		    //座机电话
		    tel:{
		    	validator:function(value,param){
					    		return /^(\d{3}-|\d{4}-)?(\d{8}|\d{7})?(-\d{1,6})?$/.test(value);
					    	},
		    	message:'电话号码不正确'
		    },
		    //联系电话
		    mobileAndTel: {
		    	validator: function (value, param) {
					    		return /(^([0\+]\d{2,3})\d{3,4}\-\d{3,8}$)|(^([0\+]\d{2,3})\d{3,4}\d{3,8}$)|(^([0\+]\d{2,3}){0,1}13\d{9}$)|(^\d{3,4}\d{3,8}$)|(^\d{3,4}\-\d{3,8}$)/.test(value);
					    	},
		    	message: '请正确输入电话号码'
		    },
		    //数组
		    number: {
		    	validator: function (value, param) {
					    		return /^[0-9]+.?[0-9]*$/.test(value);
					    	},
		    	message: '请输入数字'
		    },
		    //金额
		    money:{
		    	validator: function (value, param) {
					    		return (/^(([1-9]\d*)|\d)(\.\d{1,2})?$/).test(value);
					    	},
		    	message:'请输入正确的金额'

		    },
		    //整数或数
		    mone:{ 
		    	validator: function (value, param) {
					    		return (/^(([1-9]\d*)|\d)(\.\d{1,2})?$/).test(value);
					    	},
		    	message:'请输入整数或小数'

		    },
		    //整数
		    integ:{
		    	validator:function(value,param){
				    			return /^[+]?[0-9]\d*$/.test(value);
				    	  },
		    	message: '请输入整数'
		    },
		    
		    //匹配日期  2014-1-20  
		    dateRegc:{
		    	 validator:function(value,param){
		    			return /^\1|2\d{3}-\d{1,2}-\d{1,2}$/.test(value);
		    	  },
		    	  message: '日期格式不正确'
		    },
		   
		    //下拉框 必选 校验
		    selectValid:{
		    	validator:function(value,param){
						    	if(value == param[0]){
						    		return false;
						    	}else{
						    		return true ;
						    	}
					    	},
		    	message:'请选择'
		    },
	
		    idCode:{
		    	validator:function(value,param){
					    		return /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(value);
					    	},
		    	message: '请输入正确的身份证号'
		    },
		    //登录名称只允许汉字、英文字母、数字及下划线
		    loginName: {
		    	validator: function (value, param) {
 					    		return /^[\u0391-\uFFE5\w]+$/.test(value);
					    	},
		    	message: '登录名称只允许汉字、英文字母、数字及下划线。'
		    },
		    //最多保留两位小数
		    xiaoshuTo:{      
		    	validator : function(value){  
					    		return /^(([1-9]+)|([0-9]+\.[0-9]{1,2}))$/.test(value);
					    	}, 
		    	message : '最多保留两位小数！' 
		    },
		   
		    equalTo: { 
		    	validator: function (value, param) { 
		    					return $(param[0]).val() == value; 
		    			   }, 
		    	message: '两次输入密码不匹配！' 
		    },
		    equal:{
		    	validator: function (value, param){
		    		return value==param;
		    	},
		    	message:'字符不匹配！' 
		    },
		    //经度纬度
		    jingdu:{      
		    	validator : function(value){  
					    		return /^\d{1,3}\.\d{6}$/.test(value);
					    	}, 
		    	message : '只能输入最多三位整数且小数为六位的数！' 
		    }
		    
		}); 