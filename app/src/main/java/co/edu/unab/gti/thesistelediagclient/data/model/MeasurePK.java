package co.edu.unab.gti.thesistelediagclient.data.model;

/**
 * Created by user on 5/06/2016.
 */
public class MeasurePK {
    private String familyId;
    private String id;
    private String personId;

    public MeasurePK(String familyId, String id, String personId) {
        this.familyId = familyId;
        this.id = id;
        this.personId = personId;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }
}
