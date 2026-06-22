function aggiungiAlCarrello(idProdotto, quantita, bottone) {
    var url = contextPath + '/CarrelloServlet?action=aggiungi&id=' + idProdotto + '&qty=' + quantita + '&ajax=true';
    
    // Cambia il testo del pulsante
    var testoOriginale = bottone.innerHTML;
    bottone.innerHTML = '⏳...';
    bottone.disabled = true;
    
    var xhr = new XMLHttpRequest();
    xhr.open('GET', url, true);
    
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var risposta = JSON.parse(xhr.responseText);
            
            if (risposta.successo) {
              	
				aggiornaContatoreCarrello(risposta.numeroProdotti);
                
                // Mostra notifica
                mostraNotifica('✅ ' + risposta.messaggio, 'successo');
                
                // Ripristina pulsante
                bottone.innerHTML = '✅ Aggiunto!';
                setTimeout(function() {
                    bottone.innerHTML = testoOriginale;
                    bottone.disabled = false;
                }, 1500);
            } else {
                mostraNotifica('❌ ' + risposta.messaggio, 'errore');
                bottone.innerHTML = testoOriginale;
                bottone.disabled = false;
            }
        }
    };
    
    xhr.onerror = function() {
        mostraNotifica('❌ Errore di connessione', 'errore');
        bottone.innerHTML = testoOriginale;
        bottone.disabled = false;
    };
    
    xhr.send();
}

function aggiornaContatoreCarrello(numero) {
    var cartCount = document.getElementById('cart-count');
    if (cartCount) {
        cartCount.textContent = numero;
        if (numero > 0) {
            cartCount.style.display = 'flex';
        } else {
            cartCount.style.display = 'none';
        }
        
        // Effetto animazione
        cartCount.classList.add('cart-count-bounce');
        setTimeout(function() {
            cartCount.classList.remove('cart-count-bounce');
        }, 300);
    }

function mostraNotifica(messaggio, tipo) {
    var notifica = document.createElement('div');
    notifica.textContent = messaggio;
    notifica.style.cssText = 
        'position:fixed; top:20px; right:20px; padding:15px 25px; ' +
        'border-radius:5px; color:#fff; font-weight:bold; z-index:9999; ' +
        'animation:slideIn 0.3s ease; ' +
        'background:' + (tipo === 'successo' ? '#4CAF50' : '#f44336') + ';';
    
    document.body.appendChild(notifica);
    
    setTimeout(function() {
        notifica.style.animation = 'slideOut 0.3s ease';
        setTimeout(function() { notifica.remove(); }, 300);
    }, 3000);
}
}