package packCar2.packModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.UpdateTimestamp;
import packCar2.packUtil.IBaseEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car implements IBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String mark;

    @Column(nullable = false)
    private String model;

    @Enumerated(value = EnumType.STRING)
    private CarBodyType carBodyType;

    @Column(nullable = false)
    private LocalDate productionDate;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private long carMileage; // przebieg

    @UpdateTimestamp
    private LocalDateTime modifiedDate;

    @Formula(value = "(year(now()) - year(productionDate))")
    private int age;
}
