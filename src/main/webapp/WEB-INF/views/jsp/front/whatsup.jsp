<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>42.1</title>
        
        
        
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@include file="style.jsp" %> 
        
        <%-- <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet"> 
        <link href="<%=request.getContextPath() %>/resources/<%=request.getContextPath() %>/resources/img/assets/favicon.png" rel="icon" type="image/png">
        <link href="<%=request.getContextPath() %>/resources/css/plugins.css" rel="stylesheet" type="text/css">
        <link href="<%=request.getContextPath() %>/resources/css/theme.css" rel="stylesheet" type="text/css">
        <link href="<%=request.getContextPath() %>/resources/css/ionicons.min.css" rel="stylesheet" type="text/css">
        <link href="<%=request.getContextPath() %>/resources/css/et-line-icons.css" rel="stylesheet" type="text/css">
        <link href="<%=request.getContextPath() %>/resources/css/themify-icons.css" rel="stylesheet" type="text/css">
        <link href="<%=request.getContextPath() %>/resources/fonts/lovelo/stylesheet.css" rel="stylesheet" type="text/css">
        <link href="<%=request.getContextPath() %>/resources/css/custom.css" rel="stylesheet" type="text/css">
        <link href="<%=request.getContextPath() %>/resources/css/colors/blue.css" id="color-scheme" rel="stylesheet" type="text/css"> --%>
        
        <!-- <link href="<%=request.getContextPath() %>/resources/img/assets/favicon.png" rel="icon" type="image/png">
        <link href="css/plugins.css" rel="stylesheet" type="text/css">
        <link href="css/theme.css" rel="stylesheet" type="text/css">
        <link href="css/ionicons.min.css" rel="stylesheet" type="text/css">
        <link href="css/et-line-icons.css" rel="stylesheet" type="text/css">
        <link href="css/themify-icons.css" rel="stylesheet" type="text/css">
        <link href="fonts/lovelo/stylesheet.css" rel="stylesheet" type="text/css">
        <link href='https://fonts.googleapis.com/css?family=Raleway:100,200,300,400%7COpen+Sans:400,300' rel='stylesheet' type='text/css'>
        <link href="css/custom.css" rel="stylesheet" type="text/css">  
        <link href="css/colors/blue.css" id="color-scheme" rel="stylesheet" type="text/css"> -->
    </head>
    <body>

         <!-- Start Header -->
         <%@include file="header.jsp" %>
        <!-- End Header --> 

        <div class="site-wrapper">

            <!-- Portfolio -->
            <section class="blog">
                <div class="container-fluid">
                    <div class="row">

                        <div class="portfolio-filters-center cbp-l-filters-button" id="js-filters">
                            <div data-filter="*" class="cbp-filter-item-active cbp-filter-item">
                                All
                            </div>
                            <div data-filter=".fitness" class="cbp-filter-item">
                                Fitness
                            </div>
                            <div data-filter=".food" class="cbp-filter-item">
                                Food
                            </div>
                            <div data-filter=".place" class="cbp-filter-item">
                                Place
                            </div>
                            <div data-filter=".other" class="cbp-filter-item">
                                Other
                            </div>
                        </div>

                        <div class="blog-grid blog-columns cbp" id="blog-grid">

                            <div class="cbp-item place fitness">
                                <div class="post-date">
                                    <h4 class="month">Apr</h4>
                                    <h3 class="day">24</h3>
                                    <span class="year">2016</span>
                                </div>
                                <a href="blog-post-carousel.html" class="cbp-caption">
                                    <div class="cbp-caption-defaultWrap">
                                        <img src="<%=request.getContextPath() %>/resources/img/blog/1.jpg">
                                    </div>
                                    <div class="cbp-caption-activeWrap">
                                        <div class="cbp-l-caption-alignCenter">
                                            <div class="cbp-l-caption-body">
                                                <div class="cbp-l-caption-desc"><i class="ion-android-more-horizontal"></i></div>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                                <div class="blog-thumb-desc">
                                    <a class="link-to-post" href="blog-post-carousel.html"><h4>Awesome Masonry Post</h4></a>
                                    <p class="blog-post-categories">
                                        <span><i class="ion-ios-pricetags-outline"></i></span>
                                        <a href="#">Place</a>
                                        <span>,</span>
                                        <a href="#">Fitness</a>
                                    </p>
                                    <p class="excerpt">Curabitur quis faucibus odio, nec accumsan erat. Duis id ante convallis magna mattis pulvinar eu ut ipsum. Donec at leo id tortor mattis tempus...</p>
                                    <a class="read-more-link btn-appear" href="blog-post-carousel.html"><span>Read More <i class="ion-android-arrow-forward"></i></span></a>
                                </div>
                            </div>

                            <div class="cbp-item food other">
                                <div class="post-date">
                                    <h4 class="month">Mar</h4>
                                    <h3 class="day">29</h3>
                                    <span class="year">2016</span>
                                </div>
                                <a href="blog-post-video.html" class="cbp-caption">
                                    <div class="cbp-caption-defaultWrap">
                                        <img src="<%=request.getContextPath() %>/resources/img/blog/11.jpg">
                                    </div>
                                    <div class="cbp-caption-activeWrap">
                                        <div class="cbp-l-caption-alignCenter">
                                            <div class="cbp-l-caption-body">
                                                <div class="cbp-l-caption-desc"><i class="ion-ios-play"></i></div>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                                <div class="blog-thumb-desc">
                                    <a class="link-to-post" href="blog-post-carousel.html"><h4>On the Other Side</h4></a>
                                    <p class="blog-post-categories">
                                        <span><i class="ion-ios-pricetags-outline"></i></span>
                                        <a href="#">Food</a>
                                        <span>,</span>
                                        <a href="#">Other</a>
                                    </p>
                                    <p class="excerpt">Curabitur quis faucibus odio, nec accumsan erat. Duis id ante convallis magna mattis pulvinar eu ut ipsum. Donec at leo id tortor mattis tempus...</p>
                                    <a class="read-more-link btn-appear" href="blog-post-carousel.html"><span>Read More <i class="ion-android-arrow-forward"></i></span></a>
                                </div>
                            </div>

                            <div class="cbp-item food other">
                                <div class="cbp-caption">
                                    <iframe width="100%" height="200" scrolling="no" frameborder="no" src="https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/tracks/208093589&amp;auto_play=false&amp;hide_related=false&amp;show_comments=true&amp;show_user=true&amp;show_reposts=false&amp;visual=true"></iframe>
                                </div>
                                <div class="blog-thumb-desc">
                                    <a class="link-to-post" href="blog-post-carousel.html"><h4>Six Steps to Success</h4></a>
                                    <p class="blog-post-categories">
                                        <span><i class="ion-ios-pricetags-outline"></i></span>
                                        <a href="#">Food</a>
                                        <span>,</span>
                                        <a href="#">Other</a>
                                    </p>
                                    <p class="excerpt">Curabitur quis faucibus odio, nec accumsan erat. Duis id ante convallis magna mattis pulvinar eu ut ipsum. Donec at leo id tortor mattis tempus...</p>
                                    <a class="read-more-link btn-appear" href="blog-post-carousel.html"><span>Read More <i class="ion-android-arrow-forward"></i></span></a>
                                </div>
                            </div>

                            <div class="cbp-item place fitness">
                                <div class="post-date">
                                    <h4 class="month">Mar</h4>
                                    <h3 class="day">29</h3>
                                    <span class="year">2016</span>
                                </div>
                                <a href="blog-post-carousel.html" class="cbp-caption">
                                    <div class="cbp-caption-defaultWrap">
                                        <img src="<%=request.getContextPath() %>/resources/img/blog/12.jpg">
                                    </div>
                                    <div class="cbp-caption-activeWrap">
                                        <div class="cbp-l-caption-alignCenter">
                                            <div class="cbp-l-caption-body">
                                                <div class="cbp-l-caption-desc"><i class="ion-android-more-horizontal"></i></div>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                                <div class="blog-thumb-desc">
                                    <a class="link-to-post" href="blog-post-carousel.html"><h4>Is The Monster Real?</h4></a>
                                    <p class="blog-post-categories">
                                        <span><i class="ion-ios-pricetags-outline"></i></span>
                                        <a href="#">Place</a>
                                        <span>,</span>
                                        <a href="#">Fitness</a>
                                    </p>
                                    <p class="excerpt">Curabitur quis faucibus odio, nec accumsan erat. Duis id ante convallis magna mattis pulvinar eu ut ipsum. Donec at leo id tortor mattis tempus...</p>
                                    <a class="read-more-link btn-appear" href="blog-post-carousel.html"><span>Read More <i class="ion-android-arrow-forward"></i></span></a>
                                </div>
                            </div>

                            <div class="cbp-item place fitness">
                                <a href="blog-post-carousel.html" class="cbp-caption">
                                    <div class="cbp-caption-defaultWrap blog-thumb-quote">
                                        <h4>"Duis id ante convallis magna mattis pulvinar eu ut ipsum donec at leo id tortor"</h4>
                                    </div>
                                    <div class="cbp-caption-activeWrap">
                                        <div class="cbp-l-caption-alignCenter">
                                            <div class="cbp-l-caption-body">
                                                <div class="cbp-l-caption-desc"><i class="ion-android-more-horizontal"></i></div>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                                <div class="blog-thumb-desc">
                                    <p class="excerpt">Curabitur quis faucibus odio, nec accumsan erat. Duis id ante convallis magna mattis pulvinar eu ut ipsum. Donec at leo id tortor mattis tempus...</p>
                                    <p class="blog-post-categories">
                                        <span><i class="ion-ios-pricetags-outline"></i></span>
                                        <a href="#">Place</a>
                                        <span>,</span>
                                        <a href="#">Fitness</a>
                                    </p>
                                    <a class="read-more-link btn-appear" href="blog-post-carousel.html"><span>Read More <i class="ion-android-arrow-forward"></i></span></a>
                                </div>
                            </div>

                            <div class="cbp-item place other">
                                <div class="post-date">
                                    <h4 class="month">Feb</h4>
                                    <h3 class="day">26</h3>
                                    <span class="year">2016</span>
                                </div>
                                <a href="blog-post-carousel.html" class="cbp-caption">
                                    <div class="cbp-caption-defaultWrap">
                                        <img src="<%=request.getContextPath() %>/resources/img/blog/6.jpg">
                                    </div>
                                    <div class="cbp-caption-activeWrap">
                                        <div class="cbp-l-caption-alignCenter">
                                            <div class="cbp-l-caption-body">
                                                <div class="cbp-l-caption-desc"><i class="ion-android-more-horizontal"></i></div>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                                <div class="blog-thumb-desc">
                                    <a class="link-to-post" href="blog-post-carousel.html"><h4>You Should Go There</h4></a>
                                    <p class="blog-post-categories">
                                        <span><i class="ion-ios-pricetags-outline"></i></span>
                                        <a href="#">Place</a>
                                        <span>,</span>
                                        <a href="#">Other</a>
                                    </p>
                                    <p class="excerpt">Curabitur quis faucibus odio, nec accumsan erat. Duis id ante convallis magna mattis pulvinar eu ut ipsum. Donec at leo id tortor mattis tempus...</p>
                                    <a class="read-more-link btn-appear" href="blog-post-carousel.html"><span>Read More <i class="ion-android-arrow-forward"></i></span></a>
                                </div>
                            </div>

                            <div class="cbp-item food fitness">
                                <div class="post-date">
                                    <h4 class="month">Jan</h4>
                                    <h3 class="day">30</h3>
                                    <span class="year">2016</span>
                                </div>
                                <a href="blog-post-carousel.html" class="cbp-caption">
                                    <div class="cbp-caption-defaultWrap">
                                        <img src="<%=request.getContextPath() %>/resources/img/blog/7.jpg">
                                    </div>
                                    <div class="cbp-caption-activeWrap">
                                        <div class="cbp-l-caption-alignCenter">
                                            <div class="cbp-l-caption-body">
                                                <div class="cbp-l-caption-desc"><i class="ion-ios-play"></i></div>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                                <div class="blog-thumb-desc">
                                    <a class="link-to-post" href="blog-post-carousel.html"><h4>The Creative Edge</h4></a>
                                    <p class="blog-post-categories">
                                        <span><i class="ion-ios-pricetags-outline"></i></span>
                                        <a href="#">Food</a>
                                        <span>,</span>
                                        <a href="#">Fitness</a>
                                    </p>
                                    <p class="excerpt">Curabitur quis faucibus odio, nec accumsan erat. Duis id ante convallis magna mattis pulvinar eu ut ipsum. Donec at leo id tortor mattis tempus...</p>
                                    <a class="read-more-link btn-appear" href="blog-post-carousel.html"><span>Read More <i class="ion-android-arrow-forward"></i></span></a>
                                </div>
                            </div>

                            <div class="cbp-item food other">
                                <div class="post-date">
                                    <h4 class="month">Dec</h4>
                                    <h3 class="day">25</h3>
                                    <span class="year">2015</span>
                                </div>
                                <a href="blog-post-carousel.html" class="cbp-caption">
                                    <div class="cbp-caption-defaultWrap">
                                        <img src="<%=request.getContextPath() %>/resources/img/blog/8.jpg">
                                    </div>
                                    <div class="cbp-caption-activeWrap">
                                        <div class="cbp-l-caption-alignCenter">
                                            <div class="cbp-l-caption-body">
                                                <div class="cbp-l-caption-desc"><i class="ion-android-more-horizontal"></i></div>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                                <div class="blog-thumb-desc">
                                    <a class="link-to-post" href="blog-post-carousel.html"><h4>My Indsider's Guide</h4></a>
                                    <p class="blog-post-categories">
                                        <span><i class="ion-ios-pricetags-outline"></i></span>
                                        <a href="#">Food</a>
                                        <span>,</span>
                                        <a href="#">Other</a>
                                    </p>
                                    <p class="excerpt">Curabitur quis faucibus odio, nec accumsan erat. Duis id ante convallis magna mattis pulvinar eu ut ipsum. Donec at leo id tortor mattis tempus...</p>
                                    <a class="read-more-link btn-appear" href="blog-post-carousel.html"><span>Read More <i class="ion-android-arrow-forward"></i></span></a>
                                </div>
                            </div>

                            <div class="cbp-item food">
                                <div class="cbp-caption">
                                    <iframe width="100%" height="166" scrolling="no" frameborder="no" src="https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/tracks/208093589&amp;color=0cb4ce&amp;auto_play=false&amp;hide_related=false&amp;show_comments=true&amp;show_user=true&amp;show_reposts=false"></iframe>
                                </div>
                                <div class="blog-thumb-desc">
                                    <a class="link-to-post" href="blog-post-carousel.html"><h4>Just Listen To It</h4></a>
                                    <p class="blog-post-categories">
                                        <span><i class="ion-ios-pricetags-outline"></i></span>
                                        <a href="#">Food</a>
                                    </p>
                                    <p class="excerpt">Curabitur quis faucibus odio, nec accumsan erat. Duis id ante convallis magna mattis pulvinar eu ut ipsum...</p>
                                    <a class="read-more-link btn-appear" href="blog-post-carousel.html"><span>Read More <i class="ion-android-arrow-forward"></i></span></a>
                                </div>
                            </div>

                            <div class="cbp-item food">
                                <div class="cbp-caption">
                                    <iframe width="100%" height="240" scrolling="no" frameborder="no" src="https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/tracks/246576034&amp;auto_play=false&amp;hide_related=false&amp;show_comments=true&amp;show_user=true&amp;show_reposts=false&amp;visual=true"></iframe>
                                </div>
                                <div class="blog-thumb-desc">
                                    <a class="link-to-post" href="blog-post-carousel.html"><h4>Payday is Coming</h4></a>
                                    <p class="blog-post-categories">
                                        <span><i class="ion-ios-pricetags-outline"></i></span>
                                        <a href="#">Food</a>
                                        <span>,</span>
                                        <a href="#">Fitness</a>
                                    </p>
                                    <a class="read-more-link btn-appear" href="blog-post-carousel.html"><span>Read More <i class="ion-android-arrow-forward"></i></span></a>
                                </div>
                            </div>

                        </div>

                    </div>
                </div>
            </section>
            <!-- End Portfolio -->

		<%@include file="footer.jsp" %>
 

    </body>
</html>
