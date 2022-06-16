package jspstudy.domain;

public class PageMaker {

	private int totalCount;		//전체 데이터 개수
	private int startPage;		//첫번째 번호
	private int endPage;		//네비게이션 마지막 번호
	private boolean prev;		//이전 버튼
	private boolean next;		//다음 버튼
	private int displayPageNum = 10;
	//private Criteria cri;
	private SearchCriteria scri;
	
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcData();
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public boolean isPrev() {
		return prev;
	}
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	public int getDisplayPageNum() {
		return displayPageNum;
	}
	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}
	
	public SearchCriteria getScri() {
		return scri;
	}
	public void setScri(SearchCriteria scri) {
		this.scri = scri;
	}
	//시작페이지 끝페이지 이전 다음 버튼 값 생성 메소드
	public void calcData() {
		
		//페이지 1~ 9번까지 보여주고 있을 때 끝점이 10이 된다. 10부터 20
		endPage = (int)(Math.ceil(scri.getPage()/(double)displayPageNum) * displayPageNum);
		
		startPage = (endPage - displayPageNum) + 1;
		
		//실제 나오는 페이지 개수
		int tempEndPage = (int)(Math.ceil(totalCount/(double)scri.getPerPageNum()));
		
		if(endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		
		prev = startPage == 1 ? false : true;
		next = endPage*scri.getPerPageNum() >= totalCount ? false : true;
	}
}