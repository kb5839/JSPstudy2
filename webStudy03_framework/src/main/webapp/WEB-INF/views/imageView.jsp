<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String jsonImageNames =(String) request.getAttribute("imageCookie");
%>    
	<script type="text/javascript">
		$(function(){
			var imageRendering = function(option, value){
				let clzName = $(option).attr("class");
				let innerTag = null;
				if(clzName.startsWith("image")){
					innerTag = imgPtrn.replace("%S", value);
				}else if(clzName.startsWith("video")){
					innerTag = videoPtrn.replace("%S", value);
				}
				if(innerTag){
					resultArea.append(innerTag);
				}
			}
			var imgPtrn = "<img src='image.do?image=%S'/>";
			var videoPtrn = "<video src='image.do?image=%S' />";
			var resultArea = $("#resultArea");
			var select = $("select").on("change",function(){
// 				let array = $(this).val();
// 				console.log(array);
				let options = $(this).find("option:selected");
				console.log(options);
				resultArea.empty();
				$(options).each(function(idx, option){
					let value = $(this).text();
					setTimeout(function(){
						imageRendering(option, value);
					}, 600);
				});
				
// 				$(option).attr("value")
// 				option[0].value

				
			});
			
			<%
				if(StringUtils.isNotBlank(jsonImageNames)){
					%>
					let json = '<%=jsonImageNames %>';
					let objs = JSON.parse(json);
					console.log(objs);
					select.val(objs);
					select.trigger("change");
					<%
				}
			%>
		}); 
	</script>
		<select multiple size="10">
			<%
				
				String[] listFiles = (String[]) request.getAttribute("listFiles");
				for(String file : listFiles){
					String mime = application.getMimeType(file);
					String clz = StringUtils.startsWith(mime, "image/")?"image":
									StringUtils.startsWith(mime, "video/")?"video":"none";
					%>
					<option class="<%=clz %>"><%=file %></option>
					<%
				}
			%>
		</select>
		<div id="resultArea">
		
		</div>








