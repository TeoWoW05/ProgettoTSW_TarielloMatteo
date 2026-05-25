<%@page import="Model.Utente"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Header</title>
<link rel="stylesheet" type="text/css"  href="${pageContext.request.contextPath}/styles/Header.css">
</head>
<body>

<header class="site-header">
    <div class="header-container">

        <div class="logo">
                <h1> PIECE-<span>B-PIECE</span></h1>
        </div>

        <div class="search-bar">
            <form action="${pageContext.request.contextPath}/ricerca" method="GET">
                <input type="text" name="q" placeholder="Cerca nel negozio..." value="${param.q}">
                <button type="submit">🔍</button>
            </form>
        </div>

        <nav class="main-nav">
            <ul>
                <li><a href="${pageContext.request.contextPath}/">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/prodotti">Prodotti</a></li>
                <li><a href="${pageContext.request.contextPath}/offerte">Offerte</a></li>
                <li><a href="${pageContext.request.contextPath}/carrello">Carrello</a></li>
                <%
                  Object utenteObj = session.getAttribute("utente");
                  if(utenteObj == null){		
                %>
                <li><a href="${pageContext.request.contextPath}/Registrazione.jsp">Registrati</a></li>
                <li><a href="${pageContext.request.contextPath}/Login.jsp">Login</a></li>
                <%
                  } else{
                	  Model.Utente utente = (Model.Utente) utenteObj;
                %>
                <li><span>Benvenuto, <%= utente.getNickname() %></span></li>
            <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
            <% } %>
            </ul>
        </nav>

        <button class="mobile-toggle" aria-label="Menu">
            <span></span>
            <span></span>
            <span></span>
        </button>
    </div>
</header>

</body>
</html>