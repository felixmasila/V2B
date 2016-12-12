package com.felixmasila.v2b.activity;

import java.util.Date;

/**
 * Created by felixmasila on 11/29/16.
 */

public class Donorlocationconstructor { int donor_id,donor_id_no;
    String donor_blood_type, donor_eligibility, donor_first_contact, donor_optional_conatct;
    Double longitude, latitude;
    Date last_donation_date;

    public int getDonor_id(){
        return donor_id;}
    public void setDonor_id(int donor_id){this.donor_id=donor_id;}

    public int getDonor_id_no(){return donor_id_no;}
    public void setDonor_id_no(int donor_id_no){this.donor_id_no=donor_id_no;}

    public String getDonor_first_contact(){return donor_first_contact;}
    public void setDonor_first_contact(String donor_first_contact){this.donor_first_contact=donor_first_contact;}

    public String getDonor_optional_conatct(){return donor_optional_conatct;}
    public void setDonor_optional_conatct(String donor_optional_conatct){this.donor_optional_conatct=donor_optional_conatct;}

    public String getDonor_blood_type(){return donor_blood_type;}
    public void setDonor_blood_type(String donor_blood_type){this.donor_blood_type=donor_blood_type;}

    public String getDonor_eligibility(){return donor_eligibility;}
    public void setDonor_eligibility(String donor_eligibility){this.donor_eligibility=donor_eligibility;}

    public Date getLast_donation_date(){return last_donation_date;}
    public void setLast_donation_date(Date last_donation_date){this.last_donation_date=last_donation_date;}

    public Double getLongitude()
    {
        return longitude;
    }
    public void setLongitude(Double longitude)
    {
        this.longitude = longitude;
    }
    public Double getLatitude()
    {
        return latitude;
    }
    public void setLatitude(Double latitude)
    {
        this.latitude = latitude;
    }

}
