<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Report</title> 
    <link rel="icon" href="<c:url value="resources/image/favicone.jpg"></c:url>" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- CSS -->   
    <link rel="stylesheet" href="<c:url value="resources/css/style.css"></c:url>"> 
    <link rel="stylesheet" href="<c:url value="resources/css/bootstrap.css"></c:url>">     
    <link rel="stylesheet" href="<c:url value="resources/css/bootstrap-responsive.css"></c:url>">
    <link href = "https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel = "stylesheet">
	<!-- <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script> -->
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	  
      <script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
      <script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script> 	

<script type="text/javascript">

function downloadReport(action){
	
	$("#exportStartDate").val($("#startDate").val());
	$("#exportEndDate").val($("#endDate").val()); 
	$("#action").val($("#reportList").val()); 
	$("#exportUrlList").val($("#urlList").val());
	document.getElementById("downloadFinalList").submit();
}

function checkReportList(checkList){
	
	if(checkList.value=="companyLocations"){
		$("#checkUrlList").show();		
	}else{ 
		$("#checkUrlList").hide();
	}
	
}

</script>


 
 
  </head>

  <body>

    <div id="wrap">
      <!-- Fixed navbar Start-->
      <%@include file="include/header.jsp" %>
      <!-- Fixed navbar End -->
       
       
      <form action="<%=request.getContextPath()%>/downloadReport" name="downloadFinalList" id="downloadFinalList">
      	<input type="hidden" name="exportStartDate" id="exportStartDate">
      	<input type="hidden" name="exportEndDate" id="exportEndDate">
      	<input type="hidden" name="exportUrlList" id="exportUrlList">
      	<input type="hidden" name="action" id="action">
	</form>
      
  <div class="container"> 
  		<div class="row row_set">
  			<h3>Report</h3>
	     	<div class="col-sm-3"> 
	     		<select name="reportList" id="reportList" onchange="checkReportList(this)">
	     			<option value="">Select Option</option>
	     			
	     			<c:if test="${userId == 1}">
		     			<option value="masterScrap">Master User Verification</option>
		     			<option value="scrap">User Verification</option>
		     			<option value="masterCompany">Master Company</option>
		     			<option value="companyDetails">Company Details</option>
		     			<option value="companyLocations">Company Locations</option>
		     			<option value="masterListBuild">Master List Building</option>
		     			<option value="listBuilding">List Building</option>
		     			<option value="masterFullDetails">Master Full Details</option>
		     			<option value="fullDetails">Full Details</option>
		     			<option value="masterProfileEmail">Master Profile Email</option>
		     			<option value="profileEmailData">Profile Email Data</option>
		     		</c:if>
	     			<option value="masterGoogleData">Master Google Data</option>
	     			<option value="googlePlace">Google Place</option>
	     			<option value="masterGoogleMaps">Master Google Maps</option>
	     			<option value="mapsData">Maps Data</option>
	     			<option value="masterYelpData">Master Yelp Data</option>
	     			<option value="yelpData">Yelp Data</option>
	     			<option value="masterSpokeo">Master Spokeo</option>
	     			<option value="spokeoData">Spokeo Data</option>
	     			<option value="masterSmartystreet">Master Smartystreet</option>
	     			<option value="smartystreetData">Smartystreet Data</option>
	     			<option value="masterBing">Master Bing</option>
	     			<option value="bingData">Bing Data</option>
	     			<option value="masterZillow">Master Zillow</option>
	     			<option value="masterBingMapsDetails">Master BingMaps</option>
	     			<option value="masterGovShop">Master GovShop</option>
	     			<option value="govShop">GovShop Data</option>
	     			<option value="masterGoogle">Master Google</option>
	     			<option value="googleData">Google Data</option>
	     			<option value="masterZoom">Master Zoom Info</option>
	     			<option value="zoomInfoData">Zoom Info Data</option>
	     			<option value="masterManta">Master Manta</option>
	     			<option value="mantaData">Manta Data</option>
	     		</select> 
	     		<input type = "text" id = "startDate"  placeholder = "Start Date">
	     		<input type = "text" id = "endDate" placeholder = "End Date"> 
	     		<button class="btn btn-primary" style="margin: -10px 0px 0px 10px;"  onclick="downloadReport()">Download</button>
	     		<br />
	     		<div style="display: none;" id="checkUrlList">	
	     			<textarea rows="4" cols="5" id="urlList"></textarea>
	     		</div>
	     	</div>
		    <div class="col-sm-6">
		    
		    
		    
		    
		    </div>
		 </div> 
		    
     <%-- <div class="row">
     	<div class="col-sm-3"></div>
	    <div class="col-sm-6">
	    	
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
   	</div> --%> 
 </div>

      <div id="push"></div>
    </div>

     
     
    <script src="<c:url value="resources/js/bootstrap-dropdown.js"></c:url>"></script>
    

  </body>
    <script>
         $(function() {
            $( "#startDate" ).datepicker({
            changeMonth: true,
            changeYear: true,
            yearRange: '1930:2030'
        });

         });
      </script>
      <script>
         $(function() {
            $( "#endDate" ).datepicker({
            changeMonth: true,
            changeYear: true,
            yearRange: '1930:2030'
        });

         });
      </script>
</html>