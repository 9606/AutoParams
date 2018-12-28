$(document).ready(function () {
    $(function () {
        $(".preloader").fadeOut();
        $('#side-menu').metisMenu();
    });
    // Theme settings

    //Loads the correct sidebar on window load,
    //collapses the sidebar on window resize.
    // Sets the min-height of #page-wrapper to window size
    $(function () {
        $(window).bind("load resize", function () {
            topOffset = 60;
            width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
            if (width < 768) {
                $('div.navbar-collapse').addClass('collapse');
                topOffset = 100; // 2-row-menu
            }
            else {
                $('div.navbar-collapse').removeClass('collapse');
            }
            height = ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
            height = height - topOffset;
            if (height < 1) height = 1;
            if (height > topOffset) {
                $("#page-wrapper").css("min-height", (height) + "px");
            }
        });
        var url = window.location;
        var element = $('ul.nav a').filter(function () {
            return this.href == url || url.href.indexOf(this.href) == 0;
        }).addClass('active').parent().parent().addClass('in').parent();
        if (element.is('li')) {
            element.addClass('active');
        }
    });

    // This is for resize window
    $(function () {
        $(window).bind("load resize", function () {
            width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
            if (width < 1170) {
                $('body').addClass('content-wrapper');
                $(".open-close i").removeClass('fa-arrow-left');
                $(".open-close i").addClass('fa-list-ul');
                $(".sidebar-nav, .slimScrollDiv").css("overflow-x", "visible").parent().css("overflow", "visible");
                $(".logo span").hide();
            }
            else {
                $('body').removeClass('content-wrapper');
                $(".open-close i").removeClass('fa-list-ul');
                $(".open-close i").addClass('fa-arrow-left');
                $(".logo span").show();
            }
        });
    });

    // This is for click on open close button
    // Sidebar open close
    $(".open-close").on('click', function () {
        if ($("body").hasClass("content-wrapper")) {
            $("body").trigger("resize");
            $(".sidebar-nav, .slimScrollDiv").css("overflow", "hidden").parent().css("overflow", "visible");
            $("body").removeClass("content-wrapper");
            $(".open-close i").removeClass("fa-list-ul");
            $(".open-close i").addClass("fa-arrow-left");
            $(".logo span").show();
        }
        else {
            $("body").trigger("resize");
            $(".sidebar-nav, .slimScrollDiv").css("overflow-x", "visible").parent().css("overflow", "visible");
            $("body").addClass("content-wrapper");
            $(".open-close i").removeClass("fa-arrow-left");
            $(".open-close i").addClass("fa-list-ul");
            $(".logo span").hide();
        }
    });

    $('[data-toggle="tooltip"]').tooltip();
    $('[data-toggle="popover"]').popover();
});

// Resize all elements
$("body").trigger("resize");

// this is for close icon when navigation open in mobile view
$(".navbar-toggle").click(function () {
    $(".navbar-toggle i").toggleClass("fa fa-list-ul");
    $(".navbar-toggle i").addClass("fa fa-close");
});

function getChinese(key){
    var chinese = {
        'exception':'异常情况', 'param':'参数', 'progress':'进度', 'performance':'性能', 'isok':'运行情况', 'type':'算法类型',
        'model':'算法名字', 'library':'算法库', 'modelPath':'模型路径', 'fileId':'文件ID', 'fileType':'数据集类型', 'filePath':'文件下载',
        'fileSize':'数据集大小', 'colName':'是否存在列名', 'cluLabel':'聚类是否存在标签', 'originName':'文件名', 'uploadTime':'上传时间'
    };
    if (chinese[key]!=null){
        return chinese[key];
    } else {
        return '未知数据'
    }

}

function getValueTd(key, value, className){
    var $td = $('<td></td>');
    switch(key) {
        case 'exception':
            $td.append(value == ''?'无异常':'value');
            break;
        case 'isok':
            $td.append(value == true?'运行正常':'运行异常');
            break;
        case 'param':
            var $paramTable = $('<table class="table"></table>');
            var $paramTableBody = $('<tbody></tbody>');
            $.each(value, function (vkey, vvalue) {
                var $pTr = $('<tr></tr>');
                $pTr.append('<td>' + vkey + '</td>');
                $pTr.append('<td>' + vvalue + '</td>');
                $paramTableBody.append($pTr);
            });
            $paramTable.append($paramTableBody);
            $td.append($paramTable);
            break;
        case 'performance':
            var $paramTable = $('<table class="table"></table>');
            var $paramTableBody = $('<tbody></tbody>');
            $.each(value, function (vkey, vvalue) {
                var $pTr = $('<tr></tr>');
                $pTr.append('<td>' + vkey + '</td>');
                if (vkey == 'confusion_matrix'){
                    $pTr.append($('<td></td>').append(getCMTable(vvalue, className)));
                } else if (vkey == 'classification_report'){
                    $pTr.append($('<td></td>').append(getCRTable(vvalue, className)));
                } else {
                    $pTr.append('<td>' + vvalue + '</td>');
                }
                $paramTableBody.append($pTr);
            });
            $paramTable.append($paramTableBody);
            $td.append($paramTable);
            break;
        case 'modelPath':
            if (value == ''){
                $td.append('暂无模型');
            } else {
                $td.append('<a href="#" onclick="downloadModel(\'' + value[0] + '\', this)" data-id="' + value[1] + '">' +
                    '<i class="fa fa-download"></i><span>&nbsp&nbsp点击此链接下载到本地</span></a>');
            }
            break;
        case 'filePath':
            $td.append('<a href="#" onclick="downloadFile(\'' + value[0] + '\', this)" data-id="' + value[1] + '" data-name="' + value[2] + '">' +
                '<i class="fa fa-download"></i><span>&nbsp&nbsp点击此链接下载到本地</span></a>');
            break;
        case 'colName':
            $td.append(value ? '存在列名' : '不存在列名');
            break;
        case 'cluLabel':
            $td.append(value ? '存在标签' : '不存在标签');
            break;
        case 'uploadTime':
            $td.append(formatDateTime(value));
            break;
        default:
            $td.append(value);
            break;
    }
    return $td;

}

function getCMTable(confusion_matrix, className){
    var $tbody = $('<tbody></tbody>');
    var $headTr = $('<tr></tr>');
    $headTr.append('<td>\\</td>');
    $.each(confusion_matrix, function (key, value) {
        $headTr.append('<td>' + className[key] + '</td>');
        var $tr = $('<tr></tr>');
        $tr.append('<td>' + className[key] + '</td>');
        $.each(value, function (vkey, vvalue) {
            $tr.append('<td>' + vvalue + '</td>');
        });
        $tbody.append($tr);
    });
    return $('<table class="table"></table>').append($('<thead></thead>').append($headTr)).append($tbody);
}

function getCRTable(classification_report, className){

    var cRLines = classification_report.split("\n");
    var trimCRLines = [];
    $.each(cRLines, function (key, value) {
        if(value.replace(/\s/ig,'') != ''){
            var trimCRLinesWords = [];
            $.each(value.split(" "), function (vkey, vvalue) {
                if (vvalue != '' && vvalue != 'avg' && vvalue != '/') {
                    if (vvalue == 'total'){
                        trimCRLinesWords.push('avg / total');
                    }else if(vvalue == 'micro'){
                        trimCRLinesWords.push('micro avg');
                    }else if(vvalue == 'macro'){
                        trimCRLinesWords.push('macro avg');
                    }else if(vvalue == 'weighted'){
                        trimCRLinesWords.push('weighted avg');
                    }else {
                        trimCRLinesWords.push(vvalue);
                    }
                }
            });
            trimCRLines.push(trimCRLinesWords);
        }
    });
    var $tbody = $('<tbody></tbody>');
    var $headTr = $('<tr></tr>');
    $headTr.append('<td>\\</td>');
    $.each(trimCRLines, function (key, value) {
        if (key == 0){
            $.each(value, function (vkey, vvalue) {
                $headTr.append('<td>' + vvalue + '</td>');
            });
        }else {
            var $tr = $('<tr></tr>');
            if (parseInt(value[0]).toString() == "NaN") {
                $tr.append('<td>' + value[0] + '</td>');
            }else {
                $tr.append('<td>' + className[value[0]] + '</td>');
            }
            $.each(value.slice(1), function (vkey, vvalue) {
                $tr.append('<td>' + vvalue + '</td>');
            });
            $tbody.append($tr);
        }
    });
    return $('<table class="table"></table>').append($('<thead></thead>').append($headTr)).append($tbody);
}

function formatDateTime(inputTime) {
    var date = new Date(inputTime);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    var h = date.getHours();
    h = h < 10 ? ('0' + h) : h;
    var minute = date.getMinutes();
    var second = date.getSeconds();
    minute = minute < 10 ? ('0' + minute) : minute;
    second = second < 10 ? ('0' + second) : second;
    return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;
}

function logInfoProcess (info, time) {
    var $eventLog = $("#js-info-log");
    var liArray = $eventLog.find('li');
    var $e = $("<li data-time='" + time + "'>" + info + "</li>");

    // 先比较特殊位置
    if (liArray.length == 0 ||
        (time >= $(liArray[liArray.length - 1]).data('time') && info != $(liArray[liArray.length - 1]).html())){
        $eventLog.append($e);
    }else if (time <= $(liArray[0]).data("time") && info != $(liArray[0]).html()) {
        $eventLog.prepend($e);
    }else {
        var i = 0;
        while (i != liArray.length && time < $(liArray[i++]).data('time'));
        if (info != $(liArray[i]).html()) {
            $(liArray[i]).after($e);
        }
    }
    $("#info-log").scrollTop($("#info-log")[0].scrollHeight);
}

function logErrorProcess (info, time) {
    var $eventLog = $("#js-error-log");
    var liArray = $eventLog.find('li');
    var $e = $("<li data-time='" + time + "'>" + info + "</li>");

    // 先比较特殊位置
    if (liArray.length == 0 ||
        (time >= $(liArray[liArray.length - 1]).data('time') && info != $(liArray[liArray.length - 1]).html())){
        $eventLog.append($e);
    }else if (time <= $(liArray[0]).data("time") && info != $(liArray[0]).html()) {
        $eventLog.prepend($e);
    }else {
        var i = 0;
        while (i != liArray.length && time < $(liArray[i++]).data('time'));
        if (info != $(liArray[i]).html()) {
            $(liArray[i]).after($e);
        }
    }
    $("#error-log").scrollTop($("#error-log")[0].scrollHeight);
}

jQuery.download = function (url, method, filedir, filename) {
    jQuery('<form action="' + url + '" method="' + (method || 'post') + '" style="display: none">' +
        '<input type="text" name="filePath" value="' + filedir + '"/>' + // 文件路径
        '<input type="text" name="fileName" value="' + filename + '"/>' + // 文件名称
        '</form>').appendTo('body').submit().remove();
};

function downloadModel (url, modelA){
    var modelPath = $(modelA).data("id");
    jQuery('<form action="' + url + '" method="post" style="display: none">' +
        '<input type="text" name="modelPath" value="' + modelPath + '"/>' + // 模型路径
        '</form>').appendTo('body').submit().remove();
}

function downloadFile (url, fileA){
    var filePath = $(fileA).data("id");
    var fileName = $(fileA).data("name");
    jQuery('<form action="' + url + '" method="post" style="display: none">' +
        '<input type="text" name="filePath" value="' + filePath + '"/>' + // 文件路径
        '<input type="text" name="fileName" value="' + fileName + '"/>' + // 文件名称
        '</form>').appendTo('body').submit().remove();
}

var evaluateValueName = {
    'classification':['accuracy_score', 'precision_score', 'recall_score', 'f1_score', 'roc_auc_score'],
    'clustering':['adjusted_rand_score', 'adjusted_mutual_info_score', 'homogeneity_completeness_v_measure',
        'fowlkes_mallows_score', 'calinski_harabaz_score', 'silhouette_score'],
    'regression':['mean_squared_error', 'mean_absolute_error', 'explained_variance_score',
        'median_absolute_error', 'r2_score']
};