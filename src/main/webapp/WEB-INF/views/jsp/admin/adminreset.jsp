<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>ReActive Links</title> 
    <link rel="icon" href="<c:url value="resources/image/favicone.jpg"></c:url>" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- CSS -->   
    <link rel="stylesheet" href="<c:url value="resources/css/style.css"></c:url>"> 
    <link rel="stylesheet" href="<c:url value="resources/css/bootstrap.css"></c:url>">     
    <link rel="stylesheet" href="<c:url value="resources/css/bootstrap-responsive.css"></c:url>">
    <link href = "https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel = "stylesheet">
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	  
      <script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
      <script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
      
   <script type="text/javascript">
   
   function resetLinks(){
	   
	   if($("#reportList").val()==""){
		  alert("Please select reset option");  
	   }else if($("#masterId").val()==""){
		   alert("Please enter master url id");
		   $("#masterId").focus(); 
	   }else if(confirm("Are you sure you want to reactive?")){
		   $.ajax({
				  type : "POST", 
				  url : "resetLinks",
				  data : {
					  masterIds :$("#masterId").val(),
					  reportList : $("#reportList").val()
				  },
				  success :function(data){
					  alert("List build reactive successfully");
					  $("#masterId").val("");   
				  },error : function(e){
					  console.log("Error ::"+e); 
				  }
			   });   
	   }
   }
   
   function resetAllLinks(){
	  $.ajax({
		  type : "POST", 
		  url : "resetAllLinks",
		  data : {
			  tableName :$("#reportAllList").val(),
		  },
		  success :function(data){
			  alert("Reactive links successfully");
			  $("#reportAllList").val("");   
		  },error : function(e){
			  console.log("Error ::"+e); 
		  }
	   }); 
	   
   }
   
   </script>    	

  </head>

  <body>

    <div id="wrap">
      <!-- Fixed navbar Start-->
      <%@include file="include/header.jsp" %>
      <!-- Fixed navbar End -->
       
  <div class="container">
  		<div class="row row_set">
  			<h3>ReActive</h3>
	     	<div class="col-sm-4"> 
	     		<select name="reportList" id="reportList" >
	     			<option value="">Select Option</option>
	     			<option value="masterScrap">Master User Verification</option>
	     			<!--<option value="scrap">User Verification</option>
	     				<option value="companyDetails">Company Details</option> -->
	     			<option value="masterCompany">Master Company</option> 
	     			<option value="masterListBuild">Master List Building</option>
	     			<!-- <option value="listBuilding">List Building</option> -->
	     		</select>
	     	</div>
	     	<div class="col-sm-6">
	     		<textarea rows="4" cols="10" name="masterId" id="masterId"></textarea>
	     	</div>
	     	<div class="col-sm-2">
	     		<br>
	     		<button class="btn btn-primary" style="margin: -10px 0px 0px 10px;"  onclick="resetLinks()">Reset</button>
	     	</div>
	     	</div>
	     	
	     	
	     	<br />  
	     	<div class="row row_set">
  			<h3>Reset</h3>
	     	<div class="col-sm-4"> 
	     		<select name="reportAllList" id="reportAllList" >
	     			<option value="">Select Option</option>
	     			<option value="master_url">Master User Verification</option>
	     			<option value="master_company_url">Master Company</option> 
	     			<option value="master_url_profile">Master Full Details</option>
	     			<option value="master_list_building_url">Master List Build</option>
	     			  
	     			<!--<option value="scrap">User Verification</option>
	     				<option value="companyDetails">Company Details</option> -->
	     			<!-- <option value="listBuilding">List Building</option> -->
	     		</select>
	     	 
	     		<button class="btn btn-primary" style="margin: -10px 0px 0px 10px;"  onclick="resetAllLinks()">Reset</button>
	     	</div>
	     	</div>
	     	
		 </div> 
 </div>
      <div id="push"></div>
    <script src="<c:url value="resources/js/bootstrap-dropdown.js"></c:url>"></script>
  </body>
     
</html>