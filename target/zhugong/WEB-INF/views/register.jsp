<%--
  Created by IntelliJ IDEA.
  User: yang2
  Date: 2022/3/20
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Getting Started: Serving Web Content</title>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8" />
</head>
<body>
<form method="post" action="/user/insert" autocomplete="on">
    <table>
        <tr>
            <td>姓名：</td>
            <td><input id="name" name="name" type="text"></td>
        </tr>
        <tr>
            <td>密码：</td>
            <td><input id="password" name="password" type="text"></td>
        </tr>
        <tr>
            <td>年龄：</td>
            <td><input id="age" name="age" type="text"></td>
        </tr>

        <tr>
            <td><input type="submit" value="提交"></td>
        </tr>
    </table>
</form>
<form method="post" action="/user/sendCode">
    <table>
        <tr>
            <td>手机号：</td>
            <td><input id="phonenum" name="phonenum" type="text"></td>
        </tr>
        <tr>
            <td>验证码：</td>
            <td><input id="vericode" name="vericode" type="text" placeholder="请输入验证码"></td>
        </tr>
        <tr>
            <td><input type="submit" value="发送"></td>
        </tr>
    </table>
</form>
</body>
<script type="text/javascript">
    var InterValObj; //timer变量，控制时间
    var count = 60; //间隔函数，1秒执行
    var curCount;//当前剩余秒数
    function sendMessage() {
        curCount = count;
        $("#btn").attr("disabled", "true");
        $("#btn").val(curCount + "秒后可重新发送");
        InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次请求后台发送验证码 TODO
    }
    //timer处理函数
    function SetRemainTime() {
        if (curCount == 0) {
            window.clearInterval(InterValObj);//停止计时器
            $("#btn").removeAttr("disabled");//启用按钮
            $("#btn").val("重新发送验证码");
        } else {
            curCount--;
            $("#btn").val(curCount + "秒后可重新发送");
        }
    }
</script>
</html>
