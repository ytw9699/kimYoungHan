package hello.itemservice.service;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Slf4j

@Service
@RequiredArgsConstructor//사실 서비스는 인터페이스 구현 잘안함 바뀔일이 없기 때문에 하지만 여기선 v1,2로 이어지기 때문
public class ItemServiceV1 implements ItemService {//단순한 위임뿐

    private final ItemRepository itemRepository;


    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        itemRepository.update(itemId, updateParam);
    }

    @Override
    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }

    @Override
    public List<Item> findItems(ItemSearchCond cond) {
        log.info("itemRepositoryclass={}",itemRepository.getClass());
        return itemRepository.findAll(cond);
    }
}
