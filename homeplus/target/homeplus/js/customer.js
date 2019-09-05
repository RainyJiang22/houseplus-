/*加载函数*/
/*jquery获取参数的方法*/
var request = {
    QueryString : function(val) {
        var uri = window.location.search;
        var re = new RegExp("" +val+ "\=([^\&\?]*)", "ig");
        return ((uri.match(re))?(uri.match(re)[0].substr(val.length+1)):null);
    },
    QueryStrings : function() {
        var uri = window.location.search;
        var re = /\w*\=([^\&\?]*)/ig;
        var retval=[];
        while ((arr = re.exec(uri)) != null)
            retval.push(arr[0]);
        return retval;
    },
    setQuery : function(val1, val2) {
        var a = this.QueryStrings();
        var retval = "";
        var seted = false;
        var re = new RegExp("^" +val1+ "\=([^\&\?]*)$", "ig");
        for(var i=0; i<a.length; i++) {
            if (re.test(a[i])) {
                seted = true;
                a[i] = val1 +"="+ val2;
            }
        }
        retval = a.join("&");
        return "?" +retval+ (seted ? "" : (retval ? "&" : "") +val1+ "=" +val2);
    }
};
var id = request.QueryString("id");

//页面开始函数
$(document).ready(function () {
    loginSuccess();
    showCustomer(id);
    message(id);
    $(".log-out").click (function () {
        logout();
    })
});

//加载雇主的详细数据
function showCustomer(id){
    var url = "/person/loadCustomer";
    var data = "param=" + id;

   $.ajax({
       url:url,
       data:data,
       "dataType": "json",
       "type": "Post",
       "success":function(json){
           //成功回调后
           if (json.state == 200){
               $(".title").text(json.data.cmName);
               //隐藏当前手机号
               $(".phone").text(displayPhone(json.data.cmPhone));
           }
       }
   })
}

// 发送消息，传入当前需要留言的id
function message(id) {
    //展示消息弹窗
    $(".left-msg").click(function () {
        $("#messageModal").modal("show");
    })

    $(".submit-msg").click(function () {
        var url = "/message/insertMessageCM";
        var data = "id=" + id + "&msgContent=" + $(".msg-input").val();
        $.ajax({
            "url": url,
            "data": data,
            "dataType": "json",
            "type": "Post",
            "success": function (json) {
                if (json.state == 200) {
                    swal ({
                        title: "提醒",
                        text: "留言成功"
                    })
                    $("#messageModal").modal("hide");
                } else if (json.state == 409) {
                    swal ({
                        title: "提醒",
                        text: json.message
                    })
                }
            }
        })
    })
}


//用于隐藏手机号
function displayPhone(phone) {
    return phone.substr(0,4) + "****" + phone.substr(8, 3)
}
// 规范时间格式，将毫秒数转换为正常时间
function formatDate(mills) {
    var newTime = new Date(mills);
    var year = newTime.getFullYear();
    var month = newTime.getMonth() + 1;
    var day = newTime.getDate();
    var hour = newTime.getHours();
    var minute = newTime.getMinutes();
    var second = newTime.getSeconds();
    return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
}

// 登录成功
function loginSuccess() {
    $(".log-out").css({"display": "none"});
    $.ajax({
        "url": "/user/loginSuccess",
        "type": "Post",
        "success": function (json) {
            if (json.state == 200) {
                $(".log-out").css({"display": "block"});
                $(".log-in").css({"display": "none"});
                $(".register").css({"display": "none"});
            }
        }
    })
}

// 退出系统
function logout() {
    $.ajax ({
        "url": "/user/logout",
        "type": "Post",
        "success": function (json) {
            if (json.state == 200) {
                alert("退出成功...");
            }
        }
    })
}