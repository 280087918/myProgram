/*************
	FinReturnDebt
**************/

$(document).ready(function () {
	if (parseInt($('#totalCount1').val()) > 0) {
		var currentPage = parseInt($('#currentPage1').val());
		var pageCount = parseInt($('#pageCount1').val());
		$('#pagination1').jqPaginator({
		    totalPages: pageCount,
		    visiblePages: 10,
		    currentPage: currentPage,
		    onPageChange: function (num, type) {
		    	$('#currentPage1').val(num);
		    }
		});
	}
	
	if (parseInt($('#totalCount2').val()) > 0) {
		var currentPage = parseInt($('#currentPage2').val());
		var pageCount = parseInt($('#pageCount2').val());
		$('#pagination2').jqPaginator({
		    totalPages: pageCount,
		    visiblePages: 10,
		    currentPage: currentPage,
		    onPageChange: function (num, type) {
		    	$('#currentPage2').val(num);
		    }
		});
	}
	
	if (parseInt($('#totalCount3').val()) > 0) {
		var currentPage = parseInt($('#currentPage3').val());
		var pageCount = parseInt($('#pageCount3').val());
		$('#pagination3').jqPaginator({
		    totalPages: pageCount,
		    visiblePages: 10,
		    currentPage: currentPage,
		    onPageChange: function (num, type) {
		    	$('#currentPage3').val(num);
		    }
		});
	}
});

function toRefundDetail() {
	YouGou.UI.Dialog.alert({message:"退款详情页待完善"});
}

/**
 * 点击选项卡查询
 * @param tabQueryFlag
 */
function tabSelectQuery(tabQueryFlag) {
	$('#searchForm1').attr('action', '/finance/returndebt/returnDebtList.sc?tabQueryFlag'+tabQueryFlag);
	$('#searchForm1').submit();
}

/*
 * 点击搜索按钮查询
 */
function doQuery(flag) {
	$('#searchForm1').attr('action', '/finance/returndebt/returnDebtList.sc');
	$('#searchForm1').submit();
}

function exportExcel(flag) {
	$('#searchForm1').attr('action', '/finance/returndebt/returnDebtList.sc');
	$('#searchForm1').submit();
}
