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
 
 
 	  <!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"> -->  
    
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
 	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <!-- <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>  -->
 	   
	  <%-- <link rel="stylesheet" href="<c:url value="resources/bootstrap.min.css"></c:url>"> --%>
	   
	  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	
<style>
  
.ttabs-nav li {
  float: left;
  width: 25%;
    list-style: none;

}
.ttabs-nav li:first-child a {
  border-right: 0;
  border-top-left-radius: 6px;

}
.ttabs-nav li:last-child a {
  border-top-right-radius: 6px;
}
.ttabs-nav a {
  background: #eaeaed;
  border: 1px solid #cecfd5;
  color: #0087cc;
  display: block;
  font-weight: 600;
  padding: 10px 0;
  text-align: center;
  text-decoration: none;
}
.ttabs-nav a:hover { 
  color: #ff7b29;
}
.ttab-active a {
  background: #fff;
  border-bottom-color: transparent;
  color: #2db34a;
  cursor: default;
}
.ttabs-stage {
  border: 1px solid #cecfd5;
  border-radius: 0 0 6px 6px;
  border-top: 0;
  clear: both;
  padding: 24px 30px;
  position: relative;
  top: -1px;
  margin-left: -15px;
  
}

</style>	
	   
  </head>

  <body>

    <div id="wrap"> 
      <!-- Fixed navbar Start--> 
      <%@include file="include/header.jsp" %> 
      <!-- Fixed navbar End -->
       
     <!--   <br /><br /><br /> -->       
      
<div class="container">

<div class="ttabs">
  <ul class="ttabs-nav">
    <li><a href="#tab-1"  style="margin-left: -40px;">User Verification</a></li>
    <li><a href="#tab-2">Full Details</a></li>
    <li><a href="#tab-3">Company Details</a></li>
    <li><a href="#tab-4">Profile Email</a></li>
  </ul>
  <div class="ttabs-stage" >
    <div id="tab-1">
    	 <h3>User Verification</h3>
     	 <table class="table table-striped" style="    font-size: 13px;">
         	<tr>
         		<td width="3%" >#</td>
         		<td width="17%">Name</td>
         		<td width="20%">User Name</td>
         		<td width="40%">Mobile number</td> 
         		<td width="10%" style="text-align: center;">Pending</td>
         		<td width="10%">Total</td>
         	</tr>     	 
         	<c:set var="count" value="0"></c:set>
             <c:forEach items="${userList }" var="userList" varStatus="index">         
	         	<tr>  
	         		<td>${index.count } </td>
	         		<td>${userList.fname } ${userList.lname } </td>
	         		<td>${userList.userEmail }</td>
	         		<td>${userList.mobileNumber }</td> 
	         		<td style="text-align: center;">${userList.pending }</td>
	         		<td> 
	         			${userList.total }
	         			<c:set var="count" value="${userList.total + count }"></c:set> 
	         		</td>
	         	</tr>	
	         </c:forEach>
	         <tr>
         		<td colspan="5" >#</td>
         		<td width="10%"><strong><c:out value="${count }"></c:out></strong></td>
         	</tr>     	 
         </table> 
    </div>
    <div id="tab-2">
    	  <h3>Full Details</h3>
	      <table class="table table-striped" style="font-size: 13px;">
	       	<tr>
	       		<td width="3%" >#</td>
	       		<td width="17%">Name</td>
	       		<td width="20%">User Name</td>
	       		<td width="40%">Mobile number</td> 
         		<td width="10%" style="text-align: center;">Pending</td>
	       		<td width="10%">Total</td>
	       	</tr>     	 
	       	<c:set var="count" value="0"></c:set> 
	           <c:forEach items="${fullDetailsList }" var="fullDetailsList" varStatus="index">         
	        	<tr>
	        		<td>${index.count } </td>
	        		<td>${fullDetailsList.fname } ${fullDetailsList.lname } </td>
	        		<td>${fullDetailsList.userEmail }</td>
	        		<td>${fullDetailsList.mobileNumber }</td>
	        		<td style="text-align: center;">${fullDetailsList.pending }</td>
	        		<td>
	        			${fullDetailsList.total }
	        			<c:set var="count" value="${fullDetailsList.total + count }"></c:set> 
	        		</td>
	        	</tr>	
	        </c:forEach> 
	        <tr>
	       		<td colspan="5" >#</td>
	       		<td width="10%"><strong><c:out value="${count }"></c:out></strong></td>
	       	</tr>     	 
	      </table>
    </div>
    <div id="tab-3">
     	  <h3>Company Details</h3>
	      <table class="table table-striped" style="    font-size: 13px;">
	        	<tr>
	        		<td width="3%" >#</td>
	        		<td width="17%">Name</td>
	        		<td width="20%">User Name</td>
	        		<td width="40%">Mobile number</td> 
         			<td width="10%" style="text-align: center;">Pending</td>
	        		<td width="10%">Total</td> 
	        	</tr>     	 
	        	<c:set var="count" value="0"></c:set>
	            <c:forEach items="${companyList }" var="companyList" varStatus="index">         
	         	<tr>
	         		<td>${index.count } </td>
	         		<td>${companyList.fname } ${companyList.lname } </td>
	         		<td>${companyList.userEmail }</td>
	         		<td>${companyList.mobileNumber }</td>
	         		<td style="text-align: center;">${companyList.pending }</td>
	         		<td> 
	         			${companyList.total }
	         			<c:set var="count" value="${companyList.total + count }"></c:set> 
	         		</td>
	         	</tr>	
	         </c:forEach>
	         <tr>
	        		<td colspan="5" >#</td>
	        		<td width="10%"><strong><c:out value="${count }"></c:out></strong></td>
	        	</tr>     	 
		  </table>
    </div>
    <div id="tab-4">
      	<h3>Profile Email</h3> 
      	<table class="table table-striped" style="    font-size: 13px;">
         	<tr>
         		<td width="3%" >#</td>
         		<td width="17%">Name</td>
         		<td width="20%">User Name</td>
         		<td width="40%">Mobile number</td> 
         		<td width="10%" style="text-align: center;">Pending</td>
         		<td width="10%">Total</td>
         	</tr>     	 
         	<c:set var="count" value="0"></c:set> 
             <c:forEach items="${profileEmailList }" var="profileEmailList" varStatus="index">         
	         	<tr>
	         		<td>${index.count } </td>
	         		<td>${profileEmailList.fname } ${profileEmailList.lname } </td>
	         		<td>${profileEmailList.userEmail }</td>
	         		<td>${profileEmailList.mobileNumber }</td>
	         		<td style="text-align: center;">${profileEmailList.pending }</td>
	         		<td>
	         			${profileEmailList.total }
	         			<c:set var="count" value="${profileEmailList.total + count }"></c:set> 
	         		</td>
	         	</tr>	 
	         </c:forEach>
	         <tr>
         		<td colspan="5" >#</td>
         		<td width="10%"><strong><c:out value="${count }"></c:out></strong></td>
         	</tr>     	 
		  </table>
    </div>
  </div>
</div>
</div> 
    <div id="push"></div>
    </div>

     
<script>
// Show the first tab by default
$('.ttabs-stage div').hide();
$('.ttabs-stage div:first').show();
$('.ttabs-nav li:first').addClass('ttab-active');

// Change tab class and display content
$('.ttabs-nav a').on('click', function(event){
  event.preventDefault();
  $('.ttabs-nav li').removeClass('ttab-active');
  $(this).parent().addClass('ttab-active');
  $('.ttabs-stage div').hide(); 
  $($(this).attr('href')).show();
});
</script>
     
    <script src="<c:url value="resources/js/bootstrap-dropdown.js"></c:url>"></script>
    

  </body>
</html>