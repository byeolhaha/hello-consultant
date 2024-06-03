document.getElementById("openModal").addEventListener("click", function() {
    document.getElementById("myModal").style.display = "block";
});

document.getElementById("newCustomer").addEventListener("click", function() {
    window.location.href = "/templates/survey.html";
});

document.getElementById("existingCustomer").addEventListener("click", function() {
    userId = getUserIdFromCookie();
    if (userId === 0) {
         alert("This is your first visit. Please click on the 'No first visit✅' button");
    } else {
        window.location.href = "/templates/client-chatrooms.html";
    }
});

document.getElementsByClassName("close")[0].addEventListener("click", function() {
    document.getElementById("myModal").style.display = "none";
});

window.addEventListener("click", function(event) {
    if (event.target == document.getElementById("myModal")) {
        document.getElementById("myModal").style.display = "none";
    }
});

function getUserIdFromCookie() {
    const cookieString = document.cookie;
    const cookies = cookieString.split(';');

    for (let cookie of cookies) {
        const [cookieName, cookieValue] = cookie.split('=');
        if (cookieName.trim() === 'userId') {
            return parseInt(cookieValue) || 0;
        }
    }

    return 0;
}