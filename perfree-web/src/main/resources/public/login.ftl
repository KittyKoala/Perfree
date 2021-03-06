<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>Perfree</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, shrink-to-fit=no"/>
    <meta name="renderer" content="webkit"/>
    <meta name="force-rendering" content="webkit"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <link href="/public/libs/mdui/css/mdui.min.css" rel="stylesheet"/>
    <link href="/public/libs/dialog/dialog.css" rel="stylesheet"/>
    <link href="/public/css/login.css" rel="stylesheet"/>
    <script type="text/javascript">
        if (window !== top)
            top.location.href = location.href;
    </script>
</head>
<body class="mdui-theme-primary-indigo">
    <div class="p-login-box mdui-shadow-3">
        <div class="p-login-box-title">Perfree</div>
        <form id="loginForm">
            <div class="p-input-box">
                <span class="p-input-label">账号:</span>
                <input class="p-input" type="text" name="account" required placeholder="请输入账号"/>
            </div>

            <div class="p-input-box">
                <span class="p-input-label">密码:</span>
                <input class="p-input" type="password" name="password" required placeholder="请输入密码"/>
            </div>
        </form>

        <button class="mdui-btn mdui-btn-raised mdui-color-theme p-login-btn">登录</button>
        <p class="p-login-box-bottom">
            还没有账号? 点击<a class="mdui-text-color-theme p-register-btn">注册</a>
        </p>
    </div>
    <script src="/public/libs/jquery/jquery-3.5.1.min.js"></script>
    <script src="/public/libs/mdui/js/mdui.min.js"></script>
    <script src="/public/js/common.js"></script>
    <script src="/public/libs/dialog/dialog.js"></script>
    <script src="/public/js/login.js"></script>
</body>
</html>