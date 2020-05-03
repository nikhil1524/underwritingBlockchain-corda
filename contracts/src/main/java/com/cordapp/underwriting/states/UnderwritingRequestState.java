package com.cordapp.underwriting.states;

import com.cordapp.underwriting.contracts.UnderwritingRequestContract;
import com.cordapp.underwriting.model.UnderwritingRequestType;
import com.cordapp.underwriting.schema.underwritingRequest.PersistentUnderwritingRequestDetails;
import com.cordapp.underwriting.schema.underwritingRequest.UnderwritingRequestSchemaV1;
import com.google.common.collect.ImmutableList;
import net.corda.core.contracts.BelongsToContract;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import net.corda.core.schemas.MappedSchema;
import net.corda.core.schemas.PersistentState;
import net.corda.core.schemas.QueryableState;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

// *********
// * State for UnderwritingRequesting*
// *********

@BelongsToContract(UnderwritingRequestContract.class)
public class UnderwritingRequestState implements QueryableState {

    private final long ssn;
    private final UnderwritingRequestType requestType;
    private final Date date;

    private final Party requester;
    private final Party requestingTo;
    private final Character status;

    public UnderwritingRequestState(long ssn, UnderwritingRequestType requestType, Date date, Party requester, Party requestingTo,Character status) {
        this.ssn = ssn;
        this.requestType = requestType;
        this.date = date;
        this.requester = requester;
        this.requestingTo = requestingTo;
        this.status = status;
    }

    public long getSsn() {
        return ssn;
    }

    public UnderwritingRequestType getRequestType() {
        return requestType;
    }

    public Date getDate() {
        return date;
    }

    public Party getRequester() {
        return requester;
    }

    public Party getRequestingTo() {
        return requestingTo;
    }

    public Character getStatus() { return status; }

    @Override
    public List<AbstractParty> getParticipants(){
        return Arrays.asList(requester, requestingTo);
    }

    @NotNull
    @Override
    public PersistentState generateMappedObject(@NotNull MappedSchema schema) {
        if(schema instanceof UnderwritingRequestSchemaV1){
          return new PersistentUnderwritingRequestDetails(this.ssn, this.requestType.toString(),
                  new java.sql.Date(new Date().getTime()),this.requester.getName().toString(),
                  this.requestingTo.getName().toString(), this.status);
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
        return ImmutableList.of(new UnderwritingRequestSchemaV1());
    }
}


