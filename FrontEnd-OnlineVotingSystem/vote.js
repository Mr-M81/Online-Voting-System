// Hamburger Menu Toggle
const hamburger = document.querySelector('.hamburger');
const nav = document.querySelector('nav');
const navLinks = document.querySelectorAll('nav ul li a');

// Toggle menu on hamburger click
hamburger.addEventListener('click', () => {
    nav.classList.toggle('active');
    hamburger.classList.toggle('active');
});

// Close menu when a navigation link is clicked
navLinks.forEach(link => {
    link.addEventListener('click', () => {
        nav.classList.remove('active');
        hamburger.classList.remove('active');
    });
});


        // FAQ Toggles
        const faqQuestions = document.querySelectorAll('.faq-question');
        faqQuestions.forEach(question => {
            question.addEventListener('click', () => {
                const answer = question.nextElementSibling;
                answer.classList.toggle('active');
                const icon = question.querySelector('i');
                icon.classList.toggle('fa-chevron-up');
                icon.classList.toggle('fa-chevron-down');
            });
        });

        // Voting Form Submission
        document.getElementById('voteForm').addEventListener('submit', function(e) {
            e.preventDefault();
            
            const voterId = document.getElementById('voterId').value;
            const nationalId = document.getElementById('nationalId').value;
            const constituency = document.getElementById('constituency').value;
            const candidate = document.getElementById('candidates').value;

            if (!voterId || !nationalId || !constituency || !candidate) {
                alert('Please fill in all required fields.');
                return;
            }

            // Simulated voting process
            alert('Verifying voter credentials...');
            setTimeout(() => {
                alert('Vote successfully recorded! Thank you for participating in the democratic process.');
                this.reset();
            }, 1500);
        });

        // Contact Form Submission
        document.getElementById('contactForm').addEventListener('submit', function(e) {
            e.preventDefault();
            alert('Thank you for your message. Our support team will respond within 24 hours.');
            this.reset();
        });
