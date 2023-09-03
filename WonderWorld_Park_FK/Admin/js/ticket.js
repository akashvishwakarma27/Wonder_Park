let mainHtml = document.getElementById("body");
let btn= document.getElementById("logout");
function fetchTicketData() {
    const jwtToken = getCookie();
    const apiUrl = 'http://localhost:8888/admin/ticket/getAllTicket';

    fetch(apiUrl, {
        headers: {
            "Authorization": `Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJBa2FzaCIsInN1YiI6IkpXVCBUb2tlbiIsInVzZXJuYW1lIjoic3ViQGdtYWlsLmNvbSIsImF1dGhvcml0aWVzIjoiUk9MRV9BRE1JTiIsImlhdCI6MTY5MzE5ODgzNSwiZXhwIjoxNjkzMjI4ODM1fQ.lfzC7qvR0kyjTlOx6tIuTZvc4897s0Twt8CujET18e6XP0I-EP5qMbe18k-OIfMtxhh07ihy9MmDS1gnR6aJyw`
        }
    })
    .then(res => {
        if (!res.ok) {
            throw new Error(`Http error! Status: ${res.status}`);
        }
        return res.json();
    })
    .then(data => {
        console.log(data); 
        render(data); 
    })
    .catch(error => {
        console.error('Error fetching ticket data:', error);
    });
}

function render(tickets) {
    const cards = tickets.map(ticket => getCard(
        ticket.customer.username,
        ticket.customer.address,
        ticket.customer.mobileNumber,
        ticket.customer.email,
        ticket.activity.activityName,
        ticket.price,
        ticket.visitDate,
        ticket.createdOn,
        ticket.personCount
    ));

    mainHtml.innerHTML = cards.join(""); 
}

function getCard(username, address, mobile, email, activity, price, visit, createdOn , personCount) {
    return `
    <tr>
        <td>${username}</td>
        <td>${address}</td>
        <td>${mobile}</td>
        <td>${email}</td>
        <td>${activity}</td>
        <td>${price}</td>
        <td>${visit}</td>
        <td>${createdOn}</td>
        <td>${personCount}</td>
    </tr>`;
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

fetchTicketData();