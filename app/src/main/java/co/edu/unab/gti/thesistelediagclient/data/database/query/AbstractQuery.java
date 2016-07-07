package co.edu.unab.gti.thesistelediagclient.data.database.query;

import com.raizlabs.android.dbflow.sql.language.SQLCondition;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.Model;

import java.util.List;

import co.edu.unab.gti.thesistelediagclient.data.database.model.Family;


/**
 * Created by user on 31/05/2016.
 */
public abstract class AbstractQuery<T extends Model>  {

    private Class<T>  modelClass;

    public AbstractQuery( Class<T> modelClass){
        this.modelClass = modelClass;
    }

    public T getSingleQuery(SQLCondition query){
        return SQLite.select().from(modelClass)
                .where(query)
                .querySingle();
    }

    public List<T> getQueryList(SQLCondition query){
        return SQLite.select().from(modelClass)
                .where(query)
                .queryList();
    }

    public List<T> findAll(){
        return SQLite.select().from(modelClass)
                .queryList();
    }




}
