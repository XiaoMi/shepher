<div class="modal fade" id="importModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">×
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    Import From<span id="select-path"></span>
                </h4>
            </div>
            <form class="form-horizontal" id="import-node" action="?">
                <div class="modal-body">
                    <fieldset>
                        <div class="form-group">
                            <label for="cluster-name" class="col-lg-2 control-label">Clusters</label>
                            <div class="col-lg-8">
                                <select class="form-control input-sm" id="srcCluster" name="srcCluster">
                                    <#list clusters as cluster>
                                        <option value="${cluster.name}">${cluster.name}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="node-name" class="col-lg-2 control-label">Path</label>
                            <div class="col-lg-8" id="user-name-div">
                                <input name="srcPath" id="srcPath" type="text"
                                    class="form-control input-sm placeholder="Node Path">
                            </div>
                        </div>
                    </fieldset>
                </div>
                <input type="hidden" class="form-control" placeholder="Search" name="path" id="path" value="${path!}">
                <input type="hidden" class="form-control" placeholder="Search" name="cluster" id="cluster" value="${cluster!}">
                <input type="hidden" class="form-control" placeholder="Search" name="data" id="date" value="${data!}">
                <div class="modal-footer">
                    <button type="submit" id="select-path-submit" class="btn btn-success" 
                        onclick="onImportButtonClick(this.form, '/clusters/${cluster}/nodes/update', 'POST'); return false;">
                        Confirm
                    </button>
                    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="onCancel()">
                        Cancel
                    </button>
                </div>
            </form>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
