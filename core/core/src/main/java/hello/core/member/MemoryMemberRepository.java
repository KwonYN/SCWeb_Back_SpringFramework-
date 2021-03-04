package hello.core.member;

import java.util.HashMap;
import java.util.Map;

// 원래 인터페이스와 구현체는 다른 패키지에서 만드는 것이 설계상 좋음
// 하지만 연습이니까 편의성을 위해 같은 패키지에 생성!!
// + α : 뭐가 안된다? 빨간 줄이 그어진다? → Alt + Enter
public class MemoryMemberRepository implements MemberRepository {

    // 동시성 이슈가 있을 수 있기 때문에 ConcurrentHashMap을 사용하는 것이 좋으나
    // 연습이기 때문에 편의상 걍 HashMap 사용ㅋ
    private static Map<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        // Map의 타입(?)인 member의 id와 member 객체
        // 아마 key : value 순서인 것 같음
        store.put(member.getId(),member);
    }

    @Override
    public Member findById(Long memberId) {
        // key인 member의 id로 value인 member 객체 리턴
        return store.get(memberId);
    }
}
