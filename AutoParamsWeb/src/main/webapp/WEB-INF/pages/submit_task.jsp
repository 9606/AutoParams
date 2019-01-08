<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="include/head.jsp">
    <jsp:param name="title" value="提交新任务"/>
</jsp:include>
<style>
    input::-webkit-input-placeholder { /* WebKit browsers */
        font-size:12px;
    }
    input:-moz-placeholder { /* Mozilla Firefox 4 to 18 */
        font-size:14px;
    }
    input::-moz-placeholder { /* Mozilla Firefox 19+ */
        font-size:14px;
    }
    input:-ms-input-placeholder { /* Internet Explorer 10+ */
        font-size:14px;
    }
    .btn-group-md > .btn {
        padding: 10px 10px;
        font-size: 14px;
        line-height: 1.3333333;
        border-radius: 6px;
    }
    .btn {
        padding: 10px 16px;
        font-size: 14px;
        line-height: 1.3333333;
        border-radius: 6px;
    }
    input[type=text],textarea{
        border-radius:5px;
    }
    .process-info-log,.process-error-log {
        background-color: #f5f5f5;
        border: 1px solid #ccc;
        border-radius: 6px;
        font-family: Menlo,'Bitstream Vera Sans Mono','DejaVu Sans Mono',Monaco,Consolas,monospace,sans-serif;
        font-size: 13px;
        line-height: 1.42857143;
        word-break: break-all;
        word-wrap: break-word;
        margin: 0 0 15px;
        padding: 45px 15px 15px;
        position: relative;
    }

    .process-info-log:after,.process-error-log:after {
        position: absolute;
        top: 15px;
        left: 15px;
        font-size: 12px;
        font-weight: 700;
        color: #BBB;
        text-transform: uppercase;
        letter-spacing: 1px
    }
    .process-info-log:after {
        content: "Info Log";
    }
    .process-error-log:after {
        content: "Error Log";
    }

</style>
<jsp:include page="include/top.jsp"/>
<jsp:include page="include/sidebar.jsp"/>

<!-- Main Content -->
<!-- Start Page wrapper -->
<div id="page-wrapper">

    <div class="container-fluid">
        <div class="row bg-title">
            <!-- .page title -->
            <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                <h4 class="page-title">提交新任务</h4>
            </div>
            <!-- /.page title -->
        </div>

        <!-- .Your content row -->
        <div class="panel panel-default">
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel-group" id="panel-task">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <a class="panel-title" data-toggle="collapse" data-parent="#panel-task" href="#task-params-collapse">Step1：选择任务参数</a>
                                </div>
                                <div id="task-params-collapse" class="panel-collapse collapse in">
                                    <div class="panel-body" id="task-params-collapse-body">

                                        <div class="row">
                                            <div class="form-group">
                                                <label class="col-md-2 control-label">
                                                    <span class="text-danger">*</span>&nbsp机器学习算法：
                                                </label>
                                                <div class="col-md-3">
                                                    <select id="algo-lib" class="form-control" style="width: 75%">
                                                    </select>
                                                </div>
                                                <div class="col-md-3">
                                                    <select id="algo-type" class="form-control" onchange="changeAlgoType();" style="width: 75%">
                                                    </select>
                                                </div>
                                                <div class="col-md-3">
                                                    <select id="algo-name" class="form-control" style="width: 100%">
                                                    </select>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="row" style="margin-top: 20px;">
                                            <div class="form-group">
                                                <label class="col-md-2 control-label">
                                                    <span class="text-danger">*</span>&nbsp上传训练集：
                                                </label>
                                                <div class="col-md-6 btn-group btn-group-md">
                                                    <button class="btn btn-default" type="button" onclick="showHistory()">
                                                        <em class="fa fa-history"></em> 选择旧数据
                                                    </button>
                                                    <button class="btn btn-default" type="button" onclick="showNew()">
                                                        <em class="fa fa-plus-circle"></em> 上传新数据
                                                    </button>
                                                </div>
                                                <div class="col-md-offset-2 col-md-10" style="margin-top: 20px">
                                                    <div class="alert alert-danger alert-dismissable" id="file-alert">
                                                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                                                            ×
                                                        </button>
                                                        <h4>
                                                            注意训练集格式!
                                                        </h4> 您既可以选择之前上传的训练集，又可以上传新的训练集，但是如果您想新上传数据集，请确保您新上传的训练集符合以下要求：<br/><strong>
                                                        1. 训练集和页面中的您勾选的选项一致，选项包括：训练集格式、训练集是否存在列名、聚类训练集是否存在标签。<br/>
                                                        2. 数据若有缺失值，缺失值符号请用 ? 或者 NA。<br/>
                                                        3. 分类、回归和有标签的聚类的数据集的标签在最后一列。<br/></strong>
                                                    </div>
                                                </div>


                                                <div class="col-md-offset-2 col-md-3" id="file-history-div" style="display: none;">
                                                    <select id="file-history" class="form-control" style="width: 75%"></select>
                                                </div>
                                                <div class="col-md-offset-2 col-md-3" id="new-file-div" style="display: none;">
                                                    <span class="btn btn-success fileinput-button" id="fileupload-span" style="width: 75%">
                                                        <i class="fa fa-plus-circle"></i>
                                                        <span>从本地选择训练集</span>
                                                        <input id="fileupload" type="file" name="file" data-url="/api/task/submit/upload/train">
                                                    </span>
                                                </div>
                                                <div class="col-md-3" id="fileupload-start-div" style="display: none;">
                                                    <button type="button" id="fileupload-start" class="btn btn-danger">
                                                        <i class="fa fa-upload"></i>
                                                        <span>上传训练集</span>
                                                    </button>
                                                </div>


                                                <span id="file-property-span" style="display: none;">
                                                    <div class="col-md-offset-2 col-md-3" id="file-type-div" style="margin-top: 20px;">
                                                            <select id="file-type" class="form-control" style="width: 75%">
                                                                <option></option>
                                                                <option value="csv">csv</option>
                                                                <option value="libsvm">libsvm</option>
                                                            </select>
                                                    </div>
                                                    <div class="col-md-2" id="is-col-name-div" style="margin-top: 20px;">
                                                        <div class="checkbox checkbox-success">
                                                            <input id="is-col-name" name="is-col-name" class="styled" type="checkbox"
                                                                   checked="" disabled="disabled" <%--data-trigger="hover"--%>
                                                                   <%--data-toggle="tooltip" data-placement="top" title="勾选则表示训练集存在列名"--%>>
                                                            <label for="is-col-name" data-trigger="hover" data-toggle="tooltip" data-placement="top" title="勾选则表示训练集存在列名">存在列名</label>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-4" id="is-label-div" style="margin-top: 20px;">
                                                        <div class="checkbox checkbox-success">
                                                            <input id="is-label" name="is-label" class="styled" type="checkbox"
                                                                   disabled="disabled" <%--data-trigger="hover"--%>
                                                                   <%--data-toggle="tooltip" data-placement="top" title="勾选则表示聚类训练集存在标签"--%>>
                                                            <label for="is-label" data-trigger="hover" data-toggle="tooltip" data-placement="top" title="勾选则表示聚类训练集存在标签">聚类存在标签（该选项只针对聚类有效）</label>
                                                        </div>
                                                    </div>
                                                </span>


                                                <!-- The global progress bar -->
                                                <span id="file-progress-span" style="display: none;">
                                                    <div class="col-md-offset-2 col-md-3" style="margin-top: 20px;">文件上传进度：</div>
                                                    <div class="col-md-7" style="margin-top: 20px;">
                                                        <div id="file-progress" class="progress">
                                                            <div class="progress-bar progress-bar-success"></div>
                                                        </div>
                                                    </div>
                                                </span>


                                                <!-- The container for the uploaded files -->
                                                <div id="file-info" class="col-md-offset-2 col-md-10"></div>
                                            </div>
                                        </div>

                                        <div class="row" style="margin-top: 20px;">
                                            <div class="form-group">
                                                <label class="col-md-2 control-label">
                                                    <span class="text-success">*</span>&nbsp算法优化方向：
                                                </label>
                                                <div class="col-md-3">
                                                    <select id="eva-type" class="form-control" style="width: 75%"></select>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="row" style="margin-top: 20px;" id="opt-algo-div">
                                            <div class="form-group" id="opt-algo-fg">
                                                <label class="col-md-2 control-label">
                                                    <span class="text-success">*</span>&nbsp调参算法：
                                                </label>
                                                <div class="col-md-3">
                                                    <select id="opt-algo-name" class="form-control" style="width: 75%"></select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row" id="opt-algo-params-div"></div>

                                        <div class="row">
                                            <button id="submitTaskParams" class="btn btn-success waves-effect waves-light" onclick="checkTaskParams();" style="float:right;">
                                                保存任务参数
                                            </button>
                                        </div>

                                    </div>
                                </div>
                            </div>
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <a id="algo-params-a" class="panel-title collapsed" data-toggle="collapse" data-parent="#panel-task" href="#">Step2：选择算法参数</a>
                                </div>
                                <div id="algo-params-collapse" class="panel-collapse collapse">
                                    <div class="panel-body" id="algo-params-collapse-body">
                                        <div id="algo-params-body"></div>
                                        <div class="row">
                                            <button id="submitTask" class="btn btn-success waves-effect waves-light" onclick="checkAlgoParams();" style="float:right;">
                                                提交任务
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <a id="process-a" class="panel-title collapsed" data-toggle="collapse" data-parent="#panel-task" href="#">Step3：查看任务进度</a>
                                </div>
                                <div id="process-collapse" class="panel-collapse collapse">
                                    <div class="panel-body" id="process-collapse-panel">
                                        <div class="col-md-12" style="margin-bottom: 20px;display: none;" id="info-log-div">
                                            <%--overflow-y:auto; overflow-x:auto;--%>
                                            <div class="pre-scrollable process-info-log" style="height:100%;" id="info-log">
                                                <ol id="js-info-log"></ol>
                                                <button type="button" class="close" style="position: absolute;top: 10px;right:15px;"
                                                        onclick="(function(){$('#info-log-div').css('display', 'none');
                                                        $('#info-log-alert').css('display', 'block');})()">
                                                    ×
                                                </button>
                                            </div>
                                        </div>
                                        <div class="col-md-12">
                                            <div class="alert alert-success alert-dismissable" id="info-log-alert">
                                                <a href="javascript:void(0)" onclick="(function(){$('#info-log-div').css('display', 'block');
                                                $('#info-log-alert').css('display', 'none');})()" class="alert-link">点此查看任务详细日志</a>
                                            </div>
                                        </div>
                                        <div class="col-md-12" style="margin-bottom: 20px;display: none;" id="err-log-div">
                                            <div class="pre-scrollable process-error-log" style="height:100%;" id="error-log">
                                                <ol id="js-error-log"></ol>
                                                <button type="button" class="close" style="position: absolute;top: 10px;right:15px;"
                                                        onclick="(function(){$('#err-log-div').css('display', 'none');
                                                        $('#err-log-alert').css('display', 'block');})()">
                                                    ×
                                                </button>
                                            </div>
                                        </div>
                                        <div class="col-md-12">
                                            <div class="alert alert-danger alert-dismissable" id="err-log-alert">
                                                <a href="javascript:void(0)" onclick="(function(){$('#err-log-div').css('display', 'block');
                                                $('#err-log-alert').css('display', 'none');})()" class="alert-link">点此查看任务错误详细日志</a>
                                            </div>
                                        </div>
                                        <div class="col-md-12">
                                            <div class="alert alert-info alert-dismissable" id="result-alert">
                                                <a href="javascript:void(0)" onclick="(function(){$('#task-res-nor-div').toggle('normal');
                                                })();" class="alert-link">点此查看或隐藏最后一次输出详情</a>
                                            </div>
                                        </div>
                                        <div class="col-md-12 pre-scrollable" id="task-res-nor-div" style="width:100%;display: none;">
                                            <table class="table">
                                                <tbody id="task-res-nor-body"></tbody>
                                            </table>
                                        </div>
                                        <div class="col-md-12" style="margin-top: 20px;">
                                            <label id="task-label" style="width: 100%;text-align: left;">任务进度: 0 / 0</label>
                                        </div>
                                        <div class="col-md-10" style="margin-bottom: 20px;">
                                            <div id="task-progress" class="progress" style="margin-top: 10px;margin-bottom: 10px;">
                                                <div class="progress-bar progress-success"></div>
                                            </div>
                                        </div>
                                        <div class="col-md-2" style="margin-bottom: 20px">
                                            <button id="stopTask" class="btn btn-danger waves-effect waves-light" onclick="sendStopTaskMessage();" style="float:right;">
                                                终止任务
                                            </button>
                                        </div>

                                        <label class="control-label" style="width:100%;text-align: center;font-size:20px;margin-bottom: 20px;">
                                            算法评估曲线
                                        </label>
                                        <div id="algo-evaluate-curve" style="margin: auto;width: 100%"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>

        </div>
        <!-- /.Your content row -->
    </div>
    <!-- /.container-fluid -->
    <footer class="footer text-center"></footer>
</div>
<!-- End Page wrapper -->

<jsp:include page="include/foot.jsp"/>
<script src="/js/custom/submit_task.js"></script>
<%--<script src="/js/reconnecting-websocket.js"></script>--%>
<script src="/js/echarts/echarts.min.js"></script>
<script>

    function getRunningTaskId() {
        var taskId = -1;
        $.ajax({
            url: "/api/task/submit/running/task",
            type: "get",
            async: false,
            success: function (data) {
                taskId = data.data;
            }
        });
        return taskId;
    }
    function deleteFile() {
        if(fileInfo != null){
            fileInfo = null;
            $('#file-info').empty();
            $("#file-info").attr("style","");

            fileTypeValue = '';
            $('#file-type').val('');
            $('#file-type').trigger('change');

            $("#file-history").val('');
            $('#file-history').trigger('change');

            $('#file-progress .progress-bar').css(
                'width',
                0 + '%'
            );
        }
    }

    function checkTaskParams() {
        // 验证信息是否完全
        var isSuccess = true;
        if (algoName == '') {
            swal("未选择算法", "请先选择算法再继续进行下一步操作！","error");
            isSuccess = false;
            return;
        }
        if (fileInfo == null) {
            swal("未选择或者上传数据集", "请先选择或者上传数据集！","error");
            isSuccess = false;
            return;
        }

        // 更新数值优化算法参数
        newOptAlgoParams = $.parseJSON(optAlgoParams);
        $.each(newOptAlgoParams, function (idx, obj) {
            if (obj.type == 'int'){
                var newVal = $('#' + obj.name).val();
                newOptAlgoParams[idx].val = newVal == '' ? obj.val : Number(newVal);
            } else if (obj.type == 'string'){
                var newVal = $('#' + obj.name).find("option:selected").text();
                newOptAlgoParams[idx].val = newVal == '' ? obj.val : newVal;
            }

        });

        var algoType = $("#algo-type").find("option:selected").text();

        $('#algo-params-a').attr('href', '#algo-params-collapse');
        $('#algo-params-collapse').collapse('show');

        $('#task-params-collapse').collapse('hide');
    }

    function checkAlgoParams() {
        var isSuccess = true;
        newAlgoParams = $.parseJSON(algoParams);
        $.each($.parseJSON(algoParams), function (idx, obj) {
            var bs = $('#' + obj.paramName + '-form').data('bootstrapValidator');
            // 手动触发验证
            bs.validate();
            if (bs.isValid()) {
                if (obj.isInt) {
                    var newIntDown = $('#' + obj.paramName + '-int-down').val();
                    var newIntUp = $('#' + obj.paramName + '-int-up').val();
                    newAlgoParams[idx].intDownValue = newIntDown == '' ? obj.intDownValue : Number(newIntDown);
                    newAlgoParams[idx].intUpValue = newIntDown == '' ? obj.intUpValue : Number(newIntUp);

                }
                if (obj.isFloat) {
                    var newFloatDown = $('#' + obj.paramName + '-float-down').val();
                    var newFloatUp = $('#' + obj.paramName + '-float-up').val();
                    newAlgoParams[idx].floatDownValue = newFloatDown == '' ? obj.floatDownValue : Number(newFloatDown);
                    newAlgoParams[idx].floatUpValue = newFloatUp == '' ? obj.floatUpValue : Number(newFloatUp);
                }
                if (obj.isString){
                    var stringValues = [];
                    $.each($('#' + obj.paramName + '-string').select2('data'), function (sidx, sobj) {
                        stringValues.push(sobj.text);
                    });
                    newAlgoParams[idx].stringValues = stringValues;
                }
                if(obj.isBool){
                    var boolValues = [];
                    $.each($('#' + obj.paramName + '-bool').select2('data'), function (bidx, bobj) {
                        boolValues.push(bobj ? true : false);
                    });
                    newAlgoParams[idx].boolValues = boolValues;
                }
                if(obj.isNull){
                    newAlgoParams[idx].isNull = $('#' + obj.paramName + '-none').is(':checked') ? true : false;
                }
            } else {
                swal("参数验证失败", "请检查参数候选值！","error");
                isSuccess = false;
                return;
            }
        });
        if (isSuccess){
            checkAndStartTask();
        }
    }

    function initTaskProcessHtml() {
        submit_task.initEchart();
        $('#js-info-log').empty();
        $('#js-error-log').empty();
        $('#task-res-nor-body').empty();

        $('#task-progress .progress-bar').css('width', 0 + '%');
        $('#task-label').html('任务进度: ' + 0 + ' / ' + 0);

        $('#algo-params-collapse').collapse('hide');
        $('#process-a').attr('href', '#process-collapse');
        // $("#process-collapse-panel").css({"height":window.screen.availHeight * 2 + "px"});
        $("#info-log-div").css({"height":window.screen.availHeight * 0.3  + "px"});
        $("#err-log-div").css({"height":window.screen.availHeight * 0.3  + "px"});
        $("#task-res-nor-div").css({"max-height":window.screen.availHeight * 0.8  + "px"});
        $("#algo-evaluate-curve").css({"height":window.screen.availHeight  + "px"});

        $('#process-collapse').collapse('show');
    }

    var isRestart = ${isRestart};
    taskId = ${taskId};
    if (isRestart){
        $.ajax({
            url: "/api/task/submit/params/" + taskId,
            type: "get",
            success: function (data) {
                processRestart(data.data);
            },
            error: function (data) {
                swal(data.result, "", "error");
                console.log(data);
            }
        });
    }

    $(document).ready(function () {

        initSelect2();

        $('#fileupload').fileupload({
            dataType: 'json',
            formData: function () {
                var colName = $("#is-col-name").is(':checked');
                var cluLabel = $("#is-label").is(':checked');
                return [
                    { name: 'fileType', value: fileTypeValue},
                    { name: 'colName', value: colName},
                    { name: 'cluLabel', value: cluLabel}];
            },
            add: function (e, data) {
                $('#file-info').empty();
                $("#file-info").attr("style","margin-top:20px;");
                $('<p/>').text(data.originalFiles[0].name + ' 已选择').appendTo('#file-info');
                $('#fileupload-start').click(function() {
                    data.submit();
                });
            },
            submit: function (e, data) {
                var fileType = $("#file-type").find("option:selected").text();
                if (fileType == '') {
                    swal("未选择训练集格式", "请先选择训练集格式！","error");
                    return false;
                }
                $("#file-progress-span").attr("style","display:block;");
            },
            done: function (e, data) {
                $('#file-info').empty();
                $("#file-info").attr("style","");
                $('<p/>').text(data.result.originName + ' 已上传').appendTo('#file-info');
                fileInfo = data.result;
            },
            fail: function (e, data) {
                $('#file-info').empty();
                $("#file-info").attr("style","");
                $('<p/>').text('上传失败，请重新上传。').appendTo('#file-info');
            },
            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $('#file-progress .progress-bar').css(
                    'width',
                    progress + '%'
                );
            }
        });

        taskId = getRunningTaskId();
        if (taskId >= 0){
            swal({
                    title: "您有正在运行的任务",
                    text: "系统检测到您有正在运行的任务，您可以选择恢复该任务，选择恢复原任务将回到原任务的状态界面。",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "恢复原任务！",
                    cancelButtonText: "不恢复原任务！",
                    closeOnConfirm: true,
                    closeOnCancel: true
                },
                function (isConfirm) {
                    if (isConfirm) {
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
                    }
                }
            );
        }
    });
</script>