<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<title>Message List</title>
</head>
<body>

	<!-- add a new sportType -->
	<table>
		<tr>
			<td><a href="create">Publish a new Status Message</a></td>
		</tr>
	</table>

	<!-- list sportTypes -->
	<table border="1">
		<tr>
			<td><b>Publish Time</b></td>
			<td><b>Status Messages</b></td>
			<td><b>Publisher</b></td>
			<td><b>Options</b></td>
		</tr>
		<c:forEach var="bean" items="${list}">
			<tr>
				<td>${bean.createTime }</td>
				<td>${bean.content }</td>
				<td>${bean.publisher }</td>
				<td><a href="delete?id=${bean.id }">Delete</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>