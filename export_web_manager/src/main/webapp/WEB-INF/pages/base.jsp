<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%--
    ctx    == ""
--%>
<link rel="stylesheet" href="${ctx}/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx}/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="${ctx}/plugins/ionicons/css/ionicons.min.css">
<link rel="stylesheet" href="${ctx}/plugins/iCheck/square/blue.css">
<link rel="stylesheet" href="${ctx}/plugins/morris/morris.css">
<link rel="stylesheet" href="${ctx}/plugins/jvectormap/jquery-jvectormap-1.2.2.css">
<link rel="stylesheet" href="${ctx}/plugins/datepicker/datepicker3.css">
<link rel="stylesheet" href="${ctx}/plugins/daterangepicker/daterangepicker.css">
<link rel="stylesheet" href="${ctx}/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">
<link rel="stylesheet" href="${ctx}/plugins/datatables/dataTables.bootstrap.css">
<link rel="stylesheet" href="${ctx}/plugins/treeTable/jquery.treetable.css">
<link rel="stylesheet" href="${ctx}/plugins/treeTable/jquery.treetable.theme.default.css">
<link rel="stylesheet" href="${ctx}/plugins/select2/select2.css">
<link rel="stylesheet" href="${ctx}/plugins/colorpicker/bootstrap-colorpicker.min.css">
<link rel="stylesheet" href="${ctx}/plugins/bootstrap-markdown/css/bootstrap-markdown.min.css">
<link rel="stylesheet" href="${ctx}/plugins/adminLTE/css/AdminLTE.css">
<link rel="stylesheet" href="${ctx}/plugins/adminLTE/css/skins/_all-skins.min.css">
<link rel="stylesheet" href="${ctx}/css/style.css">
<link rel="stylesheet" href="${ctx}/plugins/ionslider/ion.rangeSlider.css">
<link rel="stylesheet" href="${ctx}/plugins/ionslider/ion.rangeSlider.skinNice.css">
<link rel="stylesheet" href="${ctx}/plugins/bootstrap-slider/slider.css">
<link rel="stylesheet" href="${ctx}/plugins/bootstrap-datetimepicker/bootstrap-datetimepicker.css">
<link rel="stylesheet" href="${ctx}/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="${ctx}/css/style.css">
<script src="${ctx}/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${ctx}/plugins/common.js"></script>
<style>
    @keyframes myfirst {
        from {
            opacity: 0;
            -webkit-transform: rotateY(45deg);
            -moz-transform: rotateY(45deg);
        }

        to {
            opacity: 1;
            webkit-transform: rotateY(0deg);
            -moz-transform: rotateY(0deg);
        }
    }

    @-moz-keyframes myfirst /* Firefox */
    {
        from {
            opacity: 0;
            -webkit-transform: rotateY(45deg);
            -moz-transform: rotateY(45deg);
        }

        to {
            opacity: 1;
            webkit-transform: rotateY(0deg);
            -moz-transform: rotateY(0deg);
        }
    }

    @-webkit-keyframes myfirst /* Safari 和 Chrome */
    {
        from {
            opacity: 0;
            -webkit-transform: rotateY(45deg);
            -moz-transform: rotateY(45deg);
        }

        to {
            opacity: 1;
            webkit-transform: rotateY(0deg);
            -moz-transform: rotateY(0deg);
        }
    }

    @-o-keyframes myfirst /* Opera */
    {
        from {
            opacity: 0;
            -webkit-transform: rotateY(45deg);
            -moz-transform: rotateY(45deg);
            height: 0%;
        }

        to {
            opacity: 1;
            webkit-transform: rotateY(0deg);
            -moz-transform: rotateY(0deg);
            height: 100%;
        }
    }

    #dataList {
        animation: myfirst 1s;
        -moz-animation: myfirst 1s; /* Firefox */
        -webkit-animation: myfirst 1s; /* Safari 和 Chrome */
        -o-animation: myfirst 1s; /* Opera */

    }

    .box {
        animation: myfirst .5s;
        -moz-animation: myfirst .5s; /* Firefox */
        -webkit-animation: myfirst .5s; /* Safari 和 Chrome */
        -o-animation: myfirst .5s; /* Opera */
    }

    .form-group, .pagination {
        animation: myfirst .7s;
        -moz-animation: myfirst .7s; /* Firefox */
        -webkit-animation: myfirst .7s; /* Safari 和 Chrome */
        -o-animation: myfirst .7s; /* Opera */

    }

    @keyframes myfirst1 {
        from {
            transform: translateY(100%);
            opacity: 0;
        }
        to {
            transform: translateY(0%);
            opacity: 1;
        }
    }

    @-moz-keyframes myfirst1 /* Firefox */
    {
        from {
            transform: translateY(100%);
            opacity: 0;
        }
        to {
            transform: translateY(0%);
            opacity: 1;
        }
    }

    @-webkit-keyframes myfirst1 /* Safari 和 Chrome */
    {
        /*from {opacity: 0;}*/
        /*to {opacity: 1;}*/
        from {
            transform: translateY(100%);
            opacity: 0;
        }
        to {
            transform: translateY(0%);
            opacity: 1;
        }
    }

    @-o-keyframes myfirst1 /* Opera */
    {
        from {
            transform: translateY(100%);
            opacity: 0;
        }
        to {
            transform: translateY(0%);
            opacity: 1;
        }
    }

    .body1 {
        animation: myfirst1 .5s;
        -moz-animation: myfirst1 .5s; /* Firefox */
        -webkit-animation: myfirst1 .5s; /* Safari 和 Chrome */
        -o-animation: myfirst1 .5s; /* Opera */

    }

    /*.content-header>h1,*/
    .content-header > h1, .content-header > h1, .panel, .nav-tabs-custom {
        animation: myfirst1 .4s;
        -moz-animation: myfirst1 .4s; /* Firefox */
        -webkit-animation: myfirst1 .4s; /* Safari 和 Chrome */
        -o-animation: myfirst1 .4s; /* Opera */
    }

    .content-wrapper {
        height: 100%;
    }

    @keyframes myfirst2 {
        from {
            transform: translateX(-100%);
            opacity: 0;
        }
        to {
            transform: translateX(0%);
            opacity: 1;
        }
    }

    @-moz-keyframes myfirst2 /* Firefox */
    {
        from {
            transform: translateX(-100%);
            opacity: 0;
        }
        to {
            transform: translateX(0%);
            opacity: 1;
        }
    }

    @-webkit-keyframes myfirst2 /* Safari 和 Chrome */
    {
        /*from {opacity: 0;}*/
        /*to {opacity: 1;}*/
        from {
            transform: translateX(-100%);
            opacity: 0;
        }
        to {
            transform: translateX(0%);
            opacity: 1;
        }
    }

    @-o-keyframes myfirst2 /* Opera */
    {
        from {
            transform: translateY(-100%);
            opacity: 0;
        }
        to {
            transform: translateY(0%);
            opacity: 1;
        }
    }

    .panel input, .panel select, .panel textarea {
        animation: myfirst2 .4s;
        -moz-animation: myfirst2 .4s; /* Firefox */
        -webkit-animation: myfirst2 .4s; /* Safari 和 Chrome */
        -o-animation: myfirst2 .4s; /* Opera */
    }

    @keyframes myfirst5 {
        100% {
            background: red
        }
        90% {
            background: rebeccapurple
        }
        80% {
            background: greenyellow
        }
        70% {
            background: darkblue
        }
        60% {
            background: greenyellow
        }
        50% {
            background: hotpink
        }
        40% {
            background: aqua
        }
        30% {
            background: saddlebrown
        }
        20% {
            background: darkblue
        }
        10% {
            background: chartreuse
        }
        0% {
            background: green
        }
    }

    @-moz-keyframes myfirst5 /* Firefox */
    {
        100% {
            background: red
        }
        90% {
            background: rebeccapurple
        }
        80% {
            background: greenyellow
        }
        70% {
            background: darkblue
        }
        60% {
            background: greenyellow
        }
        50% {
            background: hotpink
        }
        40% {
            background: aqua
        }
        30% {
            background: saddlebrown
        }
        20% {
            background: darkblue
        }
        10% {
            background: chartreuse
        }
        0% {
            background: green
        }
    }

    @-webkit-keyframes myfirst5 /* Safari 和 Chrome */
    {
        /*from {opacity: 0;}*/
        /*to {opacity: 1;}*/
        100% {
            background: red
        }
        90% {
            background: rebeccapurple
        }
        80% {
            background: greenyellow
        }
        70% {
            background: darkblue
        }
        60% {
            background: greenyellow
        }
        50% {
            background: hotpink
        }
        40% {
            background: aqua
        }
        30% {
            background: saddlebrown
        }
        20% {
            background: darkblue
        }
        10% {
            background: chartreuse
        }
        0% {
            background: green
        }
    }

    @-o-keyframes myfirst5 /* Opera */
    {
        100% {
            background: red
        }
        90% {
            background: rebeccapurple
        }
        80% {
            background: greenyellow
        }
        70% {
            background: darkblue
        }
        60% {
            background: greenyellow
        }
        50% {
            background: hotpink
        }
        40% {
            background: aqua
        }
        30% {
            background: saddlebrown
        }
        20% {
            background: darkblue
        }
        10% {
            background: chartreuse
        }
        0% {
            background: green
        }
    }

    *{

        /*animation: myfirst5 1s;*/
        /*-moz-animation: myfirst5 1s; !* Firefox *!*/
        /*-webkit-animation: myfirst5 1s; !* Safari 和 Chrome *!*/
        /*-o-animation: myfirst5 1s; !* Opera *!*/
        /*延时执行*/
        /*animation-delay:3s;*/
        /*循环执行*/
        /*animation-iteration-count:infinite;*/
    }
</style>