
         document.addEventListener("DOMContentLoaded", function() {
        const activityContainer = document.getElementById("activityContainer");
        const apiUrl = "http://localhost:8888/customers"; // Replace with your actual backend URL
        // const customerId = window.sessionStorage.getItem("id");
        const custoemrId = 1;
        const jwtToken = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJBa2FzaCIsInN1YiI6IkpXVCBUb2tlbiIsInVzZXJuYW1lIjoiYUBnbWFpbC5jb20iLCJhdXRob3JpdGllcyI6IlJPTEVfVVNFUiIsImlhdCI6MTY5MzE2MDE3OSwiZXhwIjoxNjkzMTkwMTc5fQ.vKGhF48EfGGKuE0Up8c3N9wxDH8bHMX0IZBKnkx0iKOUoE4y0lwaXtBSOqoYD1bS2o1Co1LUx09fQeSFDw3w2g";
   
        let activityId =0;
        // Function to fetch and display activities
        function fetchActivities() {
            fetch(`${apiUrl}/activity/all?pageNumber=0&pageSize=6`, {
                headers: {
                    "Authorization": `Bearer ${jwtToken}`
                }
            })
            .then(res => {
                if (!res.ok) {
                    throw new Error(`Http error! Status: ${res.status}`);
                }
                return res.json();
            })
            .then(activities => {
                activities.forEach(activity => {
                    const card = document.createElement("div");
                    card.classList.add("card");

                    const image = document.createElement("img");
                    image.src = activity.url;
                    image.alt = activity.activityName;

                    const title = document.createElement("h2");
                    title.classList.add("card-title");
                    title.textContent = activity.activityName;

                    const description = document.createElement("p");
                    description.classList.add("card-description");
                    description.textContent = activity.description;

                    const price = document.createElement("p");
                    price.classList.add("card-price");
                    price.textContent = `$${activity.price.toFixed(2)}`;

                    const bookButton = document.createElement("button");
                    bookButton.textContent = "Book ticket for the activity";
                    bookButton.classList.add("book-button");

                    bookButton.addEventListener("click", ()=>
                    {
                        activityId = activity.activityId;
                        
                    })
             
                      
                    // Add classes to the button
                    bookButton.classList.add("btn", "btn-info", "btn-lg");
                    bookButton.setAttribute("data-toggle", "modal");
                    bookButton.setAttribute("data-target", "#myModal");

                    card.appendChild(image);
                    card.appendChild(title);
                    card.appendChild(description);
                    card.appendChild(price);
                    card.appendChild(bookButton);

                    activityContainer.appendChild(card);
                });
            })
            .catch(error => {
                console.error("Error fetching activities:", error);
            });
        }

         

        // Call the function to fetch and display activities
        fetchActivities();
 
        // Handling form submission inside the modal
        const bookingForm = document.getElementById("bookingForm");

        bookingForm.addEventListener("submit", function(event) {
            event.preventDefault();

            // Retrieve form data
            const formData = new FormData(bookingForm);
            const visitDate = formData.get("visitDate");
            const personCount = parseInt(formData.get("personCount"));

            // Get the stored activityId from the clicked button
            
            // Booking data
            const bookingData = {
                visitDate: visitDate,
                personCount: personCount
            };
    
            // Send booking request to the server using activityId
            fetch(`${apiUrl}/customers/ticket/2/${activityId}`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${jwtToken}`
                
                },
                body: JSON.stringify(bookingData)
            })
            .then(response => response.json())
            .then(ticket => {
                // Handle booking success
                $('#myModal').modal('hide');


//                  // Display the booked ticket details in a new modal
//     const ticketDetailsModal = `
//     <div class="modal fade" id="ticketDetailsModal" role="dialog">
//         <div class="modal-dialog">
//             <div class="modal-content">
//                 <div class="modal-header">
//                     <button type="button" class="close" data-dismiss="modal">&times;</button>
//                     <h4 class="modal-title">Ticket Details</h4>
//                 </div>
//                 <div class="modal-body">
//                     <p><strong>Activity:</strong> ${ticket.activity.activity_name}</p>
//                     <p><strong>Visit Date:</strong> ${ticket.visitDate}</p>
//                     <p><strong>Number of Persons:</strong> ${ticket.personCount}</p>
//                     <p><strong>Total Price:</strong> $${ticket.price.toFixed(2)}</p>
//                     <p><strong>Ticket ID:</strong> ${ticket.ticketId}</p>
//                 </div>
//                 <div class="modal-footer">
//                     <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
//                 </div>
//             </div>
//         </div>
//     </div>
// `;
console.log(ticket);
// Append the modal HTML to the body
// document.body.insertAdjacentHTML('beforeend', ticketDetailsModal);

// Show the ticket details modal
$('#ticketDetailsModal').modal('show');

                // Show a success message or perform further actions
                // ...
            })
            .catch(error => {
                // Handle booking error
                console.error("Error booking ticket:", error);
                // Display an error message or handle the error
                // ...
            });
        });
    });
