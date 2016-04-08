/*******************************************************************************
 * 会员监控-可疑taox
 ******************************************************************************/
//列表
var cols = [ 
{title : '置为可疑时间',name : 'crateTime',width : 150,align : 'center',renderer : YouGou.Util.timeFixed},
{title : '会员账号',name : 'loginName',lockWidth : true,width : 300,align : 'left',renderer : function(val,item,rowIndex) {
	return '<a href="/monitor/queryMemberOrderAnalysis.sc?loginaccountId='+item.loginaccountId+'&suspiciousId='+item.suspiciousId+'&cashOut=red" target="_blank">'+val+'</a>';
}}, 
{title : '会员来源',name : 'numberId',lockWidth : true,width : 148,align : 'center', renderer: YouGou.Util.foreignMemberSourceFixed},
{title : '会员身份',name : 'memberShip',width : 100,lockWidth : true,align : 'center',renderer : YouGou.Util.emptyFixed}, 
{title : '注册时间',name : 'registerDate',width : 150,lockWidth : true,align : 'center',renderer:YouGou.Util.timeFixed}, 
{title : '可疑原因',name : 'suspiciousName',width : 250,lockWidth : true,align : 'center'}, 
{title : '操作',name : '',width : 90,align : 'center',lockWidth : true,lockDisplay : true,renderer : function(val) {
		return '<a href="javascript:void(0);" action="select">详情</a>';
	}
}];

// 分页器
var mmPaginator = $('#grid-pager').mmPaginator({});

// 搜索表单属性
var mmFormParams = new MMSearchFormParams("searchForm");

// 表格
var mmGrid = $('#grid-table').mmGrid({
	height : 'auto',
	cols : cols,
	url : '/monitor/queryCashOuterData.sc',
	fullWidthRows : true,
	autoLoad : true,
	plugins : [ mmFormParams, mmPaginator ]
});

// 查询
function doQuery() {
	mmGrid.load();
}

function dubiousReson(val) {
	if (val == 1) {
		return '已支付订单7天内>=2次取消订单退款 ';
	}
}


mmGrid.on('cellSelected', function(e, item, rowIndex, colIndex){
	var action = $(e.target).attr("action");
	// 详情
	if (action == "select") {
		window.open('/monitor/queryMemberOrderAnalysis.sc?loginaccountId='+item.loginaccountId+'&suspiciousId='+item.suspiciousId+'&cashOut=red');
	}
	e.stopPropagation(); // 阻止事件冒泡
})

J('#stateTime').calendar({
	maxDate : '#endTime',
	format : 'yyyy-MM-dd HH:mm:ss'
});
J('#endTime').calendar({
	minDate : '#stateTime',
	format : 'yyyy-MM-dd HH:mm:ss'
});

//会员来源二级菜单联动
YouGou.UI.buildSecondSourceSelect('source1','source2');