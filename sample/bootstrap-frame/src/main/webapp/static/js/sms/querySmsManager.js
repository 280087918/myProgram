/*************
	短信提醒人列表
**************/
var cols = [
    { title:'姓名', name:'mangerName',width:60, align:'center'},
    { title:'手机号码', name:'mobilePhone',width:120, align:'center'},
	{ title:'办公电话', name:'telPhone' , width:100, align:'center'},
	{ title:'邮箱', name:'email', width:120, align:'center'},
	{ title:'部门', name:'dept' ,width:120, align:'center', renderer: YouGou.Util.emptyFixed},
    { title:'操作', name:'' ,width:80, align:'center', lockDisplay: true, renderer: function(val){
    	var html = [];
    	html.push('<a href="javascript:void(0);" action="edit">编辑</a>&nbsp;&nbsp;');
    	html.push('<a href="javascript:void(0);" action="delete">删除</a>');
    	return html.join('');
    }}
];

// 分页器
var mmPaginator = $('#grid-pager').mmPaginator({});


// 表格	
var mmGrid = $('#grid-table').mmGrid({
	height: 'auto',
	cols: cols,
	url: '/sms/querySmsMangerData.sc',
	indexCol: true,
	indexColWidth: 45,
	fullWidthRows: true,
	autoLoad: true,
	plugins: [
	   mmPaginator
	]
});

mmGrid.on('cellSelected', function(e, item, rowIndex, colIndex){
	var action = $(e.target).attr("action");
    //编辑
    if(action == "edit"){
    	showEditForm(item.id,item.mangerName);
    }else if(action == "delete"){// 删除
    	YouGou.UI.Dialog.confirm({
	   		message : "确认删除名为【"+ item.mangerName +"】信息吗？"
		},function(result){
			 if(result) {
				 removeSmsManger(item.id,item.mangerName);
		     }
	    });
    }
});

//编辑
function showEditForm(id, mangerName) {
	YouGou.Ajax.doPost({
		tips : false,
		successMsg: '短信提醒人【'+ mangerName +'】修改成功!',
		url : "/sms/getSmsMangerDataById.sc",
		data : {"id" : id},
	  	success : function(data){
  			showForm();
  			YouGou.UI.initForm("smsMangerForm", data.data);
		}
	});
}

//隐藏表格显示表单
function showForm(){
	$("#girdContent").addClass("hide");
	$("#smsMangerNavbar,#smsMangerForm").removeClass("hide");
	YouGou.UI.resetForm("smsMangerForm");
}

// 隐藏表单显示表格
function hideForm(){
	$("#smsMangerNavbar,#smsMangerForm").addClass("hide");
	$("#girdContent").removeClass("hide");
}

//保存
function save(){
	if ($('#smsMangerForm').validate().form()) {
		YouGou.Ajax.doPost({
			successMsg: '短信提醒人'+ (YouGou.Util.isEmpty($("#id").val())?"创建":"修改") +'成功!',
			url: '/sms/saveSmsManger.sc',
		  	data: $("#smsMangerForm").serializeArray(),
		  	success : function(data){
	  			mmGrid.load();
	  			hideForm();
			}
		});
	}
}

//删除
function removeSmsManger(id,mangerName){
	YouGou.Ajax.doPost({
		successMsg : '短信提醒人【'+ mangerName +'】删除成功!',
		url: '/sms/removeSmsManger.sc',
	  	data: { "id" : id },
	  	success : function(data){
  			mmGrid.load();
		}
	});
}

function sendSms() {
	var sprdid = $("#sprdidList option:selected").val();
	var sdst = $("#sdst").val();
	var message = $("#smsList option:selected").val();
	if (sprdid == "-1") {
		YouGou.UI.Dialog.alert({message:"请选择要发送的产品类型"});
		return false;
	}
	if (sdst == "") {
		YouGou.UI.Dialog.alert({message:"请输入手机号码"});
		return false;
	}
	if (message == "-1") {
		YouGou.UI.Dialog.alert({message:"请选择要发送的内容"});
		return false;
	}
	YouGou.Ajax.doPost({
		successMsg : '向手机号【' + $("#sdst").val() + '】发送短信已成功!',
		url : "/sms/sendTestSms.sc",
		data : $("#smsForm").serialize(),
		success : function(data) {
			YouGou.UI.Dialog.show({
				message : '向手机号【' + $("#sdst").val() + '】发送短信已成功!',
				type : YouGou.UI.Dialog.Type.SUCCESS
			});
		}
	});
}

function showMessage(){
    var message = $("#smsList option:selected").val();
    var sprdid = $("#sprdidList option:selected").val();
    $('#sprdid').attr('value',sprdid);
    if(message == '0'){
      var text = '测试信息：尊敬的用户，您的验证码为123456，请在规定时间内输入验证码以完成注册。';
      $("#contentPlaceHolder")[0].innerHTML=text; 
      $('#smsg').attr('value',text);
    }
    else if(message == '1'){
      var text = '测试信息：尊敬的用户，您已成功购买某某商品，订单金额为XXX元，我们会在3个工作日内将商品送到您指定的地址，请您注意查收。';
      $("#contentPlaceHolder")[0].innerHTML=text;
      $('#smsg').attr('value',text);
    }
    else if(message == '2'){
      var text = '测试信息：尊敬的用户，您要索取的优惠券号码为123456，您可以在指定的影院凭此号码享受双人半价优惠。';
      $("#contentPlaceHolder")[0].innerHTML=text;
      $('#smsg').attr('value',text);
    }
}

var rules = {
	mangerName : { required : true },
	mobilePhone : { required : true }
};

var msg = {
	mangerName : {required : "姓名不能为空" },
	mangerName : {required : "手机号码不能为空" }
};

var validator = YouGou.UI.bindFormValidator("smsMangerForm",rules,msg);
