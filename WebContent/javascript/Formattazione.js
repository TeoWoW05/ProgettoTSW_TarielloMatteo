// Formatta il numero della carta con spazi ogni 4 cifre
    function formattaCarta(input) {
        // Rimuovi tutto tranne i numeri
        var valore = input.value.replace(/\D/g, '');
        
        // Aggiungi uno spazio ogni 4 cifre
        var formattato = '';
        for (var i = 0; i < valore.length; i++) {
            if (i > 0 && i % 4 === 0) {
                formattato += ' ';
            }
            formattato += valore[i];
        }
        
        input.value = formattato;
    }
    
    // Formatta la scadenza con slash (MM/AA)
	function formattaScadenza(input) {
	    var valore = input.value.replace(/\D/g, '');
	    
	    var errorePrecedente = document.getElementById('errore-scadenza');
	    if (errorePrecedente) errorePrecedente.remove();
	    
	    if (valore.length > 4) valore = valore.substring(0, 4);
	    
	    var mostraErrore = false;
	    var messaggioErrore = '';
	    
	    if (valore.length >= 2) {
	        var mese = parseInt(valore.substring(0, 2));
	        
	        if (mese > 12) {
	            messaggioErrore = '❌ Mese non valido! (01-12)';
	            mostraErrore = true;
	        } else if (mese === 0) {
	            messaggioErrore = '❌ Il mese non può essere 00!';
	            mostraErrore = true;
	        }
	        
	        // Controllo data scaduta (solo se ha 4 cifre)
	        if (valore.length === 4) {
	            var anno = parseInt('20' + valore.substring(2));
	            var oggi = new Date();
	            var annoCorrente = oggi.getFullYear();
	            var meseCorrente = oggi.getMonth() + 1;
	            
	            if (anno < annoCorrente || (anno === annoCorrente && mese < meseCorrente)) {
	                messaggioErrore = '⚠️ Questa carta è scaduta!';
	                mostraErrore = true;
	            }
	        }
	    }
	    
	    if (valore.length >= 2) {
	        valore = valore.substring(0, 2) + '/' + valore.substring(2);
	    }
	    
	    input.value = valore;
	    
	    if (mostraErrore) {
	        var errore = document.createElement('span');
	        errore.id = 'errore-scadenza';
	        errore.style.cssText = 'color: #f44336; font-size: 0.8rem; display: block; margin-top: 5px;';
	        errore.textContent = messaggioErrore;
	        input.parentNode.appendChild(errore);
	        input.style.borderColor = '#f44336';
	        
	        setTimeout(function() {
	            var err = document.getElementById('errore-scadenza');
	            if (err) err.remove();
	            input.style.borderColor = '';
	        }, 4000);
	    } else {
	        input.style.borderColor = '#4CAF50';  // Bordo verde se valido
	    }
	}