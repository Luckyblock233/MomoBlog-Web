<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>404 - Page Not Found</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/styles/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/styles/home-styles.css">
    <script defer>
        let countdown = 5;
        const timer = setInterval(() => {
            document.getElementById("timer").innerText = countdown;
            countdown--;
            if (countdown < 0) {
                clearInterval(timer);
                goBack();
            }
        }, 1000);

        function goBack() {
            history.back();
        }
    </script>
</head>

<body>
<header>
    <nav>
        <ul>
            <li><a href="${pageContext.request.contextPath}/protected/home.html">
                <img src="${pageContext.request.contextPath}/static/img/logo.png" alt="logo">
            </a></li>
            <li><a href="${pageContext.request.contextPath}/protected/home.html">首页</a></li>
            <li><a href="${pageContext.request.contextPath}/static/html/about.html">关于</a></li>
        </ul>
    </nav>
</header>

<main>
    <h1>404 - 页面未找到</h1>
    <p>抱歉，您访问的页面不存在。</p>
    <p>将在 <span id="timer">5</span> 秒后自动 <span id="back" onclick="goBack()">返回上一页面</span>。</p>
</main>

<footer>
    <ul><li> MomoBlog ©2024 Created by Luckyblock </li></ul>
    <ul><li>Version: 1.0.0-beta.20</li></ul>
    <ul><li><a href="https://www.cnblogs.com/luckyblock/">使用条款和声明 | 意见反馈</a></li></ul>
    <ul><li><a href="https://www.cnblogs.com/luckyblock/">基沃托斯ICP备2024114514号-2 | 基沃托斯公网安备1145141919810号</a></li></ul>
</footer>

</body>

</html>
