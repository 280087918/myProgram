/*************
	会员等级列表
**************/
var levelStateFixed = function(val){
	if(val == 1){
		return "<span class='text-success'><b>启用</b></span>";
	}else if(val == 0){
		return "<span class='text-danger'><b>禁用</b></span>";
	}
};
var cols = [
    { title:'等级名称', name:'levelName',width:80, align:'center'},
    { title:'会员状态', name:'levelState', lockWidth:true, width:80, align:'center',renderer: levelStateFixed},
	{ title:'会员折扣', name:'retailDiscount' , lockWidth:true, width:75, align:'center'},
	{ title:'最少消费积分', name:'pointsRangeLess', width:85, align:'center', renderer: YouGou.Util.integralFixed},
	{ title:'最大消费积分', name:'pointsRangeMore' ,width:85, align:'center', renderer: YouGou.Util.integralFixed},
	{ title:'最少消费金额', name:'consumerRangeLess' ,width:85, align:'center', renderer: YouGou.Util.moneyFixed},
	{ title:'最大消费金额', name:'consumerRangeMore' ,width:85, align:'center', renderer: YouGou.Util.moneyFixed},
	{ title:'更新时间', name:'updateTime' ,width:135, lockWidth:true, align:'center'},
    { title:'是否默认等级', name:'defaultLevel' ,width:120, align:'center', lockWidth:true, lockDisplay: true, renderer: function(val){
        if(parseInt(val) == 1){
			return '<span class="ace-icon fa fa-check"></span>'
		}
    }},
    { title:'操作', name:'' ,width:200, align:'center',lockWidth:true, renderer: function(val,item,rowIndex){
    	var html = [];
    	html.push('<a href="javascript:void(0);" action="edit">编辑</a>&nbsp;&nbsp;');
    	html.push('<a href="javascript:void(0);" action="delete">删除</a>&nbsp;&nbsp;');
    	if(item.levelState == 1){
    		html.push('<a href="javascript:void(0);" action="disable">禁用</a>');
    	}else{
    		html.push('<a href="javascript:void(0);" action="enabled">启用</a>');
    	}
        return html.join('');
    }},
    { title:'ID', name:'id', hidden: true}
];

// 表格	
var mmGrid = $('#grid-table').mmGrid({
	height: 'auto',
	cols: cols,
	url: '/member/queryMemberLevelData.sc',
	indexCol: true,
	indexColWidth: 45,
	fullWidthRows: true,
	autoLoad: true,
	plugins: [
	    $('#grid-pager').mmPaginator({})
	]
});

mmGrid.on('cellSelected', function(e, item, rowIndex, colIndex){
	var action = $(e.target).attr("action");
    //编辑
    if(action == "edit"){
    	showEditForm(item.id,item.levelName);
    }else if(action == "delete"){// 删除
   		YouGou.UI.Dialog.confirm({
   			message : "确认删除名为【"+ item.levelName +"】会员等级吗？"
   		},function(result){
   			if(result) {
                removeMemberLevel(item.id,item.levelName);
            }
   		});
    }else if(action == "disable" || action == "enabled"){// 禁用/启用
    	var operName = "启用";
    	var state = 0;
    	if(item.levelState == 1){
    		operName = "禁用";
    		state = 0;
    	}else{
    		operName = "启用";
    		state = 1;
    	}
    	YouGou.UI.Dialog.confirm({
   			message : "确认将名为【"+ item.levelName +"】会员等级"+ operName +"吗？"
   		},function(result){
   			if(result) {
   				updateLevelState(item.id,state,item.levelName);
            }
   		});
    }
    e.stopPropagation();  //阻止事件冒泡
});

// 隐藏表格显示表单
function showForm(){
	$("#girdContent").addClass("hide");
	$("#memberLevelNavbar,#memberLevelForm").removeClass("hide");
	YouGou.UI.resetForm("memberLevelForm");
}

// 隐藏表单显示表格
function hideForm(){
	$("#memberLevelNavbar,#memberLevelForm").addClass("hide");
	$("#girdContent").removeClass("hide");
}

// 修改状态
function updateLevelState(id,state,levelName){
	YouGou.Ajax.doPost({
		successMsg: '会员等级【'+ levelName +'】'+ (state==0?"禁用":"启用") +'成功!',
		url : '/member/updateLevelStateById.sc',
	  	data : { "id" : id , "levelState" : state},
	  	success : function(data){
  			mmGrid.load();
		}
	});
}

//删除
function removeMemberLevel(id,levelName){
	YouGou.Ajax.doPost({
		successMsg: '会员等级【'+ levelName +'】删除成功!',
		url: '/member/removeMemberLevelByLevelId.sc',
	  	data: { "id" : id },
	  	success : function(data){
  			mmGrid.load();
		}
	});
}

// 编辑
function showEditForm(levelId,levelName){
	YouGou.Ajax.doPost({
		tips : false,
		successMsg: '会员等级【'+ levelName +'】修改成功!',
		url : "/member/getMemberLevelByLevelId.sc",
		data : {"levelId" : levelId},
	  	success : function(data){
  			showForm();
  			YouGou.UI.initForm("memberLevelForm", data.data);
		}
	});
}

// 保存
function saveMemberLevel(){
	if(!$('#memberLevelForm').validate().form()){
		return;
	};
	
	YouGou.Ajax.doPost({
		successMsg: '会员等级'+ (YouGou.Util.isEmpty($("#id").val())?"创建":"修改") +'成功!',
		url: '/member/saveMemberLevel.sc',
	  	data: $("#memberLevelForm").serializeArray(),
	  	async: false,
	  	success : function(data){
  			mmGrid.load();
  			hideForm();
		}
	});
}

var rules = {
    levelName : { required : true },
    retailDiscount : { required : true },
    pointsRangeLess : { required : true },
    pointsRangeMore : { required : true },
    consumerRangeLess : { required : true },
    consumerRangeMore : { required : true }
};

var validator = YouGou.UI.bindFormValidator("memberLevelForm",rules,{});
