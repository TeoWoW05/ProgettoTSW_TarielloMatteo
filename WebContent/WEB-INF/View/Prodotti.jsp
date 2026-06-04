<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.List, java.util.ArrayList, java.util.Map, Model.Prodotto, Model.Utente, Model.Categoria" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Piece-B-Piece - Prodotti</title>
 <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/Prodotti.css">
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

	<a href="${pageContext.request.contextPath}/admin/aggiungiProdotto" class="btn-aggiungi">
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
                    List<Categoria> categorie = (List<Categoria>) request.getAttribute("categorie");
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
            List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("prodotti");
            
            if (prodotti != null && !prodotti.isEmpty()) {
                // Recupera la mappa delle categorie
                Map<Integer, List<String>> mappaCategorie = (Map<Integer, List<String>>) request.getAttribute("mappaCategorie");
                
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
                        List<String> cats = mappaCategorie.get(p.getCodiceProdotto());
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
                        <a href="${pageContext.request.contextPath}/prodotto?id=<%= p.getCodiceProdotto() %>" 
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
                            <a href="${pageContext.request.contextPath}/admin/modificaProdotto?id=<%= p.getCodiceProdotto() %>" 
                               class="btn-modifica">Modifica</a>
                            
                            <a href="${pageContext.request.contextPath}/admin/aggiungiQuantita?id=<%= p.getCodiceProdotto() %>" 
                               class="btn-aggiungi-quantita">+ Qtà</a>
                            
                            <a href="${pageContext.request.contextPath}/admin/eliminaProdotto?id=<%= p.getCodiceProdotto() %>" 
                               class="btn-elimina" 
                               onclick="return confirm('Sei sicuro di voler eliminare questo prodotto?')">Elimina</a>
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
                    <%
                    if (isAdmin) {
                    %>
                        <p><a href="${pageContext.request.contextPath}/admin/aggiungiProdotto">Aggiungi il primo prodotto</a></p>
                    <%
                    }
                    %>
                </div>
            <%
            }
            %>
        </div>
        
    </div>



<%@ include file ="Footer.jsp" %>
</body>
</html>