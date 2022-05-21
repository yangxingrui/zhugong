<%--
  Created by IntelliJ IDEA.
  User: yang2
  Date: 2022/3/27
  Time: 10:17
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
<form method="post" action="/user/findbynp" autocomplete="on">
    <table>
        <tr>
            <td>姓名：</td>
            <td><input id="username" name="username" type="text"></td>
        </tr>
        <tr>
            <td>密码：</td>
            <td><input id="password" name="password" type="password"></td>
        </tr>
        <tr>
            <td><input type="submit" value="登录"></td>
        </tr>
    </table>
</form>
</body>

</html>
