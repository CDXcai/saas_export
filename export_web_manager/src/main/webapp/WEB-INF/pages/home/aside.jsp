<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../base.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <!-- Sidebar user panel -->
        <div class="user-panel">
            <div class="pull-left image">
                <img src="${ctx}/img/user2-160x160.jpg" class="img-circle" alt="User Image">
            </div>
            <div class="pull-left info">
                <p> ${sessionScope.loginUser.userName}</p>
                <a href="#">${sessionScope.loginUser.companyName}</a>
            </div>
        </div>

        <!-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="sidebar-menu">
            <li class="header">菜单</li>


            <%----%>
            <%--循环构建菜单,一级菜单--%>
            <c:forEach items="${moduleList}" var="oneMenu">
                <c:if test="${oneMenu.ctype == 0}">
                    <li class="treeview">
                        <a href="#">
                            <i class="fa fa-cube"></i> <span>${oneMenu.name}</span>
                            <span class="pull-right-container">
                               <i class="fa fa-angle-left pull-right"></i>
                            </span>
                        </a>
                        <ul class="treeview-menu">
                            <%--二级菜单--%>
                            <c:forEach items="${moduleList}" var="twoMenu">
                                <c:if test="${twoMenu.parentId == oneMenu.id}">
                                    <li id="company-manager">
                                        <a href="${ctx}${twoMenu.curl}" onclick="setSidebarActive(this)" target="iframe">
                                            <i class="fa fa-circle-o"></i>${twoMenu.name}
                                        </a>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </li>
                </c:if>
            </c:forEach>


        </ul>

    </section>
    <!-- /.sidebar -->
</aside>
