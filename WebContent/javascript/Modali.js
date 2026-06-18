function openRemoveModal(button) {
    // Prende i dati dal pulsante
    var id = button.getAttribute('data-id');
    var nome = button.getAttribute('data-nome');
    
    // Mostra il nome nel modale
    document.getElementById('modalRemoveProductName').textContent = nome;
    
    // Imposta il link di conferma
	document.getElementById('confirmRemoveBtn').href = 
	    contextPath + '/CarrelloServlet?action=rimuovi&id=' + id;
    
    // Mostra il modale
    document.getElementById('removeModal').style.display = 'flex';
}

function closeRemoveModal() {
    document.getElementById('removeModal').style.display = 'none';
}
       
       // MODALE SVUOTAMENTO CARRELLO
       function openClearModal() {
           document.getElementById('clearModal').style.display = 'flex';
       }
       
       function closeClearModal() {
           document.getElementById('clearModal').style.display = 'none';
       }
       
       // Chiudi i modali cliccando fuori
       window.onclick = function(event) {
           let removeModal = document.getElementById('removeModal');
           let clearModal = document.getElementById('clearModal');
           
           if (event.target == removeModal) {
               closeRemoveModal();
           }
           if (event.target == clearModal) {
               closeClearModal();
           }
       }