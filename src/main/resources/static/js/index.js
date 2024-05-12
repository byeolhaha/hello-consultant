document.getElementById("openModal").addEventListener("click", function() {
    document.getElementById("myModal").style.display = "block";
});

document.getElementById("newCustomer").addEventListener("click", function() {
    window.location.href = "/templates/survey.html";
});

document.getElementById("existingCustomer").addEventListener("click", function() {
    window.location.href = "/templates/client-chatrooms.html";
});

document.getElementsByClassName("close")[0].addEventListener("click", function() {
    document.getElementById("myModal").style.display = "none";
});

window.addEventListener("click", function(event) {
    if (event.target == document.getElementById("myModal")) {
        document.getElementById("myModal").style.display = "none";
    }
});