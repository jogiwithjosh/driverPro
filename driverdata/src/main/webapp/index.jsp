<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>
<body>


<sec:authorize access="hasRole('DRIVER')">
	<h2>Driver Found</h2>
</sec:authorize>


<sec:authorize access="hasRole('ROLE_DRIVER')">
	<h2>ROLE_DRIVER Found</h2>
</sec:authorize>

<sec:authorize access="hasAnyRole('ADMIN','ROLE_DRIVER')">
	<h2>Admin or Driver Found</h2>
</sec:authorize>


<sec:authorize access="hasAuthority('ADMIN')">
	<h2>Admin Found</h2>
</sec:authorize>
<sec:authorize access="hasAuthority('ROLE_ADMIN')">
	<h2>Admin Found</h2>
</sec:authorize>
<sec:authorize access="hasAuthority('manager')">
	<h2>Manager</h2>
</sec:authorize>
<sec:authorize access="hasAuthority('student')">
	<h2>Student</h2>
</sec:authorize>
<sec:authorize access="hasAuthority('coursecreator')">
	<h2>coursecreator</h2>
</sec:authorize>
<sec:authorize access="hasAuthority('editingteacher')">
	<h2>editingteacher</h2>
</sec:authorize>
<sec:authorize access="hasAuthority('teacher')">
	<h2>teacher</h2>
</sec:authorize>
<sec:authorize access="hasAuthority('guest')">
	<h2>guest</h2>
</sec:authorize>
<sec:authorize access="hasAuthority('user')">
	<h2>user</h2>
</sec:authorize>
<sec:authorize access="hasAuthority('frontpage')">
	<h2>frontpage</h2>
</sec:authorize>
<h2>Hello World!</h2>
</body>
</html>
