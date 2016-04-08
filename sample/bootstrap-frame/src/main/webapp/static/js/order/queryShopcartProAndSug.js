/************* 	购物车问题和建议收集查询列表  **************/
var cols = [
            { title:'提交时间', name:'createTime',width:170,lockWidth:true, align:'center',renderer:YouGou.Util.timeFixed},
            { title:'会员账号', name:'memberName', lockWidth:true, width:240, align:'left'},
        	{ title:'意见及建议', name:'opinion',align:'left',position:'top'}
        ];
// 分页器
var mmPaginator = $('#grid-pager').mmPaginator({});

// 搜索表单属性
var mmFormParams = new MMSearchFormParams('searchForm');

// 表格	
var mmGrid = $('#grid-table').mmGrid({
	height: 'auto',
	cols: cols,
	url: '/order/collectShopcartProAndSugData.sc',
	indexCol: true,
	indexColWidth: 45,
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

J('#stateTime').calendar({maxDate:'#endTime',format:'yyyy-MM-dd HH:mm:ss'});
J('#endTime').calendar({minDate:'#stateTime',format:'yyyy-MM-dd HH:mm:ss'});
// 条件表单中label调整
$('#searchForm').find('label').css('padding-left','0px');

