<div class="col-md-3">
<#if isOnebox>
    <form id="search" action="?" method="GET">
        <div class="input-group placeholder" id="pathGroup">
            <input type="text" class="form-control input-sm" placeholder="1.0.0.1,1.0.0.2" name="show-ip" id="show-ip" value="${showIp!}"
                   onkeydown="onSearch(event)">
            <span class="input-group-btn">
                  <button type="submit" class="btn btn-success btn-sm">Show ip</button>
            </span>
        </div>
    </form>
</#if>
    <ol class="breadcrumb">
        <li><a href="/clusters/${cluster}/nodes">${cluster}</a></li>
    <#assign pathUrl=""/>
    <#list pathArr as p>
        <#if p != "">
            <#assign pathUrl+="/" + p/>
            <li><a href="/clusters/${cluster}/nodes?path=${pathUrl?url}">${p}</a></li>
        </#if>
    </#list>
    </ol>

    <ul class="list-group">
    <#list children as child>
        <#if path == "/">
            <li class="list-group-item"><a href="/clusters/${cluster}/nodes?path=${"/" + child?url}">${child}</a></li>
        <#else>
            <li class="list-group-item"><a href="/clusters/${cluster}/nodes?path=${path?url + "/" + child?url}">${child}</a>
            </li>
        </#if>
    </#list>
    </ul>
</div>