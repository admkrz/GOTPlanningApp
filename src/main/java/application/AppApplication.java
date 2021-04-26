package application;

import application.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;


/**
 * The type App application.
 */
@SpringBootApplication
@ComponentScan({"application.Models", "application.Controllers"})
@EnableJpaRepositories(basePackageClasses = {UserRepository.class, TouristRepository.class,
        TripRepository.class, PTTKWorkerRepository.class,
        PointRepository.class, BadgeRepository.class,
        MountainAreaRepository.class, MountainGroupRepository.class,
        RouteRepository.class, TripSegmentRepository.class})
public class AppApplication implements CommandLineRunner {
    /**
     * The User repository.
     */
    @Autowired
    UserRepository userRepository;

    /**
     * The Tourist repository.
     */
    @Autowired
    TouristRepository touristRepository;

    /**
     * The Trip segment repository.
     */
    @Autowired
    TripSegmentRepository tripSegmentRepository;

    /**
     * The Trip repository.
     */
    @Autowired
    TripRepository tripRepository;

    /**
     * The Pttk worker repository.
     */
    @Autowired
    PTTKWorkerRepository pttkWorkerRepository;

    /**
     * The Point repository.
     */
    @Autowired
    PointRepository pointRepository;

    /**
     * The Mountain area repository.
     */
    @Autowired
    MountainAreaRepository mountainAreaRepository;

    /**
     * The Mountain group repository.
     */
    @Autowired
    MountainGroupRepository mountainGroupRepository;

    /**
     * The Route repository.
     */
    @Autowired
    RouteRepository routeRepository;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

    /**
     * Run.
     *
     * @param args the args
     * @throws Exception the exception
     */
    @Override
    public void run(String... args) throws Exception {
        /*
        Tourist u1 = new Tourist("user1@gmail.com", "Jan", "Kowalski", "1234", 12341, "Ćwiartki 3/4", "Wrocław", "55-222");
        Tourist u2 = new Tourist("user2@gmail.com", "Jan2", "Kowalski2", "1234", 12342, "Ćwiartki 3/4", "Wrocław", "55-222");
        Tourist u3 = new Tourist("user3@gmail.com", "Jan3", "Kowalski3", "1234", 12343, "Ćwiartki 3/4", "Wrocław", "55-222");
        PTTKWorker u4 = new PTTKWorker("user4@gmail.com", "Jan4", "Kowalski4", "1234", 12344, "Ćwiartki 3/4", "Wrocław", "55-222");
        PTTKWorker u5 = new PTTKWorker("user5@gmail.com", "Jan5", "Kowalski5", "1234", 12345, "Ćwiartki 3/4", "Wrocław", "55-222");
        for (User u : new ArrayList<User>() {{
            add(u1);
            add(u2);
            add(u3);
            add(u4);
            add(u5);
        }}) {
            try {
                userRepository.save(u);
            } catch (DataIntegrityViolationException e) {
            }
        }
        try {
            touristRepository.save(u1);
        } catch (DataIntegrityViolationException e) {
        }
        try {
            touristRepository.save(u2);
        } catch (DataIntegrityViolationException e) {
        }
        try {
            touristRepository.save(u3);
        } catch (DataIntegrityViolationException e) {
        }
        try {
            pttkWorkerRepository.save(u4);
        } catch (DataIntegrityViolationException e) {
        }
        try {
            pttkWorkerRepository.save(u5);
        } catch (DataIntegrityViolationException e) {
        }


        Point p1 = new Point("Sokołowsko", PointStatus.Bare);
        Point p2 = new Point("Stożek Wielki", PointStatus.Bare);
        Point p3 = new Point("Rozdroże Pod Suchawą", PointStatus.Bare);
        Point p4 = new Point("Schronisko PTTK Andrzejówka", PointStatus.Bare);
        Point p5 = new Point("Unisław Śląski", PointStatus.Bare);
        Point p6 = new Point("Lesista Wielka", PointStatus.Bare);
        Point p7 = new Point("Mieroszów", PointStatus.Bare);

        for (Point p : new ArrayList<Point>() {{
            add(p1);
            add(p2);
            add(p3);
            add(p4);
            add(p5);
            add(p6);
            add(p7);
        }}) {
            try {
                pointRepository.save(p);
            } catch (DataIntegrityViolationException e) {
            }
        }

        MountainArea ma1 = new MountainArea("Sudety");
        try {
            mountainAreaRepository.save(ma1);
        } catch (DataIntegrityViolationException e) {
        }

        MountainGroup mg1 = new MountainGroup("S.09", "Góry Kamienne", ma1);
        try {
            mountainGroupRepository.save(mg1);
        } catch (DataIntegrityViolationException e) {
        }

        Route r1 = new Route(3.2, 256d, 3, "żółty", 31d, 6, mg1, p1, p4);
        Route r2 = new Route(3.6, 429d, 4, "czarny/niebieski", 137d, 7, mg1, p1, p3);
        Route r3 = new Route(2.4, 286d, 2, "żółty", 32d, 5, mg1, p1, p2);
        Route r4 = new Route(2.2, 285d, 1, "żółty", 22d, 4, mg1, p2, p5);
        Route r5 = new Route(4.7, 357d, 5, "żółty", 8d, 9, mg1, p7, p6);
        Route r6 = new Route(2.8, 333d, 4, "fioletowy", 67d, 9, mg1, p1, p3);
        r6.setDisablementTime(LocalDateTime.now());
        for (Route r : new ArrayList<Route>() {{
            add(r1);
            add(r2);
            add(r3);
            add(r4);
            add(r5);
            add(r6);
        }}) {
            try {
                routeRepository.save(r);
            } catch (DataIntegrityViolationException e) {
            }
        }
*/
/*
            Trip t1 = new Trip(9200.0D, 971.0D, 18,
                    LocalDate.now().plusDays(2), LocalDate.now().plusDays(5),
                    TripStatus.Planned, u1);
            tripRepository.save(t1);
*/
//        TripSegment s1 = new TripSegment(true, 6, 3200.0, 256.0, r1, t1);
//        TripSegment s2 = new TripSegment(true, 7, 3600.0, 429.0, r2, t1);
//        TripSegment s3 = new TripSegment(true, 5, 2400.0, 286.0, r3, t1);
//
//        tripSegmentRepository.save(s1);
//        tripSegmentRepository.save(s2);
//        tripSegmentRepository.save(s3);
//
//        try {
//            Trip t2 = new Trip(2500.0D, 300.0D, 6,
//                    LocalDate.now().plusDays(1), LocalDate.now().plusDays(3),
//                    TripStatus.Planned, u2, new ArrayList<>(), new ArrayList<>());
//            tripRepository.save(t2);
//        } catch (DataIntegrityViolationException e) {
//        }
//        for (int i = 0; i < 10; i++) {
//            try {
//                Trip t3 = new Trip(i * 1000.0D, i * 100.0D, i * 2,
//                        LocalDate.now().minusDays(25 - i), LocalDate.now().minusDays(20 - i),
//                        TripStatus.Planned, u1, new ArrayList<>() {{
//                    add(u2);
//                }}, new ArrayList<>());
//                tripRepository.save(t3);
//            } catch (DataIntegrityViolationException ignored) {
//            }
//        }


    }
}
