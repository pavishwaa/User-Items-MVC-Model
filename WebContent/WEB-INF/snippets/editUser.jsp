<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    <h2 style="color:red">${msg }</h2>
	<% 
		if(request.getSession().getAttribute("msg") != null)
			request.getSession().removeAttribute("msg");
	%>
	
<form method=post>
<table class="center">
	<tr><td colspan=2><span style="color:red;">${param.msg}</span></td></tr>
	<tr><td colspan=2><h2>Edit Account</h2></td></tr>
	<tr>
		<td align=left>Name: </td>
		<td><input type=text name=name value="${name}"></td>
	</tr>
	<tr>
		<td align=left>New Password: </td>
		<td><input type=text name=pass value="${pass}"></td>
	</tr>
	<tr><td colspan=2><input type=submit value=Edit></td></tr>
</table>
</form>