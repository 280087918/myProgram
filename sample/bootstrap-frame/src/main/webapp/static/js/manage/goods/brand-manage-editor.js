/**
* by lijunfang 20160324
**/
var BandManageEditor = function(){
	/**
	* 初始化函数
	**/
	function Init(){
		
		FunZtreen();
		$('.img-box').each(function(index,ele){
			var arr = [],width,height,
				imgId = '#'+$(ele).attr('id'),
				btnId = '#'+$(ele).siblings('.file-btn').attr('id'),
				sizeString = $(ele).find('.img-size').html();
			if(sizeString){
				arr = sizeString.split('*');
				width = arr[0];
				height = arr[1];
				Upload.fileUpload(imgId,btnId,width,height);
				console.log(imgId);
			}else{
				Upload.fileUpload(imgId,btnId,118,118);
			}
		})	
	};

	/**
	* ztree函数
	**/
	function FunZtreen(){
		var setting = {
	        check: {
	            enable: true
	        },
	        view: {
	            showLine: true
	        },
	        data: {
	            key: {
	                title: "t"
	            },
	            simpleData: {
	                enable: true
	            }
	        }
	    },
	    nodes = [
	            {id:1, pId:0, name: "全部"},
	            {id:11, pId:1, name: "配饰"},
	            {id:12, pId:1, name: "运动"},
	            {id:13, pId:11, name: "流行配饰"},
	            {id:14, pId:11, name: "流行配饰"},
	            {id:15, pId:12, name: "流行运动"},
	            {id:16, pId:12, name: "流行运动"}
	        ];
	    $(function () {
	            pTree = $.fn.zTree.init($("#elementList"), setting, nodes);
	    })
	};

	/**
	* upload 函数
	**/

	$(function(){
		Init();
	});
	return{

	}
}();
