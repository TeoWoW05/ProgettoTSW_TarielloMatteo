<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Model.Prodotto, java.util.*, Model.Categoria" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <%
        // Controlla se è modifica o aggiunta
        Boolean modifica = (Boolean) request.getAttribute("modifica");
        Prodotto prodotto = (Prodotto) request.getAttribute("prodotto");
        ArrayList<String> categorieProdotto = (ArrayList<String>) request.getAttribute("categorieProdotto");
        String titolo = (modifica != null && modifica) ? "Modifica Prodotto" : "Aggiungi Nuovo Prodotto";
        String actionUrl = request.getContextPath() + "/AggiungiModificaProdottoServlet";
    %>
    <title><%= titolo %> - Admin Piece B Piece</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/General.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/Aggiungi.css">
</head>
<body>

    <%@ include file="Header.jsp" %>

    <div class="admin-container">
        
        <div class="admin-header">
            <h1><%= titolo %></h1>
            <a href="${pageContext.request.contextPath}/ProdottiServlet" class="btn-back">← Torna ai prodotti</a>
        </div>
        
        <form action="<%= actionUrl %>" 
              method="post" 
              enctype="multipart/form-data" 
              class="admin-form">
            
            <!-- ID nascosto per la modifica -->
            <% if (modifica != null && modifica && prodotto != null) { %>
                <input type="hidden" name="id" value="<%= prodotto.getCodiceProdotto() %>">
            <% } %>
            
            <!-- Nome Prodotto -->
            <div class="form-group">
                <label for="nome">Nome Prodotto <span class="required">*</span></label>
                <input type="text" id="nome" name="nome" 
                       value="<%= (modifica != null && modifica && prodotto != null) ? prodotto.getNome() : "" %>"
                       placeholder="Inserisci il nome del prodotto" required maxlength="100">
                <small class="form-help">Massimo 100 caratteri</small>
            </div>
            
            <!-- Descrizione -->
            <div class="form-group">
                <label for="descrizione">Descrizione <span class="required">*</span></label>
                <textarea id="descrizione" name="descrizione" rows="6" 
                          placeholder="Descrivi il prodotto in dettaglio..." required><%= (modifica != null && modifica && prodotto != null) ? prodotto.getDescrizione() : "" %></textarea>
            </div>
            
            <!-- Prezzo e Quantità -->
            <div class="form-row">
                <div class="form-group form-half">
                    <label for="costo">Prezzo (€) <span class="required">*</span></label>
                    <input type="number" id="costo" name="costo" step="0.01" min="0.01" 
                           value="<%= (modifica != null && modifica && prodotto != null) ? prodotto.getCosto() : "" %>"
                           placeholder="0.00" required>
                </div>
                
                <div class="form-group form-half">
                    <label for="quantita">Quantità <span class="required">*</span></label>
                    <input type="number" id="quantita" name="quantita" min="0" 
                           value="<%= (modifica != null && modifica && prodotto != null) ? prodotto.getQuantitaMagazzino() : "0" %>" required>
                </div>
            </div>
            
            <!-- Immagine attuale (solo in modifica) -->
            <% if (modifica != null && modifica && prodotto != null && 
                   prodotto.getImmagine() != null && !prodotto.getImmagine().isEmpty()) { %>
                <div class="form-group">
                    <label>Immagine attuale</label>
                    <img src="${pageContext.request.contextPath}/immagini/prodotti/<%= prodotto.getImmagine() %>" 
                         alt="Immagine prodotto" style="max-width: 200px; border-radius: 5px; border: 1px solid rgba(255,255,255,0.2);">
                    <p style="color: #888; font-size: 0.85rem;"><%= prodotto.getImmagine() %></p>
                </div>
            <% } %>
            
            <!-- Upload immagine -->
            <div class="form-group">
                <label for="immagine">
                    <%= (modifica != null && modifica) ? "Nuova immagine (lascia vuoto per mantenere quella attuale)" : "Immagine del prodotto" %>
                </label>
                <input type="file" id="immagine" name="immagine" accept="image/*">
                <small class="form-help">Formati: JPG, PNG, GIF. Max 10MB</small>
            </div>
            
            <!-- Categorie -->
            <div class="form-group">
                <label>Categorie <span class="required">*</span></label>
                <small class="form-help">Seleziona almeno una categoria</small>
                
                <div class="categorie-container">
                    <label class="checkbox-card">
                        <input type="checkbox" name="categorie" value="Scheda Madre"
                               <%= (modifica != null && modifica && categorieProdotto != null && categorieProdotto.contains("Scheda Madre")) ? "checked" : "" %>>
                        <span>Scheda Madre</span>
                    </label>
                    
                    <label class="checkbox-card">
                        <input type="checkbox" name="categorie" value="Archiviazione"
                               <%= (modifica != null && modifica && categorieProdotto != null && categorieProdotto.contains("Archiviazione")) ? "checked" : "" %>>
                        <span>Archiviazione</span>
                    </label>
                    
                    <label class="checkbox-card">
                        <input type="checkbox" name="categorie" value="RAM"
                               <%= (modifica != null && modifica && categorieProdotto != null && categorieProdotto.contains("RAM")) ? "checked" : "" %>>
                        <span>RAM</span>
                    </label>
                    
                    <label class="checkbox-card">
                        <input type="checkbox" name="categorie" value="Monitor"
                               <%= (modifica != null && modifica && categorieProdotto != null && categorieProdotto.contains("Monitor")) ? "checked" : "" %>>
                        <span>Monitor</span>
                    </label>
                    
                    <label class="checkbox-card">
                        <input type="checkbox" name="categorie" value="Scheda Video"
                               <%= (modifica != null && modifica && categorieProdotto != null && categorieProdotto.contains("Scheda Video")) ? "checked" : "" %>>
                        <span>Scheda Video</span>
                    </label>
                    
                    <label class="checkbox-card">
                        <input type="checkbox" name="categorie" value="Processore"
                               <%= (modifica != null && modifica && categorieProdotto != null && categorieProdotto.contains("Processore")) ? "checked" : "" %>>
                        <span>Processore</span>
                    </label>
                    
                    <label class="checkbox-card">
                        <input type="checkbox" name="categorie" value="Dissipatore"
                               <%= (modifica != null && modifica && categorieProdotto != null && categorieProdotto.contains("Dissipatore")) ? "checked" : "" %>>
                        <span>Dissipatore</span>
                    </label>
                    
                    <label class="checkbox-card">
                        <input type="checkbox" name="categorie" value="Ventola"
                               <%= (modifica != null && modifica && categorieProdotto != null && categorieProdotto.contains("Ventola")) ? "checked" : "" %>>
                        <span>Ventola</span>  
                    </label>
                    
                    <label class="checkbox-card">
                        <input type="checkbox" name="categorie" value="Periferiche"
                               <%= (modifica != null && modifica && categorieProdotto != null && categorieProdotto.contains("Periferiche")) ? "checked" : "" %>>
                        <span>Periferiche</span>
                    </label>
                    
                    <label class="checkbox-card">
                        <input type="checkbox" name="categorie" value="Soundbar"
                               <%= (modifica != null && modifica && categorieProdotto != null && categorieProdotto.contains("Soundbar")) ? "checked" : "" %>>
                        <span>Soundbar</span>
                    </label>
                    
                    <label class="checkbox-card">
                        <input type="checkbox" name="categorie" value="Case"
                               <%= (modifica != null && modifica && categorieProdotto != null && categorieProdotto.contains("Case")) ? "checked" : "" %>>
                        <span>Case</span>
                    </label>
                    
                    <label class="checkbox-card">
                        <input type="checkbox" name="categorie" value="Accessori"
                               <%= (modifica != null && modifica && categorieProdotto != null && categorieProdotto.contains("Accessori")) ? "checked" : "" %>>
                        <span>Accessori</span>
                    </label>
                </div>
            </div>
            
            <!-- Pulsanti -->
            <div class="form-actions">
                <button type="submit" class="btn-primary">
                    <%= (modifica != null && modifica) ? "💾 Salva Modifiche" : "➕ Aggiungi Prodotto" %>
                </button>
                
                <button type="reset" class="btn-secondary">
                    🔄 Reset
                </button>
                
                <a href="${pageContext.request.contextPath}/ProdottiServlet" class="btn-cancel">
                    ❌ Annulla
                </a>
            </div>
            
        </form>
    </div>

    <%@ include file="Footer.jsp" %>

</body>
</html>