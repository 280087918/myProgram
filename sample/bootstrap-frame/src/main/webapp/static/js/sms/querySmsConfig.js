
/*************
 短信通道产品信息
**************/
var channelCols = [
{ title:'产品信息', name:'sprdid',width:60, align:'center'},
{ title:'产品编号', name:'sprdid',width:120, align:'center'},
{ title:'产品名称', name:'sprdid' , width:100, align:'center'},
{ title:'剩余数量', name:'remain', width:120, align:'center'},
{ title:'日发送量评估值', name:'sendAmount' ,width:120, align:'center', renderer: YouGou.Util.emptyFixed}
];

//分页器－短信通道产品信息
var mmChannelPaginator = $('#grid-pager-channel').mmPaginator({});

//表格－短信通道产品信息
var mmChannelGrid = $('#grid-table-channel').mmGrid({
	height: 'auto',
	cols: channelCols,
	url: '/sms/querySmsConfigData.sc',
	indexCol: true,
	indexColWidth: 45,
	fullWidthRows: true,
	autoLoad: true,
	plugins: [
　　　　mmChannelPaginator
	]
});
