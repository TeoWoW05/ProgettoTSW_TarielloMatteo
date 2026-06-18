<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.List, java.util.ArrayList, java.util.Map, Model.Prodotto, Model.Utente, Model.Categoria" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Piece-B-Piece - Prodotti</title>
 <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/Prodotti.css">
 <link rel="stylesheet" type="text/css"  href="${pageContext.request.contextPath}/styles/General.css">
     <script src="${pageContext.request.contextPath}/javascript/RegLog.js"></script>
</head>
<body>
<%@ include file ="Header.jsp" %>

<div class="page-header">
<h1>
<% String categoriaSelezionata = (String) request.getAttribute("categoriaSelezionata");
   String termineRicerca = (String) request.getAttribute("termineRicerca");
   
   if(categoriaSelezionata != null && !categoriaSelezionata.isEmpty()){
	   out.print(categoriaSelezionata);
   }else if(termineRicerca != null && !termineRicerca.isEmpty()){
	   out.print("Risultati per : "+ termineRicerca);
   }else{
	   out.print("Tutti i Prodotti");
   }
%>
</h1>

<%
	Utente utente = (Utente)session.getAttribute("utente");
	boolean isAdmin = (utente != null && utente.isAdmin());
	
	if(isAdmin){
%>

	<a href="${pageContext.request.contextPath}/AggiungiModificaProdottoServlet" class="btn-aggiungi">
	+ Aggiungi Prodotto
	</a>
	<% } %>

</div>

  <!-- Filtri e ricerca -->
        <div class="filtri">
            <form action="${pageContext.request.contextPath}/ProdottiServlet" method="GET" style="display: flex; gap: 15px; flex-wrap: wrap; width: 100%;">
                <select name="categoria" onchange="this.form.submit()">
                    <option value="">Tutte le categorie</option>
                    <%
                    ArrayList<Categoria> categorie = (ArrayList<Categoria>) request.getAttribute("categorie");
                    if (categorie != null) {
                        for (Categoria cat : categorie) {
                            String selected = (categoriaSelezionata != null && cat.getNome().equals(categoriaSelezionata)) ? "selected" : "";
                    %>
                        <option value="<%= cat.getNome() %>" <%= selected %>><%= cat.getNome() %></option>
                    <%
                        }
                    }
                    %>
                </select>
                
                <input type="text" name="cerca" placeholder="Cerca prodotto..." value="<%= termineRicerca != null ? termineRicerca : "" %>">
                <button type="submit">Cerca</button>
                
                <%
                if (categoriaSelezionata != null || termineRicerca != null) {
                %>
                    <a href="${pageContext.request.contextPath}/ProdottiServlet" class="btn-dettaglio" style="display: flex; align-items: center;">
                        Mostra tutti
                    </a>
                <%
                }
                %>
            </form>
        </div>

        <!-- Griglia prodotti -->
        <div class="prodotti-grid">
            <%
            ArrayList<Prodotto> prodotti = (ArrayList<Prodotto>) request.getAttribute("prodotti");
            
            if (prodotti != null && !prodotti.isEmpty()) {
                // Recupera la mappa delle categorie
                Map<Integer, ArrayList<String>> mappaCategorie = (Map<Integer, ArrayList<String>>) request.getAttribute("mappaCategorie");
                
                for (Prodotto p : prodotti) {
            %>
                <div class="prodotto-card">
                    
                    <!-- Immagine -->
                    <%
                    if (p.getImmagine() != null && !p.getImmagine().isEmpty()) {
                    %>
                        <img src="${pageContext.request.contextPath}/immagini/prodotti/<%= p.getImmagine() %>" 
                             alt="<%= p.getNome() %>" 
                             class="prodotto-immagine">
                    <%
                    } else {
                    %>
                        <div class="prodotto-no-img">Nessuna immagine</div>
                    <%
                    }
                    %>
                    
                    <!-- Nome -->
                    <h3 class="prodotto-nome"><%= p.getNome() %></h3>
                    
                    <!-- Categorie -->
                    <%
                    if (mappaCategorie != null) {
                        ArrayList<String> cats = mappaCategorie.get(p.getCodiceProdotto());
                        if (cats != null && !cats.isEmpty()) {
                    %>
                        <div class="prodotto-categorie">
                            <%
                            for (String cat : cats) {
                            %>
                                <span class="categoria-tag"><%= cat %></span>
                            <%
                            }
                            %>
                        </div>
                    <%
                        }
                    }
                    %>
                    
                    <!-- Descrizione -->
                    <p class="prodotto-descrizione">
                        <%= p.getDescrizione() != null && p.getDescrizione().length() > 100 
                            ? p.getDescrizione().substring(0, 100) + "..." 
                            : p.getDescrizione() %>
                    </p>
                    
                    <!-- Prezzo -->
                    <p class="prodotto-prezzo">€ <%= String.format("%.2f", p.getCosto()) %></p>
                    
                    <!-- Disponibilità -->
                    <p class="prodotto-disponibilita">
                        <%
                        if (p.getQuantitaMagazzino() > 0) {
                        %>
                            <span class="disponibile">Disponibile (<%= p.getQuantitaMagazzino() %> pezzi)</span>
                        <%
                        } else {
                        %>
                            <span class="esaurito">Esaurito</span>
                        <%
                        }
                        %>
                    </p>
                    
                    <!-- Azioni: SEMPRE visibili per tutti -->
                    <div class="prodotto-azioni">
                        <a href="${pageContext.request.contextPath}/DettagliServlet?id=<%= p.getCodiceProdotto() %>" 
                           class="btn-dettaglio">Dettagli</a>
                        
                        <%
                        if (p.getQuantitaMagazzino() > 0) {
                            // Il carrello è accessibile a tutti (anche non loggati)
                        %>
                            <a href="${pageContext.request.contextPath}/carrello?action=add&id=<%= p.getCodiceProdotto() %>" 
                               class="btn-carrello">Aggiungi al carrello</a>
                        <%
                        } else {
                        %>
                            <span class="btn-carrello disabled">Non disponibile</span>
                        <%
                        }
                        %>
                    </div>
                    
                    <!-- Azioni Admin: visibili SOLO se admin -->
                    <%
                    if (isAdmin) {
                    %>
                        <div class="admin-azioni">
                            <a href="${pageContext.request.contextPath}/AggiungiModificaProdottoServlet?id=<%= p.getCodiceProdotto() %>" 
                               class="btn-modifica">Modifica</a>
                            
                            
                            <button type="button" class="btn-elimina" 
        					onclick="openDeleteModal('<%= p.getCodiceProdotto() %>', '<%= p.getNome().replace("'", "\\'") %>')">
    						 Elimina
							</button>
                        </div>
                    <%
                    }
                    %>
                    
                </div>
            <%
                }
            } else {
            %>
                <div class="messaggio-vuoto">
                    <p>Nessun prodotto trovato.</p>
                </div>
            <%
            }
            %>
        </div>
        
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