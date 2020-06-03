package oracle.java.omyBatis3.service;

public class Paging {

	private int currentPage = 1;
	private int rowPage = 10;
	private int pageBlock = 10;
	private int start;
	private int end;
	private int startPage;
	private int endPage;
	private int total;
	private int totalPage;
	
	public Paging(int total, String currentPage1) {
		this.total= total;
		if(currentPage1 !=null ) {
			this.currentPage = Integer.parseInt(currentPage1);
		}
		//
		start =(currentPage -1) * rowPage +1;
		end = start + rowPage -1;
		totalPage = (int)Math.ceil((double)total/rowPage);
		startPage =currentPage -(currentPage-1) % pageBlock;
		
		endPage = startPage + pageBlock -1;
		if (endPage > totalPage) {
			endPage =totalPage;
			
			
		}
				}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getRowPage() {
		return rowPage;
	}

	public int getPageBlock() {
		return pageBlock;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public int getTotal() {
		return total;
	}

	public int getTotalPage() {
		return totalPage;
	}
}
