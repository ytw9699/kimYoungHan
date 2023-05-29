package hello.hellospring.controller;

import hello.hellospring.service.MemverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberoController {

    private final MemverService memverService;

    @Autowired
    public MemberoController(MemverService memverService) {
        this.memverService = memverService;
    }
}

