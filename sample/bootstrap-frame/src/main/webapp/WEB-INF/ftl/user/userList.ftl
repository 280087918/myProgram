<#-- 分销包页面  -->
<#-- =========head========-->
<#assign head>
</#assign>

<#-- =========footer===== -->
<#assign footer>
	<!-- this page -->
	<script src="/static/js/manage/commodity/userList.js"></script>
</#assign>

<#-- =========body======= -->
<#assign body>
<div id="girdContent" class="row">
	<div class="col-xs-12">
        <button class="btn btn-yougou" onclick="showForm();">
        	<i class="ace-icon fa fa-pencil align-top bigger-125"></i>
        	新增
        </button>
        <div class="row">
			<div class="col-xs-12">
				<form class="form-horizontal" id="searchForm">
		            <fieldset>
		                <div class="row">
		                    <div class="col-sm-12">
		                        <div class="form-group">
		                        	<label for="operator" class="col-sm-1 control-label col-xs-12 no-padding-right">分销包名称：</label>
									<div class="col-sm-1">
									    <input class="form-control input-sm" name="bagName" id="bagName" value="" type="text">
									</div>
									<label for="memberLevel" class="col-sm-1 control-label col-xs-12 no-padding-right"> 状态：</label>
									<div class="col-sm-1">
									    <select class="form-control input-sm" name="status" id="status">
							                <option value="0">全部</option>
											<option value="0">启用</option>
											<option value="1">停用</option>
							            </select>
									</div>
		                        	<label for="operator" class="col-sm-1 control-label col-xs-12 no-padding-right"> 创建时间：</label>
									<div class="col-sm-4">
									    <input type="text" name="startTime" id="startTime" readonly="readonly" value="" class="input-medium">
									    <label>至</label>
									    <input type="text" name="endTime" id="endTime" readonly="readonly" value="" class="input-medium">
									</div>
									<div class="col-sm-1">
			                            <input type="button" value="搜索" class="btn btn-yougou" onclick="doQuery();"/>
									</div>
		                        </div>
		                    </div>
		                </div>
		            </fieldset>
		        	</form>
			</div>
		</div>
	</div>
	
	<div class="space-6"></div>
	<table id="grid-table" class="mmg"></table>
    <div id="grid-pager" style="text-align:right;" class=""></div>
</div>

<!-- 分销包新增或者修改页面 -->
<div class="row">
	<div class="col-xs-12">
		<div id="packageNavbar" class="hide message-navbar clearfix">
			<div class="message-bar">
				<div class="message-toolbar">
					<button type="button" class="btn btn-xs btn-white btn-yougou" onclick="saveBag();">
						<i class="ace-icon fa fa-floppy-o bigger-175"></i>
						<span class="bigger-110">保存</span>
					</button>
					<button type="button" class="btn btn-xs btn-white btn-yougou" onclick="hideForm();">
						<i class="ace-icon fa fa-times bigger-175 orange2"></i>
						<span class="bigger-110">取消</span>
					</button>
				</div>
			</div>
			<div>
				<div class="messagebar-item-left">
					<a href="javascript:void(0);" class="btn-back-message-list" onclick="hideForm();">
						<i class="ace-icon fa fa-arrow-left bigger-110 middle blue"></i>
						<b class="middle bigger-110">返回列表</b>
					</a>
				</div>
			</div>
		</div>
		<form id="packageForm" class="hide form-horizontal message-form col-xs-12">
			<input type="hidden" name="id" id="id" value="${id!""}"/>
			<div>
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right" for="form-field-recipient"><font color="red">*</font>分销包名称：</label>
					<div class="col-sm-2">
						 <input type="text" id="bagName" name="bagName" placeholder="请输入分销包名称" />
					</div>
				</div>
				<div class="hr hr-10 dotted"></div>
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right" for="form-field-subject"><font color="red">*</font>分销包小图：</label>
					<div class="col-sm-6 col-xs-12">
                        <div id="filePicker1">上传</div>
					</div>
					<input type="hidden" name="Bag_small_pic" id="Bag_small_pic" value="" />
				</div>
				<div class="form-group">
					<div class="col-sm-3"></div>
					<div class="col-sm-9 img1">
					</div>
				</div>
				<div class="hr hr-10 dotted"></div>
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right" for="form-field-recipient"><font color="red">*</font>分销包大图：</label>
					<div class="col-sm-3">
						<div id="filePicker2">上传</div>
					</div>
					<input type="hidden" name="Bag_big_pic" id="Bag_big_pic" value="" />
				</div>
				<div class="form-group">
					<div class="col-sm-3"></div>
					<div class="col-sm-9 img2">
					</div>
				</div>
				<div class="hr hr-10 dotted"></div>
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right" for="form-field-recipient"><font color="red">*</font>分销包说明：</label>
					<div class="col-sm-2">
						<textarea id="comments" name="comments" maxlength="100" class="autosize-transition form-control" style="overflow: hidden; word-wrap: break-word; resize: horizontal; height: 65px;"></textarea>
						<span style="color:red" id="addressMsg"></span>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
</#assign>

<#-- =========引入模板======= -->
<#include "/include/pageBuilder.ftl" />
