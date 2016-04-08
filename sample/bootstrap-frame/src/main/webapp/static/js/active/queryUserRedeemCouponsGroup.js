/*************
积分兑换优惠卷分组列表
**************/
var cols = [
    { title:'组合名称', name:'groupPropertyName',width:70, align:'left'},
    { title:'链接', name:'groupPropertyNo',width:500, align:'left', renderer: function(val,item){
    	var html = [];
    	html.push('<a href="http://www.yougou.com/my/goRedeemCouponGroup.jhtml?groupId='+val+'"'
    			+'target="_blank">www.yougou.com/my/goRedeemCouponGroup.jhtml?groupId='+val+'</a>');
    	return html.join('');
    }},
    {title:'添加时间', name :'createTime', width :125,lockWidth:true, align:'center', renderer:YouGou.Util.timeFixed},
    { title:'操作', name:'' ,width:110, align:'center',lockWidth:true, lockDisplay: true, renderer: function(val){
    	var html = [];
    	html.push('<a href="javascript:void(0);" action="select">查看</a>&nbsp;&nbsp;');
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
	url: '/active/queryUserRedeemCouponsGroupData.sc',
	indexCol: true,
	indexColWidth: 45,
	fullWidthRows: true,
	autoLoad: true,
	remoteSort: true,
	plugins: [
	   mmFormParams, mmPaginator
	]
});

// 查询
function doQuery() {
	mmGrid.load();
}

mmGrid.on('cellSelected', function(e, item, rowIndex, colIndex) {
	var action = $(e.target).attr("action");
	// 查看
	if (action == "select") {
		showEditForm(item, 1);
	}
	// 编辑
	else if (action == "edit") {
		showEditForm(item, 2);
	} 	
	// 删除
	else if (action == "delete") {
		YouGou.UI.Dialog.confirm({
	  		message : "确认删除积分兑换优惠卷分组【" + item.groupPropertyName + "】信息吗？"
	  	}, function(result) {
	  		if(result) {
	  			removeRedeemCouponsGroup(item.id, item.groupPropertyName);
	  		}
		});
	}
});

// 回车查询
$('#searchForm input').keypress(function(e) {
	if (e.which == 13) {
		doQuery();
		return false;
	}
});

var dualListbox;
var selectNamefix = "_select";
var loadDualListBox = function(allData, selectData, type) {
	dualListbox  = $('select[name="couponGroupRelateItem"]');
	if(selectData == null) {
		$.each(allData, function(i, item) {
			dualListbox.append('<option value="' + item.id + '">'+ item.activeName + '</option>');
		});
	} else {
		$.each(allData, function(i, item1) {
			var id1 = item1.id;
			var activeName = item1.activeName;
			var selected = false;
			$.each(selectData.memberIntergralRedeemCouponGroupRelateLst, function(j, item2) {
				var id2 = item2.intergralRedeemCouponId;
				var intergralRedeemCouponName = item2.intergralRedeemCouponName;
				if(id1 == id2) {
					selected = true;
				} 
			});
			if(selected) {
				dualListbox.append('<option value="' + id1 + '" selected="selected">' + activeName + '</option>');
			} else {
				dualListbox.append('<option value="' + id1 + '">' + activeName + '</option>');
			}
		});
	}
	dualListbox.bootstrapDualListbox({
		selectorMinimalHeight : 300,
		helperSelectNamePostfix : selectNamefix,
		selectedListLabel : '使用中活动',
		nonSelectedListLabel : '全部活动',
		infoText : '一共 {0}条',
		moveOnSelect : false,
		nonSelectedFilter : '',
		infoTextFiltered : '<span class="label label-purple label-lg">过滤后结果</span>'
	});
	hideSelect(type);
};

// 查看or编辑
function showEditForm(data, type) {
	showForm(data,type);
	YouGou.Ajax.doPost({
		tips : false,
		url : "/active/getRedeemCouponsGroupData.sc",
		data : {"activeName" : null},
		success : function(allData) {
			if (type == 1 || type == 2) {
				YouGou.Ajax.doPost({
					tips : false,
					url : "/active/getRedeemCouponsGroupDataById.sc",
					data : {"id" : data.id},
					success : function(selectData) {
						loadDualListBox(allData.data, selectData.data, type);
					}
				});
			} else {
				loadDualListBox(allData.data, null, type);
			}
		}
	});
}

//删除
function removeRedeemCouponsGroup(id,groupPropertyName){
	YouGou.Ajax.doPost({
		successMsg : '积分兑换优惠卷分组【'+ groupPropertyName +'】删除成功!',
		url: '/active/removeRedeemCouponsGroup.sc',
	  	data: { "id" : id },
	  	success : function(data){
  			mmGrid.load();
		}
	});
}

function destroy(dualListbox) {
	if (!YouGou.Util.isNull(dualListbox)) {
		var element = dualListbox.bootstrapDualListbox('destroy');
		if (YouGou.Util.isEmpty(element.selector)) {
			$('#couponGroupRelateItem').remove();
			$('#dualListBox').append('<select multiple="multiple" size="10" name="couponGroupRelateItem" id="couponGroupRelateItem" ></select>');
		}
	}
}

//隐藏select
function hideSelect(type) {
	if (type == 1) {
		$('.moveall,.move,.removeall,.remove').attr('disabled', true);
		$('select[name="couponGroupRelateItem'+selectNamefix+'1"]').attr("disabled", true);
		$('select[name="couponGroupRelateItem'+selectNamefix+'2"]').attr("disabled", true);
	} else {
		$('.moveall,.move,.removeall,.remove').attr('disabled', false);
		$('select[name="couponGroupRelateItem'+selectNamefix+'1"]').attr("disabled", false);
		$('select[name="couponGroupRelateItem'+selectNamefix+'2"]').attr("disabled", false);
	}
}

//隐藏表单显示表格
function hideForm(){
	destroy(dualListbox);
	$("#redeemCouponsGroupNavbar,#redeemCouponsGroupForm").addClass("hide");
	$("#girdContent").removeClass("hide");
	YouGou.UI.resetForm("redeemCouponsGroupForm");
}

//隐藏表格显示表单
function showForm(data,type) {
	YouGou.UI.initForm("redeemCouponsGroupForm", data);
	$("#redeemCouponsGroupForm :input[name='type']").val(type);
	$("#girdContent").addClass("hide");
	$("#redeemCouponsGroupNavbar,#redeemCouponsGroupForm").removeClass("hide");
	if (type == 1) {
		$("#sbtn").addClass("hide");
		$("#redeemCouponsGroupForm :input[name='groupPropertyName']").attr("readonly", true);
	} else {
		$("#sbtn").removeClass("hide");
		$("#redeemCouponsGroupForm :input[name='groupPropertyName']").attr("readonly", false);
	}
}

function bindFormValidator(){
	var rules = {groupPropertyName : { required : true }};
	var msg = {groupPropertyName : {required : "组名不能为空!" }};
	YouGou.UI.bindFormValidator("redeemCouponsGroupForm",rules,msg);
}

// 确定添加
function save() {
	bindFormValidator();
	if ($('#redeemCouponsGroupForm').validate().form()) {
		var arr = buildModel();
		if (!arr) {
			YouGou.UI.Dialog.alert({
				message : '请选择优惠卷活动后提交！'
			});
			return ;
		}
		YouGou.Ajax.doPost({
			tips : false,
			url : "/active/updateRedeemCouponsGroup.sc",
			data : {
				'couponGroupCategories' : YouGou.Util.toJsonString(arr),
				'groupPropertyName' : $("#redeemCouponsGroupForm :input[name='groupPropertyName']").val(),
				'type' : $('#type').val()
			},
			success : function(data) {
				if (data) {
					mmGrid.load();
		  			hideForm();
				}
			}
		});
	}
}

function buildModel() {
	var arr = new Array();
	var select2 = $('select[name="couponGroupRelateItem'+selectNamefix+'2"] option');
	if (select2.length == 0) {
		return false;
	}
	MemberIntergralRedeemCouponGroupRelateVo = function(config) {
		YouGou.Base.apply(this, config);
		this.intergralRedeemCouponId = this.intergralRedeemCouponId || "";
	}
	select2.each(function() {
		var couponGroupVal = $(this).val().split(";");
		var property = new MemberIntergralRedeemCouponGroupRelateVo({
			"intergralRedeemCouponId" : couponGroupVal[0]
		});
		var intergralRedeemCouponGroupId = $('#intergralRedeemCouponGroupId').val();
		if (intergralRedeemCouponGroupId != null || intergralRedeemCouponGroupId != '') {
			property = new MemberIntergralRedeemCouponGroupRelateVo({
				"intergralRedeemCouponId" : couponGroupVal[0],
				"intergralRedeemCouponGroupId" : intergralRedeemCouponGroupId
			});
		}
		arr.push(property);
	});
	return arr;
}
