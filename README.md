# POO2-AF

## Members:
- Felipe Siqueira Godoy     RA: 190337
- Guilherme Machado Crespo  RA: 190501

# Conceptual Model

![Model](/Model.png)

# JSON to create new entities:
## /admins
{
    "name": "Admin Name",
    "email": "admin@email.com",
    "phoneNumber": "(15) 12121212"
}

## /attendees
{
    "name": "Attendee Name",
    "email": "attendee@email.com"
}

## /places
{
    "name": "Place Name",
    "address": "Street X, 99"
}

## /events
{
    "name": "Event Name",
    "description": "Event Description",
    "startDate": "22/06/2021",
    "endDate": "05/07/2021",
    "startTime": "16:30:00",
    "endTime": "20:00:00",
    "emailContact": "event@email.com",
    "amountFreeTickets": "5",
    "amountPayedTickets": "9",
    "priceTicket": "50.00",
    "admin":{
        "id": "2"
    }
}

## /events/{id}/tickets
{
    "type": "PAYED",
    "attend":{
        "id": "2"
    }
}