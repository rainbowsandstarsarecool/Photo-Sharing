<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <title>Upload photos</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container-fluid">
    <ul class="nav nav-tabs nav-justified">
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/index">Upload photo</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/view_file">View photo</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/list">List all photos</a>
        </li>
    </ul>
</div>
<div class="container text-center">
    <h1 class="display-4">Something went wrong</h1>
    <h4>
        <small>We have encountered an error while processing your request</small>
    </h4>
    <br/>
</div>
<div class="container  text-center">
    <div class="alert alert-danger">
        <strong>Error ${status}:</strong> ${error}
        <c:if test="${message ne null}"><br/>${message}</c:if>
    </div>
</div>
</body>
</html>
