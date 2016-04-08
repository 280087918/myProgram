/*************
	登录送积分列表
**************/
var cols = [
    { title:'赠送积分', name:'price',width:46, align:'center',renderer:YouGou.Util.integralFixed},
    {title:'生效时间', name :'createDate', width :80, align:'center', renderer: function(val){
    	return new Date(val).format('yyyy-MM-dd');
    }},
    {title:'更新时间', name :'updateDate', width :80, align:'center', renderer: YouGou.Util.timeFixed},
    { title:'创建者', name:'creator' ,width:70, align:'center'},
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
	url: '/active/queryActivityScoreData.sc',
	indexCol: true,
	fullWidthRows: true,
	autoLoad: true,
	plugins: [
	   mmFormParams, mmPaginator
	]
});

// 查询
function doQuery(isdown) {
	mmGrid.load();
}

mmGrid.on('cellSelected',function(e, item, rowIndex, colIndex) {
	var action = $(e.target).attr("action");
    // 编辑
    if(action == "edit"){
    	showEditForm(item.id,item.price);
    }else if (action == "delete") {// 删除
		YouGou.UI.Dialog.confirm({
   			message : "确认删除登录送积分为【" + item.price + "】信息吗？"
   		},function(result){
   			if(result) {
   				removeActivityScore(item.id, item.price);
            }
   		});
	}
});

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

$(function(){
	J('#startCreateDate').calendar({maxDate:'#endCreateDate',format:'yyyy-MM-dd'});
	J('#endCreateDate').calendar({minDate:'#startCreateDate',format:'yyyy-MM-dd'});
	J('#createDate').calendar({format:'yyyy-MM-dd'});
});


//编辑
function showEditForm(id, price) {
	YouGou.Ajax.doPost({
		tips : false,
		url : "/active/getActivityScoreDataById.sc",
		data : {"id" : id},
	  	success : function(data){
  			showForm(1);
  			data.data.createDate = new Date(data.data.createDate).format('yyyy-MM-dd');
  			YouGou.UI.initForm("userActivityScoreForm", data.data);
		}
	});
}

// 删除
function removeActivityScore(id,price){
	YouGou.Ajax.doPost({
		successMsg : '登录送积分【'+ price +'】删除成功!',
		url: '/active/removeActivityScore.sc',
	  	data: { "id" : id },
	  	success : function(data){
  			mmGrid.load();
		}
	});
}

//保存
function save() {
	var rules = {price : { required : true }, createDate : { required : true }};
	var msg = {price : {required : "" }, createDate : { required : "活动时间不能为空" }};
	if(!/^\+?[1-9][0-9]*$/.test($("#price").val())){
		$("#price").val("");
		msg.needIntergral.required = "登录积分必须为整数";
	}
	var validator = YouGou.UI.bindFormValidator("userActivityScoreForm",rules,msg);
	if ($('#userActivityScoreForm').validate().form()) {
		YouGou.Ajax.doPost({
			tips : false,
			successMsg : '登录送积分'+ (YouGou.Util.isEmpty($("#id").val()) ? "创建" : "修改") + '成功!',
			url : '/active/saveActivityScore.sc',
			data : $("#userActivityScoreForm").serializeArray(),
			success : function(data) {
				mmGrid.load();
	  			hideForm();
			}
		});
	}
}

//隐藏表格显示表单
function showForm(flag){
	$("#girdContent").addClass("hide");
	$("#userActivityScoreNavbar,#userActivityScoreForm").removeClass("hide");
	YouGou.UI.resetForm("userActivityScoreForm");
}

//隐藏表单显示表格
function hideForm(){
	$("#userActivityScoreNavbar,#userActivityScoreForm").addClass("hide");
	$("#girdContent").removeClass("hide");
}