/*************
	积分换券活动列表
**************/
var cols = [
    { title:'快捷编码', name:'quickReplyWordNo',width:60, align:'center'},
    { title:'快捷回复语', name:'quickReplyWord',width:300, align:'center'},
	{ title:'排序', sortable: true, sortName:'sort',
    	name:'sort' , width:60, align:'center', lockDisplay: true, renderer: function(val,item){
    	var html = [];
    	html.push('<span class="fa fa-minus"></span>&nbsp;&nbsp;');
		html.push('<input type="text" value="'+val+'" size="2" readonly="true">');
		html.push('&nbsp;&nbsp;<span class="fa fa-plus"></span>');
    	return html.join('');
    }},
    {title:'添加时间', name :'createTime', width :110, align:'center', renderer:YouGou.Util.timeFixed},
	{ title:'添加人', name:'operator' ,width:100, align:'center'},
    { title:'操作', name:'' ,width:100, align:'center', lockDisplay: true, renderer: function(val){
    	var html = [];
    	html.push('<a href="javascript:void(0);" action="edit">编辑</a>&nbsp;&nbsp;');
    	html.push('<a href="javascript:void(0);" action="delete">删除</a>');
    	return html.join('');
    }}
];

// 分页器
var mmPaginator = $('#grid-pager').mmPaginator({});

//搜索表单属性
var mmFormParams = new MMSearchFormParams("searchForm");


// 表格	
var mmGrid = $('#grid-table').mmGrid({
	height: 'auto',
	cols: cols,
	url: '/sms/querySmsQuickReplyData.sc',
	indexCol: true,
	indexColWidth: 45,
	fullWidthRows: true,
	autoLoad: true,
	remoteSort: true,
	sortName: 'sort',
    sortStatus: 'desc',
	plugins: [
	   mmFormParams, mmPaginator
	]
});

//查询
function doQuery() {
	mmGrid.load();
}

//查询
function sort() {
	// 条件输入校验
	var orby = $("#orby").val();
	if (orby && orby == "asc") {
		$("#arrow").attr('class', 'ace-icon fa fa-arrow-down');
		$("#orby").val("desc");
	} else {
		$("#arrow").attr('class', 'ace-icon fa fa-arrow-up');
		$("#orby").val("asc");
	}
	mmGrid.load();
}

mmGrid.on('cellSelected', function(e, item, rowIndex, colIndex){
	var action = $(e.target).attr("action");
    //编辑
    if(action == "edit"){
    	showEditForm(item.id,item.quickReplyWordNo);
    }else if(action == "delete"){// 删除
    	YouGou.UI.Dialog.confirm({
	   		message : "确认删除编码名为【"+ item.quickReplyWordNo +"】信息吗？"
		},function(result){
			 if(result) {
				 removeSmsQuickReply(item.id,item.quickReplyWordNo);
		     }
	    });
    }else if(colIndex == 3){
    	var sortObj = $(e.target).siblings().filter('input');
    	if(sortObj.length == 1){
    		var sort = parseInt(sortObj.val());
    		if ($(e.target).hasClass('fa-plus')) {
    			sortObj.val(sort + 1);
    		} else if ($(e.target).hasClass('fa-minus')) {
    			sort = sort - 1
    			sortObj.val(sort);
    		}
    		YouGou.Ajax.doPost({
    			tips : false,
    			successMsg : '短信快捷编码'+ (YouGou.Util.isEmpty(item.id) ? "创建" : "修改") + '成功!',
    			url : '/sms/saveSmsQuickReply.sc',
    			data : {"id": item.id, "sort": sortObj.val()},
    			success : function(data) {
    				mmGrid.load();
    			}
    		 });
    	}
    }
}).on('loadSuccess', function(e, data){
	$('.fa-minus,.fa-plus').hover(function(){
		$(this).css("cursor","pointer");
	});
});

//编辑
function showEditForm(id, quickReplyWordNo) {
	YouGou.Ajax.doPost({
		tips : false,
		url : "/sms/getSmsQuickReplyDataById.sc",
		data : {"id" : id},
	  	success : function(data){
  			showForm(1);
  			var type = "";
  			if (data.data.type == 1) {
  				type = "售前";
			} else if (data.data.type == 2) {
				type = "售后";
			} else if (data.data.type == 3) {
				type = "售中";
			} else {
				type = "无";
			}
  			data.data.typeName = type;
  			YouGou.UI.initForm("smsQuickReplyForm", data.data);
		}
	});
}

//隐藏表格显示表单
function showForm(flag){
	$("#girdContent").addClass("hide");
	$("#smsQuickReplyNavbar,#smsQuickReplyForm").removeClass("hide");
	if (flag == 0) {
		$("#typeName").addClass("hide");
		$("#stypeName").removeClass("hide");
		$("#sort").val("0");
	} else if (flag == 1) {
		$("#stypeName").addClass("hide");
		$("#typeName").removeClass("hide");
	}
}

//隐藏表单显示表格
function hideForm(){
	$("#smsQuickReplyNavbar,#smsQuickReplyForm").addClass("hide");
	$("#girdContent").removeClass("hide");
}

//保存
function save() {
	var rules = {quickReplyWord : { required : true },stypeName : { required : true }};
	var msg = {quickReplyWord : {required : "短信内容不能为空" },stypeName : {required : "分类不能为空" }};
	var $visible = $('#stypeName').is(':visible');
	if (!$visible) {
		rules.stypeName.required = false;
	}
	var validator = YouGou.UI.bindFormValidator("smsQuickReplyForm",rules,msg);
	if ($('#smsQuickReplyForm').validate().form()) {
		YouGou.Ajax.doPost({
			tips : false,
			successMsg : '短信快捷编码'+ (YouGou.Util.isEmpty($("#id").val()) ? "创建" : "修改") + '成功!',
			url : '/sms/saveSmsQuickReply.sc',
			data : $("#smsQuickReplyForm").serializeArray(),
			success : function(data) {
				var isChecked = false;
				$('#myTab li a').each(function() {
					var currentToggle = $(this).attr("aria-expanded");
					if (currentToggle == 'true') {
						queryByTab($(this));
						isChecked = true;
					}
				});
				if (!isChecked) {
					mmGrid.load();
				}
				hideForm();
			}
		});
	}
}

//删除
function removeSmsQuickReply(id,quickReplyWordNo){
	YouGou.Ajax.doPost({
		successMsg : '短信快捷编码【'+ quickReplyWordNo +'】删除成功!',
		url: '/sms/removeSmsQuickReply.sc',
	  	data: { "id" : id },
	  	success : function(data){
	  		var isChecked = false;
			$('#myTab li a').each(function() {
				var currentToggle = $(this).attr("aria-expanded");
				if (currentToggle == 'true') {
					queryByTab($(this));
					isChecked = true;
				}
			});
			if (!isChecked) {
				mmGrid.load();
			}
		}
	});
}

$('#stypeName').change(function(){
	$("#smsQuickReplyForm").find("input[name=type]").val(this.value);
 });

J('#queryStartTime').calendar({maxDate:'#queryEndTime',format:'yyyy-MM-dd HH:mm:ss'});
J('#queryEndTime').calendar({minDate:'#queryStartTime',format:'yyyy-MM-dd HH:mm:ss'});

$('#minus,#plus').click(function() {
	var sort = "";
	if ($(this).hasClass('fa-plus')) {
		var val = $('#sort').val();
		sort = parseInt(val) + 1;
		$('#sort').val(sort);
	}
	if ($(this).hasClass('fa-minus')) {
		var val = $('#sort').val();
		if (parseInt(val) > 0) {
			sort = parseInt(val) - 1;
			$('#sort').val(sort);
		}
	}
});

$('#myTab li a').click(function() {
	queryByTab($(this));
});

function queryByTab(object) {
	var searchForm = $("#searchForm");
	var isHaveTypeName = false;
	$.each(searchForm.serializeArray(), function(i, field) {
		if (field.name == 'type') {
			isHaveTypeName = true;
		}
	});
	if (!isHaveTypeName) {
		searchForm.append('<input type="hidden" name="type" id="type">');
	}
	var text = object.html();
	if (text == "所有") {
		$("#type").val("");
	}
	if (text == "售前") {
		$("#type").val(1);
	}
	if (text == "售后") {
		$("#type").val(2);
	}
	if (text == "售中") {
		$("#type").val(3);
	}
	cleanPager(mmPaginator);
	mmGrid.load();
}

/**
 * 初始化分页器
 * @param mmPaginator
 */
function cleanPager(mmPaginator){
	var params = {};
	var opts = mmPaginator.opts;
    params[opts.totalCountName] = 0;
    params[opts.pageParamName] = opts.page;
    params[opts.limitParamName] = opts.limit;
    mmPaginator.load(params);
}