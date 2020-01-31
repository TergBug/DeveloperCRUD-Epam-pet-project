<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<% String lang = request.getParameter("lang"); %>
<% String linkToImg = "img/"; %>
<fmt:setLocale value="<%=lang%>"/>
<fmt:setBundle basename="localization/messages"/>

<html lang="<%=lang%>">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="message.title"/></title>
</head>
<body>
<ul>
    <li><a href="?lang=en"><fmt:message key="label.lang.en"/></a></li>
    <li><a href="?lang=zh"><fmt:message key="label.lang.zh"/></a></li>
    <li><a href="?lang=ru"><fmt:message key="label.lang.ru"/></a></li>
</ul>
<h2><fmt:message key="message.title"/></h2>
<fmt:message key="message.description"/><br>
<a href="documentation"><fmt:message key="message.documentation"/></a><br>
<table style="border-spacing: 50px; width: 100%">
    <tr>
        <td style="text-align: center" colspan="3">
            <a href="<%=linkToImg%>Project-uml.png"><img src="<%=linkToImg%>Project-uml.png" alt="UML" height="50%" width="50%"></a><br>
            Project UML diagram
        </td>
    </tr>
    <tr>
        <td style="text-align: center" >
            <img src="<%=linkToImg%>Skill-sequence.png" alt="Skill-seq" height="100%" width="100%"><br>
            Skill sequence diagram
        </td>
        <td style="text-align: center" >
            <img src="<%=linkToImg%>Account-sequence.png" alt="Account-seq" height="100%" width="100%"><br>
            Account sequence diagram
        </td>
        <td style="text-align: center" >
            <img src="<%=linkToImg%>Developer-sequence.png" alt="Developer-seq" height="100%" width="100%"><br>
            Developer sequence diagram
        </td>
    </tr>
</table>
</body>
</html>
