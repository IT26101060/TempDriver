<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html><html lang="en"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
<title><c:out value="${pageTitle}"/></title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head><body class="bg-light"><div class="container py-4">
<h1 class="mb-4"><c:out value="${pageTitle}"/></h1>
<nav class="nav nav-pills flex-wrap gap-2 mb-4">
<a class="nav-link" href="${pageContext.request.contextPath}/students">Students</a>
<a class="nav-link" href="${pageContext.request.contextPath}/lesson">Lessons / Queue</a>
<a class="nav-link" href="${pageContext.request.contextPath}/instructor">Instructors</a>
<a class="nav-link" href="${pageContext.request.contextPath}/vehicle">Vehicles</a>
<a class="nav-link" href="${pageContext.request.contextPath}/payment">Payments</a>
<a class="nav-link" href="${pageContext.request.contextPath}/feedback">Admin / Feedback</a>
</nav>
<c:if test="${not empty error}"><div class="alert alert-danger"><c:out value="${error}"/></div></c:if>
<div class="card mb-4"><div class="card-body"><h4>Create or edit record</h4>
<form method="post" action="${moduleUrl}">
<input type="hidden" name="action" value="${not empty editRecord ? 'update' : 'create'}">
<c:if test="${not empty editRecord}"><input type="hidden" name="id" value="<c:out value='${editRecord[0]}'/>"></c:if>
<div class="row g-3">
<c:forEach var="f" items="${fields}" varStatus="st">
<div class="col-md-6"><label class="form-label"><c:out value="${f}"/></label>
<input class="form-control" name="${f}" required maxlength="250" value="<c:out value='${editRecord[st.index + 1]}'/>"></div>
</c:forEach></div><button class="btn btn-primary mt-3">Save</button>
<c:if test="${not empty editRecord}"><a class="btn btn-secondary mt-3" href="${moduleUrl}">Cancel edit</a></c:if>
</form></div></div>
<div class="card"><div class="card-body"><h4>Records</h4>
<form class="d-flex gap-2 my-3" method="get" action="${moduleUrl}">
<input class="form-control" name="q" placeholder="Search records"><button class="btn btn-outline-primary">Search</button><a class="btn btn-outline-secondary" href="${moduleUrl}">All</a></form>
<c:if test="${pageTitle eq 'Lesson Scheduling & Queue'}"><form method="post" action="${moduleUrl}" class="mb-3"><input type="hidden" name="action" value="process"><button class="btn btn-success">Process next request in queue</button></form></c:if>
<div class="table-responsive"><table class="table table-striped"><thead><tr><th>ID</th><c:forEach var="f" items="${fields}"><th><c:out value="${f}"/></th></c:forEach><th>Actions</th></tr></thead><tbody>
<c:forEach var="r" items="${records}"><tr><td><c:out value="${r[0]}"/></td><c:forEach var="f" items="${fields}" varStatus="st"><td><c:out value="${r[st.index+1]}"/></td></c:forEach><td>
<a class="btn btn-sm btn-warning" href="${moduleUrl}?action=edit&amp;id=${r[0]}">Edit</a>
<form class="d-inline" method="post" action="${moduleUrl}" onsubmit="return confirm('Delete this record?')"><input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="<c:out value='${r[0]}'/>"><button class="btn btn-sm btn-danger">Delete</button></form>
</td></tr></c:forEach>
<c:if test="${empty records}"><tr><td colspan="10" class="text-center">No records found.</td></tr></c:if>
</tbody></table></div></div></div><footer class="text-muted mt-4">Prototype for coursework. Protect this application before real-world use.</footer></div></body></html>
