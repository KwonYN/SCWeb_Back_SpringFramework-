package hello.core.member;

public class MemberServiceImple implements MemberService {

    // 가입하고 회원 조회하려면 필요한 것!?!? ▶ MemberRepository!
    private MemberRepository memberRepository;
    // AppConfig를 통해 구현체를 대신 생성해주고 주입해줌
    // 자, 이 코드에 MemoryMemberRepository 생성 코드가 있나? 없다!!
    // → 추상화(MemberRepository)에만 의존!
    //   구현체(MemoryMemberRepository)에 의존 X

    public MemberServiceImple(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 테스트용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
