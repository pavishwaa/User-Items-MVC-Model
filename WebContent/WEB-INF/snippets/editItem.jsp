<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@page import="testpack.Item"%>

<%
    Item item = (Item) request.getAttribute("item");
 %>

<h2 style="color:red">${msg }</h2>
	<% 
		if(request.getSession().getAttribute("msg") != null)
			request.getSession().removeAttribute("msg");
	%>
	
<form method=post>
<table class="center">
	<tr><td colspan=2><span style="color:red;">${param.msg}</span></td></tr>
	<tr><td colspan=2><h2>Edit Item</h2></td></tr>
	<tr>
		<td align=left>Item Name: </td>
		<td><input type=text name=iname value="${item.name}"></td>
	</tr>
	<tr>
		<td align=left>Qty: </td>
		<td><input type=text name=iqty value="${item.qty}"></td>
	</tr>
	<tr><td colspan=2><input type=submit value=Add></td></tr>
</table>
</form>