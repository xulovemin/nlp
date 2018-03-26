//全局common js
var common = {
	//显示后台返回的表单验证错误信息
	showErrors : function(formId, errorMessage) {
		$.each(errorMessage, function(i, o) {
			var parent_ = $("#" + formId + " [id='" + o.code + "']").parent();
			var label_ = parent_.children("label");
			if (label_.length == 0) {
				$("<label>").attr("for", o.code).addClass("error").html(
						o.message || "").insertAfter(
						$("#" + formId + " [id='" + o.code + "']"));
			} else {
				label_.html(o.message);
				label_.css('display', 'block');
			}
		});
	},
	//清空表单验证错误信息
	hideErrors : function(){
		$("label.error").remove();
		$(".error").removeClass("error");
	},
	//初始化表单信息
	initForm : function(formId){
		$("#"+formId).resetForm();
		$("#"+formId +" #id").val(0);
		common.hideErrors();
	},
	//显示信息
	showSuccessMessage : function(message) {
		var o = $("#alertMessage");
		if(o.length > 0){
			o.html(message);
		} else {
			var div = $('<div></div>').addClass('alert alert-block alert-success');
			var btn = $("<button type='button' class='close' data-dismiss='alert'><i class='ace-icon fa fa-times'></i></button>");
			var i = $('<i></i>').addClass('ace-icon fa fa-check green');
			var span = $("<span id='alertMessage'>"+message+"</span>");
			div.append(btn).append(i).append(span);
			div.insertBefore($("#alertDiv"));
		}
	},
	//将表单序列化数组转成json格式
	serializeJson : function(array){
	    var serializeObj={};
	    $(array).each(function(){
	        if(serializeObj[this.name]){
	            if($.isArray(serializeObj[this.name])){
	                serializeObj[this.name].push(this.value);
	            }else{
	                serializeObj[this.name]=[serializeObj[this.name],this.value];
	            }
	        }else{
	            serializeObj[this.name]=this.value;
	        }
	    });
	    return serializeObj;
	},
	//为datatables传参
	bindParams : function(formId, aoData){
		var obj = $("#"+formId).serializeArray();
		$.each(obj, function(i,o) {
			if(o.value != null && o.value != ''){
				aoData.push(o);
			}
		});
		return aoData;
	},
	init : function(){
		//根据name获取json数组对应的value
		Array.prototype.getValue = function (name){
			for (var i=0; i < this.length; i++) {
		        if (this[i].name == name) {
		            return this[i].value;
		        }
		    }
		};
		//处理全局ajax请求session超时
		$.ajaxSetup({
	        contentType:"application/x-www-form-urlencoded;charset=utf-8",
	        complete:function(XMLHttpRequest,textStatus){
	        	if(typeof(XMLHttpRequest.getResponseHeader)=="function"){
	                var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
	                if(sessionstatus == "timeout"){
	                    window.location = basePath+"/login";
	                }  
	        	}
	        }   
	    });
	},
	//初始化全局绑定事件
	initEvent : function(){
		//初始化表格ID删除绑定事件
		$(document).on('click','th input:checkbox',function() {
			var that = this;
			$(this).closest('table').find('tr > td:first-child input:checkbox').each(
				function() {
					this.checked = that.checked;
					$(this).closest('tr').toggleClass('selected');
				});
		});
		
		
	},
	//初始化
	contextInit : function(){
		common.init();
		common.initEvent();
	}
};

jQuery(function($) {
	common.contextInit();
});