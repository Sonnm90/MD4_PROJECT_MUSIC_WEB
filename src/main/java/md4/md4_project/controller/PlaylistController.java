package md4.md4_project.controller;

import md4.md4_project.model.Playlist;
import md4.md4_project.model.Song;
import md4.md4_project.model.User;
import md4.md4_project.service.playlist.IPlaylistService;
import md4.md4_project.service.playlist.PlaylistServiceIMPL;
import md4.md4_project.service.song.ISongService;
import md4.md4_project.service.song.SongServiceIMPL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/playlist")
public class PlaylistController extends HttpServlet {
    IPlaylistService playlistService = new PlaylistServiceIMPL();
    ISongService songService = new SongServiceIMPL();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                showFormCreatePlaylist(request, response);
                break;
            case "showAllPlaylist":
                showAllPlaylist(request, response);
                break;
            case "detailPlaylist":
                detailPlaylist(request, response);
                break;
            case "editPlaylist":
                showFormEditPlaylist(request, response);
                break;



        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                actionCreatePlaylist(request, response);
                break;
            case "addSong":
                actionAddSongToPlaylist(request, response);
                break;
            case "removeSong":
                actionRemoveSongToPlaylist(request, response);
                break;

        }
    }

    private void showFormCreatePlaylist(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/content/playlist/create.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showAllPlaylist(HttpServletRequest request, HttpServletResponse response) {
        List<Playlist> playlists = new ArrayList<>();
        playlists = playlistService.findAll();
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/content/playlist/list_playlist.jsp");
        request.setAttribute("playlists", playlists);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showAllPlaylistOfUser(HttpServletRequest request, HttpServletResponse response) {
        List<Playlist> playlists = new ArrayList<>();
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        int userId = user.getId();
        playlists = playlistService.findAllPlaylistByUserId(userId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/content/playlist/list_playlist.jsp");
        request.setAttribute("playlists", playlists);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void detailPlaylist(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        List<Song> songList = songService.findAllSongOfPlaylistByPlaylistId(id);
        Playlist playlist =  playlistService.findById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/content/playlist/detailPlaylist.jsp");
        request.setAttribute("playlist", playlist);
        request.setAttribute("songList", songList);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void actionCreatePlaylist(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String avatar = request.getParameter("avatar");
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        int userId = user.getId();
        Playlist playlist = new Playlist(name, avatar, userId);
        playlistService.save(playlist);
        showAllPlaylistOfUser(request, response);
    }
    private void showFormEditPlaylist(HttpServletRequest request,HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/content/playlist/edit_playlist.jsp");
        request.setAttribute("songOfPlaylist",songService.findAllSongOfPlaylistByPlaylistId(id));
        request.setAttribute("song", songService.findAll());
        request.setAttribute("playlist",playlistService.findById(id));
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void  actionAddSongToPlaylist(HttpServletRequest request,HttpServletResponse response){
        int playlistId = Integer.parseInt(request.getParameter("id"));

        String[] songIdStr = request.getParameterValues("addSong");
        if (songIdStr!=null) {
            int[] songIds = new int[songIdStr.length];
            for (int i = 0; i < songIdStr.length; i++) {
                songIds[i] = Integer.parseInt(songIdStr[i]);
            }
            List<Integer> listSongId = new ArrayList<>();
            for (int i = 0; i < songIds.length; i++) {
                listSongId.add(Integer.valueOf(songIds[i]));
            }
            playlistService.addSongToPlaylist(playlistId, listSongId);
            System.out.println(listSongId);
        }

        showAllPlaylistOfUser(request, response);
    }

    private void actionRemoveSongToPlaylist(HttpServletRequest request,HttpServletResponse response){
        int playlistId = Integer.parseInt(request.getParameter("id"));

        String[] songIdStr = request.getParameterValues("removeSong");
        if (songIdStr!=null) {
            int[] songIds = new int[songIdStr.length];
            for (int i = 0; i < songIdStr.length; i++) {
                songIds[i] = Integer.parseInt(songIdStr[i]);
            }
            List<Integer> listSongId = new ArrayList<>();
            for (int i = 0; i < songIds.length; i++) {
                listSongId.add(Integer.valueOf(songIds[i]));
            }
            playlistService.removeSongToPlaylist(playlistId, listSongId);
        }
        showAllPlaylistOfUser(request, response);
    }
}

