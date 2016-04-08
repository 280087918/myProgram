/*************
	SellerInfo
**************/
// 操作列动作
var actionFixed = function(val,item,rowIndex){
	var state = item.state;
	var html = [];
	if(state == "3"){
		html.push('<a href="javascript:void(0);" action="stop">停止合作</a>&nbsp;&nbsp;');
	}else if(state == "4"){
		html.push('<a ><font color="#D3D3D3">停止合作</font></a>&nbsp;&nbsp;');
	}
	html.push('<a href="javascript:void(0);" action="query">查看</a>&nbsp;&nbsp;');
    return html.join('');
};

var stateDesc = function(val,item,rowIndex){
	if(val == "3"){
		return "合作中";
	}else if(val == "4"){
		return "已停止";
	}else{
		return "其他";
	}
				
}

// 列集合
var cols = [
	
	{ title:'会员账号', name:'loginName', align:'left'},
	{ title:'会员姓名', name:'sellerName', align:'left'},	
	{ title:'佣金收入总额(元)', name:'commisionAmount', align:'center'},
	{ title:'状态', name:'state', align:'center', renderer:stateDesc},
	{ title:'审核通过时间', name:'passDate', align:'center', renderer: YouGou.Util.timeFixed},
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
	url: '/user/querySellerData.sc',
	fullWidthRows: true,
	autoLoad: true,
	plugins: [mmPaginator,mmFormParams]
});

J('#registerDateStart').calendar({maxDate:'#registerDateEnd',format:'yyyy-MM-dd HH:mm:ss'}); 
J('#registerDateEnd').calendar({minDate:'#registerDateStart',format:'yyyy-MM-dd HH:mm:ss'});

// 表格事件
mmGrid.on('cellSelected', function(e, item, rowIndex, colIndex){
	var action = $(e.target).attr("action");
	var id = item.id;
	
    if(action == "stop"){// 停止合作
    	stop(id);
    }else if(action == "query"){//查看
    	//YouGou.UI.Dialog.alert({message:"查看"});
    	window.location.href="/user/getSellerInfoById.sc?sellerId="+id
    }
    e.stopPropagation();  //阻止事件冒泡
});


//分销商停止合作弹出框
function stop(sellerId){
	YouGou.UI.Dialog.confirm({
			message : "确定要停止合作吗？"
		},function(result){
			if(result) {
				doAjaxStop(sellerId);
        }
	});
}

function doAjaxStop(sellerId){
	
	YouGou.Ajax.doPost({
   		successMsg: '',
   		url: '/user/sellerStop.sc',
   		dataType:"json",
   	  	data: { "id" : sellerId },
   	  	success : function(data){
   	  		//YouGou.Util.inspect(data);
   	  		if(data.data.resultCount >0 ){
   	  			YouGou.UI.Dialog.alert({message:"停止合作成功"});
   	  			mmGrid.load();
   	  		}else{
   	  			YouGou.UI.Dialog.alert({message:"停止合作失败"});
   	  		}
     			
   		}
   	});
}



function doQuery(){
	mmGrid.load();
}