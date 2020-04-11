package com.cordapp.underwriting.states;

import net.corda.core.contracts.ContractState;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class UnderwritingResponseNHOState implements ContractState {

    private final long ssn;
    private final UnderwriterHealthDetails underwriterHealthDetails;
    private final Date timeStamp;

    private final Party responder;
    private final Party respondingTo;


    public UnderwritingResponseNHOState(long ssn, UnderwriterHealthDetails underwriterHealthDetails, Date timeStamp, Party responder, Party respondingTo) {
        this.ssn = ssn;
        this.underwriterHealthDetails = underwriterHealthDetails;
        this.timeStamp = timeStamp;
        this.responder = responder;
        this.respondingTo = respondingTo;
    }

    public long getSsn() {
        return ssn;
    }

    public UnderwriterHealthDetails getUnderwriterHealthDetails() {
        return underwriterHealthDetails;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public Party getResponder() {
        return responder;
    }

    public Party getRespondingTo() {
        return respondingTo;
    }

    @NotNull
    @Override
    public List<AbstractParty> getParticipants() {
        return Arrays.asList(responder, respondingTo);
    }
}
