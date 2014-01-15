<%-- 
    Document   : index
    Created on : 07.01.2014, 11:27:02
    Author     : Administrator
--%>
<%@ page import="org.netbeans.enterprise.bpel.asynchronoussampleschemanamespace.TypeB,testPackage.HumanService" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1><a href="index.jsp">Refresh</a></h1>
        <h1>Pending Reports:</h1>
        <%
            String[] IDs = HumanService.getIDs();
            for (String id : IDs) {
        %>
        <p><h2><%= id %></h2>
        <h3>Keywords: <%
            TypeB item = HumanService.getItem(id);
            boolean first = true;

            for (String keyword : item.getKeyword()) {
                if (first) first = false;
                else out.print(" - ");
                out.print(keyword);
            } %></h3>
        <a target="_blank" href="ViewPdf?ID=<%= id %>">View</a> -
        <a href="ConfirmPdf?ID=<%= id %>">Confirm</a> -
        <a href="RejectPdf?ID=<%= id %>">Reject</a> -
        <a href="UploadPdf.jsp?ID=<%= id %>">Upload</a>
        </p>
        <%
        }
        %>
    </body>
</html>