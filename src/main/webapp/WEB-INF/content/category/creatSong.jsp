<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 5/17/2023
  Time: 10:54 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="format-detection" content="telephone=no" />
  <style>
    .container{
      margin: 40px 40px;
    }
    .form-group{
      margin-top: 30px;
    }

  </style>
</head>

<body style="background-color: #231B2E">
<H1 style="text-align: center; color: white; margin-top: 30px">Create a new list</H1>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<div class="container">
  <form>
    <div>
      <div class="form-group">
        <input type="text" class="form-control" placeholder="Enter the name of Song">
      </div>
      <div class="form-group">
        <select class="form-control" name="categories" id="categories">
          <c:forEach items="${categories}" var="c">
            <option value="${c.id}">${c.name}</option>
          </c:forEach>
        </select>
        <select class="form-control" name="listBand" id="listBand">
          <c:forEach items="${listBand}" var="c">
            <option value="${c.id}">${c.name}</option>
          </c:forEach>
        </select>
        <select class="form-control" name="listSinger" id="listSinger">
        <c:forEach items="${listSinger}" var="c">
          <option value="${c.id}">${c.name}</option>
        </c:forEach>
      </select>
      </div>
    </div>
  </form>
</div>
</body>
