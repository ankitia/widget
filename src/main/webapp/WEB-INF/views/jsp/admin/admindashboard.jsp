<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Dashboard</title> 
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
 	<!--   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"> --> 
 	
 	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script> 
 	
	  <%-- <link rel="stylesheet" href="<c:url value="resources/bootstrap.min.css"></c:url>"> --%>
  </head>

  <body>

    <div id="wrap">
      <!-- Fixed navbar Start-->
      <%@include file="include/header.jsp" %>
      <!-- Fixed navbar End -->
       
      <!--  <br /><br /><br /> -->
      
<!-- <div class="container">
  <h2>Toggleable Tabs</h2>
  <br>
  Nav tabs
  <ul class="nav nav-tabs" role="tablist">
    <li class="nav-item">
      <a class="nav-link active" data-toggle="tab" href="#home">Home</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" data-toggle="tab" href="#menu1">Menu 1</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" data-toggle="tab" href="#menu2">Menu 2</a>
    </li>
  </ul>

  Tab panes
  <div class="tab-content">
    <div id="home" class="container tab-pane active"><br>
      <h3>HOME</h3>
      <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
    </div>
    <div id="menu1" class="container tab-pane fade"><br>
      <h3>Menu 1</h3>
      <p>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
    </div>
    <div id="menu2" class="container tab-pane fade"><br>
      <h3>Menu 2</h3>
      <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam.</p>
    </div>
  </div>
</div>  -->
      
  <div class="container">
     <div class="row row_set">
     	<div class="col-sm-3"></div>
	    <div class="col-sm-6">
	    	<h3>User List</h3>
	    	<div class="text-right"><c:out value="${count }"></c:out></div>
		    <table class="table table-striped" style="    font-size: 13px;">
	         	<tr>
	         		<td width="3%" >#</td>
	         		<td width="17%">Name</td>
	         		<td width="20%">User Name</td>
	         		<td width="50%">Mobile number</td>
	         		<td width="10%">Total</td>
	         	</tr>     	 
	         	<c:set var="count" value="0"></c:set>
	             <c:forEach items="${userList }" var="userList" varStatus="index">         
		         	<tr>
		         		<td>${index.count } </td>
		         		<td>${userList.fname } ${userList.lname } </td>
		         		<td>${userList.userEmail }</td>
		         		<td>${userList.mobileNumber }</td>
		         		<td>
		         			${userList.total }
		         			<c:set var="count" value="${userList.total + count }"></c:set> 
		         		
		         		</td>
		         	</tr>	
		         </c:forEach>
		         <tr>
	         		<td colspan="4" >#</td>
	         		<td width="10%"><strong><c:out value="${count }"></c:out></strong></td>
	         	</tr>     	 
		         
		         	
	         </table>
	    </div>
   	</div>
 </div>

      <div id="push"></div>
    </div>

     
     
    <script src="<c:url value="resources/js/bootstrap-dropdown.js"></c:url>"></script>
    

  </body>
</html>