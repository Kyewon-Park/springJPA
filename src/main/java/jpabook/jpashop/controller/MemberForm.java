package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수입니다.")
    //Member 엔티티는 최대한 화면 로직 없이 비즈니스 로직만 가지도록 단순하게 유지
    //DTO나 Form객체 사용
    private String name;

    private String city;
    private String street;
    private String zipcode;
}
