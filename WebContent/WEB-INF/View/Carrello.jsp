<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Model.Carrello, Model.CarrelloItem" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Carrello - Piece B Piece</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/General.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/Carrello.css">
    <script> const contextPath = "${pageContext.request.contextPath}"; </script>
     <script src="${pageContext.request.contextPath}/javascript/Modali.js"></script>
     <script src="${pageContext.request.contextPath}/javascript/Ajax.js"></script>
</head>
<body>

    <%@ include file="Header.jsp" %>
    
    <div class="carrello-container">
        <h1>🛒 Il tuo Carrello</h1>
        
        <%
        String messaggio = (String) session.getAttribute("messaggioSuccesso");
        if (messaggio != null) {
        %>
            <div class="message message-success">
                <span>✅</span> <%= messaggio %>
            </div>
        <%
            session.removeAttribute("messaggioSuccesso");
        }
        
        String errore = (String) session.getAttribute("errore");
        if (errore != null) {
        %>
            <div class="message message-error">
                <span>⚠️</span> <%= errore %>
            </div>
        <%
            session.removeAttribute("errore");
        }
        
        Carrello carrello = (Carrello) session.getAttribute("carrello");
        
        if (carrello == null || carrello.isEmpty()) {
        %>
            <div class="carrello-vuoto">
                <p>🛒 Il tuo carrello è vuoto</p>
                <a href="${pageContext.request.contextPath}/ProdottiServlet" class="btn-primary">
                    Vai allo shopping
                </a>
            </div>
        <%
        } else {
        %>
            <div class="carrello-grid">
                <!-- Lista prodotti -->
                <div class="carrello-items">
                    <%
                    for (CarrelloItem item : carrello.getItems()) {
                    %>
                        <div class="carrello-item">
                            <div class="item-immagine">
                                <%
                                if (item.getProdotto().getImmagine() != null && !item.getProdotto().getImmagine().isEmpty()) {
                                %>
                                    <img src="${pageContext.request.contextPath}/immagini/prodotti/<%= item.getProdotto().getImmagine() %>" 
                                         alt="<%= item.getProdotto().getNome() %>">
                                <%
                                } else {
                                %>
                                    <div class="no-image">📷</div>
                                <%
                                }
                                %>
                            </div>
                            
                            <div class="item-info">
                                <h3>
                                    <a href="${pageContext.request.contextPath}/DettagliServlet?id=<%= item.getProdotto().getCodiceProdotto() %>">
                                        <%= item.getProdotto().getNome() %>
                                    </a>
                                </h3>
                                <p class="item-prezzo">€ <%= String.format("%.2f", item.getProdotto().getCosto()).replace(",", ".") %></p>
                            </div>
                            
                            <div class="item-quantita">
    						<button type="button" onclick="aggiornaQuantitàCarrello(<%= item.getProdotto().getCodiceProdotto() %>, -1)">−</button>
    							<span id="qty-<%= item.getProdotto().getCodiceProdotto() %>"data-max="<%= item.getProdotto().getQuantitaMagazzino() %>"><%= item.getQuantita() %></span>
    						<button type="button"  id="btn-plus-<%= item.getProdotto().getCodiceProdotto() %>"
            				<%= item.getQuantita() >= item.getProdotto().getQuantitaMagazzino() ? "disabled" : "" %> onclick="aggiornaQuantitàCarrello(<%= item.getProdotto().getCodiceProdotto() %>, 1)">+</button>
							</div>
                            
                            <div class="item-subtotale">
                                € <%= String.format("%.2f", item.getSubtotale()).replace(",", ".") %>
                            </div>
                            
                            <%
							// Prepara il nome del prodotto per JavaScript
							String nomePulito = item.getProdotto().getNome()
    						.replace("\\", "\\\\")   // Escape backslash
    						.replace("'", "\\'")     // Escape apice singolo
    						.replace("\"", "&quot;"); // Escape virgolette
								
						    %>
                            
                            <div class="item-rimuovi">
                                 <button type="button" 
            						class="btn-remove"
           							data-id="<%= item.getProdotto().getCodiceProdotto() %>"
            						data-nome="<%= item.getProdotto().getNome() %>"
            						onclick="openRemoveModal(this)">
       								 🗑️
    							</button>
                            </div>
                        </div>
                    <%
                    }
                    %>
                </div>
                
                <!-- Riepilogo -->
                <div class="carrello-riepilogo">
                    <h3>Riepilogo Ordine</h3>
                    
                    <div class="riepilogo-riga">
                        <span>Numero prodotti:</span>
                        <span><%= carrello.getNumeroProdotti() %></span>
                    </div>
                    
                    <div class="riepilogo-riga totale">
                        <span>Totale:</span>
                        <span>€ <%= String.format("%.2f", carrello.getTotale()).replace(",", ".") %></span>
                    </div>
                    
                    <a href="${pageContext.request.contextPath}/CheckoutServlet" class="btn-acquista">
                        ⚡ Procedi all'acquisto
                    </a>
                    
 					<button type="button" class="btn-svuota" onclick="openClearModal()">
                        🗑️ Svuota carrello
                    </button>
                    
                    <a href="${pageContext.request.contextPath}/ProdottiServlet" class="btn-continua">
                        ← Continua lo shopping
                    </a>
                </div>
            </div>
        <%
        }
        %>
    </div>
    
      <div id="removeModal" class="modal" style="display: none;">
        <div class="modal-content">
            <div class="modal-header">
                <h3>🗑️ Rimuovi Prodotto</h3>
            </div>
            <div class="modal-body">
                <p>Sei sicuro di voler rimuovere questo prodotto dal carrello?</p>
                <p class="modal-product-name" id="modalRemoveProductName"></p>
            </div>
            <div class="modal-footer">
                <button class="btn-cancel" onclick="closeRemoveModal()">❌ Annulla</button>
                <a id="confirmRemoveBtn" href="#" class="btn-rimuovi">🗑️ Rimuovi</a>
            </div>
        </div>
    </div>
    
     <div id="clearModal" class="modal" style="display: none;">
        <div class="modal-content">
            <div class="modal-header">
                <h3>⚠️ Svuota Carrello</h3>
            </div>
            <div class="modal-body">
                <p>Sei sicuro di voler svuotare completamente il carrello?</p>
                <p style="color: #f44336; font-size: 0.9rem;">Tutti i prodotti saranno rimossi!</p>
            </div>
            <div class="modal-footer">
                <button class="btn-cancel" onclick="closeClearModal()">❌ Annulla</button>
                <a href="${pageContext.request.contextPath}/CarrelloServlet?action=clear" class="btn-svuota-modale">🗑️ Svuota</a>
            </div>
        </div>
    </div>

    <%@ include file="Footer.jsp" %>

</body>
</html>