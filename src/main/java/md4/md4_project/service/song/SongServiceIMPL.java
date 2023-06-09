package md4.md4_project.service.song;

import md4.md4_project.config.ConnectSQL;
import md4.md4_project.model.Song;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongServiceIMPL implements ISongService {
    private Connection connection = ConnectSQL.getConnection();
    private final String SELECT_ALL_SONG = "SELECT * FROM SONG";
    private final String CREAT_NEW_SONG = "INSERT INTO SONG (name,categoryId,singerId,bandId,userId) values(?,?,?,?,?)";
    private final String CREAT_NEW_SONG_1 = "INSERT INTO SONG (name,categoryId,singerId,userId) values(?,?,?,?)";
    private final String CREAT_NEW_SONG_2 = "INSERT INTO SONG (name,categoryId,bandId,userId) values(?,?,?,?)";
    private final String ADD_SONG_ID_TO_BAND = "INSERT INTO SONG_OF_BAND (songId,bandId) values (?,?)";
    private final String ADD_SONG_ID_TO_SINGER = "INSERT INTO SONG_OF_SINGER (songId,singerId) values (?,?)";

    @Override
    public List findAll() {
        List<Song> songList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SONG);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Song song = new Song(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("category_Id"), resultSet.getInt("user_id"));
                songList.add(song);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return songList;
    }

    @Override
    public void save(Song song) {
        PreparedStatement preparedStatement = null;
        if (song.getBandId() == null) {
            try {
                connection.setAutoCommit(false);
                preparedStatement = connection.prepareStatement(CREAT_NEW_SONG, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, song.getName());
                preparedStatement.setInt(2, song.getCategoryId());
                preparedStatement.setArray(3, (Array) song.getSingerId());
                preparedStatement.setArray(4, (Array) song.getBandId());
                preparedStatement.setInt(5, song.getUserId());
                preparedStatement.executeUpdate();
                int id = 0;
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                while (resultSet.next()) {
                    id = resultSet.getInt(1);
                }

                PreparedStatement preparedStatement2 =connection.prepareStatement(ADD_SONG_ID_TO_SINGER);
                List<Integer> listSingerId = song.getSingerId();
                for (int i = 0; i < listSingerId.size(); i++) {
                    preparedStatement2.setInt(1,id);
                    preparedStatement2.setInt(2,listSingerId.get(i));
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (song.getSingerId() == null) {
            try {
                connection.setAutoCommit(false);
                preparedStatement = connection.prepareStatement(CREAT_NEW_SONG, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, song.getName());
                preparedStatement.setInt(2, song.getCategoryId());
                preparedStatement.setArray(3, (Array) song.getSingerId());
                preparedStatement.setArray(4, (Array) song.getBandId());
                preparedStatement.setInt(5, song.getUserId());
                preparedStatement.executeUpdate();
                int id = 0;
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                while (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
                PreparedStatement preparedStatement1 = connection.prepareStatement(ADD_SONG_ID_TO_BAND);
                List<Integer> listBandId = song.getBandId();
                for (int i = 0; i < listBandId.size(); i++) {
                    preparedStatement1.setInt(1,id);
                    preparedStatement1.setInt(2,listBandId.get(i));
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                connection.setAutoCommit(false);
                preparedStatement = connection.prepareStatement(CREAT_NEW_SONG, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, song.getName());
                preparedStatement.setInt(2, song.getCategoryId());
                preparedStatement.setArray(3, (Array) song.getSingerId());
                preparedStatement.setArray(4, (Array) song.getBandId());
                preparedStatement.setInt(5, song.getUserId());
                preparedStatement.executeUpdate();
                int id = 0;
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                while (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
                PreparedStatement preparedStatement1 = connection.prepareStatement(ADD_SONG_ID_TO_BAND);
                List<Integer> listBandId = song.getBandId();
                for (int i = 0; i < listBandId.size(); i++) {
                    preparedStatement1.setInt(1,id);
                    preparedStatement1.setInt(2,listBandId.get(i));
                }
                PreparedStatement preparedStatement2 =connection.prepareStatement(ADD_SONG_ID_TO_SINGER);
                List<Integer> listSingerId = song.getSingerId();
                for (int i = 0; i < listSingerId.size(); i++) {
                    preparedStatement2.setInt(1,id);
                    preparedStatement2.setInt(2,listSingerId.get(i));
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }


    @Override
    public Object findById(int id) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }
}
