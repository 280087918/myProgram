//修改节点数据
function updateAreaNode(){
	
	if($("#id").val() == null || $("#id").val() == ""){
		YouGou.UI.Dialog.alert({
			message : '资源不存在，请先增加！'
		});
		return ;
	}
	
	var sort = $('#sort').val();
	if(sort == null || sort == "" || !(/^[1-9]\d*$/.test(sort))){
		YouGou.UI.Dialog.alert({
			message : '排序不能为空,且只能为数字！'
		});
		$("#sort").val("");
		return;
	}
	
	YouGou.UI.Dialog.confirm({
   		message : '确认修改？'
	 },function(result){
	   		if(!result) {
	   			return
	       }else{
	    	   var data={
    				"name":$("#name").val(),
    				"sort":$("#sort").val(),
    				"post":$("#post").val(),
    				"code":$("#code").val(),
    				"id":$("#id").val()
    			};
    			
    			var url="/area/u_updateAreaNode.sc";
    			
    			YouGou.Ajax.doPost({
    				successMsg:'修改成功！',
    				errorMsg:'修改失败！',
    				url: url,
    				data: data,
    				success : function(data) {
    					var result = data.data;
    					result = result.replace(/(^\s*)|(\s*$)/g,'');
    					var selectNode = $('#resourceTree').tree('getSelected');
    					var resultNode = eval("("+result+")");
    					selectNode.text= resultNode.nodeName;
    				    $("#resourceTree").tree("update",selectNode);
    				},
    				error :function(){
    					return
    				}
    			});
	       }
	 });
	
	
	
}

//增加节点
function addAreaNode(){
	
	var node = $('#resourceTree').tree('getSelected');
	if(!node){
		YouGou.UI.Dialog.alert({
			message : '请选择一个节点！'
		});
		return
	}
	
	var newNodeText = $('#name').val();
	if(newNodeText == null || newNodeText == ""){
		YouGou.UI.Dialog.alert({
			message : '地区名称不能为空！'
		});
		return
	}
	
	if(newNodeText == node.text){
		YouGou.UI.Dialog.alert({
			message : '请输入地区名称！'
		});
		return
	}
	
	var sort = $('#sort').val();
	if(sort == null || sort == "" || !(/^[1-9]\d*$/.test(sort))){
		YouGou.UI.Dialog.alert({
			message : '排序不能为空,且只能为数字！'
		});
		$("#sort").val("");
		return
	}
	
	var url="/area/c_addAreaNode.sc";
	var data={
		"name":$("#name").val(),
		"sort":$("#sort").val(),
		"post":$("#post").val(),
		"code":$("#code").val(),
		"parentid":node.id
	};
	YouGou.Ajax.doPost({
		successMsg:'增加成功！',
		errorMsg:'增加失败！',
		url: url,
		data: data,
		success : function(data) {
			var result = data.data;
			result = result.replace(/(^\s*)|(\s*$)/g,'');
			var node = eval("("+result+")");
			if(node.nodeId){
				var nodeData = [{
					id:node.nodeId,
					text:node.nodeName,
					post:node.post
				}];
				
				append(nodeData);
				clearInputValue();
			}
		},
		error :function(){
			return
		}
	});
}

function loadNodeData(nodeid){
	var url = "/area/queryAreaById.sc";
	var data={
		"id":nodeid
	};
	
	ajaxRequest(url,data,function(node){
		//如果获取数据为空  则清空数据
		if(node.length == ""){
			clearInputValue();
			return ;
		}
		$("#no").val(node.nodeStruc);
		$("#id").val(node.nodeId);
		$("#name").val(node.nodeName);
		$("#sort").val(node.nodeOrder);
		if(node.post=="null"){$("#post").val("");}else{$("#post").val(node.post);}
		if(node.code=="null"){$("#code").val("");}else{$("#code").val(node.code);}
	});
	
}

function removeAreaNode(){
	
	var node = $('#resourceTree').tree('getSelected');
	if(!node){
		YouGou.UI.Dialog.alert({
			message : '请选择要删除的节点！'
		});
		return ;
	}
	
	YouGou.UI.Dialog.confirm({
   		message : '确认删除？'
	 },function(result){
	   		if(!result) {
	   			return
	       }else{
	    	   if(node.attributes != null && node.attributes.struc != null && node.attributes.struc == "root"){
	    			YouGou.UI.Dialog.alert({
	    				message : '根目录不能删除！'
	    			});
	    			return
	    		}
	    		
	    		var url = "/area/d_removeAreaNode.sc";
	    		var data={
	    			"areaid":node.id
	    		};
	    		
	    		YouGou.Ajax.doPost({
	    			successMsg:'删除成功！',
	    			errorMsg:'删除失败！',
	    			url: url,
	    			data: data,
	    			success : function() {
	    				clearInputValue();
	    				remove();
	    			},
	    			error :function(){
	    				return
	    			}
	    		});
	       }
	 });
}

function clearInputValue(){
	$("#no").val("");
	$("#id").val("");
	$("#name").val("");
	$("#sort").val("");
	$("#post").val("");
	$("#code").val("");
}

//发达ajax请求
function ajaxRequest(url,reqParam,callback){
	$.ajax({
		  type: 'POST',
		  url: url,
		  data: reqParam,
		  cache: true,
		  dataType: 'json',
		  success: callback
	});
}
