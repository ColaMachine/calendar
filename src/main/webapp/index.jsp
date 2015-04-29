<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
 <link href="<%=path %>/css/bootstrap.min.css" rel="stylesheet">
 <style>
 /* Template-specific stuff
 *
 * Customizations just for the template; these are not necessary for anything
 * with disabling the responsiveness.
 */

/* Account for fixed navbar */
body {
  padding-top: 70px;
  padding-bottom: 30px;
}

body,
.navbar-fixed-top,
.navbar-fixed-bottom {
  min-width: 970px;
}

/* Don't let the lead text change font-size. */
.lead {
  font-size: 16px;
}

/* Finesse the page header spacing */
.page-header {
  margin-bottom: 30px;
}
.page-header .lead {
  margin-bottom: 10px;
}


/* Non-responsive overrides
 *
 * Utilize the following CSS to disable the responsive-ness of the container,
 * grid system, and navbar.
 */

/* Reset the container */
.container {
  width: 970px;
  max-width: none !important;
}

/* Demonstrate the grids */
.col-xs-4 {
  padding-top: 15px;
  padding-bottom: 15px;
  background-color: #eee;
  background-color: rgba(86,61,124,.15);
  border: 1px solid #ddd;
  border: 1px solid rgba(86,61,124,.2);
}

.container .navbar-header,
.container .navbar-collapse {
  margin-right: 0;
  margin-left: 0;
}

/* Always float the navbar header */
.navbar-header {
  float: left;
}

/* Undo the collapsing navbar */
.navbar-collapse {
  display: block !important;
  height: auto !important;
  padding-bottom: 0;
  overflow: visible !important;
  visibility: visible !important;
}

.navbar-toggle {
  display: none;
}
.navbar-collapse {
  border-top: 0;
}

.navbar-brand {
  margin-left: -15px;
}

/* Always apply the floated nav */
.navbar-nav {
  float: left;
  margin: 0;
}
.navbar-nav > li {
  float: left;
}
.navbar-nav > li > a {
  padding: 15px;
}

/* Redeclare since we override the float above */
.navbar-nav.navbar-right {
  float: right;
}

/* Undo custom dropdowns */
.navbar .navbar-nav .open .dropdown-menu {
  position: absolute;
  float: left;
  background-color: #fff;
  border: 1px solid #ccc;
  border: 1px solid rgba(0, 0, 0, .15);
  border-width: 0 1px 1px;
  border-radius: 0 0 4px 4px;
  -webkit-box-shadow: 0 6px 12px rgba(0, 0, 0, .175);
          box-shadow: 0 6px 12px rgba(0, 0, 0, .175);
}
.navbar-default .navbar-nav .open .dropdown-menu > li > a {
  color: #333;
}
.navbar .navbar-nav .open .dropdown-menu > li > a:hover,
.navbar .navbar-nav .open .dropdown-menu > li > a:focus,
.navbar .navbar-nav .open .dropdown-menu > .active > a,
.navbar .navbar-nav .open .dropdown-menu > .active > a:hover,
.navbar .navbar-nav .open .dropdown-menu > .active > a:focus {
  color: #fff !important;
  background-color: #428bca !important;
}
.navbar .navbar-nav .open .dropdown-menu > .disabled > a,
.navbar .navbar-nav .open .dropdown-menu > .disabled > a:hover,
.navbar .navbar-nav .open .dropdown-menu > .disabled > a:focus {
  color: #999 !important;
  background-color: transparent !important;
}

/* Undo form expansion */
.navbar-form {
  float: left;
  width: auto;
  padding-top: 0;
  padding-bottom: 0;
  margin-right: 0;
  margin-left: 0;
  border: 0;
  -webkit-box-shadow: none;
          box-shadow: none;
}

/* Copy-pasted from forms.less since we mixin the .form-inline styles. */
.navbar-form .form-group {
  display: inline-block;
  margin-bottom: 0;
  vertical-align: middle;
}

.navbar-form .form-control {
  display: inline-block;
  width: auto;
  vertical-align: middle;
}

.navbar-form .form-control-static {
  display: inline-block;
}

.navbar-form .input-group {
  display: inline-table;
  vertical-align: middle;
}

.navbar-form .input-group .input-group-addon,
.navbar-form .input-group .input-group-btn,
.navbar-form .input-group .form-control {
  width: auto;
}

.navbar-form .input-group > .form-control {
  width: 100%;
}

.navbar-form .control-label {
  margin-bottom: 0;
  vertical-align: middle;
}

.navbar-form .radio,
.navbar-form .checkbox {
  display: inline-block;
  margin-top: 0;
  margin-bottom: 0;
  vertical-align: middle;
}

.navbar-form .radio label,
.navbar-form .checkbox label {
  padding-left: 0;
}

.navbar-form .radio input[type="radio"],
.navbar-form .checkbox input[type="checkbox"] {
  position: relative;
  margin-left: 0;
}

.navbar-form .has-feedback .form-control-feedback {
  top: 0;
}
 
 </style>
</head>
<body>

  
    <!-- Fixed navbar -->
    <nav class="navbar navbar-default navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <!-- The mobile navbar-toggle button can be safely removed since you do not need it in a non-responsive implementation -->
          <a class="navbar-brand" href="#">ColaMachine</a>
        </div>
        <!-- Note that the .navbar-collapse and .collapse classes have been removed from the #navbar -->
        <div id="navbar">
          <ul class="nav navbar-nav">
            <li class="active"><a href="#">主页</a></li>
            <li><a href="#about">关于</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">联系我们 <span class="caret"></span></a>
              <ul class="dropdown-menu" role="menu">
                <li><a target="_blank"   href="http://weibo.com/colamachine">微博</a></li>
                <li class="divider"></li>
                <li><a target="_blank"  href="mailto:likegadfly@163.com">邮件</a></li>
              </ul>
            </li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">更多 <span class="caret"></span></a>
              <ul class="dropdown-menu" role="menu">
                <li><a href="#">美食</a></li>
                <li><a href="#">日常用品</a></li>
                <li><a href="#">服务</a></li>
                <li class="divider"></li>
                <li class="dropdown-header">Nav header</li>
                <li><a href="#">Separated link</a></li>
                <li><a href="#">One more separated link</a></li>
              </ul>
            </li>
          </ul>
          <form class="navbar-form navbar-left" role="search">
            <div class="form-group">
              <input type="text" class="form-control" placeholder="查找">
            </div>
            <button type="submit" class="btn btn-default">提交</button>
          </form>
          <ul class="nav navbar-nav navbar-right">
            <li><a href="<%=path%>/items.jsp">4s服务</a></li>
            <li><a href="#">微信</a></li>
            <li><a href="mailto:likegadfly@163.com">邮件</a></li>
               <li><a target="_blank" href="<%=path%>/calendar/CalendarView.html">日历</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
    
    <div class="container">
<div class="row">
<div class="col-sm-12">

</div>
</div>
      <div class="page-header">
        <h1>Non-responsive Bootstrap</h1>
        <p class="lead">Disable the responsiveness of Bootstrap by fixing the width of the container and using the first grid system tier. <a href="http://getbootstrap.com/getting-started/#disable-responsive">Read the documentation</a> for more information.</p>
      </div>

      <h3>What changes</h3>
      <p>Note the lack of the <code>&lt;meta name="viewport" content="width=device-width, initial-scale=1"&gt;</code>, which disables the zooming aspect of sites in mobile devices. In addition, we reset our container's width and changed the navbar to prevent collapsing, and are basically good to go.</p>

      <h3>Regarding navbars</h3>
      <p>As a heads up, the navbar component is rather tricky here in that the styles for displaying it are rather specific and detailed. Overrides to ensure desktop styles display are not as performant or sleek as one would like. Just be aware there may be potential gotchas as you build on top of this example when using the navbar.</p>

      <h3>Browsers, scrolling, and fixed elements</h3>
      <p>Non-responsive layouts highlight a key drawback to fixed elements. <strong class="text-danger">Any fixed component, such as a fixed navbar, will not be scrollable when the viewport becomes narrower than the page content.</strong> In other words, given the non-responsive container width of 970px and a viewport of 800px, you'll potentially hide 170px of content.</p>
      <p>There is no way around this as it's default browser behavior. The only solution is a responsive layout or using a non-fixed element.</p>

      <h3>Non-responsive grid system</h3>
      <div class="row">
        <div class="col-xs-4">One third</div>
        <div class="col-xs-4">One third</div>
        <div class="col-xs-4">One third</div>
      </div>

    </div> <!-- /container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="<%=path %>/js/jquery.min.js"></script>
    <script src="<%=path %>/js/bootstrap.min.js"></script>

</body>
</html>