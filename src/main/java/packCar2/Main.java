package packCar2;

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

import packCar2.packDao.CarDao;
import packCar2.packDao.EntityDao;
import packCar2.packModel.Car;
import packCar2.packUtil.NoSuchElementInTheDatabaseException;
import packCar2.packUtil.ScannerWork;

import java.util.List;

public class Main {
    public static void main(final String[] args) {
        System.out.println();

        ScannerWork scannerWork = new ScannerWork();
        EntityDao entityDao = new EntityDao();
        CarDao carDao = new CarDao();


        boolean flag = false;
        char znak = 'z';
        do {
            System.out.println();
            System.out.println("Choose:\n a) create car\n b) print all cars(entityDao)\n c) print all cars(carDao)" +
                    "\n d) update car\n e) find the car by id\n f) delete car\n g) print all cars by mark" +
                    "\n h) get the oldest car\n i) get the car with thr greatest mileage\n q) quit");
            znak = scannerWork.getChar();

            switch (znak) {
                case 'a':
                    Car carA = scannerWork.createCar();
                    entityDao.saveOrUpdate(carA);
                    System.err.println("Car added.");
                    break;
                case 'b':
                    List<Car> carList = entityDao.getAll(Car.class);
                    scannerWork.printMessageB(carList);
                    break;
                case 'c':
                    carDao.printAllCars();
                    break;
                case 'd':
                    Long idD = scannerWork.getCarId();
                    try {
                        Car carD = scannerWork.updateCar(idD);
                        entityDao.saveOrUpdate(carD);
                        System.err.println("Car updated.");
                    } catch (NoSuchElementInTheDatabaseException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 'e':
                    Long idE = scannerWork.getCarId();
                    try {
                        Car carE = scannerWork.findCarById(idE);
                        System.err.println(carE);
                    } catch (NoSuchElementInTheDatabaseException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 'f':
                    Long idF = scannerWork.getCarId();
                    try {
                        scannerWork.deleteCar(idF);
                    } catch (NoSuchElementInTheDatabaseException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'g':
                    String mark = scannerWork.getCarsMark();
                    List<Car> carListG = carDao.getAllCarsByMark(mark);
                    scannerWork.printMessageG(carListG);
                    break;
                case 'h':
                    List<Car> carListH = carDao.getTheOldestCar();
                    scannerWork.printMessageH(carListH);
                    break;
                case 'i':
                    Car car = carDao.getTheGreatestMileage();
                    scannerWork.printMessageI(car);
                    break;
                case 'q':
                    flag = true;
                    break;
            }
        } while (!flag);
    }
}