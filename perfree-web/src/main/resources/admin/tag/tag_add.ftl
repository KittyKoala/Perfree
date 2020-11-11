<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>标签</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, shrink-to-fit=no"/>
    <meta name="renderer" content="webkit"/>
    <meta name="force-rendering" content="webkit"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <link rel="stylesheet" href="/public/libs/layui-v2.5.6/layui/css/layui.css">
    <link href="/public/libs/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet"/>
    <link href="/admin/static/css/main.css" rel="stylesheet"/>
</head>
<body class="layui-layout-body">
<div class="p-container p-add-panel">
    <form class="layui-form" lay-filter="addForm">
        <div class="layui-form-item">
            <label class="layui-form-label">标签名:</label>
            <div class="layui-input-block">
                <input type="text" name="name" required  lay-verify="required" placeholder="请输入标签名" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="add-btn-box">
            <button type="submit" lay-submit lay-filter="addForm" class="layui-btn layui-btn-normal p-submit-btn">确定</button>
            <button type="button" class="layui-btn layui-btn-primary p-cancel-btn">取消</button>
        </div>
    </form>
</div>

<script src="/public/libs/jquery/jquery-3.5.1.min.js"></script>
<script src="/public/libs/layui-v2.5.6/layui/layui.all.js"></script>
<script>
    let form,element,layer;
    layui.use(['layer', 'form', 'element'], function(){
        form = layui.form;
        element = layui.element;
        layer = layui.layer;
        // 表单验证
        form.verify({});
        // 表单提交
        form.on('submit(addForm)', function(data){
            $.ajax({
                type: "POST",
                url: "/admin/tag/add",
                contentType:"application/json",
                data: JSON.stringify(data.field),
                success:function(data){
                    if (data.code === 200){
                            parent.queryTable();
                            parent.layer.msg("添加成功", {icon: 1});
                            const index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                    } else {
                        layer.msg(data.msg, {icon: 2});
                    }
                },
                error: function (data) {
                    layer.msg("添加失败", {icon: 2});
                }
            });
            return false;
        });
    });

    // 取消
    $(".p-cancel-btn").click(function (){
        const index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    });
</script>
</body>
</html>