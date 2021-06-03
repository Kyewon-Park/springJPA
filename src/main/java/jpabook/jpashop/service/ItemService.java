package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
//@Transactional이 테스트 코드에서 사용되는 경우에 한정해서
// 기본으로 롤백이 됨.
//지금처럼 서비스에 있는 경우에는
// 로직이 정상 수행되면 커밋되고,
// 만약에 서비스 로직 안에서 예외가 발생하면 롤백됨.


@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional //readOnly false
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
