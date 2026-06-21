<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Model.Ordine, java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestione Ordini - Admin</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/General.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/Ordini.css">
</head>
<body>

    <%@ include file="Header.jsp" %>
    
    <div class="admin-container" style="max-width: 1200px;">
        <div class="admin-header">
            <h1>📦 Gestione Ordini</h1>
            <a href="${pageContext.request.contextPath}/ProfiloUtenteServlet" class="btn-back">← Torna al profilo</a>
        </div>
        
        <!-- Filtri -->
        <div class="filtri-ordini">
            <form action="${pageContext.request.contextPath}/ControlloOrdiniServlet" method="GET" class="filtri-form">
                <div class="filtro-gruppo">
                    <label>Da data:</label>
                    <input type="date" name="dataDa" value="${dataDa}">
                </div>
                
                <div class="filtro-gruppo">
                    <label>A data:</label>
                    <input type="date" name="dataA" value="${dataA}">
                </div>
                
                <div class="filtro-gruppo">
                    <label>Cliente (email):</label>
                    <input type="text" name="emailCliente" placeholder="Email cliente..." value="${emailCliente}">
                </div>
                
                <div class="filtro-azioni">
                    <button type="submit" class="btn-filtra">🔍 Filtra</button>
                    <a href="${pageContext.request.contextPath}/ControlloOrdiniServlet" class="btn-reset">🔄 Reset</a>
                </div>
            </form>
        </div>
        
        <!-- Messaggi -->
        <%
        String errore = (String) request.getAttribute("errore");
        if (errore != null) {
        %>
            <div class="message message-error">⚠️ <%= errore %></div>
        <%
        }
        
        ArrayList<Ordine> ordini = (ArrayList<Ordine>) request.getAttribute("ordini");
        %>
        
        <!-- Tabella ordini -->
        <div class="tabella-container">
            <table class="tabella-ordini">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Data</th>
                        <th>Cliente</th>
                        <th>Totale</th>
                        <th>Indirizzo</th>
                        <th>Pagamento</th>
                        <th>Stato</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    if (ordini != null && !ordini.isEmpty()) {
                        for (Ordine o : ordini) {
                    %>
                        <tr>
                            <td>#<%= o.getId() %></td>
                            <td><%= o.getDataFormattata() %></td>
                            <td><%= o.getEmail_utente() %></td>
                            <td><%= o.getPrezzoFormattato() %></td>
                            <td><%= o.getIndirizzoCompleto() %></td>
                            <td>****<%= o.getCartaFormattata() %></td>
                            <td>
                                <span class="stato-badge stato-<%= o.getStato().toLowerCase().replace(" ", "-") %>">
                                    <%= o.getStato() %>
                                </span>
                            </td>
                        </tr>
                    <%
                        }
                    } else {
                    %>
                        <tr>
                            <td colspan="7" class="nessun-risultato">Nessun ordine trovato</td>
                        </tr>
                    <%
                    }
                    %>
                </tbody>
            </table>
        </div>
        
        <!-- Riepilogo -->
        <%
        if (ordini != null && !ordini.isEmpty()) {
            float totaleComplessivo = 0;
            for (Ordine o : ordini) {
                totaleComplessivo += o.getTotale_ordine();
            }
        %>
            <div class="riepilogo-ordini">
                <p>Ordini trovati: <strong><%= ordini.size() %></strong></p>
                <p>Totale complessivo: <strong>€ <%= String.format("%.2f", totaleComplessivo).replace(",", ".") %></strong></p>
            </div>
        <%
        }
        %>
    </div>

    <%@ include file="Footer.jsp" %>

</body>
</html>