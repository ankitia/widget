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

<script type="text/javascript">


function setTotal(total){
	
	if(confirm("Are you sure you want to confirm?")){
		 $.ajax({
			 url : "updateLinkScore",
			 type : "POST",
			 data :{
				 "total" : total
			 },
			 success: function(data){
				 if(data=="true"){
					 alert("Score updated successfully");
					 $("#totalApproved").hide();					 
				 }else{
				 	 
				 }
				 
				 
			 },
			 error : function(e) {
				console.log("Error setTotal ::"+e);
			}
			 
			 
		 })
		 
	}
	
}
</script> 	
	
	 
	 
    
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
         		<th style="text-align: center;" width="10%">Pending</th>
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
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<%-- <td style="text-align: center;"><a href="<%=request.getContextPath() %>/userProfile">${userProfileActive }</a></td>
				<td style="text-align: center;"><a href="<%=request.getContextPath() %>/userProfileLog">${userProfileApproved }</a></td>
				<td style="text-align: center;"><a href="<%=request.getContextPath() %>/userProfileMissed">${userProfileAll  -(userProfileActive+userProfileApproved) }</a></td> 
				<td style="text-align: center;">${userProfileAll }</td>
				<td style="text-align: center;">${userProfileLastHour }</td>
				<td>${userProfileTotalHour }</td> --%>
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
		   
		   
		   <% if(session.getAttribute("approvedLink")==null || session.getAttribute("approvedLink")==""){  %>
				   <h3 id="totalApproved" class="text-left">User Verification till(07-Jan-2019) :  ${userVerificationApprovedLog } <a href="#" onclick="return setTotal(${userVerificationApprovedLog})" class="btn btn-primary">Please confirm </a> </h3>
			<% }else{
				%>
				<h3 id="totalApproved1" class="text-left">User Verification till(07-Jan-2019) :  ${userVerificationApprovedLog } <a href="#" class="btn btn-primary">Approved </a> </h3>
			<% }%>
		   
		   
		    
		
      </div>
      

      <div id="push"></div>
    </div>

     
     
    <script src="<c:url value="resources/js/bootstrap-dropdown.js"></c:url>"></script>
    

  </body>
</html>