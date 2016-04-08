

//编辑推广渠道弹出框
function toEditExtendChannel(loginaccountId){
	YouGou.UI.Dialog.show({
		title : '修改推广渠道',
		message: function(dialog) {
			var $message = $('<div></div>');
	        var pageToLoad = dialog.getData('pageToLoad');
	        $message.load(pageToLoad);
	        return $message;
		},
        data: {
             'pageToLoad': '/member/toEditExtendChannelUI.sc'
        },
		buttons: [
		    {
				label: '确定',
				cssClass: 'btn-primary',
				action: function() {
					updateExtendChannel(loginaccountId,$(this));
				}
		    }, 
			{
		    	label: '取消',
		    	action: function(dialog) {
		    		dialog.close();
		    	}
			}
	    ]
	});	
}

 //修改推广渠道
 function updateExtendChannel(loginaccountId,_this){
	 	var extensionChannel = $("input[name='extensionChannel']:checked").val();
	 	if(extensionChannel==null || extensionChannel=="")
		{
			$("#extensionChannel_stip").html("请选择推广渠道！");
			return;
		}else{
			YouGou.Ajax.doPost({
				successMsg: "修改推广渠道成功",
		  		  url: "/member/editExtendChannel.sc",
		  		  data: {"loginaccountId":loginaccountId,"extensionChannel":extensionChannel},
		  		  success : function() {
					 window.location.reload();
		  		  }
		   });
		}	
	}
 
 //检验备注
 function checkRemark(){
		var remark=$("#remark").val();
		if (remark.replace(/\s/g,"")=="") {
			YouGou.UI.tipError('备注内容不能为空');
			return false;
		}
		return true;
	}
 
 //提交表单
 function editMember(){	
	 if(!checkRemark()){
		 return;
	 }
	 var loginaccountId = $("#loginaccountId").val();
	 var attention = $("input[name='attention']:checked").val();
	 var memberName = $("#memberName").val();
	 var memberSex = $("input[name='memberSex']:checked").val();
	 var year = $("#year").val();
	 var month = $("#month").val();
	 var date = $("#date").val();
	 var provinceId = $("#provinceId").val();
	 var cityId = $("#cityId").val();
	 var district = $("#countryId").val();
	 var memberAddress = $("#memberAddress").val();
	YouGou.Ajax.doPost({
		successMsg: '会员修改成功!',
		url: '/member/editMember.sc',
		data:{"loginaccountId":loginaccountId,"attention":attention,"memberName":memberName,"memberSex":memberSex,
	  		"year":year,"month":month,"date":date,"provinceId":provinceId,"cityId":cityId,"district":district,"memberAddress":memberAddress},
	  	success : function(data){
	  		window.location.reload();
		}
	});
 }
 
//修改会员积分——弹出框
 function toEditIntegralDialog(loginaccountId,type){
	 var title;
	 if(type==1){
		 title = '赠送积分'; 
	 }else if(type==2){
		 title = '扣减积分';
	 }
 	YouGou.UI.Dialog.show({
 		title : title,
 		message: function(dialog) {
 			var $message = $('<div></div>');
 	        var pageToLoad = dialog.getData('pageToLoad');
 	        $message.load(pageToLoad);
 	        return $message;
 		},
        data: {
             'pageToLoad': '/member/toEditMemberIntegralDialog.sc?loginaccountId='+loginaccountId+'&type='+type
        },
 		buttons: [
 		    {
 				label: '确定',
 				cssClass: 'btn-primary',
 				action: function() {
 					editIntegral_dialog(type);
 				}
 		    }, 
 			{
 		    	label: '取消',
 		    	action: function(dialog) {
 		    		dialog.close();
 		    	}
 			}
 	    ]
 	});	
 }
 
 //保存会员积分的修改——弹出框
 function editIntegral_dialog(type){
	 var msg='';
	 if(type==1){
		 msg='赠送积分操作成功';
	 }else{
		 msg='扣减积分操作成功';
	 }
	//表单验证
	var rules = {
		integralType : { required : true },
		integral : { required : true },
		remark : { maxlength:200,minlength:2 }
	};
	var validator = YouGou.UI.bindFormValidator("updateIntegralForm",rules,{});
	if(!$('#updateIntegralForm').validate().form()){
		return;
	};
	//提交表单
	YouGou.Ajax.doPost({
		successMsg: msg,
		url: '/member/editMemberIntegral.sc',
	  	data: $("#updateIntegralForm").serializeArray(),
	  	success : function(data){
	  		window.location.reload();
		}
	});
}
 
//修复积分——弹出框
 function toRepairIntegralDialog(loginaccountId){
 	YouGou.UI.Dialog.show({
 		title : '修复积分',
 		message: function(dialog) {
 			var $message = $('<div></div>');
 	        var pageToLoad = dialog.getData('pageToLoad');
 	        $message.load(pageToLoad);
 	        return $message;
 		},
        data: {
             'pageToLoad': '/member/toRepairIntegralDialog.sc?loginaccountId='+loginaccountId
        },
 		buttons: [
 		    {
 				label: '执行',
 				cssClass: 'btn-primary',
 				action: function() {
 					repairIntegral_dialog();
 				}
 		    }, 
 			{
 		    	label: '取消',
 		    	action: function(dialog) {
 		    		dialog.close();
 		    	}
 			}
 	    ]
 	});	
 }
 
 //修复积分——保存
 function repairIntegral_dialog(){
	 YouGou.Ajax.doPost({
		successMsg: '修复积分成功',
		url: '/member/repairIntegral.sc',
	  	data: $("#repairIntegralForm").serializeArray(),
	  	success : function(data){
	  		window.location.reload();
		}
	});
 }
 
//解绑手机_弹出框
function toUnbindMobile(registerCheckMobile){
	var loginaccountId = $("#loginaccountId").val();
 	YouGou.UI.Dialog.show({
 		title : '解绑手机',
 		message: function(dialog) {
 			var $message = $('<div></div>');
 	        var pageToLoad = dialog.getData('pageToLoad');
 	        $message.load(pageToLoad);
 	        return $message;
 		},
         data: {
              'pageToLoad': '/member/toUnbindMobile.sc?loginaccountId='+loginaccountId+'&registerCheckMobile='+registerCheckMobile
         },
 		buttons: [
 		    {
 				label: '确定解绑',
 				cssClass: 'btn-primary',
 				action: function() {
 					sureUnBind();
 				}
 		    }, 
 			{
 		    	label: '取消',
 		    	action: function(dialog) {
 		    		dialog.close();
 		    	}
 			}
 	    ]
 	});	
 }

//解绑手机
function sureUnBind(){
	var unbindphoneremark = $("#unbindphoneremark").val();
	var loginaccountSbf = $("#loginaccountSbf").val();
	var loginNames = $("#loginNames").val();
	if(unbindphoneremark.trim() == null || unbindphoneremark.trim() == ""){
		$("#unbindphoneremark_stip").html("请填写解绑原因备注!");
		return ;
	}else{
		YouGou.Ajax.doPost({
			successMsg: "解绑成功",
			errorMsg:'修改失败',
	  		url: "/member/procUnBindPhone.sc",
	  		data:{"loginaccountSbf":loginaccountSbf,"unbindphoneremark":unbindphoneremark,"loginNames":loginNames},
 		    success : function() {
			  window.location.reload();
 		    },
			error :function(){
				return
			}
		});
	}
}

//置回正常——弹出框
function queryToUserSign(){
	YouGou.UI.Dialog.show({
		title : '置回正常',
		message: function(dialog) {
			var $message = $('<div></div>');
	        var pageToLoad = dialog.getData('pageToLoad');
	        $message.load(pageToLoad);
	        return $message;
		},
       data: {
            'pageToLoad': '/monitor/toUpdateUserMonitorUserSign.sc'
       },
		buttons: [
		    {
				label: '确定',
				cssClass: 'btn-primary',
				action: function() {
					updateUserMonitorUserSign($('#suspiciousId').val(),$("#accountId").val(),$('#remark').val());
				}
		    }, 
			{
		    	label: '取消',
		    	action: function(dialog) {
		    		dialog.close();
		    	}
			}
	    ]
	});	
}
//置回正常——结果保存
function updateUserMonitorUserSign(suspiciousId,loginaccountStrings,remark){
	YouGou.Ajax.doPost({
		successMsg: '置回正常成功!',
		url: '/monitor/updateUserMonitorUserSign.sc',
		data:{"loginaccountStrings":loginaccountStrings,"suspiciousId":suspiciousId,"remark":remark},
	  	success : function(data){
	  		window.location.reload();
		}
	});

}
