<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>List Build Missed</title> 
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
	
	function setStatus(status,urlId){	
		$.ajax({
			type : "POST",
			url : "updateUrlStatus",
			data :{
				status : status,
				urlId : urlId,
				action : "listBuild"
			},
			success : function(data){
				
			},
			error : function(e){
				consloe.log("Error ::: "+e);
			}
		});	
	}

	function getTotalMissed(urlId){
		$.ajax({
			type : "POST",
			url : "getListBuildMissedCount",
			data :{
				urlId : urlId,
				
			},
			success : function(data){
				alert(data);
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
      <div class="container text-center" style="margin-top: 50px;">
         
         <table class="table table-striped" style="    font-size: 13px;">
         	<tr>
         		<td>#</td>
         		<td>Name</td>
         		<td>Company Name</td>
         		<td>Scrap Count</td>
         		<td>Total</td>
         		<td>Url Id</td>
         	</tr>     	 
             <c:forEach items="${getCompany }" var="getCompany" varStatus="index">         
	         	<tr>
	         		<td>${index.count } </td>
	         		<td>${getCompany.name } </td>
	         		<td>${getCompany.company_name } </td>
	         		<td>${getCompany.scrapCount } </td>
	         		<td>${getCompany.totalRecord }  </td>	         		
	         		<td><a href="#" title="${getCompany.url }"> Link ${getCompany.listId }</a> </td>
	         	</tr>	
	         </c:forEach>
         </table>
         
         
         <table class="table table-striped" style="font-size: 13px;">
         	<tr>
         		<td width="3%">#</td>
         		<td width="87%">Url</td>
         		<td width="10%">Status</td>
         	</tr>     	 
         	<c:forEach items="${urlList }" var="urlList" varStatus="index">         
	         	<tr>
	         		<td>${index.count } </td> 
	         		<td>
	         			<a href="${urlList.url }&id=${urlList.userId}&urlId=${urlList.listBuildUrlId}"  onclick="setStatus('Done',${urlList.listBuildUrlId})" target="_blank"> Link ${urlList.listBuildUrlId }</a> 
	         		</td>
	         		<td> <a href="#" onclick="getTotalMissed(${urlList.listBuildUrlId})">Status</a> </td>
	         	</tr>	
	         </c:forEach> 
             <%-- <c:forEach items="${urlList }" var="urlList" varStatus="index">         
	         	<tr>
	         		<td>${index.count } </td> 
	         		<td><a href="${urlList.url }&id=${urlList.userId}&urlId=${urlList.urlId}"  onclick="setStatus('Done',${urlList.urlId})" target="_blank"> Link ${urlList.urlId }</a> </td>	         		
	         	</tr>	
	         </c:forEach> --%>
         </table>
		   
      </div>

      <div id="push"></div>
    </div>

     
     
    <script src="<c:url value="resources/js/bootstrap-dropdown.js"></c:url>"></script>
    

  </body>
</html>
