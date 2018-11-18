/**
 * Created by yhy on 2017/06/22.
 */
!(function () {
    var fileManage = {};

    fileManage.init = function getFileManageList() {
        var trainFileTable = $("#train-file").DataTable({
            deferRender: true,
            paging: true,
            ordering: true,
            order: [[ 6, "desc"]],
            autoWidth: true,
            searching: true,
            scrollX:true,
            select: true,
            ajax: {
                url: "/api/file/list",
                dataSrc: 'data'
            },
            "columns": [
                {
                    "data": 'fileId'
                },
                {
                    "data": 'originName'
                },
                {
                    "data": 'fileType'
                },
                {
                    "data": 'fileSize'
                },
                {
                    "data": 'colName',
                    "render": function (data, type, full, meta) {
                        return data ? "存在" : "不存在" ;
                    }
                },
                {
                    "data": 'cluLabel',
                    "render": function (data, type, full, meta) {
                        return data ? "存在" : "不存在" ;
                    }
                },
                {
                    "data": 'uploadTime',
                    "render": function (data, type, full, meta) {
                        return formatDateTime(data);
                    }
                },
                {
                    "data": 'fileId',
                    "render": function (data, type, full, meta) {
                        var $a = $('<a href="#" data-toggle="modal" data-target="#file-edit-modal"><i class="fa fa-pencil text-inverse"></i><span>编辑</span></a>');
                        $a.attr('data-content', JSON.stringify(full));
                        var $div = '<div class="ft-operation">' + $a.prop("outerHTML") +
                            '<a href="#" onclick="downloadFile(\'' + "/api/file/download" + '\', this)" data-name="' + full.originName + '" data-id="' + full.filePath + '"><i class="fa fa-download text-inverse"></i><span>下载</span></a>'+
                            '<a href="#" onclick="deleteFile(this)" data-id="' + data + '"><i class="fa fa-trash-o text-inverse"></i><span>删除</span></a></div>';
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
                            if (node.innerHTML === '操作' || !trainFileTable.column(idx).visible()) {
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
                            if (node.innerHTML === '操作' || !trainFileTable.column(idx).visible()) {
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
                            if (node.innerHTML === '操作' || !trainFileTable.column(idx).visible()) {
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
                            if (node.innerHTML === '操作' || !trainFileTable.column(idx).visible()) {
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
            "language": {url: '/lang/datatable.chs.json'}
        });

        $('#train-file').on('init.dt', function(){
            new $.fn.dataTable.Buttons(trainFileTable, {
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
                            var selectRows = trainFileTable.rows({ selected: true }).data().toArray();
                            var selectRowsId = [];
                            $.each(selectRows, function(key, value){
                                selectRowsId.push(value['fileId'])
                            });
                            deleteFile(selectRowsId);
                        }
                    },
                    {
                        extend: 'colvis',
                        text: '<em class="fa fa-columns"></em> 列可见性'
                    }
                ]
            });
            trainFileTable.buttons(0, null).container()
                .appendTo('#train-file_wrapper .col-sm-6:eq(3)');
            trainFileTable.buttons(1, null).container()
                .appendTo('#train-file_wrapper .col-sm-6:eq(2)');
            $('.dataTables_filter .input-sm').css('width', $('.dt-bootstrap .btn-group:eq(1)').width() / 2);
            $('#train-file_wrapper .col-sm-6:eq(2) .btn-group').css('float', 'left');
            $('#train-file_wrapper .col-sm-6:eq(3) .btn-group').css('float', 'right');
        });

        trainFileTable.on('user-select', function ( e, dt, type, cell, originalEvent ) {
            if ($.inArray($(dt.column(cell.index().column).header()).html(), ['操作']) > -1) {
                e.preventDefault();
            }
        });
    };

    $("#file-edit-modal").on("show.bs.modal", function (e) {
        var $click = $(e.relatedTarget);
        var fileInfo = $click.data("content");
        $("#edit-file-id").val(fileInfo.fileId);
        $("#edit-file-size").val(fileInfo.fileSize);
        $("#edit-upload-time").val(formatDateTime(fileInfo.uploadTime));
        $("#edit-file-name").val(fileInfo.originName);

        $.each(['libsvm', 'csv'], function (index, item) {
            if (fileInfo.fileType === item) {
                $("#edit-file-type").append($("<div class='ft-flex-item item-checked item-access col-md-4' style='display: inline-block;' data-value='" + item + "' onclick='fileManage.checkboxClick(this,$(\"#edit-file-type\"))'></div>").append($("<i class='ft-checkbox checked'/>")).append($("<span></span>").text(item)));
            }else {
                $("#edit-file-type").append($("<div class='ft-flex-item item-access col-md-4' style='display: inline-block;' data-value='" + item + "' onclick='fileManage.checkboxClick(this,$(\"#edit-file-type\"))'></div>").append($("<i class='ft-checkbox checked'/>")).append($("<span></span>").text(item)));
            }
        });

        $.each([true, false], function (index, item) {
            if (fileInfo.colName === item) {
                $("#edit-col-name").append($("<div class='ft-flex-item item-checked item-access col-md-4' style='display: inline-block;' data-value='" + item + "' onclick='fileManage.checkboxClick(this,$(\"#edit-col-name\"))'></div>").append($("<i class='ft-checkbox checked'/>")).append($("<span></span>").text(item ? "存在" : "不存在")));
            }else {
                $("#edit-col-name").append($("<div class='ft-flex-item item-access col-md-4' style='display: inline-block;' data-value='" + item + "' onclick='fileManage.checkboxClick(this,$(\"#edit-col-name\"))'></div>").append($("<i class='ft-checkbox checked'/>")).append($("<span></span>").text(item ? "存在" : "不存在")));
            }
        });

        $.each([true, false], function (index, item) {
            if (fileInfo.cluLabel === item) {
                $("#edit-clu-label").append($("<div class='ft-flex-item item-checked item-access col-md-4' style='display: inline-block;' data-value='" + item + "' onclick='fileManage.checkboxClick(this,$(\"#edit-clu-label\"))'></div>").append($("<i class='ft-checkbox checked'/>")).append($("<span></span>").text(item ? "存在" : "不存在")));
            }else {
                $("#edit-clu-label").append($("<div class='ft-flex-item item-access col-md-4' style='display: inline-block;' data-value='" + item + "' onclick='fileManage.checkboxClick(this,$(\"#edit-clu-label\"))'></div>").append($("<i class='ft-checkbox checked'/>")).append($("<span></span>").text(item ? "存在" : "不存在")));
            }
        });
    });

    fileManage.saveEdit = function () {
        var fileId = $("#edit-file-id").val();
        var fileName = $("#edit-file-name").val();
        var $fileType = $("#edit-file-type").find(".item-checked");
        if ($fileType.length != 1) {
            showAlert("必须选择1个文件类型", "", "warning", null);
            return;
        }
        var fileType = $($fileType[0]).data("value");

        var $colName = $("#edit-col-name").find(".item-checked");
        if ($colName.length != 1) {
            showAlert("必须选择列名是否存在", "", "warning", null);
            return;
        }
        var colName = $($colName[0]).data("value");

        var $cluLabel = $("#edit-clu-label").find(".item-checked");
        var cluLabel = $($cluLabel[0]).data("value");
        showBYLoading();
        var data = {};
        data["fileId"] = fileId;
        data["originName"] = fileName;
        data["fileType"] = fileType;
        data["colName"] = colName;
        data["cluLabel"] = cluLabel;
        $.ajax({
            url: "/api/file/update",
            type: "post",
            data: JSON.stringify(data),
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                hideBYLoading();
                if (data.result == "SUCCESS") {
                    showAlert("修改成功!", "", "success", function () {
                        $("#file-edit-modal").modal("hide");
                        fileManage.clearEdit();
                        $("#train-file").DataTable().ajax.reload();
                    })
                } else {
                    showAlert("修改失败!", "", "warning", null);
                }
            },
            error: function (e) {
                hideBYLoading();
                showAlert(e.message, "", "warning", null);
            }
        });
    };

    fileManage.clearEdit = function () {
        $("#edit-file-id").val("");
        $("#edit-file-size").val("");
        $("#edit-upload-time").val("");
        $("#edit-file-name").val("");
        $("#edit-file-type").html("");
        $("#edit-col-name").html("");
        $("#edit-clu-label").html("");
    };


    fileManage.checkboxClick = function (_this, $checkType) {
        $checkType.find(".item-checked").removeClass('item-checked');
        $(_this).addClass('item-checked');
    };
    window.fileManage = fileManage;
})();

function deleteFile(file) {
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
                var url = $.isArray(file) ? "/api/file/delete/batch" : "/api/file/delete/" + $(file).data("id");
                var type = $.isArray(file) ? "post" : "get";
                var data = $.isArray(file) ? JSON.stringify(file) : '';
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
                        showAlert("删除成功!", "已删除" + data.data + "个文件", "success",
                            function (isConfirm) {if (isConfirm) {
                                // window.location.reload();
                                $("#train-file").DataTable().ajax.reload();
                            }});
                    },
                    error: function (data) {
                        swal('删除失败', data.message, "warning");
                    }
                })
            }
        }
    );
}