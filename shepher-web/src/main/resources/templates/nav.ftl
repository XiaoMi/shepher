<!-- Static navbar -->
<div class="navbar navbar-default navbar-static-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>​
            </button>
        <#if cluster??>
            <a class="navbar-brand" href="/clusters/${cluster!}/nodes">Shepher</a>
        <#else>
            <a class="navbar-brand" href="/clusters">Shepher</a>
        </#if>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                       aria-expanded="false">${cluster?default("Clusters")} <span
                            class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                    <#list clusters as cluster>
                        <li><a href="/clusters/${cluster.name}/nodes">${cluster.name}</a></li>
                    </#list>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="/clusters">Home</a></li>
            <#if isAdmin!>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                       aria-expanded="false">Admin<span
                            class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="/admin">Clusters</a></li>
                        <li><a href="/permission">Permission</a></li>
                    </ul>
                </li>
            </#if >
            <#if user??>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                       aria-expanded="false">${user.name}<span
                            class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="/teams">Teams</a></li>
                        <li><a href="/logout">Sign Out</a></li>
                    </ul>
                </li>
            </#if>
                <li><a href="http://git.n.xiaomi.com/cloud-common/shepher/wikis/home" target="_blank">Wiki</a></li>
            </ul>

        </div><!--/.nav-collapse -->
    </div>
</div>