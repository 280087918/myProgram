/*************
	Order
**************/
// 操作列动作
var actionFixed = function(val,item,rowIndex){
	var html = [];
	html.push('<a href="javascript:void(0);" action="select">查看</a>&nbsp;&nbsp;');
	html.push('<a href="javascript:void(0);" action="edit">编辑</a>&nbsp;&nbsp;');
	html.push('<a href="javascript:void(0);" action="delete">删除</a>&nbsp;&nbsp;');
    return html.join('');
};

// 列集合
var cols = [
	{ title:'微分销订单号', name:'wfxOrderNo', align:'left'},
	{ title:'外部订单号', name:'outerOrderNo', align:'left'},
	{ title:'分销商id', name:'sellerId', align:'left'},
	{ title:'店铺ID', name:'shopId', align:'left'},
	{ title:'店铺名称', name:'shopName', align:'left'},
	{ title:'supplierId', name:'supplierId', align:'left'},
	{ title:'支付方式', name:'payType', align:'left'},
	{ title:'付款时间', name:'payTime', align:'center', renderer: YouGou.Util.timeFixed},
	{ title:'实付金额。精确到2位小数;单位:元。如:200.07，表示:200元7分', name:'payment', align:'center'},
	{ title:'商品购买数量。取值范围：大于零的整数', name:'num', align:'center'},
	{ title:'商品金额', name:'totalFee', align:'center'},
	{ title:'系统优惠金额', name:'discountFee', align:'center'},
	{ title:'邮费。', name:'postFee', align:'center'},
	{ title:'订单状态。', name:'status', align:'left'},
	{ title:'买家账号', name:'buyerId', align:'left'},
	{ title:'buyerAccount', name:'buyerAccount', align:'left'},
	{ title:'买家留言', name:'buyerMessage', align:'left'},
	{ title:'买家昵称', name:'buyerNick', align:'left'},
	{ title:'收货人的姓名', name:'receiverName', align:'left'},
	{ title:'收货人的手机号码', name:'receiverMobile', align:'left'},
	{ title:'收货人的电话号码', name:'receiverPhone', align:'left'},
	{ title:'身份证号码', name:'idCardNo', align:'left'},
	{ title:'收货人的所在省份', name:'receiverState', align:'left'},
	{ title:'收货人的所在城市', name:'receiverCity', align:'left'},
	{ title:'收货人的所在地区', name:'receiverDistrict', align:'left'},
	{ title:'收货人的详细地址', name:'receiverAddress', align:'left'},
	{ title:'收货人的邮编', name:'receiverZip', align:'left'},
	{ title:'创建交易时的物流方式（交易完成前，物流方式有可能改变，但系统里的这个字段一直不变）。可选值：ems, express, post, free, virtual。', name:'shippingType', align:'left'},
	{ title:'收货时间段', name:'receiveTimeRange', align:'center'},
	{ title:'订单同步至外部平台状态', name:'syncStatus', align:'left'},
	{ title:'同步批次号,', name:'syncBatchNo', align:'left'},
	{ title:'同步描述,用于保存返回订单保存失败原因', name:'syncRemark', align:'left'},
	{ title:'订单创建时间。', name:'createdTime', align:'center', renderer: YouGou.Util.timeFixed},
	{ title:'订单修改时间。', name:'updateTime', align:'center', renderer: YouGou.Util.timeFixed},
	{ title:'确认收货时间', name:'confirmTime', align:'center', renderer: YouGou.Util.timeFixed},
	{ title:'订单关闭时间', name:'closeTime', align:'center', renderer: YouGou.Util.timeFixed},
	{ title:'人工退款标志', name:'manualRefundFlag', align:'center'},
	{ title:'操作', name:'' ,width:200, align:'center',lockWidth:true, renderer: actionFixed},
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
	url: '/order/queryOrderData.sc',
	fullWidthRows: true,
	autoLoad: true,
	plugins: [mmPaginator,mmFormParams]
});

// 表格事件
mmGrid.on('cellSelected', function(e, item, rowIndex, colIndex){
	var action = $(e.target).attr("action");
    //编辑
    if(action == "edit"){
    	YouGou.UI.Dialog.alert({message:"编辑"});
    }else if(action == "delete"){// 删除
   		YouGou.UI.Dialog.confirm({
   			message : "确定要删除吗？"
   		},function(result){
   			if(result) {
                removeOrder(item.id);
            }
   		});
    }else if(action == "select"){//查看
    	YouGou.UI.Dialog.alert({message:"查看"});
    }
    e.stopPropagation();  //阻止事件冒泡
});
//删除
function removeOrder(id){
	YouGou.Ajax.doPost({
		successMsg: '删除成功！',
		url: '/order/removeOrder.sc',
	  	data: { "id" : id },
	  	success : function(data){
  			mmGrid.load();
		}
	});
}
//更新状态时使用
function updateOrder(id,status){
	YouGou.Ajax.doPost({
		successMsg: '更新成功！',
		url: '/order/saveOrder.sc',
	  	data: { "id" : id ,"status" : status },
	  	success : function(data){
  			mmGrid.load();
		}
	});
}
//查询
function doQuery(){
	mmGrid.load();
}