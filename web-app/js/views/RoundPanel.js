/**
 * Created with IntelliJ IDEA.
 * User: Malcolm
 * Date: 10/19/12
 * Time: 11:19 AM
 * To change this template use File | Settings | File Templates.
 */
Ext.define("TabRunner.views.RoundPanel", {
    extend: "Ext.panel.Panel",
    alias: "widget.roundPanel",
    layout: "border",

    items: [
        {
            xtype: "grid",
            region: "west",
            title: "Rounds",
            store: Ext.create("Ext.data.Store", {
                storeId: "roundStore",
                fields: ["roundName"],
                proxy: {
                    type: "ajax"
                }
            }),
            columns: [
                {header: "Round Name", dataIndex: "roundName"}
            ],
            tbar: [
                {
                    xtype: "button",
                    text: "Generate",
                    disabled: true,
                    id: "generateRoundButton",
                    handler: function(button) {
                        Ext.Msg.prompt("New Round", "Input the name of the round",
                            function(buton, response, messageBox) {
                                var currentTournamentId = Ext.getCmp("tournamentList").getSelected().get("id");
                                Ext.Ajax.request({
                                    url: "/TabRunner/Tournament/generateRound/" + currentTournamentId,
                                    params: {
                                        roundName: response
                                    },
                                    success: function() {
                                        Ext.StoreManager.get("roundStore").load({
                                            url: "/TabRunner/Tournament/rounds/" + currentTournamentId
                                        });
                                    },
                                    failure: function(response, request) {
                                        console.log(response);
                                    }
                                });
                            }
                        );
                    }
                }
            ],
            listeners: {
                itemdblclick: function(grid, record, item, index, e, eOpts) {
                    var roundTabs = Ext.getCmp("roundTabs");
                    var roundName = record.get("roundName");
                    var roundId = record.get("id");
                    if(!roundTabs.setActiveTab("roundTab_" + roundId)) {
                        roundTabs.setActiveTab(roundTabs.add({
                            xtype: "roundInfoTab",
                            title: record.get("roundName"),
                            id: "roundTab_" + roundId,
                            store: Ext.create("Ext.data.Store", {
                                storeId: "roundInfoStore_" + roundId,
                                fields: ["teamP", "teamD", "judge1", "judge2", "ballot1", "ballot2"],
                                autoLoad: true,
                                proxy: {
                                    url: "/TabRunner/Round/pairings/" + roundId,
                                    type: 'ajax'
                                }
                            })
                        }));
                    }
                }
            }
        },
        {
            xtype: "tabpanel",
            region: "center",
            id: "roundTabs",
            items: []
        }
    ]
});