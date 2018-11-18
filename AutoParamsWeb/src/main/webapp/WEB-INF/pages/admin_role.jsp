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
                <h4 class="page-title">角色管理</h4>
            </div>
            <!-- /.page title -->
        </div>
        <!-- .Your content row -->
        <div class="panel panel-default">
            <div class="panel-wrapper collapse in" aria-expanded="true">
                <div class="col-sm-12 col-md-12 col-lg-12 line-padding">
                    <div class="col-lg-9"></div>
                    <div class="col-lg-3" style="text-align: right;">
                        <a href="javascript:;" class="btn btn-outline btn-success " data-toggle="modal"
                           data-target="#addModal">
                            <span>添加角色</span>
                        </a>
                    </div>
                </div>
                <div class="panel-body">
                    <table class="table table-bordered" id="roleTable">
                        <thead>
                        <tr>
                            <th data-field="roleName">角色名称</th>
                            <th data-field="description">角色描述</th>
                            <th data-field="permission">权限列表</th>
                            <th data-field="">操作</th>
                        </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <%--info--%>
        <div id="perInfoModal" class="modal fade" role="dialog" aria-labelledby="perInfoModalLabel"
             data-backdrop="false"
             aria-hidden="true" style="display: none;">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">该用户权限</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <%--<span>该用户权限:</span>--%>
                            <ul id="info-permission">

                            </ul>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default waves-effect" data-dismiss="modal"
                                onclick="role.closePerInfoModal();">关闭
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <%--edit--%>
        <div id="editModal" class="modal fade" role="dialog" aria-labelledby="editModalLabel" data-backdrop="false"
             aria-hidden="true" style="display: none;">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">编辑</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group" hidden="hidden">
                            <span>id:</span>
                            <span id="edit-id"></span>
                        </div>
                        <div class="form-group">
                            <span>角色名称:</span>
                            <input id="edit-name" class="form-control">
                            <div id="error-editName" class="verify-error-message"></div>
                        </div>
                        <div class="form-group">
                            <span>角色描述:</span>
                            <input id="edit-description" class="form-control">
                        </div>
                        <div class="form-group">权限
                            <div id="edit-permission" class="ft-flex-box"></div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button id="clearEdit" class="btn btn-default waves-effect" data-dismiss="modal"
                                onclick="role.clearEdit();">取消
                        </button>
                        <button id="saveEdit" class="btn btn-success waves-effect waves-light"
                                onclick="role.saveEdit();">保存
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <%--add--%>
        <div id="addModal" class="modal fade" role="dialog" aria-labelledby="addModalLabel" data-backdrop="false"
             aria-hidden="true" style="display: none;">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">添加角色</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <span>角色名称:</span>
                            <input class="form-control" id="add-name">
                            <div id="error-addName" class="verify-error-message"></div>
                        </div>
                        <div class="form-group">
                            <span>角色描述:</span>
                            <input class="form-control" id="add-description">
                        </div>
                        <div class="form-group">权限
                            <div id="add-permission" class="ft-flex-box"></div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button id="clearAdd" class="btn btn-default waves-effect" data-dismiss="modal"
                                onclick="role.clearAdd();">取消
                        </button>
                        <button id="saveAdd" class="btn btn-success waves-effect waves-light" onclick="role.saveAdd();">
                            保存
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
<script src="/js/custom/admin_role.js"></script>
<script>
    $(document).ready(function () {
        window.role.init();
    });
</script>