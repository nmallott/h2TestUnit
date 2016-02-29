import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.RunScript;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by nicolas on 29/02/2016.
 */
public class setUpH2Test {

    private static JdbcConnectionPool cp;
    private static Connection conn;
    private String scriptName = "ddl1.sql";

    @BeforeClass
    public static void beforeClass() throws SQLException {
        cp = JdbcConnectionPool.
                create("jdbc:h2:~/test", "sa", "sa");
        conn = cp.getConnection();
        cp.dispose();
    }

    @AfterClass
    public static void afterClass() throws SQLException {
        conn.close();
    }

    @Test
    public void setUpTest_SelectCurrentDate() throws SQLException {
        String sql = "select current_date from dual";
        ResultSet resultSet = conn.createStatement().executeQuery(sql);
        while(resultSet.next()) {
            System.out.println(resultSet.getDate(1));
        }
    }

    @Test
    public void loadScript() throws FileNotFoundException, SQLException {
        InputStream script = getClass().getClassLoader().getResourceAsStream(scriptName);
        RunScript.execute(conn, new InputStreamReader(script));
    }
}
