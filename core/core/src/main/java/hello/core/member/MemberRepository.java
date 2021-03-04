package hello.core.member;

public interface MemberRepository {

    // 가입 기능
    void save(Member member);

    // 조회 기능
    Member findById(Long memberId);
}
