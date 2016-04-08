/************会员列表 start ***************/
//添加时间控件
J('#registStartDate').calendar({maxDate:'#registEndDate',format:'yyyy-MM-dd HH:mm:ss'});
J('#registEndDate').calendar({minDate:'#registStartDate',format:'yyyy-MM-dd HH:mm:ss'});

var cols = [
{ title:'登录名', name:'login_name',width:80, align:'left'},
{ title:'会员等级', name:'level_name', lockWidth:true, width:100, align:'center'},
{ title:'会员来源', name:'' , lockWidth:true, width:100, align:'center', renderer: function(val,item,rowIndex){
	if(item.number_id!=null){
		if(item.number_id=='taoxiu' || item.number_id=='taoxiu_yougou'){
			return '淘秀网'
		}else if(item.number_id=='renren'){
			return '人人网'
		}else if(item.number_id=='qqfanli'){
			return 'QQ返利'
		}else if(item.number_id=='qq'){
			return 'QQ'
		}else if(item.number_id=='mobile_android'){
			return '手机平台-Android'
		}else if(item.number_id=='mobile_ios'){
			return '手机平台-IOS'
		}else if(item.number_id=='alipay'){
			return '支付宝'
		}else if(item.number_id=='51fanli'){
			return '51返利'
		}else if(item.number_id=='139fanli'){
			return '139返利'
		}else if(item.number_id=='sinaWeibo'){
			return '新浪微博'
		}else{
			return '未知'+'['+item.number_id+']'
		}
	}else if(item.member_source!=null){
		if(item.member_source.indexOf('TB')==0){
			return '淘宝'
		}else if(item.member_source.indexOf('WBPT')==0){
			return '外部平台'
		}else{
			return '未知'
		}
	}else{
		return '优购'
	}
}},
{ title:'积分', name:'integral_total', lockWidth:true, width:75, align:'center'},
{ title:'会员身份', name:'member_ship' ,width:70, lockWidth:true, align:'center',  renderer: YouGou.Util.memberShipNameFixed},
{ title:'注册时间', name:'register_date' ,width:150, lockWidth:true, align:'center', renderer: YouGou.Util.timeFixed},
{ title:'最后登录时间', name:'last_time_out' ,width:150, lockWidth:true, align:'center'},
{ title:'操作', name:'' ,align:'center',width:200,lockWidth:true, renderer: function(){
	return '<a href="javascript:void(0);" action="updateShip">修改身份</a>&nbsp;&nbsp;<a href="javascript:void(0);" action="selectLog">变更日志</a>&nbsp;&nbsp;';
}}
];

//分页器
var mmPaginator = $('#grid-pager').mmPaginator({});

//搜索表单属性
var mmFormParams = new MMSearchFormParams("searchForm");

//表格	
var mmGrid = $('#grid-table').mmGrid({
	height: 'auto',
	cols: cols,
	url: '/member/queryMemberShipList.sc',
	indexCol: true,
	indexColWidth: 45,
	noDataText: '没有数据',
	fullWidthRows: true,
	autoLoad: false,
	plugins: [
	    mmFormParams , mmPaginator
	]
});

//查询
function doQuery(){
	// 条件输入校验
	if(mmFormParams.check()){
	    mmGrid.load();
	}
}

mmGrid.on('cellSelected', function(e, item, rowIndex, colIndex){
	var action = $(e.target).attr("action");
	//修改身份
	if(action == "updateShip"){
		showEditMemberShip(item.id);
	//变更日志
	}else if(action == "selectLog"){
		window.open('/member/queryMemberShipChangeLogs.sc?loginaccountId='+ item.id);
		
	} 
	e.stopPropagation();
});

//隐藏表格显示表单
function showForm(){
	$("#girdContent").addClass("hide");
	$("#memberShipNavbar,#memberShipForm").removeClass("hide");
	YouGou.UI.resetForm("memberShipForm");
}

//隐藏表单显示表格
function hideForm(){
	$("#memberShipNavbar,#memberShipForm").addClass("hide");
	$("#girdContent").removeClass("hide");
}
/************会员列表 end ***************/




/************会员身份变更日志 start ***************/
//添加时间控件
J('#minTime').calendar({maxDate:'#maxTime',format:'yyyy-MM-dd HH:mm:ss'});
J('#maxTime').calendar({minDate:'#minTime',format:'yyyy-MM-dd HH:mm:ss'});

var cols2 = [
{ title:'变更时间', name:'operatingTime',width:120, align:'center', renderer: YouGou.Util.timeFixed},
{ title:'订单号', name:'orderNo', lockWidth:true, width:150, align:'center'},
{ title:'会员账号', name:'loginName' , width:150, align:'left'},
{ title:'原有状态', name:'originalShip', lockWidth:true, width:100, align:'center'},
{ title:'更新状态', name:'nowShip' ,width:100, lockWidth:true, align:'center'},
{ title:'变更原因', name:'' ,width:300, align:'left',lockWidth:true, renderer: function(val,item,rowIndex){
	var html = [];
	if(item.reasonShip!=null && item.reasonShip!=""){
		html.push(item.reasonShip);
		if(item.reasonModify!=null && item.reasonModify!=""){
			html.push("(");
			html.push(item.reasonModify);
			html.push(")");
		}
	}else{
		if(item.reasonModify!=null && item.reasonModify!=""){
			html.push(item.reasonModify);
		}
	}
    return html.join('');
}},
{ title:'操作人', name:'username' ,width:120, lockWidth:true, align:'center'}
];

//分页器
var mmPaginator2 = $('#grid-pager2').mmPaginator({});

//搜索表单属性
var mmFormParams2 = new MMSearchFormParams("searchForm2");

//表格	
var mmGrid2 = $('#grid-table2').mmGrid({
	height: 'auto',
	cols: cols2,
	url: '/member/queryMemberShipLogs.sc',
	indexCol: true,
	indexColWidth: 45,
	fullWidthRows: true,
	autoLoad: false,
	plugins: [
	    mmFormParams2 , mmPaginator2
	]
});

//查询
function doQuery2(){
	// 条件输入校验
	if(mmFormParams2.check()){
	    mmGrid2.load();
	}
}
/************会员身份变更日志 end ***************/
