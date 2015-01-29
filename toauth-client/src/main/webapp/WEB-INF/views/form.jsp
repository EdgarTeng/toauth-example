<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<title>Status Message Form</title>
</head>
<body>
	<form:form method="post" action="create" modelAttribute="statusMessage">
		<table>
			<tr>
				<td><b>content:</b></td>
				<td><form:input path="content" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="submit"></td>
			</tr>
		</table>
	</form:form>
</body>
</html>