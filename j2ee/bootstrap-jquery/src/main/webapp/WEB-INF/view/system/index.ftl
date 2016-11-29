<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>1b2a</title>
<link rel="stylesheet" href="${basePath !}/asset/css/bootstrap.min.css">
<style type="text/css">
	 body{
		background: linear-gradient(to right, #2c3b4e, #4a688a, #2c3b4e);
	 }
	 .main{
		padding: 10em 0;
	  }
	 .progress{
	 	  height: 25px;
	 	  background: #262626;
	 	  padding: 5px;
	 	  overflow: visible;
	 	  border-radius: 20px;
	 	  border-top: 1px solid #000;
	 	  border-bottom: 1px solid #7992a8;
	 	  margin-top: 0px;
		  margin-bottom: 50px;
	 }
	 .progress .progress-bar{
	 	  border-radius: 20px;
	 	  position: relative;
	 	  animation: animate-positive 2s;
	 }
	 .progress .progress-value{
	 	  display: block;
	 	  padding: 3px 7px;
	 	  font-size: 13px;
	 	  color: #fff;
	 	  border-radius: 4px;
	 	  background: #191919;
	 	  border: 1px solid #000;
	 	  position: absolute;
	 	  top: -40px;
	 	  right: -10px;
	 }
	 .progress .progress-value:after{
	 	  content: "";
	 	  border-top: 10px solid #191919;
	 	  border-left: 10px solid transparent;
	 	  border-right: 10px solid transparent;
	 	  position: absolute;
	 	  bottom: -6px;
	 	  left: 26%;
	 }
	 .progress-bar.active{
	 	  animation: reverse progress-bar-stripes 0.40s linear infinite, animate-positive 2s;
	 }
	 @-webkit-keyframes animate-positive{
	 	  0% { width: 0; }
	 }
	 @keyframes animate-positive{
	 	  0% { width: 0; }
	 }
</style>
<script type="text/javascript" src="${basePath !}/asset/js/jquery-3.1.1.min.js" ></script>
<script type="text/javascript" src="${basePath !}/asset/js/bootstrap.min.js" ></script>
</head>
<body>
<div style="text-align:center;margin:2px 0; font:normal 25px 'MicroSoft YaHei';">
	<p class="bg-primary">欢迎光临本站</p>

	<div class="navbar-header">
       <a class="navbar-brand btn_login" href="#">登录</a>
    </div>
</div>
<div class="main">
	<div class="container">
		<div class="row">
			<div class="col-md-offset-3 col-md-6">
				<div style="font:normal 14px/24px 'MicroSoft YaHei';color:white;">
					<p>总进度:</p>
				</div>
				<div class="progress">
					<div class="progress-bar progress-bar-info progress-bar-striped active" style="width: 85%;">
						<div class="progress-value">85%</div>
					</div>
				</div>

				<div style="font:normal 14px/24px 'MicroSoft YaHei';color:white;">
					<p>当天进度:</p>
				</div>
				<div class="progress">
					<div class="progress-bar progress-bar-danger progress-bar-striped active daily" style="width: 60%;">
						<div class="progress-value">60%</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div style="text-align:center;margin:0 0; font:normal 14px/24px 'MicroSoft YaHei';">
	<button type="button" class="btn btn-success btn_sign"><span class="glyphicon glyphicon-ok"></span> 签到</button>&nbsp;&nbsp;&nbsp;&nbsp;
	<button type="button" class="btn btn-danger btn_clear"><span class="glyphicon glyphicon-flash"></span> 丢失</button>
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="margin: 10% 0;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">登录</h4>
			</div>
			<div class="modal-body">
				<form role="form">
					<div class="form-group">
						<label for="loginName">账户:</label>
						<input type="text" class="form-control" id="loginName" placeholder="请输入账户信息">
					</div>
					<div class="form-group">
						<label for="password">密码:</label>
						<input type="password" class="form-control" id="password" placeholder="请输入密码">
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				<button type="button" class="btn btn-primary btn_login_sub">登录</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<script type="text/javascript">
	//签到按钮点击事件
	$(".btn_sign").click(function() {
		$(".daily").width("70%");
		$(".daily").removeClass("progress-bar-danger").addClass("progress-bar-warning");
		$(".daily").children("div").html("70%");
	});

	$(".btn_login").click(function(){
		$("#myModal").modal('show');
	});

	$(".btn_login_sub").click(function(){
		alert("登录中......");
		$("#myModal").modal('hide');
	});
</script>

</body>
</html>