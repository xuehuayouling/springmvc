<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Title</title>
</head>
<body>
	<form action="http://localhost:8080/springmvc/article/addArticle"
		method="post">
		<p>
			<label>标题: <input value="${article.title}" name=title style="width:500px;height:30px" required></label>
			<label>Friends: <input name=friends type="checkbox"></label>
		</p>
		<p>
			<label>文章内容：<textarea name="content" rows="30" cols="150" required>${article.content}</textarea></label>
		</p>
		<input type="submit" value="提交...">
	</form>
</body>
</html>