<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="include/head.jsp">
    <jsp:param name="title" value="角色管理"/>
</jsp:include>
<jsp:include page="include/top.jsp"/>
<jsp:include page="include/sidebar.jsp"/>
<!-- Main Content -->
<!-- Start Page wrapper -->
<div id="page-wrapper">
    <div class="container-fluid">
        <div class="row bg-title">
            <!-- .page title -->
            <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                <h4 class="page-title">报警规则管理</h4>
            </div>
            <!-- /.page title -->
        </div>
        <!-- .Your content row -->
        <div class="panel panel-default">

            <div class="panel-body">
                <div class="modal-body">
                    <div class="form-group" hidden="hidden">
                        <span>id:</span>
                        <span id="edit-id"></span>
                    </div>
                    <div class="form-group">
                        <span>风速下限:</span>
                        <input id="edit-ws-down" class="form-control">
                        <div id="error-ws-down" class="verify-error-message"></div>
                    </div>
                    <div class="form-group">
                        <span>风速上限:</span>
                        <input id="edit-ws-up" class="form-control">
                    </div>
                    <div class="form-group">
                        <span>风速空值:</span>
                        <input id="edit-ws-null" class="form-control">
                        <div id="error-ws-null" class="verify-error-message"></div>
                    </div>
                    <div class="form-group">
                        <span>功率下限:</span>
                        <input id="edit-pDown" class="form-control">
                    </div>
                    <div class="form-group">
                        <span>功率上限:</span>
                        <input id="edit-pUp" class="form-control">
                        <div id="error-pUp" class="verify-error-message"></div>
                    </div>
                    <div class="form-group">
                        <span>功率空值:</span>
                        <input id="edit-pNull" class="form-control">
                    </div>

                    <div class="form-group">
                        <span>报警温度:</span>
                        <input id="edit-alarmT" class="form-control">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button id="saveRules" class="btn btn-success waves-effect waves-light" onclick="alarm_rules.saveEdit();">
                    保存
                </button>
            </div>
        </div>
        <!-- /.Your content row -->

    </div>
    <!-- /.container-fluid -->
    <footer class="footer text-center"></footer>
</div>
<!-- End Page wrapper -->
<jsp:include page="include/foot.jsp"/>
<script src="/customJs/alarm_rules.js"></script>
<script>
    $(document).ready(function () {
        window.alarm_rules.init();
    });
</script>