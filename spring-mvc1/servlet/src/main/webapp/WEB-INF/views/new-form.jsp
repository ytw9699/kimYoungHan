<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<!-- 다른곳에서도 재활용 위해서 상대경로 사용, [현재 URL이 속한 계층 경로 + /save]
 /servlet-mvc/members/new-form 이면 /servlet-mvc/members/save 호출
 -->
<%--참고로 web-inf는 외부에서 호출해도 호출안되는게 기본임. 그래서 서블릿 통해서 호출해라--%>
<form action="save" method="post">
    username: <input type="text" name="username" />
    age:      <input type="text" name="age" />
    <button type="submit">전송</button>
</form>

</body>
</html>