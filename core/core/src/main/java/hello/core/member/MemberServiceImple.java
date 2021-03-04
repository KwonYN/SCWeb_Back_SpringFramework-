package hello.core.member;

public class MemberServiceImple implements MemberService {

    // 가입하고 회원 조회하려면 필요한 것!?!? ▶ MemberRepository!
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
