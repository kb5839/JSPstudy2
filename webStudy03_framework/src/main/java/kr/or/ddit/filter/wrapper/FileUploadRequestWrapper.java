package kr.or.ddit.filter.wrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.print.DocFlavor.STRING;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.Part;

public class FileUploadRequestWrapper extends HttpServletRequestWrapper{

	private Map<String, List<PartWrapper>> partWrapperMap;
	
	public FileUploadRequestWrapper(HttpServletRequest request) throws IOException, ServletException {
		super(request);
		partWrapperMap = new LinkedHashMap<>();
		
		parseRequest(request);
	}

	private void parseRequest(HttpServletRequest request) throws IOException, ServletException {
		Collection<Part> parts = request.getParts();
		for(Part tmp : parts) {
			if(tmp.getContentType()==null || tmp.getSize()==0) continue;
			String partName = tmp.getName();
			PartWrapper wrapper = new PartWrapper(tmp);
			List<PartWrapper> list = null;
			if(partWrapperMap.containsKey(partName)) {
				list = partWrapperMap.get(partName);
			}else {
				list = new ArrayList<>();
				partWrapperMap.put(partName, list);
			}
			list.add(wrapper);
		}
	}

	public PartWrapper getPartWrapper(String partName){
		List<PartWrapper> list = partWrapperMap.get(partName);
		PartWrapper wrapper = null;
		if(list!=null && list.size()>0) {
			wrapper = list.get(0);
		}
		return wrapper;
	}
	
	public List<PartWrapper> getPartWrappers(String partName){
		return partWrapperMap.get(partName);
	}
	
	public Map<String, List<PartWrapper>> getPartWrapperMap() {
		return partWrapperMap;
	}
	
	public void deleteAll() throws IOException {
		for(Entry<String, List<PartWrapper>>  entry : partWrapperMap.entrySet()) {
			for(PartWrapper tmp : entry.getValue()) {
				tmp.delete();
			}
		}
	}
}











