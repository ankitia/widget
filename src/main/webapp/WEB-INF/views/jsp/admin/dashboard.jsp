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
 	
	
	 
	 
    
  </head>

  <body>

    <div id="wrap">
      <!-- Fixed navbar Start-->
      <%@include file="include/header.jsp" %>
      <!-- Fixed navbar End -->
       

      <!-- Begin page content --> 
      <div class="container text-center" style="margin-top: 50px;">
         
         <font color="red" size="4" style="float: right;"> Welcome, <%=session.getAttribute("userName") %> </font>
         
         <table class="table table-striped" style="    font-size: 16px;">
         	<tr>
         		<th width="25%">Link</th>
         		<th style="text-align: center;" width="10%">Active</th>
         		<th style="text-align: center;" width="10%">Approved</th>
         		<th style="text-align: center;" width="10%">Missed Link</th>
         		<th style="text-align: center;" width="10%">Total</th>
         		<th style="text-align: center;" width="10%">In Last Hour</th>
         		<th>In Last Eight Hour</th>
         	</tr>     	 
			<tr>
				<td>User Verification</td>
				<td style="text-align: center;" ><a href="<%=request.getContextPath() %>/userUrl"> ${userVerificationActive }</a></td>
				<td style="text-align: center;"><a href="<%=request.getContextPath() %>/userVerificationLog"> ${userVerificationApproved }</a></td>
				<td style="text-align: center;"> <a href="<%=request.getContextPath() %>/userVerificationMissed">${userVerificationAll -(userVerificationActive +userVerificationApproved)}</a></td>
				<td style="text-align: center;">${userVerificationAll }</td>
				<td style="text-align: center;">${userLastHour }</td>
				<td  > ${userTotalHour}</td>
			</tr>
			<tr>
				<td>Full Profile Details</td>
				<td style="text-align: center;"><a href="<%=request.getContextPath() %>/userProfile">${userProfileActive }</a></td>
				<td style="text-align: center;"><a href="<%=request.getContextPath() %>/userProfileLog">${userProfileApproved }</a></td>
				<td style="text-align: center;"><a href="<%=request.getContextPath() %>/userProfileMissed">${userProfileAll  -(userProfileActive+userProfileApproved) }</a></td> 
				<td style="text-align: center;">${userProfileAll }</td>
				<td style="text-align: center;">${userProfileLastHour }</td>
				<td>${userProfileTotalHour }</td>
			</tr>
			<tr>
				<td>List Building</td> 
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>              
			<tr>
				<td>Company Details</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>                
         </table>
		   
      </div>

      <div id="push"></div>
    </div>

     
     
    <script src="<c:url value="resources/js/bootstrap-dropdown.js"></c:url>"></script>
    

  </body>
</html>