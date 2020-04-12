package com.cordapp.underwriting.schema.underwritingRequest;

import com.google.common.collect.ImmutableList;
import net.corda.core.schemas.MappedSchema;

/**
 * MappedSchema subclass representing the custom schema for the UnderwitingRequest QueryableState.
 */

public class UnderwritingRequestSchemaV1 extends MappedSchema {


    /**
     * The constructor of the MappedSchema requires the schemafamily, verison, and a list of all JPA entity classes for
     * the Schema.
     */
    public UnderwritingRequestSchemaV1(){
        super(UnderwritingRequestSchemaFamily.class, 1 , ImmutableList.of(PersistentUnderwritingRequestDetails.class));
    }
}
