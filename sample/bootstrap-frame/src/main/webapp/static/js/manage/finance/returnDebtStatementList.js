/*************
	FinReturnDebt
**************/
// 操作列动作

// 列集合
var cols = [
	{ title:'订单号', name:'orderNo', align:'left'},
	{ title:'子订单号', name:'orderSubNo', align:'left'},
	{ title:'退款单编号', name:'backNo', align:'left'},
	{ title:'申请退款时间', name:'applyDate', align:'center', renderer: YouGou.Util.timeFixed},
	{ title:'客户名称', name:'customerName', align:'left'},
	{ title:'客户账号', name:'customerNo', align:'left'},
	{ title:'订单金额', name:'orderAmount', align:'left'},
	{ title:'店铺名', name:'storeName', align:'left'},
	{ title:'店铺id', name:'storeId', align:'left'},
	{ title:'订单来源', name:'orderSource', align:'left'},
	{ title:'订单二级来源编码', name:'orderSourceId', align:'left'},
	{ title:'在线支付交易号', name:'onlinePayNumber', align:'left'},
	{ title:'退款类型', name:'refundType', align:'center'},
	{ title:'退款时间', name:'refundDate', align:'center', renderer: YouGou.Util.timeFixed},
	{ title:'退款金额', name:'refundAmount', align:'left'},
	{ title:'退款原因', name:'refundNote', align:'left'},
	{ title:'操作员', name:'operator', align:'left'},
	{ title:'备注-退款说明', name:'note', align:'left'},
    { title:'ID', name:'id', hidden: true}
];

//分页器
var mmPaginator = $('#grid-pager').mmPaginator({});
// 搜索表单属性
var mmFormParams = new MMSearchFormParams("searchForm");

// 表格	
var mmGrid = $('#grid-table').mmGrid({
	height: 'auto',
	cols: cols,
	url: '/finance/returndebt/statement/queryReturnDebtStatementData.sc',
	fullWidthRows: true,
	autoLoad: true,
	plugins: [mmPaginator,mmFormParams]
});

function doQuery(){
	mmGrid.load();
}