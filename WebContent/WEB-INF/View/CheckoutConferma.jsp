<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ordine Confermato - Piece B Piece</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/General.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/Checkout.css">
</head>
<body>

    <%@ include file="Header.jsp" %>
    
    <%
        String messaggio = (String) session.getAttribute("ordineCompletato");
        if (messaggio == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
    %>
    
    <div class="conferma-container">
        <div class="conferma-card">
            <div class="conferma-icon">✅</div>
            <h1>Ordine Completato!</h1>
            <p><%= messaggio %></p>
            <p style="color: #888;">Il tuo ordine è stato effettuato correttamente.</p>
            
            <div class="conferma-azioni">
                <a href="${pageContext.request.contextPath}/ProfiloUtenteServlet" class="btn-primary">📋 Vai ai miei ordini</a>
                <a href="${pageContext.request.contextPath}/ProdottiServlet" class="btn-secondary">🛍️ Continua lo shopping</a>
            </div>
        </div>
    </div>
    
    <%
        session.removeAttribute("ordineCompletato");
    %>

    <%@ include file="Footer.jsp" %>

</body>
</html>