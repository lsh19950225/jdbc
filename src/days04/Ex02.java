package days04;

public class Ex02 {

	public static void main(String[] args) {
		
		int totalRecord = 136;
		int currentPage = 1;
		int numberPerPage = 15;
		
		// between start and end;
		int totalpages = (int)Math.ceil( (double)totalRecord / numberPerPage );
		System.out.println("total pages : " + totalpages);
		
		int start, end;
		
		for (int i = 1; i <= totalpages; i++) {
			// i / start 1 / end 10
			// 	1			11		20
			//	2			21		30
			//	3			31		40
			// i			start : (i-1)*numberPerPage+1	/ end : s+numberPerPage-1
			
			start = (i-1)*numberPerPage + 1;
			end = start + numberPerPage - 1;
			
			if (end > totalRecord) {
				end = totalRecord;
			} // if
			
			System.out.printf("%d 페이지 : start=%d~end=%d\n",i,start,end);
			
		} // for

	} // main

} // class
