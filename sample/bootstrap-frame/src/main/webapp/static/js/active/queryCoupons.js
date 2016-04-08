/*************
	积分换券活动列表
**************/
var cols = [
/*    { title:'选择', name:'id',width:10, align:'center', renderer: function(val,item){
    	var html = [];
    	var issueStartdate = new Date(item.issueStartdate).format('yyyy-MM-dd hh:mm:ss');
    	var issueEnddate = new Date(item.issueEnddate).format('yyyy-MM-dd hh:mm:ss');
    	html.push('<label><input name="couponItem"  id="coupon_'+val+'" value="'+val+'" issueStartdate="'+issueStartdate+'" issueEnddate="'+issueEnddate+'" couponname="'+item.couponName+'"  type="radio" class="ace ace-radio-1" />');
    	html.push('<span class="lbl"></span></label>');
    	return html.join('');
    }},*/
    { title:'优惠方案', name:'couponName',width:200, align:'left'},
    { title:'面额', name:'parValue',width:13, align:'center'},
    { title:'类型', name:'type',width:13, align:'center', renderer: function(type,item){
		var statusDesc = "";
		if (type == 1) {
			statusDesc = "优惠券";
		} else if (status == 2) {
			statusDesc = "礼品券";
		} else if (status == 3) {
			statusDesc = "折扣券";
		} else if (status == 5) {
			statusDesc = "礼品卡";
		} else {
			statusDesc = "免运费";
		}
		var html = [];
		html.push('<span>' + statusDesc + '</span>');
		return html.join('');
    }},
    { title:'限发数量', name:'issueAmount',width:13, align:'center'},
    {title:'发放开始时间', name :'issueStartdate', width :80, align:'center', renderer: function(issueStartdate,item){
    	var html = [];
    	html.push('<span>'+new Date(issueStartdate).format('yyyy-MM-dd hh:mm:ss')+'</span>');
    	return html.join('');
    }},
    {title:'发放结束时间', name :'issueEnddate', width :80, align:'center', renderer: function(issueEnddate,item){
    	var html = [];
    	html.push('<span>'+new Date(issueEnddate).format('yyyy-MM-dd hh:mm:ss')+'</span>');
    	return html.join('');
    }},
    { title:'状态', name :'state', width:30, align:'center', renderer: function(state,item){
    	var statusDesc = "";
		if (state == 1) {
			statusDesc = "活动中";
		} else if (state == 2) {
			statusDesc = "待审核";
		}  else {
			statusDesc = "停止";
		}
		var html = [];
		html.push('<span>' + statusDesc + '</span>');
		return html.join('');
    }}
];

// 分页器
var mmPaginator = $('#grid-pager').mmPaginator({limitList: [11]});

//搜索表单属性
var mmFormParams = new MMSearchFormParams("searchForm");

//表格	
var mmGrid = null;

//查询
function doQuery() {
	mmGrid.load();
}

J('#publicStartTime').calendar({maxDate:'#publicEndTime',format:'yyyy-MM-dd HH:mm:ss'});
J('#publicEndTime').calendar({minDate:'#publicStartTime',format:'yyyy-MM-dd HH:mm:ss'});
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

function setCouponsData() {
	var rows = mmGrid.selectedRows();
	if(rows.length <= 0) {
		alert("请选择优惠券方案");
		return;
	}
	var couponSchemeId = rows[0].id; 
	YouGou.Ajax.doPost({
		tips : false,
		url : "/active/getCouponsData.sc",
		data : {"couponSchemeId" : couponSchemeId},
	  	success : function(data) {
	  		parent.$("#dialog-confirm").dialog('close');
	  		parent.YouGou.UI.resetForm("userRedeemCouponForm");
	  		if(!YouGou.Util.isEmpty(data.data.issueStartdate) && !YouGou.Util.isEmpty(data.data.issueEnddate)){
	  			data.data.issueStartdate = new Date(data.data.issueStartdate).format('yyyy-MM-dd hh:mm:ss');
	  	  		data.data.issueEnddate = new Date(data.data.issueEnddate).format('yyyy-MM-dd hh:mm:ss');
	  		}
	  		parent.YouGou.UI.initForm("userRedeemCouponForm", data.data);
	  		parent.$("#userRedeemCouponForm :input[name='id']").val("");
	  		parent.$("#userRedeemCouponForm :input[name='couponSchemeId']").val(couponSchemeId);
		}
	});
}

function checkIsBatch() {
	var isBatch = $("#isBatch").val();
	if(!YouGou.Util.isEmpty(isBatch) && isBatch == 'true') {
		mmFormParams.formId = "searchCouponForm";
		YouGou.Ajax.doPost({
			tips : false,
			url : "/active/getMemberLevelData.sc",
		  	success : function(data){
		  		var editIntergralDiv = $('#editIntergral');
		  		$.each(data.data, function(i, memberLevelVo) {
		  			editIntergralDiv.append('<label><input name="levelId"  id="levelId_'+memberLevelVo.id+'" value="'+memberLevelVo.id+'"   type="radio" class="ace" />'
		  					+'<span class="lbl">'+memberLevelVo.levelName+'</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
		  		});
			}
		});
		$('#editTime').html('<input type="text" name="startTime" id="startTime"  value="" class="input-medium"><label>&nbsp;&nbsp;至&nbsp;&nbsp;</label>'+
		'<input type="text" name="endTime" id="endTime"   value="" class="input-medium">');
		J('#startTime').calendar({maxDate:'#endTime',format:'yyyy-MM-dd HH:mm:ss'});
		J('#endTime').calendar({minDate:'#startTime',format:'yyyy-MM-dd HH:mm:ss'});
//		mmGrid.opts.cols[0].renderer = function (val,item){
//	    	var html = [];
//	     	var issueStartdate = new Date(item.issueStartdate).format('yyyy-MM-dd hh:mm:ss');
//	    	var issueEnddate = new Date(item.issueEnddate).format('yyyy-MM-dd hh:mm:ss');
//	    	html.push('<label><input name="couponItem"  id="coupon_'+val+'" value="'+val+'"  issueStartdate="'+issueStartdate+'" issueEnddate="'+issueEnddate+'"  couponname="'+item.couponName+'"type="checkbox" class="ace ace-checkbox-1" />');
//	    	html.push('<span class="lbl"></span></label>');
//	    	return html.join('');
//	    }
	} else {
		$("#publicStartTime,#publicEndTime").click(function(){
			$("#lhgcalendar").width('200px');
		});
	}
	
	var param = {
		height: 'auto',
		cols: cols,
		url: '/active/queryCouponsData.sc',
		fullWidthRows: true,
		remoteSort: true,
		checkCol: true,
		autoLoad: true,
		plugins: [
		   mmFormParams, mmPaginator
		]
	};
	
	// 批量
	if(!YouGou.Util.isEmpty(isBatch) && isBatch == 'true') {
		param.multiSelect = true;
	}
	
	mmGrid = $('#grid-table').mmGrid(param);
	
	mmGrid.on('cellSelected',　function(e, item, rowIndex, colIndex) {
	}).on('loadSuccess', function(e, data) {
	}).on('rowInserted', function(e, item, index) {
	}).on('rowUpdated', function(e, oldItem, newItem, index) {
	}).on('rowRemoved', function(e, item, index) {
	});
}

//保存
function save(){
	bindFormValidator();
	if ($('#addCouponForm').validate().form()) {
		var couponItem = mmGrid.selectedRows();
		var startBool = true, endBool = true, inputval = "", couponName = "";
		if(couponItem.length <= 0) {
			alert("请选择优惠卷方案");
			return;
		}
		$.each(couponItem,function(index,coupon) {
			var id = coupon.id;
			if (index == 0) {
				inputval += id;
			} else {
				inputval += ',' + id;
			}
			couponName = coupon.couponName;
			// 积分兑换开始时间不能小于优惠券发放开始时间
			startBool = YouGou.Util.compareTime($("#startTime").val(), new Date(coupon.issueStartdate).format('yyyy-MM-dd hh:mm:ss'));
			// 积分兑换结束时间不能大于优惠券发放结束时间
			endBool = YouGou.Util.compareTime(new Date(coupon.issueEnddate).format('yyyy-MM-dd hh:mm:ss'), $("#endTime").val());
			if (!startBool || !endBool) {
				return false;
			}
		});
		if (!startBool || !endBool) {
			alert("兑换时间不在优惠卷方案‘"+couponName+"’的发放时间范围内，不能添加。");
			return;
		}
		$("#addCouponForm :input[name='couponSchemeId']").val(inputval);
		YouGou.Ajax.doPost({
			successMsg: '积分换券活动创建成功!',
			url: '/active/batchSaveUserRedeemCoupon.sc',
			data: $("#addCouponForm").serializeArray(),
			success : function(data){
				location.href = "/active/queryUserRedeemCouponsPage.sc";
			}
		});
	}
}

function bindFormValidator(){
	var rules = {needIntergral : { required : true }, startTime : { required : true }, endTime : { required : true }, levelId : { required : true }};
	var msg = {needIntergral : {required : "" }, startTime : { required : "兑换时间开始时间不能为空" }, endTime : { required : "兑换时间结束时间不能为空" }, levelId : { required : "用户等级不能为空" }};
	if(!/^\+?[1-9][0-9]*$/.test($("#needIntergral").val())){
		$("#needIntergral").val("");
		msg.needIntergral.required = "消耗积分必须为整数";
	}
	YouGou.UI.bindFormValidator("addCouponForm",rules,msg);
}

checkIsBatch();