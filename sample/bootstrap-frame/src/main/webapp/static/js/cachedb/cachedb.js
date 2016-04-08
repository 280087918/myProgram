var cols = [
    { title:'key', name:'key',width:816, align:'left'},
	{ title:'键值类型', name:'dataType', width:80, align:'left'},
	{ title:'生命周期', name:'expire', width:80, align:'left',renderer: function(val,item,rowIndex){
		var liveTime = "";
        if(item.expire != null){
        	if(item.expire < 0){
        		liveTime = "永久";
        	}else{
        		liveTime = item.expire+" <span class='red'>s</span>";
        	}
        }
        return liveTime;
    }},
    { title:'操作', name:'oprator' ,width:80, align:'left',renderer: function(val,item,rowIndex){
        var rehtm = '<a href="javascript:void(0);" id="id-btn-dialog-detail" onclick="del(\''+item.key+'\');">删除</a>';
        return rehtm;
    }}
];

// 搜索表单属性
var mmFormParams = new MMSearchFormParams("searchForm");

// 表格	
var mmGrid = $('#grid-table').mmGrid({
	height: 500,
	cols: cols,
	indexCol: true,
	indexColWidth: 45,
	fullWidthRows: true,
	autoLoad: true,
	noDataText: '没有数据',
	plugins: [
	    mmFormParams
	]
});

function queryData(){
	YouGou.UI.progressLoading();
	$.ajax( {
		type : "POST",
		url : "/cacahe/search.sc",
		async : false,
		data : {"keyPattern" : $('#keyPattern').val()},
		success : function(data) {
			data = JSON.parse(data);
			if(data.items.length == 0 || data.items.length < 20){
				mmGrid.setAutoHeight();// 复位高度
			}
			mmGrid.opts.items = data.items;
			mmGrid.load();
			$('#tatal-span').html('总记录数:'+data.items.length);
		}
	});
	YouGou.UI.progressStop();
}

$('#querylist').click(function(){
	queryData();
	$('#grid-table-div').removeClass('hide');
});

function del(key){
	YouGou.UI.Dialog.confirm({
		message : "你确定要删除记录吗?"
	},function(result){
		if(result) {
			YouGou.Ajax.doPost({
	    		  successMsg: "删除成功!",
	    		  url: "/cacahe/del.sc",
	    		  data: {"key":key},
	    		  success : function() {
					  queryData();
	    		  }
	       });
        }
	});
}
