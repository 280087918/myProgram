/*************
	CommoditySaleCat
**************/
// 操作列动作
var actionFixed = function(val,item,rowIndex){
	var html = [];
	html.push('<a href="javascript:void(0);" action="update">删除</a>&nbsp;&nbsp;');
    return html.join('');
};

// 列集合
var cols = [
	{ title:'子分类', name:'name', align:'left'},
	{ title:'图片', name:'picUrl', align:'center',renderer:function(val,item){
		var html;
		if(val){
			html = "<img class='saleCatPic' src='" + val + "' />";
		}else{
			html = "<span class='saleCatPic' style='color:red;display:block;'>请上传图片</span>";
		}
		return html;
	}},
	{ title:'产品数', name:'productNum', align:'center'},
	{ title:'最后更新人', name:'updateUser', align:'left'},
	{ title:'最后更新时间', name:'updateTime', align:'center', renderer: YouGou.Util.timeFixed},
	{ title:'操作', name:'' ,width:200, align:'center',lockWidth:true, renderer: actionFixed},
    { title:'ID', name:'id', hidden: true}
];

//分页器
//var mmPaginator = $('#grid-pager').mmPaginator({});
// 搜索表单属性
//var mmFormParams = new MMSearchFormParams("searchForm");

// 表格	
/*var mmGrid = $('#grid-table').mmGrid({
	height: 'auto',
	cols: cols,
	url: '/cms/querySaleCatData.sc?deleteFlag=2',
	fullWidthRows: true,
	autoLoad: true,
	plugins: [mmPaginator,mmFormParams]
});*/

// 表格事件
/*mmGrid.on('cellSelected', function(e, item, rowIndex, colIndex){
	var action = $(e.target).attr("action");
    if(action == "update"){// 逻辑删除
   		YouGou.UI.Dialog.confirm({
   			message : "确定要删除吗？"
   		},function(result){
   			if(result) {
   				updateSaleCat(item.id,1);
            }
   		});
    }
    e.stopPropagation();  //阻止事件冒泡
});*/
//删除
function removeSaleCat(id){
	YouGou.Ajax.doPost({
		successMsg: '删除成功！',
		url: '/cms/removeSaleCat.sc',
	  	data: { "id" : id },
	  	success : function(data){
  			mmGrid.load();
		}
	});
}
//更新状态时使用
function updateSaleCat(id,deleteFlag){
	YouGou.Ajax.doPost({
		successMsg: '更新成功！',
		url: '/cms/saveSaleCat.sc',
	  	data: { "id" : id ,"deleteFlag" : deleteFlag },
	  	success : function(data){
  			mmGrid.load();
		}
	});
}
//查询
function doQuery(){
	mmGrid.load();
}