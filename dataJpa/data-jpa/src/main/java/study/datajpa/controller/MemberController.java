package study.datajpa.controller;

import java.util.stream.IntStream;
import javax.annotation.PostConstruct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMembers(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member) {
        return member.getUsername();
    }

    @GetMapping("/members")
    public Page<MemberDto> getMembers(
            @PageableDefault(size = 5, sort = "username", direction = Sort.Direction.DESC) Pageable pageable) {
            Page<Member> page = memberRepository.findAll(pageable);
        return page.map(member -> new MemberDto(member));
    }

    @PostConstruct
    private void init() {
        IntStream.range(1, 100).forEach(i -> {
            memberRepository.save(new Member("user" + i, i));
        });
    }
}
