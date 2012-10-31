/**
 * Created with IntelliJ IDEA.
 * User: Malcolm
 * Date: 9/28/12
 * Time: 11:26 AM
 * To change this template use File | Settings | File Templates.
 */
Ext.define("TabRunner.views.TeamPanel", {
    extend: "Ext.grid.Panel",
    alias: "widget.teamPanel",
    store: Ext.create("Ext.data.Store", {
        storeId: "teamStore",
        fields: ["email", "firstName", "lastName"]
    }),
    columns: [
        {header: "Team Number", dataIndex: "teamNumber"},
        {header: "School Name", dataIndex: "schoolName"},
        {header: "Coach Name", dataIndex: "coachName"}
    ],
    tbar: [
        {
            xtype: "button",
            id: "addTeamButton",
            text: "Add Team",
            disabled: true,
            handler: function(addTeamButton) {
                addTeamButton.disable();
                Ext.create('Ext.window.Window', {
                        title: "Add Team",
                        bodyPadding: 5,
                        resizeable: false,
                        height: 400,
                        width: 400,
                        buttons: [
                            {
                                text: "Cancel",
                                handler: function(cancelButton) {
                                    addTeamButton.enable();
                                    button.findParentByType("window").close();
                                }
                            },
                            {
                                text: "Save",
                                handler: function(saveButton) {
                                    var form = saveButton.findParentByType("window").getChildByElement("newTeamForm");
                                    var competitorNames = [];
                                    Ext.StoreManager.get("newCompetitorStore").aggregate(function(competitors){
                                        for (var i = 0; i < competitors.length; i++) {
                                            competitorNames.push(competitors[i].get("competitorName"));
                                        }
                                    });
                                    var selectedTournamentId = Ext.getCmp("tournamentList").getSelected().get("id");
                                    form.submit({
                                        url: "/TabRunner/Tournament/addTeam/" + selectedTournamentId,
                                        params: {
                                            competitors: competitorNames
                                        }
                                    });
                                    addTeamButton.enable();
                                    saveButton.findParentByType("window").close();
                                }
                            }
                        ],
                        items: [{id: "newTeamForm", xtype: "newTeamForm"}]
                    }
                ).show();
            }
        }
    ]
});

