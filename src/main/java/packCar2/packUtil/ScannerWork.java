package packCar2.packUtil;

import packCar2.packDao.EntityDao;
import packCar2.packModel.Car;
import packCar2.packModel.CarBodyType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ScannerWork {
    Scanner scanner = new Scanner(System.in);
    EntityDao entityDao = new EntityDao();

    public CarBodyType getCarBodyTypeABC() {
        boolean flag = false;
        char znak = 'z';
        CarBodyType carBodyType = null;
        do {
            System.out.println("Choose:\n a) SEDAN\n b) COMBI\n c) CABRIO");
            znak = scanner.nextLine().charAt(0);
            switch (znak) {
                case 'a':
                    String sedanString = "SEDAN";
                    carBodyType = CarBodyType.valueOf(sedanString);
                    flag = true;
                    break;
                case 'b':
                    String combiString = "SEDAN";
                    carBodyType = CarBodyType.valueOf(combiString);
                    flag = true;
                    break;
                case 'c':
                    String cabrioString = "SEDAN";
                    carBodyType = CarBodyType.valueOf(cabrioString);
                    flag = true;
                    break;
            }
        } while (!flag);
        return carBodyType;
    }

    public char getChar() {
        char znak = scanner.nextLine().charAt(0);
        return znak;
    }

    // - tworzenie auta
    public Car createCar() {
        Car car = new Car();

        System.out.println("Get cars mark:");
        String carsMark = scanner.nextLine();
        car.setMark(carsMark);

        System.out.println("Get cars model:");
        String carsModel = scanner.nextLine();
        car.setModel(carsModel);

        System.out.println("Choose body type:");
        CarBodyType carBodyType = getCarBodyTypeABC();
        car.setCarBodyType(carBodyType);

        System.out.println("Get date of production(yyyy-mm-dd):");
        LocalDate productionDate = LocalDate.parse(scanner.nextLine());
        car.setProductionDate(productionDate);

        System.out.println("Get cars color:");
        String carsColor = scanner.nextLine();
        car.setColor(carsColor);

        System.out.println("Get cars mileage:");
        int carsMileage = Integer.parseInt(scanner.nextLine());
        car.setCarMileage(carsMileage);

        return car;
    }

    public void printMessageB(List<Car> carList) {
        if (carList.size() > 0) {
            carList.forEach(System.err::println);
        } else {
            System.err.println("No one car in the database.");
        }
    }

    // - zmianę parametrów auta
    public Car updateCar(Long carId) throws NoSuchElementInTheDatabaseException {
        Optional<Car> optionalCar = entityDao.getById(Car.class, carId);
        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();

            boolean flag = false;
            char znak = 'z';
            do {
                System.out.println("Choose position to change:\n a) cars mark\n b) cars model\n c) cars body type" +
                        "\n d) cars date of production\n e) cars color\n f) cars mileage");
                znak = scanner.nextLine().charAt(0);
                switch (znak) {
                    case 'a':
                        System.out.println("Get cars mark:");
                        String mark = scanner.nextLine();
                        car.setMark(mark);
                        flag = true;
                        break;
                    case 'b':
                        System.out.println("Get cars model:");
                        String model = scanner.nextLine();
                        car.setModel(model);
                        flag = true;
                        break;
                    case 'c':
                        CarBodyType carBodyType = getCarBodyTypeABC();
                        car.setCarBodyType(carBodyType);
                        flag = true;
                        break;
                    case 'd':
                        System.out.println("Get date of production(yyyy-mm-dd):");
                        LocalDate productionDate = LocalDate.parse(scanner.nextLine());
                        car.setProductionDate(productionDate);
                        flag = true;
                        break;
                    case 'e':
                        System.out.println("Get cars color:");
                        String carsColor = scanner.nextLine();
                        car.setColor(carsColor);
                        flag = true;
                        break;
                    case 'f':
                        System.out.println("Get cars mileage:");
                        Long mileage = Long.valueOf(scanner.nextLine());
                        car.setCarMileage(mileage);
                        flag = true;
                        break;
                }
            } while (!flag);
            return car;
        } else {
            throw new NoSuchElementInTheDatabaseException("Ther is not any car with id: " + carId + ".");
        }
    }

    public Long getCarId() {
        System.out.println("Get car id:");
        Long id = Long.valueOf(scanner.nextLine());
        return id;
    }

    // - odczytywanie auta po id
    public Car findCarById(Long Id) throws NoSuchElementInTheDatabaseException {
        Optional<Car> optionalCar = entityDao.getById(Car.class, Id);
        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            return car;
        } else {
            throw new NoSuchElementInTheDatabaseException("Ther is not any car with id: " + Id + ".");
        }
    }

    // - usuwanie auta
    public void deleteCar(Long carId) throws NoSuchElementInTheDatabaseException {
        Optional<Car> optionalCar = entityDao.getById(Car.class, carId);
        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();

            entityDao.deleteMyObject(car);

            System.err.println("Car deleted.");
        } else {
            throw new NoSuchElementInTheDatabaseException("Ther is not any car with id: " + carId + ".");
        }
    }

    public String getCarsMark() {
        System.out.println("Get cars mark:");
        String mark = scanner.nextLine();
        return mark;
    }

    public void printMessageG(List<Car> carList) {
        if (carList.size() > 0) {
            carList.forEach(System.err::println);
        } else {
            System.err.println("There are not such cars in the database.");
        }
    }

    public void printMessageH(List<Car> carList) {
        if (carList.size() > 0) {
            carList.forEach(System.err::println);
        } else {
            System.err.println("There is not any car in the database.");
        }
    }

    public void printMessageI(Car car) {
        if (car != null) {
            System.err.println(car);
        } else {
            System.err.println("There is not any car in the database.");
        }
    }
}
