var currentSlide = 0;
        var totalSlides = document.querySelectorAll('.carosello-item').length;
        
        function mostraSlide(index) {
            var slides = document.querySelectorAll('.carosello-item');
            var dots = document.querySelectorAll('.dot');
            
            if (index >= totalSlides) index = 0;
            if (index < 0) index = totalSlides - 1;
            
            currentSlide = index;
            
            slides.forEach(function(slide, i) {
                slide.classList.remove('active');
                dots[i].classList.remove('active');
            });
            
            slides[currentSlide].classList.add('active');
            dots[currentSlide].classList.add('active');
        }
        
        function cambiaSlide(delta) {
            mostraSlide(currentSlide + delta);
        }
        
        function vaiASlide(index) {
            mostraSlide(index);
        }
        
        // Auto-scorrimento ogni 4 secondi
        setInterval(function() {
            cambiaSlide(1);
        }, 4000);