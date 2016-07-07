package co.edu.unab.gti.thesistelediagclient.data.database.query;

import com.raizlabs.android.dbflow.sql.language.SQLCondition;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import co.edu.unab.gti.thesistelediagclient.data.database.model.Family;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Family_Table;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Measure;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Measure_Table;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Person;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Person_Table;

/**
 * Created by user on 30/05/2016.
 */
public class FamilyQuery {


    private static final List<Family> executeQueryList(SQLCondition query){
        return SQLite.select().from(Family.class)
                .where(query)
                .queryList();
    }

    private static final Family executeQuerySingle(SQLCondition query){
        return SQLite.select().from(Family.class)
                .where(query)
                .querySingle();
    }

    public static final List<Family> getUnsendFamilies(){

        return executeQueryList(Family_Table.isUploaded.eq(false));

    }

    public static final List<Family> findAllFamilies(){

        return SQLite.select().from(Family.class)
                .queryList();
    }

    public static final Family findFamilyById(String id){

        return executeQuerySingle(Family_Table.id.eq(id));
    }

    public static final List<Family> findAllFamiliesByCriteria(String criteria){

        return executeQueryList(Family_Table.name.like("%"+criteria+"%"));
    }

    public static boolean isAllFamilyDataUploaded(String id){

        long familyUploaded  =  SQLite.selectCountOf().from(Family.class)
                .where(Family_Table.id.eq(id)).and(Family_Table.isUploaded.eq(false))
                .count();
        long familyPersonUploaded = SQLite.selectCountOf().from(Person.class)
                .where(Person_Table.familyId.eq(id)).and(Person_Table.isUploaded.eq(false))
                .count();
        long familyMeasureUploaded = SQLite.selectCountOf().from(Measure.class)
                .where(Measure_Table.familyId.eq(id)).and(Measure_Table.isUploaded.eq(false))
                .count();

        long sum = familyMeasureUploaded+familyPersonUploaded+familyUploaded;

        if (sum == 0) {
            return true;
        } else {
            return false;
        }
    }


}
