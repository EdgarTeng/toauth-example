<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
<title></title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/css.css">
</head>
<body>

	<c:if test="${not empty msg}">
		<div class="message">${msg}</div>
	</c:if>

	<a href="${pageContext.request.contextPath}/statusMsg/create">发表状态</a>
	<br />
	<table class="table">
		<thead>
			<tr>
				<th>主人</th>
				<th>状态信息</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${statusMsgList}" var="statusMsg">
				<tr>
					<td>${statusMsg.user.userDetails.nickName}</td>
					<td>${statusMsg.content}</td>
					<td><a
						href="${pageContext.request.contextPath}/statusMsg/${statusMsg.id}/delete">删除</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</body>
</html>