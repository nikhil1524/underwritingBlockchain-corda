package com.cordapp.underwriting.flows.util;

import com.cordapp.underwriting.states.UnderwriterHealthDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class UnderwritingRandomHealthDetailsProvider {

    private static UnderwritingRandomHealthDetailsProvider instance = null;

    private static List<UnderwriterHealthDetails> listUnderwriterHealthDetails = null;

    private UnderwritingRandomHealthDetailsProvider() {
        listUnderwriterHealthDetails = new ArrayList<>();
        listUnderwriterHealthDetails.add(new UnderwriterHealthDetails(1,
                "Bob", "Marlie", new Date(), "male", "5.2", 7.0,
                false, false, true));
        listUnderwriterHealthDetails.add(new UnderwriterHealthDetails(1,
                "John", "Sinha", new Date(), "male", "4.6", 7.1,
                false, false, true));
        listUnderwriterHealthDetails.add(new UnderwriterHealthDetails(1,
                "Danial", "Woplis", new Date(), "male", "9.7", 6.4,
                false, true, false));
        listUnderwriterHealthDetails.add(new UnderwriterHealthDetails(1,
                "Kate", "surname", new Date(), "female", "8.4", 6.8,
                true, false, false));
        listUnderwriterHealthDetails.add(new UnderwriterHealthDetails(1,
                "Bob", "surname", new Date(), "male", "3.5", 7.2,
                false, true, true));
        listUnderwriterHealthDetails.add(new UnderwriterHealthDetails(1,
                "Amalia", "Watson", new Date(), "female", "8.3", 7.6,
                false, true, false));
        listUnderwriterHealthDetails.add(new UnderwriterHealthDetails(1,
                "Janicke", "Cariote", new Date(), "female", "4.7", 7.8,
                true, true, true));
        listUnderwriterHealthDetails.add(new UnderwriterHealthDetails(1,
                "Johnson", "Charie", new Date(), "male", "8.1", 6.9,
                true, false, false));
    }

    public static UnderwritingRandomHealthDetailsProvider getInstance(){
        if(instance == null ){
           instance = new UnderwritingRandomHealthDetailsProvider();
        }
        return instance;
    }

    public final static UnderwriterHealthDetails getRandomHealthDetails(long ssn){
        getInstance();
        UnderwriterHealthDetails caches =  listUnderwriterHealthDetails.get(new Random().nextInt(7));
        return new UnderwriterHealthDetails(ssn, caches.getName(), caches.getSurname(), caches.getDateOfBirth(),
                caches.getGender(), caches.getBmi(), caches.getHeight(), caches.isHasDiabatics(), caches.isHasBloodPressure(),
                caches.isHasHeartProblems());
    }
}
