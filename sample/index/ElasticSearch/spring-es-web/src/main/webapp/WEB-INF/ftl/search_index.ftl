<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
<title>搜索</title>  
<meta name="description" content=" 内容介绍不超过100个中文">  
<meta name="keywords" content=" 内容相关关键词3-5个">  
<link href="${basePath !}/css/index.css" rel="stylesheet" type="text/css" />  
  
<script type="text/javascript" src="${basePath !}/js/jquery-1.4.2.min.js"></script>
</head>  
  
<body>  
    <input type="hidden" name="basePath" id="basePath" value="${basePath}" />
    <div class="gover_search">  
        <div class="gover_search_form clearfix">  
            <span class="search_t">关键词匹配搜索</span>  
            <input type="text" class="input_search_key" id="gover_search_key" placeholder="请输入关键词直接搜索" />  
            <button type="button" class="search_btn">搜索</button>  
            <div class="search_suggest" id="gov_search_suggest">  
                <ul> 
                </ul>
            </div>  
        </div>  
    </div>
    <div class="content">
    </div>
    <script type="text/javascript" src="${basePath !}/js/index.js"></script>
</body>