/*************
	OrderRefund
**************/
// 操作列动作
var actionFixed = function(val,item,rowIndex){
	var html = [];
	html.push('<a href="javascript:void(0);" action="select">查看</a>&nbsp;&nbsp;');
    return html.join('');
};

// 列集合
var cols = [
	{ title:'退款单号', name:'refundNo', align:'left'},
	{ title:'订单号', name:'orderId', align:'left'},
	{ title:'子订单号', name:'orderDetailId', align:'left'},
	{ title:'商品编号', name:'prodId', align:'left'},
	{ title:'商品名称', name:'prodName', align:'left'},
	{ title:'买家昵称', name:'buyerNick', align:'left'},
	{ title:'分销商id', name:'sellerId', align:'left'},
	{ title:'店铺ID', name:'shopId', align:'left'},
	{ title:'店铺名称', name:'shopName', align:'left'},
	{ title:'交易总金额。', name:'totalFee', align:'center'},
	{ title:'退还金额(退还给买家的金额)。', name:'refundFee', align:'center'},
	{ title:'支付给卖家的金额(交易总金额-退还给买家的金额)。', name:'payment', align:'center'},
	{ title:'退款状态', name:'status', align:'left'},
	{ title:'货物状态', name:'goodStatus', align:'left'},
	{ title:'物流公司名称', name:'companyName', align:'left'},
	{ title:'退货运单号', name:'sid', align:'left'},
	{ title:'退款原因', name:'reason', align:'left'},
	{ title:'退款说明', name:'description', align:'left'},
	{ title:'买家是否需要退货。', name:'hasGoodReturn', align:'left'},
	{ title:'退款对应的订单交易状态', name:'orderStatus', align:'left'},
	{ title:'退款单同步状态', name:'syncStatus', align:'left'},
	{ title:'同步描述’,用于保存返回退款单保存失败原因', name:'syncRemark', align:'left'},
	{ title:'纪录创建时间', name:'createTime', align:'center', renderer: YouGou.Util.timeFixed},
	{ title:'纪录修改时间', name:'updateTime', align:'center', renderer: YouGou.Util.timeFixed},
	{ title:'是否存在超时可选值:true(是)’,false(否)', name:'existTimeout', align:'left'},
	{ title:'超时时间', name:'timeout', align:'center', renderer: YouGou.Util.timeFixed},
	{ title:'退款阶段(onsale,aftersale)', name:'refundPhase', align:'left'},
	{ title:'退款版本号', name:'refundVersion', align:'left'},
	{ title:'退款扩展属性', name:'attribute', align:'left'},
	{ title:'退款类型', name:'refundType', align:'left'},
	{ title:'退货数量', name:'proNum', align:'center'},
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
	url: '/afterSale/queryAfterSaleData.sc',
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
                removeAfterSale(item.id);
            }
   		});
    }else if(action == "select"){//查看
    	YouGou.UI.Dialog.alert({message:"查看"});
    }
    e.stopPropagation();  //阻止事件冒泡
});

function removeAfterSale(id){
	YouGou.Ajax.doPost({
		successMsg: '删除成功！',
		url: '/afterSale/removeAfterSale.sc',
	  	data: { "id" : id },
	  	success : function(data){
  			mmGrid.load();
		}
	});
}

function doQuery(){
	mmGrid.load();
}