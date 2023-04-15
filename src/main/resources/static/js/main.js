'use strict';

const signInButtonNav = document.querySelector('#sign-in-button-nav-bar'),
		formContainer = document.querySelector('.sign-in-container');

signInButtonNav.addEventListener('click', () => {
	formContainer.classList.toggle('hidden');
});