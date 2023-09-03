const ticketTable = document.getElementById("ticketTable");
const ticketBody = document.getElementById("ticketBody");
const statusFilter = document.getElementById("statusFilter");
const totalPaid = document.getElementById("totalPaid");

const customerId = 1; // Replace with the actual customer ID
const apiUrl = "http://localhost:3303"; // Replace with your actual backend URL

// Fetch customer's tickets from the API
fetch(`${apiUrl}/tickets/${customerId}`)
    .then(response => response.json())
    .then(tickets => {
        let totalPrice = 0;

        tickets.forEach(ticket => {
            const row = document.createElement("tr");

            const activityCell = document.createElement("td");
            activityCell.textContent = ticket.activity.activityName;

            const visitDateCell = document.createElement("td");
            visitDateCell.textContent = ticket.visitDate;

            const priceCell = document.createElement("td");
            priceCell.textContent = `$${ticket.price.toFixed(2)}`;
            totalPrice += ticket.price;

            const statusCell = document.createElement("td");
            const currentDate = new Date();
            const visitDate = new Date(ticket.visitDate);
            if (visitDate < currentDate) {
                statusCell.textContent = "Expired";
            } else if (visitDate.toDateString() === currentDate.toDateString()) {
                statusCell.textContent = "Operating";
            } else {
                statusCell.textContent = "Upcoming";
            }

            const actionsCell = document.createElement("td");
            const updateButton = document.createElement("button");
            updateButton.textContent = "Update";
            updateButton.addEventListener("click", () => {
                // Implement ticket update functionality here
                // Similar to booking pop-up, show a pop-up with form for updating
            });
            actionsCell.appendChild(updateButton);

            row.appendChild(activityCell);
            row.appendChild(visitDateCell);
            row.appendChild(priceCell);
            row.appendChild(statusCell);
            row.appendChild(actionsCell);

            ticketBody.appendChild(row);
        });

        totalPaid.textContent = `Total Paid: $${totalPrice.toFixed(2)}`;
    })
    .catch(error => {
        console.error("Error fetching tickets:", error);
    });
