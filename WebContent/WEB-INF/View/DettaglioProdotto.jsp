<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Model.Prodotto, java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dettaglio Prodotto - Piece B Piece</title>
   <link rel="stylesheet" type="text/css"  href="${pageContext.request.contextPath}/styles/General.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/dettaglio.css">
     <script> const contextPath = "${pageContext.request.contextPath}"; </script>
    <script src="${pageContext.request.contextPath}/javascript/RegLog.js"></script>
    <script src="${pageContext.request.contextPath}/javascript/Ajax.js"></script>
</head>
<body>

      <%@ include file ="Header.jsp" %>

    <%
        Prodotto prodotto = (Prodotto) request.getAttribute("prodotto");
        ArrayList<String> categorie = (ArrayList<String>) request.getAttribute("categorie");
        
        if (prodotto != null) {
    %>

    <div class="dettaglio-container">
           
        <div class="dettaglio-grid">
            
            <!-- Immagine -->
            <div class="dettaglio-immagine">
                <%
                if (prodotto.getImmagine() != null && !prodotto.getImmagine().isEmpty()) {
                %>
                    <img src="${pageContext.request.contextPath}/immagini/prodotti/<%= prodotto.getImmagine() %>" 
                         alt="<%= prodotto.getNome() %>">
                <%
                } else {
                %>
                    <div class="no-image">
                        <span>📷 Nessuna immagine disponibile</span>
                    </div>
                <%
                }
                %>
            </div>
            
            <!-- Info prodotto -->
            <div class="dettaglio-info">
                
                <!-- Categorie -->
                <%
                if (categorie != null && !categorie.isEmpty()) {
                %>
                <div class="categorie-tags">
                    <%
                    for (String cat : categorie) {
                    %>
                        <span class="tag"><%= cat %></span>
                    <%
                    }
                    %>
                </div>
                <%
                }
                %>
                
                <!-- Nome -->
                <h1 class="nome-prodotto"><%= prodotto.getNome() %></h1>
                
                <!-- Prezzo -->
                <p class="prezzo-prodotto">€ <%= String.format("%.2f", prodotto.getCosto()) %></p>
                
                <!-- Disponibilità -->
                <div class="disponibilita">
                    <%
                    if (prodotto.getQuantitaMagazzino() > 0) {
                    %>
                        <span class="disponibile">✅ Disponibile</span>
                        <span class="quantita">(<%= prodotto.getQuantitaMagazzino() %> pezzi in magazzino)</span>
                    <%
                    } else {
                    %>
                        <span class="esaurito">❌ Esaurito</span>
                    <%
                    }
                    %>
                </div>
                
                <!-- Descrizione -->
                <div class="descrizione-prodotto">
                    <h3>Descrizione</h3>
                    <p><%= prodotto.getDescrizione() %></p>
                </div>
                
                <!-- Azioni -->
                <div class="azioni-acquisto">
                    <%
                    if (prodotto.getQuantitaMagazzino() > 0) {
                    %>
                        <!-- Quantità -->
                        <div class="quantita-selector">
                            <label for="qty">Quantità:</label>
                            <div class="qty-controls">
                                <button type="button" onclick="changeQty(-1)">−</button>
                                <input type="number" id="qty" value="1" min="1" max="<%= prodotto.getQuantitaMagazzino() %>" readonly>
                                <button type="button" onclick="changeQty(1)">+</button>
                            </div>
                        </div>
                        
                        <!-- Pulsante carrello -->
                        <button type="button" 
       					 class="btn-carrello" 
       					 id="btn-carrello"
       					 onclick="aggiungiAlCarrello(<%= prodotto.getCodiceProdotto() %>, document.getElementById('qty').value, this)">
    					🛒 Aggiungi al carrello
						</button>
                        <!-- Pulsante acquisto diretto -->
                        <a href="${pageContext.request.contextPath}/CheckoutServlet?action=buynow&id=<%= prodotto.getCodiceProdotto() %>&qty=1" 
                           class="btn-acquista" 
                           id="btn-acquista">
                            ⚡ Acquista ora
                        </a>
                    <%
                    } else {
                    %>
                        <button class="btn-esaurito" disabled>
                            ❌ Prodotto non disponibile
                        </button>
                   
                    <%
                    }
                    %>
                </div>
                
                <!-- Torna ai prodotti -->
                <a href="${pageContext.request.contextPath}/ProdottiServlet" class="btn-back">
                    ← Torna ai prodotti
                </a>
                
                <!-- Azioni Admin -->
                <%
                Object utenteSes = session.getAttribute("utente");
                if (utenteSes != null) {
                    Model.Utente utente = (Model.Utente) utenteSes;
                    if (utente.isAdmin()) {
                %>
                    <div class="admin-actions">
                        <h4>Azioni Admin</h4>
                        <div class="admin-buttons">
                            <a href="${pageContext.request.contextPath}/AggiungiModificaProdottoServlet?id=<%= prodotto.getCodiceProdotto() %>" 
                               class="btn-modifica">✏️ Modifica</a>
                             <button type="button" class="btn-elimina" 
        					onclick="openDeleteModal('<%= prodotto.getCodiceProdotto() %>', '<%= prodotto.getNome().replace("'", "\\'") %>')">
    						 Elimina
							</button>
                        </div>
                    </div>
                <%
                    }
                }
                %>
                
            </div>
        </div>
    </div>

    <%
        } else {
    %>
        <div class="errore-container">
            <h2>Prodotto non trovato</h2>
            <p>Il prodotto richiesto non esiste o è stato rimosso.</p>
            <a href="${pageContext.request.contextPath}/ProdottiServlet">Torna ai prodotti</a>
        </div>
    <%
        }
    %>
    
    <div id="deleteModal" class="modal" style="display: none;">
    <div class="modal-content">
        <div class="modal-header">
            <h3>⚠️ Conferma Eliminazione</h3>
        </div>
        <div class="modal-body">
            <p>Sei sicuro di voler eliminare questo prodotto?</p>
            <p class="modal-product-name" id="modalProductName"></p>
            <p style="color: #f44336; font-size: 0.9rem;">Questa azione è irreversibile!</p>
        </div>
        <div class="modal-footer">
            <button class="btn-cancel" onclick="closeDeleteModal()">❌ Annulla</button>
            <a id="confirmDeleteBtn" href="#" class="btn-elimina"> Elimina</a>
        </div>
    </div>
</div>

       <%@ include file ="Footer.jsp" %>

</body>
</html>