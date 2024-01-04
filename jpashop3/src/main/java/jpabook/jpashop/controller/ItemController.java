package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form) {

        Book book = new Book();//안좋은 설계 나중에 바꿔야함 따로 메소드로 빼야한다.
             book.setName(form.getName());
             book.setPrice(form.getPrice());
             book.setStockQuantity(form.getStockQuantity());
             book.setAuthor(form.getAuthor());
             book.setIsbn(form.getIsbn());

        itemService.saveItem(book);

        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {

        Book item = (Book) itemService.findOne(itemId);//심플하게 책만 일단 가져와서 수정한다. 그래서 캐스팅함

        BookForm form = new BookForm();
                 form.setId(item.getId());
                 form.setName(item.getName());
                 form.setPrice(item.getPrice());
                 form.setStockQuantity(item.getStockQuantity());
                 form.setAuthor(item.getAuthor());
                 form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);
        
        return "items/updateItemForm";
    }

    @PostMapping("items/{itemId}/edit")//아이템 id조작할수있기때문에, 서버단에서 이 유저가 작성한건지 확인해야함
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") BookForm form) {

        Book book = new Book();
             book.setId(form.getId());
             book.setName(form.getName());
             book.setPrice(form.getPrice());
             book.setStockQuantity(form.getStockQuantity());
             book.setAuthor(form.getAuthor());
             book.setIsbn(form.getIsbn());

        itemService.saveItem(book);

        return "redirect:/items";
    }
}





