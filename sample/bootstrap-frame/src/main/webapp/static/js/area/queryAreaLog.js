/*************
	地区管理——操作日志查询列表
**************/
J('#startTime').calendar({maxDate:'#endTime',format:'yyyy-MM-dd HH:mm:ss'});
J('#endTime').calendar({minDate:'#startTime',format:'yyyy-MM-dd HH:mm:ss'});

var cols = [
    { title:'操作时间', name:'logTime',width:160,lockWidth:true, align:'center',renderer:YouGou.Util.timeFixed},
    { title:'操作人账户', name:'memberemail', lockWidth:true, width:160, align:'center'},
	{ title:'操作人姓名', name:'userName' , lockWidth:true, width:120, align:'center'},
	{ title:'操作事项', name:'remarks', align:'center'}
];

// 分页器
var mmPaginator = $('#grid-pager').mmPaginator({});

// 搜索表单属性
var mmFormParams = new MMSearchFormParams("searchForm");

// 表格	
var mmGrid = $('#grid-table').mmGrid({
	height: 'auto',
	cols: cols,
	url: '/area/queryAreaLog.sc',
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