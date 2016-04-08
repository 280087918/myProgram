/*************
	积分换券活动列表
**************/
var cols = [
    { title:'面值', name:'parValue',width:46, align:'center'},
    { title:'最低消费额', name:'lowestPay',width:70, align:'center'},
    { title:'活动名称', name:'activeName',width:250, align:'left'},
    {title:'可兑换时间', width :130, align:'center', renderer: function(val,item){
    	var html = [];
    	html.push('<span>'+item.startTime+'<br/>'+item.endTime+'</span>');
    	return html.join('');
    }},
    { title:'消耗积分', name:'needIntergral',width:60, align:'center'},
    { title:'所需等级',width:65, align:'center', renderer: function(val,item){
    	var html = [];
    	html.push('<span>'+item.memberLevel.levelName+'</span>');
    	return html.join('');
    }},
    {title:'添加时间', name :'createTime', width :130, align:'center'},
    { title:'添加人', name:'operator' ,width:70, align:'left'},
    { title:'状态',width:51, align:'center', renderer: function(val,item){
		var status = item.status;
		var statusDesc = "";
		if (status == 1) {
			statusDesc = "申请中";
		} else if (status == 2) {
			statusDesc = "已审核";
		} else if (status == 3) {
			statusDesc = "已拒审";
		} else if (status == 4) {
			statusDesc = "已终止";
		} else if (status == 5) {
			statusDesc = "已结束";
		} else if (status == 6) {
			statusDesc = "已暂停";
		}
		var html = [];
		html.push('<span>' + statusDesc + '</span>');
		return html.join('');
    }},
    { title:'操作', name:'status' ,width:100, align:'left', lockDisplay: true, renderer: function(status){
    	var html = [];
    	if(status == 1 || status == 3) {
    		html.push('<a href="javascript:void(0);" action="edit">编辑</a>&nbsp;&nbsp;');
    		html.push('<a href="javascript:void(0);" action="audit">审核</a>&nbsp;&nbsp;');
    		html.push('<a href="javascript:void(0);" action="delete">删除</a>');
    	} else if(status == 2) {
    		html.push('<a href="javascript:void(0);" action="select">查看</a>&nbsp;&nbsp;');
    		html.push('<a href="javascript:void(0);" action="audit">审核</a>&nbsp;&nbsp;');
    	} else if(status == 4) {
    		html.push('<a href="javascript:void(0);" action="delete">删除</a>');
    	} else if(status == 5) {
    		html.push('<a href="javascript:void(0);" action="select">查看</a>&nbsp;&nbsp;');
    		html.push('<a href="javascript:void(0);" action="delete">删除</a>');
    	} else if(status == 6) {
    		html.push('<a href="javascript:void(0);" action="edit">编辑</a>&nbsp;&nbsp;');
    		html.push('<a href="javascript:void(0);" action="delete">删除</a>');
    	}
    	return html.join('');
    }}
];

// 分页器
var mmPaginator = $('#grid-pager').mmPaginator({});

//搜索表单属性
var mmFormParams = new MMSearchFormParams("searchForm");

// 表格	
var mmGrid = $('#grid-table').mmGrid({
	height: 'auto',
	cols: cols,
	url: '/active/queryUserRedeemCouponsData.sc',
	checkCol: true,
	multiSelect: true,
	fullWidthRows: true,
	autoLoad: true,
	remoteSort: true,
	plugins: [
	   mmFormParams, mmPaginator
	]
});

// 查询
function doQuery(isdown) {
	if(isdown) {
		$("#isdown").val("true");
		$("#searchForm").submit();
		$("#isdown").val("");
	} else {
		mmGrid.load();
	}
}

mmGrid.on('cellSelected',function(e, item, rowIndex, colIndex) {
	var action = $(e.target).attr("action");
	// 查看
	if (action == "select") {
		showEditForm(item.id, 1);
	}
	// 编辑
	else if (action == "edit") {
		showEditForm(item.id, 2);
	}
	// 审核
	else if (action == "audit") {
		showForm(item, 3);
	}
	// 删除
	else if (action == "delete") {
		YouGou.UI.Dialog.confirm({
   			message : "确认删除活动名称为【" + item.activeName + "】信息吗？"
   		},function(result){
   			if(result) {
   				removeRedeemCoupons(item.id, item.activeName);
            }
   		});
	}
});

/**
 * 初始化分页器
 * @param mmPaginator
 */
function cleanPager(mmPaginator){
	var params = {};
	var opts = mmPaginator.opts;
    params[opts.totalCountName] = 0;
    params[opts.pageParamName] = opts.page;
    params[opts.limitParamName] = opts.limit;
    mmPaginator.load(params);
}

$(function(){
	showLevels();
	J('#startTime').calendar({maxDate:'#endTime',format:'yyyy-MM-dd HH:mm:ss'});
	J('#endTime').calendar({minDate:'#startTime',format:'yyyy-MM-dd HH:mm:ss'});
});

//展示用户等级
var levelData = null;
function showLevels() {
	YouGou.Ajax.doPost({
		tips : false,
		url : "/active/getMemberLevelData.sc",
	  	success : function(data){
	  		var levelsDiv = $('#levels');
	  		$.each(data.data, function(i, memberLevelVo) {
		  		levelsDiv.append('<input type="checkbox" class="ace ace-checkbox-1" name="levelId" id="levelId_'+memberLevelVo.id+'" value="'+memberLevelVo.id+'" />'
		  				+'<label class="lbl" for="levelId_'+memberLevelVo.id+'">'+memberLevelVo.levelName+'</label>&nbsp;&nbsp;&nbsp;&nbsp;');
	  		});
	  		levelData = data.data;
		}
	});
}

//查看or编辑
function showEditForm(obj,type) {
	if(type == 3) {
		showForm(obj, type);
	} else {
		YouGou.Ajax.doPost({
			tips : false,
			url : "/active/getUserRedeemCouponData.sc",
			data : {"id" : obj,"status" : status},
		  	success : function(data){
		  		showForm(data, type);
			}
		});
	}
}

//显示表格表单
function showForm(data, type){
	$("#girdContent").addClass("hide");
	if(type == 3) {
		var status = data.status;
		$("#activeManageNavbar,#activeManageForm").removeClass("hide");
		YouGou.UI.initForm("activeManageForm", data);
		if(!YouGou.Util.isEmpty(status)) {
			if(status == 2) {
				$("#status1").attr("disabled",true);
				$("#status3").attr("disabled",true);
			}
		}
	} else {
		$("#scouponName").addClass("hide");
		$("#userRedeemCouponNavbar,#userRedeemCouponForm").removeClass("hide");
		YouGou.UI.resetForm("userRedeemCouponForm");
		var editIntergralDiv = $('#editIntergral').html('');
		$.each(levelData, function(i, memberLevelVo) {
			var isChecked = "";
			var thisId = memberLevelVo.id;
			if(!YouGou.Util.isEmpty(data) && data.data.memberLevel.id == thisId) {
				isChecked = "checked";
			}
			editIntergralDiv.append('<label><input name="levelId"  id="levelId_'+thisId+'" value="'+thisId+'"  '+isChecked+'  type="radio" class="ace" />'
					+'<span class="lbl">'+memberLevelVo.levelName+'</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
		});
		bindFormValidator();
		// 增加
		if (type == 0) {
			$("#sbtn").removeClass("hide");
			$("#scouponName").removeClass("hide");
			reloadElement(null,$('#editTime'),true);
		} 
		// 编辑 or 查看
		else if (type == 1 || type == 2 ) {
	  		var needIntergral = $("#userRedeemCouponForm :input[name='needIntergral']");
	  		var levelId = $("#userRedeemCouponForm :input[name='levelId']");
	  		if(type == 1) {
	  			reloadElement(null,$('#editTime'),false);
	  			$("#sbtn").addClass("hide");
	  			needIntergral.attr('readonly',true);
	  			levelId.attr('disabled',true);
	  		} else{
	  			reloadElement(null,$('#editTime'),true);
	  			$("#sbtn").removeClass("hide");
	  			needIntergral.attr('readonly', false);
	  			levelId.attr('disabled',false);
	  		}
	  		if(!YouGou.Util.isEmpty(data.data.issueStartdate) && !YouGou.Util.isEmpty(data.data.issueEnddate)){
	  			data.data.issueStartdate = new Date(data.data.issueStartdate).format('yyyy-MM-dd hh:mm:ss');
	  	  		data.data.issueEnddate = new Date(data.data.issueEnddate).format('yyyy-MM-dd hh:mm:ss');
	  		}
	  		YouGou.UI.initForm("userRedeemCouponForm", data.data);
		} 
	}
}

//隐藏表单显示表格
function hideForm(){
	$("#userRedeemCouponNavbar,#userRedeemCouponForm,#activeManageNavbar,#activeManageForm").addClass("hide");
	$("#girdContent").removeClass("hide");
	reloadElement($('#searchTime'),null,true);
}

function bindFormValidator(){
	var rules = {needIntergral : { required : true }, startTime : { required : true }, endTime : { required : true }, levelId : { required : true }};
	var msg = {needIntergral : {required : "" }, startTime : { required : "兑换时间开始时间不能为空" }, endTime : { required : "兑换时间结束时间不能为空" }, levelId : { required : "用户等级不能为空" }};
	if(!/^\+?[1-9][0-9]*$/.test($("#needIntergral").val())){
		$("#needIntergral").val("");
		msg.needIntergral.required = "消耗积分必须为整数";
	}
	YouGou.UI.bindFormValidator("userRedeemCouponForm",rules,msg);
}

//保存
function save(){
	if ($('#userRedeemCouponForm').validate().form()) {
		// 积分兑换开始时间不能小于优惠券发放开始时间\结束时间不能大于优惠券发放结束时间
		if(!YouGou.Util.compareTime($("#startTime").val(), $(this).attr('issueStartdate')) 
			|| !YouGou.Util.compareTime($(this).attr('issueEnddate'), $("#endTime").val())) {
			alert("兑换时间不在优惠卷方案‘"+$("#couponName").val()+"’的发放时间范围内，不能添加。");
			return;
		}
		YouGou.Ajax.doPost({
			successMsg: '积分换券活动:'+ (YouGou.Util.isEmpty($("#id").val())?"创建":"修改") +'成功!',
			url: '/active/saveUserRedeemCoupon.sc',
		  	data: $("#userRedeemCouponForm").serializeArray(),
		  	success : function(data){
	  			mmGrid.load();
	  			hideForm();
			}
		});
	}
}

//删除
function removeRedeemCoupons(id,activeName){
	YouGou.Ajax.doPost({
		successMsg : '活动名称【'+ activeName +'】删除成功!',
		url: '/active/removeRedeemCoupons.sc',
	  	data: { "id" : id },
	  	success : function(data){
  			mmGrid.load();
		}
	});
}

function reloadElement(obj1,ojb2,isCalendar){
	if(obj1 == null && ojb2 != null) {
		var isReadonly = '';
		if(!isCalendar) {
			isReadonly = 'readonly="true"';
		}
		$('#searchTime').html('');
		ojb2.html('<input type="text" name="startTime" id="startTime"  '+isReadonly+' value="" class="input-medium"><label>&nbsp;&nbsp;至&nbsp;&nbsp;</label>'+
		'<input type="text" name="endTime" id="endTime" '+isReadonly+'  value="" class="input-medium">');
	}
	if(obj1 != null && ojb2 == null) {
		$('#editTime').html('');
		obj1.html('<input type="text" name="startTime" id="startTime" value="" class="input-medium"><label>&nbsp;&nbsp;至&nbsp;&nbsp;</label>'+
		'<input type="text" name="endTime" id="endTime" value="" class="input-medium">');
	}
	if(isCalendar) {
		J('#startTime').calendar({maxDate:'#endTime',format:'yyyy-MM-dd HH:mm:ss'});
		J('#endTime').calendar({minDate:'#startTime',format:'yyyy-MM-dd HH:mm:ss'});
	}
}

//批量审批
function batchActiveManage(){
  	var checkbox = $(":checkbox[name='activeItem']:checked"), inputval = "", status = "", isBatchApproval = true;
	if(checkbox.length > 0) {
		checkbox.each(function(index) {
			if (index == 0) {
				inputval += $(this).val();
			} else {
				inputval += ',' + $(this).val();
			}
			status = $(this).attr('status');
			if (status !=  "1") {
				isBatchApproval = false;
			}
		});
	} else {
		alert("请勾选要审核的积分换券活动！");
		return;
	}
	if(!isBatchApproval){
		alert("只有申请中的积分换券活动可以进行审核！");
		return;
	}
}

function getCouponList() {
	$("#couponSelectorIframe").attr("src","/active/addCouponsPage.sc");
	$("#dialog-confirm").removeClass('hide').dialog({
		bgiframe: true,
		resizable: true,
		draggable: false,
		modal: true,
		title: "优惠卷方案",
		width: 1020,
		height: 475,
	});
}

//审批
function activeManage() {
	YouGou.Ajax.doPost({
		successMsg: '积分换券活动审核成功!',
		url: '/active/updateManageStatus.sc',
	  	data: $("#activeManageForm").serializeArray(),
	  	success : function(data){
  			mmGrid.load();
  			hideForm();
		}
	});
}

function batchActiveManage(){
	YouGou.UI.resetForm("activeManageForm");
	var inputval = "", status = "", isBatchApproval = true;
	var rows = mmGrid.selectedRows();
	if(rows.length > 0) {
		var id = "";
		for(var i = 0; i < rows.length; i++) {
			var status = rows[i].status;
			id = rows[i].id;
			if (i == 0) {
				inputval += rows[i].id;
			} else {
				inputval += ',' + rows[i].id;
			}
			if (status !=  "1") {
				isBatchApproval = false;
			}
		}
	} else {
		alert("请勾选要审核的积分换券活动！");
		return;
	}
	if(!isBatchApproval){
		alert("只有申请中的积分换券活动可以进行审核！");
		return;
	}
	$("#girdContent").addClass("hide");
	$("#activeManageNavbar,#activeManageForm").removeClass("hide");
	rows[0].id = inputval;
	YouGou.UI.initForm("activeManageForm", rows[0]);
}

function refreshCache() {
	YouGou.Ajax.doPost({
		successMsg: '刷新缓存成功！',
		url: '/active/refreshCache.sc',
	  	success : function(data){
  			mmGrid.load();
		}
	});
}

