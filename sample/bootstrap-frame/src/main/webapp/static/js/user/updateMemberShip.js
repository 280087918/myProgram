//变更会员身份
function showEditMemberShip(loginaccountId){
	YouGou.Ajax.doPost({
		tips : false,
		successMsg: false,
		url : "/member/toEditMemberShip.sc",
		data : {"loginaccountId" : loginaccountId},
	  	success : function(data){
  			showForm();
  			//当前会员身份状
  			var memberLoginaccount = data.data;
  			var str = "暂无";
  			if(null!=memberLoginaccount.memberShip){
  				if(memberLoginaccount.memberShip=="A"){
  					str = "白名单("+memberLoginaccount.loginName+")";
  				}else if(memberLoginaccount.memberShip=="B"){
  					str = "新会员("+memberLoginaccount.loginName+")";
  				}else if(memberLoginaccount.memberShip=="G"){
  					str = "会员("+memberLoginaccount.loginName+")";
  				}else if(memberLoginaccount.memberShip=="C"){
  					str = "红名单("+memberLoginaccount.loginName+")";
  				}else if(memberLoginaccount.memberShip=="D"){
  					str = "灰名单("+memberLoginaccount.loginName+")";
  				}else if(memberLoginaccount.memberShip=="E"){
  					str = "黄名单("+memberLoginaccount.loginName+")";
  				}else if(memberLoginaccount.memberShip=="F"){
  					str = "黑名单("+memberLoginaccount.loginName+")";
  				}
  				$("#memberShip").html(str);
  				
  				//更新会员身份
  				$("input[name='currentShip']").each(function(){
  					if(this.value==memberLoginaccount.memberShip){
  						this.checked = true;
  					}
  				});
  				
  				//展示对应的变更原因
  				setShipValue(memberLoginaccount.memberShip);
  				
  				//初始化值
  				$("#accountId").val(memberLoginaccount.id);
  				$("#originalShip").val(memberLoginaccount.memberShip);
  			}
		}
	});
}

//由选中的会员身份展示其对应的变更原因
function setShipValue(ship){
	if('F' != ship && 'G' != ship) {
		$("#reasonShip").attr("disabled",true);
	}else {
		$("#reasonShip").attr("disabled",false);
	}
	// 如果当前为黑名单并且改为会员时变更原因只展示“客户投诉”和“其他”
	$('#reasonShip option').remove();
	$("#reasonShip").append("<option value=''>请选择</option>");
	if('G' == ship){
		$("#reasonShip").append("<option value='客户投诉'>客户投诉</option>");
		$("#reasonShip").append("<option value='其他'>其他</option>");
	}else{
		$("#reasonShip").append("<option value='代销商'>代销商</option>");
		$("#reasonShip").append("<option value='不良会员'>不良会员</option>");
		$("#reasonShip").append("<option value='盗取账号、优惠劵'>盗取账号、优惠劵</option>");
		$("#reasonShip").append("<option value='倒卖账号'>倒卖账号</option>");
	}
	$("#nowShip").val(ship);
}

//保存
function saveMemberShip(){
	//表单验证
	var rules1 = {
		currentShip : { required : true },
		reasonShip : { required : true },
		reasonModify : { maxlength:200,minlength:2 }
	};
	var rules2 = {
			currentShip : { required : true },
			reasonModify : { maxlength:200,minlength:2 }
		};
	var validator = YouGou.UI.bindFormValidator("memberShipForm",rules1,{});
	var currentShip = $("input[name='currentShip']:checked").val();
	if(currentShip!='F' && currentShip!='G') {
		validator = YouGou.UI.bindFormValidator("memberShipForm",rules2,{});
	}
	if(!$('#memberShipForm').validate().form()){
		return;
	};
	//提交表单
	YouGou.Ajax.doPost({
		successMsg: '会员身份修改成功!',
		url: '/member/saveMemberShip.sc',
	  	data: $("#memberShipForm").serializeArray(),
	  	success : function(data){
  			mmGrid.load();
  			hideForm();
		}
	});
}

//修改会员身份——弹出框
function toEditMemberShipDialog(loginaccountId){
	YouGou.UI.Dialog.show({
		title : '变更会员身份',
		message: function(dialog) {
			var $message = $('<div></div>');
	        var pageToLoad = dialog.getData('pageToLoad');
	        $message.load(pageToLoad);
	        return $message;
		},
       data: {
            'pageToLoad': '/member/toEditMemberShipDialog.sc?loginaccountId='+loginaccountId
       },
		buttons: [
		    {
				label: '确定',
				cssClass: 'btn-primary',
				action: function() {
					saveMemberShip_dialog();
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

//保存会员身份——弹出框
function saveMemberShip_dialog(){
	//表单验证
	var rules1 = {
		currentShip : { required : true },
		reasonShip : { required : true },
		reasonModify : { maxlength:200,minlength:2 }
	};
	var rules2 = {
			currentShip : { required : true },
			reasonModify : { maxlength:200,minlength:2 }
		};
	var validator = YouGou.UI.bindFormValidator("memberShipForm",rules1,{});
	var currentShip = $("input[name='currentShip']:checked").val();
	if(currentShip!='F' && currentShip!='G') {
		validator = YouGou.UI.bindFormValidator("memberShipForm",rules2,{});
	}
	if(!$('#memberShipForm').validate().form()){
		return;
	};
	
	//判断是否有关联账号
	if($("#hasRelation").val()==1){
		var loginNameArr = [];
		$("input[name = relationAccount]").each(function(){
			var loginName =  $(this).val();
				loginNameArr.push(loginName);
		});
		var relationName = loginNameArr.join(",");
	  	$("#relationAccounts").val(relationName);
	}
	
	//提交表单
	YouGou.Ajax.doPost({
		successMsg: '会员身份修改成功!',
		url: '/member/saveMemberShip.sc',
	  	data: $("#memberShipForm").serializeArray(),
	  	success : function(data){
	  		window.location.reload();
		}
	});
}

//修改会员身份(多个)——弹出框
function toEditManyMemberShipDialog(){
	var loginNameArr = [];
	$('input[name="relationLoginName"]:checked').each(function(){ 
		loginNameArr.push($(this).val()); 
	}); 
	var relationName = loginNameArr.join(",");
	relationName = encodeURI(encodeURI(relationName));
	YouGou.UI.Dialog.show({
		title : '修改会员身份',
		message: function(dialog) {
			var $message = $('<div></div>');
	        var pageToLoad = dialog.getData('pageToLoad');
	        $message.load(pageToLoad);
	        return $message;
		},
       data: {
            'pageToLoad': '/member/toEditMemberShipDialog.sc?loginaccountId='+$("#accountId").val()+"&relationLoginName="+relationName
       },
		buttons: [
		    {
				label: '确定',
				cssClass: 'btn-primary',
				action: function() {
					saveMemberShip_dialog();
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
