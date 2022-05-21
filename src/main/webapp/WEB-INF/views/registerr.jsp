<%--
  Created by IntelliJ IDEA.
  User: yang2
  Date: 2022/3/27
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>注册</title>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8" />
</head>
<body>
<form action="/user/insert" method="post">
    <label>
        输入账号：
        <input type="text" name="name">
    </label><br>
    <label>
        输入密码：
        <input type="password" name="password">
    </label><br>
    <label>
        年龄：
        <input type="text" name="age">
    </label><br>
    <label>
        输入手机：
    <input type="text" name="phonenum" id="phonenum">
    </label><br>
    <label>
        短信验证码：
        <input type="text" name="msgcode" oninput="value=value.replace(/[^\d]/g,'')" maxlength="6">
    </label>
    <input type="button" value="获取验证码" onclick="tencentcode()" id="getcode" name="getcode"><br>
    <input type="submit" value="注册">
</form>
<script type="text/javascript" src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
<script type="text/javascript">

    function tencentcode() {

        var phonenum=$("#phonenum").val();
        console.log(phonenum);
        time=3;
        var btn = $("#getcode");
        btn.attr("disabled", true);  //按钮禁止点击
        btn.val(time <= 0 ? "获取验证码" : ("" + (time) + "秒后可发送"));
        var hander = setInterval(function() {
            if (time <= 0) {
                clearInterval(hander); //清除倒计时
                btn.val("发送动态密码");
                btn.attr("disabled", false);
                return false;
            }else {
                btn.val("" + (time--) + "秒后可发送");
            }
        }, 1000);

        $.ajax({
            url:"./sendCode",
            type:"post",
            data:JSON.stringify({phonenum}),
            contentType:"application/json",
            dataType:"json",
            success: res => {
                console.log(res)
            },
            error: err => {
                console.log(err.statusText)
            }
        })
    }

</script>
</body>

</html>
