<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page info    = "mrlongssssss"%>
<%@ page import   = "java.lang.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%-- 注意事项 --%>
<%!
	public int count = 0;
	public String info()
  {
  	return  "hello  : " + count ;
  }; 
%>

<html>
<head>
<title>这是一个jsp页面代码</title>
</head>

<jsp:include page="./header.jsp" />
<%--@include file = "header.jsp"%--%> 

<body>
		

    欢迎进入<br>
    现在是时间是：
    <%out.println(new java.util.Date()); %>
    <br>
    <%out.println(++count);%>
    <br>
    <%out.println(info());%>
    <%="mrlong"%>
    <br>
    <%out.println(getServletInfo());%>

    <jsp:include page="./err.jsp" />

</body>
</html>