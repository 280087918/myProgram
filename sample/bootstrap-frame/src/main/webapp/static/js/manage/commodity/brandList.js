/*************
	CommodityBrand
**************/
// 操作列动作
var actionFixed = function(val,item,rowIndex){
	var html = [];
	html.push('<a href="javascript:void(0);" action="select">查看</a>&nbsp;&nbsp;');
	html.push('<a href="javascript:void(0);" action="edit">编辑</a>&nbsp;&nbsp;');
	html.push('<a href="javascript:void(0);" action="delete">删除</a>&nbsp;&nbsp;');
    return html.join('');
};

// 列集合
var cols = [
	{ title:'中文名称', name:'brandName', align:'left'},
	{ title:'英文名称', name:'englishName', align:'left'},
	{ title:'品牌小图的url', name:'logoSmallUrl', align:'left'},
	{ title:'品牌大图url', name:'logoNameUrl', align:'left'},
	{ title:'品牌编码', name:'brandNo', align:'left'},
	{ title:'logoLeastUrl', name:'logoLeastUrl', align:'left'},
	{ title:'logoMiddleUrl', name:'logoMiddleUrl', align:'left'},
	{ title:'手机客户端图片', name:'mobilePic', align:'left'},
	{ title:'状态', name:'status', align:'center'},
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
	url: '/commodity/queryBrandData.sc',
	fullWidthRows: true,
	autoLoad: true,
	plugins: [mmPaginator,mmFormParams]
});

// 表格事件
mmGrid.on('cellSelected', function(e, item, rowIndex, colIndex){
	var action = $(e.target).attr("action");
    //编辑
    if(action == "edit"){
    	YouGou.UI.Dialog.alert({message:"编辑"});
    }else if(action == "delete"){// 删除
   		YouGou.UI.Dialog.confirm({
   			message : "确定要删除吗？"
   		},function(result){
   			if(result) {
                removeBrand(item.id);
            }
   		});
    }else if(action == "select"){//查看
    	YouGou.UI.Dialog.alert({message:"查看"});
    }
    e.stopPropagation();  //阻止事件冒泡
});

function removeBrand(id){
	YouGou.Ajax.doPost({
		successMsg: '删除成功！',
		url: '/commodity/removeBrand.sc',
	  	data: { "id" : id },
	  	success : function(data){
  			mmGrid.load();
		}
	});
}

function doQuery(){
	mmGrid.load();
}