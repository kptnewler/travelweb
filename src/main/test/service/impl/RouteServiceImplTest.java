package service.impl;

import org.junit.Before;
import org.junit.Test;
import service.RouteService;

import java.sql.SQLException;

public class RouteServiceImplTest {
    private RouteService routeService;
    @Before
    public void init() {
        routeService = new RouteServiceImpl();
    }
    @Test
    public void collectRoute() throws SQLException {
        routeService.collectRoute("1", "10");
    }
}
