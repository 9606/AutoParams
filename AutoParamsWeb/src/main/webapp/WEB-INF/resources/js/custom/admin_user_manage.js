!(function () {
    var userManage = {};

    userManage.init = function getUserManageList() {
        $("#userList").DataTable({
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
                url: "/api/user",
                dataSrc: 'data'
            },
            "columns": [
                {
                    "data": 'userId'
                },
                {
                    "data": 'username'
                },
                {
                    "data": 'nickname'
                },
                {
                    "data": 'email'
                },
                {
                    "data": 'telephone'
                },
                {
                    "data": 'enabled',
                    "render": function (data, type, full, meta) {
                        if (data == 1)
                            return "已启用";
                        return "已禁用";
                    }
                },
                {
                    "data": 'userId',
                    "render": function (data, type, full, meta) {
                        if (full.userId == "1") {
                            var $div = '<div class="ft-operation">' +
                                '<a href="#" data-toggle="modal" data-target="#infoModal" data-id="' + full.userId + '"><i class="fa fa-bars text-inverse"></i><span>详情</span></a>';
                            return $div;
                        }
                        var $div = '<div class="ft-operation">' +
                            '<a href="#" data-toggle="modal" data-target="#infoModal" data-id="' + full.userId + '"><i class="fa fa-bars text-inverse"></i><span>详情</span></a>' +
                            '<a href="#" data-toggle="modal" data-target="#editModal" data-id="' + full.userId + '"><i class="fa fa-pencil text-inverse"></i><span>编辑</span></a>';
                        if (full.enabled == 1) {
                            $div += '<a href="#" onclick="userManage.changeState(\'' + full.enabled + '\',\'' + full.userId + '\');"><i class="fa fa-lock text-inverse"></i><span>禁用</span></a>'
                        } else {
                            $div += '<a href="#" onclick="userManage.changeState(\'' + full.enabled + '\',\'' + full.userId + '\');"><i class="fa fa-unlock text-inverse"></i><span>启用</span></a>';
                        }
                        $div += '<a href="#" data-toggle="modal" data-target="#resetPasswordModal" data-id="' + full.userId + '"><i class="fa fa-cogs text-inverse"></i><span>重置密码</span></a>';

                        $div += '<a href="#" onclick="userManage.deleteUser(\'' + full.userId + '\')"><i class="fa fa-trash-o text-inverse"></i><span>删除</span></a>';

                        return $div;
                    }
                }
            ],
            "language": {url: '/lang/datatable.chs.json'}
        })
    };

    $("#resetPasswordModal").on("show.bs.modal", function (e) {
        var $click = $(e.relatedTarget);
        var userId = $click.data("id");
        $("#userId").val(userId);
    });

    userManage.resetPasswordSave = function () {
        var newPassword = $("#newPassword").val().trim();
        var checkPass = $("#confirmPassword").val().trim();
        if (newPassword == null || newPassword == "" || newPassword == undefined) {
            $("#errorNewPassword").removeAttr("hidden").text("新密码不能为空!");
            return;
        }
        else if (newPassword.length < 6 || newPassword.length > 20) {
            $("#errorNewPassword").removeAttr("hidden").text("密码不能小于6位,且不能大于20位！");
            return;
        }
        // else if (!newPassword.match(/\d/) || !newPassword.match(/[A-Z]/) || !newPassword.match(/[a-z]/)) {
        //     $("#errorNewPassword").removeAttr("hidden").text("密码必须包含至少一个字母和一个数字！");
        //     return;
        // }
        if (newPassword != checkPass) {
            $("#errorConfirmPassword").removeAttr("hidden").text("输入密码不一致！");
            return;
        }
        showBYLoading();
        $.ajax({
            url: '/api/user/resetPassword',
            type: 'post',
            data: JSON.stringify({
                userId: $("#userId").val(),
                currentPwd: $("#oldPassword").val(),
                newPwd: $("#newPassword").val()
            }),
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                hideBYLoading();
                if (data.result === "SUCCESS") {
                    swal("重置成功！", "", "success");
                    $("#resetPasswordModal").modal("hide");
                    userManage.cancelResetPassword();
                } else {
                    swal("重置失败！", "", "warning");
                }
            },
            error: function (data) {
                hideBYLoading();
                swal("重置失败！", "", "warning");
            }
        })
    };

    userManage.cancelResetPassword = function () {
        $("#newPassword").val("");
        $("#confirmPassword").val("");
    };

    userManage.deleteUser = function (userId) {
        showConfirmAlert("确认删除该用户？", "", "warning", function () {
            showBYLoading();
            $.ajax({
                url: '/api/user',
                type: "delete",
                data: JSON.stringify({
                    userId: userId
                }),
                contentType: "application/json;charset=utf-8",
                success: function (data) {
                    hideBYLoading();
                    if (data.result != "SUCCESS") {
                        showAlert("删除失败!", data.message, "warning", null);
                    }
                    showAlert("删除成功!", "", "success", function () {
                        $("#userList").DataTable().ajax.reload();
                    });
                },
                error: function (e) {
                    hideBYLoading();
                    showAlert("删除失败!", e.message, "warning", null);
                }
            });
        });
    };

    userManage.changeState = function (state, userId) {
        var stateName = "禁用";
        if (state == 1) {
            state = 0;
        } else {
            state = 1;
            var stateName = "启用";
        }
        showConfirmAlert("确认'" + stateName + "'该用户？", "", "warning", function () {
            showBYLoading();
            $.ajax({
                url: "/api/user/editState",
                type: "post",
                data: JSON.stringify({
                    state: state,
                    userId: userId
                }),
                contentType: "application/json;charset=utf-8",
                success: function (data) {
                    hideBYLoading();
                    if (data.result != "SUCCESS") {
                        showAlert("修改失败!", "", "warning", null);
                    } else {
                        showAlert("修改成功!", "", "success", function () {
                            $("#userList").DataTable().ajax.reload();
                        });
                    }
                },
                error: function (e) {
                    hideBYLoading();
                    showAlert("修改失败!", e.message, "warning", null);
                }
            });
        });
    };

    $("#infoModal").on("show.bs.modal", function (e) {
        $("#user-id").text("");
        $("#user-username").text("");
        $("#user-nickname").text("");
        $("#user-email").text("");
        $("#user-tel").text("");
        //$("#user-enterprise").text("");
        $("#user-createTime").text("");
        $("#user-lastLoginTime").text("");
        $("#user-role").text("");

        $("#user-authority").text("");

        var $click = $(e.relatedTarget);
        var userId = $click.data("id");
        showBYLoading();
        $.ajax({
            url: "/api/user/detail/{id}".replace("{id}", userId),
            type: "get",
            success: function (data) {
                hideBYLoading()
                var data = data.data;
                $("#user-id").text(data.userId);
                $("#user-username").text(data.username);
                $("#user-nickname").text(data.nickname);
                $("#user-email").text(data.email);
                $("#user-tel").text(data.telephone);
                $("#user-createTime").text(dateFormatAll(new Date(data.createTime)));
                $("#user-lastLoginTime").text(dateFormatAll(new Date(data.lastTime)));
                var roles = data.roles;
                var role = "";
                for (var i = 0; i < roles.length; i++) {
                    if (i == 0 || roles.length == 1) {
                        role = roles[i].name;
                    } else if (roles.length > 1)
                        role += "|" + roles[i].name;
                }
                if (roles.length == 0) {
                    $("#user-role").text("无");
                } else
                    $("#user-role").text(role);
                var permissions = data.permissions;
                var permission = $("<div></div>");
                for (var i = 0; i < permissions.length; i++) {
                    permission.append($("<li></li>").text(permissions[i].displayName));
                }
                if (permissions.length == 0) {
                    $("#user-authority").text("无");
                } else
                    $("#user-authority").append(permission);
            },
            error: function (data) {
                hideBYLoading();
                console.log(data);
            }
        })

    });

    $("#editModal").on("show.bs.modal", function (e) {
        var $click = $(e.relatedTarget);
        var userId = $click.data("id");
        $.ajax({
            url: "/api/user/detail/{id}".replace("{id}", userId),
            type: "get",
            success: function (data) {
                $("#edit-user-id").val(data.data.userId);
                $("#edit-user-username").val(data.data.username);
                $("#edit-user-nickname").val(data.data.nickname);
                $("#edit-user-email").val(data.data.email);
                $("#edit-user-tel").val(data.data.telephone);
                $("#edit-user-role").html("");
                var allroles = data.allRoles;
                var roles = data.data.roles;
                var roleIds = [];
                $.each(roles, function (index, role) {
                    roleIds.push(role.id)
                });

                $.each(allroles, function (index, item) {
                    if (roleIds.indexOf(item.id) > -1) {
                        $("#edit-user-role").append($("<div class='ft-flex-item item-checked item-access col-md-4' style='display: inline-block;' id='" + item.id + "' data-value='" + item.id + "' onclick='userManage.checkboxClick(this,$(\"#edit-user-role\"))'></div>").append($("<i class='ft-checkbox checked'/>")).append($("<span></span>").text(item.name)));
                    } else
                        $("#edit-user-role").append($("<div class='ft-flex-item item-access col-md-4' style='display: inline-block;' id='" + item.id + "' data-value='" + item.id + "' onclick='userManage.checkboxClick(this,$(\"#edit-user-role\"))'></div>").append($("<i class='ft-checkbox checked'/>")).append($("<span></span>").text(item.name)))

                });
            },
            error: function (data) {
                console.log(data);
            }
        });
    });

    userManage.saveEdit = function () {
        var userId = $("#edit-user-id").val();
        var nickname = $("#edit-user-nickname").val();
        var email = $("#edit-user-email").val();
        var tel = $("#edit-user-tel").val();

        var check_val = $("#edit-user-role").find(".item-checked");
        if (check_val.length != 1) {
            showAlert("必须选择1个角色", "", "warning", null);
            return;
        }
        var roleId = $(check_val[0]).data("value");
        if (nickname == "" || nickname == null || nickname == undefined) {
            swal("昵称不能为空！", "", "warning");
            return;
        } else if (nickname.length > 6) {
            swal("昵称长度不能大于6位！", "", "warning");
            return;
        }
        if (email == "" || email == null || email == undefined) {
            swal("email不能为空！", "", "warning");
            return;
        } else if (!mailVerify.test(email)) {
            swal("请输入正确的邮箱格式！", "", "warning");
            return;
        }
        if (tel == "" || tel == null || tel == undefined) {
            swal("联系电话不能为空！", "", "warning");
            return;
        } else if (!telephoneVerify.test(tel) && !fixedLineTel.test(tel)) {
            swal("请输入正确的电话格式！", "", "warning");
            return;
        }
        var data = {};
        data["userId"] = userId;
        data["nickname"] = nickname;
        data["email"] = email;
        data["telephone"] = tel;
        data["roleId"] = roleId;
        showBYLoading();
        $.ajax({
            url: "/api/user",
            type: "put",
            data: JSON.stringify(data),
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                hideBYLoading();
                if (data.result == "SUCCESS") {
                    showAlert("修改成功!", "", "success", function () {
                        $("#editModal").modal("hide");
                        userManage.clearEdit();
                        $("#userList").DataTable().ajax.reload();
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

    $("#addModal").on("show.bs.modal", function () {
        $.ajax({
            url: "/api/role",
            type: "get",
            data: {},
            success: function (data) {
                var data = data.data;
                $.each(data, function (index, item) {
                    $("#add-role").append($("<div class='ft-flex-item item-access col-md-4' style='display: inline-block;' data-value='" + item.id + "' onclick='userManage.checkboxClick(this,$(\"#add-role\"))'></div>")
                        .append($("<i class='ft-checkbox checked'/>"))
                        .append($("<span></span>").text(item.name)))
                })
            },
            error: function (data) {
                console.log(data)
            }
        });
    });

    userManage.checkboxClick = function (_this, $checkType) {
        $checkType.find(".item-checked").removeClass('item-checked');
        $(_this).addClass('item-checked');
    };

    userManage.saveAdd = function () {
        var username = $("#add-username").val();
        var nickname = $("#add-nickname").val();
        var password = $("#add-password").val();
        var checkPwd = $("#add-checkPwd").val();
        var email = $("#add-email").val();
        var tel = $("#add-tel").val();
        var check_val = $("#add-role").find(".item-checked");
        if (check_val.length != 1) {
            showAlert("必须选择1个角色", "", "warning", null);
            return;
        }
        var roleId = $(check_val[0]).data("value");

        if (username == "" || username == null || username == undefined) {
            swal("用户名不能为空！", "", "warning");
            return;
        } else if (!numAndLetters.test(username)) {
            swal("用户名只能为小写字母或数字！", "", "warning");
            return;
        } else if (username.length > 6) {
            swal("用户名长度不能超过6位！", "", "warning");
            return;
        }

        if (nickname == "" || nickname == null || nickname == undefined) {
            swal("昵称不能为空！", "", "warning");
            return;
        } else if (nickname.length > 6) {
            swal("昵称长度不能大于6位！", "", "warning");
            return;
        }
        if (password == "" || password == null || password == undefined) {
            swal("密码不能为空！", "", "warning");
            return;
        } else if (password.length > 20 || password.length < 6) {
            swal("密码长度至少为6位，不超过20位！", "", "warning");
            return;
        }
        if (password != checkPwd) {
            swal("输入密码不一致！", "", "warning");
            return;
        }
        if (email == "" || email == null || email == undefined) {
            swal("email不能为空！", "", "warning");
            return;
        } else if (!mailVerify.test(email)) {
            swal("请输入正确的邮箱格式！", "", "warning");
            return;
        }
        if (tel == "" || tel == null || tel == undefined) {
            swal("联系电话不能为空！", "", "warning");
            return;
        } else if (!telephoneVerify.test(tel) && !fixedLineTel.test(tel)) {
            swal("请输入正确的电话格式！", "", "warning");
            return;
        }

        var data = {};
        data["username"] = username;
        data["nickname"] = nickname;
        data["password"] = password;
        data["email"] = email;
        data["telephone"] = tel;
        data["roleId"] = roleId;
        showBYLoading();
        $.ajax({
            url: '/api/user',
            type: 'post',
            data: JSON.stringify(data),
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                hideBYLoading();
                if (data.result === "SUCCESS") {
                    showAlert("创建成功!", "", "success", function () {
                        $("#addModal").modal("hide");
                        userManage.clearAdd();
                        $("#userList").DataTable().ajax.reload();
                    });
                } else if (data.result === "ERROR_USER_EXISTED") {
                    showAlert("用户已存在!", "", "warning", null);
                } else {
                    showAlert("创建失败！", "", "warning");
                }
            },
            error: function (data) {
                hideBYLoading();
                showAlert("创建失败！", "", "warning");
            }
        });
    };

    userManage.clearEdit = function () {
        $("#edit-user-username").val("");
        $("#edit-user-nickname").val("");
        $("#edit-user-email").val("");
        $("#edit-user-tel").val("");
        $("#edit-user-enterprise").val("");
        $("#edit-user-role").empty();
        $("#edit-user-role").val("").trigger('change');
        $("#addModal").modal('hide');
    };

    userManage.clearAdd = function () {
        $("#add-username").val("");
        $("#add-nickname").val("");
        $("#add-password").val("");
        $("#add-checkPwd").val("");
        $("#add-email").val("");
        $("#add-tel").val("");
        $("#add-role").text("");
        $("#addModal").modal('hide');
    };

    userManage.removeHint = function () {
        $(".errorPassword").attr("hidden", "hidden");
    };

    window.userManage = userManage;
})();