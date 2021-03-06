<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <title>请假</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <%@include file="includeResource.jsp" %>
    <link rel="stylesheet" href="../resources/css/index.css" />
</head>
<body>
    <div class="mainBox indexBox" style="padding-top: 20px;height: 360px;">
        <form class="layui-form subForm" id="vacationForm"> <!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
            <div class="layui-form-item">
                <label class="layui-form-label">开始日期</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input" id="startDate"name="startDate" placeholder="请选择日期" autocomplete="off" readonly lay-verify="required">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">结束日期</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input" id="endDate"name="endDate" placeholder="请选择日期" autocomplete="off" readonly lay-verify="required">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">天数</label>
                <div class="layui-input-block">
                    <input type="text" name="days" placeholder="请输入" autocomplete="off" class="layui-input" lay-verify="number">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">请假类型</label>
                <div class="layui-input-block">
                    <select name="type" lay-filter="type" lay-verify="required">
                        <option value="">请选择</option>
                        <option value="01">2019年假</option>
                        <option value="02">2019线上调休</option>
                        <option value="03">线下调休</option>
                    </select>
                </div>
                <div class="formFieldTip" style="width: 400px; margin-left: 55px;">请注意日期选择是否包含周末或节假日，如果包含，请手动减掉相应天数</div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">备注</label>
                <div class="layui-input-block">
                    <input type="text" name="remark" placeholder="请输入" autocomplete="off" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item" style="margin-left:50px;">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-radius" lay-submit lay-filter="submitForm">提交</button>
                    <button type="button" class="layui-btn layui-btn-radius layui-btn-primary" onclick="returnIndex()">返回</button>
                </div>
            </div>
        </form>
    </div>
    <script>
        layui.use(['layer',"form",'laydate'], function() {
            var layer = layui.layer;
            var form = layui.form;
            var laydate = layui.laydate;
            var $ = layui.$;

            var firstDay = getMonthFirstDay();
            var lasterDay = getMonthLastDay();

            laydate.render({
                elem: '#startDate'
                ,type: 'date'
                ,theme: 'grid'
                ,min: firstDay
                ,max: lasterDay
                ,done: function(value, date){
                    var startDate = new Date(value).getTime();
                    var endDate = $("#endDate").val();
                    if(endDate){
                        endDate = new Date(endDate).getTime();
                        if(startDate > endDate){
                            layer.alert('开始日期不能大于结束日期');
                            $("#startDate").val('');
                        }else {
                            //算天数
                            var startDay = date.date;
                            var endDay = new Date(endDate).getDate();
                            var days = parseInt(endDay) - parseInt(startDay) + 1;
                            $("#vacationForm input[name='days']").val(days);
                        }
                    }
                }
            });
            laydate.render({
                elem: '#endDate'
                ,type: 'date'
                ,theme: 'grid'
                ,min: firstDay
                ,max: lasterDay
                ,done: function(value, date){
                    var endDate = new Date(value).getTime();
                    var startDate = $("#startDate").val();
                    if(startDate){
                        startDate = new Date(startDate).getTime();
                        if(startDate > endDate){
                            layer.alert('开始日期不能大于结束日期');
                            $("#endDate").val('');
                        }else {
                            console.log(date);
                            //算天数
                            var startDay = new Date(startDate).getDate();
                            var endDay = date.date;
                            var days = parseInt(endDay) - parseInt(startDay) + 1;
                            $("#vacationForm input[name='days']").val(days);
                        }
                    }
                }
            });

            //提交
            form.on('submit(submitForm)', function(data){
                var params = data.field;
                if(params.days){
                    var lasterDate = lasterDay.split("-")[2];
                    if(params.days > lasterDate){
                        layer.alert("天数不能大于一个月最大天数！");
                        return false;
                    }

                    var mod = parseFloat(params.days) % 0.5;
                    if(mod != 0 && (params.field == '01' || params.field == '02')){
                        layer.alert("年假和线上调休的天数只能是0.5的倍数！");
                        return false;
                    }
                    var startDate = params.startDate;
                    var endDate = params.endDate;
                    var startDay = startDate.split("-")[2];
                    var endDay = endDate.split("-")[2];
                    var days = parseInt(endDay) - parseInt(startDay) + 1;
                    if(days < params.days){
                        layer.alert("天数不能大于开始结束日期之差！");
                        return false;
                    }
                }
                params.startDate = new Date(params.startDate.replace(/-/g, "\/") + " 00:00:00");
                params.endDate = new Date(params.endDate.replace(/-/g, "\/") + " 00:00:00");
                console.log(params);

                $.ajax({
                    type : "POST",
                    url : "${basePath}/vacation/insertVacation",
                    data: params,
                    dataType : "json",
                    success : function(result) {
                        if('000000' == result.state){
                            layer.msg("新增成功！");
                            window.location.href = "${basePath}/index/index";
                        }else{
                            layer.alert(result.describe, {icon: 2});
                        }
                    },
                    error : function(res) {
                        layer.alert("新增请极爱记录失败！", {title: '错误'});
                    }
                });

                return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
            });

            //返回
            window.returnIndex = function(){
                window.location.href = "${basePath}/index/index";
            }
        });
    </script>
</body>
</html>
