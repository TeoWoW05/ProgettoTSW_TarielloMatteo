<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login - Piece B Piece</title>
<link rel="stylesheet" type="text/css"  href="${pageContext.request.contextPath}/styles/LoginReg.css">
<script src="${pageContext.request.contextPath}/javascript/RegLog.js"></script>
<script src="${pageContext.request.contextPath}/javascript/Navbar.js"></script>
</head>
<body>

<%@ include file ="Header.jsp" %>

<h1>Login</h1>

<%
String errore = (String) request.getAttribute("errore");
String registrato = request.getParameter("registrato");
if (errore != null) {
%>
    <div style="color: red; text-align: center; margin-bottom: 20px;"><%= errore %></div>
<%
} else if (registrato != null) {
%>
    <div style="color: green; text-align: center; margin-bottom: 20px;">Registrazione completata! Effettua il login.</div>
<%
}
%>


<form action="${pageContext.request.contextPath}/LogServlet" method="post" class="reglog">

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

<%@ include file ="Footer.jsp" %>

</body>
</html>