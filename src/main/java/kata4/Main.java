package kata4;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:chinook.db")){
            for(Track track : SqliteTrackLoader.with(connection).loadAll())
                System.out.println(track);
        }
    }
}
