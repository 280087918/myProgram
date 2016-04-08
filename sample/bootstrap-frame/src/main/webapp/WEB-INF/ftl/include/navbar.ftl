<#if hideMenuAndHeader?? && hideMenuAndHeader>
<#else>
<!-- #section:basics/navbar.layout -->
<div id="navbar" class="navbar navbar-default navbar-fixed-top">
	<script type="text/javascript">
		try{ace.settings.check('navbar' , 'fixed')}catch(e){}
	</script>

	<div class="navbar-container" id="navbar-container">
		<!-- #section:basics/sidebar.mobile.toggle -->
		<button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
			<span class="sr-only">Toggle sidebar</span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
		</button>

		<!-- /section:basics/sidebar.mobile.toggle -->
		<div class="navbar-header pull-left" style="width: 179px;">
			<!-- #section:basics/navbar.layout.brand -->
			<a href="javascript:void(0);" class="navbar-brand">
				<#--<small>
					<i class="fa fa-user"></i>
					xxxx管理系统
				</small>-->
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;LOGO
				<!--<img src="/static/images/logo-v.png" alt="logo" style="display: block;max-width: 100%;height: auto;" class="img-responsive">-->
			</a>
			<!-- /section:basics/navbar.layout.brand -->
		</div>
		<div class="navbar-header">
			<ul class="nav navbar-nav">
				<li class="open"><a href="javascript:void(0);">权限系统</a></li>
				<li><a href="javascript:void(0);">订单系统</a></li>
				<li><a href="javascript:void(0);">仓储系统</a></li>
				<li><a href="javascript:void(0);">会员系统</a></li>
				<!--
                <li class="open"><a href="javascript:void(0);">微分销</a></li>
				<li><a href="${omsHost!""}/yitiansystem/systemmgmt/mainmanage/mainFrame.sc?root_struc=root-5">订单</a></li>
				<li><a href="${omsHost!""}/yitiansystem/systemmgmt/mainmanage/mainFrame.sc?root_struc=root-6">客服</a></li>
				<li><a href="${wmsHost!""}/yitiansystem/systemmgmt/mainmanage/mainFrame.sc?root_struc=root-12">WMS</a></li>
				<li><a href="${fmsHost!""}/yitiansystem/systemmgmt/mainmanage/mainFrame.sc?root_struc=root-10">财务</a></li>
				<li><a href="${fmsHost!""}/yitiansystem/systemmgmt/mainmanage/mainFrame.sc?root_struc=root-13">报表</a></li>
				<li><a href="${outsideHost!""}/yitiansystem/systemmgmt/mainmanage/mainFrame.sc?root_struc=root-18">外部平台</a></li>
				<li><a href="${mmsHost!""}/yitiansystem/systemmgmt/mainmanage/mainFrame.sc?root_struc=root-17">招商</a></li>
				<li><a href="${dmsHost!""}/yitiansystem/systemmgmt/mainmanage/mainFrame.sc?root_struc=root-22">分销</a></li>
				<li><a href="${tmsHost!""}/index.sc">工单</a></li>
				-->
			</ul>
		</div>

		<!-- Page Breadcrumb -->
    	<#include "/include/settingbox.ftl" >
    	<!-- /Page Breadcrumb -->
		
		<!-- #section:basics/navbar.dropdown -->
		<div class="navbar-buttons navbar-header pull-right" role="navigation">
			<ul class="nav ace-nav">
				<li class="green hide">
					<a data-toggle="dropdown" class="dropdown-toggle" href="javascript:void(0);">
						<i class="ace-icon fa fa-envelope icon-animated-vertical"></i>
						<span class="badge badge-success">5</span>
					</a>

					<ul class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
						<li class="dropdown-header">
							<i class="ace-icon fa fa-envelope-o"></i>
							5 个未读消息
						</li>

						<li class="dropdown-content">
							<ul class="dropdown-menu dropdown-navbar">
								<li>
									<a href="javascript:void(0);" class="clearfix">
										<img src="/assets/ace/avatars/avatar.png" class="msg-photo" alt="Alex's Avatar" />
										<span class="msg-body">
											<span class="msg-title">
												<span class="blue">张三:</span>
												内容xxxxxxxxxx
											</span>

											<span class="msg-time">
												<i class="ace-icon fa fa-clock-o"></i>
												<span>1分钟之前</span>
											</span>
										</span>
									</a>
								</li>

								<li>
									<a href="javascript:void(0);" class="clearfix">
										<img src="/assets/ace/avatars/avatar3.png" class="msg-photo" alt="Susan's Avatar" />
										<span class="msg-body">
											<span class="msg-title">
												<span class="blue">李四:</span>
												内容aaaaaaaaaaaaaaaaaaaaaaaaaaa
											</span>

											<span class="msg-time">
												<i class="ace-icon fa fa-clock-o"></i>
												<span>20分钟之前</span>
											</span>
										</span>
									</a>
								</li>
							</ul>
						</li>

						<li class="dropdown-footer">
							<a href="">
								查看全部消息
								<i class="ace-icon fa fa-arrow-right"></i>
							</a>
						</li>
					</ul>
				</li>

				<!-- #section:basics/navbar.user_menu -->
				<li class="light-blue">
					<a data-toggle="dropdown" href="javascript:void(0);" class="dropdown-toggle">
						<img class="nav-user-photo" src="/static/images/user.png" />
						<span class="user-info">
							<small>欢迎您,</small>admin
						</span>
						<i class="ace-icon fa fa-caret-down"></i>
					</a>

					<ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
						<li class="hide">
							<a href="javascript:void(0);">
								<i class="ace-icon fa fa-cog"></i>
								设置
							</a>
						</li>

						<li class="hide">
							<a href="javascript:void(0);">
								<i class="ace-icon fa fa-user"></i>
								个人信息
							</a>
						</li>

						<li class="divider"></li>

						<li>
							<a href="/logout">
								<i class="ace-icon fa fa-power-off"></i>
								退出
							</a>
						</li>
					</ul>
				</li>

				<!-- /section:basics/navbar.user_menu -->
			</ul>
		</div>
		<!-- /section:basics/navbar.dropdown -->
	</div><!-- /.navbar-container -->
</div>
<!-- /section:basics/navbar.layout -->
</#if>