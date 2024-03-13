package hello.itemservice.repository.memory;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MemoryItemRepository implements ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>(); //static
    private static long sequence = 0L; //static

    @Override
    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        Item findItem = findById(itemId).orElseThrow();
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    @Override
    public Optional<Item> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {

        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();

        List<Item> collect = store.values().stream()
                .filter(item -> {
                    if (ObjectUtils.isEmpty(itemName)) {//검색조건에 아이템 네임을 안적으면
                        //문자의 경우 null 조건도 있지만, 빈 문자( "" )의 경우에도 잘 동작하는지 검증한다.
                        return true; //검색안함
                    }
                    return item.getItemName().contains(itemName);//검색 조건 있으면 이경우만 통과
                }).filter(item -> {//그다음 필터 이동
                    if (maxPrice == null) {//최대 가격을 안적으면
                        return true;//데이터 다가지고옴
                    }
                    return item.getPrice() <= maxPrice;// 최대가격보다 작은것만
                })
                .collect(Collectors.toList());//모아서 리스트 반환

        return collect;
    }

    public void clearStore() {//테스트 용도
        store.clear();
    }

}
