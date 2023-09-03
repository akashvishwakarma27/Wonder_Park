document.addEventListener("DOMContentLoaded", function () {
    const loginForm = document.getElementById("loginForm");
    const errorMessage = document.getElementById("errorMessage");

    loginForm.addEventListener("submit", async function (event) {
        event.preventDefault();

        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;

        try {
            const response = await fetch("http://localhost:8888/customers/signin", {
                method: "GET",
                headers: {
                    "Authorization": "Basic " + btoa(username + ":" + password)
                }
            });

            
            if (response.ok) {
                
                const customerData = await response.json(); // Parse the response as JSON
                sessionStorage.clear();
                sessionStorage.setItem("id",customerData.customerId);
               
               
                // window.location.href = "admin.html";
                
            } else {
                errorMessage.textContent = "Invalid username or password.";
            }
        } catch (error) {
            console.error("Login error:", error);
        }
    });
});