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
import co.edu.unab.gti.thesistelediagclient.service.ServiceHandler;

/**
 * Created by user on 26/05/2016.
 */
@Table(database = TelediagDB.class)
public class Measure extends BaseModel {
    @PrimaryKey
    transient  String id;

    @Column
    @NotNull
    transient String familyId;

    @Column
    @NotNull
    transient String personId;

    MeasurePK measurePK;

    @Column
    transient boolean isUploaded;

    @Column
    String type;

    @Column
    String value;

    @Column
    Date date;



    public Measure() {

    }

    public Measure(String familyId, String personId, String type, String value, Date date) {
        this.id = UUID.randomUUID().toString();
        this.familyId = familyId;
        this.personId = personId;
        this.isUploaded = false;
        this.type = type;
        this.value = value;
        this.date = date;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public boolean isUploaded() {
        return isUploaded;
    }

    public void setUploaded(boolean uploaded) {
        isUploaded = uploaded;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public MeasurePK getMeasurePK() {
        return measurePK;
    }

    public void setMeasurePK(MeasurePK measurePK) {
        this.measurePK = measurePK;
    }

    @Override
    public void save() {
        super.save();
        ServiceHandler.getService().requestUpload();
    }
}
