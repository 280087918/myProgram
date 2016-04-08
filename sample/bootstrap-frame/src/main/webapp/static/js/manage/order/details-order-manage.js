/**
* by lijunfnag 20160324
**/
var DetailsOrderManage = function(){
	/**
	* 函数初始化
	**/
	function Init(){
		CopyData();
	};

	 /**
     * 复制数据
     * add by guoran 20151201
     */
    function CopyData() {
        var client = new ZeroClipboard($('.btn-copy'));
        client.on('ready', function(event) {
            client.on('copy', function(event) {
                ZeroClipboard.clearData();
                event.clipboardData.setData('text/plain', $(event.target).data('clipboard-text'));
            }).on('aftercopy', function(event) {
               layer.msg('复制成功', {
                    offset: 0,
                    icon: 1,
                    time: 2000
                });
            });
        }).on('error', function(event) {
            ZeroClipboard.destroy();
        });
    }

	$(function(){
		Init();
	})
	return{

	}
}();