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
        fields: ["teamNumber", "schoolName", "coachName"],
        proxy: {
            type: 'ajax'
        }
    }),
    columns: [
        {header: "Team Number", dataIndex: "teamNumber"},
        {header: "School Name", dataIndex: "schoolName"},
        {header: "Coach Name", dataIndex: "coachName"}
    ],
    listeners: {
        itemdblclick: function(grid, record, item, index, e, eOpts) {
            var teamId = grid.selModel.getLastSelected().get("id");
            var editTeamForm = Ext.create("TabRunner.views.EditTeamForm", {id: "editTeamForm", xtype: "editTeamForm"});
            Ext.create('Ext.window.Window', {
                    title: "Edit Team",
                    bodyPadding: 5,
                    resizeable: false,
                    height: 400,
                    width: 400,
                    buttons: [
                        {
                            text: "Cancel",
                            handler: function(cancelButton) {
                                cancelButton.findParentByType("window").close();
                            }
                        },
                        {
                            text: "Save",
                            handler: function(saveButton) {
                                var form = saveButton.findParentByType("window").getChildByElement("editTeamForm");
                                var competitorInfo = [];
                                Ext.StoreManager.get("editCompetitorStore").aggregate(function(competitors){
                                    for (var i = 0; i < competitors.length; i++) {
                                        competitorInfo.push(Ext.JSON.encode({
                                            competitorName: competitors[i].get("competitorName"),
                                            id: competitors[i].get("id")
                                        }));
                                    }
                                });
                                form.submit({
                                    url: "/TabRunner/Team/edit/" + teamId,
                                    params: {
                                        competitors: competitorInfo
                                    },
                                    success: function(form, action) {
                                        var currentTournamentId = Ext.getCmp("tournamentList").getSelected().get("id");
                                        Ext.StoreManager.get("teamStore").load({
                                            url: "/TabRunner/Tournament/teams/" + currentTournamentId
                                        });
                                    }
                                });
                                Ext.StoreManager.get("editCompetitorStore").removeAll();
                                saveButton.findParentByType("window").close();
                            }
                        }
                    ],
                    items: [editTeamForm]
                }
            ).show();
            editTeamForm.loadRecord(record);
            Ext.StoreManager.get("editCompetitorStore").load({url:"/TabRunner/Team/getCompetitors/" + teamId});
        }
    },
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
                                    cancelButton.findParentByType("window").close();
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
                                        },
                                        success: function(form, action) {
                                            Ext.StoreManager.get("teamStore").load({
                                                url: "/TabRunner/Tournament/teams/" + selectedTournamentId
                                            });
                                        }
                                    });
                                    addTeamButton.enable();
                                    Ext.StoreManager.get("newCompetitorStore").removeAll();
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

