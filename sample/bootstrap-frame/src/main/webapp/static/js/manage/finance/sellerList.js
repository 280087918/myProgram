/*************
	FinSellerInfo
**************/
// 操作列动作
var actionFixed = function(val,item,rowIndex){
	var html = [];
	html.push('<a href="javascript:void(0);" action="seeinfor">详情</a>&nbsp;&nbsp;');
    return html.join('');
};

// 列集合
var cols = [
    { title:'分销商账户', name:'sellerAccount', align:'left'},        
	{ title:'店铺名称', name:'shopName', align:'left'},
	{ title:'注册时间', name:'registerTime', align:'center', renderer: YouGou.Util.timeFixed},
	{ title:'最近交易时间', name:'latelyTransactionTime', align:'center', renderer: YouGou.Util.timeFixed},
	{ title:'账户余额', name:'accountBalance', align:'center',renderer:function(val,item,rowIndex){
		return "<font color='red'>"+item.accountBalance+"<font>"
	}},
	{ title:'操作', name:'' ,width:200, align:'center',lockWidth:true, renderer: actionFixed},
    { title:'ID', name:'id', hidden: true}
];

//明细列集合
var detailcols = [
	{ title:'交易流水号', name:'transactionNumber', align:'left'},
	{ title:'交易时间', name:'transactionTime', align:'center', renderer: YouGou.Util.timeFixed},
	{ title:'交易类型', name:'transactionType', align:'left',renderer:function(val,item,rowIndex){
		if(item.transactionType=='1'){
			return '佣金收益'
		}else if(item.transactionType=='2'){
			return '提现'
		}
	}},
	{ title:'收入金额', name:'incomeAmount', align:'center',renderer:function(val,item,rowIndex){
		return "<font color='green'>"+item.incomeAmount+"<font>"
	}},
	{ title:'支出金额', name:'expendAmount', align:'center',renderer:function(val,item,rowIndex){
		return "<font color='red'>"+item.expendAmount+"<font>"
	}},
	{ title:'账户余额', name:'accountBalance', align:'center'},
	{ title:'支付方式', name:'paymentStyle', align:'left',renderer:function(val,item,rowIndex){
		if(item.paymentStyle=='1'){
			return '支付宝'
		}
	}},
	{ title:'交易订单号', name:'transactionOrderNum', align:'left'},
	{ title:'单据状态', name:'billState', align:'left',renderer:function(val,item,rowIndex){
		if(item.billState=='1'){
			return '交易成功'
		}else if(item.billState=='2'){
			return '处理中'
		}else if(item.billState=='3'){
			return '交易关闭'
		}else if(item.billState=='4'){
			return '交易失败'
		}
	}},
	{ title:'操作人', name:'operater', align:'left'},
	{ title:'备注', name:'remark', align:'left'},
	{ title:'分销商ID', name:'sellerId', align:'left',hidden: true},
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
	url: '/finance/querySellerData.sc',
	fullWidthRows: true,
	autoLoad: true,
	plugins: [mmPaginator,mmFormParams]
});

// 表格事件
mmGrid.on('cellSelected', function(e, item, rowIndex, colIndex){
	var action = $(e.target).attr("action");
    //编辑
    if(action == "seeinfor"){
    	var sellerid = item.id
    	$('#myModal').modal('show')
    	//lable标签账户信息赋值.
    	$('#selleraccountlable')[0].innerText=item.sellerAccount;
    	$('#accountbalancelable')[0].innerText=item.accountBalance;
    	//初始日期格控件
    	J('#transactionTimeStart').calendar({maxDate:'#transactionTimeEnd',format:'yyyy-MM-dd'}); 
    	J('#transactionTimeEnd').calendar({minDate:'#transactionTimeStart',format:'yyyy-MM-dd'});
    	//将sellerid隐藏在表格控件中.
   	    $('#sellerId')[0].value=sellerid
    	//初始化表格数据.
    	var detailTable = initDetailTable();
   	    detailTable.load();
    }
    e.stopPropagation();  //阻止事件冒泡
});


//初始化明细表的表格控件.
function initDetailTable(){	
	 //分页器
	 var mmPaginator_qick = $('#grid-pager-qick').mmPaginator({});
	 // 搜索表单属性
	 var mmFormParams_qick = new MMSearchFormParams("queryform");
	 mmGrid_qick = $('#grid-table-qick').mmGrid({
	 height: 'auto',
	 cols: detailcols,
	 url: '/finance/sellerdetail/querySellerData.sc',
	 fullWidthRows: true,
	 noDataText: '没有数据',
	 plugins: [mmFormParams_qick , mmPaginator_qick] });
	 return mmGrid_qick
}

//查询直销商账户余额明细记录.
function querySellerDetaillist(){
	//初始化表格控件，重新做相应的表格控件加载.
	var detailTable = initDetailTable();
	detailTable.load();
}

//更新状态时使用
function updateSeller(id,status){
	YouGou.Ajax.doPost({
		successMsg: '更新成功！',
		url: '/finance/saveSeller.sc',
	  	data: { "id" : id ,"status" : status },
	  	success : function(data){
  			mmGrid.load();
		}
	});
}
//查询
function doQuery(){
	if ($('#amountCheck')[0].checked) {
		$('#amountCheckFlag')[0].value='TRUE';
	}else{
		$('#amountCheckFlag')[0].value='';
	}
	mmGrid.load();
}