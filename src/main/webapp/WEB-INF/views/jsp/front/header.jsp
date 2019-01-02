     <!-- Start Header -->
     <nav class="navbar navbar-default fullwidth">
         <div class="container">

             <div class="navbar-header">
                 <div class="container">
                     <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                         <span class="sr-only">Toggle navigation</span>
                         <span class="icon-bar top-bar"></span>
                         <span class="icon-bar middle-bar"></span>
                         <span class="icon-bar bottom-bar"></span>
                     </button>  
                     <a class="navbar-brand" href="<%=request.getContextPath()%>/"><img src="<%=request.getContextPath() %>/resources/img/assets/logo-light.png" class="logo-light" alt="#"><img src="<%=request.getContextPath() %>/resources/img/assets/logo-dark.png" class="logo-dark" alt="#"></a>
                 </div>
             </div>

             <div id="navbar" class="navbar-collapse collapse">
                 <div class="container">
                   <ul class="nav navbar-nav menu-right">

                     <li><a class="nav-item"  href="<%=request.getContextPath()%>/">EXPERTS & TRAINING</a> </li>
                     <li><a class="nav-item" href="whatsup" >WHAT'S UP</a> </li>
                     <li><a class="nav-item" >PARTNERS & GEAR</a></li>
                     <li><a class="nav-item book-a-platter">BOOK A PLATTER</a></li>
                     
                     <% if(session.getAttribute("username")!=null){ %>
                     	   <li class="dropdown adjust-pos"><a class="dropdown-toggle"><%=session.getAttribute("username") %><i class="ti-angle-down"></i></a>
                              <ul class="dropdown-menu">
                                  <li><a  onclick="openModal('id04')" href="#">User Profile</a></li>
                                  <li><a  onclick="openModal('id03')" href="#">Change Password</a></li>
                                  <li><a href="#">Booking History</a></li>
                                  <li><a href="signout">Sign Out</a></li>  
                              </ul>
                          </li>
                     <%}else{ %>
	                     <li><a  onclick="openModal('id02')">SIGN IN</a></li>
	                     <li><a onclick="openModal('id01')">SIGN UP</a></li>
                     <% } %>
                      

                     <!--button onclick="document.getElementById('id01').style.display='block'" style="width:auto;">Login</button-->

                   </ul>

                 </div>
             </div>
         </div>
     </nav>
     <!-- End Header -->
<script src="<%=request.getContextPath() %>/resources/js/jquery.min.js"></script>
