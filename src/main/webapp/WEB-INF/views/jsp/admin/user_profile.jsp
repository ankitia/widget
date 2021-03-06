<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>User Profile</title> 
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
 	<script src="<c:url value="resources/js/common.js"></c:url>"></script>
<script type="text/javascript">

/* function setStatus(status,urlId){
	$.ajax({
		type : "POST",
		url : "updateUrlStatus",
		data :{
			status : status,
			urlId : urlId,
			action : "listContacts"
		},
		followAllRedirects : true,
		success : function(data){
			
		},
		error : function(e){
			consloe.log("Error ::: "+e);
		}
	});
} */  

function setStatus(status,urlId){	
	$.ajax({
		type : "POST",
		url : "updateUrlStatus",
		data :{
			status : status,
			urlId : urlId,
			action : "listContacts"
		},
		success : function(data){
			
		},
		error : function(e){
			consloe.log("Error ::: "+e);
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
       

      <!-- Begin page content --> 
      <div class="container" style="margin-top: 10px;">
      
      	<font style="text-align: left;" size="3" ><strong> Pending </strong> ${userProfileActive }</font> <br />
        <span style="float: right;"><font style="text-align: left;" size="3" >Total Pending Link in System : </font><font size="5" color="black"> ${getTotalActiveLink }</font></span>
        <font style="text-align: left;" size="3" ><strong> Approved </strong> (<a href="<%=request.getContextPath() %>/userProfileLog">${userProfileApproved }</a>) </font> <br />
        <font style="text-align: left;" size="3" ><strong> Missed </strong> (<a href="<%=request.getContextPath() %>/userProfileMissed">${userProfileAll -(userProfileActive +userProfileApproved)}</a>)</font> <br />
              
        <c:if test="${userProfileActive == 0}">
        	<input type="button" name="getMoreLinks" id="getMoreLinks" class="btn btn-primary" onclick="getMoreLinks('userProfile')" value="Get More 50 Links">
        </c:if>
      
      	 <div style="text-align: right;">  
         		<font  size="3" > In Last Hour Clicked (<font color="green" size="4"> ${userProfileLastHour } </font>) </font>
         </div>
         <div style="text-align: right;margin-top: 10px;margin-bottom: 10px;"> 
         	 <font  size="3" > In Last Eight Hour Clicked (<font color="green" size="4"> ${userProfileTotalHour } </font>) </font> 
         </div>
      
         <table class="table table-striped">
         	<tr>
         		<td width="3%">#</td>
         		<td width="97%">Url</td>
         	</tr>     	 
             <c:forEach items="${urlList }" var="urlList" varStatus="index">         
	         	<tr>
	         		<td>${index.count } </td> 
	         		<td>
	         			<c:choose>
	         				<c:when test="${fn:contains(urlList.url, '?')}">
	         						<a href="${urlList.url }&id=${urlList.userId}&urlId=${urlList.masterUrlId}"  onclick="setStatus('Done',${urlList.masterUrlId})" target="_blank"> Profile Link ${urlList.masterUrlId }</a>
	         				</c:when> 
	         				<c:otherwise>
	         						<a href="${urlList.url }?id=${urlList.userId}&urlId=${urlList.masterUrlId}"  onclick="setStatus('Done',${urlList.masterUrlId})" target="_blank"> Profile Link ${urlList.masterUrlId }</a>
	         				</c:otherwise>
	         			</c:choose>
	         		</td>	         		
	         	</tr>	
	         </c:forEach>
         </table>
		   
      </div>

      <div id="push"></div>
    </div>
    <script src="<c:url value="resources/js/bootstrap-dropdown.js"></c:url>"></script>
  </body>
</html>