package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id") //테이블명_컬럼명 선호
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    //양방향이어서 연관관계 주인을 정해줘야 하는데
    //객체의 변경포인트가 2군데인데,
    //테이블은 FK하나만 변경하면 되기 때문에, 둘중에 하나를 주인이란 개념으로 잡음.
    //연관관계 주인 설정:FK와 가까운것으로
    //주인은 이대로 둠
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
