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
    var ballot = Ext.create("TabRunner.views.Ballot", {id: "ballot", xtype: "ballot"});
    ballot.store.removeAll(true);
    ballot.store.load({
        url: "/TabRunner/Ballot/getBallot/" + ballotId,
        method: "GET",
        callback: function() {
            ballot.loadRecord(ballot.store.last());
        }
    });

    Ext.create('Ext.window.Window', {
        title: "Ballot",
        modal: true,
        maximized: true,
        layout: 'fit',
        items: [ballot]
    }).show();

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