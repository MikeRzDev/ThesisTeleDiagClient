package co.edu.unab.gti.thesistelediagclient.data.database.query;

import com.raizlabs.android.dbflow.sql.language.SQLCondition;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import co.edu.unab.gti.thesistelediagclient.data.database.model.Family;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Measure;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Measure_Table;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Person_Table;

/**
 * Created by user on 31/05/2016.
 */
public class MeasureQuery {

    private static final List<Measure> executeQueryList(SQLCondition query){
        return SQLite.select().from(Measure.class)
                .where(query)
                .queryList();
    }

    private static final Measure executeQuerySingle(SQLCondition query){
        return SQLite.select().from(Measure.class)
                .where(query)
                .querySingle();
    }

    public static final List<Measure> getPersonMeasures(String personId){
       return executeQueryList(Measure_Table.personId.eq(personId));
    }

    public static final Measure getMeasure(String measureId){
        return executeQuerySingle(Measure_Table.id.eq(measureId));
    }

    public static final List<Measure> getUnsendMeasures(){
        return executeQueryList(Measure_Table.isUploaded.eq(false));
    }




}
