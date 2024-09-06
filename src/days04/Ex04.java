package days04;

/**
 * @author User1
 *
 */
public class Ex04 {

	public static void main(String[] args) {
		
		// 페이징블럭 : prev [1] 2 3 4 5 6 7 8 9 10 NEXT
		//					<												>
		// int currentPage = 1;
		int numberOfPageBlock = 10;
		int totalRecords = 151;
		int numberPerPage = 10;
		int totalPages = (int)Math.ceil((double)151/10); // 16
		
		int start = 1, end = 10;
		for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
			start = (currentPage - 1)/numberOfPageBlock*numberOfPageBlock + 1;
			end = start + numberOfPageBlock - 1;
			if (end>totalPages) {
				end = totalPages;
			} // if
			System.out.printf("%d\t",currentPage);
			
			if (start != 1) {
				System.out.print("prev(<) ");
			} // if
			
			for (int i = start; i <= end; i++) {
				System.out.printf(i==currentPage?"[%1$d] " : "%1$d",  i);
			} // for
			
			if (end != totalPages) {
				System.out.print(" next(>) ");
			} // if
			
			System.out.println();
			
		} // for

	} // main

} // class
