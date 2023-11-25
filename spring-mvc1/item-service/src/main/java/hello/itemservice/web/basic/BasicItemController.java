package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    @PostMapping("/addItemV1")
    public String addItemV1(@RequestParam String itemName,
                            @RequestParam Integer price,
                            @RequestParam int quantity,
                            Model model) {

        Item item = new Item();
             item.setItemName(itemName);
             item.setPrice(price);
             item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }

    @PostMapping("/addItemV2")
    public String addItemV2(@ModelAttribute("item") Item item, Model model) {

        itemRepository.save(item);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @PostMapping("/addItemV3")
    public String addItemV3(@ModelAttribute Item item) {
        itemRepository.save(item);
        //model.addAttribute("item", item); //자동 추가, 생략 가능 Item 가 > item으로 모델에 들어감
        return "basic/item";
    }

    @PostMapping("/addItemV4")
    public String addItemV4(Item item) {//객체의 경우 ModelAttribute가 적용되어 생략가능
        itemRepository.save(item);
        return "basic/item";
    }

    @PostMapping("/addItemV5")
    public String addItemV5(Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();//이렇게 변수를 더하는것은 url 인코딩안되는 문제 생김
        //String encodedParam = URLEncoder.encode(item.getId(), "UTF-8"); 이를 생략하는게 문제, 숫자는 문제없지만 한글일 경우 문제생김
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());//url 인코딩해주고 치환
        redirectAttributes.addAttribute("status", true);//치환안된거는 쿼리 파라미터로 자동 붙음
        return "redirect:/basic/items/{itemId}";//itemId는 치환됨
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }
}

