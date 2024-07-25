<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Счет</title>
</head>
<body>
<div>
    <a href="/">Главная</a>
    <h2>Счет</h2>
    <h4>name: ${pageContext.request.userPrincipal.name} </h4>
    <h4>balance: ${account.balance} </h4>
    <h4>isBlocked: ${account.blocked} </h4>

    <form method="post" action="${pageContext.request.contextPath}/account/topUp">
        <h2>Пополнить счет</h2>
        <div>
            <input type="hidden" name="name" value="${pageContext.request.userPrincipal.name}"/>
            <input name="amount" type="number" min="1" placeholder="Amount"/>
            <button type="submit">Submit</button>
        </div>
    </form>

    <form method="post" action="${pageContext.request.contextPath}/account/withdraw">
        <h2>Снять со счета</h2>
        <div>
            <input type="hidden" name="name" value="${pageContext.request.userPrincipal.name}"/>
            <input name="amount" type="number" min="1" placeholder="Amount"/>
            <button type="submit">Submit</button>
        </div>
    </form>
</div>
</body>
</html>