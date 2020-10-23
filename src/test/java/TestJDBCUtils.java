import com.xyq.web.utils.JDBCUtils;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestJDBCUtils {

    @Test
    public void testJDBCUtils() throws SQLException {
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    }

}
