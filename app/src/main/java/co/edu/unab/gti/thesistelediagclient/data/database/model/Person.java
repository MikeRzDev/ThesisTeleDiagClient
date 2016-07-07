package co.edu.unab.gti.thesistelediagclient.data.database.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;
import java.util.UUID;

import co.edu.unab.gti.thesistelediagclient.data.database.TelediagDB;
import co.edu.unab.gti.thesistelediagclient.data.model.MeasurePK;
import co.edu.unab.gti.thesistelediagclient.data.model.PersonPK;
import co.edu.unab.gti.thesistelediagclient.service.ServiceHandler;

/**
 * Created by user on 26/05/2016.
 */
@Table(database = TelediagDB.class)
public class Person extends BaseModel {
    @PrimaryKey
    transient String id;

    @Column
    @NotNull
    transient String familyId;

    PersonPK personPK;

    @Column
    transient boolean isUploaded;

    @Column
    String citizenId;

    @Column
    String citizenIdType;

    @Column
    String ethnicGroup;

    @Column
    String familyRelationship;

    @Column
    String firstName;

    @Column
    String secondName;

    @Column
    String firstSurname;

    @Column
    String secondSurname;

    @Column
    String gender;

    @Column
    Date birthdate;

    @Column
    String scholarityLevel;

    @Column
    String businessActivity;

    @Column
    String profession;

    @Column
    String healthProviderType;

    @Column
    String healthProvider;

    @Column
    String specialCondition;

    @Column
    String cronicCondition;

    @Column
    long currentMeasures;

    @Column
    String pictureURL;

    @Column
    Date dateModified;


    public Person() {

    }

    public Person(String familyId, String citizenId, String citizenIdType, String ethnicGroup,
                  String familyRelationship, String firstName, String secondName, String firstSurname,
                  String secondSurname, String gender, Date birthdate, String scholarityLevel,
                  String businessActivity, String profession, String healthProviderType,
                  String healthProvider, String specialCondition, String cronicCondition,
                  long currentMeasures, String pictureURL, Date dateModified) {
        this.id = UUID.randomUUID().toString();
        this.familyId = familyId;
        this.isUploaded = false;
        this.citizenId = citizenId;
        this.citizenIdType = citizenIdType;
        this.ethnicGroup = ethnicGroup;
        this.familyRelationship = familyRelationship;
        this.firstName = firstName;
        this.secondName = secondName;
        this.firstSurname = firstSurname;
        this.secondSurname = secondSurname;
        this.gender = gender;
        this.birthdate = birthdate;
        this.scholarityLevel = scholarityLevel;
        this.businessActivity = businessActivity;
        this.profession = profession;
        this.healthProviderType = healthProviderType;
        this.healthProvider = healthProvider;
        this.specialCondition = specialCondition;
        this.cronicCondition = cronicCondition;
        this.currentMeasures = currentMeasures;
        this.pictureURL = pictureURL;
        this.dateModified = dateModified;

    }

    public String getId() {
        return id;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public boolean isUploaded() {
        return isUploaded;
    }

    public void setUploaded(boolean uploaded) {
        isUploaded = uploaded;
    }

    public String getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(String citizenId) {
        this.citizenId = citizenId;
    }

    public String getCitizenIdType() {
        return citizenIdType;
    }

    public void setCitizenIdType(String citizenIdType) {
        this.citizenIdType = citizenIdType;
    }

    public String getEthnicGroup() {
        return ethnicGroup;
    }

    public void setEthnicGroup(String ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
    }

    public String getFamilyRelationship() {
        return familyRelationship;
    }

    public void setFamilyRelationship(String familyRelationship) {
        this.familyRelationship = familyRelationship;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getFirstSurname() {
        return firstSurname;
    }

    public void setFirstSurname(String firstSurname) {
        this.firstSurname = firstSurname;
    }

    public String getSecondSurname() {
        return secondSurname;
    }

    public void setSecondSurname(String secondSurname) {
        this.secondSurname = secondSurname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getScholarityLevel() {
        return scholarityLevel;
    }

    public void setScholarityLevel(String scholarityLevel) {
        this.scholarityLevel = scholarityLevel;
    }

    public String getBusinessActivity() {
        return businessActivity;
    }

    public void setBusinessActivity(String businessActivity) {
        this.businessActivity = businessActivity;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getHealthProviderType() {
        return healthProviderType;
    }

    public void setHealthProviderType(String healthProviderType) {
        this.healthProviderType = healthProviderType;
    }

    public String getHealthProvider() {
        return healthProvider;
    }

    public void setHealthProvider(String healthProvider) {
        this.healthProvider = healthProvider;
    }

    public String getSpecialCondition() {
        return specialCondition;
    }

    public void setSpecialCondition(String specialCondition) {
        this.specialCondition = specialCondition;
    }

    public String getCronicCondition() {
        return cronicCondition;
    }

    public void setCronicCondition(String cronicCondition) {
        this.cronicCondition = cronicCondition;
    }

    public long getCurrentMeasures() {
        return currentMeasures;
    }

    public void setCurrentMeasures(long currentMeasures) {
        this.currentMeasures = currentMeasures;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public PersonPK getPersonPK() {
        return personPK;
    }

    public void setPersonPK(PersonPK personPK) {
        this.personPK = personPK;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void save() {
        super.save();
        ServiceHandler.getService().requestUpload();
    }
}
