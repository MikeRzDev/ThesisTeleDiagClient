package co.edu.unab.gti.thesistelediagclient.data.database.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import co.edu.unab.gti.thesistelediagclient.data.database.TelediagDB;
import co.edu.unab.gti.thesistelediagclient.service.ServiceHandler;

/**
 * Created by user on 26/05/2016.
 */

@Table(database = TelediagDB.class)
public class Family extends BaseModel {

    @PrimaryKey
    String id;

    @Column
    String name;

    @Column
    String type;

    @Column
    String department;

    @Column
    String municipality;

    @Column
    String address;

    @Column
    String publicStratification;

    @Column
    String contactEmail;

    @Column
    String contactPhone;

    @Column
    String pictureUrl;

    @Column
    double latitude;

    @Column
    double longitude;

    @Column
    transient boolean isUploaded;

    @Column
    Date dateModified;



    public Family() {
    }

    public Family(String name, String type, String department, String municipality, String address,
                  String public_stratification, String contact_email, String contact_phone,
                  String picture_url, double latitude, double longitude, Date date_modified) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.type = type;
        this.department = department;
        this.municipality = municipality;
        this.address = address;
        this.publicStratification = public_stratification;
        this.contactEmail = contact_email;
        this.contactPhone = contact_phone;
        this.pictureUrl = picture_url;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateModified = date_modified;
        this.isUploaded = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPublicStratification() {
        return publicStratification;
    }

    public void setPublicStratification(String publicStratification) {
        this.publicStratification = publicStratification;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public File getPictureFile() {
        return new File(getPictureUrl());
    }

    public boolean isUploaded() {
        return isUploaded;
    }

    public void setUploaded(boolean uploaded) {
        isUploaded = uploaded;
    }

    public String getId() {
        return id;
    }

    @Override
    public void save() {
        super.save();
        ServiceHandler.getService().requestUpload();
    }
}
