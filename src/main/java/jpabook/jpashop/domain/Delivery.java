package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) //만약에 중간에 상태가 생기면 순서 다 망가지기 때문에,
                        //꼭 Ordinal 말고 String으로 넣어야 함
    private DeliveryStatus status;
}
