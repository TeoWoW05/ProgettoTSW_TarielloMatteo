// Aspetta che la pagina sia completamente caricata
document.addEventListener('DOMContentLoaded', function() {
    
    var currentSlide = 0;
    var slides = document.querySelectorAll('.carosello-item');
    var dots = document.querySelectorAll('.dot');
    var totalSlides = slides.length;
    
    // Se non ci sono slide, esci
    if (totalSlides === 0) return;
    
    function mostraSlide(index) {
        // Controllo limiti
        if (index >= totalSlides) index = 0;
        if (index < 0) index = totalSlides - 1;
        
        currentSlide = index;
        
        // Rimuovi active da tutte le slide
        for (var i = 0; i < slides.length; i++) {
            slides[i].classList.remove('active');
        }
        
        // Rimuovi active da tutti i dots
        for (var i = 0; i < dots.length; i++) {
            dots[i].classList.remove('active');
        }
        
        // Aggiungi active alla slide corrente
        slides[currentSlide].classList.add('active');
        
        // Aggiungi active al dot corrente
        if (dots[currentSlide]) {
            dots[currentSlide].classList.add('active');
        }
    }
    
    function cambiaSlide(delta) {
        mostraSlide(currentSlide + delta);
    }
    
    function vaiASlide(index) {
        mostraSlide(index);
    }
    
    // Rendi le funzioni globali per gli onclick
    window.cambiaSlide = cambiaSlide;
    window.vaiASlide = vaiASlide;
    
    // Auto-scorrimento ogni 4 secondi
    setInterval(function() {
        cambiaSlide(1);
    }, 4000);
    
});