!(function () {
    var changePwd = {};

    changePwd.init = function () {

    };

    changePwd.changePassSubmit = function () {
        var password = $("#oldPassword").val().trim();//用户输入的原密码
        var newPassword = $("#newPassword").val().trim();
        var checkPass = $("#confirmPassword").val().trim();
        var userId = $("#userId").val().trim();
        if (password == null || password == "" || password == undefined) {
            $("#errorOldPassword").removeAttr("hidden").text("原密码不能为空");
            return;
        }
        if (newPassword == null || newPassword == "" || newPassword == undefined) {
            $("#errorNewPassword").removeAttr("hidden").text("新密码不能为空");
            return;
        }
        // else if (newPassword.length < 6) {
        //     $("#errorNewPassword").removeAttr("hidden").text("密码不能小于六位！");
        //     return;
        // }
        if (newPassword != checkPass) {
            $("#errorPassword").removeAttr("hidden").text("输入密码不一致！");
            return;
        }
        showBYLoading();
        $.ajax({
            url: '/api/user/changeSelfPassword',
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
                    window.location.href = "/logout";
                } else if (data.result === "ERROR_PASSWORD") {
                    $("#errorOldPassword").removeAttr("hidden").text(data.message);
                } else {
                    swal("修改密码失败！", "", "warning");
                }
            },
            error: function (data) {
                hideBYLoading();
                swal("修改密码失败！", "", "warning");
            }
        })

    };

    changePwd.removeHint = function () {
        $(".errorPassword").attr("hidden", "hidden");
    };

    window.changePwd = changePwd;
})();
