<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
  <head> 
    <link rel="stylesheet" href="<c:url value="resources/css/bootstrap-responsive.css"></c:url>">
     <%-- <link rel="stylesheet" href="<c:url value="resources/css/bootstrap.css"></c:url>">    --%>
  </head>

  <body>
  
  	<c:if test="${userName != null }"> 
      <!-- Fixed navbar -->
      <div class="navbar navbar-fixed-top">
        <div class="navbar-inner">
          <div class="container">
            <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <a class="brand" href="#">Widget</a>
            <div class="nav-collapse collapse">    
              <ul class="nav"> 
                
                <c:choose>
                	<c:when test="${userRole == 1}">
                		<li><a href="<%=request.getContextPath() %>/admindashboard">Dashboard</a></li>
                	</c:when>
                	<c:otherwise> 
                		<li><a href="<%=request.getContextPath() %>/dashboard">Dashboard</a></li> 
                		<%-- <li><a href="<%=request.getContextPath() %>/masterProfileEmailURL">Profile Email</a></li> --%>
					      <li class="dropdown">
					        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Scripts
					        <span class="caret"></span></a>
					        <ul class="dropdown-menu">
					          	<li><a href="<%=request.getContextPath() %>/masterGoogleURL">Google</a></li>
		                		<li><a href="<%=request.getContextPath() %>/masterBingURL">Bing</a></li>
		                		<li><a href="<%=request.getContextPath() %>/masterZillowURL">Zillow</a></li>
		                		<li><a href="<%=request.getContextPath() %>/masterBingMaps">Bing Maps</a></li>
		                		<li><a href="<%=request.getContextPath() %>/masterYelpURL">Yelp</a></li>
		                		<li><a href="<%=request.getContextPath() %>/masterMapsURL">Maps</a></li>
		                		<li><a href="<%=request.getContextPath() %>/masterBingMapsDetail">Bing Maps Detail</a></li>
		                		<li><a href="<%=request.getContextPath() %>/masterGooglePlace">Google Place</a></li>
		                		<li><a href="<%=request.getContextPath() %>/masterSpokeoURL">Spokeo</a></li>
		                		<li><a href="<%=request.getContextPath() %>/masterSmartystreetDataURL">Smartystreet</a></li>
		                		<li><a href="<%=request.getContextPath() %>/masterGovShopURL">GovShop</a></li>
		                		<li><a href="<%=request.getContextPath() %>/masterGoogleZoom">Google Zoom</a></li>
		                		<li><a href="<%=request.getContextPath() %>/masterZoomInfoURL">Zoom Info</a></li>
		                		<li><a href="<%=request.getContextPath() %>/masterMantaURL">Manta Data</a></li>
		                		<li><a href="<%=request.getContextPath() %>/masterZumperURL">Zumper Data</a></li>
		                		<li><a href="<%=request.getContextPath() %>/masterRentURL">Rent Data</a></li>
		                		<li><a href="<%=request.getContextPath() %>/masterTruliaURL">Trulia Data</a></li>
					        </ul>    
					      </li>
                		
                		
                	</c:otherwise>    
                </c:choose>
                                  
                <c:if test="${userRole == 1}">
                	<c:if test="${userId == 1}">
	                	<li><a href="<%=request.getContextPath() %>/uuserAssigned">Assigned</a></li>
	                	<li><a href="<%=request.getContextPath() %>/reset">ReActive</a></li>
	                	<li><a href="<%=request.getContextPath() %>/adminupload">Upload</a></li>
	                	<li><a href="<%=request.getContextPath() %>/report">Report</a></li>
	                </c:if>
	                <c:if test="${userId != 1}">
	                	<li><a href="<%=request.getContextPath() %>/adminupload">Upload</a></li>
	                	<li><a href="<%=request.getContextPath() %>/report">Report</a></li>
	                </c:if>
                </c:if>
                
                
                <%-- <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown">Manage <b class="caret"></b></a>
                  <ul class="dropdown-menu">
                    <li><a href="<%=request.getContextPath() %>/userUrl">User Verification</a></li>
                    <li><a href="<%=request.getContextPath() %>/userProfile">Full Profile Details</a></li>
                    <li><a href="#">List Building</a></li>
                    <li><a href="#">Company Details</a></li>
                  </ul>
                </li>
                <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown">Work Log <b class="caret"></b></a>
                  <ul class="dropdown-menu">
                    <li><a href="<%=request.getContextPath() %>/userVerificationLog">User Verification</a></li>
                    <li><a href="<%=request.getContextPath() %>/userProfileLog">Full Profile Details</a></li>
                    <li><a href="#">List Building</a></li>
                    <li><a href="#">Company Details</a></li> 
                  </ul>
                </li> --%>
                
                
                <%-- <li><a href="<%=request.getContextPath() %>/manageUser">Users</a></li>
                <li class="dropdown"> 
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown">Manage <b class="caret"></b></a>
	                <ul class="dropdown-menu">
	                    <li><a href="category">Event Category</a></li>
	                    <li><a href="#">Event</a></li>	                    
	                </ul>
                </li> --%>
                
                <!-- <li><a href="#contact">Contact</a></li>
                <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <b class="caret"></b></a>
                  <ul class="dropdown-menu">
                    <li><a href="#">Action</a></li>
                    <li><a href="#">Another action</a></li>
                    <li><a href="#">Something else here</a></li>
                    <li class="divider"></li>
                    <li class="nav-header">Nav header</li>
                    <li><a href="#">Separated link</a></li>
                    <li><a href="#">One more separated link</a></li>
                  </ul>
                </li> -->
              </ul>   
              <ul class="nav navbar-nav navbar-right" style="float:right;">
              	  <li><a><font color="red" size="4" style="float: right;"> Welcome, <%=session.getAttribute("userName") %> </font></a></li>
			      <li><a href="<%=request.getContextPath() %>/logout"><span class="glyphicon glyphicon-log-in"></span> Logout</a></li>
			    </ul> 
            </div><!--/.nav-collapse -->
             
                 
          </div>
        </div>
      </div>
      
      </c:if>
      <c:if test="${userName == null }">
      		<c:redirect url="/"></c:redirect>
      </c:if> 
      
  </body>
</html>