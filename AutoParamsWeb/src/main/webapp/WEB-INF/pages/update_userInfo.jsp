<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="include/head.jsp">
    <jsp:param name="title" value="个人信息更新"/>
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
                <h4 class="page-title">个人信息更新</h4>
            </div>
            <!-- /.page title -->
        </div>
        <!-- .Your content row -->
        <div class="white-box">
            <h3 class="box-title"></h3>

            <div class="form-group hidden">
                <span>ID:</span>
                <input id="edit-user-id" class="form-control" disabled="disabled">
            </div>
            <div class="form-group">
                <span>用户名:</span>
                <input id="edit-user-username" class="form-control" disabled="disabled">
            </div>
            <div class="form-group">
                <span>昵称:</span>
                <input id="edit-user-nickname" class="form-control">
            </div>
            <div class="form-group">
                <span>邮箱:</span>
                <input id="edit-user-email" class="form-control">
            </div>
            <div class="form-group">
                <span>电话:</span>
                <input id="edit-user-tel" class="form-control">
            </div>
            <div class="form-group">角色列表:
                <div id="edit-user-role" class="ft-flex-box"></div>
            </div>
            <div class="modal-footer">
                <button id="saveSubmit" class="btn btn-success waves-effect waves-light"
                        onclick="updateUserInfo.saveEdit()">保存
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
<script src="/js/custom/update_userInfo.js"></script>
<script>
    $(document).ready(function () {
        window.updateUserInfo.init();
    });
</script>
