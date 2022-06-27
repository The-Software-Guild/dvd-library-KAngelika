package dao;

import dto.Dvd;
import java.util.List;

public interface Dao {
    Dvd addDvd(String title, Dvd dvd) throws DaoException;

    List<Dvd> getAllDvds() throws DaoException;

    Dvd getDvd(String title) throws DaoException;

    Dvd removeDvd(String title) throws DaoException;

}
