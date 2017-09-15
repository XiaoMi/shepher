<div class="modal fade" id="statModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">×
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    Stat
                </h4>
            </div>
            <div class="modal-body">
                <fieldset>
                    <table class="table table-bordered info-table">
                        <tbody>
                        <tr>
                            <td class="col-md-2 active">Czxid</td>
                            <td class="col-md-4">${stat.czxid?c}</td>
                            <td class="col-md-2 active">Mzxid</td>
                            <td class="col-md-4">${stat.mzxid?c}</td>
                        </tr>
                        <tr>
                            <td class="col-md-2 active">Pzxid</td>
                            <td class="col-md-4">${stat.pzxid?c}</td>
                            <td class="col-md-2 active">Version</td>
                            <td class="col-md-4">${stat.version?c}</td>
                        </tr>
                        <tr>
                            <td class="col-md-2 active">Cversion</td>
                            <td class="col-md-4">${stat.cversion?c}</td>
                            <td class="col-md-2 active">Aversion</td>
                            <td class="col-md-4">${stat.aversion?c}</td>
                        </tr>
                        <tr>
                            <td class="col-md-2 active">NumChildren</td>
                            <td class="col-md-4">${stat.numChildren}</td>
                            <td class="col-md-2 active">EphemeralOwner</td>
                            <td class="col-md-4">${stat.ephemeralOwner}</td>
                        </tr>
                        <tr>
                            <td class="col-md-2 active">Ctime</td>
                            <td class="col-md-4">${stat.ctime?number_to_date?string("yyyy-MM-dd HH:mm:ss")}</td>
                            <td class="col-md-2 active">Mtime</td>
                            <td class="col-md-4">${stat.mtime?number_to_date?string("yyyy-MM-dd HH:mm:ss")}</td>
                        </tr>
                        <tr>
                            <td class="col-md-2 active">DataLength</td>
                            <td class="col-md-4">${stat.dataLength?c}</td>
                        </tr>
                        </tbody>
                    </table>
                </fieldset>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->