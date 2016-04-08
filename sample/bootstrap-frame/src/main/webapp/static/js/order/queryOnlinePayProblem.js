/************* 	在线支付反馈数据查询列表  **************/
var cols = [
    { title:'提交时间', name:'answerTime',lockWidth:true,width:130, align:'center',renderer:YouGou.Util.timeFixed},
    { title:'会员账号', name:'memberName', lockWidth:true, width:170, align:'left'},
	{ title:'交易号', name:'orderNo' , lockWidth:true, width:115, align:'center'},
	{ title:'交易金额', name:'orderAmount', lockWidth:true, width:80, align:'center',renderer:YouGou.Util.moneyFixed},
	{ title:'问题类型', name:'problem' ,width:220, lockWidth:true, align:'left',renderer:function(val,item){
		if(val == 1){
			return '不知道怎么使用网上支付，怎么安装插件';
		}else if(val == 2){
			return '没有我要使用的支付方式' + (item.wantPayment == null ? '':item.wantPayment);
		}else if(val == 3){
			return '网络故障或浏览器不支持';
		}else if(val == 4){
			return '尚未开通网上银行或第三方支付平台';
		}else if(val == 5){
			return '所支付限额超过了银行支付限额';
		}else if(val == 6){
			return '余额不足';
		}else if(val == 7){
			return '银行卡卡号、密码或验证码输入有误';
		}else if(val == 8){
			return '其他' + (item.getOtherProblem == null ? "":item.getOtherProblem);
		}else {
			return '';
		}
	}},
	{ title:'意见及建议', name:'opinion' ,width:250, align:'left',position:"top"},
];
// 分页器
var mmPaginator = $('#grid-pager').mmPaginator({});

// 搜索表单属性
var mmFormParams = new MMSearchFormParams("searchForm");

// 表格	
var mmGrid = $('#grid-table').mmGrid({
	height: 'auto',
	cols: cols,
	url: '/order/queryOnlinePayProblemData.sc',
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

J('#startTime').calendar({maxDate:'#endTime',format:'yyyy-MM-dd HH:mm:ss'});
J('#endTime').calendar({minDate:'#startTime',format:'yyyy-MM-dd HH:mm:ss'});