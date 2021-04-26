package application;

import application.Controllers.TripSegmentsList;
import application.Controllers.TripUtils;
import application.Models.*;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AppApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TouristRepository touristRepositoryMock;

    @MockBean
    private TripRepository tripRepositoryMock;

    @MockBean
    private RouteRepository routeRepositoryMock;

    @MockBean
    private TripSegmentRepository tripSegmentRepositoryMock;

    @Test
    void contextLoads() { }

    @Test
    void shouldLoadTouristTripsAndRenderTripsView() throws Exception {
        int touristId = 1;
        Tourist t1 = new Tourist("user1@gmail.com", "Jan", "Kowalski", "1234",
                12341, "Ćwiartki 3/4", "Wrocław", "55-222");
        t1.setId(touristId);
        Trip tr1 = new Trip(2000.0D, 300.0D, 5,
                LocalDate.now().plusDays(2), LocalDate.now().plusDays(5),
                TripStatus.Planned, t1, new ArrayList<>(), new ArrayList<>());
        t1.setPlannedTrips(Arrays.asList(tr1));
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", touristId);
        when(touristRepositoryMock.findById(touristId)).thenReturn(Optional.of(t1));
        MockHttpServletRequestBuilder builder = get("/user/trips").session(session);
        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(view().name("trips"))
                .andExpect(model().attribute("allMyTrips", hasSize(1)));
        verify(touristRepositoryMock, times(1)).findById(touristId);
    }

    @Test
    void shouldNotLoadTouristAndRedirect() throws Exception {
        int touristId = 1;
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", touristId);
        when(touristRepositoryMock.findById(touristId)).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder builder = get("/user/trips").session(session);
        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login?error"))
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(model().attributeDoesNotExist("allMyTrips"));
        verify(touristRepositoryMock, times(1)).findById(touristId);
    }

    @Test
    void shouldLoadTouristWithNoTripsAndRenderTripsView() throws Exception {
        int touristId = 1;
        Tourist t1 = new Tourist("user1@gmail.com", "Jan", "Kowalski", "1234", 12341, "Ćwiartki 3/4", "Wrocław", "55-222");
        t1.setId(touristId);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", touristId);
        when(touristRepositoryMock.findById(touristId)).thenReturn(Optional.of(t1));
        MockHttpServletRequestBuilder builder = get("/user/trips").session(session);
        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/?noTrips"))
                .andExpect(redirectedUrl("/?noTrips"))
                .andExpect(model().attributeDoesNotExist("allMyTrips"))
                .andExpect(model().attributeDoesNotExist("noTrips"));

        verify(touristRepositoryMock, times(1)).findById(touristId);
    }

    @Test
    void shouldGetCurrentTripFromSessionId()
    {
        int tripId = 1;
        Tourist t1 = new Tourist("user1@gmail.com", "Jan", "Kowalski", "1234", 12341, "Ćwiartki 3/4", "Wrocław", "55-222");
        t1.setId(1);
        Trip tr1 = new Trip(2000.0D, 300.0D, 5,
                LocalDate.now().plusDays(2), LocalDate.now().plusDays(5),
                TripStatus.Planned, t1, new ArrayList<>(), new ArrayList<>());
        t1.setPlannedTrips(Arrays.asList(tr1));
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("tripId", tripId);
        when(tripRepositoryMock.findById(tripId)).thenReturn(Optional.of(tr1));

        Trip trip = TripUtils.getCurrentTrip(session, tripRepositoryMock);
        assert(trip.equals(tr1));
    }

    @Test
    void shouldCancelTrip() throws Exception {
        int touristId = 1;
        int tripId = 1;
        Tourist t1 = new Tourist("user1@gmail.com", "Jan", "Kowalski", "1234", 12341, "Ćwiartki 3/4", "Wrocław", "55-222");
        t1.setId(touristId);
        Trip tr1 = new Trip(2000.0D, 300.0D, 5,
                LocalDate.now().plusDays(2), LocalDate.now().plusDays(5),
                TripStatus.Planned, t1, new ArrayList<>(), new ArrayList<>());
        tr1.setId(tripId);
        t1.setPlannedTrips(Arrays.asList(tr1));
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("tripId", tripId);
        when(tripRepositoryMock.findById(tripId)).thenReturn(Optional.of(tr1));
        doAnswer((Answer<Void>) invocation -> {
            tr1.setStatus(TripStatus.NotDone);
            return null;
        }).when(tripRepositoryMock).updateStatus(tripId,TripStatus.NotDone);
        MockHttpServletRequestBuilder builder = get("/user/trip/cancel/{id}", tripId).session(session);
        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(view().name("singleTrip"))
                .andExpect(model().attribute("tripCanceled", is(true)))
                .andExpect(model().attributeExists("trip"));
        assert(tr1.getStatus() == TripStatus.NotDone);
        verify(tripRepositoryMock, times(1)).findById(tripId);

    }


    /**
     * Unit tests - Adam Krzeminski
     *
     * Test Route Controller
     */
    @Test
    void shouldShowAllRoutes() throws Exception {
        List<Route> routes = testRoutes();
        when(routeRepositoryMock.findAll()).thenReturn(routes);
        routes.remove(routes.size()-1);
        mockMvc.perform(get("/routes"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("routes",routes))
                .andExpect(model().attribute("disabled",false))
                .andExpect(model().attribute("selectedMountainGroup","empty"))
                .andExpect(model().attribute("minPoints",0))
                .andExpect(model().attribute("maxPoints",20))
                .andExpect(model().attribute("minLength",0.))
                .andExpect(model().attribute("maxLength",20.))
                .andExpect(model().attribute("minHeight",0))
                .andExpect(model().attribute("maxHeight",1000));
        verify(routeRepositoryMock, times(1)).findAll();
    }

    @Test
    void shouldShowDisabledRoutes() throws Exception {
        List<Route> routes = testRoutes();
        when(routeRepositoryMock.findAll()).thenReturn(routes);
        List<Route> expectedRoutes = new ArrayList<>();
        expectedRoutes.add(routes.get(routes.size()-1));
        mockMvc.perform(get("/routes").param("disabled", String.valueOf(true)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("routes",expectedRoutes))
                .andExpect(model().attribute("disabled",true))
                .andExpect(model().attribute("selectedMountainGroup","empty"))
                .andExpect(model().attribute("minPoints",0))
                .andExpect(model().attribute("maxPoints",20))
                .andExpect(model().attribute("minLength",0.))
                .andExpect(model().attribute("maxLength",20.))
                .andExpect(model().attribute("minHeight",0))
                .andExpect(model().attribute("maxHeight",1000));
        verify(routeRepositoryMock, times(1)).findAll();
    }

    @Test
    void shouldShowNoRoutes() throws Exception{
        List<Route> routes = testRoutes();
        when(routeRepositoryMock.findAll()).thenReturn(routes);
        mockMvc.perform(get("/routes").param("maxPoints", String.valueOf(0)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("routes",new ArrayList<>()))
                .andExpect(model().attribute("disabled",false))
                .andExpect(model().attribute("selectedMountainGroup","empty"))
                .andExpect(model().attribute("minPoints",0))
                .andExpect(model().attribute("maxPoints",0))
                .andExpect(model().attribute("minLength",0.))
                .andExpect(model().attribute("maxLength",20.))
                .andExpect(model().attribute("minHeight",0))
                .andExpect(model().attribute("maxHeight",1000))
                .andExpect(model().attribute("routesNotFound",true));
        verify(routeRepositoryMock, times(1)).findAll();
    }

    @Test
    void shouldShowSomeRoutes() throws Exception{
        List<Route> routes = testRoutes();
        when(routeRepositoryMock.findAll()).thenReturn(routes);
        List<Route> expectedRoutes = new ArrayList<>();
        expectedRoutes.add(routes.get(0));
        expectedRoutes.add(routes.get(1));
        expectedRoutes.add(routes.get(4));
        mockMvc.perform(get("/routes").param("minLength", String.valueOf(3.)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("routes",expectedRoutes))
                .andExpect(model().attribute("disabled",false))
                .andExpect(model().attribute("selectedMountainGroup","empty"))
                .andExpect(model().attribute("minPoints",0))
                .andExpect(model().attribute("maxPoints",20))
                .andExpect(model().attribute("minLength",3.))
                .andExpect(model().attribute("maxLength",20.))
                .andExpect(model().attribute("minHeight",0))
                .andExpect(model().attribute("maxHeight",1000))
                .andExpect(model().attribute("routesNotFound",false));
        verify(routeRepositoryMock, times(1)).findAll();
    }

    @Test
    void shouldShowSomeRoutesAllFilters() throws Exception{
        List<Route> routes = testRoutes();
        when(routeRepositoryMock.findAll()).thenReturn(routes);
        List<Route> expectedRoutes = new ArrayList<>();
        expectedRoutes.add(routes.get(1));
        expectedRoutes.add(routes.get(2));
        expectedRoutes.add(routes.get(3));
        mockMvc.perform(get("/routes").param("maxPoints", String.valueOf(7)).param("mountainGroup", "S.09").param("minHeight", String.valueOf(280)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("routes",expectedRoutes))
                .andExpect(model().attribute("disabled",false))
                .andExpect(model().attribute("selectedMountainGroup","S.09"))
                .andExpect(model().attribute("minPoints",0))
                .andExpect(model().attribute("maxPoints",7))
                .andExpect(model().attribute("minLength",0.))
                .andExpect(model().attribute("maxLength",20.))
                .andExpect(model().attribute("minHeight",280))
                .andExpect(model().attribute("maxHeight",1000))
                .andExpect(model().attribute("routesNotFound",false));
        verify(routeRepositoryMock, times(1)).findAll();
    }

    private List<Route> testRoutes(){
        List<Route> routes = new ArrayList<>();
        Point p1 = new Point("Sokołowsko", PointStatus.Bare);
        Point p2 = new Point("Stożek Wielki", PointStatus.Bare);
        Point p3 = new Point("Rozdroże Pod Suchawą", PointStatus.Bare);
        Point p4 = new Point("Schronisko PTTK Andrzejówka", PointStatus.Bare);
        Point p5 = new Point("Unisław Śląski", PointStatus.Bare);
        Point p6 = new Point("Lesista Wielka", PointStatus.Bare);
        Point p7 = new Point("Mieroszów", PointStatus.Bare);

        MountainArea ma1 = new MountainArea("Sudety");
        MountainGroup mg1 = new MountainGroup("S.09", "Góry Kamienne", ma1);

        Route r1 = new Route(3.2, 256d, 3, "żółty", 31d, 6, mg1, p1, p4);
        Route r2 = new Route(3.6, 429d, 4, "czarny/niebieski", 137d, 7, mg1, p1, p3);
        Route r3 = new Route(2.4, 286d, 2, "żółty", 32d, 5, mg1, p1, p2);
        Route r4 = new Route(2.2, 285d, 1, "żółty", 22d, 4, mg1, p2, p5);
        Route r5 = new Route(4.7, 357d, 5, "żółty", 8d, 9, mg1, p7, p6);
        Route r6 = new Route(1d, 3d, 5, "fioletowy", 8d, 9, mg1, p1, p3);
        r6.setDisablementTime(LocalDateTime.now());
        routes.add(r1);
        routes.add(r2);
        routes.add(r3);
        routes.add(r4);
        routes.add(r5);
        routes.add(r6);
        return routes;
    }


    /**
     * Tests Plan Trip Controller
     */
    @Test
    void shouldShowEmptyTrip() throws Exception{
        List<Route> routes = testRoutes();
        when(routeRepositoryMock.findAll()).thenReturn(routes);

        int touristId = 1;
        Tourist t1 = new Tourist("user1@gmail.com", "Jan", "Kowalski", "1234",
                        12341, "Ćwiartki 3/4", "Wrocław", "55-222");
        t1.setId(touristId);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", touristId);
        when(touristRepositoryMock.findById(touristId)).thenReturn(Optional.of(t1));

        routes.remove(5);

        mockMvc.perform(get("/planTrip").session(session))
                .andExpect(status().isOk())
                .andExpect(model().attribute("tripSegmentsObjects",new ArrayList<TripSegment>()))
                .andExpect(model().attribute("possibleSegments",routes))
                .andExpect(model().attribute("possibleSegmentsReverse",routes))
                .andExpect(model().attribute("points",0))
                .andExpect(model().attribute("length",0.))
                .andExpect(model().attribute("height",0))
                .andExpect(model().attribute("lastPoint",""));

        verify(routeRepositoryMock, times(1)).findAll();
    }

    @Test
    void shouldShowTripWithSegments() throws Exception{
        List<Route> routes = testRoutes();
        when(routeRepositoryMock.findAll()).thenReturn(routes);

        int touristId = 1;
        Tourist t1 = new Tourist("user1@gmail.com", "Jan", "Kowalski", "1234", 12341, "Ćwiartki 3/4", "Wrocław", "55-222");
        t1.setId(touristId);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", touristId);
        when(touristRepositoryMock.findById(touristId)).thenReturn(Optional.of(t1));

        int tripSegmentId=1;
        TripSegment ts1 = new TripSegment(true,6,3.2,256.,routes.get(0),null);

        when(tripSegmentRepositoryMock.findById(tripSegmentId)).thenReturn(Optional.of(ts1));

        TripSegmentsList tripSegmentsList = new TripSegmentsList(new ArrayList<>());
        tripSegmentsList.getTripSegments().add(String.valueOf(tripSegmentId));

        List<TripSegment> tripSegmentsObjects = new ArrayList<TripSegment>();
        tripSegmentsObjects.add(ts1);

        List<Route> possibleRoutes = new ArrayList<>();
        possibleRoutes.add(routes.get(0));

        mockMvc.perform(get("/planTrip").session(session).flashAttr("tripSegments",tripSegmentsList))
                .andExpect(status().isOk())
                .andExpect(model().attribute("tripSegmentsObjects",tripSegmentsObjects))
                .andExpect(model().attribute("possibleSegments",new ArrayList<>()))
                .andExpect(model().attribute("possibleSegmentsReverse",possibleRoutes))
                .andExpect(model().attribute("points",6))
                .andExpect(model().attribute("length",3.2))
                .andExpect(model().attribute("height",256))
                .andExpect(model().attribute("lastPoint","Schronisko PTTK Andrzejówka"));

        verify(routeRepositoryMock, times(1)).findAll();
    }


    @Test
    void shouldShowTripWithSegmentsAfterRemoval() throws Exception{
        List<Route> routes = testRoutes();
        when(routeRepositoryMock.findAll()).thenReturn(routes);

        int touristId = 1;
        Tourist t1 = new Tourist("user1@gmail.com", "Jan", "Kowalski", "1234", 12341, "Ćwiartki 3/4", "Wrocław", "55-222");
        t1.setId(touristId);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", touristId);
        when(touristRepositoryMock.findById(touristId)).thenReturn(Optional.of(t1));

        int tripSegmentId=1;
        TripSegment ts1 = new TripSegment(true,6,3.2,256.,routes.get(0),null);

        when(tripSegmentRepositoryMock.findById(tripSegmentId)).thenReturn(Optional.of(ts1));

        TripSegmentsList tripSegmentsList = new TripSegmentsList(new ArrayList<>());
        tripSegmentsList.getTripSegments().add(String.valueOf(tripSegmentId));

        List<TripSegment> tripSegmentsObjects = new ArrayList<>();
        tripSegmentsObjects.add(ts1);

        List<Route> possibleRoutes = new ArrayList<>();
        possibleRoutes.add(routes.get(0));

        mockMvc.perform(get("/planTrip").session(session).flashAttr("tripSegments",tripSegmentsList).flashAttr("removed", true).flashAttr("removedText", "Usunięto odcinek "))
                .andExpect(status().isOk())
                .andExpect(model().attribute("tripSegmentsObjects",tripSegmentsObjects))
                .andExpect(model().attribute("possibleSegments",new ArrayList<>()))
                .andExpect(model().attribute("possibleSegmentsReverse",possibleRoutes))
                .andExpect(model().attribute("points",6))
                .andExpect(model().attribute("length",3.2))
                .andExpect(model().attribute("height",256))
                .andExpect(model().attribute("lastPoint","Schronisko PTTK Andrzejówka"))
                .andExpect(model().attribute("removed",true))
                .andExpect(model().attribute("removedText","Usunięto odcinek "));

        verify(routeRepositoryMock, times(1)).findAll();
    }
}