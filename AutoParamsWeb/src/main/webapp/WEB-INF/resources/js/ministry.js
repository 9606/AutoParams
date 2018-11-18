var numberValidator =/^[0-9]*$/;
var lettersValidator = /^[A-Za-z]+$/;//验证大小写字母
var capitalLetters = /^[A-Z]+$/;//验证大写字母
var lowercaseLetters = /^[a-z]+$/;//验证小写字母
var numAndLetters = /^[a-z0-9]/;
var telephoneVerify = /^1[34578]\d{9}$/;
var fixedLineTel= /^0\d{2,3}-?\d{7,8}$/;
var mailVerify = /^[A-Z_a-z0-9-\.]+@([A-Z_a-z0-9-]+\.)+[a-z0-9A-Z]{2,4}$/;
//var mailVerify = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/;

//时间加减
function dateAddSubtract(date, day) {
    if (date == undefined || day == undefined || date == null || date.length == 0){
        return new Date(date.valueOf());
    }
    var time = date.valueOf() + (day) * 3600 * 24 * 1000;
    //if (time < new Date("2016-11-1").valueOf()) {
    //    return new Date("2016-11-1");
    //}
    return new Date(time);
}

//时间转成2016-11-11       参数:为时间类型
function dateFormat(date) {
    if (date == undefined || date == null || date.length == 0){
        return "";
    }
    var stringDate = "";
    var year = date.getFullYear();
    var month;
    if (date.getMonth() + 1 < 10) {
        month = "0" + (date.getMonth() + 1);
    } else {
        month = date.getMonth() + 1;
    }
    var day = date.getDate();
    if(date.getDate() < 10){
        day = "0" + date.getDate();
    }
    stringDate = year + "-" + month + "-" + day;
    return stringDate;
}

//时间转成2016-11-11 00:00:00       参数:为时间类型
function dateFormatAll(date) {
    if (date == undefined || date == null || date.length == 0){
        return "无";
    }
    var stringDate = dateFormat(date);
    stringDate += " ";
    if(date.getHours() < 10){
        stringDate = stringDate + "0" + date.getHours() + ":";
    }else{
        stringDate = stringDate + date.getHours() + ":";
    }
    if(date.getMinutes() < 10){
        stringDate = stringDate + "0" + date.getMinutes() + ":";
    }else{
        stringDate = stringDate + date.getMinutes() + ":";
    }
    if(date.getSeconds() < 10){
        stringDate = stringDate + "0" + date.getSeconds();
    }else{
        stringDate = stringDate + date.getSeconds();
    }

    return stringDate;
}

//datepicker language
// $.fn.datepicker.dates['zh-CN'] = {
//     days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
//     daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
//     daysMin: ["日", "一", "二", "三", "四", "五", "六"],
//     months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
//     monthsShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
//     today: "当天",
//     clear: "清除",
//     format: "yyyy-mm-dd",
//     titleFormat: "yyyy-mm-dd", /* Leverages same syntax as 'format' */
//     weekStart: 0
// };

function showBYLoading() {
    $($(".preloader")[0]).css("display","");
    $($(".preloader")[0]).css("opacity", 0.4);
}


function hideBYLoading() {
    $($(".preloader")[0]).css("display","none");
}

//设置chart高度和宽度
function changeClientSize($charts1,$charts2) {
    //var userSearch_width = document.getElementById(dom_id).offsetWidth;
    var sreen_width = document.body.clientWidth;
    var left_sidebar_width = document.getElementById("left_sidebar").offsetWidth;
    var charts_width = sreen_width - left_sidebar_width -240;
    if (charts_width > 400) {
        var userSearch_heigth = charts_width * 1 / 3;

        if ($charts1 != null && $charts1 != undefined){
            $charts1.css("width", charts_width);
            $charts1.css("height", userSearch_heigth);
        }
        if ($charts2 != null && $charts2 != undefined) {
            $charts2.css("height", charts_width * 4 / 7);
        }
    }
}

function showAlert(title, text, type, callback) {
    swal({
        title:title,
        text:text,
        type:type,
        showCancelButton:false,
        showConfirmButton:true,
        confirmButtonText: '确定',
        animation:"slide-from-top"
    }, callback);
}

function showConfirmAlert(title, text, type, callback) {
    swal({
        title:title,
        text:text,
        type:type,
        showCancelButton: "true",
        showConfirmButton: "true",
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        closeOnConfirm: false,
        showLoaderOnConfirm: true,
        animation:"slide-from-top"
    }, function(){
        callback();
    });
}

//数组 按某一对象 排序 大->小
function compare(property){
    return function(a,b){
        var value1 = a[property];
        var value2 = b[property];
        return value2 - value1;
    }
}