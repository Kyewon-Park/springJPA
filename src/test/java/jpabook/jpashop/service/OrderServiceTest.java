package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember("회원1", new Address("서울", "중구", "15151"));

        Book book = createBook(10000, 10, "JPA");

        int orderCount=2;
        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order order = orderRepository.findOne(orderId);
        assertEquals(order.getOrderStatus(), OrderStatus.ORDER,  "상품주문시 상태는 ORDER");
        assertEquals(order.getOrderItems().size(), 1, "주문한 상품 종류 수가 정확해야 한다");
        assertEquals(10000* orderCount, order.getTotalPrice(), "주문가격은 가격 * 수량이다");
        assertEquals(8, book.getStockQuantity(), "주문하면 재고 줄어야 함");
    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember("회원1", new Address("서울", "중구", "15151"));
        Book book = createBook(10000, 10, "JPA");
        int orderCount = 2;
        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        orderService.cancelOrder(orderId);
        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.CANCEL, getOrder.getOrderStatus(), "주문취소시 상태는 Cancel이다.");
        assertEquals(10, book.getStockQuantity(), "주문취소시 상품은 재고가 증가해야 한다.");
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = createMember("회원1", new Address("서울", "중구", "15151"));
        Book book = createBook(10000, 10, "JPA");

        int orderCount= 11;

        //when

        //then
        assertThrows(
                NotEnoughStockException.class
                ,()->orderService.order(member.getId(),book.getId(),orderCount)
                ,"재고수량이 부족하면 예외 발생한다."
        );

    }

    private Book createBook(int price, int stockQuantity, String name) {
        Book book = new Book();
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        book.setName(name);
        em.persist(book);
        return book;
    }
    //ctrl alt P 파라미터 새로 생성
    private Member createMember(String name, Address address) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(address);
        em.persist(member);
        return member;
    }


}