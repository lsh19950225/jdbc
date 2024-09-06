package days04;

/**
 * @author User1
 *
 */
public class Ex03 {

	public static void main(String[] args) {
		
		/*
		 * [게시글 상세보기 구현 처리과정에 대해서 설명]
		 * 1. DTO에서 구현에 필요한 변수를 선언한다.
		 * 2. DAO 인터페이스에서 리턴값은 boardDTO로 매개변수는 long seq로 선언하고
		 * 	예외처리를 떠 넘긴다.
		 * 3. 구현된 인터페이스 DAOImpl에서 매개변수를 선언한 후
		 * 	리턴값은 boardDTO로 받는다. 여기서도 예외처리는 떠 넘긴다.
		 * 4. 서비스로 넘어가 리턴값은 똑같이 boardDTO로 받고,
		 * 	안에 코딩 후 예외처리까지해서 컨트롤러로 넘어간다.
		 * 5. 컨트롤러에서 안에 빌더를 이용해 각 변수의 값을 뿌린 후,
		 * 	seq로 글번호수에 맞게 상세보기를 뿌려준다.
		 * 6. 마지막으로 최종 시작에서 db.conn 실행과 실행한다.
		 */
		
		/*
		 * [게시글 상세보기 구현 처리과정에 대해서 설명] 참고
		 * Ex01					BoardController				BoardService							BoardDAOImpl
		 * 	main() -> boardStart()
		 * 						ㄴ 메뉴출력()
		 * 						ㄴ 메뉴선택() 4(상세보기)
		 * 						ㄴ 메뉴처리()
		 * 								ㄴ 상세보기() ->		viewService(seq, 150) ->
		 * 															트랜잭션 처리
		 * 									보 글번호? 150	1) 조회수 증가
		 * 															2) 게시글 정보
		 * 
		 */

	} // main

} // class
