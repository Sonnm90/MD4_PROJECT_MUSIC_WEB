package md4.md4_project.service.band;

import md4.md4_project.model.Band;
import md4.md4_project.model.Song;

import java.util.List;

public interface IBandService {
    void save(Band band);
    List<Band> findAll();
    Band findByName(String name);
    List<Song> showAllSongOfBand(String name);

    void deleteById(int id);

    void save(String name, String avatar);

}
