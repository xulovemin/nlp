<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%String basePath = request.getContextPath();%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link href="bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="col-lg-12">
    <form class="form-horizontal" id="form" style="margin-top: 20px;">
        <div class="form-group">
            <div class="col-lg-2 pull-right">
                <button class="btn btn-lg btn-block btn-primary" type="button" onclick="_submit();">提 交</button>
            </div>
        </div>
    </form>
</div>
<!-- Mainly scripts -->
<script src="js/lib/jquery/jquery-2.1.1.js"></script>
<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<script>
    var url = '';
    var CONTEXT_PATH = "<%=basePath%>";

    function _submit() {
        url = CONTEXT_PATH + '/test';
        var formData = $("#form").serializeArray();
        $.ajax({
            url: url,
            type: 'POST',
            data: formData,
            success: function (data) {

            }
        });
    }
</script>
</body>
</html>