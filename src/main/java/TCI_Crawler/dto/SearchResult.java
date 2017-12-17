package TCI_Crawler.dto;

import TCI_Crawler.searchObjects.Book;
import TCI_Crawler.searchObjects.Movie;
import TCI_Crawler.searchObjects.Music;
import TCI_Crawler.searchObjects.SearchObjectBase;

import java.util.ArrayList;

public class SearchResult{

    //private final ArrayList<SearchObjectBase> retrievedObjects;
    private ArrayList<Book> books;
    private ArrayList<Music> music;
    private ArrayList<Movie> movies;

    private final long time;

    public SearchResult(ArrayList<SearchObjectBase> retrievedObjects, long time) {
        //this.retrievedObjects = retrievedObjects;
        this.time = time;
        writeToClassLists(retrievedObjects);
    }
    private void writeToClassLists(ArrayList<SearchObjectBase> listOfSearchObjects){
        ArrayList<Book> tempBookList = new ArrayList<>();
        ArrayList<Music> tempMusicList = new ArrayList<>();
        ArrayList<Movie> tempMovieList = new ArrayList<>();

        for (SearchObjectBase searchObjectBase : listOfSearchObjects){
            switch (searchObjectBase.getClass().getSimpleName()){
                case "Book":
                    tempBookList.add((Book)searchObjectBase);
                    break;

                case "music":
                    tempMusicList.add((Music)searchObjectBase);
                    break;

                case "Movie":
                    tempMovieList.add((Movie)searchObjectBase);
                    break;

            }

        }

        books = tempBookList;
        movies = tempMovieList;
        music = tempMusicList;

    }
    /*public ArrayList<SearchObjectBase> getRetrievedObjects() {
        return retrievedObjects;
    }
    */

    public ArrayList<Book> getBooks() {
        return books;
    }

    public ArrayList<Music> getMusic() {
        return music;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public long getTime() {
        return time;
    }
}
