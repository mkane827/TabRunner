/**
 * Created with IntelliJ IDEA.
 * User: Malcolm
 * Date: 2/4/13
 * Time: 4:06 PM
 * To change this template use File | Settings | File Templates.
 */
Ext.define("TabRunner.views.TeamRankings", {
    extend: "Ext.grid.Panel",
    alias: "widget.teamRankings",

    store: Ext.create("Ext.data.Store", {
        storeId: "teamRankingsStore",
        fields: ["teamNumber", "schoolName", "wins"],
        proxy: {
            type: 'ajax'
        }
    }),

    columns: [
        {header:"Team Number", dataIndex:"teamNumber"},
        {header:"School Name", dataIndex:"schoolName"},
        {header:"Wins", dataIndex:"wins"}
    ],

    tbar: [
        {
            xtype: "button",
            id: "generateTeamRankings",
            text: "Generate Ranking",
            disabled: true,
            handler: function(generateTeamRankingsButton) {
                generateTeamRankingsButton.disable();
                var selectedTournamentId = Ext.getCmp("tournamentList").getSelected().get("id");
                Ext.StoreManager.get("teamRankingsStore").load({
                    url:"/TabRunner/Tournament/generateTeamRankings/" + selectedTournamentId
                });
                generateTeamRankingsButton.enable();
            }
        }
    ]
});
