<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Title</title>
</head>
<body>
	<form action="http://localhost:8080/springmvc/word/addwords"
		method="post">
		<p>
			<label>Test: <input name=test type="checkbox"></label>
		</p>
		<p>
			<label>单词：<textarea name="words" rows="30" cols="150" required>${uncollins}</textarea></label>
		</p>
		<input type="submit" value="提交...">
	</form>
</body>
</html>