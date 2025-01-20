function refreshCaptcha(imgElement) {
    imgElement.src = "captcha.jsp?id=" + Math.random();
}