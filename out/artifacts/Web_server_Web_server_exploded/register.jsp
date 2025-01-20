<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MomoBlog-注册</title>
    <link rel="stylesheet" href="./static/styles/styles.css">
    <link rel="stylesheet" href="./static/styles/index-styles.css">
    <script src="./static/js/register.js" defer></script>
    <script src="./static/js/captcha.js" defer></script>
</head>
<body>
<div class="register-container">
    <h1>注册</h1>
    <form name="register" action="RegisterServlet" method="POST" onsubmit="return validatePassword(this)">
        <div class="form-group">
            <label for="nowUsername"></label>
            <input type="text" id="nowUsername" name="nowUsername" required placeholder="请输入用户名">
        </div>

        <div class="form-group">
            <label for="nowPassword"></label>
            <input type="password" id="nowPassword" name="nowPassword" required placeholder="请输入密码">
        </div>
        <small class="password-tip"> 必须包含大小写字母和数字，长度在6-20位之间。</small>

        <div class="form-group">
            <label for="nowCaptchaCode"></label>
            <input type="text" id="nowCaptchaCode" name="nowCaptchaCode" size="10" required placeholder="请输入验证码">
            <img id="imgValidate" src="captcha.jsp" alt="验证码" onclick="refreshCaptcha(this)">
        </div>

        <div class="form-group">
            <input type="submit" value="注册">
        </div>
    </form>
    <div class="back-to-login" onclick=backToLogin()><</div>
</div>
</body>

<%
    String errorMessage = request.getParameter("error");
%>
<script>
    message = "<%= errorMessage != null ? errorMessage : "" %>"
    if (message) alert(message);
</script>

</html>
