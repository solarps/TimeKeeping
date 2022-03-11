const btn = document.querySelector('.btn');
const modal_overlay = document.querySelector('.modal-overlay');
const close = document.querySelector('.close');

btn.addEventListener('click', () => {
    document.querySelector('.modal-content').classList.add('modal--visible');
    modal_overlay.classList.add('modal-overlay--visible');
})

modal_overlay.addEventListener('click', (e) => {
    if (e.target == modal_overlay || e.target == close) {
        modal_overlay.classList.remove('modal-overlay--visible');
    }
})


