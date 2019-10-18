<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <title>考勤记录</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <%@include file="includeResource.jsp" %>

    <style type="text/css">
        .vacationType{
            display: none;
        }
    </style>
</head>
<body>
    <div class="tableBox" style="border:none; padding-top:20px; width:1100px;">
        <form class="layui-form" id="searchForm">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">员工编号:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="userNo" autocomplete="off" placeholder="请输入员工编号" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">员工姓名:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="userName" autocomplete="off" placeholder="请输入员工姓名" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">类型</label>
                    <div class="layui-input-inline">
                        <select name="category" lay-filter="category">
                            <option value="01" selected>加班</option>
                            <option value="02">请假</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="layui-form-item" style="margin-top: -10px;">
                <div class="layui-inline">
                    <label class="layui-form-label">开始日期</label>
                    <div class="layui-input-inline">
                        <input type="text" name="startDate" id="startDate" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">结束日期</label>
                    <div class="layui-input-inline">
                        <input type="text" name="endDate" id="endDate" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">详细类型</label>
                    <div class="layui-input-inline">
                        <select name="type" lay-filter="typeFilter" id="type">
                            <option value="">请选择</option>
                            <option value="01">公休日加班</option>
                            <option value="02">节假日加班</option>
                            <option value="03">工作日晚上加班</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline">
                    <div class="layui-btn-container" style="padding-top:10px;">
                        <button type="button" data-type="search" class="layui-btn layui-btn-radius layui-btn-sm">搜索</button>
                        <button type="button" data-type="reset" class="layui-btn layui-btn-radius layui-btn-primary layui-btn-sm">清空</button>
                    </div>
                </div>
            </div>
        </form>

        <table id="attendanceTable" lay-filter="attendanceTableFilter"></table>
    </div>

    <script type="text/html" id="toolbarDemo">
        <div class="layui-btn-container">
            <button class="layui-btn layui-btn-sm" lay-event="exportRecord">导出出勤记录</button>
            <button class="layui-btn layui-btn-sm" lay-event="exportExcel">导出考勤表</button>
        </div>
    </script>

    <script>
        layui.use(['layer','table','form','laydate'], function() {
            var layer = layui.layer;
            var $ = layui.$;
            var form = layui.form;
            var table = layui.table;
            var laydate = layui.laydate;

            var firstDay = getMonthFirstDay();
            var lasterDay = getMonthLastDay();

            laydate.render({
                elem: '#startDate' //指定元素
                ,type: 'date'
                ,value: firstDay
            });
            laydate.render({
                elem: '#endDate' //指定元素
                ,type: 'date'
                ,value: lasterDay
            });

            var params =  serializeJson("searchForm");//自动将form表单封装成json
            //第一个实例
            table.render({
                elem: '#attendanceTable'
                ,id: 'attendanceTableFilter'
                ,height: 312
                ,url: '${basePath}/attendance/findAllRecordList' //数据接口
                ,method:'post'
                ,where:{params:params}
                ,toolbar: '#toolbarDemo'
                ,defaultToolbar: []
                //,page: true //开启分页
                ,cols: [[ //表头
                    {field: 'id', title: 'id', hide:true}
                    ,{field: 'userNo', title: '员工编号', width:100}
                    ,{field: 'userName', title: '员工姓名', sort: true, width:100}
                    ,{field: 'category', title: '类型'
                        ,templet: function(d){
                            if(d.category == '01'){
                                return '加班';
                            }else if(d.category == '02'){
                                return '请假';
                            }
                            return '';
                        }
                    }
                    ,{field: 'startDate', title: '开始日期', sort: true, width:115}
                    ,{field: 'endDate', title: '结束日期', width:115}
                    ,{field: 'type', title: '详细类型', width:150
                        ,templet: function(d){
                            if(d.category == '01'){
                                if(d.type == '01'){
                                    return '公休日加班';
                                }else if(d.type == '02'){
                                    return '节假日加班';
                                }else if(d.type == '03'){
                                    return '工作日晚上加班';
                                }
                            }else if(d.category == '02'){
                                if(d.type == '01'){
                                    return '2019年假';
                                }else if(d.type == '02'){
                                    return '2019线上调休';
                                }else if(d.type == '03'){
                                    return '线下调休';
                                }
                            }
                            return '';
                        }
                    }
                    ,{field: 'days', title: '天数', totalRow: true
                        ,templet: function(d){
                            if(d.days) {
                                return d.days + '天';
                            }
                            return '';
                        }
                     }
                    ,{field: 'ifChange', title: '是否算调休', width:100
                        ,templet: function(d){
                            if(d.category == '01') {
                                if (d.ifChange == '01') {
                                    return '是';
                                } else {
                                    return '否';
                                }
                            }
                            return '';
                        }
                     }
                    ,{field: 'changeDays', title: '调休天数', width:100
                        ,templet: function(d){
                            if(d.changeDays) {
                                return d.changeDays + '天';
                            }
                            return '';
                        }
                     }
                    ,{field: 'remark', title: '备注', width:160}
                    ,{field: 'createTime', title: '创建时间', width:160}
                ]]
                ,parseData: function(res){ //res 即为原始返回的数据
                    return {
                        "code": res.state, //解析接口状态
                        "msg": res.describe, //解析提示文本
                        //"count": res.total, //解析数据长度
                        "data": res.results.list //解析数据列表
                    };
                }
            });

            //类型选择
            form.on('select(category)', function(data){
                var overtimeType = '<option value="01">公休日加班</option>' +
                    '<option value="02">节假日加班</option>' +
                    '<option value="03">工作日晚上加班</option>';

                var vacationType = '<option value="01">2019年假</option>' +
                    '<option value="02">2019线上调休</option>' +
                    '<option value="03">线下调休</option>';

                $("#type option").not('option:first').remove();
                if(data.value == '02'){
                    $("#type").append(vacationType);
                }else{
                    $("#type").append(overtimeType);
                }
                form.render('select');
            });

            //查询按钮事件
            var active = {
                search: function(){
                    var params =  serializeJson("searchForm");//自动将form表单封装成json
                    table.reload('attendanceTableFilter', {
                        url: '${basePath}/attendance/findAllRecordList' //数据接口
                        ,method:'post'
                        ,where:{params:params}
                        /*,page: {
                            curr: 1 //重新从第 1 页开始
                        }*/
                    });
                },
                reset: function(){
                    $("#searchForm")[0].reset();
                }
            };
            $('.layui-btn-container button').on('click', function(){
                var type = $(this).data('type');
                active[type] ? active[type].call(this) : '';
            });

            //头工具栏事件
            table.on('toolbar(attendanceTableFilter)', function(obj){
                //var checkStatus = table.checkStatus(obj.config.id);
                if(obj.event == 'exportRecord'){
                    alert(1);
                }else if(obj.event == 'exportExcel'){
                    alert(2);
                }
            });
        });
    </script>
</body>
</html>
