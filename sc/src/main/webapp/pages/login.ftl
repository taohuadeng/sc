<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>login</title>
</head>
<body>
<form action="${rc.contextPath}/spring_security_login" method="post">
    <table>
        <tr>
            <td>公司编号：</td>
            <td><input type="text" name="corpCode"/></td>
        </tr>
        <tr>
            <td>用户名：</td>
            <td><input type="text" name="j_username"/></td>
        </tr>
        <tr>
            <td>密码：</td>
            <td><input type="password" name="j_password"/></td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="登录"/>
                <input type="reset" value="重置"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
