<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <title>首页</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <%@include file="includeResource.jsp" %>
    <link rel="stylesheet" href="../resources/css/index.css" />
</head>
<body>
    <div class="mainBox indexBox">
        <button class="btn overtime" onclick="overtime()">加班</button>
        <button class="btn vacation" onclick="vacation()">请假</button>
        <button class="btn" style="margin-top: 3px;margin-left: 3px;width: 90px;" onclick="viewOvertime()">当月加班记录</button>
        <button class="btn vacation" style="top: 250px;left: 400px;width: 90px;" onclick="viewVacation()">当月请假记录</button>
    </div>
    <script>
        layui.use(['layer'], function() {
            var layer = layui.layer;
            var $ = layui.$;

            window.overtime = function(){
                window.location.href = '${basePath}/overtime/toOvertime';
            };

            window.vacation = function(){
                window.location.href = '${basePath}/vacation/toVacation';
            };

            window.viewOvertime = function(){
                window.location.href = '${basePath}/overtime/toViewOvertime';
            };

            window.viewVacation = function(){
                window.location.href = '${basePath}/vacation/toViewVacation';
            };
        });
    </script>
</body>
</html>
