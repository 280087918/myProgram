function rendererBuild(colIndex,rowIndex,val){
	var result = !YouGou.Util.isEmpty(val)? val :"错";
	var isError = false;
	if(errorData != null){
		var x = colIndex;
		var y = rowIndex + 1;
		$.each(errorData ,function(key ,value){
			if(value != null && value.x == x && value.y == y && !YouGou.Util.isEmpty(value.errMsg)){
				result = "<span title='文件单元格值：["+ (!YouGou.Util.isEmpty(value.val)?value.val:'空') +"]，错误提示：["+value.errMsg+"]' style='cursor:pointer;' class='errorTip smaller lighter red'>错<span>";
				isError = true;
	    	}
		});
	}
	
	if(colIndex == 3 && !isError){
		result = result.substring(0,result.length - 3);
	}
	return result;
}

var cols = [
    { title:'下单时间', name:'payDatetime',width:80, align:'center',renderer: function(val,item,rowIndex){
    	var payTime = "空";
    	if(item.payDatetime != null){
    		var obj = getDate(item.payDatetime);
        	payTime = obj.format("yyyy-MM-dd")
    	}
    	return rendererBuild(1,rowIndex,payTime);
	}},
    { title:'发布点评时间', name:'commentTime',width:85, align:'center',renderer: function(val,item,rowIndex){
    	var cmtime = "空";
    	if(item.commentTime != null){
    		var obj = getDate(item.commentTime);
        	cmtime = obj.format("yyyy-MM-dd")
    	}
    	return rendererBuild(2,rowIndex,cmtime);
	}},
	{ title:'商品编码', name:'commodityNo' , width:85, align:'center',renderer: function(val,item,rowIndex){
		return rendererBuild(3,rowIndex,item.productNo);
	}},
	{ title:'商品评分', name:'commodityScore' , width:65, align:'center',renderer: function(val,item,rowIndex){
		return rendererBuild(4,rowIndex,item.commodityScore);
	}},
	{ title:'尺码大小', name:'commoditySizeSuitable', width:75, align:'center',renderer: function(val,item,rowIndex){
		var str = "空";
		if (item.commoditySizeSuitable==0){
			str="尺码合适";
		}else if(item.commoditySizeSuitable==1){
			str="尺码偏大";
		}else if(item.commoditySizeSuitable==2){
			str="尺码偏小";
		}
		return rendererBuild(5,rowIndex,str);
	}},
	{ title:'点评内容', name:'myComment', width:580, align:'left',renderer: function(val,item,rowIndex){
		return rendererBuild(6,rowIndex,item.myComment);
	}}
];

var mmGrid = $('#grid-table').mmGrid({
	height: 440,
	cols: cols,
	indexCol: true,
	indexColWidth: 45,
	fullWidthRows: true,
	autoLoad: false,
	noDataText: '没有数据'
});

J('#registTime').calendar({maxDate:'',format:'yyyy-MM-dd HH:mm:ss'}); 

//字符串转日期格式，strDate要转为日期格式的字符串
function getDate(strDate) {
    var date = eval('new Date(' + strDate.replace(/\d+(?=-[^-]+$)/,
    function (a) { return parseInt(a, 10) - 1; }).match(/\d+/g) + ')');
    return date;
}

var _dataArray = [{}];
var errorData = null;

$("#excelFormBtn").click(function(){
	YouGou.UI.fullScreenLoading();
	$.ajaxFileUpload({
		url:'/comment/importVirtualComment.sc',
		secureuri:false,
		fileElementId:'fileToUpload',
		dataType: 'text',
		success: function (data, status){
			YouGou.UI.stopLoading();
			data = data.substring(data.indexOf('{'),data.indexOf('</pre>'));
			data = JSON.parse(data);
			if(data.code != "ok"){
				YouGou.UI.Dialog.alert({message:data.msg});
				return;
			}
			errorData = data.lstCommentMatrix;
			var tipMsg = "成功加载文件，共"+ data.rows +"行";
			if(data.errorRows > 0){
				tipMsg += "，但有"+ data.errorRows +"处错误，请修正数据!";
				YouGou.UI.tipError(tipMsg);
			}else{
				YouGou.UI.tipSuccess(tipMsg);
			}
			
			if(data.rows == 0 || data.rows < 20){
				mmGrid.setAutoHeight();// 复位高度
			}
			mmGrid.opts.items = data.lstCommodityComment;
			$("#grid-table-div").removeClass("hide");
			mmGrid.load();
			_dataArray = data.lstCommodityComment;
			
			if(data.errorRows){
				$("#bathDo").addClass("disabled");
				$("#bathDo").unbind('click');
			}else{
				$("#bathDo").removeClass("disabled");
				// 提交导入
				$("#bathDo").click(function(){
					$("#bathDo").addClass("disabled");
					YouGou.Ajax.doPost({
						  url: "/comment/importToDB.sc",
						  successMsg: _dataArray.length + '条评论导入成功!',
						  fullScreenLoading:true,
						  data: {"_dataArray":JSON.stringify(_dataArray)},
						  success : function() {
						  },
						  error:function() {
							  $("#bathDo").removeClass("disabled");
						  }
					});
				});
			}
			tip();
		},error: function (XmlHttpRequest, status, e){
			YouGou.UI.Dialog.show({
            	type : YouGou.UI.Dialog.Type.WARNING,
				closable : true,
				closeByBackdrop : false,
            	cssClass : 'message-mmgrid-load-error',
            	title : '500，抱歉，服务器内部异常！',
            	message : XmlHttpRequest.responseText.replace(/(\n)+|(\r\n)+/g, ""),
            	onshown : function(dialogRef){
            		window.top.mmgridLoadErrorDialog = dialogRef;
            	}
            });
			YouGou.UI.stopLoading();
		}
	});
});

function tip(){
	$(".errorTip" ).tooltip({
		show: {
			effect: "slideDown",
			delay: 50
		},
		hide: {
			effect: "explode",
			delay: 100
		}
	});
}

$("#creatVirBtn").click(function(){
	$("#virtualUserForm").submit();
});
