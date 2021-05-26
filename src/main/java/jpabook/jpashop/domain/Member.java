package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded//내장타입을 포함함. Embeddable이랑 둘중에 하나만 있으면 됨
    private Address address;

    @OneToMany(mappedBy = "member") //order 테이블에 있는 member필드에 의해 정리됨. //일대다 관계
    private List<Order> Orders = new ArrayList<>();
}
