// 收起菜单提升交互体验
YouGou.UI.menuContract();

var operationFixed = function(val,item,rowIndex){
	var operationBtn = [];
	operationBtn.push('<div class="btn-group">');
	operationBtn.push('<button data-toggle="dropdown" class="btn btn-sm btn-yougou dropdown-toggle" aria-expanded="false">');
	operationBtn.push('选项');
	operationBtn.push('<i class="ace-icon fa fa-angle-down icon-on-right"></i>');
	operationBtn.push('</button>');
	operationBtn.push('<ul class="dropdown-menu dropdown-danger">');
	if(item.isReply!=null && item.isReply == 1){
    	operationBtn.push('<li><a href="javascript:void(0);" onclick="toReply(\''+item.id+'\');">追加回复</a></li>');
    	if(item.isShow!=null && item.isShow == 1){
        	operationBtn.push('<li><a href="javascript:void(0);" onclick="toHiden(\''+item.id+'\');">隐藏</a></li>');
    	}else{
        	operationBtn.push('<li><a href="javascript:void(0);" onclick="toShow(\''+item.id+'\');">显示</a></li>');
    	}
	}else{
    	operationBtn.push('<li><a href="javascript:void(0);" onclick="toReply(\''+item.id+'\');">回复</a></li>');
    	if(item.isShow!=null && item.isShow == 1){
    		operationBtn.push('<li><a href="javascript:void(0);" onclick="toIngron(\''+item.id+'\');">忽略</a></li>');
    	}else{
        	operationBtn.push('<li><a href="javascript:void(0);" onclick="toShow(\''+item.id+'\');">显示</a></li>');
    	}
    	operationBtn.push('<li><a href="javascript:void(0);" onclick="toHiden(\''+item.id+'\');">隐藏</a></li>');
	}
	if(item.isFilter != null && item.isFilter != 0){
		operationBtn.push('<li><a href="javascript:void(0);" onclick="toPingbi(\''+item.id+'\');">屏蔽</a></li>');
	}
	
	operationBtn.push('<li><a href="javascript:void(0);" onclick="toDetail(\''+item.id+'\');">查看评论</a></li>');
	operationBtn.push('<li><a href="javascript:void(0);" onclick="toShowLog(\''+item.id+'\');">查看日志</a></li>');
	operationBtn.push('</ul>');
	operationBtn.push('</div>');
	return operationBtn.join('');
};

var shineFixed = function(val,item,rowIndex){
	var imgUrl = "";
	if(item.shineFlag == 1){
		if(!YouGou.Util.isEmpty(item.commentShines)){
			var html = [];
			for(var i=0;i<item.commentShines.length;i++){
				var smallPicUrl = picDomain + item.commentShines[i].smallPicUrl;// 缩略图
				var middlePicUrl = picDomain + item.commentShines[i].middlePicUrl;// 展开图
				html.push('<a data-fancybox-group="thumb'+item.id+'" class="fancybox-thumbs '+ (i!=0?'hide':'') +'" href="'+middlePicUrl+'" title="第'+(i+1)+'张"> <img width="50" height="50" src="' + smallPicUrl +'"/></a>');
			}
			return html.join('');
		}else{
			return "缺失图片";
		}
	}
	return "无";
 };
 
 var stateFixed = function(val,item,rowIndex){
	var html = [];
  	if(item.isShow!=null && item.isShow == 1){
  		html.push('已显示');
  	}else{
  		html.push('已隐藏');
  	}
  	html.push("<br>");
  	if(item.isReply!=null && item.isReply == 1){
  		html.push('已回复');
  	}else{
  		html.push('未回复');
  	}
  	return html.join('');
};
var commodityNameFixed = function(val,item,rowIndex){
	var html = [];
	html.push(item.commodityName);
	html.push('<br>');
	if(item.isReply==1){
		var href = "javascript:querycommodity('','"+item.commodityName+"')";
		html.push('<a href="'+href+'">查看该商品所有评论</a>');
	}else{
		var href = "javascript:querycommodity('0','"+item.commodityName+"')";
		html.push('<a href="'+href+'">查看该商品所有未回复评论</a>');
	}
	return html.join('');
};
var memberLoginNameFixed = function(val,item,rowIndex){
	var html = [];
	html.push(item.tblMemberLoginaccount.loginName);
	html.push('<br>');
	if(item.isReply==1){
		var href = "javascript:querymember('','"+item.tblMemberLoginaccount.loginName+"')";
		html.push('<a href="'+href+'">查看该会员所有评论</a>');
	}else{
		var href = "javascript:querymember('0','"+item.tblMemberLoginaccount.loginName+"')";
		html.push('<a href="'+href+'">查看该会员所有未回复评论</a>');
	}
	return html.join('');
};

var cols1 = [
	{ title:'<input type="checkbox" name="checkboxall" id="checkboxall" class="checkall">', name:'id',width:30,lockWidth:true, align:'left',renderer: function(val,item,rowIndex){
		return '<input type="checkbox" recvalue="'+item.isReply+'" statusvalue="'+item.isShow+'" blackid="'+item.id+'" value="'+item.id+'" name="checkboxid" id="checkboxid">'
	}},
	{ title:'评论时间', name:'commentTime',width:125,lockWidth:true, align:'center'},
    { title:'状态', name:'',width:50, lockWidth:true,align:'center',renderer:stateFixed},
    { title:'商品名称', name:'commodityName' ,width:200, lockWidth:true,align:'left',position:'top',renderer: commodityNameFixed},
	{ title:'会员账号', name:'tblMemberLoginaccount.loginName' ,width:170,lockWidth:true, align:'left',renderer: memberLoginNameFixed},
	{ title:'优购订单号', name:'orderNumber' ,width:120,lockWidth:true,align:'center'},
	{ title:'是否晒单', name:'shineFlag' ,width:70, lockWidth:true,align:'center',renderer: shineFixed},
    { title:'点评内容', name:'myComment' , align:'left',position:'top'},
    { title:'操作', name:'oprator' ,width:70, lockWidth:true,align:'center',renderer: operationFixed}
];

var cols2 = [
 	{ title:'<input type="checkbox" name="checkboxall" id="checkboxall" class="checkall">', name:'id',width:30,lockWidth:true, align:'left',renderer: function(val,item,rowIndex){
 		return '<input type="checkbox" recvalue="'+item.isReply+'" statusvalue="'+item.isShow+'" blackid="'+item.id+'" value="'+item.id+'" name="checkboxid" id="checkboxid">'
 	}},
 	{ title:'评论时间', name:'commentTime',width:125,lockWidth:true, align:'center'},
 	{ title:'更新时间', name:'',width:80,lockWidth:true, hidden : true,align:'center',renderer:function(val,item,rowIndex){
 		var commodityReplies = item.tblCommodityReplies;
 		if(commodityReplies!=null && commodityReplies.length>0){
 			return commodityReplies[0].commodityDate;
 		}
 	}},
    { title:'状态', name:'',width:60, lockWidth:true,align:'center',renderer:stateFixed},
     { title:'商品名称', name:'commodityName' ,width:200, lockWidth:true,align:'left',position:'top',renderer: commodityNameFixed},
 	{ title:'会员账号', name:'tblMemberLoginaccount.loginName' ,width:100,lockWidth:true, align:'left',renderer: memberLoginNameFixed},
    
 	{ title:'优购订单号', name:'orderNumber' ,width:130,lockWidth:true,align:'center'},
 	{ title:'是否晒单', name:'shineFlag' ,width:70, lockWidth:true,align:'center',renderer: shineFixed},
     { title:'点评内容', name:'myComment' , align:'left'},
     { title:'点评发布', name:'' , width:70, lockWidth:true,align:'center',renderer: function(val,item,rowIndex){
    	 if(item.numberId!=null && item.numberId=='watering'){
    		 return '后台导入';
    	 }else{
    		 return '前台用户';	
    	 }
     }},
     { title:'隐藏时间', name:'' , width:80, lockWidth:true,align:'center',renderer: function(val,item,rowIndex){
    	 if(item.isShowTime!=null){
    		 return new Date(item.isShowTime).format('yyyy-MM-dd')+'<br>'+item.userName;
    	 }
    	 return "";
     }},
     { title:'操作', name:'oprator' ,width:70, lockWidth:true,align:'center',renderer: operationFixed}
 ];

var cols;
if($('#status').val()=='1'){
	cols = cols1;
}else{
	cols = cols2;
}

//分页器
var mmPaginator = $('#grid-pager').mmPaginator({});

// 搜索表单属性
var mmFormParams = new MMSearchFormParams("searchForm");

// 表格	
var mmGrid = $('#grid-table').mmGrid({
	height: 600,
	cols: cols,
	url: '/comment/queryCommentData.sc',
	fullWidthRows: true,
	multiSelect: true,
	autoLoad: false,
	plugins: [
	    mmFormParams , mmPaginator
	]
});

J('#startTime').calendar({maxDate:'#endTime',format:'yyyy-MM-dd HH:mm:ss'}); 
J('#endTime').calendar({minDate:'#startTime',format:'yyyy-MM-dd HH:mm:ss'});
J('#startUpdateTime').calendar({maxDate:'#endUpdateTime',format:'yyyy-MM-dd HH:mm:ss'}); 
J('#endUpdateTime').calendar({minDate:'#startUpdateTime',format:'yyyy-MM-dd HH:mm:ss'});

$("input[name$='checkboxall']").bind('click',function(){
	if (this.checked) {
		$("input[name$='checkboxid']").each(function () { this.checked = true; });
	} else {
		$("input[name$='checkboxid']").each(function () { this.checked = false; });		
	}
});

mmGrid.on('cellSelected', function(e, item, rowIndex, colIndex){
    if($(e.target).is('#checkboxid')){
    	var tl_num = $("input[name$='checkboxid']").length;
    	var ck_num = $("input[name$='checkboxid']:checked").length;
    	if(tl_num != ck_num){
    		$("input[name$='checkboxall']").each(function () { this.checked = false; });
    	}else{
    		$("input[name$='checkboxall']").each(function () { this.checked = true; });
    	}
    }
}).on('loadSuccess',function(e, item, rowIndex, colIndex){
});

/**
 * 品牌名称搜索
 */
$("#brandName").autocomplete({
	source: function( request, response ){
		$.ajax({
			type: "post",
		    url: "/comment/getBrandListByName.sc",
		    data: "brandName=" + $('#brandName').val(),
		    dataType:"json",
		    success : function(data) {
				response(data);
			},
			error: function() {
				response([]);
			}
			
		});
	},
	autoFocus: false,
	delay: 0,
	minLength: 1,
	open : function(event, ui) {
		$('.ui-autocomplete').css({
		  "overflow":"auto",
		  "height":"150px"
		}).find('li').css({
	    'border-top': '1px solid #ddd',
	    'display': 'block',
	    'line-height': '18px',
	    'padding': '5px 5px 5px 28px',
	    'position': 'relative'});  
		return false;
    },
	select: function( event, ui ) {
		$('#brandNo').val(ui.item.brandNo);
		$('#brandName').val(ui.item.brandName);
		return false;
	}
 }).autocomplete( "instance" )._renderItem = function( ul, item ) {
	return $( "<li>" )
	.append(  item.brandName  )
	.appendTo( ul );
};

//* 查看
function toDetail(id) {
	YouGou.UI.progressLoading();
	$.ajax({
       type: "post",
       url: "/comment/toDetail.sc",
       data: "id="+id+"&op=show",
       async: false,
       success : function(data) {
    	   YouGou.UI.progressStop();
    	   if (data) {
    		   YouGou.UI.Dialog.show({
    			   title: "点评详情",
    			   cssClass: "comment_detail-dialog",
    			   message : data.replace(/(\n)+|(\r\n)+/g, ""),
    			   buttons: [{
    				   label: '关闭',
    				   action: function(dialog) {
    					   dialog.close();
    				   }
    			   }]
    		   });
    	   }
	  }
   }); 
}

// 回复
function toReply(id) {
	YouGou.UI.progressLoading();
	$.ajax({
	       type: "post",
	       url: "/comment/toDetail.sc",
	       data: "id="+id+"&op=reply",
	       async: false,
	       success : function(data) {
	    	YouGou.UI.progressStop();
			if (data) {
				YouGou.UI.Dialog.show({
    			   title: "点评回复",
    			   cssClass: "comment_detail-dialog",
    			   message : data.replace(/(\n)+|(\r\n)+/g, ""),
    			   buttons: [{
    				   label: '关闭',
    				   action: function(dialog) {
    					   dialog.close();
    				   }
    			   },{
    				   label: '回复',
    				   cssClass: 'btn-yougou',
    				   action: function(dialog) {
    					   submitForm(dialog);
    				   }
    			   }],
    			   onshown : function(){
    				   bindAutoCommentTips();
    					$("input[name$='isScor']").each(function () { 
    						$(this).click(function () {
    						if (this.checked && this.value == 1) {
    							$("#radiobtn").show();
    						}else{
    							$("#radiobtn").hide();
    						}
    						});
    					});
    			   }
    		   });
			  }
			}
	   }); 
}

function bindAutoCommentTips(){
	$('#queryQuickReply').autocomplete({
		source:function( request, response ){
			$.ajax({
				type: "post",
				url: "/quickreply/selectQuickreplyback.sc",
				data:{"quickReplyNo":request.term},
				dataType: "json",
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				success : function(data) {
					response(data);
				},
				error: function() {
					response([]);
				}
			});
		},
		autoFocus: true,
		delay: 0,
		minLength: 1,
		open : function(event, ui) {
			$('.ui-autocomplete').css({
			  "z-index":"1150",
			  "overflow":"auto",
			  "width":"460px",
			  "height":"150px"
			}).find('li').css({
		    'border-top': '1px solid #ddd',
		    'display': 'block',
		    'line-height': '18px',
		    'padding': '5px 5px 5px 28px',
		    'position': 'relative'});  
			return false;
        }, 
		select: function( event, ui ) {
			$('.commodityContent').val(ui.item.value);
			return false;
		}
	});
}

function queryQuickreplylist(){
  	mmGrid_qick.load();
}

//表格	
var mmGrid_qick = null;

//使用快捷语
function useQuickReplyback(){
	YouGou.UI.progressLoading();
	$('#dialog-confirm').after('<div id="dialog-confirm-qick" class="hide"></div>')
	$.ajax({
	       type: "post",
	       url: "/quickreply/toQuickreplyBack.sc",
	       async: false,
	       success : function(data) {
	    	YouGou.UI.progressStop();
			if (data) {
				YouGou.UI.Dialog.show({
	    			   title: "快捷语",
	    			   cssClass: "comment_detail-dialog",
	    			   message : data.replace(/(\n)+|(\r\n)+/g, ""),
	    			   buttons: [{
	    				   label: '关闭',
	    				   action: function(dialog) {
	    					   dialog.close();
	    				   }
	    			   },{
	    				   label: '确定',
	    				   cssClass: 'btn-yougou',
	    				   action: function(dialog) {
	    					   if(mmGrid_qick.selectedRowsIndex() == null || mmGrid_qick.selectedRowsIndex() == ''){
	    						   YouGou.UI.Dialog.alert({message:'请选择快捷语'});
								}else{
									$(".queryQuickReply").val(mmGrid_qick.row(mmGrid_qick.selectedRowsIndex()).quickReplyNo);
									$('.commodityContent').val(mmGrid_qick.row(mmGrid_qick.selectedRowsIndex()).quickReplyContent);
									dialog.close();
								}
	    				   }
	    			   }],
	    			   onshown : function(){
	    				   var cols = [
				           	{ title:'快捷编码', name:'quickReplyNo',lockWidth:true,width:100, align:'center'},
				           	{ title:'快捷回复语', name:'quickReplyContent', width:360, align:'left'}
				           ];

				           //分页器
				           var mmPaginator_qick = $('#grid-pager-qick').mmPaginator({});

				           // 搜索表单属性
				           var mmFormParams_qick = new MMSearchFormParams("queryform");

				           // 表格	
				           mmGrid_qick = $('#grid-table-qick').mmGrid({
				           	height: 425,
				           	cols: cols,
				           	url: '/quickreply/queryCommodityCommentQuickreplyBack.sc',
				           	indexCol: false,
				           	indexColWidth: 45,
				           	fullWidthRows: true,
				           	autoLoad: true,
				           	checkCol: true,
				           	checkColWidth: 45,
				           	noDataText: '没有数据',
				           	plugins: [
				           	   mmFormParams_qick , mmPaginator_qick
				           	]
				           });
	    			   }
	    		   });
			}
		  }
	   }); 
}

function submitForm(dialog){
	var commodityContent=$("#commodityContent").val();
	if(commodityContent==null||commodityContent==""){
		YouGou.UI.Dialog.alert({message:'商品点评回复内容不能为空'});
		return false;
	}
	if(commodityContent.length>=500){
		YouGou.UI.Dialog.alert({message:'商品点评回复内容文本输入过长！'});
		return false;
	}
	var id=$("#id").val();
	var loginaccountId=$("#loginaccountId").val();
	var loginName=$("#loginName").val();
	var isIntegral="0";//是否送积分
	var integral="0";//积分
	var stickSort="0";//是否置顶
	var commodityName =$("#commodityName").val();
	var isScor=document.getElementsByName("isScor");
	var scor=document.getElementsByName("scor");
	var comment=document.getElementsByName("comment");
	for(var i=0;i <isScor.length;i++){
		if(isScor[i].checked){
			isIntegral=isScor[i].value;
		}
	}
	for(var i=0;i <scor.length;i++){
		if(scor[i].checked){
			integral=scor[i].value;
		}
	}
	for(var i=0;i<comment.length;i++){
		if(comment[i].checked){
			stickSort=comment[i].value;
		}
	}
	$(".btn-save").attr("disabled",true);
	$.ajax({
		type: "post",
		url: "/comment/saveCommodityReply.sc",
		data:{"id":id,"commodityContent":commodityContent,"loginaccountId":loginaccountId,"email":loginName,"stickSort":stickSort,"integral":integral,"isIntegral":isIntegral,"commodityName":commodityName},
		async : false,
		success : function(msg) {
			if ("1" == msg) {
				$(".commodityContent").val("");
				$(".queryQuickReply").val("");
				YouGou.UI.tipSuccess('回复用户评论成功!');
		        dialog.close();
		        mmGrid.load();
			}else{
				$(".btn-save").attr("disabled",false);
				YouGou.UI.tipError('回复用户评论失败!');
				dialog.close();
				mmGrid.load();
			}
		}
	}); 
}

/**
 * 跳转到商品单品页
 * @param commodityNo
 */
function gotoSku(commodityId){
	$.ajax({
	       type: "post",
	       url: "/comment/gotoSku.sc",
	       async:false,
	       data: "commodityId="+commodityId,
	       success : function(url) {
	    	   window.open(url); 
		  }
    }); 
}

  // 修改为显示
  function toShow(id){
    YouGou.UI.Dialog.confirm({
  		message : "确定要显示该条商品点评内容记录吗?"
  	},function(result){
  		if(result) {
  			YouGou.Ajax.doPost({
      		  successMsg: "显示商品点评内容成功!",
      		  url: "/comment/updateShow.sc",
      		  data: {"id":id},
      		  success : function() {
  				   mmGrid.load();
      		  }
  			});
          }
  	});
  }
  
  // 修改为隐藏
  function toHiden(id){
      YouGou.UI.Dialog.confirm({
    		message : "确定要隐藏该条商品点评内容记录吗?"
      },function(result){
    		if(result) {
    			YouGou.Ajax.doPost({
    	    		  successMsg: "隐藏商品点评内容成功!",
    	    		  url: "/comment/updateHide.sc",
    	    		  data: {"id":id},
    	    		  success : function() {
    					   mmGrid.load();
    	    		  }
    	       });
            }
      });
  }
  
  // 修改为忽略
   function toIngron(id){
	 YouGou.UI.Dialog.confirm({
   		message : "确定要忽略该条商品点评内容记录吗?"
     },function(result){
   		if(result) {
   			YouGou.Ajax.doPost({
   	    		  successMsg: "忽略商品点评内容成功!",
   	    		  url: "/comment/updateIgnored.sc",
   	    		  data: {"id":id},
   	    		  success : function() {
   					   mmGrid.load();
   	    		  }
   	       });
           }
     });
  }
  
   /**
    * 修改为屏蔽
    * @param id
    */
  function toPingbi(id){
	  YouGou.UI.Dialog.confirm({
   		message : "确定要屏蔽该条商品点评内容记录吗?"
     },function(result){
   		if(result) {
   			YouGou.Ajax.doPost({
   	    		  successMsg: "屏蔽商品点评内容成功!",
   	    		  url: "/comment/updatePingbi.sc",
   	    		  data: {"id":id},
   	    		  success : function() {
   					   mmGrid.load();
   	    		  }
   	       });
           }
     });
  }
  
// 查询日志
function toShowLog(id){
	YouGou.UI.progressLoading();
	$.ajax({
	       type: "post",
	       url: "/comment/toCommentLog.sc",
	       data: "commentId="+id,
	       async: false,
	       success : function(data) {
	    	YouGou.UI.progressStop();
			if (data) {
				YouGou.UI.Dialog.show({
	    			   title: "日志查看",
	    			   cssClass: "comment_detail-dialog",
	    			   message : data.replace(/(\n)+|(\r\n)+/g, ""),
	    			   buttons: [{
	    				   label: '关闭',
	    				   action: function(dialog) {
	    					   dialog.close();
	    				   }
	    			   }],
	    			   onshown : function(){
	    				   var cols = [
	    				           	{ title:'操作时间', name:'createTime',width:125, align:'center',renderer:YouGou.Util.timeFixed},
	    				           	{ title:'操作类型', name:'logType',width:100, align:'center'},
	    				           	{ title:'备注', name:'remark',width:450, align:'left'},
	    				           	{ title:'操作人', name:'userName', width:80, align:'left'}
	    				           ];
	    				           //分页器
	    				           var mmPaginator_logs = $('#grid-pager-logs').mmPaginator({});
	    				           // 表格	
	    				           var mmGrid_logs = $('#grid-table-logs').mmGrid({
	    				           	height: 'auto',
	    				           	cols: cols,
	    				           	url: '/comment/queryCommodityCommentLog.sc?commentId='+$('#commentId').val(),
	    				           	indexCol: false,
	    				           	indexColWidth: 45,
	    				           	fullWidthRows: true,
	    				           	autoLoad: true,
	    				           	noDataText: '没有数据',
	    				           	plugins: [
	    				           	    mmPaginator_logs
	    				           	]
				           });
	    			   }
	    		   });
			}
		  }
	   });
}
 
function querycommodity(style,commodityName){
	$("#shopName").val(commodityName);
 	$("#isReply").val(style);
 	querylist();
}

function querymember(style,memberName){
	$("#replyName").val(memberName);
 	$("#isReply").val(style);
 	querylist();
}

//搜索
function querylist(){
   mmGrid.load();
}
 
/*
 * 批量显示或隐藏
*/
$('#batchShow,#batchHide').bind('click',function(){
	var type = $(this).attr('tag');
	var detail = "";
	var infomsg = "";
	$('input:checkbox[name="checkboxid"]:checked').each(function() {
		var blackId = $(this).attr("blackId");
		var status = $(this).attr("statusValue");
		detail += ",{id:'"+blackId+"',isShow:"+status+"}";
	});
	detail = detail.substring(1);
	if(detail == ""){
		YouGou.UI.Dialog.alert({message:'请先选择评论，在执行批量操作!'});
		return;
	}	
	detail = "["+detail+"]";	
	if(type == "1"){
		infomsg = "确定执行批量显示吗?";
	}else if(type == "0"){
		infomsg = "确定执行批量隐藏吗?";
	}
	YouGou.UI.Dialog.confirm({
   		message : infomsg
    },function(result){
    	 if(result) {
    		 YouGou.Ajax.doPost({
    			 url: "/comment/batchShowOrHide.sc",
    			 data: {"detail":detail,"type":type},
  	  		  	 success : function() {
  	  		  		 mmGrid.load();
  		  	 }});
    	 }
     });
});

 //批量回复
 $('#batchReply').bind('click',function(){
 	var detail="";
	$('input:checkbox[name="checkboxid"]:checked').each(function() {
		var blackId=$(this).attr("blackId");
		var status=$(this).attr("statusValue");
		detail+=",{id:'"+blackId+"',isShow:"+status+"}";
	});
	detail=detail.substring(1);
	if(detail==""){
		YouGou.UI.Dialog.alert({message:'请先选择评论，在执行批量操作!'});
		return;
	}	
	detail="["+detail+"]";	
	
  	YouGou.UI.progressLoading();
	$.ajax({
	       type: "post",
	       url: "/comment/pacthCommodityCommentQuickreply.sc",
	       data: "commentIds="+detail,
	       async: false,
	       success : function(data) {
	    	YouGou.UI.progressStop();
			if (data) {
				YouGou.UI.Dialog.show({
	    			   title: "点评批量回复",
	    			   cssClass: "comment_detail-dialog",
	    			   message : data.replace(/(\n)+|(\r\n)+/g, ""),
	    			   buttons: [{
	    				   label: '关闭',
	    				   action: function(dialog) {
	    					   dialog.close();
	    				   }
	    			   },{
	    				   label: '批量回复',
	    				   cssClass: 'btn-yougou',
	    				   action: function(dialog) {
	    					   batchReply();
	    					   mmGrid.load();
	    					   dialog.close();
	    				   }
	    			   }],
	    			   onshown : function(){
	    				   bindAutoCommentTips();
	    			   }
	    		   });
			}
		  }
	   });
 });
 
 function batchReply(){
	var commodityContent=$(".commodityContent").val();
	if(commodityContent==null||commodityContent==""){
		YouGou.UI.Dialog.alert({message:'商品点评回复内容不能为空'});
		return false;
	}
	if(commodityContent.length>=500){
		YouGou.UI.Dialog.alert({message:'商品点评回复内容文本输入过长'});
		return false;
	}
  	YouGou.Ajax.doPost({
  		  successMsg: "批量回复商品点评内容成功!",
  		  url: "/comment/batchReply.sc",
  		  data: {"arr":$('.commentIds').val(),"commodityContent":commodityContent},
  		  success : function() {
				mmGrid.load();
  		  }
    });
 }
 
 //批量忽略
 $('#batchIngore').bind('click',function(){
 	var detail="";
	$('input:checkbox[name="checkboxid"]:checked').each(function() {
		var blackId=$(this).attr("blackId");
		var status=$(this).attr("statusValue");
		detail+=",{id:'"+blackId+"',isShow:"+status+"}";
	});
	detail=detail.substring(1);
	if(detail==""){
		YouGou.UI.Dialog.alert({message:'请先选择评论，在执行批量操作!'});
		return;
	}	
	detail="["+detail+"]";
	var infomsg="确定执行批量忽略所选商品点评内容记录吗?"	
	YouGou.UI.Dialog.confirm({
   		message : infomsg
     },function(result){
   		if(result) {
   			YouGou.Ajax.doPost({
  	  		  successMsg: "批量忽略点评内容成功!",
  	  		  url: "/comment/batchIngrol.sc",
  	  		  data: {"detail":detail},
  	  		  success : function() {
  					mmGrid.load();
  	  		  }
   			});
       }
     });
 });  
 
 /**
  * 批量屏蔽
  */
 $('#batchShield').bind('click',function(){
 	var detail="";
	var infomsg="";
	$('input:checkbox[name="checkboxid"]:checked').each(function() {
		var blackId=$(this).attr("blackId");
		detail+=",{id:'"+blackId+"'}";
	});
	detail=detail.substring(1);
	if(detail==""){
		YouGou.UI.Dialog.alert({message:'请先选择评论，在执行批量操作!'});
		return;
	}	
	infomsg="确定执行批量屏蔽吗?"
	detail="["+detail+"]";	
	YouGou.UI.Dialog.confirm({
   		message : infomsg
     },function(result){
   		if(result) {
   			YouGou.Ajax.doPost({
  	  		  successMsg: "批量屏蔽点评内容成功!",
  	  		  url: "/comment/batchShield.sc",
  	  		  data: {"detail":detail},
  	  		  success : function() {
  					mmGrid.load();
  	  		  }
   			});
       }
     });
 });
 
 /**
  * 导出点评
  */
 $('#exportBtn').bind('click',function(){
	YouGou.UI.loading();
	$('.exportFrm').attr("src", "/comment/exportcomment.sc?"+$('#searchForm').formSerialize());
	YouGou.UI.stopLoading();
 });
 
if($("#status").val() == 1){
	mmGrid.load();
	mmGrid.on('loadSuccess',function(e, item, rowIndex, colIndex){
		var totalCount = mmPaginator.params().totalCount;
		$("#unprocessed").html(totalCount).removeClass("hide");
		$("#unprocessed_loadding").hide();
	});
}