<div class="modal fade" id="snapshotModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">×
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    Snapshot
                </h4>
            </div>
            <div class="modal-body">
                <fieldset>
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th class="col-md-2">ZkVersion</th>
                            <th class="col-md-2">Creator</th>
                            <th class="col-md-2">Action</th>
                            <th class="col-md-2">Reviewer</th>
                            <th class="col-md-4">Time</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list snapshots as snapshot>
                        <tr>
                            <td><a href="/snapshots/${snapshot.id?c}">
                                <#if snapshot_index == 0>
                                    current
                                <#else>
                                    ${snapshot.zkVersion}
                                </#if>
                            </a></td>
                            <td>${snapshot.creator}</td>
                            <td>${snapshot.actionDetail.description}</td>
                            <td>${snapshot.reviewer}</td>
                            <td>${snapshot.time?string("yyyy-MM-dd HH:mm:ss")}</td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                    <a href="/snapshots/clusters/${cluster}?path=${path?url}" class="pull-right">more</a>
                </fieldset>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->