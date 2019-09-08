package packCar2.packDao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import packCar2.packModel.Car;
import packCar2.packUtil.HibernateUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CarDao {
    public void printAllCars() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Car> carCriteriaQuery = criteriaBuilder.createQuery(Car.class);
            Root<Car> carRoot = carCriteriaQuery.from(Car.class);

            carCriteriaQuery.select(carRoot);

            session.createQuery(carCriteriaQuery).getResultList().forEach(System.err::println);
        }
    }

    // - odczytywanie aut wg. marki, typu nadwozia, daty produkcji
    public List<Car> getAllCarsByMark(String mark) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Car> carCriteriaQuery = criteriaBuilder.createQuery(Car.class);
            Root<Car> carRoot = carCriteriaQuery.from(Car.class);

            carCriteriaQuery.select(carRoot)
                    .where(criteriaBuilder.equal(carRoot.get("mark"), mark));

            return session.createQuery(carCriteriaQuery).getResultList();
        }
    }

    // - wyszukanie najstarszego/najmłodszego samochodu
    public List<Car> getTheOldestCar() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Car> carCriteriaQuery = criteriaBuilder.createQuery(Car.class);
            Root<Car> carRoot = carCriteriaQuery.from(Car.class);

            carCriteriaQuery.select(carRoot)
                    .orderBy(criteriaBuilder.asc(carRoot.get("productionDate")));

            return session.createQuery(carCriteriaQuery).setMaxResults(1).getResultList();
        }
    }

    // - wyszukanie samochodu o największym/najmniejszym przebiegu
    public Car getTheGreatestMileage() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Car> carCriteriaQuery = criteriaBuilder.createQuery(Car.class);
            Root<Car> carRoot = carCriteriaQuery.from(Car.class);

            carCriteriaQuery.select(carRoot)
                    .orderBy(criteriaBuilder.desc(carRoot.get("carMileage")));

            return session.createQuery(carCriteriaQuery).setMaxResults(1).getSingleResult();
        }
    }
}
