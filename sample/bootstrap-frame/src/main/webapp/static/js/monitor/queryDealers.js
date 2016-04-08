/*****************可疑代销商 start************************/
J('#stateTime').calendar({maxDate:'#endTime',format:'yyyy-MM-dd HH:mm:ss'});
J('#endTime').calendar({minDate:'#stateTime',format:'yyyy-MM-dd HH:mm:ss'});
var suspiciousNameFixed = function(val){
	if(val == 1){
		return "相同IP";
	}else if(val == 2){
		return "相同会员账号";
	}else if(val == 3){
		return "相同收货人手机";
	}
};
var suspiciousFixed1 = function(val,item,rowIndex){
	if(item.suspiciousId == 1 && !YouGou.Util.isEmpty(item.suspiciousDetail)){
		return item.suspiciousDetail;
	}else{
		return "";
	}
};
var suspiciousFixed2 = function(val,item,rowIndex){
	if(item.suspiciousId == 2 && !YouGou.Util.isEmpty(item.suspiciousDetail)){
		return item.loginName;
	}else{
		return "";
	}
};
var suspiciousFixed3 = function(val,item,rowIndex){
	if(item.suspiciousId == 3 && !YouGou.Util.isEmpty(item.suspiciousDetail)){
		return item.suspiciousDetail;
	}else{
		return "";
	}
};
var cols = [
    { title:'置为可疑时间', name:'crateTime',width:150,lockWidth:true, align:'center',renderer: YouGou.Util.timeFixed},
    { title:'可疑条件', name:'suspiciousId',  width:100, lockWidth:true,align:'center',renderer: suspiciousNameFixed},
	{ title:'下单IP', name:'suspiciousDetail' ,align:'center',renderer: suspiciousFixed1},
	{ title:'会员账号', name:'loginName', width:200, align:'left',renderer: suspiciousFixed2},
	{ title:'收货人手机', name:'suspiciousDetail' , align:'center',renderer: suspiciousFixed3},
	{ title:'可疑原因', name:'suspiciousName',width:240,align:'left'},
	{ title:'涉及订单数', name:'orderCount' ,width:100,lockWidth:true, align:'center'},
	{ title:'涉及会员账号数', name:'memberCount' ,width:110, lockWidth:true, align:'center'},
    { title:'操作', name:'' ,width:60, align:'center', lockWidth:true, lockDisplay: true, renderer: function(val){
        return '<a href="javascript:void(0);" action="select">详情</a>';
    }}
];

// 分页器
var mmPaginator = $('#grid-pager').mmPaginator({});

// 搜索表单属性
var mmFormParams = new MMSearchFormParams("searchForm");

// 表格	
var mmGrid = $('#grid-table').mmGrid({
	height: 'auto',
	cols: cols,
	url: '/monitor/queryDealersData.sc',
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

mmGrid.on('cellSelected', function(e, item, rowIndex, colIndex){
	var action = $(e.target).attr("action");
    //查看
	if(action == "select"){
        window.open('/monitor/queryDealersDetailPage.sc?suspiciousId='+ item.suspiciousId +'&suspiciousDetail='+item.suspiciousDetail);
    }
    e.stopPropagation();  //阻止事件冒泡
});

/*****************可疑代销商 end************************/


/*****************可疑相似地址 start************************/
J('#startDate').calendar({maxDate:'#startDate',format:'yyyy-MM-dd HH:mm:ss'});
J('#endDate').calendar({minDate:'#endDate',format:'yyyy-MM-dd HH:mm:ss'});
var cols2 = [
    { title:'置为可疑时间', name:'ceateTime',width:150, lockWidth:true,align:'center',renderer:YouGou.Util.timeFixed},
    { title:'疑似地址', name:'consigneeAddress',  width:550, align:'left'},
	{ title:'主订单号', name:'orderMainNo' , width:150, align:'center'},
	{ title:'涉及会员数量', name:'memberCount', width:80,lockWidth:true, align:'center'},
	{ title:'涉及订单数量', name:'orderCount' ,width:80,lockWidth:true, align:'center'},
    { title:'操作', name:'' ,width:160, align:'center', lockWidth:true, lockDisplay: true, renderer: function(val){
        return '<a href="javascript:void(0);" action="addRisk">添加至风险库</a>&nbsp;&nbsp;<a href="javascript:void(0);" action="select">详情</a>'
    }}
];

// 分页器
var mmPaginator2 = $('#grid-pager2').mmPaginator({});

// 搜索表单属性
var mmFormParams2 = new MMSearchFormParams("searchForm2");
  
// 表格	
var mmGrid2 = $('#grid-table2').mmGrid({
	height: 'auto',
	cols: cols2,
	url: '/monitor/queryMemberDealersSimilary.sc',
	fullWidthRows: true,
	autoLoad: false,
	plugins: [
	    mmFormParams2 , mmPaginator2
	]
});

$('#myTab li a').click(function(){
	var action = parseInt($(this).attr("action"));
    if(action == 1){
    	mmGrid1.load();
    }else if(action == 2){
    	mmGrid2.load();
    }
});

// 查询
function doQuery2(){
   mmGrid2.load();
}

mmGrid2.on('cellSelected', function(e, item, rowIndex, colIndex){
	var action = $(e.target).attr("action");
    //添加至风险库
    if(action == "addRisk"){
    	addRiskStorage(item.orderMainNo);
    //详情
    }else if(action == "select"){
    	window.open('/monitor/toQueryMemberDealersDetailSimilary.sc?id='+ item.id);
    }
    e.stopPropagation();
});

//添加至风险库——弹出框
function addRiskStorage(orderMainNo){
	YouGou.UI.Dialog.show({
		title : '添加至风险库',
		message: function(dialog) {
			var $message = $('<div></div>');
	        var pageToLoad = dialog.getData('pageToLoad');
	        $message.load(pageToLoad);
	        return $message;
		},
       data: {
            'pageToLoad': '/monitor/toAddSurePageToRiskStorage.sc?orderMainNo='+orderMainNo
       },
		buttons: [
		    {
				label: '确定',
				cssClass: 'btn-primary',
				action: function() {
					addSurePageToRiskStorage();
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
//添加至风险库——操作保存
function addSurePageToRiskStorage(){
	YouGou.Ajax.doPost({
		successMsg: '添加至风险库操作成功!',
		url: '/monitor/addDealersToRiskStorage.sc',
	  	data: $("#addRiskForm").serializeArray(),
	  	success : function(data){
	  		window.location.reload();
		}
	});
}
/*****************可疑相似地址 end************************/
