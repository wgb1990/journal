<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<title>Admin-<sitemesh:title/></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<link rel="stylesheet" href="${ctx}/static/css/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${ctx}/static/css/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${ctx}/static/css/invalid.css" type="text/css" media="screen" />
<link href="${ctx}/static/jquery-validation/1.11.1/validate.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="${ctx}/static/scripts/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/simpla.jquery.configuration.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/facebox.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/jquery.wysiwyg.js"></script>

<sitemesh:head/>
</head>

<body>
<div id="body-wrapper">
    <%@ include file="/WEB-INF/layouts/sider.jsp"%>
    <div id="main-content">
        <noscript> <!-- Show a notification if the user has disabled javascript -->
            <div class="notification error png_bg">
                <div>
                    Javascript is disabled or is not supported by your browser. Please <a href="http://browsehappy.com/" title="Upgrade to a better browser">upgrade</a> your browser or <a href="http://www.google.com/support/bin/answer.py?answer=23852" title="Enable Javascript in your browser">enable</a> Javascript to navigate the interface properly.
                </div>
            </div>
        </noscript>
        <sitemesh:body/>
        <%@ include file="/WEB-INF/layouts/footer.jsp"%>
    </div>
</div>
</body>
</html>