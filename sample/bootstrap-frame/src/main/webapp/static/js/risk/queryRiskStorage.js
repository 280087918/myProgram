/*************  风险库数据列表   **************/
var cols = [
    { title:'添加时间', name:'createTime',lockWidth:true,width:125, align:'center',renderer:YouGou.Util.timeFixed}, 
    { title:'操作人', name:'oprName', lockWidth:true, width:80, align:'center'},
	{ title:'订单号', name:'orderMainNo' , lockWidth:true, width:115, align:'center'},
	{ title:'添加类型', name:'type', lockWidth:true,width:80, align:'center'},
	{ title:'收货地址', name:'address',width:270 ,align:'left'},
	{ title:'收货人', name:'consigneeName' ,width:80, lockWidth:true,align:'center'},
	{ title:'收货手机', name:'phone' ,lockWidth:true,width:90, align:'center'},
	{ title:'内容', name:'cause' ,align:'left'},
    { title:'操作', name:'' ,width:70, lockWidth:true,align:'center',lockWidth:true, renderer: function(val,item,rowIndex){
    	var html = [];
    	html.push('<a href="javascript:void(0);" action="edit">编辑</a>&nbsp;&nbsp;');
    	html.push('<a href="javascript:void(0);" action="delete">删除</a>');
        return html.join('');
    }},
    { title:'ID', name:'id', hidden: true}
];

//表单分页器
var mmPaginator = $('#grid-pager').mmPaginator({});
//搜索表单属性
var mmFormParams = new MMSearchFormParams("searchForm");

// 表格	
var mmGrid = $('#grid-table').mmGrid({
	height: 'auto',
	cols: cols,
	url: '/risk/queryRiskStorageData.sc',
	fullWidthRows: true,
	autoLoad: true,
	plugins: [
	          mmFormParams,mmPaginator
	]
});

mmGrid.on('cellSelected', function(e, item, rowIndex, colIndex){
	var action = $(e.target).attr("action");
    //编辑风险库数据
    if(action == "edit"){
    	editRiskStorage(item);
    }else if(action == "delete"){// 删除
    	YouGou.UI.Dialog.confirm({
	   		message : '确认删除名该条风险库数据吗？'
		},function(result){
			 if(result) {
			 	removeRiskStorage(item.id);
		     }
	    });
    }
    e.stopPropagation();  //阻止事件冒泡
});

function doQuery(){
	// 条件输入校验
    mmGrid.load();
}

//编辑风险库数据
function editRiskStorage(item){
	showForm(item.area);
	YouGou.UI.initForm("riskStorageForm", item);
}

//删除风险库信息
function removeRiskStorage(riskId){
	YouGou.Ajax.doPost({
		successMsg: '删除该条风险库信息成功!',
		url: '/risk/deleteRiskStorageById.sc',
	  	data: {"id" : riskId},
	  	success : function(data){
  			mmGrid.load();
		}
	});
}

//保存风险库信息
function saveRiskStorage(){
	if(!validateForm()){
		return;
	}
	YouGou.Ajax.doPost({
		successMsg: '风险库信息'+ (YouGou.Util.isEmpty($("#riskId").val())?"创建":"修改") +'成功!',
		url: '/risk/saveRiskStorageData.sc',
	  	data: $("#riskStorageForm").serializeArray(),
	  	success : function(data){
  			mmGrid.load();
  			hideForm();
		}
	});
}

//初始化省市区信息
function initArea(area){
	//先清空
	$("#provinceId").empty();
	//省编号
	var provinceId = null;
	//市编号
	var cityId = null;
	if(area){
		cityId = area.substring(0,area.lastIndexOf("-"));
		provinceId = cityId.substring(0,cityId.lastIndexOf("-"));
	}
	areaInit("provinceId",provinceId,"cityId",cityId,"countryId",area);
}

//隐藏表格显示表单
function showForm(area){
	$("#girdContent").addClass("hide");
	$("#riskStorageNavbar,#riskStorageForm").removeClass("hide");
	YouGou.UI.resetForm("riskStorageForm");
	initArea(area);
}

// 隐藏表单显示表格
function hideForm(){
	$("#riskStorageNavbar,#riskStorageForm").addClass("hide");
	$("#girdContent").removeClass("hide");
}


function validateForm(){
	var numberRule = /(^\d*$)/; // 全为数字
	var englishAllRule = /^[a-z|A-Z]+$/;// 全部为英文
	var symbolAllRule = /[a-z\d\s\u4e00-\u9fa5]/;// 全部为标点符号
	var symbolRule = new RegExp(
			".*(`|~|%|!|@|\\+|#|\\[|\\]|<|>|《|》|\/|\\^|=|\\?|~|！|:|；|;|：|·|\\$|￥|【|】|…|&|‘|“|”|\"|？|\\*|\\(|\\)|{|}|（|）|、|\\\\|\\|)+"); // 标点符号
	var followNumberRule = /\d{8}/;// 连续8个数字
	var address = $("#address").val();
	//收货人地址、收货人姓名、收货人手机必须完整填写其一
	if(!address && !$("#consigneeName").val() && !$("#mobilePhone").val()){
		errorMessage("addressMsg","收货人地址、收货人姓名、收货人手机必须完整填写其一");
		return false;
	}
	//判断地址
	if(address){
		if($(countryId).val() == -1){
			errorMessage("countryIdMsg","请选择省市区");
			return false;
		}
		address = address.replace(/(^\s*)|(\s*$)/g, "");// 去除左右空格
		if (address.length <= 5 || address.length >= 120){
			errorMessage("addressMsg","收货人地址，要求5-120个字符");
			return false;
		}
		if (address.match(numberRule) || !address.match(symbolAllRule) || address.match(englishAllRule)
				|| address.match(symbolRule) || followNumberRule.test(address)) {
			errorMessage("addressMsg","请填写详细地址，不能全部是数字/英文/包含特殊符号（括号、井号等）");
			return false;
		}
	}
	//判断收货人姓名
	var consigneeName = $("#consigneeName").val(); 
	if(consigneeName){
		consigneeName = consigneeName.replace(/(^\s*)|(\s*$)/g, "");// 去除左右空格
		if(consigneeName.length < 2 || consigneeName.length >= 25 ){
			errorMessage("consigneeNameMsg","收货人姓名，要求2-25个字符");
			return false;
		}
		if (consigneeName.match(numberRule) || !consigneeName.match(symbolAllRule) || consigneeName.match(symbolRule)) {
			errorMessage("consigneeNameMsg","请您使用真实姓名，不能全部是数字，不能包含特殊符号（括号、井号等）");
			return false;
		}
	}
	//判断收货人手机
	var phone = $("#phone").val();
	if(phone){
		phone = phone.replace(/(^\s*)|(\s*$)/g, "");// 去除左右空格
		var rePhone = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0-9]|17[0-9])\d{8}$/;
		if(!rePhone.test(phone)){
			errorMessage("phoneMsg","请您输入正确格式的手机号码");
			return false;
		}
	}
	
	return true;
}
function errorMessage(id,msg){
	$("#" + id).text(msg);
}
function clearErrorMsg(id){
	$("#" + id).text("");
}

J('#startTime').calendar({maxDate:'#endTime',format:'yyyy-MM-dd HH:mm:ss'});
J('#endTime').calendar({minDate:'#startTime',format:'yyyy-MM-dd HH:mm:ss'});
