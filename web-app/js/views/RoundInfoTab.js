/**
 * Created with IntelliJ IDEA.
 * User: Malcolm
 * Date: 11/7/12
 * Time: 5:58 PM
 * To change this template use File | Settings | File Templates.
 */
var openBallot = function(grid, cell, row, column, e, record) {
    var header = grid.getHeaderAtIndex(column).dataIndex;
    var ballotId;
    if (header === "judge1") {
        ballotId = record.get("ballot1");
    }
    else if (header === "judge2") {
        ballotId = record.get("ballot2");
    }
    else {
        return;
    }

    Ext.Ajax.request({
        url: "/TabRunner/Ballot/getBallot/" + ballotId,
        method: "GET",
        success: function(response, request) {
            var json = Ext.JSON.decode(response.responseText);

            var ballot = Ext.create("TabRunner.views.Ballot",
                {
                    id: "ballot",
                    xtype: "ballot"
                }
            );

            Ext.StoreManager.get("competitorStore").loadData(json.competitors, false);

            ballot.loadRecord(Ext.create("BallotModel", json));

            Ext.create('Ext.window.Window', {
                title: "Ballot",
                modal: true,
                width: 600,
                height: 600,
                layout: 'fit',
                items: [ballot],
                buttons: [
                    {
                        text: "Save",
                        handler: function(saveButton) {
                            var window = saveButton.findParentByType("window");
                            var form = window.getChildByElement("ballot");
                            form.submit({
                                url: "/TabRunner/Ballot/save/" + ballotId,
                                success: function(form, action) {
                                    window.close();
                                }
                            });
                        }
                    },
                    {
                        text: "Cancel",
                        handler: function(cancelButton) {
                            cancelButton.findParentByType("window").close();
                        }
                    }
                ]
            }).show();
        }
    });

};


Ext.define("TabRunner.views.RoundInfoTab", {
    extend: "Ext.grid.Panel",
    alias: "widget.roundInfoTab",
    selType: "cellmodel",
    closable: true,
    columns: [
        {header: "Plaintiff/Prosecution", dataIndex: "teamP"},
        {header: "Defense", dataIndex: "teamD"},
        {header: "Judge 1", dataIndex: "judge1", listeners:{dblclick:openBallot}},
        {header: "Judge 2", dataIndex: "judge2", listeners:{dblclick:openBallot}}
    ]
});