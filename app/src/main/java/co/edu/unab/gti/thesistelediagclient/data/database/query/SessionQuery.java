package co.edu.unab.gti.thesistelediagclient.data.database.query;


import com.raizlabs.android.dbflow.sql.language.SQLCondition;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import co.edu.unab.gti.thesistelediagclient.data.database.model.Person;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Session;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Session_Table;

public class SessionQuery {
    private static final List<Session> executeQueryList(SQLCondition query){
        return SQLite.select().from(Session.class)
                .where(query)
                .queryList();
    }

    private static final Session executeQuerySingle(SQLCondition query){
        return SQLite.select().from(Session.class)
                .where(query)
                .querySingle();
    }

    public static Session getSessionByEmail(String email){
        return executeQuerySingle(Session_Table.email.eq(email));
    }

    public static Session getSessionByEmailAndPassword(String email,String password){
        return SQLite.select().from(Session.class)
                .where(Session_Table.email.eq(email))
                .and(Session_Table.password.eq(password))
                .querySingle();
    }


}
