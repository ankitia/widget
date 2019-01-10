<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>User assigned</title> 
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
 	
	  <%-- <link rel="stylesheet" href="<c:url value="resources/bootstrap.min.css"></c:url>"> --%>
<script type="text/javascript">

function getUserActiveLink(){
	
	$.ajax({
		type : "POST",
		url  : "",
		data : {
			userId :userId, 
		},
		success : function(data){
			alert(data);
			
			
		},
		error : function(e){
			console.log("Error getUserActiveLink::"+e);
		}
		
		
	});
	
}  

function setLinks(){
	
	if($("#operation").val()=="Assign"){
		if($("#allocateLink").val()==0 || $("#allocateLink").val()==""){
			alert("Please enter number of link");
			return false;
		}else if(parseInt($("#pendingLink").val()) < parseInt($("#allocateLink").val())){
			alert("Please entered link is grater then pending link");
			$("#allocateLink").val("0");
			$("#allocateLink").focus();
			return false;
		}
	}
	
	if($("#userId").val()==""){
		alert("Please select user");
		return false;
	}else if($("#operation").val()==""){
		alert("Please select operation");
		return false;
	}else {
		
		$.ajax({
			type : "POST",
			url : "setPendingLink",
			data : {
				 action :$("#operation").val(),
				 userId : $("#userId").val(),
				 limit : $("#allocateLink").val(),
			},
			success : function(data){
				 
				
				if(data=="true"){
					if($("#operation").val()=="Assign"){
						alert("Data assigned successfully");
						$("#allocateLink").val("0");
						$("#allocateLink").hide();
					}else{
						alert("Data reset successfully");
					}
					
				}else if(data=="false"){
					alert("Data is not available");
				}
				
				
				
			},
			error : function(e){
				console.log("Error getUserActiveLink::"+e);
			}
			
		});
		
	}
	
}

function setOperation(){
	
	if($("#operation").val()=="Assign"){
		$("#allocateLink").show();
	}else{   
		$("#allocateLink").hide();
	}
	
}

</script>	
	 
	 
    
  </head>

  <body>

    <div id="wrap">
      <!-- Fixed navbar Start-->
      <%@include file="include/header.jsp" %>
      <!-- Fixed navbar End -->
       
      
  <div class="container">
     <div class="row">
     	<div class="col-sm-3"></div>
	    <div class="col-sm-6">
	    	<h3>User List</h3>
	    	Pending link : <strong>${pendingLink}</strong> <br />  
	    	 <div class="form-group">
		      <select class="form-control" id="userId">
		        <option value="">Select User</option>
		        <c:forEach items="${userList }" var="userList" varStatus="index">
		        	<c:if test="${userList.userId != 1}">
		        		<option value="${userList.userId }">${userList.userEmail}</option>
		        	</c:if>
		        		
		        </c:forEach>
		      </select>
		      <select  class="form-control" id="operation" onchange="setOperation()">
		      	<option value="">Select Operation</option>
		      	<option value="Reset">Reset</option>
		      	<option value="Assign">Assign</option>
		      </select>
		      
		      <input type="text" placeholder="Number of link" value="0" id="allocateLink" style="display: none;">
		      <input type="button" name="submit" style="margin-top: -10px;" value="submit" class="btn btn-primary"  onclick="setLinks()">
		        
		      
		        
		      
      		</div>
      		
      		
	    	<input type="hidden" name="pendingLink" id="pendingLink" value="${pendingLink }">
	    	<div class="text-right"><c:out value="${count }"></c:out></div>
		     
	    </div>
   	</div>
 </div>

      <div id="push"></div>
    </div>

     
     
    <script src="<c:url value="resources/js/bootstrap-dropdown.js"></c:url>"></script>
    

  </body>
</html>