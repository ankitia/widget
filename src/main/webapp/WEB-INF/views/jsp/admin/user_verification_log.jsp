<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>User Verification Log</title> 
    <link rel="icon" href="<c:url value="resources/image/favicone.jpg"></c:url>" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- CSS -->   
    <link rel="stylesheet" href="<c:url value="resources/css/style.css"></c:url>"> 
    <link rel="stylesheet" href="<c:url value="resources/css/bootstrap.css"></c:url>">     
    <link rel="stylesheet" href="<c:url value="resources/css/bootstrap-responsive.css"></c:url>">
	<!-- <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script> -->
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
 	
	
	 
	 
    
  </head>

  <body>

    <div id="wrap">
      <!-- Fixed navbar Start-->
      <%@include file="include/header.jsp" %>
      <!-- Fixed navbar End -->
       

      <!-- Begin page content --> 
      <div class="container text-center" style="margin-top: 50px;">
         
         
         
         <h2>Total : ${total }</h2>
         
         <table class="table table-striped" style="    font-size: 13px;">
         	<tr>
         		<td>#</td>
         		<td>Name</td>
         		<td>Current Org</td>
         		<td>Current Position</td>
         		<td>Url Id</td>
         	</tr>     	 
             <c:forEach items="${getScrap }" var="getScrap" varStatus="index">         
	         	<tr>
	         		<td>${index.count } </td>
	         		<td>${getScrap.name } </td>
	         		<td>${getScrap.current_org } </td>
	         		<td>${getScrap.current_position } </td>	         		
	         		<td><a href="#" title="${getScrap.url }"> Link ${getScrap.url_id }</a> </td>
	         	</tr>	
	         </c:forEach>
         </table>
		   
      </div>

      <div id="push"></div>
    </div>

     
     
    <script src="<c:url value="resources/js/bootstrap-dropdown.js"></c:url>"></script>
    

  </body>
</html>