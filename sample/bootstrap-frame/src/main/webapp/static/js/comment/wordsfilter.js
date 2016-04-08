var cols = [
    { title:'关键词', name:'filerWords',width:90, align:'center'},
    { title:'添加人', name:'operatior',width:90, align:'center'},
	{ title:'添加时间', name:'createTime' , width:199, align:'center',renderer : YouGou.Util.timeFixed},
	{ title:'类型', name:'type', width:105, align:'center',renderer: function(val,item,rowIndex){
		var str = "";
		if (item.type==0){
			str="屏蔽";
		}else if(item.type==1){
			str="隐藏";
		}else if(item.type==2){
			str="特定词";
		}else if(item.type==3){
			str="特例词";
		}
		return str;
	}},
	{ title:'位置', name:'numberPosition', width:105, align:'center',renderer: function(val,item,rowIndex){
		var str = "";
		if ((item.type==2 || item.type==3) && item.numberPosition==1){
			str="在数字之前";
		}else if((item.type==2 || item.type==3) && item.numberPosition==2){
			str="在数字之后";
		}
		return str;
	}},
	{ title:'操作', name:'oprator' ,width:200, lockWidth:true,align:'center',renderer: function(val,item,rowIndex){
        return	'<a href="javascript:void(0);" action="edit">修改</a>&nbsp;&nbsp;<a href="javascript:void(0);" action="delete">删除</a>'
    }}
];

//分页器
var mmPaginator = $('#grid-pager').mmPaginator({});

// 搜索表单属性
var mmFormParams = new MMSearchFormParams("queryform");

// 表格	
var mmGrid = $('#grid-table').mmGrid({
	height: 'auto',
	cols: cols,
	url: '/wordsfilter/getWordsFilter.sc',
	indexCol: true,
	indexColWidth: 45,
	fullWidthRows: true,
	autoLoad: true,
	noDataText: '没有数据',
	plugins: [
	    mmFormParams , mmPaginator
	]
});
        
J('#startTime').calendar({maxDate:'#endTime',format:'yyyy-MM-dd HH:mm:ss'}); 
J('#endTime').calendar({minDate:'#startTime',format:'yyyy-MM-dd HH:mm:ss'});

mmGrid.on('cellSelected', function(e, item, rowIndex, colIndex){
	var action = $(e.target).attr("action");
	//修改
	if(action == 'edit'){
		update(item.id);
	//删除
	}else if(action == 'delete'){
		del(item.id);
	} 
	e.stopPropagation();
});

 function querylist(){
    mmGrid.load();
 }
 
 $('#type').change(function(){
	if(this.value != '' && (this.value == 0 || this.value == 1)){
		$('#numposspan').hide();
	}else{
		$('#numposspan').show();
	}
 });
 
 $('#add-quick').click(function(){
	 YouGou.UI.progressLoading();
	 	$.ajax({
	 	       type: "post",
	 	       url: "/wordsfilter/toAddWordsFilter.sc",
	 	       async: false,
	 	       success : function(data) {
	 	    	YouGou.UI.progressStop();
	 			if (data) {
	 				YouGou.UI.Dialog.show({
	     			   title: "添加关键词",
	     			   cssClass: 'detail-dialog',
	     			   message : data.replace(/(\n)+|(\r\n)+/g, ""),
	     			   buttons: [{
	     				   label: '关闭',
	     				   action: function(dialog) {
	     					   dialog.close();
	     				   }
	     			   },{
	     				   label: '回复',
	     				   cssClass: 'btn-yougou',
	     				   action: function(dialog) {
	     					  addWords(dialog);
	     				   }
	     			   }],
	     			   onshown : function(){
	     				   	$('#wztr').hide();
		  	 				$("input[name=type]").click(function(){
		  	 					if(this.value == 0 || this.value == 1){
		  	 						$('#wztr').hide();
		  	 						$("input[name=numberPosition]").removeAttr("checked");
		  	 						$("#numberPosition_stip").html("");
		  	 					}else{
		  	 						$('#wztr').show();
		  	 					}
		  	 				});
	
		  	 				$("#filerWords_1").click(function(){
		  	 					if($(this).val()=="请填写关键词,长度不得超过40个字符(20个汉字)."){
		  	 						$(this).val("");
		  	 						$("#wordsContent_stip").html("");
		  	 						$(this).css({'color':'#000'});
		  	 					}
		  	 				});
	
		  	 				$("#filerWords_1").blur(function(){
		  	 					if($(this).val()==""){
		  	 						$(this).val("请填写关键词,长度不得超过40个字符(20个汉字).");
		  	 						$("#wordsContent_stip").html("");
		  	 						$(this).css({'color':'#999'});
		  	 					}
		  	 				});
	     			   }
	     		   });
	 			}
	 		  }
	 	   }); 
 });
 
 /** 
  *textarea文本框输入字数检测 
  *textareaId：textarea的dom标识 
  *maxLen：要求的最大字节长度 
  */  
  function chkTextareaLen(textareaId,counterId,maxLen) {  
      try{  
          var textareaValue = document.getElementById(textareaId).value;  
          var curLen = 0,substrLen = 0;  
            
          for (var i=0; i<textareaValue.length; i++) {    
              if (textareaValue.charCodeAt(i)>127 || textareaValue.charCodeAt(i)==94) {    
                  curLen += 2;    
              } else {  
                  curLen ++;    
              }   
        
              if(curLen > maxLen){  
                  substrLen = i;  
                  break;  
              }  
          }  
            
          if(curLen > maxLen) {  
              if(substrLen == 0) substrLen = maxLen;  
              document.getElementById(textareaId).value = textareaValue.substring(0,substrLen);  
              //alert("文本长度不能大于"+maxLen+"个字节(中文占2个字节)");   
          }else{  
              document.getElementById(counterId).innerHTML = "您还可以输入"+(maxLen - curLen)+"字";  
          }  
      }catch(e){}  
  }  
 
  function add(dialog){	
		var filerWords=$("#filerWords_1").val();
		var type = $("input[name=type]:checked").val();
		var numberPosition = $("input[name=numberPosition]:checked").val();
		if(type== 2 || type==3){
			if(numberPosition == null || numberPosition == undefined){
				$("#numberPosition_stip").html("请选择数字位置");
				return;
			}
		}else{
			numberPosition = 0;
		}
		YouGou.Ajax.doPost({
	  		  successMsg: "新增关键词成功!",
	  		  url: "/wordsfilter/c_commodityCommentwords.sc",
	  		  data: {"filerWords":filerWords,
					"type":type,
					"numberPosition":numberPosition
					},
	  		  success : function() {
	  			  $("#filerWords_1").val("");
	  			  mmGrid.load();
	  			  dialog.close();
	  		  }
	     });
	}

	function addWords(dialog){	
		var filerWords=$("#filerWords_1").val();
		if($("#filerWords_1").val()=="" || $("#filerWords_1").val()=="请填写关键词,长度不得超过40个字符(20个汉字).")
		{
			$("#wordsContent_stip").html("关键词不能为空！");
			$("#filerWords_1").focus();
			return;
		}
		$.ajax( {
			type : "POST",
			url : "/wordsfilter/checkWordsFilter.sc",
			data : {"filerWords" : filerWords},
			success : function(data) {
				if (data == "1") {
					add(dialog);
				} else if (data == "2") {
					$("#wordsContent_stip").html("关键词已存在");
					return;
				} 
			}
		});
	}
 
 function update(id) {
 	YouGou.UI.progressLoading();
 	$.ajax({
 	       type: "post",
 	       url: "/wordsfilter/toUpdateWordsFilter.sc",
 	       data: "id="+id,
 	       async: false,
 	       success : function(data) {
 	    	YouGou.UI.progressStop();
 			if (data) {
 				YouGou.UI.Dialog.show({
     			   title: "修改过滤关键词",
     			   cssClass: 'detail-dialog',
     			   message : data.replace(/(\n)+|(\r\n)+/g, ""),
     			   buttons: [{
     				   label: '关闭',
     				   action: function(dialog) {
     					   dialog.close();
     				   }
     			   },{
     				   label: '确定',
     				   cssClass: 'btn-yougou',
     				   action: function(dialog) {
     					  updateWords(dialog);
     				   }
     			   }],
     			   onshown : function(){
     				   $('#wztr').hide();
     	 				var types = $('#Oldtype').val();
     	 				if(types == 0 || types == 1){
     	 					$('#wztr').hide();
     	 				}else if(types == 2 || types == 3){
     	 				 	$('#wztr').show();
     	 				}
     	 				$("input[name=type]").click(function(){
     	 					if(this.value == 0 || this.value == 1){
     	 						$('#wztr').hide();
     	 						$("input[name=numberPosition]").removeAttr("checked");
     	 						$("#numberPosition_stip").html("");
     	 					}else{
     	 						$('#wztr').show();
     	 					}
     	 				});
     	 				$("#filerWords_1").click(function(){
     	 					if($(this).val()=="请填写关键词,长度不得超过40个字符(20个汉字)."){
     	 						$(this).val("");
     	 						$(this).css({'color':'#000'});
     	 					}
     	 				});
     	 				$("#filerWords_1").blur(function(){
     	 					if($(this).val()==""){
     	 						$(this).val("请填写关键词,长度不得超过40个字符(20个汉字).");
     	 						$(this).css({'color':'#999'});
     	 					}
     	 				});
     			   }
     		   });
 			}
 		  }
 	   }); 
 }
 
 function updateThis(dialog){	
		var filerWords=$("#filerWords_1").val();
		var type = $("input[name=type]:checked").val();
		var numberPosition = $("input[name=numberPosition]:checked").val();
		if(type== 2 || type==3){
			if(numberPosition == null || numberPosition == undefined){
				$("#numberPosition_stip").html("请选择数字位置");
				return;
			}
		}else{
			numberPosition = 0;
		}
		var id=$("#wordId").val();
		YouGou.Ajax.doPost({
	  		  successMsg: "修改关键词成功!",
	  		  url: "/wordsfilter/u_commodityCommentWordsFilter.sc",
	  		  data: {"id":id,
					"filerWords":filerWords,
					"type":type,
					"numberPosition":numberPosition
					},
	  		  success : function() {
	  			  $("#filerWords_1").val("");
	  			  mmGrid.load();
	  			  dialog.close();
	  		  }
	     });
	}

	function updateWords(dialog){	
		var filerWords=$("#filerWords_1").val();
		if($("#filerWords_1").val()=="" || $("#filerWords_1").val()=="请填写关键词,长度不得超过40个字符(20个汉字)."){
			$("#wordsContent_stip").html("关键词不能为空！");
			$("#filerWords_1").focus();
			return;
		}
		var type = $("input[name=type]:checked").val();
		var numberPosition = $("input[name=numberPosition]:checked").val();
		if(numberPosition == null || numberPosition == undefined){
			numberPosition = 0;
		}
		// 若修改的值没变，不提交修改
		var Oldtype = $('#Oldtype').val();
		var OldfilerWords = $('#OldfilerWords').val();
		var OldnumberPosition = $('#OldnumberPosition').val();
		if(filerWords == OldfilerWords && Oldtype == type && numberPosition == OldnumberPosition){
			dialog.close();
		}else if(filerWords != OldfilerWords){
			$.ajax( {
				type : "POST",
				url : "/wordsfilter/checkWordsFilter.sc",
				data : {"filerWords" : filerWords},
				success : function(data) {
					if (data == "1") {
						updateThis(dialog);
					} else if (data == "2") {
						$("#wordsContent_stip").html("关键词已存在");
					} 
				}
			});
		}else if(filerWords == OldfilerWords){
			updateThis(dialog);
		}
	}
 
 function del(id){
	 YouGou.UI.Dialog.confirm({
	   		message : '你确定要删除记录吗'
	 },function(result){
	   		if(result) {
	   			YouGou.Ajax.doPost({
		    		  successMsg: "删除关键词成功!",
		    		  url: "/wordsfilter/d_commodityCommentWordsFilter.sc",
		    		  data: {"id":id},
		    		  success : function() {
						   mmGrid.load();
		    		  }
		       });
	       }
     });
}