<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <title>忘记密码</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <%@include file="includeResource.jsp" %>
    <link rel="stylesheet" href="../resources/css/index.css" />
    <script src="${basePath}/resources/js/jquery.base64.js"></script>
    <script src="${basePath}/resources/js/md5.js"></script>
</head>
<body>
    <div class="mainBox indexBox" style="padding-top: 20px;height: 240px;">
        <form class="layui-form subForm" id="resetPwdForm"> <!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
            <div class="layui-form-item">
                <label class="layui-form-label">姓名</label>
                <div class="layui-input-block">
                    <input type="text" name="userName" placeholder="请输入姓名" autocomplete="off" class="layui-input" lay-verify="required">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">新密码</label>
                <div class="layui-input-block">
                    <input type="password" name="password" placeholder="请输入新密码" autocomplete="off" class="layui-input" lay-verify="required|passWord">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">确认密码</label>
                <div class="layui-input-block">
                    <input type="password" name="passwordAgain" placeholder="再次输入密码" autocomplete="off" class="layui-input" lay-verify="required|passWord">
                </div>
            </div>

            <div class="layui-form-item" style="margin-left:55px;">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-radius" lay-submit lay-filter="submitForm">重置</button>
                    <button type="button" class="layui-btn layui-btn-radius layui-btn-primary" onclick="returnLogin()">返回</button>
                </div>
            </div>
        </form>
    </div>
    <script>
        layui.use(['layer',"form"], function() {
            var layer = layui.layer;
            var form = layui.form;
            var $ = layui.$;

            form.verify({
                passWord: [
                    /^[\S]{6,12}$/
                    ,'密码必须6到12位，且不能出现空格'
                ]
            });

            //提交
            form.on('submit(submitForm)', function(data){

                console.log(data.field);

                $.base64.utf8encode = true;
                var password = $.base64.btoa(data.field.password);
                data.field.password = password;

                var passwordAgain = $.base64.btoa(data.field.passwordAgain);
                data.field.passwordAgain = passwordAgain;

                $.ajax({
                    type : "POST",
                    url : "${basePath}/index/updatePwd",
                    data: data.field,
                    dataType : "json",
                    success : function(result) {
                        if('000000' == result.state){
                            layer.msg("修改成功！");
                            window.location.href = "${basePath}/index/toLogin";
                        }else{
                            layer.alert(result.describe, {icon: 2});
                        }
                    },
                    error : function(res) {
                        layer.alert("修改失败！", {title: '错误'});
                    }
                });

                return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
            });

            //返回
            window.returnLogin = function(){
                window.location.href = "${basePath}/index/toLogin";
            }
        });
    </script>
</body>
</html>
