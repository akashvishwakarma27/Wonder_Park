const carousel = document.querySelector(".carousel"),
    arrowIcons = document.querySelectorAll(".wrapper i"),
    firstImgWidth = carousel.querySelector("img").clientWidth + 14; 

let isDragging = false,
    startX,
    scrollLeft;

// showing and hiding prev/next icon according to carousel scroll left value
const showHideIcons = () => {
    let scrollWidth = carousel.scrollWidth - carousel.clientWidth;
    arrowIcons[0].style.display = carousel.scrollLeft == 0 ? "none" : "block";
    arrowIcons[1].style.display = carousel.scrollLeft == scrollWidth ? "none" : "block";
}

// scrolling images/carousel to left according to mouse pointer
const dragging = (e) => {
    if (!isDragging) return;
    e.preventDefault();
    const x = e.pageX || e.touches[0].pageX;
    const diff = startX - x;
    carousel.scrollLeft = scrollLeft + diff;
    showHideIcons();
}

carousel.addEventListener("mousedown", (e) => {
    isDragging = true;
    startX = e.pageX;
    scrollLeft = carousel.scrollLeft;
    carousel.classList.add("dragging");
});

carousel.addEventListener("touchstart", (e) => {
    isDragging = true;
    startX = e.touches[0].pageX;
    scrollLeft = carousel.scrollLeft;
    carousel.classList.add("dragging");
});

document.addEventListener("mouseup", () => {
    isDragging = false;
    carousel.classList.remove("dragging");
});

document.addEventListener("touchend", () => {
    isDragging = false;
    carousel.classList.remove("dragging");
});

carousel.addEventListener("mousemove", dragging);
carousel.addEventListener("touchmove", dragging);

arrowIcons.forEach(icon => {
    icon.addEventListener("click", () => {
        carousel.scrollLeft += icon.id == "left" ? -firstImgWidth : firstImgWidth;
        setTimeout(() => showHideIcons(), 60);
    });
});




// ToptobottomPart

var backToTopBtn = document.querySelector("#back-to-topbtn");

window.addEventListener("scroll", function () {
    if (window.pageYOffset > 100) {
        backToTopBtn.style.display = "block";
    } else {
        backToTopBtn.style.display = "none";
    }
});

backToTopBtn.addEventListener("click", function () {
    window.scrollTo({
        top: 0,
        behavior: "smooth"
    });
});


// TravelDetailsRedirection

let travelDetailsRedirection = document.getElementsByClassName("TravelDetailsRedirection");
for (let i = 0; i < travelDetailsRedirection.length; i++) {
    travelDetailsRedirection[i].addEventListener("click", () => {
        location.href = "Html/TravelDetails.html";
    });
}

// login_SignUpRedirection

let login_SignUpRedirection = document.getElementById("login_SignUpRedirection");
login_SignUpRedirection.addEventListener("click", () => {
    location.href = "Html/login_SignUp.html";
})
