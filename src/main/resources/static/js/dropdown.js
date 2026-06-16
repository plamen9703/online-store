document.addEventListener("DOMContentLoaded", function () {

    const button = document.getElementById("userMenuButton");
    const menu = document.getElementById("userDropdown");

    if (!button || !menu) return;

    button.addEventListener("click", function (e) {
        e.stopPropagation();
        menu.classList.toggle("show");
    });

    document.addEventListener("click", function () {
        menu.classList.remove("show");
    });
});