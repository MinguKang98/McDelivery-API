package mcdonald.mcdeliveryapi.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter(value = AccessLevel.PRIVATE)
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private DeliveryStatus status;

    private Address address;

    public void changeDeliveryStatus(DeliveryStatus status) {
        this.status = status;
    }

    /* 생성자 메서드 */
    public static Delivery createDelivery(DeliveryStatus status,Address address) {
        Delivery delivery = new Delivery();
        delivery.setStatus(status);
        delivery.setAddress(address);

        return delivery;
    }

}
