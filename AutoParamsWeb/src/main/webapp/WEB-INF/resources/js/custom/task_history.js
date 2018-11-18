var myChart = null;
var serie = {type: 'line', smooth: false, seriesLayoutBy: 'row'};
var legendData = [];
var dimensionsData = [];
var evaValues = [];
var seriesData = [];

function initTaskHistory(){

    $("#task-res-nor-div").css({"max-height":window.screen.availHeight * 0.6  + "px"});
    $("#task-res-nor-div").css({"height":window.screen.availHeight * 0.6  + "px"});

    $("#task-res-modal").on("show.bs.modal", function (e) {
        var $click = $(e.relatedTarget);
        var taskResult = $click.data("content");
        if ($.type(taskResult) === "object"){
            var resultOrder = ['progress', 'library', 'type', 'model', 'isok', 'exception', 'modelPath', 'param', 'performance'];
            $('#task-res-nor-body').empty();
            var $tr = null;
            var className = taskResult.className;
            $.each(resultOrder, function (key, value) {
                if (value === 'modelPath'){
                    taskResult[value] = ['/api/task/manage/download/model', taskResult[value]]
                }
                $tr = $('<tr></tr>');
                $tr.append('<td>' + getChinese(value) + '</td>');
                $tr.append(getValueTd(value, taskResult[value], className));
                $('#task-res-nor-body').append($tr);
            });
            // $("#task-res-modal-dialog").css({"height":window.screen.availHeight * 0.8 + "px"});
            $("#task-res-modal-dialog").css({"width":window.screen.availWidth * 0.6 + "px"});
            $("#task-res-ab-div").css('display','none');
            $("#task-res-nor-div").css('display','block');

        } else{
            $("#task-res-ab-div").empty();
            $("#task-res-modal-dialog").css({"width":window.screen.availWidth * 0.4 + "px"});
            $("#task-res-ab-div").css('display','block');
            $("#task-res-nor-div").css('display','none');
            if ($.type(taskResult) === "string") {
                $("#task-res-ab-div").html(taskResult);
            }else {
                $("#task-res-ab-div").html('该任务暂无结果');
            }
        }
    });

    $("#task-detail-modal").on("show.bs.modal", function (e) {

        var $click = $(e.relatedTarget);
        var taskId = $click.data("id");
        showBYLoading();
        $("#task-detail-model-dialog").css({"width":window.screen.availWidth * 0.8 + "px"});

        initEchart();
        // $("#algo-evaluate-curve").css({"height":window.screen.availHeight  + "px"});
        // 截图用
        $("#algo-evaluate-curve").css({"height":window.screen.availHeight * 0.6  + "px"});

        $.ajax({
            url: "/api/task/manage/detail/{id}".replace("{id}", taskId),
            type: "get",
            success: function (data) {
                $('#js-info-log').empty();
                $('#js-error-log').empty();
                hideBYLoading();
                $.each(data.data, function (key, value) {
                    if (value.isInfo) {
                        logInfoProcess(value.detailText, value.detailTime);
                        updateTaskProcess(JSON.parse(value.detailText));
                    }else {
                        logErrorProcess(value.detailText, value.detailTime)
                    }
                });
            },
            error: function (data) {
                hideBYLoading();
                console.log(data);
            }
        })
    });
    $("#task-detail-modal").on("shown.bs.modal", function (e) {
        $("#algo-evaluate-curve").css('width', $("#algo-evaluate-curve").width());
        myChart.resize();
    });
    $("#file-modal").on("show.bs.modal", function (e) {

        var $click = $(e.relatedTarget);
        var fileId = $click.data("id");
        showBYLoading();
        $('#file-body').empty();
        $.ajax({
            url: "/api/file/detail/{id}".replace("{id}", fileId),
            type: "get",
            success: function (data) {
                hideBYLoading();
                $.each(data.data, function (key, value) {
                    if (key === 'filePath') {
                        value = ['/api/task/manage/download/file', value, data.data.originName];
                    }
                    var $tr = $('<tr></tr>');
                    $tr.append('<td>' + getChinese(key) + '</td>');
                    $tr.append(getValueTd(key, value ,null));
                    $('#file-body').append($tr);
                });
            },
            error: function (data) {
                hideBYLoading();
                console.log(data);
            }
        })
    });

    $("#algo-modal").on("show.bs.modal", function (e) {
        var $click = $(e.relatedTarget);
        var algoParams = $click.data("content");
        $('#algo-body').empty();
        $.each(algoParams, function (key, value) {
            var $tr = $('<tr></tr>');
            $tr.append('<td>' + value.paramName + '&nbsp候选值：</td>');
            var paramRangeStr = '';
            if (value.isInt){
                paramRangeStr += value.intDownValue + '&nbsp~&nbsp' + value.intUpValue;
                paramRangeStr += '&nbsp&nbsp共' + value.intNum + '个整数' + '<br/>';
            }
            if (value.isFloat) {
                paramRangeStr += value.floatDownValue + '&nbsp~&nbsp' + value.floatUpValue;
                paramRangeStr += '&nbsp&nbsp共' + value.floatNum + '个浮点数' + '<br/>';
            }
            if (value.isBool) {
                $.each(value.boolValues, function (vkey, vvalue) {
                    paramRangeStr += vvalue + '&nbsp&nbsp';
                });
                paramRangeStr += '&nbsp&nbsp共' + value.boolValues.length + '个布尔类型<br/>';
            }
            if (value.isString) {
                $.each(value.stringValues, function (vkey, vvalue) {
                    paramRangeStr += vvalue + '&nbsp&nbsp';
                });
                paramRangeStr += '&nbsp&nbsp共' + value.stringValues.length + '个枚举类型<br/>';
            }
            if (value.isNull) {
                paramRangeStr += '包含 null';
            }
            $tr.append('<td>' + paramRangeStr + '</td>');
            $('#algo-body').append($tr);
        });
    });

    $("#opt-algo-modal").on("show.bs.modal", function (e) {
        var $click = $(e.relatedTarget);
        var algoParams = $click.data("content");
        $('#opt-algo-body').empty();
        $.each(algoParams, function (key, value) {
            var $tr = $('<tr></tr>');
            $tr.append('<td>' + value.placeholder + '</td>');
            $tr.append('<td>' + value.val + '</td>');
            $('#opt-algo-body').append($tr);
        });
    });

    var taskHistoryTable = $("#task-history").DataTable({
        deferRender: true,
        paging: true,
        ordering: true,
        order: [[ 3, "desc"]],
        autoWidth: true,
        searching: true,
        scrollX:true,
        select: true,
        // scrollCollapse: true,
        // scroller:true,
        // aLengthMenu: [[10, 25, 50, -1], ["10", "25", "50", "所有"]],
        ajax: {
            url: "/api/task/manage/list",
            dataSrc: 'data'
        },
        columns: [
            {
                "data": 'taskId'
            },
            {
                "data": 'taskStatusDesc'
            },
            {
                "data": 'submitTime',
                "render": function (data, type, full, meta) {
                    return formatDateTime(data);
                }
            },
            {
                "data": 'doneTime',
                "render": function (data, type, full, meta) {
                    return formatDateTime(data);
                }
            },
            {
                "data": 'taskResult',
                "render": function (data, type, full, meta) {
                    if (type === 'export'){
                        return data;
                    }else if (type === 'print'){
                        return 'PDF 格式暂不支持该列';
                    } else {
                        var $a = $('<a href="#" data-toggle="modal" data-target="#task-res-modal"><i class="fa fa-bars text-inverse"></i><span>详情</span></a>');
                        $a.attr('data-content', data);
                        var $div = '<div class="ft-operation">' +
                            $a.prop("outerHTML")+'</div>';
                        return $div
                    }
                }
            },
            {
                "data": 'taskId',
                "render": function (data, type, full, meta) {
                    if (type === 'export'){
                        var exportString = '';
                        $.ajax({
                            url: "/api/task/manage/detail/{id}".replace("{id}", data),
                            type: "get",
                            async: false,
                            success: function (data) {
                                if (data.data == null || data.data.length == 0){
                                    exportString = '暂无任务详细输出';
                                } else {
                                    exportString = JSON.stringify(data.data);
                                }
                            },
                            error: function (data) {
                                exportString = '暂时无法获取';
                            }
                        });
                        return exportString;
                    }else if (type === 'print'){
                        return 'PDF 格式暂不支持该列';
                    }else {
                        var $div = '<div class="ft-operation">' +
                            '<a href="#" data-toggle="modal" data-target="#task-detail-modal" data-id="' + data + '"><i class="fa fa-bars text-inverse"></i><span>详情</span></a></div>';
                        return $div;
                    }

                }
            },
            {
                "data": 'fileId',
                "render": function (data, type, full, meta) {
                    if (type === 'export' || type === 'print'){
                        return full.fileName;
                    }else {
                        var $div = '<div class="ft-operation">' +
                            '<a href="#" data-toggle="modal" data-target="#file-modal" data-id="' + data + '"><i class="fa fa-bars text-inverse"></i><span>' + full.fileName + '</span></a></div>';
                        return $div;
                    }

                }
            },
            {
                "data": 'algoLib'
            },
            {
                "data": 'algoName'
            },
            {
                "data": 'algoParams',
                "render": function (data, type, full, meta) {
                    if (type === 'export'){
                        return JSON.stringify(data);
                    }else if (type === 'print'){
                        return 'PDF 格式暂不支持该列';
                    }else {
                        var $a = $('<a href="#" data-toggle="modal" data-target="#algo-modal"><i class="fa fa-bars text-inverse"></i><span>详情</span></a>');
                        $a.attr('data-content', data);
                        var $div = '<div class="ft-operation">' +
                            $a.prop("outerHTML")+'</div>';
                        return $div;
                    }

                }
            },
            {
                "data": 'optAlgoName'
            },
            {
                "data": 'optAlgoParams',
                "render": function (data, type, full, meta) {
                    if (type === 'export'){
                        return JSON.stringify(data);
                    }else if (type === 'print'){
                        return 'PDF 格式暂不支持该列';
                    }else {
                        var $a = $('<a href="#" data-toggle="modal" data-target="#opt-algo-modal"><i class="fa fa-bars text-inverse"></i><span>详情</span></a>');
                        $a.attr('data-content', data);
                        var $div = '<div class="ft-operation">' +
                            $a.prop("outerHTML")+'</div>';
                        return $div;
                    }
                }
            },
            {
                "data": 'taskId',
                "render": function (data, type, full, meta) {
                    var $div = '<div class="ft-operation">' +
                        '<a href="#" onclick="deleteTask(this)" data-id="' + data + '"><i class="fa fa-trash-o text-inverse"></i><span>删除</span></a>' +
                        '<a href="#" onclick="reStartTask(this)" data-id="' + data + '"><i class="fa fa-repeat text-inverse"></i><span>重新运行</span></a></div>';
                    return $div;
                }
            }
        ],
        dom:
        "<'row'<'col-sm-6'l><'col-sm-6'f>>" +
        "<'row'<'col-sm-6'><'col-sm-6'>>" +
        "<'row'<'col-sm-12'tr>>" +
        "<'row table-footer'<'col-sm-5'i><'col-sm-7'p>>",
        buttons: [
            {
                extend: 'copy',
                exportOptions: {
                    columns: function (idx, data, node) {
                        if (node.innerHTML === '操作' || !taskHistoryTable.column(idx).visible()) {
                            return false;
                        }else {
                            return true;
                        }
                    },
                    orthogonal: 'export'
                },
                text: '<em class="fa fa-files-o"></em> 复制表格'
            },
            {
                extend: 'csv',
                exportOptions: {
                    columns: function (idx, data, node) {
                        if (node.innerHTML === '操作' || !taskHistoryTable.column(idx).visible()) {
                            return false;
                        }else {
                            return true;
                        }
                    },
                    orthogonal: 'export'
                },
                text: '<em class="fa fa-file-text-o"></em> 导出 CSV'
            },
            {
                extend: 'excel',
                exportOptions: {
                    columns: function (idx, data, node) {
                        if (node.innerHTML === '操作' || !taskHistoryTable.column(idx).visible()) {
                            return false;
                        }else {
                            return true;
                        }
                    },
                    orthogonal: 'export'
                },
                text: '<em class="fa fa-file-excel-o"></em> 导出 Excel'
            },
            {
                extend: 'print',
                exportOptions: {
                    columns: function ( idx, data, node ) {
                        if (node.innerHTML === '操作' || !taskHistoryTable.column(idx).visible()) {
                            return false;
                        }else {
                            return true;
                        }
                    },
                    orthogonal: 'print'
                },
                text: '<em class="fa fa-file-pdf-o"></em> 导出 PDF'
            }
        ],
        language: {url: '/lang/datatable.chs.json'}
    });

    $('#task-history').on('init.dt', function(){
        new $.fn.dataTable.Buttons(taskHistoryTable, {
            buttons: [
                {
                    extend: 'selectAll',
                    text: '<em class="fa fa-check-square-o"></em> 全选'
                },
                {
                    extend: 'selectNone',
                    text: '<em class="fa fa-square-o"></em> 全不选'
                },
                {
                    text: '<em class="fa fa-trash-o"></em> 批量删除',
                    action: function (e, dt, node, conf) {
                        var selectRows = taskHistoryTable.rows({ selected: true }).data().toArray();
                        var selectRowsId = [];
                        $.each(selectRows, function(key, value){
                            selectRowsId.push(value['taskId'])
                        });
                        deleteTask(selectRowsId);
                    }
                },
                {
                    extend: 'colvis',
                    text: '<em class="fa fa-columns"></em> 列可见性'
                }
            ]
        });
        taskHistoryTable.buttons(0, null).container()
            .appendTo('#task-history_wrapper .col-sm-6:eq(3)');
        taskHistoryTable.buttons(1, null).container()
            .appendTo('#task-history_wrapper .col-sm-6:eq(2)');
        $('.dataTables_filter .input-sm').css('width', $('.dt-bootstrap .btn-group:eq(1)').width() / 2);
        $('#task-history_wrapper .col-sm-6:eq(2) .btn-group').css('float', 'left');
        $('#task-history_wrapper .col-sm-6:eq(3) .btn-group').css('float', 'right');
    });

    taskHistoryTable.on('user-select', function ( e, dt, type, cell, originalEvent ) {
        if ($.inArray($(dt.column(cell.index().column).header()).html(),
            ['任务结果', '任务详细输出', '训练集', '机器学习算法参数', '调参算法参数', '操作']) > -1) {
            e.preventDefault();
        }
    });
}


function deleteTask(task) {
    swal({
            title: "确定删除吗？",
            text: "删除之后不可恢复，请谨慎删除！",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "确定删除！",
            cancelButtonText: "取消删除！",
            closeOnConfirm: false,
            closeOnCancel: true
        },
        function (isConfirm) {
            if (isConfirm) {
                var url = $.isArray(task) ? "/api/task/manage/delete/batch" : "/api/task/manage/delete/" + $(task).data("id");
                var type = $.isArray(task) ? "post" : "get";
                var data = $.isArray(task) ? JSON.stringify(task) : '';
                $.ajax({
                    url: url,
                    type: type,
                    data: data,
                    contentType: "application/json;charset=utf-8",
                    success: function (data) {
                        if (data.result != "SUCCESS") {
                            showAlert("删除失败!", data.message, "warning", null);
                            return;
                        }
                        showAlert("删除成功!", "已删除" + data.data[0] + "项任务，" + data.data[1] + "项详细任务",
                            "success", function (isConfirm) {if (isConfirm) {$("#task-history").DataTable().ajax.reload();}});
                    },
                    error: function (data) {
                        swal('删除失败', data.message, "warning");
                    }
                })
            }
        }
    );
}
function reStartTask(task){
    $.ajax({
        url: "/api/task/manage/restart/" + $(task).data("id"),
        type: "get",
        contentType: "application/json;charset=utf-8",
        success: function (data) {
            if (data.result != "SUCCESS") {
                showAlert("重新运行任务失败!", data.message, "warning", null);
                return;
            }
            window.location.href = data.data;

        },
        error: function (data) {
            swal('重新运行任务失败', data.message, "warning");
        }
    })
}
function initEchart() {
    myChart = echarts.init(document.getElementById('algo-evaluate-curve'));
    serie = {type: 'line', smooth: false, seriesLayoutBy: 'row'};
    legendData = [];
    dimensionsData = [];
    evaValues = [];
    seriesData = [];
    var option = {
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
}
function updateTaskProcess(taskResult){
    var nowTime = parseInt(taskResult.progress.split('/')[0]);
    var evaValue = taskResult['performance'];
    evaValue['process'] = nowTime;
    evaValues.push(evaValue);
    var option = null;
    if (nowTime == 1){
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
}