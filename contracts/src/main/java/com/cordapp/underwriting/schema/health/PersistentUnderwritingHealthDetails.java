package com.cordapp.underwriting.schema.health;


import net.corda.core.schemas.PersistentState;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

/**
 * JPA Entity for saving Health details to the database table
 */
@Entity
@Table(name = "HEALTH_DETAILS")
public class PersistentUnderwritingHealthDetails extends PersistentState implements Serializable {

   // @Id private final UUID id;
    @Column private final Long ssn;
    @Column private final String name;
    @Column private final String surname;
    @Column private final Date dateOfBirth;
    @Column private final String gender;
    @Column private final String bmi;
    @Column private final Float height;
    @Column private final Boolean hasDiabetics;
    @Column private final Boolean hasBloodPressure;
    @Column private final Boolean isHasHeartProblems;


    public PersistentUnderwritingHealthDetails(Long ssn, String name, String surname, Date dateOfBirth, String gender, String bmi, Float height, Boolean hasDiabetics, Boolean hasBloodPressure, Boolean isHasHeartProblems) {
      //  this.id = UUID.randomUUID();
        this.ssn = ssn;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bmi = bmi;
        this.height = height;
        this.hasDiabetics = hasDiabetics;
        this.hasBloodPressure = hasBloodPressure;
        this.isHasHeartProblems = isHasHeartProblems;
    }

    public PersistentUnderwritingHealthDetails(){
       // this.id = null;
        this.ssn = null;
        this.name = null;
        this.surname = null;
        this.dateOfBirth = null;
        this.gender = null;
        this.bmi = null;
        this.height = null;
        this.hasDiabetics = null;
        this.hasBloodPressure = null;
        this.isHasHeartProblems = null;
    }

//    public UUID getId() {
//        return id;
//    }

    public Long getSsn() {
        return ssn;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getBmi() {
        return bmi;
    }

    public Float getHeight() {
        return height;
    }

    public Boolean getHasDiabetics() {
        return hasDiabetics;
    }

    public Boolean getHasBloodPressure() {
        return hasBloodPressure;
    }

    public Boolean getHasHeartProblems() {
        return isHasHeartProblems;
    }
}
