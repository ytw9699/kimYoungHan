package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    //싱글톤이라 static안써도 되지만 만약 ItemRepository를 new해서 계속 생성한다면 static 필요

    private static final Map<Long, Item> store = new HashMap<>(); 
    //멀티쓰레드 환경에서는 실제로 해시맵쓰면 안됨, 동시에 여러쓰레드 접근시 문제생김 그래서 concurrenthashmap써야함
    private static long sequence = 0L;//마찬가지로 long쓰면 안되고  AtomicLong 써야함

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());//store에 변화가 없게 ArrayList로 감싼다
    }

    public void update(Long itemId, Item updateParam) {
        //정석으로는 Item보단 ItemParamDto 같은걸 만들어서 id를 제외하고 파라미터3개들어간 객체만드는게 맞음
        //그래야 id의 사용여부 혼란을 줄인다. 설계상 명확하게 하는게 좋다 명확성이 중복보다 우선시
        Item findItem = findById(itemId);
             findItem.setItemName(updateParam.getItemName());
             findItem.setPrice(updateParam.getPrice());
             findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }//HashMap 데이터 날림
}
