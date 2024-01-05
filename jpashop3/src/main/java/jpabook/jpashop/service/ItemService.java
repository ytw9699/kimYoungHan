package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {//itemRepository에 단순히 위임만함. 그래서 컨트롤러에서 itemRepository에 바로 접근하는것도 문제 없음

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional//이 코드가 merger 와 거의 비슷함 하지만 merge는 비어있는 필드 데이터를 null로 바꾸는 단점이있음
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item item = itemRepository.findOne(itemId);
             item.setName(name);
             item.setPrice(price);
             item.setStockQuantity(stockQuantity);
        //사실 이렇게 set 으로 할게 아니라 의미있는 메소드로 만들어서 엔티티에 넣어야함 그래야 추적가능
        //item.change(name, price, stockQuantity);//change 만 찾으면 어디서 변경했는지 모두 추적가능!
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
