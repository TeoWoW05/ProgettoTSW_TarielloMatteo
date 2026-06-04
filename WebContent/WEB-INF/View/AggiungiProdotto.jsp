<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Aggiungi Prodotto - Admin Piece B Piece</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/General.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/Aggiungi.css">
</head>
<body>

   <%@ include file ="Header.jsp" %>

    <div class="admin-container">
        
        <div class="admin-header">
            <h1>Aggiungi Nuovo Prodotto</h1>
            <a href="${pageContext.request.contextPath}/ProdottiServlet" class="btn-back">← Torna ai prodotti</a>
        </div>
        
        <form action="${pageContext.request.contextPath}/AggiungiProdottoServlet" 
              method="post" 
              enctype="multipart/form-data" 
              class="admin-form">
            
            <!-- Nome Prodotto -->
            <div class="form-group">
                <label for="nome">Nome Prodotto <span class="required">*</span></label>
                <input type="text" id="nome" name="nome" placeholder="Inserisci il nome del prodotto" required maxlength="100">
                <small class="form-help">Massimo 100 caratteri</small>
            </div>
            
            <!-- Descrizione -->
            <div class="form-group">
                <label for="descrizione">Descrizione <span class="required">*</span></label>
                <textarea id="descrizione" name="descrizione" rows="6" placeholder="Descrivi il prodotto in dettaglio..." required></textarea>
            </div>
            
            <!-- Prezzo e Quantità -->
            <div class="form-row">
                <div class="form-group form-half">
                    <label for="costo">Prezzo (€) <span class="required">*</span></label>
                    <input type="number" id="costo" name="costo" step="0.01" min="0.01" placeholder="0.00" required>
                </div>
                
                <div class="form-group form-half">
                    <label for="quantita">Quantità <span class="required">*</span></label>
                    <input type="number" id="quantita" name="quantita" min="0" value="0" required>
                </div>
            </div>
            
            <!-- Immagine -->
            <div class="form-group">
                <label for="immagine">Immagine del prodotto</label>
                <input type="file" id="immagine" name="immagine" accept="image/*">
                <small class="form-help">Formati: JPG, PNG, GIF. Max 10MB</small>
            </div>
            
            <!-- Categorie -->
            <div class="form-group">
                <label>Categorie <span class="required">*</span></label>
                <small class="form-help">Seleziona almeno una categoria</small>
                
                <div class="categorie-container">
                    <label class="checkbox-card">
                        <input type="checkbox" name="categorie" value="Abbigliamento">
                        <span>Scheda Madre</span>
                    </label>
                    
                    <label class="checkbox-card">
                        <input type="checkbox" name="categorie" value="Scarpe">
                        <span>Archiviazione</span>
                    </label>
                    
                    <label class="checkbox-card">
                        <input type="checkbox" name="categorie" value="Accessori">
                        <span>RAM</span>
                    </label>
                    
                    <label class="checkbox-card">
                        <input type="checkbox" name="categorie" value="Sport">
                        <span>Monitor</span>
                    </label>
                    
                    <label class="checkbox-card">
                        <input type="checkbox" name="categorie" value="Elettronica">
                        <span>Scheda Video</span>
                    </label>
                    
                     <label class="checkbox-card">
                        <input type="checkbox" name="categorie" value="Elettronica">
                        <span>Processore</span>
                    </label>
                    
                     <label class="checkbox-card">
                        <input type="checkbox" name="categorie" value="Elettronica">
                        <span>Dissipatore</span>
                    </label>
                    
                     <label class="checkbox-card">
                        <input type="checkbox" name="categorie" value="Elettronica">
                        <span>Ventola</span>  
                    </label>
                    
                     <label class="checkbox-card">
                        <input type="checkbox" name="categorie" value="Elettronica">
                        <span>Periferiche</span>
                    </label>
                    
                     <label class="checkbox-card">
                        <input type="checkbox" name="categorie" value="Elettronica">
                        <span>Soundbar</span>
                    </label>
                    
                     <label class="checkbox-card">
                        <input type="checkbox" name="categorie" value="Elettronica">
                        <span>Case</span>
                    </label>
                    
                     <label class="checkbox-card">
                        <input type="checkbox" name="categorie" value="Elettronica">
                        <span>Accessori</span>
                    </label>
                </div>
            </div>
            
            <!-- Pulsanti -->
            <div class="form-actions">
                <button type="submit" class="btn-primary">
                    ➕ Aggiungi Prodotto
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

   <%@ include file ="Footer.jsp" %> 

</body>
</html>