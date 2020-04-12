package com.cordapp.underwriting.states;

import net.corda.core.serialization.CordaSerializable;

import java.util.Date;

@CordaSerializable
public class UnderwriterHealthDetails {

    private final long ssn;
    private final String name;
    private final String surname;
    private final Date dateOfBirth;
    private final String gender;
    private final String bmi;
    private final double height;
    private final boolean hasDiabatics;
    private final boolean hasBloodPressure;
    private final boolean hasHeartProblems;

    public UnderwriterHealthDetails(long ssn, String name, String surname, Date dateOfBirth, String gender, String bmi, double height, boolean hasDiabatics, boolean hasBloodPressure, boolean hasHeartProblems) {
        this.ssn = ssn;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bmi = bmi;
        this.height = height;
        this.hasDiabatics = hasDiabatics;
        this.hasBloodPressure = hasBloodPressure;
        this.hasHeartProblems = hasHeartProblems;
    }

    public long getSsn() {
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

    public double getHeight() {
        return height;
    }

    public boolean isHasDiabatics() {
        return hasDiabatics;
    }

    public boolean isHasBloodPressure() {
        return hasBloodPressure;
    }

    public boolean isHasHeartProblems() {
        return hasHeartProblems;
    }
}
