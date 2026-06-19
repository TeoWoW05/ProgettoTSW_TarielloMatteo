<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Footer</title>
<link rel="stylesheet" type="text/css"  href="${pageContext.request.contextPath}/styles/Footer.css">
</head>
<body>

<footer class="site-footer">
    <div class="footer-content">
        <div class="footer-section">
            <h3>Piece-B-Piece</h3>
            <p>Hardware affidabile per il tuo Personal Computer</p>
        </div>
        
        <div class="footer-section">
            <h4>Link Utili</h4>
            <ul>
                <li><a href="${pageContext.request.contextPath}/HPServlet">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/ProdottiServlet">Prodotti</a></li>
                <li><a href="${pageContext.request.contextPath}/CarrelloServlet">Carrello</a></li>
            </ul>
        </div>
        
        <div class="footer-section">
            <h4>Seguici</h4>
            <div class="social-icons">
                <a href="https://www.instagram.com/tariello_matteo/" class="social-icon" aria-label="Instagram"><img src="${pageContext.request.contextPath}/immagini/Instagram.png" alt="Instagram"></a>
                <a href="https://www.facebook.com/people/Matteo-Tariello/pfbid0283TFeTLqD1SHLMYyFrU352gYaFPKJeuUsrWyHQ7PY4GfijVgSWRwWGWpbSNKLnKfl/" class="social-icon" aria-label="Facebook"><img src="${pageContext.request.contextPath}/immagini/Facebook.png" alt="Facebook"></a>
                <a href="https://x.com/teoWoah" class="social-icon" aria-label="Twitter"><img src="${pageContext.request.contextPath}/immagini/twitter.png" alt="X"></a>
                <a href="https://www.tiktok.com/@tariellomatteo" class="social-icon" aria-label="TikTok"><img src="${pageContext.request.contextPath}/immagini/TikTok.png" alt="Tiktok"></a>
            </div>
        </div>
    </div>
    
    <div class="footer-bottom">
        <p>&copy; 2026 Piece-B-Piece. Tutti i diritti riservati.</p>
    </div>
</footer>
</body>
</html>