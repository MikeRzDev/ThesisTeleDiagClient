package co.edu.unab.gti.thesistelediagclient.data.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by user on 26/05/2016.
 */

@Database(name = TelediagDB.NAME, version = TelediagDB.VERSION)
public class TelediagDB {

    public static final String NAME = "Telediag"; // we will add the .db extension
    public static final int VERSION = 1;
}
