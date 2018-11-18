<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="include/head.jsp">
    <jsp:param name="title" value="用户管理"/>
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
                <h4 class="page-title">用户管理</h4>
            </div>
            <!-- /.page title -->
        </div>
        <!-- .Your content row -->
        <div class="panel panel-default">
            <div class="col-sm-12 col-md-12 col-lg-12 line-padding">
                <a class="btn btn-outline btn-success " data-toggle="modal" data-target="#addModal"
                   style="float: right;cursor: pointer">
                    <span>添加用户</span>
                </a>
            </div>
            <%--table--%>
            <div class="panel-wrapper collapse in" aria-expanded="true">
                <%--<div class="box box-primary table-header-line">--%>
                <div class="panel-body">
                    <table class="table table-bordered" id="userList">
                        <thead>
                        <tr>
                            <th data-field="id">用户ID</th>
                            <th data-field="username">用户名</th>
                            <th data-field="nickname">昵称</th>
                            <th data-field="email">邮箱</th>
                            <th data-field="tel">电话</th>
                            <th data-field="state">状态</th>
                            <th data-field="id">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
                <%--</div>--%>
            </div>
            <%--info--%>
            <div id="infoModal" class="modal fade" role="dialog" aria-labelledby="infoModalLabel"
                 data-backdrop="false"
                 aria-hidden="true" style="display: none;">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">详细信息</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="form-group hidden">
                                    <div class="col-sm-4" style="text-align: right;">ID:</div>
                                    <div class="col-sm-8">
                                        <div id="user-id">1</div>
                                    </div>
                                </div>
                                <div class="form-group col-sm-12">
                                    <div class="col-sm-4" style="text-align: right;">用户名:</div>
                                    <div class="col-sm-8">
                                        <div id="user-username"></div>
                                    </div>
                                </div>
                                <div class="form-group col-sm-12">
                                    <div class="col-sm-4" style="text-align: right;">显示名称:</div>
                                    <div class="col-sm-8">
                                        <div id="user-nickname"></div>
                                    </div>
                                </div>
                                <div class="form-group col-sm-12">
                                    <div class="col-sm-4" style="text-align: right;">邮箱:</div>
                                    <div class="col-sm-8">
                                        <div id="user-email"></div>
                                    </div>
                                </div>
                                <div class="form-group col-sm-12">
                                    <div class="col-sm-4" style="text-align: right;">电话:</div>
                                    <div class="col-sm-8">
                                        <div id="user-tel"></div>
                                    </div>
                                </div>
                                <div class="form-group col-sm-12">
                                    <div class="col-sm-4" style="text-align: right;">创建时间:</div>
                                    <div class="col-sm-8">
                                        <div id="user-createTime"></div>
                                    </div>
                                </div>
                                <div class="form-group col-sm-12">
                                    <div class="col-sm-4" style="text-align: right;">最后登录时间:</div>
                                    <div class="col-sm-8">
                                        <div id="user-lastLoginTime"></div>
                                    </div>
                                </div>
                                <div class="form-group col-sm-12">
                                    <div class="col-sm-4" style="text-align: right;">角色列表:</div>
                                    <div class="col-sm-8">
                                        <div id="user-role"></div>
                                    </div>
                                </div>
                                <div class="form-group col-sm-12">
                                    <div class="col-sm-4" style="text-align: right;">拥有的权限:</div>
                                    <div class="col-sm-8">
                                        <div id="user-authority"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default waves-effect" data-dismiss="modal">关闭</button>
                        </div>
                    </div>
                </div>
            </div>
            <%--add--%>
            <div id="addModal" class="modal fade" role="dialog" aria-labelledby="addModalLabel"
                 data-backdrop="false"
                 aria-hidden="true" style="display: none;">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">新建用户</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <span>用户名:</span>
                                <input id="add-username" class="form-control">
                            </div>
                            <div class="form-group">
                                <span>昵称:</span>
                                <input id="add-nickname" class="form-control">
                            </div>
                            <div class="form-group">
                                <span>密码:</span>
                                <input id="add-password" class="form-control" type="password">
                            </div>
                            <div class="form-group">
                                <span>确认密码:</span>
                                <input id="add-checkPwd" class="form-control" type="password">
                            </div>
                            <div class="form-group">
                                <span>联系邮箱:</span>
                                <input id="add-email" class="form-control">
                            </div>
                            <div class="form-group">
                                <span>联系电话:</span>
                                <input id="add-tel" class="form-control">
                            </div>
                            <div class="form-group">角色:
                                <div id="add-role" class="ft-flex-box"></div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button id="clearAdd" class="btn btn-default waves-effect"
                                    data-dismiss="modal" onclick="userManage.clearAdd()">取消
                            </button>
                            <button id="saveAdd" class="btn btn-success waves-effect waves-light"
                                    onclick="userManage.saveAdd()">保存
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <%--edit--%>
            <div id="editModal" class="modal fade" role="dialog" aria-labelledby="editModalLabel"
                 data-backdrop="false"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <%--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>--%>
                            <h4 class="modal-title">编辑用户信息</h4>
                        </div>
                        <div class="modal-body">
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
                        </div>
                        <div class="modal-footer">
                            <button id="clearEdit" class="btn btn-default waves-effect"
                                    data-dismiss="modal" onclick="userManage.clearEdit()">取消
                            </button>
                            <button id="saveEdit" class="btn btn-success waves-effect waves-light"
                                    onclick="userManage.saveEdit()">保存
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <%--resetPassword--%>
            <div id="resetPasswordModal" class="modal fade" role="dialog"
                 aria-labelledby="deleteModalLabel" data-backdrop="false"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <%--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>--%>
                            <h4 class="modal-title">重置密码</h4>
                        </div>
                        <div class="modal-body" id="changeUserPass">
                            <div class="hidden">
                                <label for="userId">用户id:</label>
                                <input id="userId">
                            </div>
                            <div class="hidden">
                                <label for="oldPassword">原密码:</label>
                                <input id="oldPassword">
                            </div>
                            <div>
                                <label for="newPassword">新密码:</label>
                                <input id="newPassword" name="newPassword" type="password" class="form-control"
                                       placeholder="请输入新密码！" onfocus="userManage.removeHint()">
                                <b id="errorNewPassword" class="errorPassword" hidden style="color: red"></b>
                                <br>
                            </div>
                            <div>
                                <label for="confirmPassword">确认新密码:</label>
                                <input id="confirmPassword" name="confirmPassword" type="password" class="form-control"
                                       placeholder="请再次输入新密码！" onfocus="userManage.removeHint()">
                                <b id="errorConfirmPassword" class="errorPassword" hidden style="color: red"></b>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button id="cancelResetPassword" class="btn btn-default waves-effect"
                                    data-dismiss="modal" onclick="userManage.cancelResetPassword()">取消
                            </button>
                            <button id="resetPassword" class="btn btn-success waves-effect waves-light"
                                    onclick="userManage.resetPasswordSave();">确认
                            </button>
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
<script src="/js/custom/admin_user_manage.js"></script>
<script>
    $(document).ready(function () {
        window.userManage.init();
    });
</script>

