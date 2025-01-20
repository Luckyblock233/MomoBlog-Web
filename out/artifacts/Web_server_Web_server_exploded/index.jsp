<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MomoBlog-登录</title>
    <link rel="stylesheet" href="./static/styles/styles.css">
    <link rel="stylesheet" href="./static/styles/index-styles.css">
    <script src="./static/js/captcha.js" defer></script>
</head>

<body>
<div class="login-container">
    <h1>登录</h1>
    <form name="login" action="${pageContext.request.contextPath}/LoginServlet" method="post" onsubmit="return validate(this)">
        <div class="form-group">
            <label for="nowUsername"></label>
            <input type="text" id="nowUsername" name="nowUsername" required placeholder="请输入用户名">
        </div>

        <div class="form-group">
            <label for="nowPassword"></label>
            <input type="password" id="nowPassword" name="nowPassword" required placeholder="请输入密码">
        </div>

        <div class="form-group">
            <label for="nowCaptchaCode"></label>
            <input type="text" id="nowCaptchaCode" name="nowCaptchaCode" size="10" required placeholder="请输入验证码">
            <img id="imgValidate" src="captcha.jsp" alt="验证码" onclick="refreshCaptcha(this)">
        </div>

        <div class="form-group">
            <input type="checkbox" id="keep" name="keep">
            <label for="keep">两周内免登陆</label>
        </div>

        <div class="form-group">
            <input type="submit" value="登录">
        </div>
    </form>

    <div class="register-link">
        <form action="register.jsp" method="post">
            <input type="submit" value="注册">
        </form>
    </div>
</div>
</body>

<%
    String errorMessage = request.getParameter("error");
%>
<script>
    message = "<%= errorMessage != null ? errorMessage : "" %>"
    if (message) alert(message);

    console.log("    ____ _                                __         __     _                  __  __" +
        "   /  _/( )____ ___     _      __ ____ _ / /_ _____ / /_   (_)____   ____ _   / / / /" +
        "   / /  |// __ `__ \   | | /| / // __ `// __// ___// __ \ / // __ \ / __ `/  / / / /" +
        " _/ /    / / / / / /   | |/ |/ // /_/ // /_ / /__ / / / // // / / // /_/ /  / /_/ /  _  _  _" +
        "/___/   /_/ /_/ /_/    |__/|__/ \__,_/ \__/ \___//_/ /_//_//_/ /_/ \__, /   \____/  (_)(_)(_)" +
        "                                                                  /____/                     ");
</script>
</html>