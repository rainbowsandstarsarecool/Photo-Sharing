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
            <a class="nav-link" href="${pageContext.request.contextPath}/">Upload photo</a>
        </li>
        <li class="nav-item">
            <a class="nav-link active" href="#">View photo</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/list">List all photos</a>
        </li>
    </ul>
</div>
<div class="container text-center">
    <h1 class="display-4">View photo</h1>
    <h4>
        <small>Enter the photo id into textbox below to view it</small>
    </h4>
    <br/>
</div>
<div class="container text-center">

    <div class="jumbotron text-center">
        <form action="${pageContext.request.contextPath}/view_file" method="POST">
            <div class="row">
                <div class="col">
                </div>
                <div class="col">
                    <input type="text" name="hash_id" class="form-control" placeholder="Enter hash id to view">
                </div>
                <div class="col" align="left">
                    <input type="submit" class="btn btn-outline-info "/>
                </div>
            </div>
        </form>
    </div>
</div>
<c:if test="${hash_id ne null}">
    <div class="container text-center">
        <h4>
            <small>Your file hash id is: ${hash_id}</small>
        </h4>
        <input type="submit" value="Delete File" class="btn btn-info" onclick="window.location='/delete/${hash_id}';"/>
        <br/><br/><a href="/file/${hash_id}"><img class="img-fluid" src="/file/${hash_id}"/></a>
    </div>
</c:if>
</body>
</html>
