package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PagingVO<T> implements Serializable{
	
	public PagingVO(int screenSize, int blockSize) {
		super();
		this.screenSize = screenSize;
		this.blockSize = blockSize;
	}

	private int totalRecord;
	private int currentPage;
	private int screenSize=10;
	private int blockSize=5;
	
	private int totalPage;
	private int startRow;
	private int endRow;
	private int startPage;
	private int endPage;
	
	private SearchVO searchVO;
	
	private List<T> data;
	
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		totalPage = (totalRecord+(screenSize-1))/screenSize;
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		endRow = currentPage * screenSize;
		startRow = endRow - (screenSize - 1);
		
		endPage = (currentPage+(blockSize-1))/blockSize*blockSize;
		startPage = endPage - (blockSize-1);
	}
	
	private final String PATTERN = "<a href='#' data-page='%d' class='%s'>%s</a>";
	public String getPagingHTML() {
		StringBuffer html = new StringBuffer();
		endPage = totalPage<endPage?totalPage:endPage;
		if(startPage>blockSize) {
			html.append(String.format(PATTERN, (startPage-blockSize), "previous", "이전"));
		}
		for(int page=startPage; page<=endPage; page++) {
			if(currentPage==page) {
				html.append(String.format(PATTERN, page, "current", page));
			}else {
				html.append(String.format(PATTERN, page, "", page));
			}
		}
		if(endPage<totalPage) {
			html.append(String.format(PATTERN, (endPage+1), "next", "다음"));
		}
		return html.toString();
	}
	
//	<nav aria-label="...">
//	  <ul class="pagination">
//	    <li class="page-item disabled">
//	      <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Previous</a>
//	    </li>
//	    <li class="page-item"><a class="page-link" href="#">1</a></li>
//	    <li class="page-item active" aria-current="page">
//	      <a class="page-link" href="#">2 <span class="sr-only">(current)</span></a>
//	    </li>
//	    <li class="page-item"><a class="page-link" href="#">3</a></li>
//	    <li class="page-item">
//	      <a class="page-link" href="#">Next</a>
//	    </li>
//	  </ul>
//	</nav>
	
	private final String BT_PATTERN = 
	    "<li class='page-item %s' %s>"
	    +"<a class='page-link' href='#' %s>%s</a>"
	    +"</li>";
	private final String DATAPAGE = "data-page='%d'";
	public String getPagingHTML_BS() {
		StringBuffer html = new StringBuffer();
		html.append("<nav aria-label='Page navigation'>");
		html.append("<ul class='pagination'>");
		String liClass = startPage>blockSize?"":"disabled";
		String liAddAttr = "";
		String aAddAttr = startPage>blockSize?String.format(DATAPAGE, (startPage-blockSize))
												:"tabindex='-1' aria-disabled='true'";
		String aText = "Previous";
		html.append(String.format(BT_PATTERN, liClass, liAddAttr, aAddAttr, aText));
		endPage = totalPage<endPage?totalPage:endPage;
		for(int page = startPage; page <= endPage; page++) {
			liClass = page==currentPage?"active":"";
			liAddAttr = page==currentPage?"aria-current='page'":"";
			aAddAttr = String.format(DATAPAGE, page);
			aText = page==currentPage?page+"<span class='sr-only'>(current)</span>":(page+"");
			html.append(String.format(BT_PATTERN, liClass, liAddAttr, aAddAttr, aText));
		}
		liClass = endPage<totalPage?"":"disabled";
		liAddAttr = "";
		aAddAttr = endPage<totalPage?String.format(DATAPAGE, (endPage+1))
												:"tabindex='-1' aria-disabled='true'";
		aText = "Next";
		html.append(String.format(BT_PATTERN, liClass, liAddAttr, aAddAttr, aText));
		html.append("</ul>");
		html.append("</nav>");
		return html.toString();
	}
}









