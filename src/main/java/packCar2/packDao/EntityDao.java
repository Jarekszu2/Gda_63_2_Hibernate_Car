package packCar2.packDao;

/*
Zadanie 1
I) Zaprojektuj encję Car modelującą samochód. Samochód powinien posiadać:
- unikalny identyfikator (int id)
- markę (String brand)
- model (String model)
- typ nadwozia (CarBodyType carBodyType) będący enumem (np. SEDAN, COMBI, CABRIO);
przechowywanym w baize jako String
- data produkcji (LocalDate productionDate)
- kolor (String color)
- ilość przejechanych kilometrów (long km)
- datę modyfikacji (LocalDateTime modifiedDate) (@UpdateTimestamp)
- int age; – pole to ma być automatycznie ustawiane
#
# Formula przyjmuje w cudzysłowiu kod SQL (różny w zależności od tego jakiej bazy używamy - np. dla mongo musi to być zapytanie mongo)
II) Zaimplementuj klasę CarDAO umożliwiającą:
- tworzenie auta
- odczytywanie auta po id
- zmianę parametrów auta
- usuwanie auta
- odczytywanie wszystkich aut (zaproponuj kilka sortowań)
- odczytywanie aut wg. marki, typu nadwozia, daty produkcji
- wyszukanie najstarszego/najmłodszego samochodu
- wyszukanie samochodu o największym/najmniejszym przebiegu
 */

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import packCar2.packUtil.HibernateUtil;
import packCar2.packUtil.IBaseEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EntityDao {

    public <T extends IBaseEntity> void saveOrUpdate(T entity) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            session.saveOrUpdate(entity);

            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public <T extends IBaseEntity> List<T> getAll(Class<T> classT) {
        List<T> list = new ArrayList<>();
        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {

            // Narzędzie do kreowania zapytania, do tworzenia query i budowania klauzuli 'where'
            CriteriaBuilder cb = session.getCriteriaBuilder();

            // Obiekt reprezentujący zapytanie o <typ generyczny>
            CriteriaQuery<T> criteriaQuery = cb.createQuery(classT);

            // reprezentuje tabelę 'Student' i tworzymy tą instancję żeby powiedzieć
            // "do jakiej tabeli chcemy wykonać zapytanie"
            Root<T> rootTable = criteriaQuery.from(classT);

            // wykonanie select'a z tabeli
            criteriaQuery.select(rootTable);

            // wywołujemy zapytanie, wyniki zbieramy do listy
            list.addAll(session.createQuery(criteriaQuery).list());
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list;
    }


    public <T extends IBaseEntity> Optional<T> getById(Class<T> classT, Long id) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {

            T entity = session.get(classT, id);

            return Optional.ofNullable(entity);
        }
    }

    // delete
    public <T extends IBaseEntity> void delete(Class<T> classT, Long id) {
        Optional<T> optionalEntity = getById(classT, id);

        if (optionalEntity.isPresent()) {
            deleteMyObject(optionalEntity.get());
        } else {
            System.err.println("Nie udało się odnaleźć instancji");
        }
    }

    public <T extends IBaseEntity> void deleteMyObject(T entity) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();

            session.delete(entity);

            transaction.commit();
        }
    }
}
