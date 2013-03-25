/**
 * Created with IntelliJ IDEA.
 * User: Malcolm
 * Date: 10/19/12
 * Time: 11:16 AM
 * To change this template use File | Settings | File Templates.
 */
Ext.define("TabRunner.views.TournamentList", {
    extend: "Ext.grid.Panel",
    alias: "widget.tournamentList",

    hideHeaders: true,
    store: Ext.create("Ext.data.Store", {
        storeId: "tournamentStore",
        autoLoad: true,
        fields: ["tournamentName"],
        proxy: {
            type: 'ajax',
            api: {
                read: "/TabRunner/tournament/list",
                create: "/TabRunner/tournament/create"
            },
            root: 'items',
            totalProperty: 'totalCount'
        }
    }),
    columns: [
        {header: "Tournaments", dataIndex: "tournamentName"}
    ],
    tbar: [
        {
            xtype: "button",
            text: "New",
            handler: function(button) {
                Ext.Msg.prompt("New Tournament", "Input the name of the tournament",
                    function(buton, response, messageBox) {
                        Ext.Ajax.request({
                            url: "/TabRunner/Tournament/create",
                            params: {
                                tournamentName:response
                            },
                            success: function() {
                                Ext.StoreManager.get("tournamentStore").load();
                            }
                        });
                    }
                );
            }
        },
        {
            xtype: "button",
            text: "Refresh",
            handler: function(button) {
                Ext.StoreManager.get("tournamentStore").load();
            }
        }
    ],
    listeners: {
        select: function(rowModel, record, index, event) {
            var tournamentId = record.get("id");
            Ext.StoreManager.get("teamStore").load({
                url: "/TabRunner/Tournament/teams/" + tournamentId
            });
            Ext.getCmp("addTeamButton").enable();

            Ext.StoreManager.get("judgeStore").load({
                url: "/TabRunner/Tournament/judges/" + tournamentId
            });
            Ext.getCmp("addJudgeButton").enable();

            Ext.StoreManager.get("roundStore").load({
                url: "/TabRunner/Tournament/rounds/" + tournamentId
            });
            Ext.getCmp("generateRoundButton").enable();

            Ext.getCmp("generateTeamRankings").enable();
            Ext.getCmp("generateIndividualRankings").enable();
        }
    },

    getSelected: function() {
        return this.selModel.getLastSelected();
    }

});