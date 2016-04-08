/*************
	FinSellerAccountWithdraw
**************/
// 操作列动作
var actionFixed = function(val,item,rowIndex){
	var html = [];
	html.push('<a href="javascript:void(0);" action="select">审批</a>&nbsp;&nbsp;');
    return html.join('');
};

var modifyFixed = function(val,item,rowIndex){
	var html = [];
	html.push('<a href="javascript:void(0);" action="select">付款</a>&nbsp;&nbsp;');
    return html.join('');
};

// 待审核列集合
var cols = [
	{ title:'提现申请单号', name:'withdrawApplyNo', align:'left'},
	{ title:'申请时间', name:'applyTime', align:'center', renderer: YouGou.Util.timeFixed},
	{ title:'店铺名称', name:'shopName', align:'left'},
	{ title:'分销商账号', name:'sellerAccount', align:'left'},
	{ title:'提现金额', name:'withdrawAmount', align:'center'},
	{ title:'支付方式', name:'paymenStyle', align:'left',renderer:function(val,item,rowIndex){
		if(item.paymenStyle=='1'){
			return '支付宝'
		}
	}},
	{ title:'开户名', name:'accountName', align:'left'},
	{ title:'提现时间', name:'modifyTime', align:'center', renderer: YouGou.Util.timeFixed},
	{ title:'操作', name:'' ,width:200, align:'center',lockWidth:true, renderer: actionFixed},
    { title:'ID', name:'id', hidden: true}
];

//待付款列集合
var modifycols = [
        	{ title:'提现申请单号', name:'withdrawApplyNo', align:'left'},
        	{ title:'申请时间', name:'applyTime', align:'center', renderer: YouGou.Util.timeFixed},
        	{ title:'店铺名称', name:'shopName', align:'left'},
        	{ title:'分销商账号', name:'sellerAccount', align:'left'},
        	{ title:'账户余额', name:'accountBalance', align:'center', hidden: true},
        	{ title:'实收费用', name:'actualReceivedAmount', align:'center', hidden: true},
        	{ title:'服务费', name:'serviceAmount', align:'center', hidden: true},
        	{ title:'提现金额', name:'withdrawAmount', align:'center'},
        	{ title:'支付方式', name:'paymenStyle', align:'left',renderer:function(val,item,rowIndex){
        		if(item.paymenStyle=='1'){
        			return '支付宝'
        		}
        	}},
        	{ title:'开户名', name:'accountName', align:'left'},
        	{ title:'对方开户行', name:'accountBankNo', align:'left', hidden: true},
        	{ title:'账号', name:'accountNo', align:'left', hidden: true},
        	{ title:'申请原因说明', name:'applyReason', align:'left', hidden: true},
        	{ title:'申请人', name:'applyer', align:'left'},
        	{ title:'审核人', name:'operater', align:'left'},
        	{ title:'审核时间', name:'operaterTime', align:'center', renderer: YouGou.Util.timeFixed},
        	{ title:'付款人', name:'modifier', align:'left', hidden: true},
        	{ title:'提现时间', name:'modifyTime', align:'center', renderer: YouGou.Util.timeFixed},
        	{ title:'单据状态', name:'billStatus', align:'left',renderer:function(val,item,rowIndex){
          		if(item.billStatus=='1'){
          			return '待审核'
          		}else if(item.billStatus=='2'){
          			return '已审核'
          		}else if(item.billStatus=='3'){
          			return '已体现'
          		}else if(item.billStatus=='4'){
          			return '审核不通过'
          		}
          	}},
        	{ title:'备注说明', name:'remark', align:'left'},
        	{ title:'操作', name:'' ,width:200, align:'center',lockWidth:true, renderer: modifyFixed},
            { title:'ID', name:'id', hidden: true}
        ];

var allcols = [
              	{ title:'提现申请单号', name:'withdrawApplyNo', align:'center'},
              	{ title:'申请时间', name:'applyTime', align:'center', renderer: YouGou.Util.timeFixed},
              	{ title:'店铺名称', name:'shopName', align:'left'},
              	{ title:'分销商账号', name:'sellerAccount', align:'left'},
              	{ title:'账户余额', name:'accountBalance', align:'center', hidden: true},
              	{ title:'实收费用', name:'actualReceivedAmount', align:'center', hidden: true},
              	{ title:'服务费', name:'serviceAmount', align:'center', hidden: true},
              	{ title:'提现金额', name:'withdrawAmount', align:'center'},
              	{ title:'支付方式', name:'paymenStyle', align:'left',renderer:function(val,item,rowIndex){
              		if(item.paymenStyle=='1'){
              			return '支付宝'
              		}
              	}},
              	{ title:'开户名', name:'accountName', align:'left'},
              	{ title:'对方开户行', name:'accountBankNo', align:'left', hidden: true},
              	{ title:'账号', name:'accountNo', align:'left', hidden: true},
              	{ title:'申请原因说明', name:'applyReason', align:'left', hidden: true},
              	{ title:'申请人', name:'applyer', align:'left'},
              	{ title:'审核人', name:'operater', align:'left'},
              	{ title:'审核时间', name:'operaterTime', align:'center', renderer: YouGou.Util.timeFixed},
              	{ title:'付款人', name:'modifier', align:'left'},
              	{ title:'提现时间', name:'modifyTime', align:'center', renderer: YouGou.Util.timeFixed},
              	{ title:'单据状态', name:'billStatus', align:'left',renderer:function(val,item,rowIndex){
              		if(item.billStatus=='1'){
              			return '待审核'
              		}else if(item.billStatus=='2'){
              			return '已审核'
              		}else if(item.billStatus=='3'){
              			return '已体现'
              		}else if(item.billStatus=='4'){
              			return '审核不通过'
              		}
              	}},
              	{ title:'备注说明', name:'remark', align:'left'}
              ];

//初始日期格控件
J('#applyTimeStart').calendar({maxDate:'#applyTimeEnd',format:'yyyy-MM-dd'}); 
J('#applyTimeEnd').calendar({minDate:'#applyTimeStart',format:'yyyy-MM-dd'});

//分页器
var mmPaginator = $('#grid-pager').mmPaginator({});
// 搜索表单属性
var mmFormParams = new MMSearchFormParams("searchForm");

// 表格	
var mmGrid = $('#grid-table').mmGrid({
	height: 'auto',
	cols: cols,
	url: '/finance/accountwithdraw/querySellerData.sc',
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
                removeSeller(item.id);
            }
   		});
    }else if(action == "select"){//查看
    	YouGou.UI.Dialog.alert({message:"查看"});
    }
    e.stopPropagation();  //阻止事件冒泡
});
//删除
function removeSeller(id){
	YouGou.Ajax.doPost({
		successMsg: '删除成功！',
		url: '/finance/removeSeller.sc',
	  	data: { "id" : id },
	  	success : function(data){
  			mmGrid.load();
		}
	});
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
	mmGrid.load();
}

//分页器
var modifyPaginator = $('#modify-pager').mmPaginator({});
//搜索表单属性
var modifyFormParams = new MMSearchFormParams("searchForm2");
//表格	
var modifyGrid = $('#modify-table').mmGrid({
	height: 'auto',
	cols: modifycols,
	url: '/finance/accountwithdraw/querySellerData.sc',
	fullWidthRows: true,
	autoLoad: true,
	plugins: [modifyFormParams , modifyPaginator]
});

//查询
function doQuery2(){
	modifyGrid.load();
}

//分页器
var allPaginator = $('#all-pager').mmPaginator({});
//搜索表单属性
var allFormParams = new MMSearchFormParams("searchFormAll");
//表格	
var modifyGrid = $('#all-table').mmGrid({
	height: 'auto',
	cols: allcols,
	url: '/finance/accountwithdraw/querySellerData.sc',
	fullWidthRows: true,
	autoLoad: true,
	plugins: [allFormParams , allPaginator]
});

//查询
function doQuery3(){
	modifyGrid.load();
}