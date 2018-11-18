!(function () {
    var role = {};

    role.init = function () {
        $("#roleTable").DataTable({
            "dom": '<"datatable-header"<"dataTables_filter">l><"datatable-scroll"t><"datatable-footer dataTable-footer-padding"<"pull-left"i><"pull-right"p>>',
            paging: true,
            lengthChange: false,
            ordering: true,
            autoWidth: false,
            info: true,
            serverSide: false,
            fixedHeader: true,
            searching: false,
            "destroy": true,
            aLengthMenu: [15],
            ajax: {
                url: "/api/role",
                dataSrc: 'data'
            },
            "columns": [
                {
                    "data": 'name'
                },
                {
                    "data": 'description'
                },
                {
                    "data": '',
                    "render": function (data, type, full, meta) {
                        return '<a href="#" data-toggle="modal" data-target="#perInfoModal" data-id="' + full.id + '">' + full.permissionCount + '项权限';
                    }
                },
                {
                    "data": '',
                    "render": function (data, type, full, meta) {
                        var $div = '<div class="ft-operation">' +
                            '<a href="#" data-toggle="modal" data-target="#editModal" data-id="' + full.id + '"><i class="fa fa-pencil text-inverse"></i><span>编辑</span></a>' +
                            '<a href="#" onclick="role.deleteRole(this)" data-id="' + full.id + '"><i class="fa fa-trash-o text-inverse"></i><span>删除</span></a>';
                        return $div;
                    }
                }
            ],
            "language": {url: '/lang/datatable.chs.json'}
        });

        $("#perInfoModal").on("show.bs.modal", function (e) {
            var $click = $(e.relatedTarget);
            var id = $click.data("id");
            $("#info-permission").empty();
            showBYLoading();
            $.ajax({
                url: "/api/permission/{id}".replace("{id}", id),
                type: "get",
                success: function (data) {
                    hideBYLoading();
                    if (data.result == "SUCCESS") {
                        if (data.data.length == 0) {
                            swal("该用户暂无权限", "", "warning");
                            $("#perInfoModal").modal("hide");
                        }
                        $.each(data.data, function (index, permission) {
                            $("#info-permission").append("<li>" + permission.displayName + "</li>");
                        });
                    } else {
                        swal("数据不存在", "", "warning");
                        $("#perInfoModal").modal("hide");
                    }
                },
                error: function (data) {
                    hideBYLoading();
                    console.log(data);
                    swal("数据不存在", "", "warning");
                    $("#perInfoModal").modal("hide");
                }
            });
        });

        $("#editModal").on("show.bs.modal", function (e) {
            var $click = $(e.relatedTarget);
            var id = $click.data("id");
            $("#edit-name").val("");
            $("#edit-description").val("");
            $("#edit-permission").html("");
            $("#edit-permission").val("").trigger('change');
            showBYLoading();
            $.ajax({
                url: "/api/role/{id}".replace("{id}", id),
                type: "get",
                success: function (data) {
                    hideBYLoading();
                    var permissions = data.permissions;
                    var roleInfo = data.data;
                    if (data.result == "SUCCESS" && roleInfo.length != 0) {
                        $("#edit-id").attr("data-id", roleInfo.id);
                        $("#edit-name").val(roleInfo.name);
                        $("#edit-description").val(roleInfo.description);
                        var permission = roleInfo.permissions;
                        var permissionIds = [];
                        $.each(permission, function (index, role) {
                            permissionIds.push(role.id)
                        });

                        $.each(permissions, function (index, item) {
                            if (permissionIds.indexOf(item.id) > -1) {
                                $("#edit-permission").append($("<div class='ft-flex-item item-checked item-access col-md-4' style='display: inline-block;' id='" + item.id + "' data-value='" + item.id + "' onclick='role.checkboxClick(this)'></div>").append($("<i class='ft-checkbox checked'/>")).append($("<span></span>").text(item.displayName)));
                            } else
                                $("#edit-permission").append($("<div class='ft-flex-item item-access col-md-4' style='display: inline-block;' id='" + item.id + "' data-value='" + item.id + "' onclick='role.checkboxClick(this)'></div>").append($("<i class='ft-checkbox checked'/>")).append($("<span></span>").text(item.displayName)))
                        });
                    }
                },
                error: function (data) {
                    hideBYLoading();
                    swal(data.result, "", "error");
                    console.log(data);
                }
            });
        });
    };

    role.closePerInfoModal = function () {
        $("#info-permission").empty();
    };

    role.clearEdit = function () {
        $("#edit-id").removeAttr("data-id");
        $("#edit-roleName").val("");
        $("#edit-permission").html("");
        $("#edit-permission").val("").trigger('change');
    };

    role.saveEdit = function () {
        var check_val = $("#edit-permission").find(".item-checked");
        var permissionsId = [];
        $.each(check_val, function (index, permission) {
            permissionsId.push($(permission).data("value"));
        });
        if ($("#edit-name").val().trim() == "") {
            $("#error-editName").text("角色名称不能为空");
            return;
        } else {
            $("#error-editName").text("");
        }
        var data = {
            id: $("#edit-id").data("id"),
            name: $("#edit-name").val(),
            description: $("#edit-description").val(),
            permissionIds: permissionsId    //多项权限
        };
        showBYLoading();
        $.ajax({
            url: "/api/role",
            type: "put",
            data: JSON.stringify(data),
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                hideBYLoading();
                if (data.result != "SUCCESS") {
                    showAlert("修改失败!", data.message, "warning", null);
                    return;
                }
                showAlert("修改成功!", "", "success", function () {
                    $("#editModal").modal("hide");
                    role.clearEdit();
                    $("#roleTable").DataTable().ajax.reload();
                })
            },
            error: function (data) {
                hideBYLoading();
                swal(data.message, "", "warning");
                console.log(e);
            }
        })
    };

    $("#addModal").on("show.bs.modal", function () {
        showBYLoading();
        $.ajax({
            url: "/api/permission",
            type: "get",
            data: {},
            success: function (data) {
                hideBYLoading();
                var data = data.data;
                $.each(data, function (index, item) {
                    $("#add-permission").append($("<div class='ft-flex-item item-access col-md-4' style='display: inline-block;' data-value='" + item.id + "' onclick='role.checkboxClick(this)'></div>").append($("<i class='ft-checkbox checked'/>")).append($("<span></span>").text(item.displayName)))
                })
            },
            error: function (data) {
                hideBYLoading();
                console.log(data)
            }
        });
    });

    role.checkboxClick = function (_this) {
        $(_this).hasClass('item-checked') ? $(_this).removeClass('item-checked') : $(_this).addClass('item-checked');
    };

    role.saveAdd = function () {
        if ($("#add-name").val().trim() == "") {
            $("#error-addName").text("角色名称不能为空");
            return;
        } else {
            $("#error-addName").text("");
        }
        var check_val = $("#add-permission").find(".item-checked");
        var permissionsId = [];
        $.each(check_val, function (index, permission) {
            permissionsId.push($(permission).data("value"));
        });
        var data = {
            name: $("#add-name").val(),
            description: $("#add-description").val(),
            permissionIds: permissionsId
        };
        showBYLoading();
        $.ajax({
            url: "/api/role",
            type: "post",
            contentType: "application/json;charset:utf-8",
            data: JSON.stringify(data),
            success: function (data) {
                hideBYLoading();
                if (data.result != "SUCCESS") {
                    showAlert("创建失败!", data.message, "warning", null);
                    return;
                }
                showAlert("创建成功!", "", "success", function () {
                    $("#addModal").modal("hide");
                    role.clearAdd();
                    $("#roleTable").DataTable().ajax.reload();
                })
            },
            error: function (data) {
                hideBYLoading();
                console.log(data);
            }
        });
    };

    role.clearAdd = function () {
        $("#add-name").val("");
        $("#add-description").val("");
        $("#add-permission").html("");
        $("#add-permission").val("").trigger('change');
    };

    role.deleteRole = function (e) {
        var roleId = $(e).data("id");
        if ([1, 2, 3, 4].indexOf(roleId) > -1) {
            showAlert("该角色不可删除", "", "warning", null);
            return;
        }
        showConfirmAlert("确认删除角色?", "", "warning", function () {
            showBYLoading();
            $.ajax({
                url: "/api/role",
                type: "delete",
                data: JSON.stringify({
                    id: roleId
                }),
                contentType: "application/json;charset=utf-8",
                success: function (data) {
                    hideBYLoading();
                    if (data.result != "SUCCESS") {
                        showAlert("删除失败!", data.message, "warning", null);
                        return;
                    }
                    showAlert("删除成功!", "", "success", function () {
                        $("#roleTable").DataTable().ajax.reload();
                    })
                },
                error: function (data) {
                    hideBYLoading();
                    swal(data.message, "", "确定");
                    console.log(e);
                }
            })
        });
    };

    window.role = role;
})();