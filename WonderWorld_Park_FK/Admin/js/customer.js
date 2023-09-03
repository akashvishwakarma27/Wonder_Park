let mainHtml = document.getElementById("body");
let btn= document.getElementById("logout");
function fetchCustomerData(){
    const jwtToken = getCookie();
    const apiUrl = 'http://localhost:8888/admin/customers';
    
    fetch(apiUrl, {
        headers: {
            "Authorization": `Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJBa2FzaCIsInN1YiI6IkpXVCBUb2tlbiIsInVzZXJuYW1lIjoic3ViQGdtYWlsLmNvbSIsImF1dGhvcml0aWVzIjoiUk9MRV9BRE1JTiIsImlhdCI6MTY5MzE5ODgzNSwiZXhwIjoxNjkzMjI4ODM1fQ.lfzC7qvR0kyjTlOx6tIuTZvc4897s0Twt8CujET18e6XP0I-EP5qMbe18k-OIfMtxhh07ihy9MmDS1gnR6aJyw`
        }
    })
    .then(res => {
        if(!res.ok){
            throw new Error(`Http error! Status: ${res.status}`)
        }
        return res.json();
    })
    .then(data => {
        
        render(data);
    })
    .catch(error => {
        console.error('Error fetching ticket data:' , error);
    });


}

function render(cust){
    const cards = cust.map(cust => getCard(
        cust.customerId,
        cust.username,
        cust.address,
        cust.mobileNumber,
        cust.email,
        cust.createdOn,
        cust.isDeleted
    ));

    mainHtml.innerHTML = cards.join(" ");
}

function getCard(customerId, username,address,mobileNumber,email,createdOn,isDeleted){
    return`
    <tr>
        <td>${username}</td>
        <td>${address}</td>
        <td>${email}</td>
        <td>${mobileNumber}</td>
        <td>${createdOn}</td>
        <td>${isDeleted}</td>
        <td><button onclick="deleteCustomer(${customerId})" class="button">Delete</button></td>
    </tr>`;
}


function deleteCustomer(customerId){
    const jwtToken= getCookie();
    const apiurl= `http://localhost:8888/admin/customers/delete/${customerId}`;

    fetch(apiurl , {
        method: 'DELETE',
        headers: {
            "authorization": `Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJBa2FzaCIsInN1YiI6IkpXVCBUb2tlbiIsInVzZXJuYW1lIjoic3ViQGdtYWlsLmNvbSIsImF1dGhvcml0aWVzIjoiUk9MRV9BRE1JTiIsImlhdCI6MTY5MzE5ODgzNSwiZXhwIjoxNjkzMjI4ODM1fQ.lfzC7qvR0kyjTlOx6tIuTZvc4897s0Twt8CujET18e6XP0I-EP5qMbe18k-OIfMtxhh07ihy9MmDS1gnR6aJyw`
        }
    })
    .then(res => {
        if(!res.ok){
            throw new Error(`HTTP error! Status: ${res.status}`);
        }
        console.log(`Customer with ID ${customerId} deleted successfully.`);
        fetchCustomerData();
    })
    .catch(error => {
        console.error("Delete error:", error);
    });
}

function getCookie() {
    const value = document.cookie;
    const parts = value.split("=");

    return parts[1];
}

btn.addEventListener("click", logout)
 
function logout(){
    document.cookie = "name=Martin Roy;max-age=0";
    window.location.href= "adminLogin.html";
}

fetchCustomerData();