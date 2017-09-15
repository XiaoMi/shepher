<!DOCTYPE html>
<html lang="en">
<#include "header.ftl">
<body>
<!-- nav -->
<div class="navbar navbar-default navbar-static-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/clusters">Shepher</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="/clusters">Home</a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</div>

<!-- error display-->
<div class="container">
    <div class="panel panel-warning">
        <div class="panel-heading">
            <h3 class="panel-title">Shepher Error Page</h3>
        </div>
        <div class="panel-body">
            <p>${message!} ,click <a href="#" onClick="onClick()">here</a> to home page</p>
        </div>
    </div>
</div>

<script>
    function onClick() {
        javascript:history.back(-1);
    }
</script>
</body>
</html>
