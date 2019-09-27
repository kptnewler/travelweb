package dao;

public interface FavoriteDao {
    boolean addFavoriteRoute(String rid, String uid);
    boolean deleteFavoriteRoute(String rid, String uid);
    boolean isCollectRouteByUser(String rid, String uid);
}
