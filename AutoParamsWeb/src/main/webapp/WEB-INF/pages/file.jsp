<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="include/head.jsp">
<jsp:param name="title" value="训练集管理"/>
</jsp:include>
<style>
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
                <h4 class="page-title">数据集管理</h4>
            </div>
            <!-- /.page title -->
        </div>
        <!-- .Your content row -->
        <div class="panel panel-default">
            <div class="col-sm-12 col-md-12 col-lg-12 line-padding">
                <a class="btn btn-outline btn-success " data-toggle="modal" data-target="#file-upload-modal"
                   style="float: right;cursor: pointer">
                    <span>上传新数据</span>
                </a>
            </div>

            <%--table--%>
            <div class="panel-wrapper collapse in" aria-expanded="true">
                <div class="panel-body">
                    <table class="table table-striped table-bordered" id="train-file">
                        <thead>
                            <tr>
                                <th data-field="id">文件ID</th>
                                <th data-field="filename">文件名称</th>
                                <th data-field="fileType">文件类型</th>
                                <th data-field="fileSize">文件大小</th>
                                <th data-field="colName">列名</th>
                                <th data-field="cluLabel">聚类标签</th>
                                <th data-field="uploadTime">上传时间</th>
                                <th data-field="id">操作</th>
                            </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                </div>
            </div>

            <%--add--%>
            <div id="file-upload-modal" class="modal fade" role="dialog" data-backdrop="false"
                 aria-hidden="true" style="display: none;">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">上传数据</h4>
                        </div>
                        <div class="modal-body">

                            <div class="form-group">
                                <div class="col-md-12" style="padding-left: 0px;margin-bottom: 10px;">上传文件：</div>
                                <div class="col-md-4">
                                    <span class="btn btn-success fileinput-button" id="fileupload-span">
                                        <i class="fa fa-plus-circle"></i>
                                        <span>从本地选择训练集</span>
                                        <input id="fileupload" type="file" name="file" data-url="/api/file/upload/train">
                                    </span>
                                </div>
                                <div class="col-md-8">
                                    <button id="fileuploadStart" class="btn btn-primary start">
                                        <i class="glyphicon glyphicon-upload"></i>
                                        <span>开始上传</span>
                                    </button>
                                </div>
                            </div>
                            <div class="form-group">
                                <!-- The global progress bar -->
                                <span id="file-progress-span" style="display: none;">
                                    <div class="col-md-3" style="margin-top: 10px;">文件上传进度：</div>
                                    <div class="col-md-9" style="margin-top: 10px;">
                                        <div id="file-progress" class="progress" style="margin-bottom: 0px;">
                                            <div class="progress-bar progress-bar-success"></div>
                                        </div>
                                    </div>
                                </span>
                                <div id="file-info" class="col-md-12" style="margin-top: 10px;"></div>
                            </div>
                            <div class="form-group">
                                文件类型：
                                <div id="add-file-type" class="ft-flex-box">
                                    <div class="ft-flex-item item-checked item-access col-md-4" style="display: inline-block;" data-value="csv" onclick="fileManage.checkboxClick(this,$('#add-file-type'))">
                                        <i class="ft-checkbox checked"></i><span>csv</span>
                                    </div>
                                    <div class="ft-flex-item item-access col-md-4" style="display: inline-block;" data-value="libsvm" onclick="fileManage.checkboxClick(this,$('#add-file-type'))">
                                        <i class="ft-checkbox checked"></i><span>libsvm</span>
                                    </div>
                                </div>
                            </div>
                                <div class="form-group">
                                    列名：
                                    <div id="add-col-name" class="ft-flex-box">
                                        <div class="ft-flex-item item-checked item-access col-md-4" style="display: inline-block;" data-value="true" onclick="fileManage.checkboxClick(this,$('#add-col-name'))">
                                            <i class="ft-checkbox checked"></i><span>存在</span>
                                        </div>
                                        <div class="ft-flex-item item-access col-md-4" style="display: inline-block;" data-value="false" onclick="fileManage.checkboxClick(this,$('#add-col-name'))">
                                            <i class="ft-checkbox checked"></i><span>不存在</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    聚类标签（只针对聚类数据集有效）：
                                    <div id="add-clu-label" class="ft-flex-box">
                                        <div class="ft-flex-item item-access col-md-4" style="display: inline-block;" data-value="true" onclick="fileManage.checkboxClick(this,$('#add-clu-label'))">
                                            <i class="ft-checkbox checked"></i><span>存在</span>
                                        </div>
                                        <div class="ft-flex-item item-checked item-access col-md-4" style="display: inline-block;" data-value="false" onclick="fileManage.checkboxClick(this,$('#add-clu-label'))">
                                            <i class="ft-checkbox checked"></i><span>不存在</span>
                                        </div>
                                    </div>
                                </div>

                        </div>

                        <div class="modal-footer">
                            <button id="clearAdd" class="btn btn-default waves-effect"
                                    data-dismiss="modal" onclick="fileManage.clearAdd()">取消
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <%--file-edit--%>
            <div id="file-edit-modal" class="modal fade" role="dialog" data-backdrop="false"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">编辑文件信息</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <span>文件ID：</span>
                                <input id="edit-file-id" class="form-control" disabled="disabled">
                            </div>
                            <div class="form-group">
                                <span>文件大小：</span>
                                <input id="edit-file-size" class="form-control" disabled="disabled">
                            </div>
                            <div class="form-group">
                                <span>上传时间：</span>
                                <input id="edit-upload-time" class="form-control" disabled="disabled">
                            </div>
                            <div class="form-group">
                                <span>文件名：</span>
                                <input id="edit-file-name" class="form-control">
                            </div>
                            <div class="form-group">文件类型：
                                <div id="edit-file-type" class="ft-flex-box"></div>
                            </div>
                            <div class="form-group">列名：
                                <div id="edit-col-name" class="ft-flex-box"></div>
                            </div>
                            <div class="form-group">聚类标签（只针对聚类数据集有效）：
                                <div id="edit-clu-label" class="ft-flex-box"></div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button id="clearEdit" class="btn btn-default waves-effect"
                                    data-dismiss="modal" onclick="fileManage.clearEdit()">取消
                            </button>
                            <button id="saveEdit" class="btn btn-success waves-effect waves-light"
                                    onclick="fileManage.saveEdit()">保存
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
<script src="/js/custom/file.js"></script>
<script>
    $(document).ready(function () {
        $("#file-type").select2({
            placeholder: "选择训练集格式",
            language: "zh-CN",
            minimumResultsForSearch: 10
        });
        $('#fileupload').fileupload({
            dataType: 'json',
            formData: function () {

                var fileType = $($("#add-file-type").find(".item-checked")[0]).data("value");
                var colName = $($("#add-col-name").find(".item-checked")[0]).data("value");
                var cluLabel = $($("#add-clu-label").find(".item-checked")[0]).data("value");
                return [
                    { name: 'fileType', value: fileType},
                    { name: 'colName', value: colName},
                    { name: 'cluLabel', value: cluLabel}];
            },
            add: function (e, data) {
                $('#file-info').empty();
                $('<p/>').text(data.originalFiles[0].name + ' 已选择').appendTo('#file-info');
                $('#fileuploadStart').click(function() {
                    data.submit();
                });
            },
            submit: function (e, data) {

                var $fileType = $("#add-file-type").find(".item-checked");
                if ($fileType.length != 1) {
                    showAlert("必须选择1个文件类型", "", "warning", null);
                    return false;
                }
                var $colName = $("#add-col-name").find(".item-checked");
                if ($colName.length != 1) {
                    showAlert("必须选择列名是否存在", "", "warning", null);
                    return false;
                }
                var $cluLabel = $("#add-clu-label").find(".item-checked");
                if ($cluLabel.length != 1) {
                    showAlert("必须选择列名是否存在", "", "warning", null);
                    return false;
                }

                $("#file-progress-span").css("display","block");
            },
            done: function (e, data) {
                $('#file-info').empty();
                $('<p/>').text(data.result.originName + ' 已上传').appendTo('#file-info');
                $("#train-file").DataTable().ajax.reload();
            },
            fail: function (e, data) {
                $('#file-info').empty();
                $('<p/>').text('上传失败，请重新上传。').appendTo('#file-info');
            },
            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $('#file-progress .progress-bar').css('width', progress + '%');
            }
        });

        window.fileManage.init();
    });
</script>

