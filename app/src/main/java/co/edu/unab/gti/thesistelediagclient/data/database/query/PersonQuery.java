package co.edu.unab.gti.thesistelediagclient.data.database.query;

import com.raizlabs.android.dbflow.sql.language.SQLCondition;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import co.edu.unab.gti.thesistelediagclient.data.database.model.Family;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Person;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Person_Table;

/**
 * Created by user on 31/05/2016.
 */
public class PersonQuery {

    private static final List<Person> executeQueryList(SQLCondition query){
        return SQLite.select().from(Person.class)
                .where(query)
                .queryList();
    }

    private static final Person executeQuerySingle(SQLCondition query){
        return SQLite.select().from(Person.class)
                .where(query)
                .querySingle();
    }

    public static final List<Person> getPersonsByFamilyId(String familyId){
        return executeQueryList(Person_Table.familyId.eq(familyId));
    }

    public static final Person getPerson(String id){
        return executeQuerySingle(Person_Table.id.eq(id));
    }

    public static final List<Person> getUnsendPersons(){
        return executeQueryList(Person_Table.isUploaded.eq(false));
    }
}
