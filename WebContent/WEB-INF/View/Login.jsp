<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login - Piece B Piece</title>
<link rel="stylesheet" type="text/css"  href="${pageContext.request.contextPath}/styles/LoginReg.css">
<script src="${pageContext.request.contextPath}/javascript/RegLog.js"></script>
</head>
<body>

<h1>Login</h1>

<form action="" method="post">

<div class="label">
<label for="email">Email</label>
<input type="email" name="email">
</div>
<div class="label">
<label for="pass">Password</label>
<input type="password" name="pass" id="mostraPass">
</div>
<div id="mostraPass">
<input type="checkbox" name="mostraPass" onclick="logPass()">
<label for="mostraPass">Mostra Password</label>
</div>
 <br>

<input type="submit" value="Invio">



</form>

</body>
</html>