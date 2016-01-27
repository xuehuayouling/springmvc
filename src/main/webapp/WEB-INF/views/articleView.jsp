<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Title</title>
<style type="text/css">
a:active {
	color: #0000FF
}
</style>
<script type="text/javascript">
	function getSelText() {
		var txt = '';
		if (window.getSelection) {
			txt = window.getSelection();
		} else if (document.getSelection) {
			txt = document.getSelection();
		} else if (document.selection) {
			txt = document.selection.createRange().text;
		}
		return txt.toString();
	}
	function clickborder(word) {
		alert(word);
	}
</script>
</head>
<body>
	<div id=content ondblclick="clickborder()">
		<p>
			<strong>${article.title}</strong>
		</p>
		<p>${article.content}</p>
	</div>
</body>
</html>