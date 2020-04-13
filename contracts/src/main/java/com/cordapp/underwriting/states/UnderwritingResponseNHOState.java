package com.cordapp.underwriting.states;

import com.cordapp.underwriting.contracts.UnderwritingRequestContract;
import com.cordapp.underwriting.schema.health.PersistentUnderwritingHealthDetails;
import com.cordapp.underwriting.schema.health.UnderwritingHealthSchemaV1;
import com.cordapp.underwriting.schema.underwritingRequest.PersistentUnderwritingRequestDetails;
import com.cordapp.underwriting.schema.underwritingRequest.UnderwritingRequestSchemaV1;
import com.google.common.collect.ImmutableList;
import net.corda.core.contracts.BelongsToContract;
import net.corda.core.contracts.ContractState;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import net.corda.core.schemas.MappedSchema;
import net.corda.core.schemas.PersistentState;
import net.corda.core.schemas.QueryableState;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@BelongsToContract(UnderwritingRequestContract.class)
public class UnderwritingResponseNHOState implements QueryableState {

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



    @NotNull
    @Override
    public PersistentState generateMappedObject(@NotNull MappedSchema schema) {
        if(schema instanceof UnderwritingHealthSchemaV1){

            return new PersistentUnderwritingHealthDetails(this.ssn, this.underwriterHealthDetails.getName(),
                    this.underwriterHealthDetails.getSurname(),new java.sql.Date(this.underwriterHealthDetails.getDateOfBirth().getTime()),
                    this.underwriterHealthDetails.getGender(), this.underwriterHealthDetails.getBmi(),
                    this.underwriterHealthDetails.getHeight(), this.underwriterHealthDetails.isHasDiabatics(),
                    this.underwriterHealthDetails.isHasBloodPressure(),
                    this.underwriterHealthDetails.isHasHeartProblems());
        } else{
            throw new IllegalArgumentException("Unsupported Schema");
        }
    }

    /**
     * Returns a list of supported Schemas by this Queryable State.
     *
     * @return Iterable<MappedSchema>
     */
    @NotNull
    @Override
    public Iterable<MappedSchema> supportedSchemas() {
        return ImmutableList.of(new UnderwritingHealthSchemaV1());
    }

}
