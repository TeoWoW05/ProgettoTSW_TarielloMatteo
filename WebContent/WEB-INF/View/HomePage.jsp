<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Model.Prodotto, java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Piece-B-Piece - HomePage</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/General.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/Homepage.css">
    <script src="${pageContext.request.contextPath}/javascript/Carosello.js"></script>
</head>
<body>

    <%@ include file="Header.jsp" %>

    <!-- Carosello -->
    <div class="carosello-container">
        <div class="carosello" id="carosello">
            <%
            ArrayList<Prodotto> prodottiCarosello = (ArrayList<Prodotto>) request.getAttribute("prodottiCarosello");
            if (prodottiCarosello != null && !prodottiCarosello.isEmpty()) {
                for (int i = 0; i < prodottiCarosello.size(); i++) {
                    Prodotto p = prodottiCarosello.get(i);
            %>
                <div class="carosello-item <%= i == 0 ? "active" : "" %>" data-index="<%= i %>">
                    <a href="${pageContext.request.contextPath}/DettagliServlet?id=<%= p.getCodiceProdotto() %>">
                        <div class="carosello-immagine">
                            <%
                            if (p.getImmagine() != null && !p.getImmagine().isEmpty()) {
                            %>
                                <img src="${pageContext.request.contextPath}/immagini/prodotti/<%= p.getImmagine() %>" 
                                     alt="<%= p.getNome() %>">
                            <%
                            } else {
                            %>
                                <div class="no-image">📷</div>
                            <%
                            }
                            %>
                        </div>
                        <div class="carosello-info">
                            <h2><%= p.getNome() %></h2>
                            <p class="carosello-prezzo">€ <%= String.format("%.2f", p.getCosto()).replace(",", ".") %></p>
                            <span class="carosello-link">Scopri →</span>
                        </div>
                    </a>
                </div>
            <%
                }
            }
            %>
        </div><!-- chiusura carosello -->

        <!-- Frecce -->
        <button class="carosello-btn carosello-prev" onclick="cambiaSlide(-1)">&#10094;</button>
        <button class="carosello-btn carosello-next" onclick="cambiaSlide(1)">&#10095;</button>

      
    </div><!-- chiusura carosello-container -->

    <!-- Prodotti in evidenza -->
    <div class="home-container">
        <h2 class="section-title">🔥 Prodotti in evidenza</h2>

        <div class="prodotti-grid">
            <%
            ArrayList<Prodotto> prodottiEvidenza = (ArrayList<Prodotto>) request.getAttribute("prodottiEvidenza");
            if (prodottiEvidenza != null && !prodottiEvidenza.isEmpty()) {
                for (Prodotto p : prodottiEvidenza) {
            %>
                <a href="${pageContext.request.contextPath}/DettagliServlet?id=<%= p.getCodiceProdotto() %>" 
                   class="prodotto-card">
                    <div class="prodotto-immagine">
                        <%
                        if (p.getImmagine() != null && !p.getImmagine().isEmpty()) {
                        %>
                            <img src="${pageContext.request.contextPath}/immagini/prodotti/<%= p.getImmagine() %>" 
                                 alt="<%= p.getNome() %>">
                        <%
                        } else {
                        %>
                            <div class="no-image">📷</div>
                        <%
                        }
                        %>
                    </div>
                    <h3><%= p.getNome() %></h3>
                    <p class="prezzo">€ <%= String.format("%.2f", p.getCosto()).replace(",", ".") %></p>
                </a>
            <%
                }
            }
            %>
        </div><!-- chiusura prodotti-grid -->

        <!-- Vedi tutti -->
        <div class="vedi-tutti">
            <a href="${pageContext.request.contextPath}/ProdottiServlet" class="btn-vedi-tutti">
                Vedi tutti i prodotti →
            </a>
        </div>
    </div><!-- chiusura home-container -->

    <%@ include file="Footer.jsp" %>

</body>
</html>