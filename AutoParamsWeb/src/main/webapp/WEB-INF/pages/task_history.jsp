<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="include/head.jsp">
    <jsp:param name="title" value="任务历史"/>
</jsp:include>
<style>
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
    .dropdown-menu{
        padding: 0px;
    }
    .table-footer{
        margin-top: 10px;
    }
    input[type=search]{
        border-radius:5px;
    }
    div.dt-buttons {
        clear: both;
    }
    tr.selected a, table.dataTable tbody th.selected a, table.dataTable tbody td.selected a {
        color: #ffffff !important;
    }
    tr.selected i, table.dataTable tbody th.selected i, table.dataTable tbody td.selected i {
        color: #ffffff !important;
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
                <h4 class="page-title">查看历史数据</h4>
            </div>
            <!-- /.page title -->
        </div>
        <!-- .Your content row -->
        <div class="panel panel-default">
            <%--table--%>
            <div class="panel-wrapper collapse in" aria-expanded="true">
                <%--<div class="box box-primary table-header-line">--%>
                <div class="panel-body">
                    <table class="table table-striped table-bordered dataTable display nowrap" id="task-history">
                        <thead>
                            <tr>
                                <th data-field="task_id">任务ID</th>
                                <th data-field="task_status">任务状态</th>
                                <th data-field="submit_time">提交时间</th>
                                <th data-field="done_time">完成时间</th>
                                <th data-field="task_result">任务结果</th>
                                <th data-field="task_detail">任务详细输出</th>
                                <th data-field="file_info">训练集</th>
                                <th data-field="algo_lib">算法库</th>
                                <th data-field="algo_name">机器学习算法</th>
                                <th data-field="algo_params">机器学习算法参数</th>
                                <th data-field="opt_algo_name">调参算法</th>
                                <th data-field="opt_algo_params">调参算法参数</th>
                                <th data-field="op">操作</th>
                            </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                </div>
                <%--</div>--%>
            </div>

            <%--task-result--%>
            <div id="task-res-modal" class="modal fade" role="dialog"
                 data-backdrop="false" aria-hidden="true" style="display: none;">
                <div id="task-res-modal-dialog" class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">任务结果详情</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row" id="task-res-ab-div" style="display: none;text-align: center;"></div>
                            <div class="pre-scrollable" id="task-res-nor-div" style="display: none">
                                <table class="table">
                                    <tbody id="task-res-nor-body"></tbody>
                                </table>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default waves-effect" data-dismiss="modal">关闭</button>
                        </div>
                    </div>
                </div>
            </div>

            <%--task-detail--%>
            <div id="task-detail-modal" class="modal fade" role="dialog"
                 data-backdrop="false" aria-hidden="true" style="display: none;">
                <div id="task-detail-model-dialog" class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">任务详细输出</h4>
                        </div>
                        <div class="modal-body">
                            <div class="pre-scrollable process-info-log" id="info-log">
                                <ol id="js-info-log"></ol>
                            </div>
                            <div class="pre-scrollable process-error-log" id="error-log">
                                <ol id="js-error-log"></ol>
                            </div>
                            <label class="control-label" style="width:100%;text-align: center;font-size:20px;margin-bottom: 20px;">
                                算法评估曲线
                            </label>
                            <div id="algo-evaluate-curve" style="margin: auto;width: 100%"></div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default waves-effect" data-dismiss="modal">关闭</button>
                        </div>
                    </div>
                </div>
            </div>

            <%--file-info--%>
            <div id="file-modal" class="modal fade" role="dialog"
                 data-backdrop="false" aria-hidden="true" style="display: none;">
                <div id="file-modal-dialog" class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">文件详细信息</h4>
                        </div>
                        <div class="modal-body">
                            <div class="pre-scrollable" id="file-div">
                                <table class="table">
                                    <tbody id="file-body"></tbody>
                                </table>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default waves-effect" data-dismiss="modal">关闭</button>
                        </div>
                    </div>
                </div>
            </div>
            <%--algo-params--%>
            <div id="algo-modal" class="modal fade" role="dialog"
                 data-backdrop="false" aria-hidden="true" style="display: none;">
                <div id="algo-modal-dialog" class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">算法参数</h4>
                        </div>
                        <div class="modal-body">
                            <div class="pre-scrollable" id="algo-div">
                                <table class="table">
                                    <tbody id="algo-body"></tbody>
                                </table>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default waves-effect" data-dismiss="modal">关闭</button>
                        </div>
                    </div>
                </div>
            </div>
            <%--opt-algo-params--%>
            <div id="opt-algo-modal" class="modal fade" role="dialog"
                 data-backdrop="false" aria-hidden="true" style="display: none;">
                <div id="opt-algo-modal-dialog" class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">优化算法参数</h4>
                        </div>
                        <div class="modal-body">
                            <div class="pre-scrollable" id="opt-algo-div">
                                <table class="table">
                                    <tbody id="opt-algo-body"></tbody>
                                </table>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default waves-effect" data-dismiss="modal">关闭</button>
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
<script src="/js/ministry.js"></script>
<script src="/js/echarts/echarts.min.js"></script>
<script src="/js/custom/task_history.js"></script>

<script>
    $(function(){
        initTaskHistory();
    })
</script>