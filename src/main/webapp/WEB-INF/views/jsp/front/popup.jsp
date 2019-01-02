
<script type="text/javascript">
function openModal(id){
	for(var i=1;i<=4;i++){
		if('id0'+i==id){
			document.getElementById('id0'+i).style.display='block';	   
		}else{
			document.getElementById('id0'+i).style.display='none';
		}
	}
} 
</script>


 <!-- Sign Up Modal Starts -->
 <div id="id01" class="modal">
   <form class="modal-content animate" >
   <div class="imgcontainer">
       <h3 class="modal_header_new">SIGN UP</h3>
       <span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">&times;</span>
     </div>
     
     <div class="container" style="width: 100%;">
         <div class="row">
             <div class="contact">
                     <fieldset><input name="fname" type="text" id="fname" placeholder="First Name*"/></fieldset>
                     <fieldset><input name="lname" type="text" id="lname" placeholder="Last Name*"/></fieldset>
                     <fieldset><input name="email" type="text" id="email" placeholder="Email*"/></fieldset>
                     <fieldset><input name="pass" type="password" id="pass" placeholder="Password*"/></fieldset>
                     <fieldset><input name="verifypass" type="password" id="verifypass" placeholder="Verify Password*"/></fieldset>
                     <fieldset><input type="submit"   class="submit btn btn-md btn-primary" id="submit" value="Sign Up" /></fieldset>
             </div>
         </div>
     </div>
   </form>
 </div>
 <!-- Sign Up Modal Ends -->
        
  <!-- Sign In Modal Starts -->
   <div id="id02" class="modal">
     <form class="modal-content animate" action="/action_page.php">
       
       <div class="imgcontainer">
         <h3 class="modal_header_new">SIGN IN</h3>
         <span onclick="document.getElementById('id02').style.display='none'" class="close" title="Close Modal">&times;</span>
       </div>

       <div class="container" style="width: 100%;">
           <div class="row">
               <div class="contact">
                       <fieldset><input name="userEmail" type="email" id="userEmail"  placeholder="Email*" required="required" /></fieldset>
                       <fieldset><input name="userPassword" type="text" id="userPassword" placeholder="Password*" required="required" /></fieldset>
                       <fieldset><input type="submit" onclick="return checkValidUser()"  class="submit btn btn-md btn-primary" id="submit" value="Sign In" />
                       <span class="psw"><a href="#">Forgot Password?</a></span></fieldset>
               </div>
           </div>
       </div>
     </form>
   </div>
   <!-- Sign In Modal Ends -->       
   
   
   <!-- Change Pass Modal Starts -->
        <div id="id03" class="modal">
          <form class="modal-content animate" action="/action_page.php">
          	
          	<div class="imgcontainer">
              <h3 class="modal_header_new">CHANGE PASSWORD</h3>
              <span onclick="document.getElementById('id03').style.display='none'" class="close" title="Close Modal">&times;</span>
            </div>

            <div class="container" style="width: 100%;">
                <div class="row">
                    <div class="contact">
                            <fieldset><input name="userPassword" type="password" id="userPassword" placeholder="Old Password*" required="required" /></fieldset>
                            <fieldset><input name="userPassword" type="password" id="userPasswordNew1" placeholder="New Password*" required="required" /></fieldset>
                            <fieldset><input name="userPassword" type="password" id="userPasswordNew1" placeholder="Confirm New Password*" required="required" /></fieldset>
                            <fieldset><input type="submit" onclick="return checkValidUser()"  class="submit btn btn-md btn-primary" id="submit" value="Update Password" />
                            </fieldset>
                        
                    </div>
                </div>
            </div>
          </form>
        </div>
        <!-- Change Pass Modal Ends -->
        
        <!-- User Profile Modal Starts -->
        <div id="id04" class="modal">
          <form class="modal-content animate" >         
          	<div class="imgcontainer">
              <h3 class="modal_header_new">USER PROFILE</h3>
              <span onclick="document.getElementById('id04').style.display='none'" class="close" title="Close Modal">&times;</span>
            </div>

            <div class="container" style="width: 100%;">
                <div class="row">
                    <div class="contact">
                            <fieldset><input name="fname" type="text" id="fname" placeholder="First Name*"/></fieldset>
                            <fieldset><input name="lname" type="text" id="lname" placeholder="Last Name*"/></fieldset>
                            <fieldset><input name="email" type="text" id="email" placeholder="Email*"/></fieldset>
                            <fieldset><input type="submit" onclick="return signUp();" class="submit btn btn-md btn-primary" id="submit" value="Update Profile" /></fieldset>
                    </div>
                </div>
            </div>
          </form>
        </div>
        <!-- User Profile Modal Ends -->
        
        