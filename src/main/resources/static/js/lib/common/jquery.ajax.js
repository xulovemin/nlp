(function($) {
	var defaults = {
		formId : '',//表单ID
		url : null,//url地址
		callback : '',//回调方法
		params : null,//参数
		valid : true,//是否验证表单 true验证      false不验证
		id : '',//唯一标识 例如单条删除时候传递的ID
		ids : 'Ids',//多条删除时候查询ID的标识
		fill : true,//是否填充表单 true填充	false不填充
		type : 'POST',
		async : true, //是否同步提交 false为同步 默认异步
		dataType : 'json',//数据类型
		cache : true,//设置为 false 将不会从浏览器缓存中加载请求信息
		isloading: true,// 设置为false将不提示数据正在加载
		showMessage : true//是否显示提示信息
	};
	//封装AJAX保存方法 支持同步和url
	jQuery.extend({
		ajax_ : function(options){
			var opts = $.extend({}, defaults, options);
			//判断是否验证表单
			if(opts.valid){
				if(!$("#"+opts.formId).valid()){
					return false;
				}
			}
			//提交
			if (opts.isloading) {
				$.isLoading({text : "玩命处理中……"});
			}
			jQuery.ajax({
				url : basePath + opts.url,
				data: opts.params,
				type : opts.type,
				async: opts.async,
				dataType : opts.dataType,
				success : function(data) {
					if (opts.isloading) {
						$.isLoading("hide");
					}
					//提示信息
					if(opts.showMessage){
						if(data.resultMessage){
							new $.Zebra_Dialog(data.resultMessage, {
							    'buttons':  false,
							    'modal': false,
							    'type': 'confirmation',
							    'position': ['right - 20', 'top + 20'],
							    'auto_close': 2000
							});
						}
					}
					
					if(opts.callback){
						opts.callback(data);
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					if (opts.isloading) {
						$.isLoading("hide");
					}
					new $.Zebra_Dialog('系统异常', {
			            'type':     'error',
			            'position': ['right - 20', 'top + 20'],
			            'title':    '系统提示'
			        });
				}
			});
		},
		//封装AJAX保存方法
		saveObj : function(options) {
			var opts = $.extend({}, defaults, options);
			//判断是否验证表单
			if(opts.valid){
				if(!$("#"+opts.formId).valid()){
					return false;
				}
			}
			//提交
			if (opts.isloading) {
				$.isLoading({text : "玩命处理中……"});
			}
			var ajax_option = {data:opts.params, type:opts.type, success:function(data){
				if (opts.isloading) {
					$.isLoading("hide");
				}
				//返回成功
				if (data.resultState) {
					if(opts.showMessage){
						new $.Zebra_Dialog(data.resultMessage, {
						    'buttons':  false,
						    'modal': false,
						    'type': 'confirmation',
						    'position': ['right - 20', 'top + 20'],
						    'auto_close': 2000
						});
					}
					if(opts.callback){
						opts.callback(data);
					}
				} 
				//返回失败
				else {
					if(opts.showMessage){
						new $.Zebra_Dialog(data.resultMessage, {
				            'type':     'error',
				            'position': ['right - 20', 'top + 20'],
				            'title':    '系统提示'
				        });
					}
					//后台验证表单失败
					if (data.formErrorMessage) {
						common.showErrors(opts.formId, data.formErrorMessage);
					}
				}
			}, error:function(XmlHttpRequest, textStatus, errorThrown){
				if (opts.isloading) {
					$.isLoading("hide");
				}
				new $.Zebra_Dialog('系统异常', {
		            'type':     'error',
		            'position': ['right - 20', 'top + 20'],
		            'title':    '系统提示'
		        });
			}};
			$("#"+opts.formId).ajaxSubmit(ajax_option);
		
		},
		//封装AJAX get方法
		getObj : function(options){
			var opts = $.extend({}, defaults, options);
			if (opts.isloading) {
				$.isLoading({text : "玩命处理中……"});
			}
			$.getJSON(basePath + opts.url, opts.params, function(data) {
				if (opts.isloading) {
					$.isLoading("hide");
				}
				//判断是否填充表单
				if(opts.fill){
					$("#"+opts.formId).autofill(data);
				}
				if(opts.callback){
					opts.callback(data);
				}
			}).fail(function(data, status, xhr) {
				new $.Zebra_Dialog('系统异常', {
		            'type':     'error',
		            'position': ['right - 20', 'top + 20'],
		            'title':    '系统提示'
		        });
			}).done(function(d) {
	        }).always(function(d) {
	        });
		},
		//封装标准删除方法
		delObj : function(options) {
			var opts = $.extend({}, defaults, options);
			var ids = String(opts.id);
			if (ids == 0) {
				var idsArr = [];
				$("[name='"+opts.ids+"']:checked").each(function() {
					idsArr.push($(this).val());
				});
				ids = idsArr.join(",");
			}
			if(ids.length > 0){
				bootbox.confirm("确认删除吗?", function(result) {
					if(result) {
						$.post(basePath + opts.url, {id:ids}, function(data) {
							if(opts.showMessage){
								new $.Zebra_Dialog(data.resultMessage, {
								    'buttons':  false,
								    'modal': false,
								    'position': ['right - 20', 'top + 20'],
								    'auto_close': 2000
								});
							}
							if (data.resultState) {
								if(opts.callback){
									opts.callback(data);
								}
							}
						}, "json");
					}
				});
			} else {
				bootbox.alert("请选择要删除的记录");
			}
		},
    });
})(jQuery);