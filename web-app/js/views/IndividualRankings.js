/**
 * Created with IntelliJ IDEA.
 * User: Malcolm
 * Date: 3/25/13
 * Time: 11:45 AM
 * To change this template use File | Settings | File Templates.
 */
Ext.define("TabRunner.views.IndividualRankings", {
    extend: "Ext.panel.Panel",
    alias: "widget.individualRankings",
    layout: {
        type: "hbox",
        align: "stretch"
    },

    items: [
        {
            xtype: "grid",
            flex: 1,
            title: "Attorney Rankings",
            store: Ext.create("Ext.data.Store", {
                storeId: "individualAttorneyRankingsStore",
                fields: ["competitorName", "teamNumber", "schoolName", "ranks"]
            }),

            columns: [
                {header:"Competitor Name", dataIndex: "competitorName", width: 200},
                {header:"Team Number", dataIndex:"teamNumber"},
                {header:"School Name", dataIndex:"schoolName"},
                {header:"Ranks", dataIndex:"ranks"}
            ]
        },
        {
            xtype: "grid",
            layout: "fit",
            flex: 1,
            title: "Witness Rankings",
            store: Ext.create("Ext.data.Store", {
                storeId: "individualWitnessRankingsStore",
                fields: ["competitorName", "teamNumber", "schoolName", "ranks"]
            }),

            columns: [
                {header:"Competitor Name", dataIndex: "competitorName", width: 200},
                {header:"Team Number", dataIndex:"teamNumber"},
                {header:"School Name", dataIndex:"schoolName"},
                {header:"Ranks", dataIndex:"ranks"}
            ]
        }
    ],

    tbar: [
        {
            xtype: "button",
            id: "generateIndividualRankings",
            text: "Generate Ranking",
            disabled: true,
            handler: function(generateIndividualRankingsButton) {
                generateIndividualRankingsButton.disable();
                var selectedTournamentId = Ext.getCmp("tournamentList").getSelected().get("id");
                Ext.Ajax.request({
                    url:"/TabRunner/Tournament/generateIndividualRankings/" + selectedTournamentId,
                    success: function(response, options) {
                        var data = Ext.JSON.decode(response.responseText);
                        var attorneyStore = Ext.StoreManager.get("individualAttorneyRankingsStore");
                        var witnessStore = Ext.StoreManager.get("individualWitnessRankingsStore");
                        attorneyStore.loadRawData(data.attorney, false);
                        witnessStore.loadRawData(data.witness, false);
                        attorneyStore.sort("ranks", "desc");
                        witnessStore.sort("ranks", "desc");
                    }
                });
                generateIndividualRankingsButton.enable();
            }
        }
    ]

});