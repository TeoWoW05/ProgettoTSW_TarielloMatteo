<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Errore - Piece B Piece</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/General.css">
     <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/Error.css">
</head>
<body>

    <%@ include file="Header.jsp" %>

    <div class="error-container">
        <div class="error-icon">⚠️</div>
        
        <h1>Si è verificato un errore</h1>
        
        <%
        String errore = (String) request.getAttribute("errore");
        if (errore != null && !errore.isEmpty()) {
        %>
            <p><%= errore %></p>
        <%
        } else {
        %>
            <p>Qualcosa è andato storto. Riprova più tardi.</p>
        <%
        }
        %>
        
        <%
       
        String dettagli = (String) request.getAttribute("dettagli");
        if (dettagli != null && !dettagli.isEmpty()) {
        %>
            <div class="error-details">
                <strong>Dettagli:</strong><br>
                <%= dettagli %>
            </div>
        <%
        }
        %>
        
        <a href="${pageContext.request.contextPath}/HPServlet" class="btn-home">🏠 Torna alla Home</a>
        <br>
        <a href="javascript:history.back()" class="btn-back">← Torna indietro</a>
    </div>

    <%@ include file="Footer.jsp" %>

</body>
</html>