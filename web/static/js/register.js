function validatePassword(formElement) {
    const passwordField = formElement.nowPassword;
    const passwordValue = passwordField.value;
    const passwordRegex = /^[A-Za-z0-9]{6,20}$/;

    if (!passwordRegex.test(passwordValue)) {
        alert("密码必须同时包含大小写字母和数字且长度应该在6-20之间");
        passwordField.focus();
        return false;
    }
    return true;
}

function backToLogin() {
    window.location.href = "./index.jsp";
}