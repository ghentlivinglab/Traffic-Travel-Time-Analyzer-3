<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Verkeer 3 GUI</title>
    <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet"  type="text/css" />
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet"  type="text/css" />
</head>
<body>
<header class="col-md-12">
    <img src="<c:url value="/resources/img/banner.jpg"/>" class="banner-fullscreen">
    <div class="col-md-4" id="div-logo"><img src="<c:url value="/resources/img/logo.png"/>" class="logo"></div>
    <div class="col-md-8" id="div-bannertext"><p class="header-text">MOBILITEIT STAD GENT</p></div>
</header>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="<c:url value="/"/>">Home</a></li>
                <li><a href="<c:url value="/about"/>">Over</a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<div id="content">