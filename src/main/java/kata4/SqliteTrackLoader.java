package kata4;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static java.util.Collections.emptyList;

public class SqliteTrackLoader implements TrackLoader{
    private final Connection connection;

    public SqliteTrackLoader(Connection connection) {
        this.connection = connection;
    }

    public static TrackLoader with(Connection connection){
        return new SqliteTrackLoader(connection);
    }

    @Override
    public List<Track> loadAll(){
        try{
            return load(queryAll());
        }catch (SQLException){
            return emptyList();
        }
    }
    private List<Track> load(ResultSet resultSet) throws  SQLException{
        List<Track> list = new ArrayList<>();
        while (resultSet.next())
            list.add(trackFrom(resultSet));
        return list;
    }
    private Track trackFrom(ResultSet resultSet) throws SQLException{
        return new Track(
                resultSet.getString("track"),
                resultSet.getInt("milliseconds"),
                resultSet.getDouble("unitPrice"),
                resultSet.getString("album"),
                resultSet.getString("genre"),
                resultSet.getString("artist")
        );
    }
    private final static String QueryAll = "SELECT tracks.name as track,Milliseconds, UnitPrice, album.Title as album, genres.Name as genre,artist.Name as artist FROM tracks, albums, genres artists WHERE\n" +
            "\ttracks.AlbumId = albums.AlbumId AND\n" +
            "\ttracks.GenresId = genres.GenreId AND\n" +
            "\ttracks.ArtistId = artists.ArtistId AND\n";
    private ResultSet queryAll() throws SQLException{
        return connection.createStatement().executeQuery(QueryAll);
    }
}
