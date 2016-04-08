/*************
	会员查询列表
**************/
var isEdit = parseInt($('#isEdit').val());
var cols = [
    { title:'会员账号', name:'loginName',width:80, align:'left'},
    { title:'来源平台', name:'platform', lockWidth:true, width:100, align:'center'},
	{ title:'注册类型', name:'regType' , lockWidth:true, width:100, align:'center'},
	{ title:'会员等级', name:'memberLevelName', lockWidth:true, width:75, align:'center'},
	{ title:'会员身份', name:'memberShip' ,width:70, lockWidth:true, align:'center', renderer: YouGou.Util.emptyFixed},
	{ title:'标签', name:'tag' ,width:100, lockWidth:true,hidden:true, align:'center'},
	{ title:'注册时间', name:'registerDate' ,width:145, lockWidth:true, align:'center',renderer:YouGou.Util.timeFixed},
	{ title:'最后登录时间', name:'lastTimeOut' ,width:140, lockWidth:true, align:'center'},
    { title:'操作', name:'' ,width:(isEdit==1?210:100), align:'center',lockWidth:true, renderer: function(val,item,rowIndex){
    	if($("#isEdit").val()==1){
    		return '<a href="javascript:void(0);" action="edit">编辑会员</a>&nbsp;&nbsp;<a href="javascript:void(0);" action="updateShip">变更会员身份</a>&nbsp;&nbsp;'
    	}else{
    		return '<a href="javascript:void(0);" action="select">查看</a>'
    	}
    }},
];

// 分页器
var mmPaginator = $('#grid-pager').mmPaginator({});

// 搜索表单属性
var mmFormParams = new MMSearchFormParams("searchForm");

// 表格	
var mmGrid = $('#grid-table').mmGrid({
	height: 'auto',
	cols: cols,
	url: '/member/queryMemberData.sc',
	fullWidthRows: true,
	autoLoad: false,
	plugins: [
	    mmFormParams , mmPaginator
	]
});

// 查询
function doQuery(){
	// 条件输入校验
	var bLoginaccountId = YouGou.Util.isEmpty($('#loginaccountId').val());
	var bLoginName = YouGou.Util.isEmpty($('#loginName').val());
	var bRegisterCheckMobile = YouGou.Util.isEmpty($('#registerCheckMobile').val());
	var bRegisterCheckEmail = YouGou.Util.isEmpty($('#registerCheckEmail').val());
	var bPlatform = YouGou.Util.isEmpty($('#platform').val());
	var bRegType = YouGou.Util.isEmpty($('#regType').val());
	var bMemberShip =YouGou.Util.isEmpty($('#memberShip').val());
	var bMemberLevel = YouGou.Util.isEmpty($('#memberLevel').val());
	var bStateTime = YouGou.Util.isEmpty($('#stateTime').val());
	var bEndTime = YouGou.Util.isEmpty($('#endTime').val());
	var bReceiveMobile = YouGou.Util.isEmpty($('#receiveMobile').val());

	// 全部条件为空
	var isAllNull = bLoginaccountId && bLoginName 
						&& bRegisterCheckMobile && bRegisterCheckEmail 
						&& bPlatform && bRegType
						&& bMemberShip && bMemberLevel
						&& bStateTime && bEndTime && bReceiveMobile;
	// 索引条件为空
	var isIndexNull = bLoginaccountId && bLoginName 
			&& bRegisterCheckMobile && bRegisterCheckEmail
			&& bStateTime && bEndTime;
	// 注册时间段为空
	var isRegTime = bStateTime && bEndTime;
	// 平台  + 注册时间范围 + 索引列为空
	var isPlatformNull = isRegTime && isIndexNull && !bPlatform;
	// 注册类型 + 注册时间范围 + 索引列为空
	var isRegTypeNull = isRegTime && isIndexNull && !bRegType;
	// 会员身份 + 注册时间范围 + 索引列为空
	var isMemberShipNull = isRegTime && isIndexNull && !bMemberShip;
	// 会员等级 + 注册时间范围 + 索引列为空
	var isMemberLevelNull = isRegTime && isIndexNull && !bMemberLevel;
		
	if(isAllNull){
		YouGou.UI.Dialog.show({message : '查询条件不能全部为空!'});
		return;
	}else if(isPlatformNull){
		YouGou.UI.Dialog.show({message : '来源平台必须选择注册时间段查询!'});
		return;
	}else if(isRegTypeNull){
		YouGou.UI.Dialog.show({message : '注册类型必须选择注册时间段查询!'});
		return;
	}else if(isMemberShipNull){
		YouGou.UI.Dialog.show({message : '会员身份必须选择注册时间段查询!'});
		return;
	}else if(isMemberLevelNull){
		YouGou.UI.Dialog.show({message : '会员等级必须选择注册时间段查询!'});
		return;
	}else{
		mmGrid.load();
	}
}

mmGrid.on('cellSelected', function(e, item, rowIndex, colIndex){
	var action = $(e.target).attr("action");
	//查看
    if(action == "select"){
        window.open('/member/memberDetailPage.sc?loginaccountId='+ item.id +'&platform=yougou&tabType=info&isEdit=0');
    //编辑会员
    }else if(action == "edit"){
    	 window.open('/member/memberDetailPage.sc?loginaccountId='+ item.id +'&platform=yougou&tabType=editInfo&isEdit=1');
       //变更会员身份
    }else if(action == "updateShip"){
    	showEditMemberShip(item.id);
    } 
    e.stopPropagation();
});

J('#stateTime').calendar({maxDate:'#endTime',format:'yyyy-MM-dd HH:mm:ss'});
J('#endTime').calendar({minDate:'#stateTime',format:'yyyy-MM-dd HH:mm:ss'});

//会员解锁-表单验证
var rules = {loginName : { required : true }};
var validator = YouGou.UI.bindFormValidator("memberUnlockForm",rules,{});
		
//会员解锁-查询
function selectMemberLockInfo(){
	if(!$('#memberUnlockForm').validate().form()){
		return;
	};
	$("#memberLockInfo").html("").hide();
	new YouGou.Ajax.doPost({
		tips: false,
		url: '/member/selectMemberLockInfo.sc',
		data: "loginName=" + $("#loginNameValue").val(),
	  	success: function(obj){
	  		var data= obj.data;
	  		if(obj.msg=="success"){
	  			var html = [];
				html.push('会员帐号：' + data.loginName);
				html.push('，ip地址：'+ data.ip);
				html.push('，验证方式：' + data.validateType);
				html.push('【'+ data.custom1 +'】');
				html.push('，验证状态：');
				if(data.validated == "1"){
					html.push('已验证');
				}else{
					html.push('未验证');
				}
				html.push('，验证时间：');
				var vTime = data.securityValidDate;
				vTime = vTime.substring(0,vTime.length-2);
				html.push(vTime);
				$("#memberLockInfo").html(html.join('')).show();
	  		}else{
	  			YouGou.UI.tipError(obj.msg);
	  		}
		}
	});
}

//会员解锁-解锁
function unLockMemberLock(){
	if(!$('#memberUnlockForm').validate().form()){
		return;
	};
	$("#memberLockInfo").html("").hide();
	new YouGou.Ajax.doPost({
	   tips: false,
	   url: "/member/unLockMember.sc",
	   data: "loginName=" + $("#loginNameValue").val(),
	   success: function(obj){
		   var data = obj.data;
		   if(data == "ok"){
			   YouGou.UI.tipSuccess(obj.msg);
		   }else{
			   YouGou.UI.tipError(obj.msg);
		   }
		   if(data == "ok" || data== "foundBind"){
			   selectMemberLockInfo();
		   }
		}
	}); 
	
}

//隐藏表格显示表单
function showForm(){
	$("#girdContent").addClass("hide");
	$("#memberShipNavbar,#memberShipForm").removeClass("hide");
	YouGou.UI.resetForm("memberShipForm");
}

//隐藏表单显示表格
function hideForm(){
	$("#memberShipNavbar,#memberShipForm").addClass("hide");
	$("#girdContent").removeClass("hide");
}
