<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>42.1</title>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@include file="style.jsp" %>
    </head>
    <body>
        <!-- Start Header -->
         <%@include file="header.jsp" %>
        <!-- End Header -->

        <section class="hero-fullscreen">
            <div class="hero-slider navigation-thin">
                <!-- Slide -->
                <div class="slide overlay bg-img-1">
                    <div class="hero-container container">
                        <div class="hero-content">
                            <div class="appear white text-center">
                                <p class="subheading">We Set You Apart To Grow Online</p>
                                <h1 class="large mt20 mb20">Bring your <span class="bold">business</span> to life</h1>
                                <p class="hidden-xs">We create experiences that transform brands, grow businesses and makepeoples lives<br>better. Awesome interdisciplinary team dedicated to your success!</p>
                                <a href="#features" class="btn btn-lg btn-primary btn-scroll mt30 mt0-xs">We're Creative</a>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- End Slide -->

                <!-- Slide -->
                <div class="slide overlay bg-img-3">
                    <div class="hero-container container">
                        <div class="hero-content">
                            <div class="appear white text-center">
                                <p class="subheading">We Set You Apart To Grow Online</p>
                                <h1 class="large mt20 mb20">Bring your <span class="bold">business</span> to life</h1>
                                <p class="hidden-xs">We create experiences that transform brands, grow businesses and makepeople’s lives<br>better. Awesome interdisciplinary team dedicated to your success!</p>
                                <a href="#features" class="btn btn-lg btn-primary btn-scroll mt30 mt0-xs">We're Creative</a>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- End Slide -->
            </div>
        </section>
        
        <!-- Image Column -->
        <section>
            <div class="container-fluid">
                <div class="vertical-align">
                    <div class="col-md-6">
                        <img src="<%=request.getContextPath() %>/resources/img/assets/expert.png" class="img-responsive mr-auto">
                    </div>
                    <div class="col-md-6 md-pr20pr">
                        <h2><strong>Experts & Training</strong></h2>
                        <h4 class="subheading">Subtitles for Experts and Trianing</h4>
                        <p class="mt30">Objectively innovate empowered manufactured products whereas parallel platforms. Holisticly predominate extensible testing procedures for reliable supply chains. Dramatically engage top-line web services vis-a-vis cutting-edge deliverables. Collaboratively administrate empowered markets. Innovate empowered manufactured products whereas.</p>
                        <p>Efficiently unleash cross-media information without cross-media value. Quickly maximize timely deliverables for real-time schemas. Dramatically maintain clicks-and-mortar solutions without functional solutions.</p>
                        <a href="http://themeforest.net/user/vossendesign/portfolio" class="btn btn-lg btn-primary btn-circle btn-appear mt20"><span>Read in Detail<i class="ion-chevron-right"></i></span></a>
                    </div>

                </div>
            </div>
        </section>
        <!-- End Image Column -->

        <!-- Image Column -->
        <section class="pb40">
            <div class="container-fluid">
                <div class="vertical-align">

                    <div class="col-md-6 md-pl20pr">
                        <h2><strong>What's Up</strong></h2>
                        <h4 class="subheading">Get to know about Events</h4>
                        <p class="mt30">Objectively innovate empowered manufactured products whereas parallel platforms. Holisticly predominate extensible testing procedures for reliable supply chains. Dramatically engage top-line web services vis-a-vis cutting-edge deliverables. Collaboratively administrate empowered markets. Innovate empowered manufactured products whereas.</p>
                        <p>Efficiently unleash cross-media information without cross-media value. Quickly maximize timely deliverables for real-time schemas. Dramatically maintain clicks-and-mortar solutions without functional solutions.</p>
                        <a href="http://themeforest.net/user/vossendesign/portfolio" class="btn btn-lg btn-primary btn-circle btn-appear mt20"><span>More About Events<i class="ion-chevron-right"></i></span></a>
                    </div>

                    <div class="col-md-6">
                        <img src="<%=request.getContextPath() %>/resources/img/assets/whatsup.png" class="img-responsive mr-auto">
                    </div>

                </div>
            </div>
        </section>
        <!-- End Image Column -->

        <!-- Image Column -->
        <section class="pt40 pb80">
            <div class="container">
                <div class="vertical-align">

                    <div class="col-md-6">
                        <img src="<%=request.getContextPath() %>/resources/img/assets/partners.png" class="img-responsive mr-auto">
                    </div>

                    <div class="col-md-6 p5percent">
                        <h2><strong>Parters & Gears</strong></h2>
                        <h4 class="subheading">Details about this section</h4>
                        <p class="mt30">Objectively innovate empowered manufactured products whereas parallel platforms. Holisticly predominate extensible testing procedures for reliable supply chains. Dramatically engage top-line web services vis-a-vis cutting-edge deliverables. Collaboratively administrate empowered markets. Innovate empowered manufactured products whereas.</p>
                        <a href="http://themeforest.net/user/vossendesign/portfolio" class="btn btn-lg btn-primary btn-circle btn-appear mt20"><span>Purchase Now<i class="ion-chevron-right"></i></span></a>
                    </div>

                </div>
            </div>
        </section>
        <!-- End Image Column -->
       <%@include file="footer.jsp" %>
    </body>
</html>
