package com.cordapp.underwriting.schema.health;

import com.google.common.collect.ImmutableList;
import net.corda.core.schemas.MappedSchema;

/**
 * MappedSchema subclass representing the custom schema for the UnderwtingHealth QueryableState.
 */
public class UnderwritingHealthSchemaV1 extends MappedSchema {

    /**
     * The constructor of the MappedSchema requires the schemafamily, verison, and a list of all JPA entity classes for
     * the Schema.
     */
    public UnderwritingHealthSchemaV1() {
        super(UnderwritingHealthSchemaFamily.class, 1, ImmutableList.of(PersistentUnderwritingHealthDetails.class));
    }
}
