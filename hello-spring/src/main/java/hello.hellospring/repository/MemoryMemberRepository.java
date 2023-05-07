package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{ // implements method all 기능으로 한 번에 implements
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L; // Long 자료형 이용

    @Override
    public Member save(Member member) { // 저장을 어디에 할 지
        member.setId(++sequence); // sequence 증감
        store.put(member.getId(), member); //map에 저장됨
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) { //store에서 꺼낸다.
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member->member.getName().equals(name))
                .findAny(); // 람다 사용
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
    public void clearStore(){
        store.clear(); //store를 싹 비워준다
    }
}
