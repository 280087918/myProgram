/************可疑黑名单  start ***************/
//添加时间控件
J('#stateTime1').calendar({maxDate:'#stateTime1',format:'yyyy-MM-dd HH:mm:ss'});
J('#endTime1').calendar({minDate:'#endTime1',format:'yyyy-MM-dd HH:mm:ss'});

//列表
var cols = [ 
{title : '置为可疑时间',name : 'crateTime',width : 150,lockWidth : true,align : 'center',renderer : YouGou.Util.timeFixed},
{title : '会员账号',name : 'loginName',width : 220,align : 'left',renderer : function(val,item,rowIndex) {
	return '<a href="/monitor/queryMemberOrderAnalysis.sc?loginaccountId='+item.loginaccountId+'&suspiciousId='+item.suspiciousId+'&logType=1" target="_blank">'+val+'</a>';
}},
{title : '会员来源',name : 'numberId',lockWidth : true,width : 148,align : 'center', renderer: YouGou.Util.numberIdFixed},
{title : '会员身份',name : 'memberShip',width : 100,lockWidth : true,align : 'center',renderer : YouGou.Util.emptyFixed}, 
{title : '注册时间',name : 'registerDate',width : 150,lockWidth : true,align : 'center',renderer:YouGou.Util.timeFixed}, 
{title : '可疑原因',name : 'suspiciousName',width : 250,align : 'center'}, 
{title : '操作',name : '',width : 100,align : 'center',lockWidth : true,lockDisplay : true,renderer : function(val) {
		return '<a href="javascript:void(0);" action="select">详情</a>';
	}
}];

// 会员来源二级菜单联动
YouGou.UI.buildSecondSourceSelect('source1','source2');
YouGou.UI.buildSecondSourceSelect('source3','source4');

// 分页器
var mmPaginator = $('#grid-pager').mmPaginator({});

// 搜索表单属性
var mmFormParams = new MMSearchFormParams("searchForm");

// 表格
var mmGrid = $('#grid-table').mmGrid({
	height : 'auto',
	cols : cols,
	url : '/monitor/queryUserMonitorBnySelectStatistics.sc',
	fullWidthRows : true,
	autoLoad : true,
	plugins : [ mmFormParams, mmPaginator ]
});

// 查询
function doQuery() {
	mmGrid.load();
}

mmGrid.on('cellSelected',function(e, item, rowIndex, colIndex) {
	var action = $(e.target).attr("action");
	// 查看
	if (action == 'select') {
		window.open('/monitor/queryMemberOrderAnalysis.sc?loginaccountId='+item.loginaccountId+'&suspiciousId='+item.suspiciousId+'&logType=1');
	}
	e.stopPropagation(); // 阻止事件冒泡
});
/************可疑黑名单  end ***************/


/************可疑黄名单  start ***************/

//添加时间控件
J('#stateTime2').calendar({maxDate:'#stateTime2',format:'yyyy-MM-dd HH:mm:ss'});
J('#endTime2').calendar({minDate:'#endTime2',format:'yyyy-MM-dd HH:mm:ss'});

//列表
var cols2 = [ 
{title : '置为可疑时间',name : 'crateTime',width : 150,lockWidth : true,align : 'center',renderer : YouGou.Util.timeFixed},
{title : '会员账号',name : 'loginName',width : 220,align : 'left',renderer : function(val,item,rowIndex) {
	return '<a href="/monitor/queryMemberOrderAnalysis.sc?loginaccountId='+item.loginaccountId+'&suspiciousId='+item.suspiciousId+'&logType=2" target="_blank">'+val+'</a>';
}},
{title : '会员来源',name : 'numberId',lockWidth : true,width : 148,align : 'center', renderer: YouGou.Util.numberIdFixed},
{title : '会员身份',name : 'memberShip',width : 100,lockWidth : true,align : 'center',renderer : YouGou.Util.emptyFixed}, 
{title : '注册时间',name : 'registerDate',width : 150,lockWidth : true,align : 'center',renderer:YouGou.Util.timeFixed}, 
{title : '可疑原因',name : 'suspiciousName',width : 250,align : 'center'}, 
{title : '操作',name : '',width : 100,align : 'center',lockWidth : true,lockDisplay : true,renderer : function(val) {
		return '<a href="javascript:void(0);" action="select">详情</a>';
	}
}];

//分页器
var mmPaginator2 = $('#grid-pager2').mmPaginator({});

// 搜索表单属性
var mmFormParams2 = new MMSearchFormParams("searchForm2");

// 表格
var mmGrid2 = $('#grid-table2').mmGrid({
	height : 'auto',
	cols : cols2,
	url : '/monitor/queryUserMonitorBnySelectStatistics.sc',
	fullWidthRows : true,
	autoLoad : false,
	noDataText: '没有数据',
	plugins : [ mmFormParams2, mmPaginator2 ]
});

$('#myTab li a').click(function(){
	var action = parseInt($(this).attr("action"));
    if(action == 1){
    	mmGrid.load();
    }else if(action == 2){
    	mmGrid2.load();
    }
});

// 查询
function doQuery2() {
	mmGrid2.load();
}

mmGrid2.on('cellSelected',function(e, item, rowIndex, colIndex) {
	var action = $(e.target).attr("action");
	// 查看
	if (action == 'select') {
		window.open('/monitor/queryMemberOrderAnalysis.sc?loginaccountId='+item.loginaccountId+'&suspiciousId='+item.suspiciousId+'&logType=2');
	}
	e.stopPropagation(); // 阻止事件冒泡
});
/************可疑黄名单  end ***************/
