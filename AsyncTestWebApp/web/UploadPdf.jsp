<%-- 
    Document   : UploadPdf
    Created on : 14.01.2014, 11:39:56
    Author     : Install
--%>
<%@ page import="org.netbeans.enterprise.bpel.asynchronoussampleschemanamespace.TypeB,testPackage.HumanService" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%
            String id = request.getParameter("ID");
            TypeB item;
            if (id == null || (item = HumanService.getItem(id)) == null) {
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } else {
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
       <h1>Upload custom PDF for <%= item.getId() %></h1>
       <h3>Keywords: <%
            boolean first = true;
            for (String keyword : item.getKeyword()) {
                if (first) first = false;
                else out.print(" - ");
                out.print(keyword);
            }
        %></h3>

        <form action="UploadReceiver" method="post" enctype="multipart/form-data">
        <input type="file" name="file" size="50" />
        <input type="hidden" name="ID" value="<%= id %>" />
        <br />
        <input type="submit" value="Upload File" />
        </form>
    </body>
</html>
<% } %>