package com.trecstudios.pillbox;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by derek on 11/15/14.
 */
public class Pill implements Serializable {
    private Date dateAdded;
    private String ndc; //e.g. 68462-126-01
    private String brandName; //e.g. Motrin
    private String fullName; //e.g. Ibuprofen
    private String dosageName; //e.g. 100mg per tablet
    private PillTiming timing; //e.g. represents "per day" "in the morning" "every 6 hours"
    private int dosage; //e.g. 2 tablets per [time interval]
    private int startingQuantity; //e.g. 100t tablets per bottle
    private int quantity; //e.g. 83 tablets left in bottle
    private String nickname; //e.g. blue pill (user-set)
    private Date nextTarget;

    public Pill(String ndc) {
        this.ndc = ndc;
        setFromNDC();
        dateAdded = new Date(); //i.e. now
    }

    public void setTiming(PillTiming timing) {
        this.timing = timing;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    public void setInitialTarget(Date target){
        this.nextTarget = target;
    }

    /*
    // For prompting user with date chooser or parse text or something
    public void updateTiming(PillTiming timing){
        this.timing = timing;
    }
    */

    // For prompting user with dose requirements (adjust/set from two per day to one per day, etc.)
    private void setFromNDC() {
    	if(ndc.equals("6846212601")){
    		brandName = null;
            fullName = "GABAPENTIN";
            dosageName = "mg";
            startingQuantity = 100; //FROM NDC
            quantity = startingQuantity; //FROM NDC
            dosage = 1; //FROM NDC
    	}else if(ndc.equals("8259266010")){
    		brandName = null;
    		fullName = "NAKED MightyMango";
    		dosageName = "flOz";
    		startingQuantity = 10; //FROM NDC
    		quantity = startingQuantity; //FROM NDC
    		dosage = 1; //FROM NDC
    	}else{
    		brandName = null;
            fullName = "GABAPENTIN";
            dosageName = "mg";
            startingQuantity = 100; //FROM NDC
            quantity = startingQuantity; //FROM NDC
            dosage = 1; //FROM NDC
    	}
    }
    
    public static String getNameFromNDC(String ndc){
    	if(ndc.equals("6846212601")){
    		return "GABAPENTIN";
    	}else if(ndc.equals("8259266010")){
    		return "NAKED MightyMango";
    	}else{
    		return "UNKNOWN";
    	}
    }

    private void decrementQuantityByDosage() {
        this.quantity -= this.dosage;
    }


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDosageName() {
        return this.dosageName;
    }

    public String getDisplayName() {
        if (this.nickname != null) return this.nickname;
        if (this.brandName != null) return this.brandName;
        /* else */
        return this.fullName;
    }

    public int getApproxDaysRemaining() {
        return (int) (this.quantity / (this.timing.pillsPerDay() * this.dosage));
    }

    public void markAsTaken() {
        this.nextTarget = this.timing.getNextTarget(this.nextTarget);
        this.decrementQuantityByDosage();
    }

    public boolean isLow(int daysThreshold) {
        return this.getApproxDaysRemaining() <= daysThreshold;
    }

    public boolean isUntaken(Date currentTime) {
       
        return currentTime.after(this.nextTarget);
    }

}
