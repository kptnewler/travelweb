package dao.impl;

import dao.FavoriteDao;
import org.junit.Test;

public class FavoriteDaoImplTest {
    private FavoriteDao dao = new FavoriteDaoImpl();
    @Test
    public void addFavoriteRoute() {
    }

    @Test
    public void isCollectRouteByUser() {
        System.out.println(dao.isCollectRouteByUser("10", "1"));
    }

    @Test
    public void deleteFavoriteRoute() {
    }
}
