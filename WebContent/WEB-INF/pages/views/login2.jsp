<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String sys= request.getContextPath();
	request.setAttribute("sys", sys);
%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>系统登录 - 驾校管理系统</title>
    <link rel="stylesheet" href="${sys }/common/css/style.css"/>
</head>
<body class="loginBox">
    <div class="loginHeader">
        <header >
            <h1>龙乡驾校管理系统</h1>
        </header>	
         <form id="form1" class="loginForm">
                <div class="inputbox">
                    <label for="user">用户名：</label>
                    <input id="user" type="text" name="loginName" placeholder="请输入登录名" required/>
                </div>
                <div class="inputbox">
                    <label for="mima">密码：</label>
                    <input id="mima" type="password" name="passWord" placeholder="请输入密码" required/>
                </div>
                <div class="subBtn">
                    <input id="loginBtn" type="button" value="登录" />
                    <input type="reset" value="重置"/>
                </div>
        </form>
    </div>
</body>
<script type="text/javascript" src="${sys }/common/js/jquery.js"></script>
<script type="text/javascript">
	$(function() {
		$("#loginBtn").click(function() {
			$.ajax({
				url : "${sys }/system/login",
				method : "post",
				data : $('#form1').serialize(),
				dataType : "json",
				/*beforeSend : function() {
					$("#loginBtn").attr("disabled", true);
				}, */
				success : function(data) {
					if (data.code == 1) {
						window.location.href = "${sys}/system/welcome";
					} else {
						$("#passWord").val("");
						$("#loginBtn").removeAttr("disabled");
						alert(data.msg);
					}
				},
				error:function(data){
					var error = data;
				}
			});
		})
	})
</script>
</html>