/*************
	下单手机
**************/

var cols = [
    { title:'姓名', name:'userName',width:135, align:'center'},
    { title:'手机号码', name:'phone',  width:120, align:'center'},
	{ title:'备注', name:'remark' , width:110, align:'center'},
    { title:'操作', name:'' ,width:80, align:'center', lockWidth:true, lockDisplay: true, renderer: function(val){
        return '<button class="btn btn-sm btn-danger">详情</button>';
    }}
];

// 分页器
var mmPaginator = $('#grid-pager').mmPaginator({});

// 搜索表单属性
var mmFormParams = new MMSearchFormParams("searchForm");

// 表格	
var mmGrid = $('#grid-table').mmGrid({
	height: 'auto',
	cols: cols,
	url: '/order/queryOrderPhoneData.sc',
	fullWidthRows: true,
	autoLoad: true,
	plugins: [
	    mmFormParams , mmPaginator
	]
});

// 查询
function doQuery(){
	// 条件输入校验
	if(mmFormParams.check()){
        mmGrid.load();
	}
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
