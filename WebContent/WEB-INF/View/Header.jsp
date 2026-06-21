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
<script src="${pageContext.request.contextPath}/javascript/Navbar.js"></script>
</head>
<body>

<header class="site-header">
    <div class="header-container">

        <div class="logo">
                <h1> PIECE-<span>B-PIECE</span></h1>
        </div>

        <div class="search-bar">
            <form action="${pageContext.request.contextPath}/ProdottiServlet" method="GET">
                <input type="text" name="cerca" placeholder="Cerca nel negozio..." value="${param.q}">
                <button type="submit">🔍</button>
            </form>
        </div>

        <nav class="main-nav">
            <ul>
                <li><a href="${pageContext.request.contextPath}/HPServlet">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/ProdottiServlet">Prodotti</a></li>
                <li><a href="${pageContext.request.contextPath}/CarrelloServlet">Carrello</a></li>
                <%
                  Object utenteObj = session.getAttribute("utente");
                  if(utenteObj == null){		
                %>
                <li><a href="${pageContext.request.contextPath}/RegServlet">Registrati</a></li>
                <li><a href="${pageContext.request.contextPath}/LogServlet">Login</a></li>
                <%
                  } else{
                	  Model.Utente utente = (Model.Utente) utenteObj;
                %>
                <li><span id="utente"><a href="${pageContext.request.contextPath}/ProfiloUtenteServlet"> <%= utente.getNickname() %> </a></span></li>
                <% if (utente.isAdmin()) { %>
        <li><a href="${pageContext.request.contextPath}/ControlloOrdiniServlet">Pannello Admin</a></li>
        	 <% } %>
            <li><a href="${pageContext.request.contextPath}/LogoutServ">Logout</a></li>
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