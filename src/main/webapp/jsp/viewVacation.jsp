<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <title>请假记录</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <%@include file="includeResource.jsp" %>
</head>
<body>
    <div class="tableBox" style="border:none;">
        <table id="vacationTable" lay-filter="vacationTableFilter"></table>
    </div>

    <script type="text/html" id="toolbarDemo">
        <div class="layui-btn-container">
            <button class="layui-btn layui-btn-sm" lay-event="back">返回</button>
        </div>
    </script>

    <script>
        layui.use(['layer','table'], function() {
            var layer = layui.layer;
            var $ = layui.$;

            var table = layui.table;

            //第一个实例
            table.render({
                elem: '#vacationTable'
                ,id: 'vacationTableFilter'
                ,height: 312
                ,url: '${basePath}/vacation/findVacationList' //数据接口
                ,method:'post'
                ,toolbar: '#toolbarDemo'
                ,defaultToolbar: []
                ,totalRow: true
                //,page: true //开启分页
                ,cols: [[ //表头
                    {field: 'id', title: 'id', hide:true}
                    ,{field: 'startDate', title: '开始日期', sort: true, totalRowText: '合计'}
                    ,{field: 'endDate', title: '结束日期'}
                    ,{field: 'days', title: '天数', totalRow: true
                        ,templet: function(d){
                            if(d.days) {
                                return d.days + '天';
                            }
                            return '';
                        }
                    }
                    ,{field: 'type', title: '请假类型', width:150
                        ,templet: function(d){
                            if(d.type == '01'){
                                return '2019年假';
                            }else if(d.type == '02'){
                                return '2019线上调休';
                            }else if(d.type == '03'){
                                return '线下调休';
                            }
                            return '';
                        }
                     }
                    ,{field: 'createTime', title: '创建时间', width:180}
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

            //头工具栏事件
            table.on('toolbar(vacationTableFilter)', function(obj){
                switch(obj.event){
                    case 'back':
                        window.location.href = '${basePath}/index/index';
                        break;
                };
            });
        });
    </script>
</body>
</html>
