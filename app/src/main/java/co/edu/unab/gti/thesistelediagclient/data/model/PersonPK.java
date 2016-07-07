package co.edu.unab.gti.thesistelediagclient.data.model;

/**
 * Created by user on 5/06/2016.
 */
public class PersonPK {
    private String familyId;
    private String id;

    public PersonPK(String familyId, String id) {
        this.familyId = familyId;
        this.id = id;
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
}
