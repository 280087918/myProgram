/************* 会员监控-可疑相似地址 **************/
var cols = [
    { title:'置为可疑时间', name:'ceateTime',lockWidth:true,width:145, align:'center'},
    { title:'疑似地址', name:'consigneeAddress',  width:100, align:'center'},
	{ title:'主订单号', name:'orderMainNo', width:110,lockWidth:true, align:'center'},
	{ title:'涉及会员数量', name:'memberCount' ,width:50, lockWidth:true, align:'center'},
	{ title:'涉及订单数量', name:'orderCount' ,width:50, lockWidth:true, align:'center'},
    { title:'操作', name:'' ,width:80, align:'center', lockWidth:true, lockDisplay: true, renderer: function(val){
        return '<button class="btn btn-sm btn-danger">详情</button>';
    }},
    { title:'ID', name:'id', hidden: true}
];

// 分页器
var mmPaginator = $('#grid-table').mmPaginator({});

// 搜索表单属性
var mmFormParams = new MMSearchFormParams("searchForm");

// 表格	
var mmGrid = $('#grid-table').mmGrid({
	height: 'auto',
	cols: cols,
	url: '/monitor/queryMemberAddressSimilaryData.sc',
	indexCol : true,
	fullWidthRows: true,
	autoLoad: true,
	plugins: [
	    mmFormParams , mmPaginator
	]
});

// 查询
function doQuery(){
    mmGrid.load();
}

mmGrid.on('cellSelected', function(e, item, rowIndex, colIndex){
    //查看
    if($(e.target).is('.btn, .btn-sm, .btn-danger')){
        e.stopPropagation();  //阻止事件冒泡
        window.open('/monitor/queryDealersDetailPage.sc?suspiciousId='+ item.suspiciousId +'&suspiciousDetail='+item.suspiciousDetail);
    }
}).on('loadSuccess', function(e, data){
}).on('rowInserted', function(e, item, index){
}).on('rowUpdated', function(e, oldItem, newItem, index){
}).on('rowRemoved', function(e, item, index){
});

J('#startTime').calendar({maxDate:'#endTime',format:'yyyy-MM-dd HH:mm:ss'});
J('#endTime').calendar({minDate:'#startTime',format:'yyyy-MM-dd HH:mm:ss'});