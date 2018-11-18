/**
 * Created by bozhi on 2017/5/11.
 */
!(function () {
    var updateUserInfo = {};

    updateUserInfo.init = function () {
        $.ajax({
            url: "/api/user/changeSelfInfo",
            type: "get",
            data: {},
            success: function (data) {
                if (data.result === "SUCCESS") {
                    $("#edit-user-id").val(data.data.userId);
                    $("#edit-user-username").val(data.data.username);
                    $("#edit-user-nickname").val(data.data.nickname);
                    $("#edit-user-email").val(data.data.email);
                    $("#edit-user-tel").val(data.data.telephone);
                    var roles = data.data.roles;
                    var role = "";
                    for (var i = 0; i < roles.length; i++) {
                        if (i == 0 || roles.length == 1) {
                            role = roles[i].name;
                        } else if (roles.length > 1)
                            role += "|" + roles[i].name;
                    }
                    if (roles.length == 0) {
                        $("#edit-user-role").text("无");
                    } else{
                        $("#edit-user-role").text(role);
                    }
                }
            },
            error: function (data) {
                console.log(data);
            }
        })
    };

    updateUserInfo.checkboxClick = function (_this) {
        $(_this).parent(".item-access").hasClass('item-checked')?$(_this).parent(".item-access").removeClass('item-checked'):$(_this).parent(".item-access").addClass('item-checked');
    };

    updateUserInfo.saveEdit = function () {
        var userId = $("#edit-user-id").val();
        var nickname = $("#edit-user-nickname").val();
        var email = $("#edit-user-email").val();
        var tel = $("#edit-user-tel").val();
        var roleId = $("#edit-user-role").val();
        if (nickname == "" || nickname == null || nickname == undefined) {
            swal("昵称不能为空！", "", "warning");
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
        if (roleId == "" || roleId == null || roleId == undefined) {
            roleId = [""];
        }
        showBYLoading();
        $.ajax({
            url: '/api/user/changeSelfInfo',
            type: 'put',
            data: JSON.stringify({
                userId: userId,
                nickname: nickname,
                email: email,
                telephone: tel,
                roleId: roleId
            }),
            contentType:"application/json;charset=utf-8",
            success: function (data) {
                hideBYLoading();
                swal("更新信息成功！", "", "success");
            },
            error: function (data) {
                hideBYLoading();
                swal("更新信息失败！", "", "error");
            }
        });
    };

    window.updateUserInfo = updateUserInfo;
})();