<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>List Build Verification Log</title> 
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
         
         
         
         <h2>Total : ${total }</h2>
         
         <c:set value="0" var="scrapTotal"></c:set>
         <c:set value="0" var="finalTotal"></c:set>
         
         <table class="table table-striped" style="    font-size: 13px;">
         	<tr>
         		<td width="3%">#</td>
         		<td width="17%">Name</td>
         		<td width="50%">Company Name</td>
         		<td width="10%" style="text-align: right;">Scrap Count</td>
         		<td width="10%" style="text-align: right;">Total</td>
         		<td width="10%">Url Id</td>
         	</tr>     	 
             <c:forEach items="${getCompany }" var="getCompany" varStatus="index">         
	         	<tr>
	         		<td>${index.count } </td>
	         		<td>${getCompany.name } </td>
	         		<td>${getCompany.company_name } </td>
	         		<td style="text-align: right;">${getCompany.scrapCount } 
	         			<c:set value="${getCompany.scrapCount + scrapTotal }" var="scrapTotal"></c:set>
	         		</td>
	         		<td style="text-align: right;">${getCompany.totalRecord } 
	         			<c:set value="${getCompany.totalRecord + finalTotal }" var="finalTotal"></c:set>
	         		 </td>	         		
	         		<td><a href="#" title="${getCompany.url }"> Link ${getCompany.listId }</a> </td>
	         	</tr>	
	         </c:forEach>
	         <tr>
         		<td colspan="3"> </td> 
         		<td style="text-align: right;"><strong>${scrapTotal }</strong></td>
         		<td style="text-align: right;"> <strong>${finalTotal }</strong></td>
         		<td></td> 
         	</tr> 
         </table>
      </div>
      <div id="push"></div>
    </div>
    <script src="<c:url value="resources/js/bootstrap-dropdown.js"></c:url>"></script>
  </body>
</html>