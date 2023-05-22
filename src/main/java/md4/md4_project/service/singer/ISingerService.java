package md4.md4_project.service.singer;

import md4.md4_project.model.Singer;
import md4.md4_project.model.Song;

import java.util.List;

public interface ISingerService {
    List<Singer> findAll();
    Singer findByName(String name);
    List<Song> showAllSongOfSinger(String name);
    Singer findById(int id);
    void deleteById(int id);
    void save(String name, String avatar);
    void edit(int id, String name, String avatar);
}
