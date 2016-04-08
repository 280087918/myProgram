/*************
	操作日志查询列表
**************/
var cols = [
    { title:'操作类型', name:'type',width:150,lockWidth:true, align:'center'},
    { title:'会员账号', name:'memberemail', lockWidth:true, width:150, align:'center'},
	{ title:'操作人', name:'userName' , lockWidth:true, width:150, align:'center'},
	{ title:'操作时间', name:'logTime', lockWidth:true, width:150, align:'center',renderer:YouGou.Util.timeFixed},
	{ title:'操作IP', name:'ip' ,width:100, lockWidth:true, align:'center'},
	{ title:'操作机器', name:'machinename' ,width:120, lockWidth:true, align:'center'},
	{ title:'操作备注', name:'remarks' ,align:'left'}
];

// 分页器
var mmPaginator = $('#grid-pager').mmPaginator({});

// 搜索表单属性
var mmFormParams = new MMSearchFormParams("searchForm");

// 表格	
var mmGrid = $('#grid-table').mmGrid({
	height: 'auto',
	cols: cols,
	url: '/member/queryMemberLogData.sc',
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