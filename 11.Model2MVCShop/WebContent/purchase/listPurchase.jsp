<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
	<meta charset="EUC-KR">
	<!--  ///////////////////////// Bootstrap, jQuery CDN ////////////////////////// -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" >
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" >
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" ></script>
	
	<!-- Bootstrap Dropdown Hover CSS -->
   <link href="/css/animate.min.css" rel="stylesheet">
   <link href="/css/bootstrap-dropdownhover.min.css" rel="stylesheet">
   
    <!-- Bootstrap Dropdown Hover JS -->
   <script src="/javascript/bootstrap-dropdownhover.min.js"></script>
	
	<!--  ///////////////////////// CSS ////////////////////////// -->
	<style>
 		body {
            padding-top : 50px;
        }
     </style>
<title>���� �����ȸ</title>

<script type="text/javascript">
function fncGetPageList(currentPage) {
	$("#currentPage").val(currentPage);
	$("form").attr("method","POST").attr("action","/purchase/listPurchase" ).submit();
}

$(function() {
	
	 $(".ct_list_pop td:nth-child(1) ").on("click" , function(){ 
		var tranNo = $(this).data("param");
			self.location = "/purchase/getPurchase?tranNo="+tranNo ;
		});
	 
	 $(".ct_list_pop td:nth-child(3) ").on("click" , function(){ 
			var tranNo = $(this).data("param1");
				self.location = "/user/getUser?userId=" ;
			});
	 $(".ct_list_pop:nth-child(2n+1)" ).css("background-color" , "whitesmoke");
	 
 });
</script>
</head>

<body>
<form>
<jsp:include page="/layout/toolbar.jsp" />


<div class="container">

	<div class="page-header">
	       	<h3 class=" text-info">���Ÿ����ȸ</h3>
	       	<h5 class="text-muted">���� ������ ������ <strong class="text-danger">����</strong>�� ������.</h5>
	    </div>
	    
    	<div class="row">
			    <div class="col-md-6 text-left">
			    	<p class="text-primary">
			    		��ü  ${resultPage.totalCount } �Ǽ�, ���� ${resultPage.currentPage}  ������
			    	</p>
			    </div>
		   </div>

<table class="table table-hover table-striped" >
      
        <thead>
          <tr>
            <th align="center">No</th>
            <th align="left" >ȸ�� ID</th>
            <th align="left">ȸ����</th>
            <th aling="left">��ǰ��</th>
            <th align="left">��ȭ��ȣ</th>
            <th align="left">�����Ȳ</th>
            <th align="left">��������</th>
          </tr>
        </thead>
       
		<tbody>
		
		  <c:set var="i" value="0" />
		<c:forEach var="purchase" items="${list}">
			<c:set var="i" value="${ i+1 }" />
			<tr>
			  <td align="center">${ i }</td>
			  <td align="left"  data-param="${purchase.tranNo }">${user.userId}</td>
			  <td align="left">${purchase.receiverName }</td>
			  <td align="left">${purchase.purchaseProd.prodName}</td>
			    <td align="left">${purchase.receiverPhone }</td>
			  <td align="left">����
					<c:choose>
				<c:when test = "${purchase.tranCode =='1  '}">���ſϷ�</c:when>
				<c:when test = "${purchase.tranCode =='2  '}">�����</c:when>
				<c:when test = "${purchase.tranCode =='3  '}">��ۿϷ�</c:when>
				<c:otherwise> �Ϸη�</c:otherwise>
			</c:choose>		
				���� �Դϴ�.
			  </td>
			    <td align="left">
			    <c:if test="${purchase.tranCode=='2  ' }">
			<div class="col-xs-8 col-md-4"><a href="/purchase/updateTranCode?tranNo=${purchase.tranNo }&tranCode=3&currentPage=${resultPage.currentPage}">���ǵ���</a></div>
			</c:if>
			</td>
			</tr>
          </c:forEach>
        
        </tbody>
      
      </table>
	
		<%-- <div class="row">
	  		<div class="col-xs-4 col-md-2 " data-param="${purchase.tranNo }"><strong>ȸ��ID</strong></div>
			<div class="col-xs-8 col-md-4">${user.userId}</div>
		</div>
		<hr/> --%>
		
	



	<%-- 
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>��������</strong></div>
	  		<c:if test="${purchase.tranCode=='2  ' }">
			<div class="col-xs-8 col-md-4"><a href="/purchase/updateTranCode?tranNo=${purchase.tranNo }&tranCode=3&currentPage=${resultPage.currentPage}">���ǵ���</a></div>
			</c:if>
		</div>
		<hr/>
		
		</c:forEach> --%>
	



		
	
		<%-- <c:if test="${purchase.tranCode=='2  ' }">
		<a href="/purchase/updateTranCode?tranNo=${purchase.tranNo }&tranCode=3&currentPage=${resultPage.currentPage}">���ǵ���</a>
			</c:if>
		</td> --%>
		

<!-- PageNavigation Start... -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top:10px;">
	<tr>
		<td align="center">
		   <input type="hidden" id="currentPage" name="currentPage" value=""/>
		
			<%-- <% if( resultPage.getCurrentPage() <= resultPage.getPageUnit() ){ %>
					�� ����
			<% }else{ %>
					<a href="javascript:fncGetPurchaseList('<%=resultPage.getCurrentPage()-1%>')">�� ����</a>
			<% } %>

			<%	for(int i=resultPage.getBeginUnitPage();i<= resultPage.getEndUnitPage() ;i++){	%>
					<a href="javascript:fncGetPurchaseList('<%=i %>');"><%=i %></a>
			<% 	}  %>
	
			<% if( resultPage.getEndUnitPage() >= resultPage.getMaxPage() ){ %>
					���� ��
			<% }else{ %>
					<a href="javascript:fncGetPurchaseList('<%=resultPage.getEndUnitPage()+1%>')">���� ��</a>
			<% } %>
		<%System.out.println("resultPage.getCurrentPage()======="+resultPage.getCurrentPage()); %>
		<%System.out.println("resultPage.getEndUnitPage()======="+resultPage.getEndUnitPage()); %>
 --%>
 		 <jsp:include page="../common/pageNavigator_new.jsp"/>
     	</td>
	</tr>
</table>
<!-- PageNavigation End... -->
</form>

</div>

</body>
</html>