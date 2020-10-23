function deleteServiceDetail(a, b, c) {
    var flag = confirm("确认删除？");
    if(flag) {
        $.post("log/deleteServiceDetail", {serviceName: a, ip: b}, function (data) {
            if(data > 0) {
                alert("删除成功！");
                location.href = "ServiceUpdateShow.html?environmentName=" + c;
            } else {
                alert("删除失败！")
            }
        });
    }
}