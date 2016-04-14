package challenge;

import java.util.ArrayList;
import java.util.Iterator;

public class PhoneNumbers {
	private final long id;
	private final int total;
	private final int page;
	private final int perPage;
	private final ArrayList<String> numbers;
	private int endIndex = 0;
	private int startIndex = 0;
	private int endPage;
	private String error = null;
	
	
	public PhoneNumbers(long id, int total, int page, int perPage) {
		this.id = id;
		this.total = total;
		this.page = page==0?1:page;
		this.perPage = perPage;
		this.endPage = total/perPage +1;
		numbers = new ArrayList<String>(perPage);
	}
	
	public PhoneNumbers(String error) {
		this.error = error;
		this.id = 0;
		this.total = 0;
		this.page = 0;
		this.perPage = 0;
		this.endPage = 0;
		numbers = new ArrayList<String>(0);
	}


	public long getId() {
		return id;
	}


	public int getTotal() {
		return total;
	}


	public int getPage() {
		return page;
	}


	public int getPerPage() {
		return perPage;
	}
	
	public void addNumber(String numberOption) {
		endIndex++;

//		if(total > 0 && (page == 1 || (endIndex/perPage > page))){
//			numbers.add(numberOption);
//		}
		if(numbers.isEmpty()) {
			startIndex = endIndex;
		}
		numbers.add(numberOption);
	}
	
	public Iterator<String> getNumberIter()
	{
		return numbers.iterator();
	}
	
	public int getStartIndex() {
		return startIndex;
	}


	public int getEndIndex() {
		return endIndex;
	}


	public int getEndPage() {
		return endPage;
	}
	
	public String getError() {
		return error;
	}


	@Override
	public String toString() {
		return 	"id"  + id +
		"\ntotal: " + total +
		"\npage: " + page +
		"\nperPage: " + perPage +
		"\nnumbers: " + numbers +
		"\nendIndex: " + endIndex + 
		"\nstartIndex: " + startIndex + 
		"\nerror: " + error;

	}
}
