/**
 * 
 */
function regPass() {
  let x = document.getElementsByClassName("pass");
  for(let i = 0; i<2 ; i++){
  if (x[i].type === "password") {
    x[i].type = "text";
  } else {
    x[i].type = "password";
   }
  }
}

function logPass() {
  let x = document.getElementById("mostraPass");
  if (x.type === "password") {
    x.type = "text";
  } else {
    x.type = "password";
   }
  }
  
  function changeQty(delta) {
              let input = document.getElementById('qty');
              let current = parseInt(input.value);
              let newQty = current + delta;
              
              if (newQty >= 1 && newQty <= parseInt(input.max)) {
                  input.value = newQty;
                  
                  // Aggiorna link carrello e acquista
                  var id = '<%= prodotto.getCodiceProdotto() %>';
                  document.getElementById('btn-carrello').href = 
                      '${pageContext.request.contextPath}/carrello?action=add&id=' + id + '&qty=' + newQty;
                  document.getElementById('btn-acquista').href = 
                      '${pageContext.request.contextPath}/checkout?action=buynow&id=' + id + '&qty=' + newQty;
              }
          }
		  
		  function openDeleteModal(id, nome) {
		          document.getElementById('deleteModal').style.display = 'flex';
		          document.getElementById('modalProductName').textContent = nome;
		          document.getElementById('confirmDeleteBtn').href = 
		              '${pageContext.request.contextPath}/EliminaProdServlet?id=' + id;
		      }
		      
		      function closeDeleteModal() {
		          document.getElementById('deleteModal').style.display = 'none';
		      }
		      
		      // Chiudi il modale cliccando fuori
		      window.onclick = function(event) {
		          var modal = document.getElementById('deleteModal');
		          if (event.target == modal) {
		              closeDeleteModal();
		          }
		      }