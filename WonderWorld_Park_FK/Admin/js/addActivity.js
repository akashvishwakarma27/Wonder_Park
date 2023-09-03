


document.addEventListener("DOMContentLoaded", function () {
    const addActivityForm = document.getElementById("addActivityForm");
    addActivityForm.addEventListener("submit", function (event) {
        event.preventDefault();

        const activityName = document.getElementById("activityName").value;
        const personCapacity = parseInt(document.getElementById("personCapacity").value);
        const distance = parseInt(document.getElementById("distance").value);
        const price = parseFloat(document.getElementById("price").value);
        const url = document.getElementById("url").value;

        const newActivity = {
            activityName: activityName,
            personCapacity: personCapacity,
            distance: distance,
            price: price,
            url: url
        };

        addActivity(newActivity);
    });
});

function addActivity(newActivity) {
    
    const jwtToken = getCookie();
    
    fetch(`http://localhost:8888/admin/activity/add`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            "Authorization": `Bearer ${jwtToken}` // Replace jwtToken with your actual JWT token
        },
        body: JSON.stringify(newActivity)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        console.log("Activity added successfully.");
        window.location.href = "admin.html";
        
    })
    .catch(error => {
        console.error("Add error:", error);
    });
}
function getCookie() {
    const value =  document.cookie;
    
    const parts = value.split("=");
    
    return parts[1];
}