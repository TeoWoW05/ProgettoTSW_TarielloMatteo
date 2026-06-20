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
        // Rimuovi tutto tranne i numeri
        var valore = input.value.replace(/\D/g, '');
        
        // Aggiungi lo slash dopo i primi 2 numeri
        if (valore.length >= 2) {
            valore = valore.substring(0, 2) + '/' + valore.substring(2, 4);
        }
        
        input.value = valore;
    }