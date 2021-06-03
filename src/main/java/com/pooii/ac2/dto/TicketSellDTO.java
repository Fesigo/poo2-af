package com.pooii.ac2.dto;

import javax.validation.constraints.NotNull;

import com.pooii.ac2.entities.Attend;
import com.pooii.ac2.entities.TicketType;

public class TicketSellDTO {

    @NotNull(message = "Ticket type required!")
    private TicketType type;

    @NotNull(message = "Attendee ID required!")
    private Attend attend;

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public Attend getAttend() {
        return attend;
    }

    public void setAttend(Attend attend) {
        this.attend = attend;
    }
    
}
