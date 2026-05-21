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