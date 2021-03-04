package hello.core.order;

public interface OrderService {

    // 회원 ID, 상품명, 상품가격을 넘겨주면
    // 주문 결과가 반환됨
    Order createOrder(Long memberId, String itemName, int itemPrice);
}
