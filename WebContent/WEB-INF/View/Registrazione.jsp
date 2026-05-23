<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registrazione - Piece B Piece</title>
<link rel="stylesheet" type="text/css"  href="${pageContext.request.contextPath}/styles/LoginReg.css">
<script src="${pageContext.request.contextPath}/javascript/RegLog.js"></script>
</head>
<body>

<h1>Registrazione</h1>

<%
String errore = (String) request.getAttribute("Errore");
if (errore != null) {
%>
    <div class="error-message" style="display:block; color:red; text-align:center; margin-bottom:20px;">
        <%= errore %>
    </div>
<%
}
%>

<form action="${pageContext.request.contextPath}/RegServlet" method="post">

<div class="label">
<label for="nome">Nome</label>
<input type="text" name="nome">
</div>
<div class="label">
<label for="cognome">Cognome</label>
<input type="text" name="cognome">
</div>
<div class="label">
<label for="nickname">Nickname</label>
<input type="text" name="nickname">
</div>
<div class="label">
<label for="email">Email</label>
<input type="email" name="email">
</div>
<div class="label">
<label for="pass">Password</label>
<input type="password" name="pass" class="pass">
</div>
<div class="label">
<label for="passCon">Conferma Password</label>
<input type="password" name="passCon" class="pass">
</div>
<div id="mostraPass">
<input type="checkbox" name="mostraPass" onclick="regPass()">
<label for="mostraPass">Mostra Password</label>
</div>




 <br>

<input type="submit" value="Invio">



</form>

</body>
</html>