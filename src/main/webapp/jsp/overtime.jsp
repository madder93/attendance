<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <title>加班</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <%@include file="includeResource.jsp" %>
    <link rel="stylesheet" href="../resources/css/index.css" />
</head>
<body>
    <div class="mainBox indexBox" style="padding-top: 20px;height: 250px;">
        <form class="layui-form subForm" id="overtimeForm"> <!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
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
                <label class="layui-form-label">加班类型</label>
                <div class="layui-input-block">
                    <select name="type" lay-filter="type" lay-verify="required">
                        <option value="">请选择</option>
                        <option value="01">公休日加班</option>
                        <option value="02">节假日加班</option>
                        <option value="03">工作日晚上加班</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item" style="display: none;" id="dasyDiv">
                <label class="layui-form-label">天数</label>
                <div class="layui-input-block">
                    <input type="text" name="days" placeholder="请输入" autocomplete="off" class="layui-input" lay-verify="daysRequired">
                </div>
            </div>
            <div class="layui-form-item" style="display: none;" id="ifChangeDiv">
                <label class="layui-form-label">是否算调休</label>
                <div class="layui-input-block">
                    <input type="radio" name="ifChange" value="00" title="否" checked lay-filter="ifChange">
                    <input type="radio" name="ifChange" value="01" title="是" lay-filter="ifChange">
                </div>
            </div>
            <div class="layui-form-item" style="display: none;" id="changeDaysDiv">
                <label class="layui-form-label">结束时间</label>
                <div class="layui-input-block">
                    <select name="changeDays" lay-verify="changeDaysRequired">
                        <option value="">请选择</option>
                        <option value="0.25">20:30</option>
                        <option value="0.5">22:00</option>
                    </select>
                </div>
                <div class="formFieldTip">加班到20:30算0.25天；加班到22:00算0.5天</div>
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
                        }
                    }
                }
            });

            //加班类型选择
            form.on('select(type)', function(data){
                if(data.value == '03'){   //工作日晚上加班
                    $("#dasyDiv").hide();
                    $("#ifChangeDiv").show();
                    $(".indexBox").css("height", "300px");
                    $("#overtimeForm input[name='days']").val('');
                }else{
                    $("#dasyDiv").show();
                    $("#ifChangeDiv").hide();
                    $("#changeDaysDiv").hide();
                    $(".indexBox").css("height", "280px");
                    //算天数
                    var startDate = $("#startDate").val();
                    var endDate = $("#endDate").val();
                    if(startDate && endDate){
                        var startDay = startDate.split("-")[2];
                        var endDay = endDate.split("-")[2];
                        var days = parseInt(endDay) - parseInt(startDay) + 1;
                        $("#overtimeForm input[name='days']").val(days);
                    }
                }
            });

            //是否调休选择
            form.on('radio(ifChange)', function(data){
                if(data.value == '01'){
                    $("#changeDaysDiv").show();
                    $(".indexBox").css("height", "370px");
                }else{
                    $("#changeDaysDiv").hide();
                    $(".indexBox").css("height", "330px");
                }
            });

            //表单验证
            form.verify({
                daysRequired: function(value, item){ //value：表单的值、item：表单的DOM对象
                    var display = $("#dasyDiv").css("display");
                    if(display != "none"){
                        if(!value){
                            return '天数不能为空';
                        }
                    }
                }
                ,changeDaysRequired: function(value, item){ //value：表单的值、item：表单的DOM对象
                    var display = $("#changeDaysDiv").css("display");
                    if(display != "none"){
                        if(!value){
                            return '结束时间不能为空';
                        }
                    }
                }
            });

            //提交
            form.on('submit(submitForm)', function(data){
                var params = data.field;
                if(params.days){
                    var mod = parseFloat(params.days) % 0.5;
                    if(mod != 0){
                        layer.alert("公休日和节假日加班天数只能是0.5的倍数！");
                        return false;
                    }
                    var days = calcDays(params.startDate, params.endDate);
                    if(days < params.days){
                        layer.alert("天数不能大于开始结束日期之差！");
                        return false;
                    }
                }
                if(params.changeDays) {
                    var days = calcDays(params.startDate, params.endDate);
                    var changeDays = days * params.changeDays;
                    params.changeDays = changeDays;
                }
                params.startDate = new Date(params.startDate.replace(/-/g, "\/") + " 00:00:00");
                params.endDate = new Date(params.endDate.replace(/-/g, "\/") + " 00:00:00");
                console.log(params);

                $.ajax({
                    type : "POST",
                    url : "${basePath}/overtime/insertOvertime",
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
                        layer.alert("新增加班记录失败！", {title: '错误'});
                    }
                });

                return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
            });

            //返回
            window.returnIndex = function(){
                window.location.href = "${basePath}/index/index";
            }

            //计算天数
            function calcDays(startDate, endDate) {
                var startDay = startDate.split("-")[2];
                var endDay = endDate.split("-")[2];
                var days = parseInt(endDay) - parseInt(startDay) + 1;
                return days;
            }
        });
    </script>
</body>
</html>
