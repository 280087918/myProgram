/*************
	SellerInfo
**************/
// 操作列动作
var actionFixed = function(val,item,rowIndex){
	var html = [];
	html.push('<a href="javascript:void(0);" action="pass">通过</a>&nbsp;&nbsp;');
	html.push('<a href="javascript:void(0);" action="refuse">拒绝</a>&nbsp;&nbsp;');
	html.push('<a href="javascript:void(0);" action="query">查看</a>&nbsp;&nbsp;');
    return html.join('');
};

// 列集合
var cols = [
	
	{ title:'会员账号', name:'loginName', align:'left'},
	{ title:'会员姓名', name:'sellerName', align:'left'},
	{ title:'订单总数', name:'orderCount', align:'center'},
	{ title:'订单总金额(元)', name:'orderAmount', align:'center'},
	{ title:'申请时间', name:'createTime', align:'center', renderer: YouGou.Util.timeFixed},
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
	url: '/user/querySellerAuditData.sc',
	fullWidthRows: true,
	autoLoad: true,
	plugins: [mmPaginator,mmFormParams]
});

J('#createTimeStart').calendar({maxDate:'#createTimeEnd',format:'yyyy-MM-dd HH:mm:ss'}); 
J('#createTimeEnd').calendar({minDate:'#createTimeStart',format:'yyyy-MM-dd HH:mm:ss'});

// 表格事件
mmGrid.on('cellSelected', function(e, item, rowIndex, colIndex){
	var action = $(e.target).attr("action");
	var id = item.id;
    //通过
    if(action == "pass"){   	
    	YouGou.UI.Dialog.confirm({
   			message : "确定要审核通过吗？"
   		},function(result){
   			if(result) {
   				doPassAjax(id);
            }
   		});
    }else if(action == "refuse"){// 拒绝
    	refuse(id);
    }else if(action == "query"){//查看
    	//YouGou.UI.Dialog.alert({message:"查看"});
    	window.location.href="/user/getSellerAuditInfoById.sc?sellerId="+id
    }
    e.stopPropagation();  //阻止事件冒泡
});

/*function removeSeller(id){
	YouGou.Ajax.doPost({
		successMsg: '删除成功！',
		url: '/user/querySellerAuditData.sc',
	  	data: { "id" : id },
	  	success : function(data){
  			mmGrid.load();
		}
	});
}*/

//ajax 审核通过
function doPassAjax(id){
	
	YouGou.Ajax.doPost({
   		successMsg: '',
   		url: '/user/sellerAuditPass.sc',
   		dataType:"json",
   	  	data: { "id" : id },
   	  	success : function(data){
   	  		//YouGou.Util.inspect(data);
   	  		if(data.data.resultCount >0 ){
   	  			YouGou.UI.Dialog.alert({message:"审核通过"});
   	  			mmGrid.load();
   	  		}else{
   	  			YouGou.UI.Dialog.alert({message:"审核失败"});
   	  		}
     			
   		}
   	});
}

//分销商审核审核拒绝弹出框
function refuse(sellerId){
	YouGou.UI.Dialog.show({
		title : '分销商审核拒绝',
		message: function(dialog) {
			var msg = "<div>拒绝原因：<textarea id='refuseRemark' style='width:300px;height:200px' /></div>";
			return msg;
		},
		buttons: [
		    {
				label: '确定',
				cssClass: 'btn-primary',
				action: function(dialog) {
					var refuseRemark = $("#refuseRemark").val();
					doRefuseAjax(sellerId,refuseRemark,dialog);
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

//ajax 审核拒绝
function doRefuseAjax(sellerId,refuseRemark,dialog){	
	
	YouGou.Ajax.doPost({
   		successMsg: '',
   		url: '/user/sellerAuditRefuse.sc',
   		dataType:"json",
   	  	data: { "id" : sellerId ,"refuseRemark":refuseRemark},
   	  	success : function(data){
   	  		//YouGou.Util.inspect(data);
	    	  	if(data.data.resultCount >0 ){
   	  			YouGou.UI.Dialog.alert({message:"拒绝成功"});
   	  			dialog.close();
   	  			mmGrid.load();
   	  		}else{
   	  			YouGou.UI.Dialog.alert({message:"拒绝失败"});
   	  		}
     			
   		}
   	});
}

function doQuery(){
	mmGrid.load();
}