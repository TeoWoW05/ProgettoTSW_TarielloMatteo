<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="Model.Carrello,  Model.CarrelloItem " %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Checkout - Piece-B-Piece</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/General.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/Checkout.css">
         <script src="${pageContext.request.contextPath}/javascript/Formattazione.js"></script>
</head>
<body>
 <%@ include file="Header.jsp" %>
 
  <%
        Carrello carrello = (Carrello) session.getAttribute("carrello");
    %>
    
    <div class="checkout-container">
        <h1>🛒 Checkout</h1>
        
        <!-- Messaggi errore -->
        <%
        String erroreCheckout = (String) session.getAttribute("erroreCheckout");
        if (erroreCheckout != null) {
        %>
            <div class="message message-error">
                <span>⚠️</span> <%= erroreCheckout %>
            </div>
        <%
            session.removeAttribute("erroreCheckout");
        }
        %>
        
        <div class="checkout-grid">
            
            <!-- Form dati -->
            <div class="checkout-form">
                
                <!-- Dati Spedizione -->
                <div class="checkout-card">
                    <h2>📍 Indirizzo di Spedizione</h2>
                    
                    <form id="checkout-form" action="${pageContext.request.contextPath}/CheckoutServlet" method="post">
                        
                        <div class="form-group">
                            <label for="via">Via</label>
                            <input type="text" id="via" name="via" placeholder="es. Via Roma">
                        </div>
                        
                        <div class="form-row">
                            <div class="form-group form-half">
                                <label for="civico">Civico</label>
                                <input type="text" id="civico" name="civico" placeholder="es. 42">
                            </div>
                            
                            <div class="form-group form-half">
                                <label for="cap">CAP</label>
                                <input type="text" id="cap" name="cap" placeholder="es. 00100" maxlength="5">
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="citta">Città</label>
                            <input type="text" id="citta" name="citta" placeholder="es. Roma">
                        </div>
                        
                        <!-- Dati Carta -->
                        <h2 style="margin-top: 30px;">💳 Dati Carta di Credito</h2>
                        
                        <div class="form-group">
                            <label for="pan">Numero Carta</label>
                            <input type="text" id="pan" name="pan" placeholder="1234 5678 9012 3456" maxlength="19" oninput="formattaCarta(this)">
                            <small>16 cifre</small>
                        </div>
                        
                        <div class="form-row">
                            <div class="form-group form-half">
                                <label for="scadenza">Scadenza</label>
                                <input type="text" id="scadenza" name="scadenza" placeholder="MM/AA" maxlength="5" oninput="formattaScadenza(this)">
                            </div>
                            
                            <div class="form-group form-half">
                                <label for="cvv">CVV</label>
                                <input type="text" id="cvv" name="cvv" placeholder="123" maxlength="3" oninput="this.value = this.value.replace(/[^0-9]/g, '')">
                            </div>
                        </div>
                        
                        <button type="submit" class="btn-acquista">
                            Conferma ordine
                        </button>
                    </form>
                </div>
                
            </div>
            
            <!-- Riepilogo ordine -->
            <div class="checkout-riepilogo">
                <div class="checkout-card">
                    <h2>📦 Riepilogo Ordine</h2>
                    
                    <div class="riepilogo-items">
                        <%
                        for (CarrelloItem item : carrello.getItems()) {
                        %>
                            <div class="riepilogo-item">
                                <div class="riepilogo-item-info">
                                    <span class="riepilogo-item-nome"><%= item.getProdotto().getNome() %></span>
                                    <span class="riepilogo-item-qty">x<%= item.getQuantita() %></span>
                                </div>
                                <span class="riepilogo-item-prezzo">€ <%= String.format("%.2f", item.getSubtotale()).replace(",", ".") %></span>
                            </div>
                        <%
                        }
                        %>
                    </div>
                    
                    <div class="riepilogo-totale">
                        <span>Totale</span>
                        <span>€ <%= String.format("%.2f", carrello.getTotale()).replace(",", ".") %></span>
                    </div>
                    
                    <a href="${pageContext.request.contextPath}/CarrelloServlet" class="btn-back-link">← Torna al carrello</a>
                </div>
            </div>
            
        </div>
    </div>
 
 
 
 <%@ include file="Footer.jsp" %>
</body>
</html>