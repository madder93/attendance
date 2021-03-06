//获取当前月的第一天
function getMonthFirstDay(){
    var now = new Date();
    var year = now.getFullYear();
    var month = now.getMonth();
    var monthFirstDate = new Date(year, month, 1);
    return dateFmt('yyyy-MM-dd', monthFirstDate);
}

//获取当前月的最后一天
function getMonthLastDay(){
    var now = new Date();
    var year = now.getFullYear();
    var month = (now.getMonth() + 1);
    var monthEndDate = new Date(year, month, 0);
    return dateFmt('yyyy-MM-dd', monthEndDate);
}

//日期格式化
function dateFmt(fmt, date) {
    var o = {
        "M+" : date.getMonth() + 1, // 月份
        "d+" : date.getDate(), // 日
        "h+" : date.getHours(), // 小时
        "m+" : date.getMinutes(), // 分
        "s+" : date.getSeconds(), // 秒
        "q+" : Math.floor((date.getMonth() + 3) / 3), // 季度
        "S" : date.getMilliseconds()
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    for ( var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}