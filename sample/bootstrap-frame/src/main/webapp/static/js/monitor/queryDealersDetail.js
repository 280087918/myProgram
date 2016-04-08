//主表格
var cols = [
	{ title:'<input type="checkbox" name="checkboxall" id="checkboxall" class="checkall">', name:'', width:45, lockWidth:true,align:'center',renderer:function(val,item){
		var html = "<input name='spr_check_single' type='checkbox' value='"+item.loginaccountId+"' blackId='"+item.loginaccountId+"'/>";
		return html;
	}},
    { title:'会员账号', name:'loginaName', align:'center',renderer : function(val,item,rowIndex) {
    	var suspiciousId = $("#suspiciousId").val();
    	return '<input type="hidden" id="loginId" value="'+item.loginaccountId+'"><a href="/monitor/queryMemberOrderAnalysis.sc?loginaccountId='+item.loginaccountId+'&suspiciousId='+suspiciousId+'" target="_blank">'+val+'</a>';
    }},
    { title:'已绑定手机', name:'registerCheckMobile', align:'center'},
	{ title:'会员身份', name:'memberShip' , align:'center'},
	{ title:'涉及订单数', name:'orderCount',  align:'center'},
	{ title:'订单总数', name:'orderTotalCount', align:'center'}
];

// 分页器
var mmPaginator = $('#grid-pager').mmPaginator({});

// 搜索表单属性
var mmFormParams = new MMSearchFormParams("searchForm");

//子表格
var subGridRowExpanded = function(e,item,subGridTableId,subGridPagerId){
	var cols = [
        { title:'下单时间', name:'createTime',width:120, lockWidth:true,align:'center',renderer:function(val){return new Date(val).format('yyyy-MM-dd hh:mm:ss');}},
        { title:'订单号', name:'orderSubNo',width:120, lockWidth:true,align:'center'},
    	{ title:'商品名称', name:'' , align:'center',renderer:function(val,item){
    		if(null!=item.orderDetail4subs){
    			var list = item.orderDetail4subs;
    			var str = "";
    			for(var i=0; i<list.length; i++){
    				str += list[i].prodName + ",";
    			}
    			str = str.substring(0,str.length-1);
    			return str;
    		}
    	}},
    	{ title:'收货人', name:'',width:50, lockWidth:true,align:'center',renderer:function(val,item){
    		if(null!=item.orderConsigneeInfo){
    			return item.orderConsigneeInfo.userName;
    		}
    	}},
    	{ title:'收货人手机', name:'' ,width:100, lockWidth:true, align:'center',renderer:function(val,item){
    		if(null!=item.orderConsigneeInfo){
    			return item.orderConsigneeInfo.mobilePhone;
    		}
    	}},
    	{ title:'收货地址', name:'', align:'center',renderer:function(val,item){
    		if(null!=item.orderConsigneeInfo){
    			return item.orderConsigneeInfo.provinceName+item.orderConsigneeInfo.cityName+item.orderConsigneeInfo.areaName+item.orderConsigneeInfo.consigneeAddress;
    		}
    	}},
        { title:'支付方式', name:'payment',width:70, lockWidth:true, align:'center',renderer:function(val){
    		if(val=='ONLINE_PAYMENT'){
    			return "在线支付";
    		}else{
    			return "货到付款";
    		}
    	}},
    	{ title:'支付状态', name:'payStatusName',width:80, lockWidth:true , align:'center'},
    	{ title:'订单状态', name:'baseStatusName',width:70, lockWidth:true, align:'center'},
    	{ title:'下单IP', name:'clientIp',width:80, lockWidth:true, align:'center'}
    ];
	var submmGrid = $('#'+subGridTableId).mmGrid({
		cols: cols,
		height: "auto",
		url: '/monitor/queryDealersOrderDetailList.sc?loginaccountId='+item.loginaccountId,
		fullWidthRows: true,
		autoLoad: true,
		isSubGrid: true,
		plugins: [
		    $('#'+subGridPagerId).mmPaginator({}),mmFormParams
		]
	});
	return submmGrid;
};

//主表格	
var mmGrid = $('#grid-table').mmGrid({
	height: 'auto',
	cols: cols,
	url: '/monitor/queryDealersOrderData.sc',
	autoLoad: true,
	fullWidthRows: true,
	noDataText:'没有数据',
	subGridCol : true,// 子表格列
	subGridRowExpanded : subGridRowExpanded,// 展开子表格事件
	plugins: [
	    mmFormParams , mmPaginator
	]
});

$("input[name$='checkboxall']").bind('click',function(){
	if (this.checked) {
		$("input[name$='spr_check_single']").each(function () { this.checked = true; });
	} else {
		$("input[name$='spr_check_single']").each(function () { this.checked = false; });		
	}
});

//加入黑名单
function queryTo(){
	var loginaccountStrings = new Array();
	var i = 0;
	$('input:checkbox[name="spr_check_single"]:checked').each(function() {
		var blackId=$(this).attr("blackId");
		loginaccountStrings[i] = blackId;
		i++;
	});
	if(loginaccountStrings.length == 0){
		YouGou.UI.Dialog.show({message : '请选择要批量的操作!'});
		return;
	}	
	$("#loginaccountStrings").val(loginaccountStrings);
	var loginId = $("#loginId").val();
	toEditMemberShipDialog(loginId,1);
}

//置回正常——弹出框
function queryToUserSign(){
	var loginaccountStrings = new Array();
	var i = 0;
	$('input:checkbox[name="spr_check_single"]:checked').each(function() {
		var blackId=$(this).attr("blackId");
		loginaccountStrings[i] = blackId;
		i++;
	});
	if(loginaccountStrings.length == 0){
		YouGou.UI.Dialog.show({message : '请选择要批量的操作!'});
		return;
	}	
	$("#loginaccountStrings").val(loginaccountStrings);
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
					updateUserMonitorUserSign($('#suspiciousId').val(),$('#loginaccountStrings').val(),$('#remark').val());
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
		data:{"loginaccountStrings":loginaccountStrings,"suspiciousId":suspiciousId,"remark":remark,"bnyOrDealers":"dealers"},
	  	success : function(data){
	  		window.location.reload();
		}
	});

}
