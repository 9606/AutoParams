var algoId = '';
var algoLib = '';
var algoName = '';
var algoParams = '';
var newAlgoParams = null;

var evaType = '';

var optAlgoId = '';
var optAlgoName = '';
var optAlgoParams = '';
var newOptAlgoParams = null;

var fileInfo = null;
var fileTypeValue = '';

var webSocket = null;
var taskId = null;

!(function () {
    var submit_task = {};

    var myChart = null;
    var serie = {type: 'line', smooth: false, seriesLayoutBy: 'row'};
    var legendData = [];
    var dimensionsData = [];
    var evaValues = [];
    var seriesData = [];
    var lastInsertPos = 0;
    var latestProcessTime = 0;

    submit_task.initEchart = function () {
        myChart = echarts.init(document.getElementById('algo-evaluate-curve'));
        serie = {type: 'line', smooth: false, seriesLayoutBy: 'row'};
        legendData = [];
        dimensionsData = [];
        evaValues = [];
        seriesData = [];
        lastInsertPos = 0;
        latestProcessTime = 0;
        var option = {
            // title :{
            //     text: '算法评估曲线'
            // },
            tooltip: {
                trigger: 'axis'
            },
            grid: {
                left: '0%',
                right: '4%',
                bottom: '10%',
                containLabel: true
            },
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            series: seriesData
        };
        myChart.setOption(option, true);
    };

    submit_task.updateTaskProcess = function(taskResult){
        var nowTime = parseInt(taskResult.progress.split('/')[0]);
        var allTime = parseInt(taskResult.progress.split('/')[1]);
        if (nowTime > latestProcessTime){
            var progress = parseInt(nowTime / allTime * 100, 10);
            $('#task-progress .progress-bar').css(
                'width',
                progress + '%'
            );
            $('#task-label').html('任务进度: ' + nowTime + ' / ' + allTime);
        }

        var evaValue = taskResult['performance'];
        evaValue['process'] = nowTime;
        // evaValues.push(evaValue);
        submit_task.insertionSort(evaValues, evaValue);
        var option = null;
        if (legendData.length == 0 || dimensionsData.length == 0 || seriesData.length == 0){
            dimensionsData.push('process');
            $.each(evaluateValueName[taskResult['type']], function (key, value) {
                if (evaValue[value] != null){
                    legendData.push(value);
                    dimensionsData.push(value);
                    seriesData.push(serie);
                }
            });

            option = {
                dataZoom: [
                    {
                        type: 'slider',
                        xAxisIndex: 0,
                        start: 0,
                        end: 100
                    },
                    {
                        type: 'inside',
                        xAxisIndex: 0,
                        start: 0,
                        end: 100
                    },
                    {
                        type: 'slider',
                        yAxisIndex: 0,
                        start: 0,
                        end: 100
                    },
                    {
                        type: 'inside',
                        yAxisIndex: 0,
                        start: 0,
                        end: 100
                    }
                ],
                legend: {
                    data: legendData
                },
                dataset: {
                    dimensions: dimensionsData,
                    source: evaValues
                },
                xAxis: {type: 'category'},
                yAxis: {},
                series: seriesData
            };
        }else {
            option = {
                dataset: {
                    dimensions: dimensionsData,
                    source: evaValues
                }
            }
        }
        myChart.setOption(option);
        if (nowTime == allTime){
            addTaskResultHtml(taskResult);
        }
    };

    submit_task.insertionSort = function insertionSort(array, item) {
        // 先比较三个特殊位置
        if (array.length == 0 || item['process'] > array[array.length - 1]['process']){
            array.push(item);
            lastInsertPos = array.length - 1;
            return array;
        }
        if (item['process'] < array[0]['process']) {
            array.splice(0, 0, item);
            lastInsertPos = 0;
            return array;
        }
        if (item['process'] > array[lastInsertPos]['process']) {
            while (++lastInsertPos != array.length && item['process'] >= array[lastInsertPos]['process']){
                if (item['process'] == array[lastInsertPos]['process']) {
                    return array;
                }
            }
            array.splice(lastInsertPos, 0, item);
            return array;
        }else {
            while (--lastInsertPos != 0 && item['process'] <= array[lastInsertPos]['process']){
                if (item['process'] == array[lastInsertPos]['process']) {
                    return array;
                }
            }
            array.splice(lastInsertPos, 0, item);
            return array;
        }
    };

    $('#process-collapse').on('shown.bs.collapse', function () {
        $("#algo-evaluate-curve").css('width', $("#algo-evaluate-curve").width());
        myChart.resize();
    });

    submit_task.startWebSocketHtml5 = function () {
        // if (webSocket.readyState == 1){
        //     webSocket.close();
        // }
        if (webSocket != null && webSocket.readyState == 1){
            webSocket.close();
        }
        var host = window.location.host;
        if ("WebSocket" in window) {
            console.log("您的浏览器支持 WebSocket!");
            // webSocket = new ReconnectingWebSocket("ws://" + host + "/api/task/submit/webSocket/task/process", null, {
            //     debug: true,
            //     maxReconnectAttempts: 10
            // });
            webSocket = new WebSocket("ws://" + host + "/api/task/submit/webSocket/task/process");
        } else if ('MozWebSocket' in window) {
            webSocket = new MozWebSocket("ws://" + host + "/api/task/submit/webSocket/task/process");
        } else {
            webSocket = new SockJS("http://" + host + "/api/task/submit/sockjs/task/process");
        }

        webSocket.onmessage = function (evt) {
            // USER_STOP("用户主动停止任务"),
            // INFO_LOG("正常任务信息"),
            // ERR_LOG("异常任务信息"),
            // SERVER_DONE("服务器完成任务，无需停止"),
            // SERVER_STOP("服务器已停止任务信息"),
            // SERVER_UNKNOWN_ERROR("服务器发生未知错误"),
            // TASK_FINISH("任务已完成");
            var message = JSON.parse(evt.data);
            // console.log(message);
            if (message.type == 1){
                submit_task.updateTaskProcess(JSON.parse(message.content));
                logInfoProcess(message.content, message.msgOrder);
            }else if(message.type == 2){
                logErrorProcess(message.content, message.msgOrder);
            }else if(message.type == 3){
                $('#task-label').html(message.content);
            }else if(message.type == 6){
                webSocket.close();
                $('#task-label').html(message.content);
            }else{
                $('#task-label').html(message.content);

            }

        };
        webSocket.onerror = function (evt) {
            console.log("任务进度 websocket 错误");
        };
        webSocket.onclose = function () {
            console.log("任务进度 websocket 关闭");
        };
    };

    window.submit_task = submit_task;
})();

function checkAndStartTask(){
    var taskId = getRunningTaskId();
    if (taskId >= 0){
        swal({
                title: "您有正在运行的任务",
                text: "系统检测到您有正在运行的任务，如果选择继续提交，正在运行的任务将停止，选择恢复正在运行任务将回到正在任务的状态界面。",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "继续提交！",
                cancelButtonText: "恢复原任务！",
                closeOnConfirm: true,
                closeOnCancel: true
            },
            function (isConfirm) {
                if (isConfirm) {
                    initTaskProcessHtml();
                    submit_task.startWebSocketHtml5();
                    webSocket.onopen = function () {
                        console.log("任务进度 websocket 连接上");
                        startTask();
                    };

                }
                else {
                    $.ajax({
                        url: "/api/task/submit/params/" + taskId,
                        type: "get",
                        success: function (data) {
                            processRecover(data.data, taskId);
                        },
                        error: function (data) {
                            swal(data.result, "", "error");
                            console.log(data);
                        }
                    });
                    // initTaskProcessHtml();
                    // submit_task.startWebSocketHtml5();
                    // webSocket.onopen = function () {
                    //     console.log("任务进度 websocket 连接上");
                    //
                    // };

                }
            }
        );
    } else {
        initTaskProcessHtml();
        submit_task.startWebSocketHtml5();
        webSocket.onopen = function () {
            console.log("任务进度 websocket 连接上");
            startTask();
        };
    }

}
function startTask() {

    var data = {
        'algoId': algoId,
        'algoLib': algoLib,
        'algoName': algoName,
        'algoParams': newAlgoParams,
        'fileInfo': fileInfo,
        'evaType' : evaType,
        'optAlgoId': optAlgoId,
        'optAlgoName': optAlgoName,
        'optAlgoParams': newOptAlgoParams
    };
    $.ajax({
        url: "/api/task/submit/start",
        type: "post",
        data: JSON.stringify(data),
        contentType: "application/json;charset=utf-8",
        success: function (data) {
            if (data.result != "SUCCESS") {
                swal("提交任务失败!", data.message, "error");
            }
            taskId = data.data;
        },
        error: function (data) {
            swal("提交任务失败!", data.message, "error");
            console.log(data);
        }
    })
}
function sendStopTaskMessage(){
    if (!webSocket.readyState == 1){
        swal("终止任务失败!", "任务已完成，无法停止", "error");
    } else {
        webSocket.send(JSON.stringify({"type": 0,"content":taskId}));
        $('#task-label').html('任务正在停止');
    }
}

function addOptAlgoHtml(param){
    var optParamsHtml =
        '<div class="form-group">' +
            '<label class="col-md-2 control-label">' +
                '<span class="text-success">*</span>&nbsp优化算法属性：' +
            '</label>';
    $.each(param, function (key, value) {
        if (key%3 == 0 && key > 0){
            optParamsHtml +=
                '<div class="col-md-offset-2 col-md-3" style="margin-bottom: 20px;">';
        } else {
            optParamsHtml +=
                '<div class="col-md-3" style="margin-bottom: 20px;">';
        }
        optParamsHtml +=
                '<input type="text" style="width: 75%;" id="' + value.name + '" name="' + value.name + '" ' +
                    'class="form-control" placeholder="默认' + value.placeholder + '：' + value.val + '">' +
            '</div>';
    });
    optParamsHtml += '</div>';
    $('#opt-algo-params-div').empty();
    $('#opt-algo-params-div').append(optParamsHtml);
    $('#opt-algo-params-div').attr('style','margin-top: 20px;');

}

function initSelect2() {
    // $.fn.select2.defaults.set( "theme", "bootstrap" );
    // $('.select2-multiple').select2({
    //     theme: "bootstrap"
    // });
    $("#algo-type").prop("disabled", true);
    $("#algo-name").prop("disabled", true);
    $("#file-type").prop("disabled", true);
    $("#eva-type").prop("disabled", true);
    // 暂时禁用，只能选贝叶斯优化，等后续后端脚本改进再让用户自定义
    $("#opt-algo-name").prop("disabled", true);

    $("#algo-lib").select2({
        placeholder: "请选择算法库",
        language: "zh-CN",
        minimumResultsForSearch: 10,
        ajax: {
            url: "/api/task/submit/algo/lib",
            dataType: "json",
            type: "get",
            data: function (params) {
            },
            processResults: function (data) {
                var results = [];
                $.each(data.data, function (key, value) {
                    var o = {};
                    o.id = value;
                    o.text = value;
                    results.push(o);
                });
                return {results: results};
            }
        }
    }).on('select2:select', function (e) {
        $("#algo-type").prop("disabled", false);
        $("#algo-type").empty();
        $("#algo-name").prop("disabled", true);
        $("#algo-name").empty();
        $("#eva-type").prop("disabled", true);
        $("#eva-type").empty();
        $("#opt-algo-name").prop("disabled", true);
        $("#opt-algo-name").empty();
        $("#opt-algo-params-div").prop("display", 'none');
        $("#opt-algo-params-div").empty();

        algoLib = $('#algo-lib').select2('data')[0].id;
        $("#algo-type").select2({
            placeholder: "请选择算法类型",
            language: "zh-CN",
            minimumResultsForSearch: 10,
            ajax: {
                url: "/api/task/submit/algo/lib/type",
                dataType: "json",
                type: "get",
                data: function (params) {
                    return {'algoLib': algoLib};
                },
                processResults: function (data) {
                    var results = [];
                    $.each(data.data, function (key, value) {
                        var o = {};
                        o.id = key;
                        o.text = value;
                        results.push(o);
                    });
                    return {results: results};
                }
            }
        });
    });

    $("#algo-type").select2({
        placeholder: "请选择算法类型",
        language: "zh-CN"
    });

    $("#algo-name").select2({
        placeholder: "请选择机器学习算法",
        language: "zh-CN"
    });

    $("#file-type").select2({
        placeholder: "选择训练集格式",
        language: "zh-CN",
        minimumResultsForSearch: 10
    }).on('select2:select', function (e) {
        fileTypeValue = $("#file-type").find("option:selected").text();
    });

    $("#file-history").select2({
        placeholder: "从历史训练集中选择训练集",
        language: "zh-CN",
        minimumResultsForSearch: 5,
        ajax: {
            url: "/api/task/submit/file/history",
            dataType: "json",
            type: "get",
            processResults: function (data) {
                var results = [];
                $.each(data.data, function (key, value) {
                    var o = {};
                    o.id = value.fileId;
                    o.text = value.originName;
                    results.push(o);
                });
                return {results: results};
            }
        }
    }).on('select2:select', function (e) {
        $.ajax({
            url: "/api/task/manage/file/detail/" + $('#file-history').select2('data')[0].id,
            type: "get",
            success: function (data) {
                fileInfo = data.data;
                fileTypeValue = fileInfo.fileType;
                $('#file-type').val(fileInfo.fileType);
                $('#file-type').trigger('change');

                $('#file-info').empty();
                $("#file-info").attr("style","margin-top: 20px;");
                $('<p/>').text(fileInfo.originName + ' 已选择').appendTo('#file-info');
            },
            error: function (data) {
                console.log(data);
            }
        });
    });

    $("#eva-type").select2({
        placeholder: "选择算法优化方向",
        language: "zh-CN",
        minimumResultsForSearch: 10
    });

    $('#opt-algo-name').select2({
        placeholder: "选择调参算法",
        language: "zh-CN",
        minimumResultsForSearch: 10,
        ajax: {
            url: "/api/task/submit/opt/algo/names",
            dataType: "json",
            type: "get",
            data: function (params) {},
            processResults: function (data) {
                var results = [];
                $.each(data.data, function (key, value) {
                    var o = {};
                    o.id = value.optAlgoId;
                    o.text = value.optAlgoName;
                    results.push(o);
                });
                return {results: results};
            }
        }
    }).on('select2:select', function (e) {
        optAlgoId = $('#opt-algo-name').select2('data')[0].id;
        optAlgoName = $("#opt-algo-name").find("option:selected").text();
        $.ajax({
            url: "/api/task/submit/opt/algo/params",
            type: "get",
            data: {'optAlgoId' : optAlgoId},
            success: function (data) {
                $('#opt-algo-params-div').empty();
                $('#opt-algo-params-div').attr('style','');
                optAlgoParams = data.data;
                var optAlgoParamsJson = JSON.parse(optAlgoParams);
                if (optAlgoParamsJson.length != 0) {
                    addOptAlgoHtml(optAlgoParamsJson);
                }
            },
            error: function (data) {
                swal(data.result, "", "error");
                console.log(data);
            }
        });
    });

}

function changeAlgoType() {
    var algoType = $("#algo-type").find("option:selected").text();
    var evaTypeSelect2 = $('#eva-type');
    var optAlgoNameSelect2 = $('#opt-algo-name');

    // $('#algo-name').val(null).trigger('change');
    $("#algo-name").prop("disabled", false);
    $("#algo-name").empty();
    $("#algo-name").select2({
        placeholder: "请选择算法",
        language: "zh-CN",
        minimumResultsForSearch: 10,
        ajax: {
            url: "/api/task/submit/algo/lib/type/names",
            dataType: "json",
            type: "get",
            data: function (params) {
                return {
                    'algoLib': algoLib,
                    'algoType': algoType
                };
            },
            processResults: function (data) {
                var results = [];
                $.each(data.data, function (key, value) {
                    var o = {};
                    o.id = value.algoId;
                    o.text = value.algoName;
                    results.push(o);
                });
                return {results: results};
            }
        }
    }).on('select2:select', function (e) {
        algoId = $('#algo-name').select2('data')[0].id;
        algoName = $("#algo-name").find("option:selected").text();
        algoType = $("#algo-type").find("option:selected").text();
        $.ajax({
            url: "/api/task/submit/algo/params",
            type: "get",
            data: {'algoId' : algoId},
            success: function (data) {
                algoParams = data.data;
                $("#algo-params-body").empty();
                addParamForm();
                addValidator();
                $('.select2-multiple').select2({
                    theme: "bootstrap"
                });
                selectValues();
            },
            error: function (data) {
                swal(data.result, "", "error");
                console.log(data);
            }
        });
        evaTypeSelect2.select2({
            placeholder: "选择评估类型",
            language: "zh-CN",
            minimumResultsForSearch: 10,
            ajax:{
                url: "/api/task/submit/algo/evaType",
                dataType: "json",
                type: "get",
                data: function (params) {
                    return {
                        'algoLib': algoLib,
                        'algoType': algoType
                    }
                },
                processResults: function (data) {
                    var results = [];
                    $.each(data.data, function (key, value) {
                        var o = {};
                        o.id = key;
                        o.text = value;
                        results.push(o);
                    });
                    return {results: results};
                }
            }
        }).on('select2:select', function (e) {
            evaType = $("#eva-type").find("option:selected").text();
        });
        // 暂时禁用，每个算法只能选默认评价指标，等后续后端脚本改进再让用户自定义
        // evaTypeSelect2.prop("disabled", false);
        $.ajax({
            url: "/api/task/submit/algo/evaType",
            type: "get",
            data: {
                'algoLib': algoLib,
                'algoType': algoType
            },
            success: function (data) {
                $.each(data.data, function (key, value) {
                    if (value === 'accuracy_score'|| value == "mean_squared_error" || value == "calinski_harabaz_score"){
                        var option = new Option(value, key, true, true);
                    }else {
                        var option = new Option(value, key, false, false);
                    }
                    evaTypeSelect2.append(option).trigger('change');
                });
                evaTypeSelect2.trigger('select2:select');
            },
            error: function (data) {
                swal(data.result, "", "error");
                console.log(data);
            }
        });
    });

    $("#eva-type").prop("disabled", true);
    $("#eva-type").empty();

    if(optAlgoNameSelect2.select2('data').length == 0){
        $.ajax({
            url: "/api/task/submit/opt/algo/names",
            type: "get",
            success: function (data) {
                $.each(data.data, function (key, value) {
                    if (value == 'Bayesian Optimization'){
                        var option = new Option(value.optAlgoName, value.optAlgoId, true, true);
                    }else {
                        var option = new Option(value.optAlgoName, value.optAlgoId, false, false);
                    }
                    optAlgoNameSelect2.append(option).trigger('change');
                });
                optAlgoNameSelect2.trigger('select2:select');
            },
            error: function (data) {
                swal(data.result, "", "error");
                console.log(data);
            }
        });
    }


}

function showHistory(){
    $('#file-info').empty();
    $("#file-info").attr("style","");
    $("#file-history").val('');
    $('#file-history').trigger('change');
    $("#file-type").val('');
    $('#file-type').trigger('change');
    $("#file-progress-span").attr("style","display:none;");
    var fileInfo = null;
    var fileTypeValue = '';

    $("#new-file-div").css('display','none');
    $("#file-history-div").css('display','block');
    $("#fileupload-start-div").attr("style", 'visibility: hidden;');

    $("#file-property-span").css('display','block');
    $("#file-type").prop("disabled", true);
    $("#is-col-name").prop("disabled", true);
    $("#is-col-name").prop("checked",true);
    $("#is-label").prop("disabled", true);
    $("#is-label").prop("checked",false);
}

function showNew(){
    $('#file-info').empty();
    $("#file-info").attr("style","");
    $("#file-history").val('');
    $('#file-history').trigger('change');
    $("#file-type").val('');
    $('#file-type').trigger('change');
    $("#file-progress-span").attr("style","display:none;");
    var fileInfo = null;
    var fileTypeValue = '';

    $("#file-history-div").css('display','none');
    $("#new-file-div").css('display','block');
    $("#fileupload-start-div").attr("style", 'visibility:visible;');

    $("#file-property-span").css('display','block');
    $("#file-type").prop("disabled", false);
    $("#is-col-name").prop("disabled", false);
    $("#is-col-name").prop("checked",true);
    $("#is-label").prop("disabled", false);
    $("#is-label").prop("checked",false);
}

function getIntHtml(obj){
    var intHtml =
        '<div class="form-group">' +
            '<label class="col-sm-2 control-label">整数范围：</label>' +
            '<div class="col-sm-2">' +
                '<input type="text" id="' + obj.paramName + '-int-down" name="' + obj.paramName + '-int-down"' +
                'class="form-control" placeholder="默认下限：' + obj.intDownValue + '">' +
            '</div>' +
            '<span class="col-sm-1 text-success"' +
            'style="font-size:20px;text-align: center;display:block;">~</span>' +
            '<div class="col-sm-2">' +
                '<input type="text" id="' + obj.paramName + '-int-up" name="' + obj.paramName + '-int-up"' +
                'class="form-control" placeholder="默认上限：' + obj.intUpValue + '">' +
            '</div>' +
            '<label class="col-sm-2 control-label">整数个数：</label>' +
            '<div class="col-sm-2">' +
                '<input type="text" id="' + obj.paramName + '-int-num" name="' + obj.paramName + '-int-num"' +
                'class="form-control" placeholder="默认个数：' + obj.intNum + '">' +
            '</div>' +
        '</div>';
    return intHtml;
}
function getFloatHtml(obj){
    var floatHtml =
        '<div class="form-group">' +
            '<label class="col-sm-2 control-label">浮点数范围：</label>' +
            '<div class="col-sm-2">' +
                '<input type="text" id="' + obj.paramName + '-float-down" name="' + obj.paramName + '-float-down"' +
                'class="form-control" placeholder="默认下限：' + obj.floatDownValue + '">' +
            '</div>' +
            '<span class="col-sm-1 text-success"' +
            'style="font-size:20px;text-align: center;display:block;">~</span>' +
            '<div class="col-sm-2">' +
                '<input type="text" id="' + obj.paramName + '-float-up" name="' + obj.paramName + '-float-up"' +
                'class="form-control" placeholder="默认上限：' + obj.floatUpValue + '">' +
            '</div>' +
            '<label class="col-sm-2 control-label">浮点数个数：</label>' +
            '<div class="col-sm-2">' +
                '<input type="text" id="' + obj.paramName + '-float-num" name="' + obj.paramName + '-float-num"' +
                'class="form-control" placeholder="默认个数：' + obj.floatNum + '">' +
            '</div>' +
        '</div>';
    return floatHtml;

}
function getStringHtml(obj){
    var optionHtml = '';
    $.each(obj.stringValues, function (key, value) {
        optionHtml += '<option value="' + value + '">' + value + '</option>';
    });
    var stringHtml =
        '<div class="form-group">' +
            '<label class="control-label col-sm-2">枚举类型：</label>' +
            '<div class="col-sm-4">' +
                '<select id="' + obj.paramName + '-string" class="select2-multiple form-control"' +
                'multiple="multiple" style="width: 100%;">' + optionHtml + '</select>' +
                // 'multiple="multiple"></select>' +
            '</div>' +
        '</div>';

    return stringHtml;

}
function getBoolHtml(obj){
    var boolHtml =
        '<div class="form-group">' +
            '<label class="control-label col-sm-2">布尔类型：</label>' +
            '<div class="col-sm-4">' +
                '<select id="' + obj.paramName + '-bool" class="select2-multiple form-control"' +
                'multiple="multiple" style="width: 100%;">' +
                '<option value="true">True</option>' +
                '<option value="false">False</option>' +
                '</select>' +
            '</div>' +
        '</div>';
    return boolHtml;

}
function getNoneHtml(obj){
    var noneHtml =
        '<div class="form-group">' +
            '<label class="control-label col-sm-2">None 类型：</label>' +
            '<div class="col-sm-4">' +
                '<div class="checkbox checkbox-success">' +
                    '<input id="' + obj.paramName + '-none" name="' + obj.paramName + '-none" ' +
                    'class="styled" type="checkbox" checked="">' +
                    '<label for="' + obj.paramName + '-none">' +
                    '包含' +
                    '</label>' +
                '</div>' +
            '</div>' +
        '</div>';
    return noneHtml;
}

function addParamForm() {
    $("#algo-params-body").empty();
    var algoParamsJSON = $.parseJSON(algoParams);
    $.each(algoParamsJSON, function(idx, obj) {
        var content=
            '<form id="' + obj.paramName + '-form" class="form-horizontal">' +
            '<label class="control-label" style="font-size:20px;text-align: center;display:block;margin-bottom: 10px;">' +
            obj.paramName + '&nbsp候选值：' +
            '</label>' +
            '</form>';
        var hrHtml = '<hr align="center" width="100%" color="#F5F5F5" size="1">';
        $("#algo-params-body").append(content);
        if (idx != algoParamsJSON.length - 1) {
            $("#algo-params-body").append(hrHtml);
        }
        if(obj.isInt){
            $('#' + obj.paramName + '-form').append(getIntHtml(obj));
        }
        if(obj.isFloat){
            $("#" + obj.paramName + '-form').append(getFloatHtml(obj));
        }
        if(obj.isString){
            $("#" + obj.paramName + '-form').append(getStringHtml(obj));
        }
        if(obj.isBool){
            $("#" + obj.paramName + '-form').append(getBoolHtml(obj));
        }
        if(obj.isNull){
            $("#" + obj.paramName + '-form').append(getNoneHtml(obj));
        }
    });
}

function addValidator() {
    $.each($.parseJSON(algoParams), function(idx, obj) {
        $('#' + obj.paramName + '-form').bootstrapValidator({
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            }
        });
        if(obj.isInt){
            var intDownGreatMessage = '下限必须大于' + (obj.intMinInclusive?'等于 ':' ') + obj.intMin;
            $('#' + obj.paramName + '-form').bootstrapValidator('addField', $('#' + obj.paramName + '-int-down'), {
                validators: {
                    greaterThan: {
                        inclusive:obj.intMinInclusive,
                        value: obj.intMin,
                        message: intDownGreatMessage
                    },
                    lessThan: {
                        value: obj.paramName + '-int-up',
                        message: '下限必须小于上限'
                    },
                    integer: {
                        message: '请输入整数'
                    }
                }
            });
            var intUpLessMessage = '上限必须小于' + (obj.intMaxInclusive?'等于 ':' ') + obj.intMax;
            $('#' + obj.paramName + '-form').bootstrapValidator('addField', $('#' + obj.paramName + '-int-up'), {
                validators: {
                    greaterThan: {
                        value: obj.paramName + '-int-down',
                        message: '上限必须大于下限'
                    },
                    lessThan: {
                        inclusive:obj.intMaxInclusive,
                        value: obj.intMax,
                        message: intUpLessMessage
                    },
                    integer: {
                        message: '请输入整数'
                    }
                }
            });
            $('#' + obj.paramName + '-form').bootstrapValidator('addField', $('#' + obj.paramName + '-int-num'), {
                validators: {
                    greaterThan: {
                        value: 0,
                        message: '数量必须大于 0'
                    },
                    integer: {
                        message: '数量必须是整数'
                    }
                }
            });
        }

        if(obj.isFloat){
            var floatDownGreatMessage = '下限必须大于' + (obj.floatMinInclusive?'等于 ':' ') + obj.floatMin;
            $('#' + obj.paramName + '-form').bootstrapValidator('addField', $('#' + obj.paramName + '-float-down'), {
                validators: {
                    greaterThan: {
                        inclusive:obj.floatMinInclusive,
                        value: obj.floatMin,
                        message: floatDownGreatMessage
                    },
                    lessThan: {
                        value: obj.paramName + '-float-up',
                        message: '下限必须小于上限'
                    },
                    numeric: {
                        message: '请输入数字'
                    }
                }
            });
            var floatUpLessMessage = '上限必须小于' + (obj.floatMaxInclusive?'等于 ':' ') + obj.floatMax;
            $('#' + obj.paramName + '-form').bootstrapValidator('addField', $('#' + obj.paramName + '-float-up'), {
                validators: {
                    greaterThan: {
                        value: obj.paramName + '-float-down',
                        message: '上限必须大于下限'
                    },
                    lessThan: {
                        inclusive:obj.floatMaxInclusive,
                        value: obj.floatMax,
                        message: floatUpLessMessage
                    },
                    numeric: {
                        message: '请输入数字'
                    }
                }
            });
            $('#' + obj.paramName + '-form').bootstrapValidator('addField', $('#' + obj.paramName + '-float-num'), {
                validators: {
                    greaterThan: {
                        value: 0,
                        message: '数量必须大于 0'
                    },
                    integer: {
                        message: '数量必须是整数'
                    }
                }
            });
        }
    });
}

function selectValues() {
    $.each($.parseJSON(algoParams), function(idx, obj) {
        if(obj.isString){
            // for(var values = [], n = 0; n < obj.stringValues.length; values[n] = n++);
            // $("#" + obj.paramName + '-string').val(values);
            $("#" + obj.paramName + '-string').val(obj.stringValues);
            $("#" + obj.paramName + '-string').trigger('change');
            // $.each(obj.stringValues, function (key, value) {
            //     var newOption = new Option(value, key, true, true);
            //     $("#" + obj.paramName + '-string').append(newOption).trigger('change');
            // });
        }

        if(obj.isBool){
            // values = [];
            // $.each(obj.boolValues, function (key, value) {
            //     if(value){
            //         values.push(0);
            //     }
            //     if (!value){
            //         values.push(1);
            //     }
            // });
            $("#" + obj.paramName + '-bool').val(obj.boolValues);
            $("#" + obj.paramName + '-bool').trigger('change');
            // var selectIndex = 0;
            // $.each(obj.boolValues, function (key, value) {
            //     var newOption = new Option(value, key, true, true);
            //     $("#" + obj.paramName + '-bool').append(newOption).trigger('change');
            // });
        }
    });
}

function addTaskResultHtml(taskResult) {
    var resultOrder = ['progress', 'library', 'type', 'model', 'isok', 'exception', 'modelPath', 'param', 'performance'];
    $('#task-res-nor-body').empty();
    var $tr = null;
    var className = taskResult.className;
    $.each(resultOrder, function (key, value) {
        if (value === 'modelPath'){
            taskResult[value] = ['/api/task/submit/download/model', taskResult[value]]
        }
        $tr = $('<tr></tr>');
        $tr.append('<td>' + getChinese(value) + '</td>');
        $tr.append(getValueTd(value, taskResult[value], className));
        $('#task-res-nor-body').append($tr);
    });

}
function processRecover(submitParams, taskId) {

    processHtmlAndVar(submitParams);
    initTaskProcessHtml();

    // 建立WebSocket接受任务实时输出
    submit_task.startWebSocketHtml5();
    webSocket.onopen = function () {
        console.log("任务进度 websocket 连接上");
        // 拉取离线期间任务输出
        $.ajax({
            url: "/api/task/submit/task/detail/offline",
            type: "get",
            data: {'taskId' : taskId}
        });
    };

}
function processRestart(submitParams){
    processHtmlAndVar(submitParams);
    initTaskProcessHtml();
    checkAndStartTask();

    // submit_task.startWebSocketHtml5();
    // webSocket.onopen = function () {
    //     console.log("任务进度 websocket 连接上");
    //     checkAndStartTask();
    // };
}
function processHtmlAndVar(submitParams) {
    algoId = submitParams.algoId;
    algoLib = submitParams.algoLib;
    algoName = submitParams.algoName;
    algoParams = submitParams.algoParams;
    newAlgoParams = JSON.parse(submitParams.newAlgoParams);

    evaType = submitParams.evaType;

    optAlgoId = submitParams.optAlgoId;
    optAlgoName = submitParams.optAlgoName;
    optAlgoParams = submitParams.optAlgoParams;
    newOptAlgoParams = JSON.parse(submitParams.newOptAlgoParams);

    fileInfo = JSON.parse(submitParams.fileInfo);
    fileTypeValue = fileInfo.fileType;

    $("#algo-lib").empty();
    $("#algo-lib").append(new Option(algoLib, algoLib, true, true)).trigger('change');

    var algoType = submitParams.algoType;
    $("#algo-type").prop("disabled", false);
    $("#algo-type").empty();
    $("#algo-type").append(new Option(algoType, algoType, true, true)).trigger('change');

    $("#algo-name").prop("disabled", false);
    $("#algo-name").empty();
    $("#algo-name").append(new Option(algoName, algoId, true, true)).trigger('change');

    $("#file-history-div").css('display','block');
    $("#fileupload-start-div").attr("style", 'visibility: hidden;');
    $("#file-property-span").css('display','block');
    $("#file-history").empty();
    $("#file-history").append(new Option(fileInfo.originName, fileInfo.fileId, true, true)).trigger('change');
    $("#file-type").prop("disabled", true);
    // $("#file-type").empty();
    // $("#file-type").append(new Option(fileInfo.fileType, fileInfo.fileType, true, true)).trigger('change');
    // 不需要清空，因为是写死的，只有libsvm和csv两个选项
    $('#file-type').val(fileInfo.fileType).trigger('change');
    $("#is-col-name").prop("disabled", true);
    $("#is-col-name").prop("checked",fileInfo.colName);
    $("#is-label").prop("disabled", true);
    $("#is-label").prop("checked",fileInfo.cluLabel);

    $("#eva-type").prop("disabled", true);
    $("#eva-type").empty();
    $("#eva-type").append(new Option(evaType, evaType, true, true)).trigger('change');
    // 暂时禁用，只能选贝叶斯优化，等后续后端脚本改进再让用户自定义
    $("#opt-algo-name").prop("disabled", true);
    $("#opt-algo-name").empty();
    $("#opt-algo-name").append(new Option(optAlgoName, optAlgoId, true, true)).trigger('change');
    addOptAlgoHtml(JSON.parse(optAlgoParams));
    addOptAlgoParamsValue(newOptAlgoParams);
    $('#task-params-collapse').collapse('hide');

    addParamForm();
    addValidator();
    $('.select2-multiple').select2({
        theme: "bootstrap"
    });
    // selectValues();
    addAlgoParamsValue(newAlgoParams);
    $('#algo-params-a').attr('href', '#algo-params-collapse');
    $('#algo-params-collapse').collapse('hide');
}

function addOptAlgoParamsValue(newOptAlgoParams){
    $.each(newOptAlgoParams, function (key, value) {
        $('#' + value.name).val(value.val);
    });
}
function addAlgoParamsValue(newAlgoParams){
    $.each(newAlgoParams, function (idx, obj) {
        if(obj.isInt){
            $('#' + obj.paramName + '-int-down').val(obj.intDownValue);
            $('#' + obj.paramName + '-int-up').val(obj.intUpValue);
            $('#' + obj.paramName + '-int-num').val(obj.intNum);
        }
        if(obj.isFloat){
            $('#' + obj.paramName + '-float-down').val(obj.floatDownValue);
            $('#' + obj.paramName + '-float-up').val(obj.floatUpValue);
            $('#' + obj.paramName + '-float-num').val(obj.floatNum);
        }
        if(obj.isString){
            $("#" + obj.paramName + '-string').val(obj.stringValues);
            $("#" + obj.paramName + '-string').trigger('change');
        }
        if(obj.isBool){
            $("#" + obj.paramName + '-bool').val(obj.boolValues);
            $("#" + obj.paramName + '-bool').trigger('change');
        }
    });
}