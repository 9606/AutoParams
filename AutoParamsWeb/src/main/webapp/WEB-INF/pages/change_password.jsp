<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="include/head.jsp">
    <jsp:param name="title" value="修改密码"/>
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
                <h4 class="page-title">密码修改</h4>
            </div>
            <!-- /.page title -->
        </div>
        <!-- .Your content row -->
        <div class="white-box">
            <h3 class="box-title"></h3>

            <div>
                <label for="oldPassword">原密码:</label>
                <input id="oldPassword" name="oldPassword" type="password" class="form-control"
                       placeholder="请输入原密码！" onfocus="changePwd.removeHint()">
                <b class="errorPassword" id="errorOldPassword" hidden style="color: red"></b>
                <br>
            </div>
            <div>
                <label for="newPassword">新密码:</label>
                <input id="newPassword" name="newPassword" type="password" class="form-control"
                       placeholder="请输入新密码！" onfocus="changePwd.removeHint()">
                <b class="errorPassword" id="errorNewPassword" hidden style="color: red"></b>
                <br>
            </div>
            <div>
                <label for="confirmPassword">确认新密码:</label>
                <input id="confirmPassword" name="confirmPassword" type="password" class="form-control"
                       placeholder="再次输入新密码！" onfocus="changePwd.removeHint()">
                <b class="errorPassword" id="errorPassword" hidden style="color: red"></b>
            </div>
            <div class="hidden"><input id="userId" value="${userId}"></div>
            <div class="modal-footer">
                <%--<button type="button" class="btn btn-default waves-effect" onclick="javascript:history.back(-1)">取消</button>--%>
                <button id="changePassSubmit" class="btn btn-success waves-effect waves-light"
                        onclick="changePwd.changePassSubmit()">确定
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
<script src="/js/custom/change_password.js"></script>