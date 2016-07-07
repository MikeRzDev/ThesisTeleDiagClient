package co.edu.unab.gti.thesistelediagclient.data.database;



import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.structure.database.DatabaseHelperListener;
import com.raizlabs.dbflow.android.sqlcipher.SQLCipherOpenHelper;

/**
 * Created by user on 27/05/2016.
 */
public class SQLCipherHelperImpl  extends SQLCipherOpenHelper {


    public SQLCipherHelperImpl(DatabaseDefinition databaseDefinition, DatabaseHelperListener listener) {
        super(databaseDefinition, listener);
    }

    @Override
    protected String getCipherSecret() {
        return "m1p45sw0rd";
    }
}
