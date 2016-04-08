var cols = [
    { title:'快捷编码', name:'quickReplyNo',width:100,lockWidth:true, align:'center'},
    { title:'快捷回复语', name:'quickReplyContent',align:'left'},
	{ title:'添加时间', name:'createTime' , width:125, lockWidth:true,align:'center',renderer : YouGou.Util.timeFixed},
	{ title:'添加人', name:'userName', width:80, lockWidth:true,align:'center'},
    { title:'操作', name:'oprator' ,width:90, lockWidth:true,align:'center',renderer: function(val,item,rowIndex){
        return	'<a href="javascript:void(0);" action="edit">修改</a>&nbsp;&nbsp;<a href="javascript:void(0);" action="delete">删除</a>'
    }}
];

//分页器
var mmPaginator = $('#grid-pager').mmPaginator({});

// 搜索表单属性
var mmFormParams = new MMSearchFormParams("queryform");

// 表格	
var mmGrid = $('#grid-table').mmGrid({
	height: 'auto',
	cols: cols,
	url: '/quickreply/queryCommodityCommentQuickreply.sc',
	indexCol: true,
	indexColWidth: 35,
	fullWidthRows: true,
	autoLoad: true,
	noDataText: '没有数据',
	plugins: [
	    mmFormParams , mmPaginator
	]
});
        
J('#startTime').calendar({maxDate:'#endTime',format:'yyyy-MM-dd HH:mm:ss'}); 
J('#endTime').calendar({minDate:'#startTime',format:'yyyy-MM-dd HH:mm:ss'});

mmGrid.on('cellSelected', function(e, item, rowIndex, colIndex){
	var action = $(e.target).attr("action");
	//修改
	if(action == 'edit'){
		update(item.id);
	//删除
	}else if(action == 'delete'){
		del(item.id);
	} 
	e.stopPropagation();
});


function querylist(){
    mmGrid.load();
}
 
 $('#add-quick').click(function(){
	 YouGou.UI.progressLoading();
	 	$.ajax({
 	       type: "post",
 	       url: "/quickreply/addTo.sc",
 	       async: false,
 	       success : function(data) {
 	    	    YouGou.UI.progressStop();
 				if (data) {
	 				YouGou.UI.Dialog.show({
	 					title: "添加快捷回复语",
	 					message : data.replace(/(\n)+|(\r\n)+/g, ""),
	 					buttons: [{
	 		                label: '取消',
	 		                action: function(dialog) {
	 		                    dialog.close();
	 		                }
	 		            }, {
	 		                label: '提交',
	 		                cssClass: 'btn-yougou',
	 		                action: function(dialog) {
	 		                	addQuickReply(dialog);
	 		                }
	 		            }]
	 				});
 				}
	 	    }
 	   }); 
 });
 
function check(){
	$("#quickReplyContent_stip").html("");
 	$("#quickReplyNo_stip").html("");
 	var content = $("#quickReplyContent_1");
	if(YouGou.Util.isEmpty(content.val())){
		$("#quickReplyContent_stip").html("快捷回复内容不能为空！");
		content.focus();
		return false;
	}
	var no = $("#quickReplyNo_1");
	if(YouGou.Util.isEmpty(no.val())){
		$("#quickReplyNo_stip").html("快捷编号不能为空！");
		no.focus();
		return false;
	}
	return true;
}
 
function addQuickReply(dialog){
	if(!check()){
		return;
	}
	YouGou.Ajax.doPost({
		  errorMsg: "快捷编号已存在",
		  tips:false,
  		  url: "/quickreply/selectQuickreply.sc",
  		  data: {"quickReplyNo" : $("#quickReplyNo_1").val()},
  		  success : function() {
	  			YouGou.Ajax.doPost({
	  				successMsg: "添加快捷语成功",
	  				tips:false,
	  		  		  url: "/quickreply/c_commodityCommentQuickreply.sc",
	  		  		  data: {"quickReplyNo" : $("#quickReplyNo_1").val(),"quickReplyContent":$("#quickReplyContent_1").val()},
	  		  		  success : function() {
	  		  			  dialog.close();
	  		  			  mmGrid.load();
	  		  		  }
	  		   });
  		  }
   });
 }
 
function update(id) {
 	YouGou.UI.progressLoading();
 	$.ajax({
 	       type: "post",
 	       url: "/quickreply/updateTo.sc",
 	       data: "id="+id,
 	       async: false,
 	       success : function(data) {
 	    	   YouGou.UI.progressStop();
 	    	   if (data) {
 	    		  YouGou.UI.Dialog.show({
	 					title: "修改快捷回复语",
	 					message : data.replace(/(\n)+|(\r\n)+/g, ""),
	 					buttons: [{
	 		                label: '取消',
	 		                action: function(dialog) {
	 		                    dialog.close();
	 		                }
	 		            }, {
	 		                label: '提交',
	 		                cssClass: 'btn-yougou',
	 		                action: function(dialog) {
	 		                	updateQuickReply(dialog);
	 		                }
	 		            }]
	 				});
 	    	   }
 		  }
   }); 
}
 
function updateQuickReply(dialog){
	if(!check()){
		return;
	}
	var quickReplyContent = $("#quickReplyContent_1").val();
	var quickReplyNo = $("#quickReplyNo_1").val();
	var id = $("#quickReplyID").val();
	YouGou.Ajax.doPost({
		successMsg: "修改快捷语成功",
	    url: "/quickreply/u_commodityCommentQuickreply.sc",
	    data: {"quickReplyContent":quickReplyContent,"id":id,"quickReplyNo":quickReplyNo},
	  	success : function() {
	  		mmGrid.load();
	  		dialog.close();
  		}
	});
}
 
function del(id){
	 YouGou.UI.Dialog.confirm({
	   		message : '你确定要删除记录吗'
     },function(result){
	   		if(result) {
	   			YouGou.Ajax.doPost({
	      		  successMsg: "快捷回复语删除成功!",
	      		  url: "/quickreply/d_commodityCommentQuickreply.sc",
	      		  data: {"id":id},
	      		  success : function() {
	  				   mmGrid.load();
	      		  }
	   			});
	       }
     });
 }