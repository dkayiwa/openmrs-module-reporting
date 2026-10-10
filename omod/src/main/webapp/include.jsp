<%@ include file="/WEB-INF/template/include.jsp"%>
<%-- legacyui's include.jsp no longer declares the JSTL core tags, which the module's pages and portlets use --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!-- Include taglibs from reporting module -->
<%@ taglib prefix="wgt" uri="/WEB-INF/view/module/htmlwidgets/resources/htmlwidgets.tld" %>
<%@ taglib prefix="rpt" uri="/WEB-INF/view/module/reporting/resources/reporting.tld" %>
<%@ taglib prefix="rptTag" tagdir="/WEB-INF/tags/module/reporting" %>
