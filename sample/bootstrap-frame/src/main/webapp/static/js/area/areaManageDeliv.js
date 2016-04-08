//初始化树控件  
function onloadResourceTree(url,checkbox){
	$('#resourceTree').tree({
		checkbox: (checkbox == null) ? true : checkbox,
		url:url,
		onClick: function(node) {
			loadNodeData(node.id);
		},
		onContextMenu: function(e, node) {
			e.preventDefault();
			$('#resourceTree').tree('select', node.target);
			$('#mm').menu('show', {
				left: e.pageX,
				top: e.pageY
			});
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
		if(node.nodeId != "1"){
			$("#cashShow").removeAttr("style");
			$(".btn_yes").removeAttr("style");
			if(node.isUsable==1){
				$("#isUsable").prop("checked",true);
			}else if(node.isUsable==0){
				$("#isNoUsable").prop("checked",true);
			}
			if(node.isSupportInvoice==1){
				$("#isSupportInvoice").prop("checked",true);
			}else if(node.isSupportInvoice==0){
				$("#isNoSupportInvoice").prop("checked",true);
			}
			if(node.isCashOnDelivy==1){
				$("#butTheServiceArea").removeAttr("readonly");
				$("#notServiceArea").removeAttr("readonly");
				$("#pcplatform").prop("checked",true);
				$("#isCashOnDelivy").val("1");
				if(node.isCashOnDelivyByMobile==1){
					$("#wireplatform").prop("checked",true);
					$("#isCashOnDelivyByMobile").val("1");
				}else if(node.isCashOnDelivyByMobile==0){
					$("#wireplatform").prop("checked",false);
					$("#isCashOnDelivyByMobile").val("0");
				}
			}else if(node.isCashOnDelivy==0){
				$("#butTheServiceArea").attr("readonly","true");
				$("#notServiceArea").attr("readonly","true");
				$("#pcplatform").prop("checked",false);
				$("#isCashOnDelivy").val("0");
				if(node.isCashOnDelivyByMobile==1){
					$("#butTheServiceArea").removeAttr("readonly");
					$("#notServiceArea").removeAttr("readonly");
					$("#wireplatform").prop("checked",true);
					$("#isCashOnDelivyByMobile").val("1");
				}else if(node.isCashOnDelivyByMobile==0){
					$("#wireplatform").prop("checked",false);
					$("#isCashOnDelivyByMobile").val("0");
				}
			}
		}else{
			$("#cashShow").attr("style","display:none;");	
			$(".btn_yes").attr("style","display:none;");	
		}
		
		if(node.level == 3){
			$("#cashDelivy").removeAttr("style");
			$(".btn_yes").removeAttr("style");
			if(node.butTheServiceArea=="null"){$("#butTheServiceArea").attr("value","");}else{$("#butTheServiceArea").attr("value",node.butTheServiceArea);}
			if(node.notServiceArea=="null"){$("#notServiceArea").attr("value","");}else{$("#notServiceArea").attr("value",node.notServiceArea);}
		}else{
			$("#cashDelivy").attr("style","display:none;");
		}
		
		var selectNode = $('#resourceTree').tree('getSelected');
		selectNode.iconCls= node.iconCls;	
		$("#resourceTree").tree("update",selectNode);
	});
	
}

function clearInputValue(){
	$("#id").attr("value","");
	$("#name").attr("value","");
	$("#sort").attr("value","");
	$("#post").attr("value","");
	$("#code").attr("value","");
}

//发达ajax请求
function ajaxRequest(url,reqParam,callback){
	$.ajax({
		  type: 'POST',
		  url: url,
		  data: reqParam,
		  cache: true,
		  dataType:'json',
		  success: callback
	});
}

//修改节点数据
function update2AreaNode(){
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
    				"butTheServiceArea":$("#butTheServiceArea").val(),
    				"notServiceArea":$("#notServiceArea").val(),
    				"isUsable":$("input[name='isUsable']:checked").val(),
    				"isSupportInvoice":$("input[name='isSupportInvoice']:checked").val(),
    				"isCashOnDelivy":$("#isCashOnDelivy").val(),
    				"isCashOnDelivyByMobile":$("#isCashOnDelivyByMobile").val(),
    				"id":$("#id").val()		
    			};
    			
    			var url="/area/u_updateAreaNodeForDely.sc";
    			YouGou.Ajax.doPost({
    				  url: url,
    				  data: data,
    				  success : function(data) {
    					var result = data.data;
     					result = result.replace(/(^\s*)|(\s*$)/g,'');
    					var selectNode = $('#resourceTree').tree('getSelected');
    					var resultNode = eval("("+result+")");
    					selectNode.iconCls= resultNode.iconCls;					
    					var childNodes = $("#resourceTree").tree("getData",selectNode.target);
    					var arrChild = childNodes.children;
    					for(var i=0;i<arrChild.length;i++){
    						arrChild[i].iconCls= resultNode.iconCls;
    						$("#resourceTree").tree("update",arrChild[i]);	
    						
    						var arrChildNodes = $("#resourceTree").tree("getData",arrChild[i].target);
    						 if(arrChildNodes.children != null){
    						 	var arrChildmore = arrChildNodes.children;
    						 	for(var j=0;j<arrChildmore.length;j++){
    						 		arrChildmore[j].iconCls= resultNode.iconCls;
    						 		$("#resourceTree").tree("update",arrChildmore[j]);	
    						 	}
    						 }
    					}
    					var parentNode = $("#resourceTree").tree("getParent",selectNode.target);
    					if(parentNode != null){
    						var url = "/area/queryAreaById.sc";
    						var data={
    							"id":parentNode.id
    						};
    						ajaxRequest(url,data,function(spresultNode){
    							//更新第二父节点
    							parentNode.iconCls= spresultNode.iconCls;
    							parentNode.text = spresultNode.nodeName;
    							$("#resourceTree").tree("update",parentNode);
    						});	
    						
    						var gtParentNode = $("#resourceTree").tree("getParent",parentNode.target);
    						if(gtParentNode != null){
    							var url = "/area/queryAreaById.sc";
    							var data={
    								"id":gtParentNode.id
    							};
    							ajaxRequest(url,data,function(gtresultNode){
    								//更新第一节点
    								gtParentNode.iconCls= gtresultNode.iconCls;
    								gtParentNode.text = gtresultNode.nodeName;
    								$("#resourceTree").tree("update",gtParentNode);
    							});
    						}
    					}
    					$("#resourceTree").tree("update",selectNode);
    					if(resultNode.isUsable==1){
    						$("#isUsable").prop("checked",true);
    					}else if(resultNode.isUsable==0){
    						$("#isNoUsable").prop("checked",true);
    					}
    					if(resultNode.isSupportInvoice==1){
    						$("#isSupportInvoice").prop("checked",true);
    					}else if(resultNode.isSupportInvoice==0){
    						$("#isNoSupportInvoice").prop("checked",true);
    					}
    					if(resultNode.isCashOnDelivy==1){
    						$("#pcplatform").prop("checked",true);
    						$("#isCashOnDelivy").val("1");
    						
    						if(resultNode.isCashOnDelivyByMobile==1){
    							$("#wireplatform").prop("checked",true);
    							$("#isCashOnDelivyByMobile").val("1");
    						}else if(resultNode.isCashOnDelivyByMobile==0){
    							$("#wireplatform").prop("checked",false);
    							$("#isCashOnDelivyByMobile").val("0");
    						}
    					}else if(resultNode.isCashOnDelivy==0){
    						$("#pcplatform").prop("checked",false);
    						$("#isCashOnDelivy").val("0");
    						
    						if(resultNode.isCashOnDelivyByMobile==1){
    							$("#wireplatform").prop("checked",true);
    							$("#isCashOnDelivyByMobile").val("1");
    						}else if(resultNode.isCashOnDelivyByMobile==0){
    							$("#wireplatform").prop("checked",false);
    							$("#isCashOnDelivyByMobile").val("0");
    						}
    					}
    				  },
    				  error :function(){
    						return
    					}
    			});	
	       }
	 });


}

function importcvs(){
	 YouGou.UI.progressLoading();
	 	$.ajax({
	       type: "post",
	       url: "/area/import_area.sc",
	       async: false,
	       success : function(data) {
	    	    YouGou.UI.progressStop();
				if (data) {
	 				YouGou.UI.Dialog.show({
	 					title: "导入区域",
	 					message : data.replace(/(\n)+|(\r\n)+/g, ""),
	 					buttons: [{
	 	    				   label: '关闭',
	 	    				   action: function(dialog) {
	 	    					   dialog.close();
	 	    				   }
	 	    			   }]
	 				});
				}
	 	    }
	   });
}

function downLoadAreaData(){
	location.href='/area/downLoadAreaData.sc';
}

$("input[name='pcplatform']").click(function() {
	var list = $("input[name='pcplatform']");
	for(var i=0 ;i< list.length ;i++){
		if(list[i].checked){		   
		    $("#butTheServiceArea").removeAttr("readonly");
	   		$("#notServiceArea").removeAttr("readonly");
	   		$("#isCashOnDelivy").val("1");
		}else{
	  	 	$("#isCashOnDelivy").val("0");	   
		}
	}
});

$("input[name='wireplatform']").click(function() {
	var list = $("input[name='wireplatform']");
	for(var i=0 ;i< list.length ;i++){
		if(list[i].checked){		   
		    $("#butTheServiceArea").removeAttr("readonly");
	   		$("#notServiceArea").removeAttr("readonly");
	   		$("#isCashOnDelivyByMobile").val("1");
		}else{
	  	 	$("#isCashOnDelivyByMobile").val("0");	   
		}
	}
});