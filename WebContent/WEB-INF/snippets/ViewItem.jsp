<%@page import="testpack.Item"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


    <%
    	Item item = (Item) request.getAttribute("item");
    %>
    
  <table class="center">
  <h3>Show Item</h3>
	<ul>
		<li>Item Id: ${item.id}</li>
		<li>Item Name: ${item.name}</li>
		<li>Item Qty: ${item.qty}</li>
	</ul>

  </table>
  <a href="Home"><h3> Home..</h3></a>
    