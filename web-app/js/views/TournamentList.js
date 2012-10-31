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
            text: "New Tournament",
            handler: function(button) {
                Ext.Msg.prompt("New Tournament", "Input the name of the tournament",
                    function(buton, response, messageBox) {
                        var tournamentStore = Ext.StoreManager.get("tournamentStore");
                        tournamentStore.add({tournamentName:response});
                        tournamentStore.sync();
                        tournamentStore.load();
                    }
                );
            }
        }
    ],
    listeners: {
        select: function(rowModel, record, index, event) {
            Ext.StoreManager.get("teamStore").load({
                url: "/TabRunner/Tournament/teams/" + record.get("id")
            });
            Ext.getCmp("addTeamButton").enable();
        }
    },

    getSelected: function() {
        return this.selModel.getLastSelected();
    }

});