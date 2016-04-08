/*************
	分销包
**************/
// 分销包状态
var bagStateFixed = function(val){
	var status = parseInt(val);
    if(status == 0){
		return '启用';
	}else if(status == 1){
		return '停用';
	}else{
		return '未知状态['+status+']';
	}
};
// 操作列
var actionFixed = function(val,item,rowIndex){
	var html = [];
	html.push('<a href="javascript:void(0);" action="edit">编辑</a>&nbsp;&nbsp;');
	html.push('<a href="javascript:void(0);" action="delete">删除</a>&nbsp;&nbsp;');
	if(item.status == 1){
		html.push('<a href="javascript:void(0);" action="enabled">启用</a>');
	}else{
		html.push('<a href="javascript:void(0);" action="disable">禁用</a>');
	}
    return html.join('');
};

// 列集合
var cols = [
    { title:'分销包名称', name:'bagName', align:'left'},
    { title:'包含商品数', name:'commodityNum', lockWidth:true, width:80, align:'center'},
	{ title:'排序', sortable: false, sortName:'sortNo',
    	name:'sortNo' , width:60, align:'center', lockDisplay: true, renderer: function(val,item){
    	var html = [];
    	html.push('<span class="fa fa-minus"></span>&nbsp;&nbsp;');
		html.push('<input type="text" value="'+val+'" size="2" readonly="true">');
		html.push('&nbsp;&nbsp;<span class="fa fa-plus"></span>');
    	return html.join('');
    }},
    { title:'最后更新时间', name:'updateTime' ,width:135, lockWidth:true, align:'center', renderer : YouGou.Util.timeFixed},
	{ title:'最后更新人', name:'updateUser' ,width:85,lockWidth:true, align:'center'},
    { title:'状态', name:'status' ,width:80, align:'center', lockWidth:true, lockDisplay: true, renderer: bagStateFixed},
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
	url: '/commodity/queryPackageListData.sc',
	fullWidthRows: true,
	autoLoad: true,
	plugins: [mmPaginator,mmFormParams]
});

// 表格事件
mmGrid.on('cellSelected', function(e, item, rowIndex, colIndex){
	var action = $(e.target).attr("action");
    //编辑
    if(action == "edit"){
    	alert("edit...");
    }else if(action == "delete"){// 删除
   		YouGou.UI.Dialog.confirm({
   			message : "确认删除名为【"+ item.bagName +"】分销包吗？"
   		},function(result){
   			if(result) {
   				alert("delete...");
            }
   		});
    }else if(action == "disable" || action == "enabled"){// 禁用/启用
    	var operName = "";
    	if(item.state == 1){
    		operName = "启用";
    	}else{
    		operName = "禁用";
    	}
    	YouGou.UI.Dialog.confirm({
   			message : "确认将名为【"+ item.bagName +"】分销包"+ operName +"吗？"
   		},function(result){
   			if(result) {
   				alert(operName+"...");
            }
   		});
    }else if(colIndex == 2){
    	var sortObj = $(e.target).siblings().filter('input');
    	if(sortObj.length == 1){
    		var sort = parseInt(sortObj.val());
    		if ($(e.target).hasClass('fa-plus')) {
    			sortObj.val(sort + 1);
    		} else if ($(e.target).hasClass('fa-minus')) {
    			sort = sort - 1;
    			sortObj.val(sort);
    		}
    		YouGou.Ajax.doPost({
    			tips : false,
    			successMsg : '短信快捷编码'+ (YouGou.Util.isEmpty(item.id) ? "创建" : "修改") + '成功!',
    			url : '/commodity/savePackageData.sc',
    			data : {"id": item.id, "sortNo": sortObj.val()},
    			success : function(data) {
    				mmGrid.load();
    			}
    		 });
    	}
    }
    e.stopPropagation();  //阻止事件冒泡
}).on('loadSuccess', function(e, data){
	$('.fa-minus,.fa-plus').hover(function(){
		$(this).css("cursor","pointer");
	});
});

$('#minus,#plus').click(function() {alert(1);
	var sort = "";
	if ($(this).hasClass('fa-plus')) {
		var val = $('#sortNo').val();
		sort = parseInt(val) + 1;
		$('#sortNo').val(sort);
	}
	if ($(this).hasClass('fa-minus')) {
		var val = $('#sortNo').val();
		if (parseInt(val) > 0) {
			sort = parseInt(val) - 1;
			$('#sortNo').val(sort);
		}
	}
});

//隐藏表格显示表单(添加编辑菜单)
function showForm(area){
	$("#girdContent").addClass("hide");
	$("#packageNavbar,#packageForm").removeClass("hide");
	YouGou.UI.resetForm("packageForm");
	initArea(area);
}

//隐藏表单显示表格
function hideForm(){
	$("#packageNavbar,#packageForm").addClass("hide");
	$("#girdContent").removeClass("hide");
	$(".img1").html("");
	$("#Bag_small_pic").val("");
	$(".img2").html("");
	$("#Bag_big_pic").val("");
}

function doQuery(){
	mmGrid.load();
}

//图片上传内相关
var ratio = window.devicePixelRatio || 1;
var thumbnailWidth = 100 * ratio;
var thumbnailHeight = 100 * ratio;

//上传小图
var uploader1 = WebUploader.create({
	//选择完文件之后自动上传
	auto : true,
	swf : '/static/js/webuploader/Uploader.swf',
	server : '/commodity/uploadImg.sc',
	pick : '#filePicker1',
	duplicate : true,
	formData : {'imgType' : 1},
	accept : {
		title : 'Images',
		extensions : 'gif,jpg,jpeg,bmp,png',
		mimeTypes: 'image/*'
	}
});

uploader1.on('uploadSuccess', function(file, response) {//上传图片
	uploader1.makeThumb(file, function(error, src) {//放图片缩略图
		if (error) {
			return;
		}
		//alert(response.data.imgPath);
		$(".img1").html("<img src='"+response.data.imgBasePath + "/" + response.data.imgPath+"' />");
		$("#Bag_small_pic").val(response.data.imgPath);
	}, thumbnailWidth, thumbnailHeight );
});

//上传大图
var uploader2 = WebUploader.create({
	//选择完文件之后自动上传
	auto : true,
	swf : '/static/js/webuploader/Uploader.swf',
	server : '/commodity/uploadImg.sc?imgType=2',
	pick : '#filePicker2',
	duplicate : true,
	formData : {'imgType' : 2},
	accept : {
		title : 'Images',
		extensions : 'gif,jpg,jpeg,bmp,png',
		mimeTypes: 'image/*'
	}
});

uploader2.on('uploadSuccess', function(file, response) {//上传图片
	console.log(file);
	console.log(response);
	uploader2.makeThumb(file, function(error, src) {//放图片缩略图
		if (error) {
			return;
		}
		$(".img2").html("<img src='"+response.data.imgBasePath + "/" + response.data.imgPath+"' />");
		$("#Bag_big_pic").val(response.data.imgPath);
	}, thumbnailWidth, thumbnailHeight );
});

function saveBag() {
	if(!validateForm()) {
		return;
	}
	YouGou.Ajax.doPost({
		successMsg: '分销包'+ (YouGou.Util.isEmpty($("#id").val())?"创建":"修改") +'成功!',
		url: '/commodity/savePackageData.sc',
	  	data: $("#packageForm").serializeArray(),
	  	success : function(data){
  			mmGrid.load();
  			hideForm();
		}
	});
}

function validateForm(){
	//分销包名称非空
	var bagName = $("#packageForm #bagName").val();
	bagName = bagName.replace(/(^\s*)|(\s*$)/g, "");//去左右空格
	if(YouGou.Util.isEmpty(bagName)) {
		YouGou.UI.tip("请填写分销包名称",'error');
		return false;
	}
	
	//分销包小图非空
	var bagSmallPic = $("#Bag_small_pic").val();
	bagSmallPic = bagSmallPic.replace(/(^\s*)|(\s*$)/g, "");//去左右空格
	if(YouGou.Util.isEmpty(bagSmallPic)) {
		YouGou.UI.tip("请填上传分销包小图",'error');
		return false;
	}
	
	//分销包打图非空
	var bagBigPic = $("#Bag_big_pic").val();
	bagSmallPic = bagSmallPic.replace(/(^\s*)|(\s*$)/g, "");//去左右空格
	if(YouGou.Util.isEmpty(bagBigPic)) {
		YouGou.UI.tip("请填上传分销包大图",'error');
		return false;
	}
	
	//分销包打图非空
	var bagBigPic = $("#Bag_big_pic").val();
	bagSmallPic = bagSmallPic.replace(/(^\s*)|(\s*$)/g, "");//去左右空格
	if(YouGou.Util.isEmpty(bagBigPic)) {
		YouGou.UI.tip("请填上传分销包大图",'error');
		return false;
	}
	
	var comments = $("#comments").val();
	comments = comments.replace(/(^\s*)|(\s*$)/g, "");
	if(YouGou.Util.isEmpty(comments)) {
		YouGou.UI.tip("请填写分销包说明",'error');
		return false;
	}
	return true;
}