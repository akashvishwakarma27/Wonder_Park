document.addEventListener("DOMContentLoaded", function () {
    const loginForm = document.getElementById("loginForm");
    const errorMessage = document.getElementById("errorMessage");

    loginForm.addEventListener("submit", async function (event) {
        event.preventDefault();

        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;

        try {
            const response = await fetch("http://localhost:8888/admin/signin", {
                method: "GET",
                headers: {
                    "Authorization": "Basic " + btoa(username + ":" + password)
                }
            });

            
            if (response.ok) {

               console.log(response);
               
                window.location.href = "admin.html";
                
            } else {
                errorMessage.textContent = "Invalid username or password.";
            }
        } catch (error) {
            console.error("Login error:", error);
        }
    });
});