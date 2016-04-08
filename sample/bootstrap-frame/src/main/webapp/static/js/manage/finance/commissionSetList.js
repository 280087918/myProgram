/*************
	CommissionPercent
**************/
// 操作列动作
var actionFixed = function(val,item,rowIndex){
	var html = [];
	html.push('<a href="javascript:void(0);" action="edit">编辑</a>&nbsp;&nbsp;');
	html.push('<a href="javascript:void(0);" action="delete">删除</a>&nbsp;&nbsp;');
    return html.join('');
};

// 列集合
var cols = [
	{ title:'分类名称', name:'baseCatNo', align:'center'},
	{ title:'一级佣金比例', name:'commissionLevel1Percent', align:'center'},
	{ title:'二级佣金比例', name:'commissionLevel2Percent', align:'center'},
	{ title:'最后更新用户', name:'updateUser', align:'center'},
	{ title:'创建时间', name:'createTime',width:125,  align:'center', renderer: YouGou.Util.timeFixed},
	{ title:'最后更新时间', name:'updateTime',width:125,  align:'center', renderer: YouGou.Util.timeFixed},
	{ title:'操作', name:'' ,width:200, align:'center',lockWidth:true, renderer: actionFixed}
];

//分页器
var mmPaginator = $('#grid-pager').mmPaginator({});
// 搜索表单属性
var mmFormParams = new MMSearchFormParams("categoryCommissionForm");

// 表格	
var mmGrid = $('#grid-table').mmGrid({
	height: 'auto',
	cols: cols,
	url: '/finance/queryCommissionSetData.sc',
	fullWidthRows: true,
	autoLoad: true,
	plugins: [mmPaginator,mmFormParams]
});


// 表格事件
mmGrid.on('cellSelected', function(e, item, rowIndex, colIndex){
	var action = $(e.target).attr("action");
    //编辑
    if(action == "edit"){
    	updateCategoryCommission(item.id);
    }else if(action == "delete"){// 删除
   		YouGou.UI.Dialog.confirm({
   			message : "确定要删除吗？"
   		},function(result){
   			if(result) {
                removeCategoryCommission(item.id);
            }
   		});
    }
    e.stopPropagation();  //阻止事件冒泡
});

function removeCategoryCommission(id){
	YouGou.Ajax.doPost({
		successMsg: '删除成功！',
		url: '/finance/removeCategoryCommission.sc',
	  	data: { "id" : id },
	  	success : function(data){
  			mmGrid.load();
		}
	});
}

function updateCategoryCommission(id) {
 	YouGou.UI.progressLoading();
 	$.ajax({
 	       type: "post",
 	       url: '/finance/updateCategoryCommission.sc',
 	       data: {"id":id},
 	       async: false,
 	       success : function(data) {
 	    	   YouGou.UI.progressStop();
 	    	   if (data) {
 	    		  YouGou.UI.Dialog.show({
	 					title: "分类佣金比例编辑",
	 					message : data.replace(/(\n)+|(\r\n)+/g, ""),
	 					buttons: [{
			 		                label: '取消',
			 		                action: function(dialog) {
			 		                    dialog.close();
			 		                }
			 		            }, 
			 		            {
			 		                label: '提交',
			 		                cssClass: 'btn-yougou',
			 		                action: function(dialog) {
			 		                	saveCategoryCommission(dialog);
			 		                }
		 		            }]
	 				});
 	    	   }
 		  }
   }); 
}

$('#add-quick').click(function(){
	 YouGou.UI.progressLoading();
	 	$.ajax({
	 	       type: "post",
	 	       url: '/finance/commissionSetAdd.sc',
	 	       async: false,
	 	       success : function(data) {
	 	    	YouGou.UI.progressStop();
	 			if (data) {
	 				YouGou.UI.Dialog.show({
	     			   title: "分类佣金比例添加",
	     			   cssClass: 'detail-dialog',
	     			   message : data.replace(/(\n)+|(\r\n)+/g, ""),
	     			   buttons: [{
	     				   label: '关闭',
	     				   action: function(dialog) {
	     					   dialog.close();
	     				   }
	     			   },{
	     				   label: '提交',
	     				   cssClass: 'btn-yougou',
	     				   action: function(dialog) {
	     					  saveCategoryCommission(dialog);
	     				   }
	     			   }]
	     		   });
	 			}
	 		  }
	 	   }); 
});

function saveCategoryCommission(dialog){	
	var baseCatId = $("#settlementType1").val();
	if(YouGou.Util.isEmpty(baseCatId)){
		$("#settlementType1_stip").html("商品分类必须选择！");
		content.focus();
		return false;
	}
	var commissionLevel1Percent = $("#commissionLevel1Percent").val();
	if(YouGou.Util.isEmpty(commissionLevel1Percent)){
		$("#commissionLevel1Percent_stip").html("一级佣金比例不能为空！");
		content.focus();
		return false;
	}
	var commissionLevel2Percent = $("#commissionLevel2Percent").val();
	if(YouGou.Util.isEmpty(commissionLevel2Percent)){
		$("#commissionLevel2Percent_stip").html("二级级佣金比例不能为空！");
		content.focus();
		return false;
	}
	var id = $("#id").val();
	YouGou.Ajax.doPost({
		successMsg: "操作成功",
		url: '/finance/saveCategoryCommission.sc',
	    data: {"baseCatId":baseCatId,"id":id,"commissionLevel1Percent":commissionLevel1Percent,"commissionLevel2Percent":commissionLevel2Percent},
	  	success : function() {
			mmGrid.load();
	  		dialog.close();
  		}
	});
}

function defaultCommissionSetBtn(){
	var defaultCommission1=$("#defaultCommission1").val();//没有设置过的佣金比例
	var defaultCommission2=$("#defaultCommission2").val();
	 $("#setBtn").empty();
	 $("#commissionTD1").empty();
	 $("#commissionTD2").empty();
	 $("#commissionTD1").append('<input  name="commissionLevel1Percent" id="commissionLevel1Percent" value="'+defaultCommission1+'" type="text">');
	 $("#commissionTD2").append('<input  name="commissionLevel2Percent" id="commissionLevel2Percent" value="'+defaultCommission2+'" type="text">');
	 $("#setBtn").append('<input type="button" value="保存" class="btn btn-yougou" onclick="defaultCommissionSaveBtn();"/>');
}

function defaultCommissionSaveBtn(){
	var commissionLevel1Percent=$("#commissionLevel1Percent").val();
	var commissionLevel2Percent=$("#commissionLevel2Percent").val();
	YouGou.Ajax.doPost({
		successMsg: "设置成功！",
		url: '/finance/commissionSaveInfo.sc',
	  	data: { "commissionLevel1Percent" : commissionLevel1Percent,"commissionLevel2Percent":commissionLevel2Percent },
	  	success : function(){
	  		 $("#setBtn").empty();
	  		 $("#commissionTD1").empty();
	  		 $("#commissionTD2").empty();
	  		 $("#commissionTD1").append('<font>'+commissionLevel1Percent+'</font>');
	  		 $("#commissionTD2").append('<font>'+commissionLevel2Percent+'</font>');
	  		 $("#setBtn").append('<input type="button" value="设置" class="btn btn-yougou" onclick="defaultCommissionSetBtn();"/>');
	  		dialog.close();
		}
	});
}

function doQuery(){
	mmGrid.load();
}