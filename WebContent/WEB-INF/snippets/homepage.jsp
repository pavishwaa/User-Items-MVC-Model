<%@ page import="testpack.Item,java.util.ArrayList" language="java" 
contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<tr><td colspan=2><span style="color:red;">${param.msg}</span></td></tr>

<table class="center" width="70%">
	<tr>
		<td align=left><h1>Welcome: ${name}</h1></td>
		<td align=right>
			<a href="EditAccount">Edit Account</a> 
			<a href="Logout">LogOut</a>
		</td>
	</tr>
	<tr>
		<td colspan=2 align=center>
			<h2>List of Items</h2>
			
			<table class="center">
				<tr><th>Actions</th><th>Item Name</th><th>Quantity</th></tr>
				<%
					ArrayList<Item> allItems = (ArrayList<Item>)request.getAttribute("allItems");
					for(Item item : allItems) {
						%>
							<tr>
								<td>
									<a href="ViewItem?id=<%=item.getId() %>">View</a> 
									<a href="EditItem?id=<%=item.getId() %>">Edit</a> 
									<a href="DeleteItem?id=<%=item.getId() %>">Delete</a>
								</td>
								<td><%=item.getName() %></td>
								<td><%=item.getQty() %></td>
							</tr>						
								
													
						<%
					}
				%>
			</table><br>
			<a href="InsertItem">Insert Item</a>
			<br><br>
		</td>
	</tr>
</table>
