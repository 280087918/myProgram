//主表格
var cols = [
    { title:'会员账号', name:'loginName', align:'left',renderer : function(val,item,rowIndex) {
    	return '<input type="hidden" id="loginId" value="'+item.loginaccountId+'"><a href="/monitor/queryMemberOrderAnalysis.sc?loginaccountId='+item.loginaccountId+'" target="_blank">'+val+'</a>';
    }},
    { title:'已绑定手机', name:'', align:'center',renderer : function(val,item,rowIndex) {
    	if(item.isBindPhone!=null && item.isBindPhone==1){
    		return '是';
    	}else{
    		return '否';
    	}
    }},
	{ title:'会员身份', name:'level' , align:'center', renderer: YouGou.Util.memberShipNameFixed},
	{ title:'涉及订单数', name:'',  align:'center',renderer : function(val,item,rowIndex) {
    	var dealersDetail = item.dealersDetail;
    	if(dealersDetail!=null){
    		return dealersDetail.length;
    	}else{
    		return 0;
    	}
    }}
];

// 分页器
var mmPaginator = $('#grid-pager').mmPaginator({});

// 搜索表单属性
var mmFormParams = new MMSearchFormParams("searchForm");

//二级子表格
var subGridRowExpanded2 = function(e,item,subGridTableId,subGridPagerId){
	var cols = [
        /*{ title:'下单时间', name:'',width:120, lockWidth:true,align:'center',renderer:function(){
        	var val = item.orderTime;
        	return val.substring(0,val.length-2);
        }}, */
        { title:'订单号', name:'orderSubNo',width:130,align:'center'},
        /*{ title:'收货地址', name:'', align:'left',renderer:function(val,item){
        	return item.orderConsigneeInfo.consigneeAddress;
        }}, 
        { title:'收货人', name:'userName',width:70, lockWidth:true,align:'center',renderer:function(val,item){
        	return item.orderConsigneeInfo.userName;
        }}, 
    	{ title:'收货人手机', name:'' ,width:100, lockWidth:true, align:'center',renderer:function(val,item){
        	return item.orderConsigneeInfo.mobilePhone;
        }}, 
    	{ title:'支付方式', name:'paymentName',width:80, lockWidth:true , align:'center'},*/
    	{ title:'支付状态', name:'payStatusName',width:80, lockWidth:true , align:'center'},
    	{ title:'订单状态', name:'orderStatusName',width:120, lockWidth:true, align:'center'}
    ];
	var submmGrid = $('#'+subGridTableId).mmGrid({
		cols: cols,
		height: "auto",
		width: 350,
		fullWidthRows: true,
		url: '/monitor/queryMemberOrderSubByOrderMainNo.sc?orderMainNo='+item.orderMainNo,
		autoLoad: true,
		noDataText:'没有数据',
		isSubGrid: true,
		subGridCol : true,
		plugins: []
	});
	return submmGrid;
};

var subGrids = [];

//子表格
var subGridRowExpanded = function(e,item,subGridTableId,subGridPagerId){
	var cols = [
        { title:'下单时间', name:'orderTime',width:125, lockWidth:true,align:'center',renderer:YouGou.Util.timeFixed}, 
        { title:'订单号', name:'orderMainNo',width:125, lockWidth:true,align:'center'},
        { title:'收货地址', name:'consigneeAddress',align:'left',renderer:function(val,item,rowIndex){
        	if(item.dealersFlag==1){
    			return '<span class="label label-info arrowed-in-right arrowed">原地址</span>'+val;
    		}else{
    			return val;
    		}
        }},
        { title:'收货人', name:'userName',width:90, lockWidth:true,align:'center'},
    	{ title:'收货人手机', name:'mobilePhone' ,width:100, lockWidth:true, align:'center'},
    	{ title:'相似度', name:'similarity',width:80, lockWidth:true , align:'center',renderer:YouGou.Util.percentFixed}
    ];
	var submmGrid = $('#'+subGridTableId).mmGrid({
		cols: cols,
		height: "auto",
		fullWidthRows: true,
		width: 1100,
		items : item.dealersDetail,
		checkCol: true,
		multiSelect: true,
		isSubGrid: true,
		subGridCol : true,
		isSubGridExpanded : false,// 是否默认展开子表格
		subGridRowExpanded : subGridRowExpanded2,
		plugins: []
	});
	subGrids.push(submmGrid);
	return submmGrid;
};

//主表格	
var mmGrid = $('#grid-table').mmGrid({
	height: 'auto',
	cols: cols,
	url: '/monitor/queryMemberDealersDetailSimilary.sc',
	autoLoad: true,
	fullWidthRows: true,
	checkCol: true,
	multiSelect: true,
	noDataText:'没有数据',
	subGridCol : true,// 子表格列
	isSubGridExpanded : true,
	subGridRowExpanded : subGridRowExpanded,// 展开子表格事件
	plugins: [
	    mmFormParams , mmPaginator
	]
});

//批量添加至风险库——弹出框
function addRiskStorage(){
	var idsString = new Array();
	$.each(subGrids,function(index){
		var subGrid = subGrids[index];
		var rows = subGrid.selectedRows();
		$.each(rows,function(index){
			idsString.push(rows[index].id);
		});
	});
	
	if(idsString.length == 0){
		YouGou.UI.Dialog.show({message : '请选择要批量的操作!'});
		return;
	}
	$('#idsString').val(idsString);
	YouGou.UI.Dialog.show({
		title : '批量添加至风险库',
		message: function(dialog) {
			var $message = $('<div></div>');
	        var pageToLoad = dialog.getData('pageToLoad');
	        $message.load(pageToLoad);
	        return $message;
		},
       data: {
            'pageToLoad': '/monitor/toAddSurePageToRiskStorage.sc'
       },
		buttons: [
		    {
				label: '确定',
				cssClass: 'btn-primary',
				action: function() {
					addAllSurePageToRiskStorage();
				}
		    }, 
			{
		    	label: '取消',
		    	action: function(dialog) {
		    		dialog.close();
		    	}
			}
	    ]
	});	
}
//批量添加至风险库——保存
function addAllSurePageToRiskStorage(){
	YouGou.Ajax.doPost({
		successMsg: '批量添加至风险库操作成功!',
		url: '/monitor/addAllDealersToRiskStorage.sc',
		data:{"suspiciousName":$('#suspiciousName').val(),"idsString":$("#idsString").val()},
	  	success : function(data){
	  		window.location.reload();
		}
	});
}

//移除可疑列表
function queryToUserSign(){
	YouGou.UI.Dialog.confirm({
  		message : "确定移除整个可疑列表吗？点击确定,会将所有的本页所有的可疑信息移除。请慎重选择！"
  	},function(result){
  		if(result) {
  			YouGou.Ajax.doPost({
      		  successMsg: "操作成功!",
      		  url: "/monitor/setDealersToNormal.sc",
      		  data:{"parentId":$(parentId).val()},
      		  success : function() {
      			window.location.reload();
      		  }
  			});
          }
  	});
}
