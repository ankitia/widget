<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Manage User</title> 
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
      <div class="container">
        <div class="page-header">
          <h3 class="header_mg">Manage Users
          	<span class="header_mg" style="float: right;font-size: 12px;"> 
          		 
          	</span>
          </h3>
             
        </div>
		
                
         
		   <table class="table">
    <thead>
      <tr>
      	<th>#</th>
        <th>Name</th>
        <th>Email</th>
        <th>Password</th>
      </tr> 
    </thead>
    <tbody>
    	
    	<c:forEach items="${userList }" var="userList" varStatus="num">
	    	  <tr>  
	    	 	<td>${num.count} </td>   
	        	<td>${userList.fname } ${userList.fname }</td> 
		        <td>${userList.userEmail }</td>
		        <td>${userList.password }</td>
		      </tr> 
    	</c:forEach>	
    </tbody>
  </table>


      </div>

      <div id="push"></div>
    </div>

    <!-- <div id="footer">
      <div class="container">
        <p class="muted credit">Example courtesy <a href="http://martinbean.co.uk">Martin Bean</a> and <a href="http://ryanfait.com/sticky-footer/">Ryan Fait</a>.</p>
      </div>
    </div> -->
  
     
    <script src="<c:url value="resources/js/bootstrap-dropdown.js"></c:url>"></script>
  </body>
</html>